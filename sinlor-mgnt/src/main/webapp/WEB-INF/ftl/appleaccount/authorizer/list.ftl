<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-授权账号列表</title>
		<#include "lib/base_source.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>授权账号列表</li>
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
		                                	<span class="add-on">AppleId:</span>
		                                	<input name="appleId" value="" type="input"/>
		                                </div>
		                               <div class="input-prepend">
			                               	<span class="add-on">类型</span>
			                               	<@spring.formSingleSelect "para.type", types, " " />
		                               </div>
		                               <div class="input-prepend">
			                               	<span class="add-on">状态</span>
			                               	<@spring.formSingleSelect "para.status", status, " " />
		                               </div>
		                                <button class="btn btn-info query" onClick="modifyPage()">
		                                    <i class="icon-search icon-white"></i>过滤
		                                </button>
		                            </form>
		                        </div>
		                    </div>
	                        <#if errMsg>
	                        	<font color="red">添加失败，原因：${errMsg}</font> 
	                        </#if>
		                    <div id="div_add" class="well form-inline">
		                        <div class='main-action'>
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/appleaccount/authorizer/add" enctype="multipart/form-data" method="post">
		                                <div class="input-prepend">
		                                	<span class="add-on">AppleId：</span>
		                                	<input class="span2" name="appleId" type="text" placeholder="appleId" value="">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">密码：</span>
		                                	<input class="span2" name="password" type="text" placeholder="pwd" value="">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">名称：</span>
		                                	<input class="span2" name="name" type="text" placeholder="" value="">
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">类型：</span>		                                	
			                                <select name="type">
												<#list types?keys as key>													
													<option value="${key}">${types[key]}</option>
												</#list>
											</select>
		                                </div>
		                                <div class="input-prepend">
		                                	<span class="add-on">区域：</span>
		                                	<input class="span2" name="locale" type="text" placeholder="cn" value="">
		                                </div>
		                                <span class="add-on">用户图标:</span>
	                                	<div class="fileinput fileinput-new" data-provides="fileinput">
	                                        <div class="fileinput-new thumbnail" style="width: 75px; height: 75px;">
	                                            <img data-src="holder.js/100%x100%" alt="...">
	                                        </div>
	                                        <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 75px; max-height: 75px	;"></div>
	                                        <div>
	                                            <span class="btn btn-default btn-file">
		                                            <span class="fileinput-new">Select image</span>
		                                            <span class="fileinput-exists">Change</span>
		                                            <input type="file" name="icon">
	                                            </span>
	                                            <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
	                                        </div>
	                                    </div>
		                                <br/>                                
		                                <br/>
		                                <button class="btn btn-info query" >
		                                    <i class="icon-search icon-white"></i>添加
		                                </button>
		                            </form>
		                        </div>                        
		                    </div>
	            			<br/><br/>
		                    <div class="table-content">
		                        <form id="itemForm" name="itemForm" action="" method="post">
		                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
		                                <thead>
		                                <tr>
		                                    <th>AppleId</th>
		                                    <th>密码</th>
		                                    <th>名称</th>
		                                    <th>icon</th>
		                                    <th>区域</th>
		                                    <th>类型</th>
		                                    <th>对外授权</th>
		                                    <th>状态</th>
		                                    <th>操作</th>
		                                    <th>详情</th>
		                                    <th>已购应用</th>
		                                    <th>可下载应用</th>
		                                    <th>已授权设备</th>
		                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if values??>
		                                    <#list values as value>
		                                    <tr>
		                                        <td>${value.appleId}</td>
		                                        <td class="edit_td" default_id="${value.id?c}" property="password" account_name="${value.appleId}">
	                                        		<label for="name" class="span4"><span id="span_password_${value.id?c}" class="text-info" preValue="${value.password!}">${ value.password! }</span></label>
	                                        	</td>
		                                        <td class="edit_td" default_id="${value.id?c}" property="name" account_name="${value.appleId}">
	                                        		<label for="name" class="span4"><span id="span_name_${value.id?c}" class="text-info" preValue="${value.name!}">${ value.name! }</span></label>
	                                        	</td>
		                                        <td>
		                                        	<#if value.icon??>
		                                        		<img style="width:15px; height:15px;" src="${value.icon}"/>
		                                        	</#if>
		                                        </td>                                     
		                                        <td class="edit_td" default_id="${value.id?c}" property="locale" account_name="${value.appleId}">
	                                        		<label for="name" class="span4"><span id="span_locale_${value.id?c}" class="text-info" preValue="${value.locale!}">${ value.locale! }</span></label>
	                                        	</td>	                                        	
	                                        	<td>
	                                        		${types[value.type?c]}
	                                        	</td>
	                                        	<td>
	                                        		${appleAuthorizerAccountPrepares[value.prepare?c]}
	                                        	</td>
			                                    <td>
			                                    	<#if value.status == 0>
			                                    		<font id="status_font_${value.id}" color="green">√</font>
			                                		<#else>
			                                			<font id="status_font_${value.id}" color="red">X</font>
			                                		</#if>
			                                    </td>
			                                    <td>
			                                    	<#if value.status == 0>
			                                			<span default_id = ${value.id} status = "${value.status}" class="btn delete btn-danger">删除</span>
			                                		<#else>
			                                			<span default_id = ${value.id} status = "${value.status}" class="btn delete btn-success">正常</span>
			                                		</#if>
			                                    </td>
		                                        <td>
		                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/appleaccount/authorizer/detail?id=${value.id}">详情</a>
		                                        </td>
		                                        <td>
		                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/appleaccount/authorizer/buy/app/info/list?appleId=${value.appleId}">已购应用</a>
		                                        </td>
		                                        <td>
		                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/authorizer/app/ipa/list?appleAccountId=${value.id?c}">可下载应用</a>
		                                        </td>
		                                        <td>
		                                        	 <a class="btn btn-info query" href="${rc.contextPath}/auth/appleaccount/authorizer/auth/device/info/list?appleAccountId=${value.id?c}">已授权设备</a>
		                                        </td>
		                                    </tr>
		                                    </#list>
		                                </#if>
		                                </tbody>
		                            </table>
		                        </form>
	                    		<div class="pagination" id="itemPage">
	                    	</div>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>
		<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	    <script>
		    $(document).ready(function(){
		        var currPageDiv = "#pager_page";
		        var totalDiv = "#pager_total";
		        var sizeDiv = "#pager_size";
		        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
		    });
		    
		    function modifyPage(){
		    	$("#pager_page").val(1);
		    }
		    
	 		$('.edit_td').click(function() {
	 			var id = $(this).attr("default_id");
	 			var property = $(this).attr("property");
	 			var spanCtr = $("#span_" + property + "_" + id);
	 			var preValue = $(spanCtr).attr("preValue");
	 			var accountName = $(this).attr("account_name");
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
			   			var text = $('#attribute').val();
			   			$('#attribute').parent().text(preValue);
			   			$('#attribute').remove();
		   				return ;
		   			}
		   			var typeName = (property == "password") ? "密码" : (property == "locale") ? "区域" : "名称";
	 				bootbox.confirm("将帐号【" + accountName  +"】的" + typeName + "由【" + preValue + "】改为【" + curValue + "】?", function(result) {
			 			if(result){
	 						var postUrl = "${rc.contextPath}/auth/appleaccount/authorizer/modify.json";
	 						var postData = "id=" + id + "&" + property + "=" + curValue;
				 			var posting = $.post(postUrl, postData);
				 			posting.done(function(resp){
				 				if(!resp.data){//
				 					//show msg	 					
				 					alert("操作失败,请联系该死的开发人员.Msg:"+resp.message);			 					
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
		    
			$("span.delete").bind("click",function(){
		 			event.preventDefault();
		 			bootbox.setDefaults({
		 				locale: "zh_CN"
		 			});
		 			var eventCtl = $(this);
		 			var id = $(this).attr("default_id"); 			
		 			var preStatus = $(this).attr("status");
		 			var status = preStatus == 0 ? -1 : 0;
		 			var postUrl = "${rc.contextPath}/auth/appleaccount/authorizer/modify.json";
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
			 			posting.done(function(resp){
			 				if(!resp.data){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+resp.message);
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