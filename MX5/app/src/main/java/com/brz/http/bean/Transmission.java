package com.brz.http.bean;

import java.util.List;

/**
 * Created by macro on 16/7/18.
 */
public class Transmission {

    private String totalSize;
    private String completedSize;
    private String downloadSpeed;
    private List<Item> files;

    @Override
    public String toString() {
        return "totalSize: " + totalSize + " completeSize: " + completedSize + " files: " + files.size();
    }

    public String getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(String totalSize) {
        this.totalSize = totalSize;
    }

    public String getCompletedSize() {
        return completedSize;
    }

    public void setCompletedSize(String completedSize) {
        this.completedSize = completedSize;
    }

    public String getDownloadSpeed() {
        return downloadSpeed;
    }

    public void setDownloadSpeed(String downloadSpeed) {
        this.downloadSpeed = downloadSpeed;
    }

    public List<Item> getFiles() {
        return files;
    }

    public void setFiles(List<Item> files) {
        this.files = files;
    }

    public static class Item {
        public static final String STATE_WAITING = "0";
        public static final String STATE_DOWNLOADING = "1";
        public static final String STATE_COMPLETE = "2";
        public static final String STATE_FAILURE = "3";

        private String fileName;
        private String fileSigna;
        private String statue;
        private String fileSize;
        private String fileCompleted;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getFileSigna() {
            return fileSigna;
        }

        public void setFileSigna(String fileSigna) {
            this.fileSigna = fileSigna;
        }

        public String getStatue() {
            return statue;
        }

        public void setStatue(String statue) {
            this.statue = statue;
        }

        public String getFileSize() {
            return fileSize;
        }

        public void setFileSize(String fileSize) {
            this.fileSize = fileSize;
        }

        public String getFileCompleted() {
            return fileCompleted;
        }

        public void setFileCompleted(String fileCompleted) {
            this.fileCompleted = fileCompleted;
        }
    }
}
