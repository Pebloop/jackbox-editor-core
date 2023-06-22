package jackbox.editor.core.editors.jackbox2.core.models;

public class ContentJsonModel {
    static public class Game {
        public String name = "";
        public String mainSwf = "";
        public String title = "";
        public String players = "";
        public boolean family = false;
        public boolean audience = false;
        public String description = "";
        public String video = "";
    }

    public Game[] games;
}
