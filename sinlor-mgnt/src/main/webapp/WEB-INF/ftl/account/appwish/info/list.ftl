<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<title>应用汇-管理后台-已求应用列表</title>
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
            <li>已求应用列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
		                        <div class="input-prepend">
                                	<span class="add-on">RootId</span>
                                	<input name="rootId" value="" type="input"/>
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">应用购买状态</span>
                                	<select name="hadBuy">
                                		<#if para.hadBuy == 0>
                                			<option value="0" selected>等待购买</option>
                                		<#else>
                                			<option value="0">等待购买</option>
                                		</#if>
                                		<#if para.hadBuy == 1>
                                			<option value="1" selected>已购买</option>
                                		<#else>
                                			<option value="1">已购买</option>
                                		</#if>
                                	</select>
                                </div>
							   	<span class="add-on">开始时间</span>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
							      <input class="span2" id="startDateString" name="st" size="16" type="text" value="${para.st?substring(0,10)}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <span class="add-on">结束时间</span>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
							      <input class="span2" id="endDateString" name="et" size="16" type="text" value="${para.et?substring(0,10)}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <div class="input-prepend">
							    	<span class="add-on">排序字段</span>
							    	<@spring.formSingleSelect "para.column", columns, " " />
							    </div>
                                <button class="btn btn-info query" onClick="modifyPage()">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>                    
                    <div class="table-content">
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>应用Id</th>
                                    <th>苹果链接</th>
                                    <th>已求/价格</th>
                                    <th>购买状态</th>
                                    <th>发生时间</th>
                                    <th>求应用列表</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td>
                                        <td>
                                        	<@machrefapp rootSimple.appStoreId, rootSimple.name/>
                                        </td>
                                        <td>${ value.wished?c }/${rootSimple.price}</td>
	                                    <td>
	                                    	<#if value.hadBuy == 1>
	                                    		<font color="green">√</font>
	                                		<#else>
	                                			<font color="red">X</font>
	                                		</#if>
	                                    </td>
	                                    <td>
                                            <#if value.updateTime ??>
                                            ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
	                                    </td>
                                        <td>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/account/appwish/history/list?rootId=${value.rootId}">查看明细</a>
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
	    
        $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
            'date-format': 'yyyy-mm-dd',
            stepYear: 1,
            stepMonth: 1,
            stepDay: 1,
            startView: 2,
            minView: 2,
            maxView: 2           
        });

	    function modifyPage(){
	    	$("#pager_page").val(1);
	    }
	</script>
</body>
</html>