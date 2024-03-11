package org.dromara.pdf.fop.support.layout;

import lombok.extern.slf4j.Slf4j;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.area.AreaTreeHandler;
import org.apache.fop.fo.FOElementMapping;
import org.apache.fop.fo.FONode;
import org.apache.fop.fo.FOText;
import org.apache.fop.fo.FObjMixed;
import org.apache.fop.fo.extensions.ExternalDocument;
import org.apache.fop.fo.flow.Character;
import org.apache.fop.fo.flow.Float;
import org.apache.fop.fo.flow.*;
import org.apache.fop.fo.flow.table.*;
import org.apache.fop.fo.pagination.*;
import org.apache.fop.layoutmgr.*;
import org.apache.fop.layoutmgr.inline.*;
import org.apache.fop.layoutmgr.list.ListBlockLayoutManager;
import org.apache.fop.layoutmgr.list.ListItemLayoutManager;
import org.apache.fop.layoutmgr.table.TableLayoutManager;
import org.apache.fop.util.CharUtilities;

import java.util.*;

/**
 * 布局管理器映射
 *
 * @author xsx
 * @date 2022/12/19
 * @since 1.8
 * <p>
 * Copyright (c) 2020 xsx All Rights Reserved.
 * x-easypdf-fop is licensed under Mulan PSL v2.
 * You can use this software according to the terms and conditions of the Mulan PSL v2.
 * You may obtain a copy of Mulan PSL v2 at:
 * http://license.coscl.org.cn/MulanPSL2
 * THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
 * EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
 * MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
 * See the Mulan PSL v2 for more details.
 * </p>
 */
@Slf4j
public class LayoutManagerMapping implements LayoutManagerMaker {

    /**
     * The map of LayoutManagerMakers
     */
    private final Map<Object, Object> makers = new HashMap<>();

    private FOUserAgent userAgent;

    /**
     * default constructor
     */
    public LayoutManagerMapping() {
    }

    /**
     * Initializes the set of maker objects associated with this XEasyPdfTemplateLayoutManagerMapping
     */
    public void initialize(FOUserAgent userAgent) {
        this.userAgent = userAgent;
        registerMaker(FOText.class, new FOTextLayoutManagerMaker());
        registerMaker(FObjMixed.class, new Maker());
        registerMaker(BidiOverride.class, new BidiOverrideLayoutManagerMaker());
        registerMaker(Inline.class, new InlineLayoutManagerMaker());
        registerMaker(Footnote.class, new FootnoteLayoutManagerMaker());
        registerMaker(InlineContainer.class, new InlineContainerLayoutManagerMaker());
        registerMaker(BasicLink.class, new BasicLinkLayoutManagerMaker());
        registerMaker(Block.class, new BlockLayoutManagerMaker());
        registerMaker(Leader.class, new LeaderLayoutManagerMaker());
        registerMaker(RetrieveMarker.class, new RetrieveMarkerLayoutManagerMaker());
        registerMaker(RetrieveTableMarker.class, new RetrieveTableMarkerLayoutManagerMaker());
        registerMaker(Character.class, new CharacterLayoutManagerMaker());
        registerMaker(ExternalGraphic.class, new ExternalGraphicLayoutManagerMaker());
        registerMaker(BlockContainer.class, new BlockContainerLayoutManagerMaker());
        registerMaker(ListItem.class, new ListItemLayoutManagerMaker());
        registerMaker(ListBlock.class, new ListBlockLayoutManagerMaker());
        registerMaker(InstreamForeignObject.class, new InstreamForeignObjectLayoutManagerMaker());
        registerMaker(PageNumber.class, new PageNumberLayoutManagerMaker());
        registerMaker(PageNumberCitation.class, new PageNumberCitationLayoutManagerMaker());
        registerMaker(PageNumberCitationLast.class, new PageNumberCitationLastLayoutManagerMaker());
        registerMaker(Table.class, new TableLayoutManagerMaker());
        registerMaker(TableBody.class, new Maker());
        registerMaker(TableColumn.class, new Maker());
        registerMaker(TableRow.class, new Maker());
        registerMaker(TableCell.class, new Maker());
        registerMaker(TableFooter.class, new Maker());
        registerMaker(TableHeader.class, new Maker());
        registerMaker(Wrapper.class, new WrapperLayoutManagerMaker());
        registerMaker(Title.class, new InlineLayoutManagerMaker());
        registerMaker(ChangeBarBegin.class, new Maker());
        registerMaker(ChangeBarEnd.class, new Maker());
        registerMaker(MultiCase.class, new MultiCaseLayoutManagerMaker());
        registerMaker(MultiSwitch.class, new MultiSwitchLayoutManagerMaker());
        registerMaker(Float.class, new FloatLayoutManagerMaker());
    }

