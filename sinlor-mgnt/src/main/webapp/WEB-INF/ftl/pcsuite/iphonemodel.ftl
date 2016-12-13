<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-PC助手iPhone模型图</title>
		<#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
	</head>
	<body>
		<div class='whole-container'>
		<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>PC助手iPhone模型图</li>
		        </ul>
		    </div>
		    <div class="container-fluid">
		        <div class="row-fluid">
		            <div class="container span12">
		            <#include "lib/alert.ftl">
		                <div class='main-content'>
		                    <div class="well form-inline">
		                    	<#if data??>
			                        <div class='main-action'>		                        
		                                <div class="input-prepend">
		                                	<span class="add-on">设备：</span>
		                                	<#if platform??>
		                                		<input class="span2" name="platform" readonly="readonly" type="text"value="${platform.content}">
		                                	<#else>
		                                		<input class="span2" name="platform" readonly="readonly" type="text"value="${data.platform}">
		                                	</#if>
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">颜色：</span>
		                                	<input class="span2" name="color" readonly="readonly" type="text"value="${data.color}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">图片高：</span>
		                                	<input class="span2" name="height" readonly="readonly" type="text" value="${data.height}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">图片宽：</span>
		                                	<input class="span2" name="width" readonly="readonly" type="text"value="${data.width}">
		                                </div>
		                                <br/><br/>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕左上角离左边距离：</span>
		                                	<input class="span2 padding" default_id = "${data.id}" cur_value="${data.paddingLeft}" name="paddingLeft" type="text"value="${data.paddingLeft}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕左上角离顶部距离：</span>
		                                	<input class="span2 padding" default_id = "${data.id}" cur_value="${data.paddingTop}" name="paddingTop" type="text" placeholder="padding top" value="${data.paddingTop}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕左下角离右边距离：</span>
		                                	<input class="span2 padding" default_id = "${data.id}" cur_value="${data.paddingRight}" name="paddingRight" type="text" placeholder="padding right" value="${data.paddingRight}">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕右下角离底部距离：</span>
		                                	<input class="span2 padding" default_id = "${data.id}" cur_value="${data.paddingBottom}" name="paddingBottom" type="text" placeholder="padding bottom" value="${data.paddingBottom}">
		                                </div>
		                                <br/><br/>
		                                <div class="input-prepend">
		                                	<span class="add-on">模型图：</span>
		                                	<div class="controls input-prepend">
												<img width="${data.width}" height="${data.height}" src="${data.imgUrl}">
											</div>
		                                </div>
		                                <br/><br/>
	                                	<span class="add-on">更换模型图：</span>
										<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/pcsuite/iphone/model/modify/icon" method="post">
											<input type="hidden" name="id" value="${data.id?c}"/>
											<div class="fileinput fileinput-new" data-provides="fileinput">
				                                <div class="fileinput-new thumbnail" style="width: 72px; height: 72px;">
				                                    <img data-src="holder.js/100%x100%" alt="...">
				                                </div>
				                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 72px; max-height: 72px	;"></div>
				                                <div>
				                                    <span class="btn btn-default btn-file">
				                                    	<span class="fileinput-new">Select image</span>
				                                    	<span class="fileinput-exists">Change</span>
				                                    	<input type="file" name="screen">
				                                    </span>
				                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
				                                </div>
				                        	</div>
				                        	<br/>
				                            <button class="btn btn-info add" >
				                                <i class="icon-search icon-white"></i>修改图标
				                            </button>		
										</form>
			                        </div>
		                        </#if>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
		</div>
		<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
		<script type="text/javascript">
			$('input.padding').bind("blur", function(e){
				var defaultId = $(this).attr("default_id");
				var preValue = $(this).attr("cur_value");
				var curValue = $(this).val();
				if(preValue == curValue){
					return;
				}
				var eventCtl=$(this);
				var properties=$(this).attr("name");
				bootbox.confirm("是否对" + properties+ "进行更改,由【"+preValue+"】改为【"+curValue+"】?", function(result){
					if(!result){
						$(eventCtl).val(preValue);
					}
					var postUrl = "${rc.contextPath}/auth/pcsuite/iphone/model/modify";
					var postData = "id=" + defaultId + "&" + properties + "=" + curValue;
		 			var posting = $.post(postUrl, postData);
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
							$(eventCtl).val(preValue);
		 				}else{
		 					alert("操作成功");	 					
		 					$(ctl).attr("cur_value", curValue);
		 				}
		 			});
				});
			});
		</script>
	</body>
</html>