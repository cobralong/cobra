<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<title>应用汇-管理后台-PC助手iPhone模型图列表</title>
		<#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
	</head>
	<body>
		<div class='whole-container'>
		<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>PC助手iPhone模型图列表</li>
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
		                                	<span class="add-on">iOS系统</span>
		                             		<select name="platform" class="search_platform">
				                            	<#list platforms as platform>
				                            		<#if para.platform == platform.platform>
				                            			<option value="${platform.platform}" selected>${platform.content}</option>
				                            		<#else>
				                        				<option value="${platform.platform}">${platform.content}</option>
				                        			</#if>
				                            	</#list>
		                                	</select>
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">设备颜色</span>
		                             		<select id = "sel_search_color" name="color">		
		                                	</select>
		                                </div>
		                                <button class="btn btn-info query" >
		                                    <i class="icon-search icon-white"></i>过滤
		                                </button>
		                            </form>
		                        </div>
		                    </div>
		                    <button class="btn addToggle btn-info add">点击新增</button>
                            <#if errMsg>
                            	<font color="red">添加失败，原因：${errMsg}</font> 
                            </#if>
		                    <div id="div_add" class="well form-inline" style="display:none;">
		                        <div class='main-action'>
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/pcsuite/iphone/model/add" enctype="multipart/form-data" method="post">
		                                <div class="input-prepend">
		                                	<span class="add-on">iOS系统</span>
		                             		<select name="platform" class="add_platform">
				                            	<#list platforms as platform>
				                            		<#if para.platform == platform.platform>
				                            			<option value="${platform.platform}" selected>${platform.content}</option>
				                            		<#else>
				                        				<option value="${platform.platform}">${platform.content}</option>
				                        			</#if>
				                            	</#list>
		                                	</select>
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">设备颜色</span>
		                             		<select id = "sel_add_color" name="color">		
		                                	</select>
		                                </div>
		                                <span class="add-on">手机模型图:</span>
                                    	<div class="fileinput fileinput-new" data-provides="fileinput">
	                                        <div class="fileinput-new thumbnail" style="width: 248px; height: 512px;">
	                                            <img data-src="holder.js/100%x100%" alt="...">
	                                        </div>
	                                        <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 248px; max-height: 512px	;"></div>
	                                        <div>
	                                            <span class="btn btn-default btn-file">
		                                            <span class="fileinput-new">Select image</span>
		                                            <span class="fileinput-exists">Change</span>
		                                            <input type="file" name="screen">
	                                            </span>
	                                            <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
	                                        </div>
                                        </div>
		                                <br/>                                
		                                <br/>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕左上角离左边距离：</span>
		                                	<input class="span2" name="paddingLeft" type="text" placeholder="padding left" value="">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕左上角离顶距离：</span>
		                                	<input class="span2" name="paddingTop" type="text" placeholder="padding top" value="">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕左下角离右边距离：</span>
		                                	<input class="span2" name="paddingRight" type="text" placeholder="padding right" value="">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">屏幕右下角离底部距离：</span>
		                                	<input class="span2" name="paddingBottom" type="text" placeholder="padding bottom" value="">
		                                </div>
		                                <button class="btn btn-info query" >
		                                    <i class="icon-search icon-white"></i>添加
		                                </button>
		                            </form>
		                        </div>                        
		                    </div>
		                    <br/><br/>
		                    <div class="table-content">
		                    <#-- table -->
		                    	<h4>当前有记录<font color="green">${para.pager.total}</font>条<h4>
		                        <form id="itemForm" name="itemForm" action="" method="post">
		                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
		                                <thead>
			                                <tr>                                    
			                                    <th>Id</th>
			                                    <th>设备型号</th>
			                                    <th>设备颜色</th>
			                                    <th>模型图地址</th>
			                                    <th>状态</th>
			                                    <th>操作</th>
			                                    <th>详情</th>
			                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if datas??>
		                                    <#list datas as data>
		                                    <tr>                                        
		                                        <td>
		                                        	${data.id}
		                                        </td>
	                                        	<td>
	                                        		<#list platforms as platform>
				                            			<#if data.platform == platform.platform>
				                            				${platform.content}
				                        				</#if>
				                            		</#list>
		                                        </td>
		                                        <td>${ data.color! }</td>
		                                        <td><a href="${data.imgUrl}" target="_blank"><img width="${data.width/10}" height="${data.height/10}" src="${data.imgUrl}"></a></td>
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
		                                        <td><a class="btn btn-info query" href="${rc.contextPath}/auth/pcsuite/iphone/model/detail?id=${data.id}">详情</a></td>
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
		        var platform = $("select.search_platform").val();
		        initSearchColors(platform, "sel_search_color");
		        platform = $("select.add_platform").val();
		        initSearchColors(platform, "sel_add_color");
		    });
		        
		    $(document).on( 'change', 'select.search_platform', function(){
		    	var platform = $(this).val();
		    	initSearchColors(platform, "sel_search_color");    	
		    });
		        
		    $(document).on( 'change', 'select.add_platform', function(){
		    	var platform = $(this).val();
		    	initSearchColors(platform, "sel_add_color");    	
		    });
		    
	 		$("button.addToggle").bind("click", function(){
	 			$("#div_add").toggle();
	 		});
	 		
		    function initSearchColors(platform, colorSelId){
		    	var postUrl = "${rc.contextPath}/auth/client/apple/device/color/list.json";
	    		var posting = $.post(postUrl, {"platform": platform});
	    		posting.done(function(data){
	    			var optionHtml=""; 	    			
	    			for(color in data.data){
	    				optionHtml += "<option value='" + data.data[color] + "'>" + data.data[color] + "</option>";
	    			}
	    			optionHtml+="<option value=''>无</option>";
	    			$("#" + colorSelId).html(optionHtml);
	    		});
		    }
		    
	 		$("span.delete").bind("click",function(){
	 			event.preventDefault();
	 			bootbox.setDefaults({
	 				locale: "zh_CN"
	 			});
	 			var eventCtl = $(this);
	 			var id = $(this).attr("default_id"); 			
	 			var preStatus = $(this).attr("status");
	 			var status = preStatus == 0 ? -1 : 0;
	 			var postUrl = "${rc.contextPath}/auth/pcsuite/iphone/model/modify.json";
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