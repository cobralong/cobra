(function() {
	CKEDITOR.dialog.add("insertApplicationLimitTags",
					function(a) {
						return {
							title : "限免应用标签",
							minWidth : "500px",
							minHeight : "500px",
							contents : [ {
								id : "tab1",
								label : "",
								title : "",
								expand : true,
								// width : "500px",
								// height : "500px",
								padding : 0,
								elements : [
										{
											type : "html",
											style : "width:300px;height:30px",
											html : '<input type="text" name="applicationLimitsTAgs" id="applicationLimitsTAgs" placeHolder="请输入限免应用标签">'
										} ]
							} ],
							onOk : function() {
								// 点击确定按钮后的操作
								var tags="<p>限免标签:<input type='text' name='applicationLimitTags' value='"+$("#applicationLimitsTAgs").val()+"'></p><br/>";
								a.insertHtml(tags);
								$("#applicationLimitsTAgs").val("");
							}
						}
					})
})();

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