    /**
     * Registers a Maker class for a specific formatting object.
     *
     * @param clazz the formatting object class
     * @param maker the maker for the layout manager
     */
    protected void registerMaker(Class<?> clazz, Maker maker) {
        makers.put(clazz, maker);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("all")
    public void makeLayoutManagers(FONode node, List lms) {
        Maker maker = (Maker) makers.get(node.getClass());
        if (maker == null) {
            if (FOElementMapping.URI.equals(node.getNamespaceURI())) {
                log.error("No LayoutManager maker for class " + node.getClass());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("Skipping the creation of a layout manager for " + node.getClass());
                }
            }
        } else {
            maker.make(node, lms, userAgent);
        }
    }

    /**
     * {@inheritDoc}
     */
    public LayoutManager makeLayoutManager(FONode node) {
        List<Object> lms = new ArrayList<>();
        makeLayoutManagers(node, lms);
        if (lms.size() == 0) {
            throw new IllegalStateException("LayoutManager for class " + node.getClass() + " is missing.");
        } else if (lms.size() > 1) {
            throw new IllegalStateException("Duplicate LayoutManagers for class " + node.getClass() + " found, only one may be declared.");
        }
        return (LayoutManager) lms.get(0);
    }

    /**
     * {@inheritDoc}
     */
    public PageSequenceLayoutManager makePageSequenceLayoutManager(AreaTreeHandler ath, PageSequence ps) {
        return new PageSequenceLayoutManager(ath, ps);
    }

    /**
     * {@inheritDoc}
     */
    public ExternalDocumentLayoutManager makeExternalDocumentLayoutManager(AreaTreeHandler ath, ExternalDocument ed) {
        return new ExternalDocumentLayoutManager(ath, ed);
    }

    /**
     * {@inheritDoc}
     */
    public FlowLayoutManager makeFlowLayoutManager(PageSequenceLayoutManager pslm, Flow flow) {
        return new FlowLayoutManager(pslm, flow);
    }

    /**
     * {@inheritDoc}
     */
    public ContentLayoutManager makeContentLayoutManager(PageSequenceLayoutManager pslm, Title title) {
        return new ContentLayoutManager(pslm, title);
    }

    /**
     * {@inheritDoc}
     */
    public StaticContentLayoutManager makeStaticContentLayoutManager(PageSequenceLayoutManager pslm, StaticContent sc, SideRegion reg) {
        return new StaticContentLayoutManager(pslm, sc, reg);
    }

    /**
     * {@inheritDoc}
     */
    public StaticContentLayoutManager makeStaticContentLayoutManager(PageSequenceLayoutManager pslm, StaticContent sc, org.apache.fop.area.Block block) {
        return new StaticContentLayoutManager(pslm, sc, block);
    }

