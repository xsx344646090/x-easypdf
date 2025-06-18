package org.dromara.pdf.pdfbox.support.fonts;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.fontbox.FontBoxFont;
import org.apache.fontbox.cff.CFFCIDFont;
import org.apache.fontbox.cff.CFFFont;
import org.apache.fontbox.ttf.*;
import org.apache.fontbox.type1.Type1Font;
import org.apache.fontbox.util.autodetect.FontFileFinder;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.font.CIDSystemInfo;
import org.apache.pdfbox.pdmodel.font.FontCache;
import org.apache.pdfbox.pdmodel.font.FontFormat;
import org.apache.pdfbox.pdmodel.font.PDPanoseClassification;
import org.dromara.pdf.pdfbox.core.base.Banner;
import org.dromara.pdf.pdfbox.core.enums.FontType;
import org.dromara.pdf.pdfbox.support.Constants;
import org.dromara.pdf.pdfbox.util.FileUtil;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.*;

/**
 * A FontProvider which searches for fonts on the local filesystem.
 *
 * @author John Hewson
 */
public class FileSystemFontProvider extends FontProvider {

    private static final Log LOG = LogFactory.getLog(FileSystemFontProvider.class);

    static {
        Banner.print();
    }

    private final List<FSFontInfo> fontInfoList = new ArrayList<>();
    private final FontCache cache;

    /**
     * Constructor.
     */
    FileSystemFontProvider(FontCache cache) {
        this.cache = cache;
        boolean scanDiskFont = !Objects.equals("false", System.getProperty(Constants.FONT_SCAN_SWITCH));
        if (scanDiskFont) {
            try {
                if (LOG.isInfoEnabled()) {
                    LOG.info("Will search the local system for fonts");
                }

                // scan the local system for font files
                FontFileFinder fontFileFinder = new FontFileFinder();
                List<URI> fonts = fontFileFinder.find();
                List<File> files = new ArrayList<>(fonts.size());
                for (URI font : fonts) {
                    files.add(new File(font));
                }

                if (LOG.isTraceEnabled()) {
                    LOG.info("Found " + files.size() + " fonts on the local system");
                }

                List<FSFontInfo> cachedInfos = loadDiskCache(files);
                if (cachedInfos != null && !cachedInfos.isEmpty()) {
                    fontInfoList.addAll(cachedInfos);
                    cachedInfos.forEach(info -> {
                        fontInfoByName.put(info.getFontName(), info);
                        fontInfoByName.put(info.getPostScriptName(), info);
                    });
                    LOG.info("Finished loading on-disk font cache, found " + fontInfoList.size() + " fonts");
                } else {
                    LOG.info("Building on-disk font cache, this may take a while");
                    scanFonts(files);
                    saveDiskCache();
                    LOG.info("Finished building on-disk font cache, found " + fontInfoList.size() + " fonts");
                }
            } catch (SecurityException e) {
                LOG.error("Error accessing the file system", e);
            }
        }
    }

    @Override
    public String addFont(File file) {
        try {
            String filePath = file.getPath().toLowerCase();
            if (filePath.endsWith(".ttf") || filePath.endsWith(".otf")) {
                addTrueTypeFont(file, FileUtil.parseName(file));
            } else if (filePath.endsWith(".ttc") || filePath.endsWith(".otc")) {
                addTrueTypeCollection(file, FileUtil.parseName(file));
            } else if (filePath.endsWith(".pfb")) {
                addType1Font(file, FileUtil.parseName(file));
            }
        } catch (IOException e) {
            LOG.error("Error parsing font " + file.getPath(), e);
        }
        return this.fontInfoList.get(this.fontInfoList.size() - 1).getFontName();
    }

    @Override
    public String addFont(File file, String alias) {
        try {
            String filePath = file.getPath().toLowerCase();
            if (filePath.endsWith(".ttf") || filePath.endsWith(".otf")) {
                addTrueTypeFont(file, alias);
            } else if (filePath.endsWith(".ttc") || filePath.endsWith(".otc")) {
                addTrueTypeCollection(file, alias);
            } else if (filePath.endsWith(".pfb")) {
                addType1Font(file, alias);
            }
        } catch (IOException e) {
            LOG.error("Error parsing font " + file.getPath(), e);
        }
        return this.fontInfoList.get(this.fontInfoList.size() - 1).getFontName();
    }

