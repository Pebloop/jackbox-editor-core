package jackbox.editor.core.utils;

import com.jpexs.decompiler.flash.AbortRetryIgnoreHandler;
import com.jpexs.decompiler.flash.EventListener;
import com.jpexs.decompiler.flash.ReadOnlyTagList;
import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.exporters.ImageExporter;
import com.jpexs.decompiler.flash.exporters.modes.ImageExportMode;
import com.jpexs.decompiler.flash.exporters.settings.ImageExportSettings;
import com.jpexs.decompiler.flash.importers.ImageImporter;
import com.jpexs.decompiler.flash.tags.DefineBitsLossless2Tag;
import com.jpexs.decompiler.flash.tags.DefineBitsLosslessTag;
import com.jpexs.decompiler.flash.tags.DefineEditTextTag;
import com.jpexs.decompiler.flash.tags.DefineTextTag;
import com.jpexs.decompiler.flash.tags.base.ImageTag;
import com.jpexs.decompiler.flash.tags.base.MissingCharacterHandler;
import com.jpexs.decompiler.flash.tags.text.TextParseException;
import com.jpexs.decompiler.flash.types.ALPHABITMAPDATA;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SwfEditor {
    SWF swf;

    public SwfEditor(SWF swf) {
        this.swf = swf;
    }

    public byte[] apply() throws IOException {
        OutputStream os = new FileOutputStream("./tmp.swf");
        try {
            this.swf.saveTo(os);
        } catch (IOException e) {
            System.out.println("ERROR: Error during SWF saving");
        }
        os.close();
        File file = new File("./tmp.swf");
        InputStream is = new FileInputStream(file);
        return is.readAllBytes();
    }

    public void setDefineEditTextTag(String id, String text) {
        swf.getTags().forEach(tag -> {
            if (tag.getTagName() != "DefineEditText") return;

            if (tag.getName().equals(id)) {
                ((DefineEditTextTag) tag).initialText = text;
                tag.setModified(true);
            }
        });
    }

    public void setDefineTextTag(String id, List<String> text) {
        swf.getTags().forEach(tag -> {
            if (tag.getTagName() != "DefineText") return;

            if (tag.getName().equals(id)) {
                DefineTextTag defineTextTag = (DefineTextTag) tag;
                MissingCharacterHandler missingCharacterHandler = new MissingCharacterHandler();
                String[] texts = new String[text.size()];
                for (int j = 0; j < text.size(); j++) {
                    texts[j] = text.get(j);
                }
                try {
                    String formattedText = defineTextTag.getFormattedText(true).text;
                    defineTextTag.setFormattedText(missingCharacterHandler, formattedText, texts);
                } catch (TextParseException e) {
                    throw new RuntimeException(e);
                }
                tag.setModified(true);
            }
        });
    }

    public void setDefineEditTextTags(String[] ids, String text) {
        swf.getTags().forEach(tag -> {
            if (tag.getTagName() != "DefineEditText") return;

            for (String id : ids) {
                if (tag.getName().equals(id)) {
                    ((DefineEditTextTag) tag).initialText = text;
                    tag.setModified(true);
                }
            }
        });
    }

    public String getDefineEditTextTag(String id) {
        AtomicReference<String> res = new AtomicReference<>("");
        swf.getTags().forEach(tag -> {
            if (!Objects.equals(tag.getTagName(), "DefineEditText")) return;

            if (tag.getName().equals(id)) {
                res.set(((DefineEditTextTag) tag).initialText);
                return;
            }
        });
        return res.get();
    }

    public List<String> getDefineTextTag(String id) {
        AtomicReference<List<String>> res = new AtomicReference<>(Collections.emptyList());
        swf.getTags().forEach(tag -> {
            if (!Objects.equals(tag.getTagName(), "DefineText")) return;

            if (tag.getName().equals(id)) {
                DefineTextTag defineTextTag = (DefineTextTag) tag;
                res.set(defineTextTag.getTexts());
                return;
            }
        });
        return res.get();
    }

    public byte[] getDefineBitsLosslessTag(String id) {
        AtomicReference<List<File>> files = new AtomicReference<>(Collections.emptyList());
        AtomicReference<byte[]> res = new AtomicReference<>(new byte[0]);
        swf.getTags().forEach(tag -> {
            if (!Objects.equals(tag.getTagName(), "DefineBitsLossless")
            && !Objects.equals(tag.getTagName(), "DefineBitsLossless2")) return;

            System.out.println(tag.getName());
            if (tag.getName().equals(id)) {

                    // create png image
                    ImageExporter exporter = new ImageExporter();
                    ReadOnlyTagList r = new ReadOnlyTagList(Collections.singletonList(tag));
                    ImageExportSettings settings = new ImageExportSettings(ImageExportMode.PNG);
                    EventListener listener = new EventListener() {
                        @Override
                        public void handleExportingEvent(String s, int i, int i1, Object o) {
                        }

                        @Override
                        public void handleExportedEvent(String s, int i, int i1, Object o) {
                        }

                        @Override
                        public void handleEvent(String s, Object o) {
                        }
                    };
                    AbortRetryIgnoreHandler handler = new AbortRetryIgnoreHandler() {
                        @Override
                        public int handle(Throwable throwable) {
                            return AbortRetryIgnoreHandler.IGNORE_ALL;
                        }

                        @Override
                        public AbortRetryIgnoreHandler getNewInstance() {
                            return this;
                        }
                    };
                    try {
                        files.set(exporter.exportImages(handler,
                                "tmp",
                                r,
                                settings,
                                listener
                                ));
                    } catch (IOException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                return;
            }
        });
        if (files.get().size() > 0) {
            try {
                res.set(new FileInputStream(files.get().get(0)).readAllBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return res.get();
    }

    public void setDefineBitsLosslessTag(String id, byte[] data) {
        swf.getTags().forEach(tag -> {
            if (!Objects.equals(tag.getTagName(), "DefineBitsLossless")
            && !Objects.equals(tag.getTagName(), "DefineBitsLossless2")) return;

            if (tag.getName().equals(id)) {
                ImageImporter importer = new ImageImporter();

                if (tag instanceof DefineBitsLosslessTag) {
                    DefineBitsLosslessTag defineBitsLosslessTag = (DefineBitsLosslessTag) tag;
                    try {
                        importer.importImage(defineBitsLosslessTag, data);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (tag instanceof DefineBitsLossless2Tag) {
                    DefineBitsLossless2Tag defineBitsLossless2Tag = (DefineBitsLossless2Tag) tag;
                    try {
                        importer.importImage(defineBitsLossless2Tag, data);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                tag.setModified(true);
                return;
            }
        });
    }
}
