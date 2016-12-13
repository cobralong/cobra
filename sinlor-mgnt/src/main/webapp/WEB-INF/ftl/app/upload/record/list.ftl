<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-应用信息上架列表</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>上架列表</li>
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
                                	<span class="add-on">上架方式</span>
                                	<select name="type">
                                		<#list types as type>
                                			<#if type.type == para.type>
                                				<option selected value="${type.type}">${type.desc}</option>
                                			<#else>
                                				<option value="${type.type}">${type.desc}</option>
                                			</#if>
                                		</#list>
                                	</select>
                                </div><div class="input-prepend">
                                	<span class="add-on">上架状态</span>
                                	<select name="onlineStatus">
                                		<#list onlinestatuses as onlinestatus>
                                			<#if onlinestatus.status == para.onlineStatus>
                                				<option selected value="${onlinestatus.status}">${onlinestatus.desc}</option>
                                			<#else>
                                				<option value="${onlinestatus.status}">${onlinestatus.desc}</option>
                                			</#if>
                                		</#list>
                                	</select>
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
                                    <th>上架信息Id</th>
                                    <th>ItemId</th>
                                    <th>包名</th>
                                    <th>版本</th>
                                    <th>区域</th>
                                    <th>rootId</th>
                                    <th>上架状态</th>
                                    <th>说明</th>
                                    <th>内容查看</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td>${value.onlineRecordId}</td>
                                        <td>${ value.itemId!"无" }</td>
                                        <td>${ value.bundleId!"无" }</td>
                                        <td>${ value.version!"无" }</td>
                                        <td>${ value.locale!"无" }</td>
                                        <td>${ value.rootId!"无" }</td>
                                        <td>${ value.status! }</td>
                                        <td>${ value.message! }</td>
                                        <td>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/app/upload/crawler/application/detail?id=${value.onlineRecordId}&type=${para.type}">详情</a>
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