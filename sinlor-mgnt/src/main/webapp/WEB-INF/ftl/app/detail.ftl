<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-管理后台</title>
		<#include "lib/base_source.ftl">
	</head>
	<body>
		<#include "/lib/header.ftl">
		<div>
	        <ul class="breadcrumb">
	            <li>管理后台</li><span class="divider">/</span>
	            <li>软件查看</li>
	        </ul>
    	</div>
        <#include "lib/alert.ftl">
        <div class="container">
			<div class = "well form-horizontal">
					<div class="control-group">
						<label class="control-label">软件名称:</label>
						<div class="controls">
							${data.title}
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">编辑命名:</label>
						<div class="controls">
							<input class="editortitle" type="text" rootId = "${data.rootId?c}" preValue = "${rootApplication.editorTitle}" value="${rootApplication.editorTitle!}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">icon:</label>
						<div class="controls">
							<#if (icons?size > 0)>
								<#list icons as iconList>
									<#list iconList as icon>
										<img width="${icon.width}" height="${icon.height}" src="${icon.url}" alt="${data.title}的图标" title="${data.title}的图标">
									</#list>
								</#list>
							<#else>
								暂无图标
							</#if>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">软件类别:</label>
						<div class="controls">
							<#if parentCate??>
								父类型：${parentCate.name}<br/>
							</#if>
                            <#if (cates?size > 0)>
                                类型：
                                <#list cates as cate>
                                    <span>${cate.name}</span>
                                </#list>
                            <#else>
                                暂无图标
                            </#if>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">本地下载:</label>
						<div class="controls">
							<#if appRes.downloadUrl??>
	 							<a class="btn btn-primary download" href="${appRes.downloadUrl}">
	 								<i class="icon-download icon-white"></i>下载应用
	 							</a>
 							<#else>
 								暂无应用文件
							</#if>
						</div>
					</div>
					
					<div class="control-group">
						<label class="control-label">小编点评:</label>
						<div class="controls">
							<input class="editorReview" type="text" rootId = "${data.rootId?c}" preValue = "${rootApplication.editorReview}" value="${rootApplication.editorReview!}"/>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">应用版本:</label>
						<div class="controls">${data.version}
							<a href="${rootApplication.appStoreUrl!}" target="_blank">在iTunes中查看</a>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">详细描述:</label>
						<div class="controls">
							${data.content}
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">更新信息:</label>
						<div class="controls">
							${data.releaseView}
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">系统需求:</label>
						<div class="controls">
							${data.requirements}
						</div>
					</div>
					<div class="preview_snapshot">
						<img id="normal_img" src="">
					</div>
					<div class="control-group">
						<label class="control-label">iPhone截图:</label>
						<div class="controls">
							<#if (iphones?size > 0)>							
								<#list iphones as iphoneList>
									${iphoneList}
									<#list iphoneList as iphone>
										<img class="snapshot" src="${iphone.url}" alt="${data.title}的截图" title="${data.title}的截图">
									</#list>
								</#list>
							<#else>
								暂无图标
							</#if>
						</div>
					</div>
					<div class="preview_snapshot">
						<img id="normal_ipad_img" src="">
					</div>
					<div class="control-group">
						<label class="control-label">iPad截图:</label>
						<div class="controls">
							<#if (ipads?size > 0)>							
								<#list ipads as ipadList>
									<#list ipadList as ipad>
										<img class="snapshot" src="${ipad.url}" alt="${data.title}的截图" title="${data.title}的截图">
									</#list>
								</#list>
							<#else>
								暂无图标
							</#if>
						</div>
					</div>
			</div>
		</div>
		<script type="text/javascript">
			 /**
			 ------------显示大图开始----------------
			 **/
			 
			 $(".snapshot").die("click");
			 $(".snapshot").live("click",function(){
			 		var link = $(this).attr("src");
			 		console.log("link:", link);
			 		$("#normal_img").attr("src", link);
			 		$(".preview_snapshot").show();
			 });
			 $(".snapshot").die("mouseout");
			 $(".snapshot").live("mouseout",function(){
			 		$(".preview_snapshot").hide()
			 });
			 /**
			 ------------显示大图结束----------------
			 **/
			 
			 
            $("input.editortitle").bind("blur", function () {
                var ctl = $(this);
                var rootId= $(ctl).attr("rootId");
                var preValue = $(ctl).attr("preValue");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将应用的编辑名称(应用展示名称)改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/app/modifyeditortitle";
		 			var posting = $.post(postUrl, {'rootId': rootId, "editorTitle": curValue});
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
            
			 
            $("input.editorReview").bind("blur", function () {
                var ctl = $(this);
                var rootId= $(ctl).attr("rootId");
                var preValue = $(ctl).attr("preValue");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将应用的小编点评改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/app/modifyeditorreview";
		 			var posting = $.post(postUrl, {'rootId': rootId, "editorReview": curValue});
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