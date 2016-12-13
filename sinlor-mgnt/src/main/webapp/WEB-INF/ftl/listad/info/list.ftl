<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-横幅广告列表</title>
		<#include "lib/base_source.ftl">			
		<#include "ftl_macro.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>横幅广告列表</li>
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
		                                	<span class="add-on">资源Id:</span>
		                                	<input name="target" value="" type="input"/>
		                                </div>
									    <div class="input-prepend">
									    	<span class="add-on">广告类型:</span>
									    	<@spring.formSingleSelect "para.type", types, " " />
									    </div>
									    <div class="input-prepend">
									    	<span class="add-on">广告状态:</span>
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
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/listad/info/add" enctype="multipart/form-data" method="post">
		                                <div class="input-prepend">
									    	<span class="add-on">广告类型:</span>
									    	<@spring.formSingleSelect "para.type", types, " " />
									    </div>
									    
		                                <div class="input-prepend">
		                                	<span class="add-on">标题:</span>
		                                	<input type="input" class="span5" name="title" value=""/>
		                                </div>
		                                <br/><br/>
		                                <div id="div-app">
			                            	<div class="input-prepend">
	                                            <span class="add-on">资源Id:</span>
	                                            <input id="input-app-id" class="span2 search" name="target" type="text" placeholder="ID" value="">
	                                        </div>
	                                        <div class="input-prepend">
	                                            <span class="add-on">应用名称:</span>
	                                            <input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="">
	                                            <span class="add-on">OTA包:</span>
			                                    <input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="">
			                                    <input id="input_cert" class="span2" style="width:100px" name="name" type="text" placeholder="签名" disabled value="">
	                                        </div>
	                                   	</div>
	                                    <div id="div-url" style="display:none">
	                                    	<div class="input-prepend">
	                                    		<span class="add-on">网页资源:</span>
	                                    		<input id="input-url" class="span8" name="target" type="text" placeholder="http://ios.appchin.com" value=""/>
	                                    	</div>
	                                    </div>  
		                                <br/>
                                        <br/>
		                                <span class="add-on">横幅图片:</span>
	                                	<div class="fileinput fileinput-new" data-provides="fileinput">
	                                        <div class="fileinput-new thumbnail" style="width: 475px; height: 125px;">
	                                            <img data-src="holder.js/100%x100%" alt="...">
	                                        </div>
	                                        <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 475px; max-height: 125px;"></div>
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
		                        <form id="itemForm" name="itemForm" action="${rc.contextPath}/auth/listad/recom/list" method="post">
		                        	<input id="item-form-list-ad-info-id" type="hidden" name="listAdInfoId"/>
		                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
		                                <thead>
		                                <tr>
		                                    <th>Id</th>
		                                    <th>标题</th>
		                                    <th>类型</th>
		                                    <th>资源</th>
		                                    <th>icon</th>
		                                    <th>状态</th>
		                                    <th>详情</th>
		                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if values??>
		                                    <#list values as value>
		                                    <tr>
		                                    	<#if value.type == 1>
                                    				<#assign rootSimple = rootSimples[value.target]>
                                    			</#if>
		                                        <td>${value.id}</td>
		                                        <td>${value.title}</td>
		                                        <td>${types[value.type?c]}</td>
		                                        <td>
		                                        	<#if value.type ==1>
		                                        		<@hrefapp value.target, rootSimple.name/>
		                                        	<#else>
		                                        		<a href="${value.target}" target="_blank">去看看</a>
		                                        	</#if>
		                                        </td>
		                                        <td>
		                                        	<#if value.icon??>
		                                        		<img style="width: 75px; height: 25px;" src="${value.icon}"/>
		                                        	</#if>
		                                        </td>
		                                        <td>
		                                        	<@wrongrightspan value.status, 0, value.id/>
		                                        </td>
		                                        <td>
		                                        	 <input list-ad-info-id="${value.id}" type="button" class="btn btn-info query" value="推广设置"/>
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
		        var inputSel = $("#addForm select");
		        var inputSelType =  $(inputSel).val();
		        if(inputSelType == 9999 || inputSelType == 1){
		        	$("#input-app-id").prop("disabled",false);
		        	$("#input-url").prop("disabled",true);
		        	$("#input-app-id").prop("readonly",false);
		        	$("#input-url").prop("readonly",true);
		        	$("#div-app").show();
		        	$("#div-url").hide();
		        }else{
		        	$("#input-app-id").prop("disabled",true);
		        	$("#input-url").prop("disabled",false);
		        	$("#input-app-id").prop("readonly",true);
		        	$("#input-url").prop("readonly",false);
		        	$("#div-app").hide();
		        	$("#div-url").show();
		        }
		    });
		    
		    function modifyPage(){
		    	$("#pager_page").val(1);
		    }
		    
		    $("input.btn").bind("click", function(){
		    	var listAdInfoId = $(this).attr("list-ad-info-id");
		    	$("#item-form-list-ad-info-id").val(listAdInfoId);
		    	$("#itemForm").submit();
		    });
		    
		    $("#addForm select").bind("change", function(){
	 			event.preventDefault();
	 			bootbox.setDefaults({
	 				locale: "zh_CN"
	 			});
	 			var inputSelType =  $(this).val();
		        if(inputSelType == 9999 || inputSelType == 1){
		        	$("#input-app-id").prop("disabled",false);
		        	$("#input-url").prop("disabled",true);
		        	$("#input-app-id").prop("readonly",false);
		        	$("#input-url").prop("readonly",true);
		        	$("#div-app").show();
		        	$("#div-url").hide();
		        }else{
		        	$("#input-app-id").prop("disabled",true);
		        	$("#input-url").prop("disabled",false);
		        	$("#input-app-id").prop("readonly",true);
		        	$("#input-url").prop("readonly",false);
		        	$("#div-app").hide();
		        	$("#div-url").show();
		        }
			});
		    
            $("input.search").bind("blur", function () {
                var ctl = $(this);
                var rid = $(ctl).val();
                var txtCtl = $("#input_app_name");
                var btn = $("button.add");
                var posting = $.post("${rc.contextPath}/auth/promote/search", {"id": rid});
                posting.done(function (result) {
                    if (result.success) {
                        $(txtCtl).val(result.data.name);                        
						$("#input_plist").val(result.data.downloadUrl);
						$("#input_cert").val(result.data.certSerial);
                    } else {
                        $(ctl).focus();
                        $(txtCtl).val(result.message);
                    }
                });
            });
            
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
	 						var postUrl = "${rc.contextPath}/auth/account/shareaccount/modify.json";
	 						var postData = "id=" + id + "&" + property + "=" + curValue;
				 			var posting = $.post(postUrl, postData);
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
		    
			$("span.delete").bind("click",function(){
		 			event.preventDefault();
		 			bootbox.setDefaults({
		 				locale: "zh_CN"
		 			});
		 			var eventCtl = $(this);
		 			var id = $(this).attr("default_id"); 			
		 			var preStatus = $(this).attr("status");
		 			var status = preStatus == 0 ? -1 : 0;
		 			var postUrl = "${rc.contextPath}/auth/account/shareaccount/modify.json";
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