    /**
     * a layout manager maker base class
     */
    public static class Maker {
        /**
         * Create a layout manager.
         *
         * @param node the associated FO node
         * @param lms  a list of layout managers to which new manager is to be added
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            // no layout manager
        }
    }

    /**
     * a layout manager maker
     */
    public static class FOTextLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            FOText foText = (FOText) node;
            if (foText.length() > 0) {
                lms.add(new TextLayoutManager(foText, userAgent));
            }
        }
    }

    /**
     * a layout manager maker
     */
    public static class BidiOverrideLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            if (node instanceof BidiOverride) {
                lms.add(new BidiLayoutManager((BidiOverride) node));
            }
        }
    }

    /**
     * a layout manager maker
     */
    public static class InlineLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new InlineLayoutManager((InlineLevel) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class FootnoteLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new FootnoteLayoutManager((Footnote) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class InlineContainerLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new InlineContainerLayoutManager((InlineContainer) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class BasicLinkLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new BasicLinkLayoutManager((BasicLink) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class BlockLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new BlockLayoutManager((Block) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class LeaderLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new LeaderLayoutManager((Leader) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class CharacterLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            Character foCharacter = (Character) node;
            if (foCharacter.getCharacter() != CharUtilities.CODE_EOT) {
                lms.add(new CharacterLayoutManager(foCharacter));
            }
        }
    }

    /**
     * a layout manager maker
     */
    public static class ExternalGraphicLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            ExternalGraphic eg = (ExternalGraphic) node;
            if (!eg.getSrc().equals("")) {
                lms.add(new ExternalGraphicLayoutManager(eg));
            }
        }
    }

    /**
     * a layout manager maker
     */
    public static class BlockContainerLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new BlockContainerLayoutManager((BlockContainer) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class ListItemLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new ListItemLayoutManager((ListItem) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class ListBlockLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new ListBlockLayoutManager((ListBlock) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class InstreamForeignObjectLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new InstreamForeignObjectLM((InstreamForeignObject) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class PageNumberLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new PageNumberLayoutManager((PageNumber) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class PageNumberCitationLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new PageNumberCitationLayoutManager((PageNumberCitation) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class PageNumberCitationLastLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new PageNumberCitationLastLayoutManager((PageNumberCitationLast) node));
        }
    }

    /**
     * a layout manager maker
     */
    public static class TableLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new TableLayoutManager((Table) node));
        }
    }

    /**
     * a layout manager maker
     */
    public class RetrieveMarkerLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            Iterator<?> baseIter;
            baseIter = node.getChildNodes();
            if (baseIter == null) {
                return;
            }
            while (baseIter.hasNext()) {
                FONode child = (FONode) baseIter.next();
                makeLayoutManagers(child, lms);
            }
        }
    }

    public class RetrieveTableMarkerLayoutManagerMaker extends Maker {
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            FONode.FONodeIterator baseIter = node.getChildNodes();
            if (baseIter == null) {
                // this happens when the retrieve-table-marker cannot be resolved yet
                lms.add(new RetrieveTableMarkerLayoutManager((RetrieveTableMarker) node));
                return;
            }
            while (baseIter.hasNext()) {
                // this happens when the retrieve-table-marker has been resolved
                FONode child = baseIter.next();
                makeLayoutManagers(child, lms);
            }
        }
    }

    /**
     * a layout manager maker
     */
    public class WrapperLayoutManagerMaker extends Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            //We insert the wrapper LM before it's children so an ID
            //on the node can be registered on a page.
            lms.add(new WrapperLayoutManager((Wrapper) node));
            Iterator<?> baseIter;
            baseIter = node.getChildNodes();
            if (baseIter == null) {
                return;
            }
            while (baseIter.hasNext()) {
                FONode child = (FONode) baseIter.next();
                makeLayoutManagers(child, lms);
            }
        }
    }

    public static class MultiSwitchLayoutManagerMaker extends Maker {

        @Override
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new MultiSwitchLayoutManager((MultiSwitch) node));
        }
    }

    public static class MultiCaseLayoutManagerMaker extends Maker {

        @Override
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new MultiCaseLayoutManager((MultiCase) node));
        }
    }

    public static class FloatLayoutManagerMaker extends Maker {
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new FloatLayoutManager((Float) node));
        }
    }
}
