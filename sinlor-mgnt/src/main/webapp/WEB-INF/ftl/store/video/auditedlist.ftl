<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li>
            <span class="divider">/</span>
            <li>视频管理列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
	                <button class="btn addToggle glyphicon-pushpin btn-primary">查询框</button>
	                    <div id="div_add" class="well form-inline">
	                        <div class='main-action'>
	                            <form id="searchForm" name="searchForm" action="" method="post">
	                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
			                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
			                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
			                        <div class="input-prepend">
	                                    <span class="add-on">视频名称</span>
	                             		<input type="input" name="title" value="${para.title}"/>
                               		</div>
			                        <div class="input-prepend">
	                                    <span class="add-on">推荐客户端</span>
	                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                               		</div>
	                            	<div class="input-prepend">
	                                    <span class="add-on">分类</span>
	                                     <@spring.formSingleSelect "para.mainCategory", cates, " " />
	                            	</div>
	                            	<div class="input-prepend">
	                                    <span class="add-on">状态</span>
	                                     <@spring.formSingleSelect "para.status", status, " " />
	                            	</div>
	                                <button class="btn btn-info search" >
	                                    <i class="icon-search icon-white"></i>查询
	                                </button>
	                            </form>
	                        </div>
	                    </div>
	                </button>
                    <div class="table-content">
                    <#-- table -->
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
	                            <tr>
	                            	<th>Id</th>  
	                                <th>标题</th>                                
	                                <th>主分类</th>
	                                <th>时长</th>
	                                <th>文件大小</th>
	                                <th>截图</th>
	                                <th>操作</th>
	                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                                <#list values as value>
                                <tr>
                                	<td>${value.videoInfoId}</td>
                                    <td>
                                    	${value.title}
                                    </td>
                                    <td>
                                    	${value.mainCategory}
                                    </td>
                                    <td>
                                    	${value.videoDuration}
                                    </td>
                                    <td>
                                    	${value.videoSize}
                                    </td>
                                    <td>
                                    	<img style="width:150px; height:70px" src="${value.videoImg}"/>
                                    </td>
                                    <td>
                                    	 <a class="btn btn-info query" href="${rc.contextPath}/auth/store/video/videoinfo?id=${value.videoInfoId}">详情</a>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
                        <div class="pagination" id="itemPage"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
<script>
    $(document).ready(function () {
        var currPageDiv = "#pager_page";
        var totalDiv = "#pager_total";
        var sizeDiv = "#pager_size";
        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
    });
</script>
</body>
</html>