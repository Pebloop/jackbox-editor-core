package jackbox.editor.core.utils;

import com.jpexs.decompiler.flash.SWF;
import com.jpexs.decompiler.flash.tags.DefineEditTextTag;

import java.io.*;
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
            if (tag.getTagName() != "DefineEditText") return;

            if (tag.getName().equals(id)) {
                res.set(((DefineEditTextTag) tag).initialText);
                return;
            }
        });
        return res.get();
    }
}
