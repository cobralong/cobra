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
            <li>PC助手iTunes驱动列表</li>
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
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="add-on">操作系统</span>
                             		<select name="system">
		                            	<#list windows as window>
		                            		<#if para.system == window.version>
		                            			<option value="${window.version}" selected>${window.desc}</option>
		                            		<#else>
		                        				<option value="${window.version}">${window.desc}</option>
		                        			</#if>
		                            	</#list>
                                	</select>
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">系统架构</span>
                                    <@spring.formSingleSelect "para.arch", archs, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">适用客户端版本：</span>
                                    <@spring.formSingleSelect "para.pcsuiteVersion", versions, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/pcsuite/deprecated/driver/add" enctype="multipart/form-data" method="post">
                                <div class="input-prepend">
                                	<span class="add-on">操作系统</span>
                                	<select name="system">
		                            	<#list windows as window>
		                            		<#if para.system == window.version>
		                            			<option value="${window.version}" selected>${window.desc}</option>
		                            		<#else>
		                        				<option value="${window.version}">${window.desc}</option>
		                        			</#if>
		                            	</#list>
                                	</select>
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">系统架构</span>
                                	<select name="arch">
		                            	<#list archs as arch>
		                        			<option value="${arch.desc}">${arch.desc}</option>
		                            	</#list>
                                	</select>
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">适用客户端版本：</span>
                                    <@spring.formSingleSelect "para.pcsuiteVersion", versions, " " />
                                </div>                                
                                <br/>                                
                                <br/>
                                <div class="input-prepend">
                                	<span class="add-on">驱动文件地址：</span>
                                	<input class="span4" name="url" type="text" placeholder="文件CDN地址" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">驱动文件Md5值：</span>
                                	<input class="span2" name="md5" type="text" placeholder="文件MD5值" value="">
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
                                    <th>名称</th>
                                    <th>操作系统</th>
                                    <th>系统描述</th>
                                    <th>系统架构</th>
                                    <th>助手版本号</th>
                                    <th>文件下载地址</th>
                                    <th>文件MD5值</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                    <th>安装说明</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if datas??>
                                    <#list datas as data>
                                    <tr>                                        
                                        <td>
                                        	${data.id}
                                        </td>
                                        <td>${ data.system! }</td>
                                        <td>${ data.desc! }</td>
                                        <td>${data.arch! }</td>
                                        <td>${data.pcsuiteVersion! }</td>
                                        <td><@shorthref data.url!/></td>
                                        <td>${data.md5}</td>
                                        <td>
                                        	<#if data.status == 0>
                                        		<font id="status_font_${data.id}" color="green">√</font>
                                    		<#else>
                                    			<font id="status_font_${data.id}" color="red">X</font>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if data.status == 0>
                                    			<span default_id = ${data.id} status = "${data.status}" class="btn delete btn-danger">删除</span>
                                    		<#else>
                                    			<span default_id = ${data.id} status = "${data.status}" class="btn delete btn-success">正常</span>
                                    		</#if>
                                        </td>
                                        <td><a class="btn btn-info query" href="${rc.contextPath}/auth/pcsuite/deprecated/driver/detail?id=${data.id}">详情</a></td>
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
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    
 		$("span.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("default_id"); 			
 			var preStatus = $(this).attr("status");
 			var status = preStatus == 0 ? -1 : 0;
 			var postUrl = "${rc.contextPath}/auth/pcsuite/deprecated/driver/modify.json";
 			var confirmMsg = "";
 			if(status == -1){
 				confirmMsg = "删除当前记录";
 			}else{
 				confirmMsg = "恢复当前记录";
 			}
	 		bootbox.confirm(confirmMsg+"?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{ 					
	 					alert("操作成功，已" + confirmMsg + "!");
	 					var fontCtl = $("#status_font_"+ id);
	 					$(eventCtl).attr("status", status);
	 					if(status == 0){	 					
	 						$(eventCtl).attr("class", "btn delete btn-danger");
	 						$(eventCtl).html("删除");
	 						$(fontCtl).attr("color", "green");
	 						$(fontCtl).html("√");
	 					}else{
	 						$(eventCtl).attr("class", "btn delete btn-success");
	 						$(eventCtl).html("正常");
	 						$(fontCtl).attr("color", "red");
	 						$(fontCtl).html("X");
	 					}
	 				}
	 			});
			});
		});
    </script>
</body>
</html>