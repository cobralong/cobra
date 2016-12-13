<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-PC助手iTunes DLL驱动列表</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>PC助手渠道列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/pcsuite/channel/add" method="post">
                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">                               
                                <div class="input-prepend">
                                	<span class="add-on">渠道：</span>
                                	<input class="span4" name="channel" type="input" placeholder="pc.***" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">渠道描述：</span>
                                	<input class="span2" name="name" type="text" placeholder="渠道描述" value="">
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>添加
                                </button>
                            </form>
                            <#if errMsg>
                            	<font color="red">添加失败，原因：${errMsg}</font> 
                            </#if>
                        </div>                        
                    </div>
                    <div class="table-content">
                    <#-- table -->
                    	<h4>当前有记录<font color="green">${para.pager.total}</font>条<h4>
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>                                    
                                    <th>Id</th>
                                    <th>渠道</th>
                                    <th>渠道描述</th>
                                    <th>状态</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if datas??>
                                    <#list datas as data>
                                    <tr>                                        
                                        <td>
                                        	${data.id}
                                        </td>
                                        <td>${ data.channel! }</td>
                                        <td>${ data.name! }</td>
                                        <td>
                                        	<#if data.status == 0>
                                        		<font id="status_font_${data.id}" color="green">√</font>
                                    		<#else>
                                    			<font id="status_font_${data.id}" color="red">X</font>
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
	</div>
	<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#addForm",currPageDiv,totalDiv,sizeDiv);
	    });
    </script>
</body>
</html>