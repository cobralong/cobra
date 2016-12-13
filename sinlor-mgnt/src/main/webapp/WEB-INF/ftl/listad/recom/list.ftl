<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台-横幅广告推荐列表</title>
		<#include "lib/base_source.ftl">			
		<#include "ftl_macro.ftl">
	</head>
	<body>
		<div class='whole-container'>
			<#include "/lib/header.ftl">
		    <div>
		        <ul class="breadcrumb">
		            <li>管理后台</li><span class="divider">/</span>
		            <li>横幅广告推荐列表</li>		            
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
		                                	<span class="add-on">广告Id:</span>
		                                	<input name="listAdInfoId" value="${para.listAdInfoId}" type="input"/>
		                                </div>
									    <div class="input-prepend">
									    	<span class="add-on">渠道:</span>
									    	<@spring.formSingleSelect "para.channel", channels, " " />
									    </div>
									    <div class="input-prepend">
									    	<span class="add-on">状态:</span>
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
		                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/listad/recom/add" enctype="multipart/form-data" method="post">
		                            	<div class="input-prepend">
                                            <span class="add-on">广告Id:</span>
                                            <input class="span2 search" name="listAdInfoId" type="text" placeholder="ID" value="${para.listAdInfoId}">
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">广告标题:</span>
                                            <#if info??>
                                            	<input id="input_title" class="span2" style="width:200px" name="title" type="text" placeholder="应用名称" disabled value="${info.title}">
                                            	<img id="ad-info-icon" style="width:475px; height:125px;" disabled src="${info.icon}"/>
                                            <#else>
                                            	<input id="input_title" class="span2" style="width:200px" name="title" type="text" placeholder="应用名称" disabled value="">
                                            	<img id="ad-info-icon" style="width:475px; height:125px;"  disabled src=""/>                                            	
                                            </#if>
                                        </div>
		                                <br/>
                                        <br/>
                                        
                                       	<div class="input-prepend">
                                        	<#if info && info.type == 1??>
                                        		<#assign rootSimple = rootSimples[info.target]>
                                        		<div id="div-app">
		                                       		<span class="add-on">资源名称：</span>
													<#if rootSimple??>
														<input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="${rootSimple.name}">
		                                           		<span class="add-on">OTA包</span>
				                                    	<input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="${rootSimple.downloadUrl}">
				                                    <#else>
				                                    	<input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="">
		                                            	<span class="add-on">OTA包</span>
				                                    	<input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="">
													</#if>
												</div>
												<div id="div-url" style="display:none;">
													<span class="add-on">资源名称：</span>
													<input id="input-url" disabled value="${info.target}"/>
												</div>
											<#else>
												<div id="div-app" style="display:none;">
		                                       		<span class="add-on">目的资源：</span>
													<#if rootSimple??>
														<input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="${rootSimple.name}">
		                                           		<span class="add-on">OTA包</span>
				                                    	<input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="${rootSimple.downloadUrl}">
				                                    <#else>
				                                    	<input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="">
		                                            	<span class="add-on">OTA包</span>
				                                    	<input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="">
													</#if>
												</div>
												<div id="div-url">
													<span class="add-on">资源名称：</span>
													<input id="input-url" disabled value="${info.target}"/>
												</div>
											</#if>
                                       	</div>
                                        <br/>
                                        <br/>
                                        <div class="input-prepend">
                                            <span class="add-on">渠道:</span>
                                     		<@spring.formSingleSelect "para.channel", channels, " " />
                                        </div>
                                        <span class="add-on">开始时间:</span>
                                        <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                            <input class="span2" id="startDateString" name="st" size="16" type="text" value="">
                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>
                                        <span class="add-on">结束时间:</span>
                                        <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                            <input class="span2" id="endDateString" name="et" size="16" type="text" value="">
                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>									    
                                        <div class="input-prepend">
                                            <span class="add-on">展示列表:</span>
                                            <@spring.formSingleSelect "para.place", places, " " />                                   
                                        </div>							    
                                        <div class="input-prepend">
                                            <span class="add-on">在展示列表中的位置:</span>
                                            <input class="span1" size="10" name="position" type="text" placeholder="position" value="5">                                   
                                        </div>
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
		                                    <th>渠道</th>
		                                    <th>列表</th>
		                                    <th>位置</th>
		                                    <th>开始时间</th>
		                                    <th>结束时间</th>
		                                    <th>广告标题</th>
		                                    <th>资源</th>
		                                    <th>状态</th>
		                                    <td>操作</td>
		                                </tr>
		                                </thead>
		                                <tbody class="sortable">
		                                <#if values??>
		                                    <#list values as value>
			                                    <tr>
			                                    	<#list infos as info>
		                        	                	<#if info.id == value.listAdInfoId>
		                        	                		<#assign adInfo = info/>
		                        	                	</#if>
		                        	                </#list>
			                                        <td>${channels[value.channel]}</td>
			                                        <td>${places[value.place]}</td>
			                                        <td class="edit_td" pid="${value.id?c}" title = "${adInfo.title}" channel="${channels[value.channel]}" place="${places[value.place]}">
			                                        	<label for="name" class="span1"><span id="p_${value.id?c}" class="text-info" preValue="${value.position?c}">${ value.position! }</span></label>
			                                        </td>
			                                        <td>
						                                <#if value.startTime ??>
						                                ${ value.startTime?string("yyyy-MM-dd HH:mm:ss") }
						                                <#else>
						                                    -
						                                </#if>
						                            </td>
						                            <td class="edit_time_td" pid="${value.id?c}" title = "${adInfo.title}" channel="${channels[value.channel]}" place="${places[value.place]}">
			                                        	<span id="p_time_${value.id?c}" actionChannel = "${value.channel!}" preValue="${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }">${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }</span>
			                                        </td>
						                            
													<#assign notInList=true/>
			                                        <#list infos as info>
		                        	                	<#if info.id == value.listAdInfoId>
		                            	            		<td>${info.title}</td>
		                            	            		<#if info.type == 1>
		                            	            			<td><@hrefapp info.target, rootSimples[info.target].name/></td>
		                            	            		<#elseif info.type == 2>
		                            	            			<td><a href="${info.target}" target="_blank">${info.target}</a></td>
		                            	            		</#if>
		                                    	    	</#if>
				                                    </#list>
			                                        <td>
			                                        	<@wrongrightspan value.status, 0, value.id/>
			                                        </td>
				                                    <td>
				                                    	<#if value.status == 0>
				                                			<span default_id = ${value.id} status = "${value.status}" class="btn delete btn-danger">删除</span>
				                                		<#else>
				                                			<span default_id = ${value.id} status = "${value.status}" class="btn delete btn-success">正常</span>
				                                		</#if>
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
		<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
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
		    
            $("input.search").bind("blur", function () {
                var ctl = $(this);
                var rid = $(ctl).val();
                var txtCtl = $("#input_title");
                var icomImg = $("#ad-info-icon");
                var btn = $("button.add");
                var posting = $.post("${rc.contextPath}/auth/listad/info/detail.json", {"id": rid});
                posting.done(function (result) {
                    if (result.success) {
                        $(txtCtl).val(result.data.info.title);
                        $(icomImg).prop("src",result.data.info.icon);
                        if(result.data.info){
                        	if(result.data.info.type == 1){
                        		if(result.data.app){
	                        		$("#div-app").show();
	                        		$("#div-url").hide();
									$("#input_app_name").val(result.data.app.name);
									$("#input_plist").val(result.data.app.downloadUrl);
                        		}
                        	}else if(result.data.info.type == 2){
                        		$("#div-app").hide();
                        		$("#div-url").show();                        		
                        		$("#input-url").val(result.data.info.target)
                        	}
                        }
                        $(btn).removeAttr("disabled");
                    } else {
                        $(ctl).focus();
                        $(txtCtl).val(result.message);
                        $(icomImg).prop("src","");
                        $("#input_app_name").val('');
                        $("#input_plist").val('');
                        $("#input-url").val("");
                        $(btn).attr("disabled", "disabled");
                    }
                });
            });

     		$('.edit_td').click(function() {
     			var pid = $(this).attr("pid");
     			var place = $(this).attr("place");
     			var title = $(this).attr("title");
     			var channel = $(this).attr("channel");
     			var title = $(this).attr("title");
     			var preValue = $('#p_'+pid).attr("preValue");
     			var input = $('<input id="attribute" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />')
     			$('#p_'+pid).text('').append(input);
     			input.select();
     			input.blur(function() {
     				var preValue = $(this).parent().attr("preValue");
     				var hidden = $("#hidden_"+pid);
     				var appTitle = $(hidden).attr("app_title"); 				
     				var curValue = $('#attribute').val();
     				if(curValue == ""){
     					$(this).val(preValue);
     					$(this).focus();
     					reutrn;
     				}
     				if(curValue == preValue){
    		   			$('#attribute').parent().text(preValue);
    		   			$('#attribute').remove();
    	   				return;
    	   			}
     				bootbox.confirm("将广告【" + title + "】的横幅广告在渠道【"+ channel + "】的【" + place  +"】列表的展示位置由"+preValue+"------>"+curValue+"?", function(result) {
    		 			if(result){
     						var postUrl = "${rc.contextPath}/auth/listad/recom/modify";
    			 			var posting = $.post(postUrl, {'id': pid, "position": curValue});
    			 			posting.done(function(errMsg){
    			 				if(errMsg){//
    			 					//show msg	 					
    			 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
    					   			$('#attribute').parent().text(preValue);
    					   			$('#attribute').remove();
    			 				}else{
    			 					alert("修改成功.");
    					   			$('#attribute').parent().text(curValue);
    					   			 $('#p_'+pid).attr("preValue", curValue);
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
     		
     		$('.edit_time_td').click(function() {
     			var pid = $(this).attr("pid");
     			var place = $(this).attr("place");
     			var title = $(this).attr("title");
     			var channel = $(this).attr("channel");
     			var title = $(this).attr("title");
     			var preValue = $('#p_time_'+pid).attr("preValue");
     			var input = $('<input id="timeattribute" type="text" preValue="' + preValue + '" value="' + preValue + '" />')
     			$('#p_time_'+pid).text('').append(input);
     			input.select();
     			input.blur(function() {
     				var preValue = $(this).parent().attr("preValue");
     				var hidden = $("#hidden_"+pid);
     				var appTitle = $(hidden).attr("app_title"); 				
     				var curValue = $('#timeattribute').val(); 				
     				if(curValue == ""){
     					$(this).val(preValue);
     					$(this).focus();
     					return;
     				}
     				if(curValue == preValue){
    		   			$('#timeattribute').parent().text(preValue);
    		   			$('#timeattribute').remove();
    	   				return;
    	   			}
     				bootbox.confirm("将广告【" + title + "】的横幅广告在渠道【"+ channel + "】的【" + place  +"】列表的推荐结束时间由"+preValue+"------>"+curValue+"?", function(result) {
    		 			if(result){
   	 						var postUrl = "${rc.contextPath}/auth/listad/recom/modify";
   				 			var posting = $.post(postUrl, {'id': pid, "et": curValue});
   				 			posting.done(function(errMsg){
   				 				if(errMsg){//
   				 					//show msg	 					
   				 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
   						   			$('#timeattribute').parent().text(preValue);
   						   			$('#timeattribute').remove();
   				 				}else{
   				 					alert("修改成功.");
   						   			$('#timeattribute').parent().text(curValue);
   						   			 $('#p_time_'+pid).attr("preValue", curValue);
   						   			$('#timeattribute').remove();
   				 				}
   				 			});
    		 			}else{
    			   			$('#timeattribute').parent().text(preValue);
    			   			$('#timeattribute').remove();
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
	 			var postUrl = "${rc.contextPath}/auth/listad/recom/modify";
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
		 						$(eventCtl).attr("class", "btn ∂ btn-danger");
		 						$(eventCtl).html("删除");
		 						$(fontCtl).removeClass("lable-delete").addClass("label-success");
		 						$(fontCtl).html("√");
		 					}else{
		 						$(eventCtl).attr("class", "btn delete btn-success");
		 						$(eventCtl).html("正常");
		 						$(fontCtl).removeClass("label-success").addClass("label-delete");
		 						$(fontCtl).html("X");
		 					}
		 				}
		 			});
				});
			});
     		
            $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
                timeFormat: 'yyyy-mm-dd',
                stepYear: 1,
                stepMonth: 1,
                stepDay: 1
            });
		</script>
	</body>
</html>