    @Override
    public String addFont(InputStream inputStream, String alias, FontType type) {
        try {
            if (type == FontType.TTF || type == FontType.OTF) {
                addTrueTypeFont(inputStream, alias, type);
            } else if (type == FontType.TTC || type == FontType.OTC) {
                addTrueTypeCollection(inputStream, alias, type);
            } else if (type == FontType.PFB) {
                addType1Font(inputStream, alias, type);
            }
        } catch (IOException e) {
            LOG.error("Error parsing font " + type, e);
        }
        return this.fontInfoList.get(this.fontInfoList.size() - 1).getFontName();
    }

    public File getDiskCacheFile() {
        return FileUtil.getFontCacheFile();
    }

    private void scanFonts(List<File> files) {
        // to force a specific font for debug, add code like this here:
        // files = Collections.singletonList(new File("font filename"))
        files.forEach(this::addFont);
    }

    /**
     * Saves the font metadata cache to disk.
     */
    private void saveDiskCache() {
        try {
            File file = getDiskCacheFile();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (FSFontInfo fontInfo : fontInfoList) {
                    writeFontInfo(writer, fontInfo);
                }
            } catch (IOException e) {
                LOG.warn("Could not write to font cache", e);
                LOG.warn("Installed fonts information will have to be reloaded for each start");
                LOG.warn("You can assign a directory to the 'pdfbox.fontcache' property");
            }
        } catch (SecurityException e) {
            LOG.debug("Couldn't create writer for font cache file", e);
        }
    }

    private void writeFontInfo(BufferedWriter writer, FSFontInfo fontInfo) throws IOException {
        if (Objects.nonNull(fontInfo.file)) {
            this.writeToFontInfo(writer, fontInfo);
        }
    }

    private void writeToFontInfo(BufferedWriter writer, FSFontInfo fontInfo) throws IOException {
        writer.write(fontInfo.getFontName());
        writer.write("|");
        writer.write(fontInfo.getPostScriptName());
        writer.write("|");
        writer.write(fontInfo.format.toString());
        writer.write("|");
        if (fontInfo.cidSystemInfo != null) {
            writer.write(fontInfo.cidSystemInfo.getRegistry() + '-' + fontInfo.cidSystemInfo.getOrdering() + '-' + fontInfo.cidSystemInfo.getSupplement());
        }
        writer.write("|");
        if (fontInfo.usWeightClass > -1) {
            writer.write(Integer.toHexString(fontInfo.usWeightClass));
        }
        writer.write("|");
        if (fontInfo.sFamilyClass > -1) {
            writer.write(Integer.toHexString(fontInfo.sFamilyClass));
        }
        writer.write("|");
        writer.write(Integer.toHexString(fontInfo.ulCodePageRange1));
        writer.write("|");
        writer.write(Integer.toHexString(fontInfo.ulCodePageRange2));
        writer.write("|");
        if (fontInfo.macStyle > -1) {
            writer.write(Integer.toHexString(fontInfo.macStyle));
        }
        writer.write("|");
        if (fontInfo.panose != null) {
            byte[] bytes = fontInfo.panose.getBytes();
            for (int i = 0; i < 10; i++) {
                String str = Integer.toHexString(bytes[i]);
                if (str.length() == 1) {
                    writer.write('0');
                }
                writer.write(str);
            }
        }
        writer.write("|");
        writer.write(fontInfo.file.getAbsolutePath());
        writer.newLine();
    }

    /**
     * Loads the font metadata cache from disk.
     */
    private List<FSFontInfo> loadDiskCache(List<File> files) {
        Set<String> pending = new HashSet<>(files.size());
        for (File file : files) {
            pending.add(file.getAbsolutePath());
        }

        List<FSFontInfo> results = new ArrayList<>();

        // Get the disk cache
        File file = null;
        boolean fileExists = false;
        try {
            file = getDiskCacheFile();
            fileExists = file.exists();
        } catch (SecurityException e) {
            LOG.debug("Error checking for file existence", e);
        }

        if (fileExists) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|", 11);
                    if (parts.length < 11) {
                        LOG.warn("Incorrect line '" + line + "' in font disk cache is skipped");
                        continue;
                    }

                    String fontName;
                    String postScriptName;
                    FontFormat format;
                    CIDSystemInfo cidSystemInfo = null;
                    int usWeightClass = -1;
                    int sFamilyClass = -1;
                    int ulCodePageRange1;
                    int ulCodePageRange2;
                    int macStyle = -1;
                    byte[] panose = null;
                    File fontFile;

                    fontName = parts[0];
                    postScriptName = parts[1];
                    format = FontFormat.valueOf(parts[2]);
                    if (!parts[3].isEmpty()) {
                        String[] ros = parts[3].split("-");
                        cidSystemInfo = new CIDSystemInfo(ros[0], ros[1], Integer.parseInt(ros[2]));
                    }
                    if (!parts[4].isEmpty()) {
                        usWeightClass = (int) Long.parseLong(parts[4], 16);
                    }
                    if (!parts[5].isEmpty()) {
                        sFamilyClass = (int) Long.parseLong(parts[5], 16);
                    }
                    ulCodePageRange1 = (int) Long.parseLong(parts[6], 16);
                    ulCodePageRange2 = (int) Long.parseLong(parts[7], 16);
                    if (!parts[8].isEmpty()) {
                        macStyle = (int) Long.parseLong(parts[8], 16);
                    }
                    if (!parts[9].isEmpty()) {
                        panose = new byte[10];
                        for (int i = 0; i < 10; i++) {
                            String str = parts[9].substring(i * 2, i * 2 + 2);
                            int b = Integer.parseInt(str, 16);
                            panose[i] = (byte) (b & 0xff);
                        }
                    }
                    fontFile = new File(parts[10]);
                    if (fontFile.exists()) {
                        FSFontInfo info = new FSFontInfo(fontFile, format, fontName, postScriptName, cidSystemInfo, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose, this);
                        results.add(info);
                    } else {
                        LOG.debug("Font file " + fontFile.getAbsolutePath() + " not found, skipped");
                    }
                    pending.remove(fontFile.getAbsolutePath());
                }
            } catch (IOException e) {
                LOG.warn("Error loading font cache, will be re-built", e);
                return null;
            }
        }

        if (!pending.isEmpty()) {
            // re-build the entire cache if we encounter un-cached fonts (could be optimised)
            LOG.warn("New fonts found, font cache will be re-built");
            return null;
        }

        return results;
    }

    /**
     * Adds a TTC or OTC to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addTrueTypeCollection(final File ttcFile, String alias) throws IOException {
        try (TrueTypeCollection ttc = new TrueTypeCollection(ttcFile)) {
            ttc.processAllFonts(ttf -> addTrueTypeFontImpl(ttf, ttcFile, alias));
        } catch (IOException e) {
            LOG.error("Could not load font file: " + ttcFile, e);
        }
    }

    /**
     * Adds a TTC or OTC to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addTrueTypeCollection(InputStream inputStream, String alias, FontType type) throws IOException {
        try (TrueTypeCollection ttc = new TrueTypeCollection(inputStream)) {
            File tempFile = new File(Constants.TEMP_FILE_PATH, alias + type.getSuffix());
            FileUtils.writeByteArrayToFile(tempFile, IOUtils.toByteArray(inputStream));
            ttc.processAllFonts(ttf -> addTrueTypeFontImpl(ttf, tempFile, ttf.getName()));
        } catch (IOException e) {
            LOG.error("Could not load font file: " + alias + type.getSuffix(), e);
        }
    }

    /**
     * Adds an OTF or TTF font to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addTrueTypeFont(File file, String alias) throws IOException {
        try {
            if (file.getPath().toLowerCase().endsWith(".otf")) {
                OTFParser parser = new OTFParser(true);
                OpenTypeFont otf = parser.parse(new RandomAccessReadBufferedFile(file));
                addTrueTypeFontImpl(otf, file, alias);
            } else {
                TTFParser parser = new TTFParser(true);
                TrueTypeFont ttf = parser.parse(new RandomAccessReadBufferedFile(file));
                addTrueTypeFontImpl(ttf, file, alias);
            }
        } catch (IOException e) {
            LOG.error("Could not load font file: " + file, e);
        }
    }

    /**
     * Adds an OTF or TTF font to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addTrueTypeFont(InputStream inputStream, String alias, FontType type) throws IOException {
        try {
            File tempFile = new File(Constants.TEMP_FILE_PATH, alias + type.getSuffix());
            FileUtils.writeByteArrayToFile(tempFile, IOUtils.toByteArray(inputStream));
            addTrueTypeFont(tempFile, alias);
        } catch (IOException e) {
            LOG.error("Could not load font file: " + alias + type.getSuffix(), e);
        }
    }

    /**
     * Adds an OTF or TTF font to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addTrueTypeFontImpl(TrueTypeFont ttf, File file, String alias) throws IOException {
        try {
            // read PostScript name, if any
            if (ttf.getName() != null && ttf.getName().contains("|")) {
                fontInfoList.add(new FSIgnored(file, FontFormat.TTF, "*skippipeinname*", "*skippipeinname*"));
                LOG.warn("Skipping font with '|' in name " + ttf.getName() + " in file " + file);
            } else if (ttf.getName() != null) {
                // ignore bitmap fonts
                if (ttf.getHeader() == null) {
                    fontInfoList.add(new FSIgnored(file, FontFormat.TTF, this.getFontName(file, ttf.getName()), ttf.getName()));
                    return;
                }
                int macStyle = ttf.getHeader().getMacStyle();

                int sFamilyClass = -1;
                int usWeightClass = -1;
                int ulCodePageRange1 = 0;
                int ulCodePageRange2 = 0;
                byte[] panose = null;
                OS2WindowsMetricsTable os2WindowsMetricsTable = ttf.getOS2Windows();
                // Apple's AAT fonts don't have an OS/2 table
                if (os2WindowsMetricsTable != null) {
                    sFamilyClass = os2WindowsMetricsTable.getFamilyClass();
                    usWeightClass = os2WindowsMetricsTable.getWeightClass();
                    ulCodePageRange1 = (int) os2WindowsMetricsTable.getCodePageRange1();
                    ulCodePageRange2 = (int) os2WindowsMetricsTable.getCodePageRange2();
                    panose = os2WindowsMetricsTable.getPanose();
                }

                String format;
                FSFontInfo info;
                if (ttf instanceof OpenTypeFont && ((OpenTypeFont) ttf).isPostScript()) {
                    format = "OTF";
                    CFFFont cff = ((OpenTypeFont) ttf).getCFF().getFont();
                    CIDSystemInfo ros = null;
                    if (cff instanceof CFFCIDFont) {
                        CFFCIDFont cidFont = (CFFCIDFont) cff;
                        String registry = cidFont.getRegistry();
                        String ordering = cidFont.getOrdering();
                        int supplement = cidFont.getSupplement();
                        ros = new CIDSystemInfo(registry, ordering, supplement);
                    }
                    info = new FSFontInfo(file, FontFormat.OTF, this.getFontName(file, ttf.getName()), ttf.getName(), ros, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose, this);
                    fontInfoList.add(info);
                    cache.addFont(info, ttf);
                    fontInfoByName.put(info.getFontName(), info);
                    fontInfoByName.put(info.getPostScriptName(), info);
                    if (Objects.nonNull(alias)) {
                        fontInfoByName.put(alias, new FSFontInfo(file, FontFormat.OTF, alias, ttf.getName(), ros, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose, this));
                    }
                } else {
                    CIDSystemInfo ros = null;
                    if (ttf.getTableMap().containsKey("gcid")) {
                        // Apple's AAT fonts have a "gcid" table with CID info
                        byte[] bytes = ttf.getTableBytes(ttf.getTableMap().get("gcid"));
                        String reg = new String(bytes, 10, 64, StandardCharsets.US_ASCII);
                        String registryName = reg.substring(0, reg.indexOf('\0'));
                        String ord = new String(bytes, 76, 64, StandardCharsets.US_ASCII);
                        String orderName = ord.substring(0, ord.indexOf('\0'));
                        int supplementVersion = bytes[140] << 8 & (bytes[141] & 0xFF);
                        ros = new CIDSystemInfo(registryName, orderName, supplementVersion);
                    }

                    format = "TTF";
                    info = new FSFontInfo(file, FontFormat.TTF, this.getFontName(file, ttf.getName()), ttf.getName(), ros, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose, this);
                    fontInfoList.add(info);
                    cache.addFont(info, ttf);
                    fontInfoByName.put(info.getFontName(), info);
                    fontInfoByName.put(info.getPostScriptName(), info);
                    if (Objects.nonNull(alias)) {
                        fontInfoByName.put(alias, new FSFontInfo(file, FontFormat.TTF, alias, ttf.getName(), ros, usWeightClass, sFamilyClass, ulCodePageRange1, ulCodePageRange2, macStyle, panose, this));
                    }
                }

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Added font [name: '" + info.getFontName() + "', postScriptName: '" + info.getPostScriptName() + "', alias: '" + Optional.ofNullable(alias).orElse("") + "']");
                }
                if (LOG.isTraceEnabled()) {
                    NamingTable name = ttf.getNaming();
                    if (name != null) {
                        LOG.trace(format + ": '" + name.getPostScriptName() + "' / '" + name.getFontFamily() + "' / '" + name.getFontSubFamily() + "'");
                    }
                }
            } else {
                fontInfoList.add(new FSIgnored(file, FontFormat.TTF, "*skipnoname*", "*skipnoname*"));
                LOG.warn("Missing 'name' entry for PostScript name in font " + file);
            }
        } catch (IOException e) {
            fontInfoList.add(new FSIgnored(file, FontFormat.TTF, "*skipexception*", "*skipexception*"));
            LOG.error("Could not load font file: " + file, e);
        } finally {
            ttf.close();
        }
    }

    /**
     * Adds a Type 1 font to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addType1Font(File pfbFile, String alias) throws IOException {
        try (InputStream inputStream = Files.newInputStream(pfbFile.toPath())) {
            Type1Font type1 = Type1Font.createWithPFB(inputStream);
            addType1Font(pfbFile, type1, alias);
        } catch (IOException e) {
            LOG.error("Could not load font file: " + pfbFile, e);
        }
    }

    /**
     * Adds a Type 1 font to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addType1Font(InputStream inputStream, String alias, FontType type) throws IOException {
        try {
            Type1Font type1 = Type1Font.createWithPFB(inputStream);
            File tempFile = new File(Constants.TEMP_FILE_PATH, alias + type.getSuffix());
            FileUtils.writeByteArrayToFile(tempFile, IOUtils.toByteArray(inputStream));
            addType1Font(tempFile, type1, null);
        } catch (IOException e) {
            LOG.error("Could not load font file: " + alias + type.getSuffix(), e);
        }
    }

    /**
     * Adds a Type 1 font to the file cache. To reduce memory, the parsed font is not cached.
     */
    private void addType1Font(File pfbFile, Type1Font type1, String alias) throws IOException {
        try {
            if (type1.getName() == null) {
                fontInfoList.add(new FSIgnored(pfbFile, FontFormat.PFB, "*skipnoname*", "*skipnoname*"));
                LOG.warn("Missing 'name' entry for PostScript name in font " + pfbFile);
                return;
            }
            if (type1.getName().contains("|")) {
                fontInfoList.add(new FSIgnored(pfbFile, FontFormat.PFB, "*skippipeinname*", "*skippipeinname*"));
                LOG.warn("Skipping font with '|' in name " + type1.getName() + " in file " + pfbFile);
                return;
            }
            FSFontInfo info = new FSFontInfo(pfbFile, FontFormat.PFB, type1.getName(), type1.getName(), null, -1, -1, 0, 0, -1, null, this);
            fontInfoList.add(info);
            fontInfoByName.put(info.getFontName(), info);
            if (Objects.nonNull(alias)) {
                fontInfoList.add(new FSFontInfo(pfbFile, FontFormat.PFB, alias, type1.getName(), null, -1, -1, 0, 0, -1, null, this));
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Added font [name: '" + info.getFontName() + "', postScriptName: '" + info.getPostScriptName() + "', alias: '" + Optional.ofNullable(alias).orElse("") + "']");
            }
            if (LOG.isTraceEnabled()) {
                LOG.trace("PFB: '" + type1.getName() + "' / '" + type1.getFamilyName() + "' / '" + type1.getWeight() + "'");
            }
        } catch (Exception e) {
            LOG.error("Could not load font file: pfb", e);
        }
    }

    @Override
    public String toDebugString() {
        StringBuilder sb = new StringBuilder();
        for (FSFontInfo info : fontInfoList) {
            sb.append(info.getFormat());
            sb.append(": ");
            sb.append(info.getPostScriptName());
            sb.append(": ");
            sb.append(info.file.getPath());
            sb.append('\n');
        }
        return sb.toString();
    }

    @Override
    public List<? extends FontInfo> getFontInfo() {
        return fontInfoList;
    }

    private String getFontName(File file, String postScriptName) {
        String fontName;
        try {
            fontName = Font.createFont(Font.TRUETYPE_FONT, file).getFontName();
        } catch (Exception e) {
            fontName = postScriptName;
        }
        return fontName;
    }

    private static class FSFontInfo extends FontInfo {
        private final String fontName;
        private final String postScriptName;
        private final FontFormat format;
        private final CIDSystemInfo cidSystemInfo;
        private final int usWeightClass;
        private final int sFamilyClass;
        private final int ulCodePageRange1;
        private final int ulCodePageRange2;
        private final int macStyle;
        private final PDPanoseClassification panose;
        private final File file;
        private final FileSystemFontProvider parent;

        private FSFontInfo(File file, FontFormat format, String fontName, String postScriptName, CIDSystemInfo cidSystemInfo, int usWeightClass, int sFamilyClass, int ulCodePageRange1, int ulCodePageRange2, int macStyle, byte[] panose, FileSystemFontProvider parent) {
            this.file = file;
            this.format = format;
            this.fontName = fontName;
            this.postScriptName = postScriptName;
            this.cidSystemInfo = cidSystemInfo;
            this.usWeightClass = usWeightClass;
            this.sFamilyClass = sFamilyClass;
            this.ulCodePageRange1 = ulCodePageRange1;
            this.ulCodePageRange2 = ulCodePageRange2;
            this.macStyle = macStyle;
            this.panose = panose != null && panose.length >= PDPanoseClassification.LENGTH ? new PDPanoseClassification(panose) : null;
            this.parent = parent;
        }

        @Override
        public String getFontName() {
            return fontName;
        }

        @Override
        public String getPostScriptName() {
            return postScriptName;
        }

        @Override
        public FontFormat getFormat() {
            return format;
        }

        @Override
        public CIDSystemInfo getCIDSystemInfo() {
            return cidSystemInfo;
        }

        /**
         * {@inheritDoc}
         * <p>
         * The method returns null if there is there was an error opening the font.
         */
        @Override
        public synchronized FontBoxFont getFont() {
            // synchronized to avoid race condition on cache access,
            // which could result in an unreferenced but open font
            // return parent.cache.getFont(this);
            FontBoxFont cached = parent.cache.getFont(this);
            if (cached != null) {
                return cached;
            } else {
                if (Objects.isNull(file)) {
                    return null;
                }
                FontBoxFont font;
                switch (format) {
                    case PFB:
                        font = getType1Font(postScriptName, file);
                        break;
                    case TTF:
                        font = getTrueTypeFont(postScriptName, file);
                        break;
                    case OTF:
                        font = getOTFFont(postScriptName, file);
                        break;
                    default:
                        throw new RuntimeException("can't happen");
                }
                if (font != null) {
                    parent.cache.addFont(this, font);
                }
                return font;
            }
        }

        @Override
        public int getFamilyClass() {
            return sFamilyClass;
        }

        @Override
        public int getWeightClass() {
            return usWeightClass;
        }

        @Override
        public int getCodePageRange1() {
            return ulCodePageRange1;
        }

        @Override
        public int getCodePageRange2() {
            return ulCodePageRange2;
        }

        @Override
        public int getMacStyle() {
            return macStyle;
        }

        @Override
        public PDPanoseClassification getPanose() {
            return panose;
        }

        @Override
        public String toString() {
            return super.toString() + " " + file;
        }

        private TrueTypeFont getTrueTypeFont(String postScriptName, File file) {
            try {
                TrueTypeFont ttf = readTrueTypeFont(postScriptName, file);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Loaded " + postScriptName + " from " + file);
                }
                return ttf;
            } catch (IOException e) {
                LOG.warn("Could not load font file: " + file, e);
            }
            return null;
        }

        private TrueTypeFont readTrueTypeFont(String postScriptName, File file) throws IOException {
            if (file.getName().toLowerCase().endsWith(".ttc")) {
                @SuppressWarnings("squid:S2095")
                // ttc not closed here because it is needed later when ttf is accessed,
                // e.g. rendering PDF with non-embedded font which is in ttc file in our font directory
                TrueTypeCollection ttc = new TrueTypeCollection(file);
                TrueTypeFont ttf;
                try {
                    ttf = ttc.getFontByName(postScriptName);
                } catch (IOException ex) {
                    ttc.close();
                    throw ex;
                }
                if (ttf == null) {
                    ttc.close();
                    throw new IOException("Font " + postScriptName + " not found in " + file);
                }
                return ttf;
            } else {
                TTFParser ttfParser = new TTFParser(true);
                return ttfParser.parse(new RandomAccessReadBufferedFile(file));
            }
        }

        private OpenTypeFont getOTFFont(String postScriptName, File file) {
            try {
                if (file.getName().toLowerCase().endsWith(".ttc")) {
                    @SuppressWarnings("squid:S2095")
                    // ttc not closed here because it is needed later when ttf is accessed,
                    // e.g. rendering PDF with non-embedded font which is in ttc file in our font directory
                    TrueTypeCollection ttc = new TrueTypeCollection(file);
                    TrueTypeFont ttf;
                    try {
                        ttf = ttc.getFontByName(postScriptName);
                    } catch (IOException ex) {
                        LOG.error(ex.getMessage(), ex);
                        ttc.close();
                        return null;
                    }
                    if (ttf == null) {
                        ttc.close();
                        throw new IOException("Font " + postScriptName + " not found in " + file);
                    }
                    return (OpenTypeFont) ttf;
                }

                OTFParser parser = new OTFParser(true);
                OpenTypeFont otf = parser.parse(new RandomAccessReadBufferedFile(file));

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Loaded " + postScriptName + " from " + file);
                }
                return otf;
            } catch (IOException e) {
                LOG.warn("Could not load font file: " + file, e);
            }
            return null;
        }

        private Type1Font getType1Font(String postScriptName, File file) {
            try (InputStream input = Files.newInputStream(file.toPath())) {
                Type1Font type1 = Type1Font.createWithPFB(input);

                if (LOG.isDebugEnabled()) {
                    LOG.debug("Loaded " + postScriptName + " from " + file);
                }
                return type1;
            } catch (IOException e) {
                LOG.warn("Could not load font file: " + file, e);
            }
            return null;
        }
    }

    /**
     * Represents ignored fonts (i.e. bitmap fonts).
     */
    private static final class FSIgnored extends FSFontInfo {
        private FSIgnored(File file, FontFormat format, String fontName, String postScriptName) {
            super(file, format, fontName, postScriptName, null, 0, 0, 0, 0, 0, null, null);
        }
    }
}
