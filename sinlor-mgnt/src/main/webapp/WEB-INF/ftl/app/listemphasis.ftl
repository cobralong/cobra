<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>重点监控应用列表</li>
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
                                	<span class="add-on">ROOT_ID</span>
                                    <input class="span2" name="rootId" type="text" placeholder="ROOT_ID" value="${para.rootId?c}">
                                </div>
							    <div class="input-prepend">
                                	<span class="add-on">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                        	</form>
                        </div>
                    </div>
                    <br/>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/app/addemphasis" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">ROOT_ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="rootId" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span4" name="name" type="text" placeholder="应用名称" disabled value="">
                                    <span class="add-on">售价</span>
                                    <input id="input_app_price" class="span"  name="price" type="text" placeholder="应用售价" disabled value="">                                    
                                </div>
                                <button class="btn btn-info add" disabled >
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${errMsg}">
                                	<#if errMsg??>
                                		<font>${errMsg}</font>
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
                                    <th>名称(编辑名称)</th>
                                    <th>分类</th>
                                    <th>价格</th>
                                    <th>最新更新时间</th>
                                    <th>重点应用</th>
                                    <th>操作</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.rootId?c}">
                                        <td>
                                            <a class="mark-visited" href="http://ios.appchina.com/app/${ value.rootId! }" target="_blank" title="${value.name}">
                                               <#if value.name?length gt 8>
                                               	${value.name?substring(0,8)}...
                                               <#else>
                                               	${value.name}
                                               </#if>
                                               <#if value.name?length gt 0>
                                               	(${value.name})
                                               </#if>                                               
                                            </a>
                                        </td>
                                        <td>${value.categoryName! }</td>
                                        <td>
                                            <#if value.price == 0>
                                            	免费
                                            <#else>
                                                ${value.priceSymbol} ${value.price}
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.releaseDate ??>
                                            	${ value.releaseDate?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                        	<#if para.status == 0>
                                    			<span class="label label-success">√</span>
                                    		<#else>
                                    			<span class="label label-danger">X</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if para.status == 0>
                                        		<button appName="${value.name!''}" rootId="${value.rootId?c}" status = "-1" class="btn btn-danger delete">取消重点</button>
                                			<#else>
                                				<button appName="${value.name!''}" rootId="${value.rootId?c}" status = "0" class="btn btn-success delete">设置重点</button>
                                			</#if>
                                        </td>
                                        <td>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/app/detail?rootId=${value.rootId?c}">详情</a>
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
        
 		$("button.queryToggle").bind("click", function(){
 			$("#div_query").toggle();
 		});
 		
 		$("button.addToggle").bind("click", function(){
 			$("#div_add").toggle();
 		});
 		
 		$("input.search").bind("blur", function(){
 			var ctl = $(this);
 			var rid = $(ctl).val();
 			var txtCtl = $("#input_app_name");
 			var priceCtl = $("#input_app_price");
 			var urlCtl = $("#input_app_url");
 			var btn = $("button.add");
 			var posting = $.post("${rc.contextPath}/auth/promote/search", {"id" : rid});
 			posting.done(function(result){
 				if(result.success){
 					$(txtCtl).val(result.data.name);
 					$(priceCtl).val(result.data.price);
 					$(btn).removeAttr("disabled");
 				}else{
 					$(txtCtl).val(result.message);
 					$(priceCtl).val();
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
 			var rootId = $(this).attr("rootId");
 			var postUrl = "${rc.contextPath}/auth/app/modifyemphasisstatus.json";
 			var appName = $(this).attr("appName");
 			var removeTr = $("#tr_" + rootId);
			var status = $(eventCtl).attr("status");
			var action = (status == 0) ? "添加到重点应用列表中":"从重点应用列表中移除";
	 		bootbox.confirm("是否将应用"+ appName + action + "?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'rootId': rootId, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					$(removeTr).remove();
	 					alert("操作成功，已将应用"+ appName + action + "!");
	 				}
	 			});
			});
		}); 
    </script>
</body>
</html>