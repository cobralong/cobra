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
            <li>软件查询</li>
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
                            	<input type="hidden" id="search_type_db" name="type" value="1">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="add-on">应用名称</span>
                                    <input class="span2" name="title" type="text" placeholder="名称" value="${para.title}">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>搜索引擎查询
                                </button>
                            </form>
                            <form id="searchForm1" name="searchForm" action="" method="post">
                            	<input type="hidden" id="search_type_db" name="type" value="2">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="add-on">应用名称</span>
                                    <input class="span2" name="title" type="text" placeholder="名称" value="${para.title}">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>数据库查询【特慢】
                                </button>
                            </form>
                            <form id="searchForm2" name="searchForm" action="" method="post">
                            	<input type="hidden" id="search_type_db" name="type" value="3">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="add-on">应用rootId</span>
                                    <input class="span2" name="rootIds" type="text" placeholder="rootIds" value="${para.rootIds}">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>数据库查询【特快】
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
                                    <th>名称</th>
                                    <th>rootId</th>
                                    <th>短描述</th>
                                    <th>分类</th>
                                    <th>价格</th>
                                    <th>版本号</th>
                                    <th>企业版地址</th>
                                    <th>更新时间</th>
                                    <th>授权下载状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td>  
                                        	<@hrefapp value.rootId, value.name/>
                                        </td>                                        
                                        <td>  
                                        	${value.rootId}
                                        </td>
                                        <td>${ value.shortDesc! }</td>
                                        <td>
                                        	${ value.categoryName! }
                                        </td>
                                        <td>
                                            <#if value.price == 0>
                                            	免费
                                            <#else>
                                               ${value.price}
                                            </#if>
                                        </td>
                                        <td>
                                        	${value.version}
                                      	</td>
                                        <td>
                                        	${value.downloadUrl}
                                      	</td>
                                        <td>
                                            <#if value.releaseDate ??>
                                            	${ value.releaseDate?string("yyyy-MM-dd HH:mm:ss") }
                                            </#if>
                                        </td>
                                        <td>${rootIdBoughtMap[value.rootId?c]}</td>
                                        <td>
                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/app/detail?rootId=${value.rootId}">详情</a>
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
    <script type="text/javascript">
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
    </script>
</body>
</html>