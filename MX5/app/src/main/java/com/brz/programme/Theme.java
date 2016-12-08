package com.brz.programme;

import java.util.List;

/**
 * Created by macro on 16/4/12.
 */
public class Theme {
  private String id;
  private String name;
  private String type;
  private String fileSigna;
  private List<Programme> defaults;
  private String url;
  private String size;
  private String publishid;
  private String singa;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public String getFileSigna() {
    return fileSigna;
  }

  public List<Programme> getDefaults() {
    return defaults;
  }

  @Override public String toString() {
    return "id: " + id + " name: " + name + " type: " + type + " fileSigna: " + fileSigna;
  }

  public String getUrl() {
    return url;
  }

  public String getSize() {
    return size;
  }

  public String getSinga() {
    return singa;
  }

  public String getPublishid() {
    return publishid;
  }
}
