package com.appchina.ios.mgnt.controller.model.info;


public class AppStoreWallpaperParameter extends TimedStatusStartSizeParameter {

    private Integer id;
    private String title;
    private String url;
    private String resizeUrl;
    private Integer chosen;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getResizeUrl() {
        return resizeUrl;
    }
    public void setResizeUrl(String resizeUrl) {
        this.resizeUrl = resizeUrl;
    }
    public Integer getChosen() {
        return chosen;
    }
    public void setChosen(Integer chosen) {
        this.chosen = chosen;
    }

}
