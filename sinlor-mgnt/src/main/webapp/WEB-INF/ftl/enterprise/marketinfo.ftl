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
            <li>第三方ipa市场列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/enterprise/addmarketinfo" method="post">		                      
                                <div class="input-prepend">
                                    <span class="add-on">序号</span>
                                    <input id="add_market_id" class="span2" name="id" type="text" placeholder="序号Id" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">名称</span>
                                    <input id="add_market_name" class="span2 search" name="name" type="text" placeholder="名称" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">网址</span>
                                    <input id="add_market_site" class="span5 search" name="site" type="text" placeholder="http://" value="">
                            	</div>	
                                <button class="btn btn-info add">
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${para.errMsg}">
                                	<#if para.errMsg??>
                                		<font>${para.errMsg}</font>
                                	</#if>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>名称</th>
                                    <th>网址</th>
                                    <th>入库时间</th>
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
                                        	${value.name!''}
                                        </td>
                                        <td>
                                        	<a href="${value.site!''}" target="_blank"> ${value.site}</a>
                                        </td>
                                        <td>                                        	
                                            <#if value.createTime ??>
                                            	${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
        <script type="text/javascript">
        	$(function(){
            	$("#addForm").submit(function(){
            		var id = $("#add_market_id").val();
            		if($.isNumeric(id) && Math.floor(id) == id){
            			var name = $("#add_market_name").val();
            			if(name){
            				var site = $("#add_market_site").val();
            				if(site){
            					return true;
            				}else{
            					alert("网站不能为空!");
            					$("#add_market_site").focus();
            				}
            			}else{
            				alert("名称不能为空!");
            				$("#add_market_name").focus();
            			}
            		}else{
            			alert("序号须为整数!");
            			$("#add_market_id").focus();
            		};
            		return false
            	});
            });
        </script>
</body>
</html>