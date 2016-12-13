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
            <li>快速上架</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/app/online" method="post">
                                <div class="input-prepend">
                                	<span class="add-on">ItemId</span>
                                    <input class="span2" name="itemId" type="text" placeholder="苹果应用Id" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">Url</span>
                                    <input class="span" name="url" type="text" placeholder="苹果应用Url" value="">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="btn-info btn-white">快速上架</i>
                                </button>
                            </form>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="add-on">ItemId</span>
                                    <input class="span2" name="preItemId" type="text" placeholder="苹果应用Id" value="">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>是否上架
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>itemId</th>
                                    <th>区域</th>
                                    <th>appStore</th>
                                    <th>rootId</th>
                                    <th>价格</th>
                                    <th>状态</th>
                                    <th>更新时间</th>
                                    <th>插入时间</th>
                                    <th>授权下载状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                	<#list values as value>
                                    <tr>
                                    	<#assign simple = simples[value.rootId?c]>
                                        <td>
                                    		<@machrefapp value.itemId, value.name/>	
                                        </td>                                        
                                        <td>  
                                        	${value.locale}
                                        </td>
                                        <td>${ value.url! }</td>
                                        <td>
                                        	<#if value.rootId??>
                                        		<@hrefapp value.rootId, value.rootId/>
                                        	<#else>
                                        		---无rootId
                                        	</#if>                                        	
                                        </td>
                                        <td>
                                        	<#if simples[value.rootId?c]==undefined>
                                        	
                                            <#elseif simple.price == 0>
                                            	免费
                                            <#else>
                                                ${simple.priceSymbol} ${simple.price}
                                            </#if>
                                        </td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span class="label label-success">√</span>
                                    		<#else>
                                    			<span class="label label-danger" disabled>X</span>
                                    		</#if>
                                    	</td>
                                        <td>
                                            <#if value.updateTime ??>
                                            	${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.createTime ??>
                                            	${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                            </#if>
                                        </td>
                                        <td>${rootIdBoughtMap[value.rootId?c]}</td>
                                        <td>
                                        	<#if value.rootId??>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/app/detail?rootId=${value.rootId?c}">详情</a>
                                        	</#if>
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
</body>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	 </script>
</html>