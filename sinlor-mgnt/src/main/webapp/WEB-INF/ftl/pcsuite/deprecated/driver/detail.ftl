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
            <li>PC助手iTunes驱动安装说明</li>
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
		                        <#if driver??>
	                                <div class="input-prepend">
	                                	<span class="add-on">Id:</span>
	                                    <input class="span2" name="id" readonly="readonly"  value="${driver.id}">
	                                </div>
	                                <div class="input-prepend">
	                                	<span class="add-on">操作系统：</span>
	                                    <input class="span2" readonly="readonly" value="${driver.desc}">
	                                </div>
	                                <div class="input-prepend">
	                                	<span class="add-on">适用CPU架构：</span>
	                                    <input class="span2" name="arch" readonly="readonly" value="${driver.arch}">
	                                </div>
	                                <div class="input-prepend">
	                                	<span class="add-on">适用客户端版本：</span>
	                                    <input class="span2" readonly="readonly"  value="${driver.pcsuiteVersion}">
	                                </div><br/><br/>
	                                <div class="input-prepend">
	                                	<span class="add-on">文件下载地址：</span>
	                                    <input class="span5" readonly="readonly"  value="${driver.url}">
	                                </div>
	                                <div class="input-prepend">
	                                	<span class="add-on">文件md5：</span>
	                                    <input class="span4" readonly="readonly"  value="${driver.md5}">
	                                </div>
                                </#if>
                            </form>
                        </div>
                    </div>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/pcsuite/deprecated/driver/installinfo/add">
                            	<input class="span2" name="driverId" type="hidden" value="${driver.id}">
                                <div class="input-prepend">
                                	<span class="add-on">文件名</span>
                                    <input class="span2" name="names" type="text" placeholder="安装文件名" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">安装参数</span>
                                    <input class="span2" name="params" type="text" placeholder="Windows7" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">安装顺序</span>
                                    <input class="span2" name="indexes" type="text" placeholder="数字,越小越早安装" value="">
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
                                    <th>名称</th>
                                    <th>安装参数</th>
                                    <th>安装顺序</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if installInfos??>
                                    <#list installInfos as data>
                                    <tr>                                        
                                        <td>${data.id}</td>
                                        <td>${data.name}</td>
                                        <td>${ data.param! }</td>
                                        <td class="edit_td" default_id="${data.id?c}">
                                        	<label for="name" class="span4"><span id="span_${data.id?c}" class="text-info" preValue="${data.index!}">${ data.index! }</span></label>
                                        </td>
                                        <td>
                                        	<#if data.status == 0>
                                        		<font id="status_font_${data.id}" color="green">√</font>
                                    		<#else>
                                    			<font id="status_font_${data.id}" color="red">X</font>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if data.status == 0>
                                    			<span default_id = ${data.id} status = "0" set_status="-1" class="btn delete btn-danger">删除</span>
                                    		<#else>
                                    			<span default_id = ${data.id} status = "-1" set_status="0" class="btn delete btn-success">正常</span>
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
 			var setStatus = $(this).attr("set_status");
 			var postUrl = "${rc.contextPath}/auth/pcsuite/deprecated/driver/installinfo/modify.json";
 			var confirmMsg = "";
 			if(setStatus == -1){
 				confirmMsg = "删除当前记录";
 			}else{
 				confirmMsg = "恢复当前记录";
 			}
	 		bootbox.confirm(confirmMsg+"?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": setStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{	 					
	 					alert("操作成功，已" + confirmMsg + "!");
	 					var fontCtl = $("#status_font_"+ id);
	 					$(eventCtl).attr("status", setStatus);
	 					$(eventCtl).attr("set_status", preStatus);
	 					if(setStatus == 0){
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
		
 		$('.edit_td').click(function() {
 			var id = $(this).attr("default_id");
 			var spanCtr = $("#span_" + id);
 			var preValue = $(spanCtr).attr("preValue");
 			var input = $('<input id="attribute" type="text" class="span4" preValue="' + preValue + '" value="' + preValue + '" />')
 			$(spanCtr).text('').append(input);
 			input.select();
 			input.blur(function() {			
 				var curValue = $('#attribute').val();
 				if(curValue == ""){
 					$(this).val(preValue);
 					$(this).focus();
 					return ;
 				}
 				if(curValue == preValue){
		   			$('#attribute').parent().text(preValue);
		   			$('#attribute').remove();
	   				return ;
	   			}
 				bootbox.confirm("修改记录的安装顺序由"+preValue+"到"+curValue+"?", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/auth/pcsuite/deprecated/driver/installinfo/modify.json";
			 			var posting = $.post(postUrl, {'id': id, "index": curValue});
			 			posting.done(function(errMsg){
			 				if(errMsg){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
					   			$('#attribute').parent().text(preValue);
					   			$('#attribute').remove();
			 				}else{
			 					alert("修改成功.");
			 					$('#attribute').parent().attr("preValue", curValue);
					   			$('#attribute').parent().text(curValue);
					   			$('#attribute').remove();
			 				}
			 			});
		 			}else{
			   			$('#attribute').parent().text(preValue);
			   			$('#attribute').remove();
		 			}
				});
 			});
		});
    </script>
</body>
</html>