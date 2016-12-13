<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">

    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>审核视频</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
        <#include "lib/alert.ftl">
            <div class='main-content'>
                <div class="well form-inline">
                    <div class='main-action'>
                        <div class="input-prepend">
                            <span class="add-on">视频标题</span>
                            <input id="title" name="title" type="text" class="span4 title" style="margin-left:30px;" placeholder="视频标题" pre_value="${data.title}" default_id = "${data.videoInfoId}" value="${data.title}">
                        </div>
                        <br/>
                        <br/>
                        
                        <div class="input-prepend">
                            <span class="add-on" style=margin-right:20px;">推荐客户端</span>
	                       	<#list bundleIds?keys as key>
	                       		<#if recomBundleIds?? && recomBundleIds?seq_contains(key)>
	                       			<input name="bundleIdList" type="checkbox" class="bundleId" default_id = "${data.videoInfoId}" desc = "${bundleIds[key]}" checked value="${key}"><span  style="margin-right:20px;">${bundleIds[key]}</span>
	                       		<#else>
	                       			<input name="bundleIdList" type="checkbox" class="bundleId" default_id = "${data.videoInfoId}" desc = "${bundleIds[key]}" value="${key}"><span  style="margin-right:20px;">${bundleIds[key]}</span>
	                       		</#if>		                        	  
	        				</#list>
                        </div>      
                        <br/>
                        <br/>
                        <div class="input-prepend">
                            <span class="add-on">视频主分类</span>
                        	<select id="sel_maintag" style="margin-left:20px;" cur_value="${data.mainCategory}" default_id = "${data.videoInfoId}">
                        		<#list cates as cate>
                        			<#if cate.id == data.mainCategory>
                        				<option value="${cate.id}" selected>${cate.name}</option>
                        			<#else>
                        				<option value="${cate.id}">${cate.name}</option>
                        			</#if>                            			
                        		</#list>
                        	</select>
                        </div>
                        <br/>
                        <br/>
                        <div class="input-prepend">
                        	<span class="add-on">所属分类:</span>
                        	<input id="hidden_categorys" type="hidden" name="categorys" value="">								
							<#if cates??>
								<table class="table table-striped table-bordered table-condensed" style="margin-left:100px; margin-top:-30px;" id="itemTable">
		                            <thead>
			                            <tr>
			                            	<th></th>
			                                <th>名称</th>
			                            	<th></th>
			                                <th>名称</th>
			                            	<th></th>
			                                <th>名称</th>
			                            </tr>
		                            </thead>
		                            <tbody class="sortable">
		                            	<#list cates as cate>
		                            		<#assign index = cate_index>
		                            		<#if index == 0 || (index % 3  == 0)>
		                            			<tr>
		                            		</#if>
		                            			<td style="width:5%">
		                            				<#if recomCateIds?seq_contains(cate.id)>
		                            					<input class="cate_check" checked type="checkbox" desc="${cate.name!}" default_id = "${data.videoInfoId}" name="categoryIds" value="${cate.id}">
		                            				<#else>
		                            					<input class="cate_check" type="checkbox" desc="${cate.name!}" default_id = "${data.videoInfoId}" name="categoryIds" value="${cate.id}">
		                            				</#if>
		                            			</td>
		                            			<#if (index+1)%3 !=0 && (index+1) == cates?size>
			                                    	<td style="border-right: 1px solid #dddddd;">${ cate.name! }</td>
			                                    <#else>
			                                    	<td>${ cate.name! }</td>				                                    
			                                    </#if>
			                              	<#if (index+1) == cates?size || (index+1) % 3  == 0>
		                                		</tr>
		                                	</#if>
		                                </#list>
		                            </tbody>
	                        	</table>
	                    	</#if>		                    
                        </div>
                        <br/>
                        <div class="input-prepend">
                            <span class="add-on">播放Video</span>
                            <#if metas??>
                            	<#list metas as video>
                            		<#if video_index == 0>
                            			<input class="span4" disabled value="${video.originName}" style="margin-left:28px; "><a href="${video.videoUrl}" target="_blank">观看视频</a><br/>
                            		<#else>
                            			<input class="span4" disabled value="${video.originName}" style="margin-left:102px; margin-top:5px;"><a href="${video.videoUrl}" target="_blank">观看视频</a><br/>
                            		</#if>
                            	</#list>
                            </#if>
                        </div><br/>
                        <div class="input-prepend">
                            <span class="add-on">相关应用</span>
                            <input name="relateRootIds" class="span6 rootids" style="margin-left:35px; margin-top:5px;" placeholder="1,2,3..." pre_value="${data.relateRootIds}" default_id = "${data.videoInfoId}" value="${data.relateRootIds}">
                        </div><br/>
                        <div class="input-prepend">
                            <span class="add-on">内容描述</span>
                            <textarea name="content" cols="120" rows="14" class="span6 desc" style="margin-left:35px; margin-top:5px;" default_id = "${data.videoInfoId}" pre_value="${data.desc}">${data.desc!}</textarea>                       	 
                        </div>
                        <br/>                            
                        <div class="input-prepend">
                            <span class="add-on">当前截图</span>
                        	<img style="max-width: 320px; max-height: 180px; margin-left:50px; " src="${data.videoImg}"/>
                        </div>
                        <br/>
						<form id="addform" name="addform" method="post" action="${rc.contextPath}/auth/store/video/modifybanner" enctype="multipart/form-data">                            
							<input type="hidden" name="id" value="${para.videoInfoId}"/>
							<div class="fileinput fileinput-new" data-provides="fileinput">                            	
							    <div class="fileinput-new thumbnail" style="width: 320px; height: 180px; margin-left:100px; ">
							        <img data-src="holder.js/100%x100%" alt="..." src="${para.iconUrl}" />
							    </div>
							    <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 320px; max-height: 180px; margin-left:140px; "></div>
							    <div>
							    	<span class="add-on">更新截图:</span>
							        <span class="btn btn-default btn-file" style="margin-left:40px;" ><span class="fileinput-new" >Select image</span><span class="fileinput-exists">Change</span>
							            <input type="file" name="screen"></span>
							        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
							    </div>
							</div>
							<br/>
							<div class="input-prepend">
							    <input id="submitBtn" type="submit" class="btn btn-info add" value="修改截图"/>
								<#if errMsg??>
									<font id="font_errmsg" color="red">${errMsg}</font>
								</#if>
							</div>
						</form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
    <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
    <script type="text/javascript">
    	$(document).ready(function () {
        	$("#sel_maintag").attr("cur_value", $("sel_maintag").val());   	         	
            $(".cate_check").each(function(e){
            	var curSelValue = $("select").val();
            	var curCheckValue = $(this).val();
            	if(curCheckValue == curSelValue){
            		$(this).prop("checked", true);
            		$(this).prop("disabled", "true");
            	}
            });
        });

		$("input.bundleId[type='checkbox']").bind("click",function(){
			var eventCtl = $(this);
			var value = $(eventCtl).val();
			var defaultId = $(eventCtl).attr("default_id");
			var checked = $(this).is(":checked");
			var confirmMsg="";
			var status = 0;
			var showClient = $(eventCtl).attr("desc");
			if(checked){
				confirmMsg="请注意，你正在将此视频放在" + showClient + "客户端上进行展示，是否继续？";
				status = 0;
			}else{
				confirmMsg="请注意，你正在取消此视频在" + showClient + "客户端的展示，是否继续？";
				status = -1;
			}
			bootbox.confirm(confirmMsg, function(result){
				if(!result){
					$(eventCtl).prop("checked", !checked);
 					return;
 				}
 				var postUrl = "${rc.contextPath}/auth/store/video/recom/client/modify.json";
	 			var posting = $.post(postUrl, {'videoInfoId': defaultId, "bundleId": value, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(eventCtl).prop("checked", !checked);
	 				}else{
	 					alert("修改成功.");	 					
	 					if(value=="all"){
							if(checked){					
								$("input.bundleId[type='checkbox']").each(function(){
									$(this).prop("checked", true);
								});	
							}else{
								$("input.bundleId[type='checkbox']").each(function(){
									$(this).prop("checked", false);
								});
							}
						}else{
							if(!$(this).is(":checked")){
								$("input.bundleId[type='checkbox']").each(function(){
									if($(this).val() == "all"){
										$(this).prop("checked", false);	
									}
								});
							}
						}
	 				}
 				}).fail(function(errMsg){
 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg.statusText);	 				
					$(eventCtl).prop("checked", !checked);
	 			});
			});
		});
		
		
		$("input.cate_check[type='checkbox']").bind("click",function(){
			var eventCtl = $(this);
			var value = $(eventCtl).val();
			var defaultId = $(eventCtl).attr("default_id");
			var checked = $(this).is(":checked");
			var confirmMsg="";
			var status = 0;
			var showClient = $(eventCtl).attr("desc");
			if(checked){
				confirmMsg="请注意，你正在将此视频放在" + showClient + "分类上进行展示，是否继续？";
				status = 0;
			}else{
				confirmMsg="请注意，你正在取消此视频在" + showClient + "分类的展示，是否继续？";
				status = -1;
			}
			bootbox.confirm(confirmMsg, function(result){
				if(!result){
					$(eventCtl).prop("checked", !checked);
 					return;
 				}
 				var postUrl = "${rc.contextPath}/auth/store/video/recom/cate/modify.json";
	 			var posting = $.post(postUrl, {'videoInfoId': defaultId, "cateId": value, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(eventCtl).prop("checked", !checked);
	 				}else{
	 					alert("修改成功.");
	 				}
 				}).fail(function(errMsg){
 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg.statusText);	 				
					$(eventCtl).prop("checked", !checked);
	 			});
			});
		});
		
		
		$("#sel_maintag").bind("change", function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var selectCtl = $(this);
 			var curValue = $(this).attr("cur_value");
 			var defaultId = $(this).attr("default_id");
 			var setValue = $(this).val(); 			
 			bootbox.confirm("请注意，你正在修改此视频的分类，是否继续?", function(result){
 				if(!result){
 					$(selectCtl).val(curValue);
 					return;
 				}
 				var postUrl = "${rc.contextPath}/auth/store/video/modifyvideoinfo.json";
	 			var posting = $.post(postUrl, {'id': defaultId, "mainTag": setValue});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
 						$(selectCtl).val(curValue);
	 				}else{
	 					alert("修改成功.");
	 					$(selectCtl).attr("cur_value", setValue);
	 					$(".cate_check").each(function(e){
	 						var curCheckValue = $(this).val();
	 						if(curCheckValue == setValue){
					    		$(this).prop("checked", true);
					    		$(this).prop("disabled", "true");
					    	}else if(curCheckValue == curValue){
					    		$(this).prop("checked", false);
					    		$(this).prop("disabled", "false");
					    	}
	 					});
	 				}
	 			}).fail(function(errMsg){
	 				alert("操作失败,请联系该死的开发人员.Msg:"+errMsg.statusText);	 				
 					$(selectCtl).val(curValue);
	 			});
 			});
		});
		
		$("input.title").bind("blur", function(e){
			var blurCtl = $(this);
			var curValue = $(this).val();
			var defaultId = $(this).attr("default_id");
			var preValue = $(this).attr("pre_value");
			if(preValue == curValue){
				return;
			}
			bootbox.confirm("将视频标题由【"+preValue+"】改为【"+curValue+"】?", function(result) {
	 			if(!result){
	 				$(blurCtl).val(preValue);
	 				return;
	 			}
				var postUrl = "${rc.contextPath}/auth/store/video/modifyvideoinfo.json";
	 			var posting = $.post(postUrl, {'id': defaultId, "title": curValue});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(blurCtl).val(preValue);
	 				}else{
	 					alert("修改成功.");
	 					$(blurCtl).attr("pre_value", curValue);
	 				}
	 			}).fail(function(errMsg){
	 				alert("操作失败,请联系该死的开发人员.Msg:"+errMsg.statusText);
	 			});
			});
		});
		
		$("input.rootids").bind("blur", function(e){
			var blurCtl = $(this);
			var curValue = $(this).val();
			var defaultId = $(this).attr("default_id");
			var preValue = $(this).attr("pre_value");
			if(preValue == curValue){
				return;
			}
			bootbox.confirm("将视频相关应用由【"+preValue+"】改为【"+curValue+"】?", function(result) {
	 			if(!result){
	 				$(blurCtl).val(preValue);
	 				return;
	 			}
				var postUrl = "${rc.contextPath}/auth/store/video/modifyvideoinfo.json";
	 			var posting = $.post(postUrl, {'id': defaultId, "relateRootIds": curValue});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(blurCtl).val(preValue);
	 				}else{
	 					alert("修改成功.");
	 					$(blurCtl).attr("pre_value", curValue);
	 				}
	 			}).fail(function(errMsg){
	 				alert("操作失败,请联系该死的开发人员.Msg:"+errMsg.statusText);
	 			});
			});
		});

		
		$("textarea.desc").bind("blur", function(e){
			var blurCtl = $(this);
			var curValue = $(this).val();
			var defaultId = $(this).attr("default_id");
			var preValue = $(this).attr("pre_value");
			if(preValue == curValue){
				return;
			}
			bootbox.confirm("将视频内容描述由【"+preValue+"】改为【"+curValue+"】?", function(result) {
	 			if(!result){
	 				$(blurCtl).val(preValue);
	 				return;
	 			}
				var postUrl = "${rc.contextPath}/auth/store/video/modifyvideoinfo.json";
	 			var posting = $.post(postUrl, {'id': defaultId, "desc": curValue});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(blurCtl).val(preValue);
	 				}else{
	 					alert("修改成功.");
	 					$(blurCtl).attr("pre_value", curValue);
	 				}
	 			}).fail(function(errMsg){
	 				alert("操作失败,请联系该死的开发人员.Msg:"+errMsg.statusText);
	 			});
			});
		});
    </script>
</body>
</html>