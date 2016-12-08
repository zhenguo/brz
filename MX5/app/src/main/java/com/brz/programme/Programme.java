package com.brz.programme;

/**
 * Created by macro on 16/4/12.
 */
public class Programme {

    private String name;
    private String id;
    private String size;
    private String status;
    private String fileSigna;

    private String url;
    private String signa;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getSize() {
        return size;
    }

    public String getStatus() {
        return status;
    }

    public String getFileSigna() {
        return fileSigna;
    }

    @Override
    public String toString() {
        return "id: " + id
                + " name: " + name
                + " size: " + size
                + " fileSigna: " + fileSigna
                + " status: " + status;
    }

    public String getUrl() {
        return url;
    }

    public String getSigna() {
        return signa;
    }
}
