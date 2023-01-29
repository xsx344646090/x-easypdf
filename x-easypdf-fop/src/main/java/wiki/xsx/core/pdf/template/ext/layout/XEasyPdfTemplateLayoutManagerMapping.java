package wiki.xsx.core.pdf.template.ext.layout;

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
 * Copyright (c) 2020-2023 xsx All Rights Reserved.
 * x-easypdf is licensed under Mulan PSL v2.
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
public class XEasyPdfTemplateLayoutManagerMapping implements LayoutManagerMaker {

    /**
     * The map of LayoutManagerMakers
     */
    private final Map<Object, Object> makers = new HashMap<>();

    private FOUserAgent userAgent;

    /**
     * default constructor
     */
    public XEasyPdfTemplateLayoutManagerMapping() {
    }

    /**
     * Initializes the set of maker objects associated with this XEasyPdfTemplateLayoutManagerMapping
     */
    public void initialize(FOUserAgent userAgent) {
        this.userAgent = userAgent;
        registerMaker(FOText.class, new XEasyPdfTemplateLayoutManagerMapping.FOTextLayoutManagerMaker());
        registerMaker(FObjMixed.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(BidiOverride.class, new XEasyPdfTemplateLayoutManagerMapping.BidiOverrideLayoutManagerMaker());
        registerMaker(Inline.class, new XEasyPdfTemplateLayoutManagerMapping.InlineLayoutManagerMaker());
        registerMaker(Footnote.class, new XEasyPdfTemplateLayoutManagerMapping.FootnoteLayoutManagerMaker());
        registerMaker(InlineContainer.class, new XEasyPdfTemplateLayoutManagerMapping.InlineContainerLayoutManagerMaker());
        registerMaker(BasicLink.class, new XEasyPdfTemplateLayoutManagerMapping.BasicLinkLayoutManagerMaker());
        registerMaker(Block.class, new XEasyPdfTemplateLayoutManagerMapping.BlockLayoutManagerMaker());
        registerMaker(Leader.class, new XEasyPdfTemplateLayoutManagerMapping.LeaderLayoutManagerMaker());
        registerMaker(RetrieveMarker.class, new XEasyPdfTemplateLayoutManagerMapping.RetrieveMarkerLayoutManagerMaker());
        registerMaker(RetrieveTableMarker.class, new XEasyPdfTemplateLayoutManagerMapping.RetrieveTableMarkerLayoutManagerMaker());
        registerMaker(Character.class, new XEasyPdfTemplateLayoutManagerMapping.CharacterLayoutManagerMaker());
        registerMaker(ExternalGraphic.class, new XEasyPdfTemplateLayoutManagerMapping.ExternalGraphicLayoutManagerMaker());
        registerMaker(BlockContainer.class, new XEasyPdfTemplateLayoutManagerMapping.BlockContainerLayoutManagerMaker());
        registerMaker(ListItem.class, new XEasyPdfTemplateLayoutManagerMapping.ListItemLayoutManagerMaker());
        registerMaker(ListBlock.class, new XEasyPdfTemplateLayoutManagerMapping.ListBlockLayoutManagerMaker());
        registerMaker(InstreamForeignObject.class, new XEasyPdfTemplateLayoutManagerMapping.InstreamForeignObjectLayoutManagerMaker());
        registerMaker(PageNumber.class, new XEasyPdfTemplateLayoutManagerMapping.PageNumberLayoutManagerMaker());
        registerMaker(PageNumberCitation.class, new XEasyPdfTemplateLayoutManagerMapping.PageNumberCitationLayoutManagerMaker());
        registerMaker(PageNumberCitationLast.class, new XEasyPdfTemplateLayoutManagerMapping.PageNumberCitationLastLayoutManagerMaker());
        registerMaker(Table.class, new XEasyPdfTemplateLayoutManagerMapping.TableLayoutManagerMaker());
        registerMaker(TableBody.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(TableColumn.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(TableRow.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(TableCell.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(TableFooter.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(TableHeader.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(Wrapper.class, new WrapperLayoutManagerMaker());
        registerMaker(Title.class, new XEasyPdfTemplateLayoutManagerMapping.InlineLayoutManagerMaker());
        registerMaker(ChangeBarBegin.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(ChangeBarEnd.class, new XEasyPdfTemplateLayoutManagerMapping.Maker());
        registerMaker(MultiCase.class, new MultiCaseLayoutManagerMaker());
        registerMaker(MultiSwitch.class, new MultiSwitchLayoutManagerMaker());
        registerMaker(Float.class, new XEasyPdfTemplateLayoutManagerMapping.FloatLayoutManagerMaker());
    }

    /**
     * Registers a Maker class for a specific formatting object.
     *
     * @param clazz the formatting object class
     * @param maker the maker for the layout manager
     */
    protected void registerMaker(Class<?> clazz, XEasyPdfTemplateLayoutManagerMapping.Maker maker) {
        makers.put(clazz, maker);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("all")
    public void makeLayoutManagers(FONode node, List lms) {
        XEasyPdfTemplateLayoutManagerMapping.Maker maker = (XEasyPdfTemplateLayoutManagerMapping.Maker) makers.get(node.getClass());
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
    public static class FOTextLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
        /**
         * {@inheritDoc}
         */
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            FOText foText = (FOText) node;
            if (foText.length() > 0) {
                lms.add(new XEasyPdfTemplateTextLayoutManager(foText, userAgent));
            }
        }
    }

    /**
     * a layout manager maker
     */
    public static class BidiOverrideLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class InlineLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class FootnoteLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class InlineContainerLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class BasicLinkLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class BlockLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class LeaderLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class CharacterLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class ExternalGraphicLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class BlockContainerLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class ListItemLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class ListBlockLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class InstreamForeignObjectLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class PageNumberLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class PageNumberCitationLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class PageNumberCitationLastLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public static class TableLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public class RetrieveMarkerLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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

    public class RetrieveTableMarkerLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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
    public class WrapperLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
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

    public static class MultiSwitchLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {

        @Override
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new MultiSwitchLayoutManager((MultiSwitch) node));
        }
    }

    public static class MultiCaseLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {

        @Override
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new MultiCaseLayoutManager((MultiCase) node));
        }
    }

    public static class FloatLayoutManagerMaker extends XEasyPdfTemplateLayoutManagerMapping.Maker {
        public void make(FONode node, List<Object> lms, FOUserAgent userAgent) {
            lms.add(new FloatLayoutManager((Float) node));
        }
    }
}
