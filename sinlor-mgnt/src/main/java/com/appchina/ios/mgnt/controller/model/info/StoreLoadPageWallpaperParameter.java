package com.appchina.ios.mgnt.controller.model.info;


public class StoreLoadPageWallpaperParameter {
		private int id;
	    private String title;
	    /**
	     * 原始图
	     */
	    private String url;
	    /**
	     * 压缩文件大小的图的地址
	     */
	    private String resizeUrl;
		public int getId() {
			return id;
		}
		public void setId(int id) {
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
	    
}
