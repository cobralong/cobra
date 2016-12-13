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
            <li>应用下载干预列表</li>
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
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="">RootId</span>
                                	<input class="span2 search" name="rootId" type="text" placeholder="rootId" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info search" onClick="modifyPage()">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>应用名</th>
                                    <th>应用包名</th>
                                    <th>版本号(当前库版本)</th>
                                    <th>价格</th>
                                    <th>支持设备</th>
                                    <th>plist地址</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                    	<#if value.itemId??>
                                    		<#assign simple = itemIdRootSimpleMap[value.itemId?c]>
                                    	</#if>
                                    	<#assign pnsimple = bundleIdRootSimpleMap[value.bundleId!'']>
                                    	<td>
	                                    	<#if value.itemId??>
	                                    		<@machrefapp value.itemId, value.appName/>	                                    	
	                                    	</#if>                                        	
                                        </td> 
                                		<#if value.rootId??>
                                			 <td>
	                                        	<@hrefapp value.rootId, value.appName/>
	                                        </td>
                                		<#else>
                                			<td></td>
                                		</#if>
                                        <td>${ value.bundleId! }</td>
                                        <td>${ value.shortVersion! }
	                                        <#if simple?? && simple!='' &&  simple.version??>
	                                        	<#if simple.version == value.shortVersion>
		                                       		(<font color='green'>${simple.version}</font>)
		                                       	<#else>
		                                       		(<font color='red'>${simple.version}</font>)
		                                       	</#if>
	                                    	</#if>
                                        </td>
                                        <td>
                                        	<#if value.price != 0>
                                        		<font color="red">${ value.price! }</font>
                                        	<#else>
                                        		<font color="green">${ value.price! }</font>
                                        	</#if>
                                        </td>
                                        <td>${ value.supportDevice! }</td>
                                        <td>${ value.enterprisePlistUrl! }</td>
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
	    function modifyPage(){
	    	$("#pager_page").val(1);
	    }
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("app_id");
 			var hidden = $("#hidden_"+id);
 			var appTitle = $(hidden).attr("app_title");
 			var appChannel = $(hidden).attr("app_channel");
 			var postUrl = "${rc.contextPath}/auth/promote/modify";
 			var preStatus = $(hidden).attr("promote_status");
 			var action = preStatus == 0 ? "暂停":"开始";
 			var setStatus = preStatus == 0 ? -1 : 0;
 			
	 		bootbox.confirm("是否在"+appChannel+"渠道"+action+"推荐应用"+appTitle+"?", function(result) {
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
		 				var statusInfo= preStatus == 0 ? "删除" : "正常";
		 				var removeClass= preStatus == 0 ? "btn-success" : "btn-danger";
		 				var addClass= preStatus == 0 ? "btn-danger" : "btn-success";
		 				$(hidden).attr("promote_status", setStatus);
	 					$(positiveEventCtl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					$(statusSpan).removeClass(removeClass).addClass(addClass);
	 					$(statusSpan).text(statusInfo);
	 					alert("操作成功，已"+action+"推荐");
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