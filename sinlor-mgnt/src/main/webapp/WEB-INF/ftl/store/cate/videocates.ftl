<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
	<#include "/lib/store_header.ftl">
	
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>分类列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	 <div class="well form-inline">
                        <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/cate/addvideocate" method="post">
                            <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
	                    	<input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
	                    	<input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                            <div class="input-prepend">
                                <span class="add-on">分类名称</span>
                                <input id="input_app_name" class="span2" name="name" type="text" placeholder="分类名称" value="">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">分类短描述</span>
                                <input id="input_app_desc" class="span2" name="desc" type="text" placeholder="分类名称" value="">
                            </div>					    
                            <div class="input-prepend">
                                <span class="add-on">位置</span>
                                <input class="span1" size="10" name="position" type="text" placeholder="可不填" value="">                                   
                            </div>
                           	<span class="add-on">分类Icon:</span>
                            <div class="fileinput fileinput-new" data-provides="fileinput">
                                <div class="fileinput-new thumbnail" style="width: 72px; height: 72px;">
                                    <img data-src="holder.js/100%x100%" alt="...">
                                </div>
                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 72px; max-height: 72px	;"></div>
                                <div>
                                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="icon"></span>
                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                </div>
                            </div>
                            <button class="btn btn-info add" >
                                <i class="icon-search icon-white"></i>增加
                            </button>
                            <div id="addInfoDiv" errMsg="${errMsg}">
                            	<#if errMsg??>
                            		<font>${errMsg}</font>
                            	</#if>
                            </div>
                        </form>
                    </div>
                    <div class="table-content">
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>Id</th>
                                <th>图标</th>
                                <th>名称</th>
                                <th>短描述</th>
                                <th>应用数</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                            	<#list values as value>
                            		<tr>
	                                	<td>
	                                     	${value.id}
	                                     </td>
	                                    <td>
		                                    <#if value.icon!=''>
		                                    	<img id="icon-img" class="apk-icon" src="${ value.icon }" />
		                               	 	</#if>
	                                    </td>
	                                    <td>${ value.name! }</td>
	                                    <td>${ value.shortDesc! }</td>
	                                    <td>${ value.apps! }</td>
	                                    <td> <a class="btn btn-info query" href="${rc.contextPath}/auth/store/cate/videocatedetail?id=${value.id}">详情</a>
                                	</tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                        <div class="pagination" id="itemPage"></div>
                    </div><!-- end div class table-content-->
                </div><!-- end div class main-content-->
            </div><!-- end div class container span12-->
        </div><!-- end div class row-fluid-->
    </div><!-- end div class container-fluid-->
</div><!-- end div class whole-container-->
</body>
<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
<script>
    $(document).ready(function () {
        var currPageDiv = "#pager_page";
        var totalDiv = "#pager_total";
        var sizeDiv = "#pager_size";
        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
    });
</script>
</html>