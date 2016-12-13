package com.appchina.ios.mgnt.controller.model;

/**
 * Created by zhouyanhui on 7/21/15.
 */
public class UploadFileResp {
    private String url;
    private String md5;
    private boolean saved;
    private String message;
    private String location;
    private String resizeUrl;

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getResizeUrl() {
		return resizeUrl;
	}

	public void setResizeUrl(String resizeUrl) {
		this.resizeUrl = resizeUrl;
	}

	@Override
    public String toString() {
        return "UploadFileResp [url=" + url + ", md5=" + md5 + ", saved=" + saved + ", message=" + message
                + ", location=" + location + "]";
    }

}
