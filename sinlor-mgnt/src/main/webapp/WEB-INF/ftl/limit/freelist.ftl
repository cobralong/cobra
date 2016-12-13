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
            <li>限免榜列表</li>
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
                                	<span class="add-on">排序类别</span>
                                     <@spring.formSingleSelect "para.type", types, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
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
                                    <th>应用</th>
                                    <th>图标</th>
                                    <th>更新时间</th>
                                    <th>限免时间</th>
                                    <th>原始价格</th>
                                    <th>热度</th>
                                    <th>状态</th>
                                    <th>下载地址</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<input id="hidden_${value.id}" type="hidden" root_id = "${value.rootId!}" app_title="${rootSimple.name}"  limit_free_status="${value.status}"/>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td> 
                                        <td>
                                            <#if rootSimple.releaseDate ??>
                                            ${ rootSimple.releaseDate?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                            <#if rootSimple.discountDate ??>
                                            ${ rootSimple.discountDate?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>${value.priceOrigin}</td>
                                        <td>${value.hot}</td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="btn btn-success">正常</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="btn btn-danger" disabled>删除</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<@hrefipaplist rootSimple.downloadUrl!''/>
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
 
 		$("input.search").bind("blur", function(){
 			var ctl = $(this);
 			var rid = $(ctl).val();
 			var txtCtl = $("#input_app_name");
 			var btn = $("button.add");
 			var posting = $.post("${rc.contextPath}/auth/promote/search", {"id" : rid});
 			posting.done(function(result){
 				if(result.success){
 					$(txtCtl).val(result.data.title);
 					$(btn).removeAttr("disabled");
 				}else{
 					$(txtCtl).val(result.message);
					$(btn).attr("disabled", "disabled");
 				} 				
 			});
 		});

    </script>
</body>
</html>