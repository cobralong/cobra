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
            <li>AppTag榜单列表</li>
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
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                              	<div class="input-prepend">
                                	<span class="add-on">分类</span>
                                     <@spring.formSingleSelect "para.cid", categorys, " " />
                                </div>
							    <div class="input-prepend">
                                	<span class="add-on">排序类别</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                                <div errMsg="${para.errMsg!''}">
			                        <#if para.errMsg??>
			                            <font color='red'>${para.errMsg!''}</font>
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
                                    <th>应用</th>
                                    <th>图标</th>
                                    <th>更新时间</th>
                                    <th>热度</th>
                                    <th>下载数</th>
                                    <th>下载地址</th>
                                    <th>授权应用</th>
                                    <th>编辑</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<#assign statusRespMap = statusRespMaps[value.rootId?c]>
                                    	<input id="hidden_${value.id}" type="hidden" root_id = "${value.rootId!}" app_title="${rootSimple.name}" type_name= "${types[para.type?c]}" promote_status="${value.status}"/>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td> 
                                        <td>
                                            <#if value.releaseDate ??>
                                            ${ value.releaseDate?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>${value.hot}</td>
                                        <td>${value.downloads}</td>
                                        <td>
                                        	<@hrefipaplist rootSimple.downloadUrl!''/>
                                        </td>
                                        <td>
                                        	<#if statusRespMap.download==true>
                                    			<span id="auth_status_span_${value.id}" class="btn btn-success" disabled>可下载</span>
                                    		<#elseif statusRespMap.bought==true>
                                    			<span id="auth_status_span_${value.id}" class="btn btn-warning" disabled>已购买/下载异常</span>
                                    		<#else>
                                    			<span id="auth_status_span_${value.id}" class="btn btn-danger" disabled>未购买</span>
                                        	</#if>
                                        </td>										
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


    </script>
</body>
</html>