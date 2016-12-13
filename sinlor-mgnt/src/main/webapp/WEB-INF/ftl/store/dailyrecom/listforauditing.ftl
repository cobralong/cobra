<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/store_header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>每日精选【<#if user==1>用户<#else>审核人员</#if>】所见列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                    <span class="add-on">推荐客户端</span>
                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                                <button id="btn_now_promote" class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>正在推荐
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
		                <#if para.status == 0>	
		                	<p>在<b><font color="green">${para.st}</font></b>时间在进行展示的应用列表:</p>
		                </#if>
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>推荐标题</th>
                                    <th>资源类型</th>
                                    <th>资源标签</th>
                                    <th>客户端</th>
                                    <th>展示位置</th>
                                    <th>图片</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}" >                  
                                    	<input id="hidden_${value.id}" type="hidden" title="${value.title}" />
                                        <td class="edit_td" default_id="${value.id?c}" property="title" td_name="${value.title!}" for_class="span4">
                                       		<label for="name" class="span4">
                                       			<span id="span_title_${value.id?c}" class="text-info" pre_value="${value.title}">${ value.title! }</span>
                                       		</label>
                                       	</td>
                                        <td>
                                        	<#if value.type == 0>
                                        		<a href="${rc.contextPath}/auth/store/video/videoinfodetail?id=${value.recomId}">
                                        	<#elseif value.type == 1>
                                        		<a href="${rc.contextPath}/auth/store/app/detail?id=${value.recomId}">
                                        	<#elseif vlaue.type == 4>
                                        		<a href="${rc.contextPath}/auth/store/app/tagapps?id=${value.recomId}">
                                        	</#if>
                                        	${types[value.type?c]}</a>
                                        </td>
                                        <td>
                                        	${value.mainTag}
                                        </td>
                                        <td>
                                        	${value.bundleId}
                                        </td>
                                        <td class="edit_td" default_id="${value.id?c}" property="<#if user==0>auditingPosition<#else>auditedPosition</#if>" td_name="${value.title!}" 
                                        	for_class="span1">
                                        	<label for="name" class="span1">
                                        	<span id="<#if user==0>span_auditingPosition_${value.id?c}<#else>span_auditedPosition_${value.id?c}</#if>" 
                                        		class="text-info" 
                                        		pre_value="<#if user==0>${value.auditingPosition?c}<#else>${value.auditedPosition?c}</#if>">
                                        		<#if user==0>${value.auditingPosition?c}<#else>${value.auditedPosition?c}</#if>
                                        	</span>
                                        	</label>
                                       	</td>
                                        <td>
                                        	<a href="${value.img}" target="_blank"><img style="width:150px; height:70px" src="${value.img}" alt="${value.img}"/></a>
                                        </td>
                                        <td>
                                            <#if value.startTime ??>
                                            ${ value.startTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td class="edit_time_td" pid="${value.id?c}">
                                        	<span id="p_time_${value.id?c}" preValue="${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }">${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }</span>
                                        </td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="btn btn-success" disabled>正常</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="btn btn-danger" disabled>删除</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<button id="promote_btn_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " pre_status= "${value.status}" default_id="${value.id}" <#if value.status == 0>disabled</#if>>
                                        		推荐                                        	
                                			</button>
                                        	<button id="unpromote_btn_${value.id}" class="btn btn-danger delete" default_id="${value.id}"  pre_status= "${value.status}" <#if value.status != 0>disabled</#if>>
                                        		停止
                                			</button>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </form>
                    	<div class="pagination" id="itemPage"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
	</div>
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    
 		$("#btn_now_promote").bind("click",function(){
 			$("#search_startDateString").val("");
 		});
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("default_id");
 			var trCtl = $("#tr_"+id);
 			var hidden = $("#hidden_"+id);
 			var title = $(hidden).attr("title");
 			var preStatus = $(this).attr("pre_status");
 			var postUrl = "${rc.contextPath}/auth/store/dailyrecom/modify";
 			var actionMsg = preStatus == 0 ? 
 										"是否将精选【【"+title+"】】从每日精选中移除?" :
 										"是否将精选【【"+title+"】】添加到从每日精选列表中?"; 			
 			var setStatus = preStatus == 0 ? -1 : 0;
 			
	 		bootbox.confirm(actionMsg, function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": setStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
		 				var responseMsg = preStatus == 0 ? 
								"成功将精选【【"+title+"】】从每日精选列表中移除!" :
								"成功将精选【【"+title+"】】添加到每日精选列表列表!";
	 					alert(responseMsg);	 					
	 					trCtl.remove();
	 				}
	 			});
			});
		});
 		$('.edit_td').click(function() {
 			var id = $(this).attr("default_id");
 			var property = $(this).attr("property");
 			var spanCtr = $("#span_" + property + "_" + id);
 			var preValue = $(spanCtr).attr("pre_value");
 			var tdName = $(this).attr("td_name");
 			var input = $('<input id="attribute" type="text" class="span4" preValue="' + preValue + '" value="' + preValue + '" />')
 			$(spanCtr).text('').append(input);
 			input.select();
 			input.blur(function() {			
 				var curValue = $('#attribute').val();
 				if(curValue == ""){
 					$(this).val(preValue);
 					$(this).focus();
 					return ;
 				}
 				if(curValue == preValue){
		   			var text = $('#attribute').val();
		   			$('#attribute').parent().text(preValue);
		   			$('#attribute').remove();
	   				return ;
	   			}
	   			var typeName = (property == "title") ? "标题" : (property == "auditingPosition") ? "审核人员所见位置" : "用户所见位置";
 				bootbox.confirm("将每日推荐【" + tdName  +"】的" + typeName + "由【" + preValue + "】改为【" + curValue + "】?", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/auth/store/dailyrecom/modify";
 						var postData = "id=" + id + "&" + property + "=" + curValue;
			 			var posting = $.post(postUrl, postData);
			 			posting.done(function(errMsg){
			 				if(errMsg){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
					   			$('#attribute').parent().text(preValue);
					   			$('#attribute').remove();
			 				}else{
			 					alert("修改成功.");
			 					$('#attribute').parent().attr("pre_value", curValue);
					   			$('#attribute').parent().text(curValue);
					   			$('#attribute').remove();
			 				}
			 			});
		 			}else{
			   			$('#attribute').parent().text(preValue);
			   			$('#attribute').remove();
		 			}
				});
 			});
		});
		
        $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
            timeFormat: 'yyyy-mm-dd',
            stepYear: 1,
            stepMonth: 1,
            stepDay: 1
        });

 
    </script>
</body>
</html>