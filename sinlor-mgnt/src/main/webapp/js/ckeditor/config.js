/**
 * @license Copyright (c) 2003-2016, CKSource - Frederico Knabben. All rights
 *          reserved. For licensing, see LICENSE.md or
 *          http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.height = 500; // 高度
	config.font_names = '正文/正文;宋体/宋体;黑体/黑体;仿宋/仿宋_GB2312;楷体/楷体_GB2312;隶书/隶书;幼圆/幼圆;微软雅黑/微软雅黑;'
			+ config.font_names;
	config.filebrowserUploadUrl = getRootPath()+"/auth/store/funny/column/content/addimage.ftl";
	config.extraPlugins = 'insertApplication,insertApplicationLimit,insertApplicationSets';
	
};

function getRootPath() {
	// 获取当前网址，如： http://localhost:8083/uimcardprj/share/meun.jsp
	//http://10.18.0.54:7070/ios-mgnt/auth/store/funny/column/content/add
	//http://iosmgnt.appchina.com/auth/store/funny/column/content/add
	var curWwwPath = window.document.location.href;
	// 获取主机地址之后的目录，如： uimcardprj/share/meun.jsp
	var pathName = window.document.location.pathname;
	var pos = curWwwPath.indexOf(pathName);
	// 获取主机地址，如： http://localhost:8083
	var localhostPaht = curWwwPath.substring(0, pos);
	// 获取带"/"的项目名，如：/uimcardprj
	var projectName = pathName
			.substring(0, pathName.substr(1).indexOf('/') + 1);
	if(localhostPaht.indexOf("localhost")==-1 && localhostPaht.indexOf("10.18.0.54")==-1){
		return localhostPaht;
	}else{
		return (localhostPaht + projectName);
	}
}
