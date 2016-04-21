package com.brz.programme;

import java.util.List;

/**
 * Created by macro on 16/4/12.
 */
public class ProgrammeContext {
    private String templateType;
    private Programme programme;
    private Layout layout;
    private List<ContentItem> content;

    public String getTemplateType() {
        return templateType;
    }

    public Programme getProgramme() {
        return programme;
    }

    public Layout getLayout() {
        return layout;
    }

    public List<ContentItem> getContent() {
        return content;
    }

    public static class ContentItem {
        private Region region;
        private Seq seq;

        public Region getRegion() {
            return region;
        }

        public Seq getSeq() {
            return seq;
        }
    }

    public static class Seq {
        private String playtime;
        private List<Item> item;

        public String getPlaytime() {
            return playtime;
        }

        public List<Item> getItem() {
            return item;
        }
    }

    public static class Item {
        private String type;
        private String src;
        private String size;
        private String fileSigna;
        private String url;
        private String color;
        private String mode;
        private String startdate;
        private String enddate;
        private String cleardate;

        public String getType() {
            return type;
        }

        public String getSrc() {
            return src;
        }

        public String getSize() {
            return size;
        }

        public String getFileSigna() {
            return fileSigna;
        }

        public String getUrl() {
            return url;
        }

        public String getColor() {
            return color;
        }

        public String getMode() {
            return mode;
        }

        public String getStartdate() {
            return startdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public String getCleardate() {
            return cleardate;
        }
    }

    public static class Region {
        private String id;
        private String type;
        private String name;
        private String model;
        private String zindex;
        private String left;
        private String top;
        private String width;
        private String height;

        public String getId() {
            return id;
        }

        public String getType() {
            return type;
        }

        public String getName() {
            return name;
        }

        public String getModel() {
            return model;
        }

        public String getZindex() {
            return zindex;
        }

        public String getLeft() {
            return left;
        }

        public String getTop() {
            return top;
        }

        public String getWidth() {
            return width;
        }

        public String getHeight() {
            return height;
        }
    }

    public static class Layout {
        private String width;
        private String height;
        private String rotate;

        public String getWidth() {
            return width;
        }

        public String getHeight() {
            return height;
        }

        public String getRotate() {
            return rotate;
        }
    }

    public static class ProgrammeInfo {
        private String id;
        private String name;
        private String playtime;
        private String type;
        private String version;

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getPlaytime() {
            return playtime;
        }

        public String getType() {
            return type;
        }

        public String getVersion() {
            return version;
        }
    }

    public static class Coordinate {
        private float left;
        private float right;
        private float top;
        private float buttom;

        public Coordinate(float left, float right, float top, float buttom) {
            this.left = left;
            this.right = right;
            this.top = top;
            this.buttom = buttom;
        }

        public float getLeft() {
            return left;
        }

        public void setLeft(float left) {
            this.left = left;
        }

        public float getRight() {
            return right;
        }

        public void setRight(float right) {
            this.right = right;
        }

        public float getTop() {
            return top;
        }

        public void setTop(float top) {
            this.top = top;
        }

        public float getButtom() {
            return buttom;
        }

        public void setButtom(float buttom) {
            this.buttom = buttom;
        }
    }
}
