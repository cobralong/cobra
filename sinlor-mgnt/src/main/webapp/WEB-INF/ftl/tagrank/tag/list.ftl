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
            <li>AppTag列表</li>
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
                              	<div class="input-prepend">
                                	<span class="add-on">父级分类</span>
                                     <@spring.formSingleSelect "para.parent", tagMap, " " />
                                </div>
                                <br/>
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

                    <#--<button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>-->
                    <#--<div id="div_add" class="well form-inline">-->
                        <#--<div class='main-action'>-->
                            <#--<form id="addForm" name="addForm" action="${rc.contextPath}/auth/tagrank/tag/add" method="post">-->
                                <#--<div class="input-prepend">-->
                                    <#--<span class="add-on">父级标签</span>-->
                                <#--<@spring.formSingleSelect "para.parent", toptagMap, " " />-->
                                <#--</div>-->
                                <#--<br/>-->
                                <#--<div class="input-prepend">-->
                                    <#--<span class="add-on">标签名</span>-->
                                    <#--<input class="span1" size="10" name="name" type="text" placeholder="标签名" value="">-->
                                <#--</div>-->
                                <#--<button class="btn btn-info add" >-->
                                    <#--<i class="icon-search icon-white"></i>增加-->
                                <#--</button>-->
                                <#--<div id="addInfoDiv" errMsg="${para.errMsg}">-->
                                <#--<#if para.errMsg??>-->
                                    <#--<font>${para.errMsg}</font>-->
                                <#--</#if>-->
                                <#--</div>-->
                            <#--</form>-->
                        <#--</div>-->
                    <#--</div>-->

                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Name</th>
                                    <th>EnName</th>
                                    <th>Position</th>
                                    <th>图标</th>
                                    <th>父ID</th>
                                    <th>颜色</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<input id="hidden_${value.id}" type="hidden" app_title="${value.name}" promote_status="${value.status}"/>
                                        <td>
                                        	${value.id}
                                        </td> 
                                        <td>
                                        	${value.name}
                                        </td>
                                        <td>
                                            ${value.enName}
                                        </td>
                                        <td>
                                        ${value.position}
                                        </td>
                                        <td>
                                            <@appicon value.icon, value.name/>
                                        </td>
                                        <td>${value.parent}</td>
                                        <td>${value.color}</td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </form>
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