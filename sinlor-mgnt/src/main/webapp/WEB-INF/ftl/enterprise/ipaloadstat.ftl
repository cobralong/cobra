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
            <li>ipa入库统计</li>
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
	                                <div class="input-prepend">
	                                	<span class="">应用市场</span>
	                                	 <@spring.formSingleSelect "para.marketId", marketNames, " " />
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
                                <button class="btn btn-info search" onClick="modifyPage()">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>日期</th>
                                    <th>市场Id</th>
                                    <th>应用总数</th>
                                    <th>成功/失败</th>
                                    <th>文件错误</th>
                                    <th>文件丢失</th>
                                    <th>签名无效</th>
                                    <th>第三方SDK</th>
                                    <th>非签名包</th>
                                    <th>未知异常</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">    
										<td>
                                       		<#if value.date??>
                                       			${value.date?string("yyyy-MM-dd") }
                                       		<#else>
                                       			当前页面总计
                                       		</#if>
                                   		</td>
                                    	<td>
                                    		<#if value.marketId == -2>
                                    			总计
	                                    	<#elseif value.marketId == -1>
	                                    		未知市场
	                                    	<#else>
	                                    		${marketNames[value.marketId?c]}
	                                    	</#if>
                                        </td>
                                       	<td>${value.total}</td>
                                       	<td>${value.success}/${value.failed}</td>
                                       	<td>${value.openFailed}</td>
                                       	<td>${value.notFound}</td>
                                       	<td>${value.revoked}</td>
                                       	<td>${value.excludSdk}</td>
                                       	<td>${value.noEnterprise}</td>
                                       	<td>${value.unknown}</td>
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