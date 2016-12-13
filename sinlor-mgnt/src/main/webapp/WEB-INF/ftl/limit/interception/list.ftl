<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>限免榜干预列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<button class="btn queryToggle  btn-info">
                		<span class="glyphicon glyphicon-search"></span>查询框
                	</button>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
							    <div class="input-prepend">
                                	<span class="add-on">排序类别</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/limit/intervention/add" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="name" type="text" placeholder="应用名称" disabled value="">
                                </div>
                                <br/>
							    <div class="input-prepend">
                                	<span class="add-on">排序类别</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">位置</span>
                                    <input class="span1" size="10" name="rank" type="text" placeholder="rank" value="">                                   
                                </div>						    
                                <button class="btn btn-info add" disabled >
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${para.errMsg}">
                                	<#if para.errMsg??>
                                		<font>${para.errMsg}</font>
                                	</#if>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>应用</th>
                                    <th>图标</th>
                                    <th>类型</th>
                                    <th>位置</th>
                                    <th>状态</th>
                                    <th>下载地址</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<input id="hidden_${value.id}" type="hidden" root_id = "${value.rootId!}" app_title="${rootSimple.name}" type_name= "${types[value.type?c]}" promote_status="${value.status}"/>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                        <td>${types[value.type?c]}</td>
                                        <td>${value.rank}</td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="btn btn-success">干预中</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="btn btn-danger" disabled>不干预</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<@hrefipaplist rootSimple.downloadUrl!''/>
                                        </td>
                                        <td>
                                        	<button id="promote_btn_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " app_id = "${value.id!}"  <#if value.status == 0>disabled</#if>>
                                        		干预                                        	
                                			</button>
                                        	<button id="unpromote_btn_${value.id}" class="btn btn-danger delete" app_id = "${value.id!}"  <#if value.status != 0>disabled</#if>>
                                        		不干预
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

                    <div class="modal hide" id="batch-modal">
                        <div class="modal-header">
                            <button type="button" class="close close-modal" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h3 id="batch-operate-title"></h3>
                        </div>
                        <div class="modal-body">
                            <textarea class="input-block-level" id="reason"></textarea>
                        </div>
                        <div class="modal-footer">
                            <a href="#" class="btn close-modal">关闭</a>
                            <a class="btn btn-primary batch-submit" id="">确定</a>
                        </div>
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
 
 		$("input.search").bind("blur", function(){
 			var ctl = $(this);
 			var rid = $(ctl).val();
 			var txtCtl = $("#input_app_name");
 			var btn = $("button.add");
 			var posting = $.post("${rc.contextPath}/auth/promote/search", {"id" : rid});
 			posting.done(function(result){
 				if(result.success){
 					$(txtCtl).val(result.data.name);
 					$(btn).removeAttr("disabled");
 				}else{
 					$(txtCtl).val(result.message);
					$(btn).attr("disabled", "disabled");
 				} 				
 			});
 		});
 		
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("app_id");
 			var trCtl = $("#tr_"+id);
 			var hidden = $("#hidden_"+id);
 			var appTitle = $(hidden).attr("app_title");
 			var type_name = $(hidden).attr("type_name");
 			var postUrl = "${rc.contextPath}/auth/limit/intervention/modify";
 			var preStatus = $(hidden).attr("promote_status");
 			var action = preStatus == 0 ? "删除":"添加";
 			var setStatus = preStatus == 0 ? -1 : 0;
 			
	 		bootbox.confirm("是否在【"+type_name+"】限免榜上"+action+"排行干预"+appTitle+"?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": setStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
		 				var statusSpan = $("#status_span_"+id);
		 				var positiveEventCtl =  preStatus == 0 ? $("#promote_btn_"+id) : $("#unpromote_btn_"+id);
		 				var statusInfo= preStatus == 0 ? "不干预" : "干预";
		 				var removeClass= preStatus == 0 ? "btn-success" : "btn-danger";
		 				var addClass= preStatus == 0 ? "btn-danger" : "btn-success";
		 				$(hidden).attr("promote_status", setStatus);
	 					$(positiveEventCtl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					$(statusSpan).removeClass(removeClass).addClass(addClass);
	 					$(statusSpan).text(statusInfo);
	 					alert("操作成功，已"+action+"干预");					
	 					trCtl.remove();
	 				}
	 			});
			});
		}); 
    </script>
</body>
</html>