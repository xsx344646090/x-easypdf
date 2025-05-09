/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dromara.pdf.pdfbox.support.linearization;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.pdfbox.cos.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * [!CLASS_DESCRIPTION!]
 *
 * @author Jonathan Rahn
 * @version $Id$
 */
class PreLinearizationOptimizer {

    private static final Log LOG = LogFactory.getLog(PreLinearizationOptimizer.class);

    //~ Instance members ------------------------------------------------------------------------------------------------------------------------------

    private final COSDocument document;
    /**
     * [!FIELD_DESCRIPTION!]
     */
    BidirectionalMultiMap<ObjUser, COSBase> userObjectMap;
    private boolean pushed_inherited_attributes_to_pages = false;

    //~ Constructors ----------------------------------------------------------------------------------------------------------------------------------

    /**
     * [!CONSTR_DESCIRPTION_FOR_PreLinearizationOptimizer!]
     *
     * @param document [!PARA_DESCRIPTION!]
     */
    PreLinearizationOptimizer(final COSDocument document) {
        this.document = document;
        this.userObjectMap = new BidirectionalMultiMap<>();
    }

    //~ Methods ---------------------------------------------------------------------------------------------------------------------------------------

    private List<COSObject> pushInheritedAttributesToPage(List<COSObject> pages, final boolean allow_changes, final boolean warn_skipped_keys) {
        // Traverse pages tree pushing all inherited resources down to the
        // page level.

        final HashMap<COSName, COSBase> key_ancestors = new HashMap<>();

        final HashSet<COSBase> visited = new HashSet<>();
        final COSDictionary trailer = this.document.getTrailer();
        final COSObject root_obj = (COSObject) (trailer.getItem(COSName.ROOT));
        final COSDictionary root = (COSDictionary) (root_obj.getObject());
        final COSObject pages_obj = (COSObject) root.getItem(COSName.PAGES);

        pushInheritedAttributesToPageInternal(pages_obj, key_ancestors, pages, allow_changes, warn_skipped_keys, visited);
        if (!key_ancestors.isEmpty()) {
            LOG.info("key_ancestors not empty after" + " pushing inherited attributes to pages");
        }
        this.pushed_inherited_attributes_to_pages = true;
        return pages;
    }


    private void pushInheritedAttributesToPageInternal(final COSObject cur_pages_obj, final HashMap<COSName, COSBase> key_ancestors, final List<COSObject> pages, final boolean allow_changes, final boolean warn_skipped_keys, final Set<COSBase> visited) {
        final COSDictionary cur_pages = (COSDictionary) cur_pages_obj.getObject();

        if (visited.contains(cur_pages)) {
            LOG.info("Loop detected in /Pages structure (inherited attributes)");
        }
        visited.add(cur_pages);

        // Extract the underlying dictionary object
        final COSName type = (COSName) cur_pages.getItem(COSName.TYPE);

        if (type == COSName.PAGES) {
            // Make a list of inheritable keys. Only the keys /MediaBox,
            // /CropBox, /Resources, and /Rotate are inheritable
            // attributes. Push this object onto the stack of pages nodes
            // that have values for this attribute.

            final HashSet<COSName> inheritable_keys = new HashSet<>();
            final Set<COSName> keys = cur_pages.keySet();

            for (final COSName key : keys) {
                if ((key == COSName.MEDIA_BOX) || (key == COSName.CROP_BOX) || (key == COSName.RESOURCES) || (key == COSName.ROTATE)) {
                    if (!allow_changes) {
                        LOG.info("optimize detected an " + "inheritable attribute when called " + "in no-change mode");
                    }

                    // This is an inheritable resource
                    inheritable_keys.add(key);

                    final COSBase oh = cur_pages.getItem(key);

                    key_ancestors.put(key, oh);

                    // Remove this resource from this node.  It will be
                    // reattached at the page level.
                    cur_pages.removeItem(key);
                } else if (!((key == COSName.TYPE) || (key == COSName.PARENT) || (key == COSName.KIDS) || (key == COSName.COUNT))) {
                    // Warn when flattening, but not if the key is at the top
                    // level (i.e. "/Parent" not set), as we don't change these;
                    // but flattening removes intermediate /Pages nodes.
                    if ((warn_skipped_keys) && (cur_pages.containsKey(COSName.PARENT))) {
                        LOG.info("unknown key not inherited");
                        LOG.info("Unknown key " + key + " in /Pages object" + " is being discarded as a result of flattening the /Pages tree");
                    }
                }
            }

            // Visit descendant nodes.
            final COSArray kids = (COSArray) cur_pages.getItem(COSName.KIDS);

            // int n = kids.getArrayNItems();
            for (final COSBase kid : kids) {
                pushInheritedAttributesToPageInternal((COSObject) kid, key_ancestors, pages, allow_changes, warn_skipped_keys, visited);
            }

            // For each inheritable key, pop the stack.  If the stack
            // becomes empty, remove it from the map.  That way, the
            // invariant that the list of keys in key_ancestors is exactly
            // those keys for which inheritable attributes are available.
            // if (! inheritable_keys.isEmpty())
            // {
            for (final COSName key : inheritable_keys) {
                key_ancestors.remove(key);
            }
        } else if (type == COSName.PAGE) {
            // Add all available inheritable attributes not present in
            // this object to this object.
            key_ancestors.forEach((key, object) -> {
                if (!cur_pages.containsKey(key)) {
                    cur_pages.setItem(key, object);
                }
            });
            pages.add(cur_pages_obj);
        } else {
            LOG.info("Invalid type in page tree!");
        }
        visited.remove(cur_pages);
    }


