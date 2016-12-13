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
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>专栏内容列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                <div class="well form-inline">  
                             <div id="addInfoDiv">
				                <#if errMsg??>
				                <font color="red">${errMsg}</font>
				                </#if>
				            </div>                     
				 </div>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
                                <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}"/>
                                <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}"/>
                                <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}"/>
                                <div class="input-prepend">
                                    <span class="add-on">内容标题</span>
                                    <input class="span2" name="title" type="text" placeholder="内容标题" value="${para.title}">
                                </div>
                                <!--<div class="input-prepend">
                                    <span class="">状态</span>
                                	<input class="span2" name="status" type="text" placeholder="状态" value="${para.status}">
                                </div>-->
                                <div class="input-prepend">
                                    <span class="add-on">分类</span>
                                    <@spring.formSingleSelect "para.ctypeId", ctypesall, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">作者</span>
                                    <@spring.formSingleSelect "para.authorId", authorsall, " " />
                                </div>
                                <div class="input-prepend">
	                                <span class="add-on">展示日期</span>
	                                <div id="showDate" class="input-append date datetimepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
								      <input class="span2"  name="showDate" size="16" type="text" value="${para.showDate}">
								      <span class="add-on"><i class="icon-calendar"></i></span>
								    <div>
							    </div>
                                <button class="btn btn-info search">
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
                                    <th>ID</th>
                                    <th>标题</th>
                                    <th>分类</th>
                                    <th>作者</th>
                                    <th>文章地址</th>
                                    <th>不带应用地址</th>
                                    <th>展示日期</th>
                                    <th>浏览量</th>
                                    <th>更新时间</th>
                                    <th>状态</th>
                                    <th>列表编辑</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <td>
                                        ${value.id}
                                        </td>
                                        <td>
                                        ${value.title}
                                        </td>
                                        <td>
                                            ${ctypesall[value.ctypeId?c]}
                                        </td>
                                        <td>
                                            ${authorsall[value.authorId?c]}
                                        </td>
                                        <td><@hrefipaplist value.viewUrl!''/></td>
                                        <td><@hrefipaplist value.viewNoApplicationUrl!''/></td>
                                        <td>
                                            ${value.showDate?string("yyyy-MM-dd HH:mm:ss")}
                                        </td>
                                        <td>
                                            ${value.views}
                                        </td>
                                        <td>
                                        ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                        </td>
                                        <td>
                                            <#if columncontentids[value.id?c] != trues>
                                                <font id="font_status_${value.id}" color="green">√</font>
                                            <#else>
                                            	<font id="font_status_${value.id}" color="red">X</font>
                                            </#if>
                                        </td>
                                        <td>
                                        <#if value.columnContentId lte 0 && value.applicationSetsId lte 0>
                                            <a href="${rc.contextPath}/funny/client/add?columnDetailId=${value.id!}"class="btn btn-warning delete glyphicon glyphicon-trash ">
                                                列表编辑
                                            </a>
                                         <#elseif value.columnContentId gte 0 && value.applicationSetsId lte 0>
                                         	<a href="${rc.contextPath}/funny/client/android/add?columnDetailId=${value.columnContentId!}"class="btn btn-warning delete glyphicon glyphicon-trash ">
                                                列表编辑
                                            </a>
                                          <#else>
                                         	<a href="${rc.contextPath}/funny/client/appset/add?columnDetailId=${value.applicationSetsId!}"class="btn btn-warning delete glyphicon glyphicon-trash ">
                                                列表编辑
                                            </a>
                                         </#if>
                                        </td>
                                        <td>
                                            <a href="${rc.contextPath}/auth/store/funny/column/content/detail?id=${value.id!}&type=0" type="button"
                                                    class="btn btn-success delete glyphicon glyphicon-trash ">
                                                详情
                                            </a>
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
    $(document).ready(function () {
        var currPageDiv = "#pager_page";
        var totalDiv = "#pager_total";
        var sizeDiv = "#pager_size";
        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
    });

    $("#showDate").datetimepicker({
	            'date-format': 'yyyy-mm-dd',
	            stepYear: 1,
	            stepMonth: 1,
	            stepDay: 1,
	            startView: 2,
	            minView: 2,
	            maxView: 2           
	        });

</script>
</body>
</html>