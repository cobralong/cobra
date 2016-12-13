<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-管理后台</title>
		<#include "lib/base_source.ftl">		
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/switchery.min.css">
	</head>
	<body>
		<#include "/lib/header.ftl">
		<div>
	        <ul class="breadcrumb">
	            <li>管理后台</li><span class="divider">/</span>
	            <li>客户端缓存刷新时间管理</li>
	        </ul>
    	</div>
        <#include "lib/alert.ftl">
        <div class="container">
			<div class = "well form-horizontal">
					<div class="control-group">
						<label class="control-label">Banner缓存刷新时间(秒):</label>
						<div class="controls">
							<input type="text" dataId = "${data.id?c}" dataType="1" dataDesc="Banner缓存刷新时间" preValue = "${data.bannerEt?c}" value="${data.bannerEt?c}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">列表缓存刷新时间(秒):</label>
						<div class="controls">
							<input type="text" dataId = "${data.id?c}"  dataType="2" dataDesc="列表缓存刷新时间" preValue = "${data.listEt?c}" value="${data.listEt?c}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">详情缓存刷新时间(秒):</label>
						<div class="controls">
							<input type="text" dataId = "${data.id?c}" dataType="3" dataDesc="详情缓存刷新时间" preValue = "${data.detailEt?c}" value="${data.detailEt?c}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">搜索热词缓存刷新时间(秒):</label>
						<div class="controls">
							<input type="text" dataId = "${data.id?c}" dataType="4" dataDesc="搜索热词缓存刷新时间" preValue = "${data.searchHotwordEt?c}" value="${data.searchHotwordEt?c}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">搜索备注词缓存刷新时间(秒):</label>
						<div class="controls">
							<input type="text" dataId = "${data.id?c}" dataType="5" dataDesc="搜索备注词缓存刷新时间" preValue = "${data.placeholderEt?c}" value="${data.placeholderEt?c}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">图像缓存刷新时间(秒):</label>
						<div class="controls">
							<input type="text" dataId = "${data.id?c}" dataType="6" dataDesc="图像缓存刷新时间" preValue = "${data.imgEt?c}" value="${data.imgEt?c}"/>
						</div>
					</div>					
					<div class="control-group">
						<label class="control-label">启用苹果帐号下载功能:</label>
						<div class="controls">
							<#if data.accountDownload == 0>
								<input dataId = "${data.id?c}" dataType="7" value = "1" dataDesc="苹果帐号下载功能" type="checkbox" cancle="0" class="js-switch" checked />
								<span class="js-switch-check">正在使用</span>
							<#else>
								<input dataId = "${data.id?c}" dataType="7" value = "0" dataDesc="苹果帐号下载功能" type="checkbox" cancle="0" class="js-switch" />
								<span class="js-switch-check">已被禁用</span>
							</#if>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">启用下载调优汇报功能:</label>
						<div class="controls">
							<#if data.downloadTunning == 1>
								<input dataId = "${data.id?c}" dataType="8" value = "0" dataDesc="客户端下载调优数据汇报功能" type="checkbox" cancle="0" class="js-switch" checked />
								<span class="js-switch-check">正在启用</span>
							<#else>
								<input dataId = "${data.id?c}" dataType="8" value = "1" dataDesc="客户端下载调优数据汇报功能" type="checkbox" cancle="0" class="js-switch" />
								<span class="js-switch-check">已被禁用</span>
							</#if>
						</div>
					</div>
			</div>
		</div>
		<script type="text/javascript" src='${rc.contextPath}/js/switchery.min.js'></script>
		<script type="text/javascript">			
			$(document).ready(function(){
				$('.js-switch').each(function(index, elem){
					new Switchery(elem,{color:"green", secondaryColor: "red", size: "small"});
				});
				var jsswitchs = $('.js-switch');
				$(jsswitchs).each(function(index, elem){
					elem.onchange = function() {
					 	var ctl = $(this);
					 	var cancle = $(ctl).attr("cancle");
				 		$(ctl).attr("cancle","0");
					 	if(cancle == 1){
					 		return;
					 	}
	                	var id = $(ctl).attr("dataId");
	                	var type = $(ctl).attr("dataType");
	                	var desc = $(ctl).attr("dataDesc");
						var value =$(ctl).attr("value");
						var actionMsg="";
						if(this.checked){
							$('.js-switch-check').text("正在使用");
							actionMsg = "是否启用" + desc + "？";
						}else{
	  						$('.js-switch-check').text("已被禁用");
	  						actionMsg = "是否禁用" + desc + "？";
	  					}
	                	bootbox.confirm(actionMsg, function(result) {
				 			if(!result){
				 				$(ctl).attr("cancle","1");
				 				$(ctl).click();
				 				return;
				 			}
				 			var postUrl = "${rc.contextPath}/auth/client/modifyconf";
				 			var posting = $.post(postUrl, {'id': id, "type": type, "value" : value});
				 			posting.done(function(errMsg){
				 				if(errMsg){//
				 					//show msg	 					
				 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
				 				}else{
				 					alert("操作成功");
				 				}
				 			});
						});//END BOOTBOX FUNCTION
					};
				});
			});

            $("input").bind("blur", function () {
                var ctl = $(this);
                var id = $(ctl).attr("dataId");
                var type = $(ctl).attr("dataType");
                var desc = $(ctl).attr("dataDesc");
                var preValue = $(ctl).attr("preValue");
                
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将" + desc + "改为"+curValue+"秒?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/client/modifyconf";
		 			var posting = $.post(postUrl, {'id': id, "type": type, "value" : curValue});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
		 				}else{
		 					alert("操作成功");	 					
		 					$(ctl).attr("preValue", curValue);
		 				}
		 			});
				});
            });
		</script>
	</body>
</html>