    /**
     * @param pages Pass an empty list, it will be filled with all pages of the document
     * @return
     * @throws IOException
     */
    BidirectionalMultiMap<ObjUser, COSBase> optimize(List<COSObject> pages) throws IOException {
        if (!this.userObjectMap.isT1Empty()) {
            // already optimized
            return this.userObjectMap;
        }

        // The PDF specification indicates that /Outlines is supposed to
        // be an indirect reference.  Force it to be so if it exists and
        // is direct.  (This has been seen in the wild.)
      /*   COSObject root = this.document.getCatalog();//getRoot();
              if (root.getItem(COSName.OUTLINES).isDirect())
              {

              }
              if (root.getKey("/Outlines").isDictionary())
              {
                  QPDFObjectHandle outlines = root.getKey("/Outlines");
                  if (! outlines.isIndirect())
                  {
                      QTC::TC("qpdf", "QPDF_optimization indirect outlines");
                      root.replaceKey("/Outlines", makeIndirectObject(outlines));
                  }
              } */
        // Traverse pages tree pushing all inherited resources down to the
        // page level.  This also initializes this.m.all_pages.
        pushInheritedAttributesToPage(pages, true, false);

        final COSObject root = (COSObject) document.getTrailer().getItem(COSName.ROOT);

        // Traverse pages
        int pageno = 0;

        for (final COSBase page : pages) {
            updateObjectMaps(new ObjUser(ObjUser.user_e.ou_page, pageno++), page);
        }

        // Traverse document-level items
        document.getTrailer().keySet().forEach((key) -> {
            if (key == COSName.ROOT) {
                // handled separately
            } else {
                updateObjectMaps(new ObjUser(ObjUser.user_e.ou_trailer_key, key), document.getTrailer().getItem(key));
            }
        });

        ((COSDictionary) root.getObject()).keySet().forEach((key) -> {
            // Technically, /I keys from /Thread dictionaries are supposed
            // to be handled separately, but we are going to disregard
            // that specification for now.  There is loads of evidence
            // that pdlin and Acrobat both disregard things like this from
            // time to time, so this is almost certain not to cause any
            // problems.
            updateObjectMaps(new ObjUser(ObjUser.user_e.ou_root_key, key), ((COSDictionary) root.getObject()).getItem(key));
        });

        this.userObjectMap.addValuePair(new ObjUser(ObjUser.user_e.ou_root), root);
        return this.userObjectMap;

        // filterCompressedObjects(object_stream_data);
    }


    private void updateObjectMaps(final ObjUser ou, final COSBase treeObj) {
        final HashSet<COSBase> visited = new HashSet<>();

        updateObjectMapsInternal(ou, visited, treeObj, true);
    }


    private void updateObjectMapsInternal(final ObjUser ou, final Set<COSBase> visited, final COSBase treeObjIn, final boolean top) {
        // Traverse the object tree from this point taking care to avoid
        // crossing page boundaries.
        if (visited.contains(treeObjIn)) {
            return;
        }

        boolean is_page_node = false;
        COSBase treeObj = treeObjIn;

        if (treeObj instanceof COSObject && ((COSObject) treeObj).getObject() == null) {
            treeObj.setDirect(false);
        } else {
            treeObj.setDirect(true);
        }

        if (treeObjIn instanceof COSObject) {
            treeObj = ((COSObject) treeObjIn).getObject();
        }

        if ((treeObj instanceof COSDictionary) && ((COSDictionary) treeObj).containsKey(COSName.TYPE)) {
            final COSName type = (COSName) ((COSDictionary) treeObj).getItem(COSName.TYPE);

            if (type == COSName.PAGE) {
                is_page_node = true;
                if (!top) {
                    return;
                }
            }
        }

        if (treeObjIn instanceof COSObject) {
            this.userObjectMap.addValuePair(ou, treeObjIn);
            visited.add(treeObjIn);
        }

        if (treeObj instanceof COSArray) {
            visited.add(treeObjIn);
            for (final COSBase item : ((COSArray) treeObj)) {
                updateObjectMapsInternal(ou, visited, item, false);
            }
        } else if ((treeObj instanceof COSDictionary) || (treeObj instanceof COSStream)) {
            visited.add(treeObjIn);
            for (final COSName key : ((COSDictionary) treeObj).keySet()) {
                if (is_page_node && (key == COSName.THUMB)) {
                    // Traverse page thumbnail dictionaries as a special
                    // case.
                    updateObjectMaps(new ObjUser(ObjUser.user_e.ou_thumb, ou.pageno), ((COSDictionary) treeObj).getItem(key));
                } else if (!is_page_node || (key != COSName.PARENT)) {
                    final COSBase child = ((COSDictionary) treeObj).getItem(key);

                    updateObjectMapsInternal(ou, visited, child, false);
                }
            }
        }
    }
}
