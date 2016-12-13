<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<title>应用汇-管理后台</title>
		<#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
	</head>
	<body>
	<div class='whole-container'>
		<#include "/lib/store_header.ftl">	
	    <div>
	        <ul class="breadcrumb">
	            <li>AppStore管理后台</li><span class="divider">/</span>
	            <li>分类列表</li>
	        </ul>
	    </div>
    	<div class="container-fluid">
        	<div class="row-fluid">
            	<div class="container span12">
	            	<#include "lib/alert.ftl">
	                <div class='main-content'>
		                <button class="btn addToggle glyphicon-pushpin btn-primary">查询框</button>
	                    <div id="div_add" class="well form-inline">
	                        <div class='main-action'>
	                            <form method="post">
	                                <div class="input-prepend">	                                    
	                                    <span class="add-on">客户端</span>
	                                    <select id="sel_bundleid" name="bundleId" type='add' class="client_choose">
	                                		<#list bundleIds?keys as key>
	                            				<option value="${key}">${bundleIds[key]}</option>
	                            			</#list>
	                                	</select>
	                            	</div>
	                                <button class="btn btn-info query" >
	                                    <i class="icon-query icon-white"></i>查询
	                                </button>
	                            </form>
	                        </div>
	                    </div>
	                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
	            	 	<div class="well form-inline">
	                        <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/client/adddynamiccode" method="post">
	                            <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                    	<input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                    	<input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
	                            <div class="input-prepend">	                                    
	                                <span class="add-on">客户端</span>
	                                <select id="sel_bundleid" name="bundleId" type='add' class="client_choose">
	                            		<#list bundleIds?keys as key>
	                        				<option value="${key}">${bundleIds[key]}</option>
	                        			</#list>
	                            	</select>
	                        	</div>
	                            <div class="input-prepend">
	                                <span class="add-on">客户端版本号</span>	                                    
	                            	<select id="sel_version" name="clientVersion" type='add'>
	                            		<option value="all">全部</option>                                			
	                            	</select>
	                        	</div>
	                            <div class="input-prepend">
	                                <span class="add-on">文件名称</span>
	                                <input id="input_app_name" class="span2" name="name" type="text" placeholder="文件名称" value="">
	                            </div>
	                           	<span class="add-on">上传文件:</span>
	                            <div class="fileinput fileinput-new" data-provides="fileinput">
	                                <div>
	                                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select File</span><span class="fileinput-exists">Change</span><input type="file" name="codes"></span>
	                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
	                                </div>
	                            </div>
	                            <button class="btn btn-info add" >
	                                <i class="icon-search icon-white"></i>增加
	                            </button>
	                            <div id="addInfoDiv" errMsg="${errMsg}">
	                            	<#if errMsg??>
	                            		<font>${errMsg}</font>
	                            	</#if>
	                            </div>
	                        </form>
	               		</div>
	                </div><!--end main content-->
	                <div class="table-content">
	                    <table class="table table-striped table-bordered table-condensed" id="itemTable">
	                        <thead>
	                        <tr>
	                            <th>Id</th>
	                            <th>客户端</th>
	                            <th>文件名</th>
	                            <th>地址</th>
	                            <th>MD5值</th>
	                            <th>状态</th>
	                            <th>操作</th>
	                            <th>详情</th>
	                        </tr>
	                        </thead>
	                        <tbody class="sortable">
	                        <#if values??>
	                        	<#list values as value>
	                        		<tr>
	                                	<td>
	                                     	${value.id}
	                                    </td>	                                        
                                        <td>
                                        	${value.bundleId}---${bundleIds[value.bundleId]}---${value.clientVersion}
                                        	<#list bundleIdVersionList as bundleIdVersion>
                                				<#if value.bundleId == bundleIdVersion.bundleId  && value.clientVersion == bundleIdVersion.clientVersion>
                                					---${audits[bundleIdVersion.auditStatus?c]}
                                				</#if>
                                			</#list>
                                        </td>
	                                    <td>${ value.name! }</td>
	                                    <td><@shorthref value.url!/></td>
	                                    <td>${ value.md5! }</td>
	                                    <td>
	                                    	<#if value.status == 0>
	                                    		<font id="status_font_${value.id}" color="green">√</font>
	                                    	<#else>
	                                    		<font id="status_font_${value.id}" color="red">X</font>
	                                    	</#if>
	                                    </td>
	                                    <td>
	                                    	<#if value.status == 0>
	                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" default_id="${value.id?c}" set_value="-1" cur_value="0">
	                                        		删除
	                                			</button>
                                			<#else>                                			
                                        		<button id="btn_delete_${value.id?c}" class="btn btn-success delete" default_id="${value.id?c}" set_value="0" cur_value="-1">
                                        			恢复                             	
                                				</button>
	                                		</#if>
	                                    </td>
	                                    <td> <a class="btn btn-info query" href="${rc.contextPath}/auth/store/cate/detaildynamiccode?id=${value.id}">详情</a>
	                            	</tr>
	                            </#list>
	                        </#if>
	                        </tbody>
	                    </table>
	                	<div class="pagination" id="itemPage"></div>
                	</div><!-- end div class table-content-->     
            	</div><!-- end div class container span12-->
        	</div><!-- end div class row-fluid-->
    	</div><!-- end div class container-fluid-->
	</div><!-- end div class whole-container-->
</body>
	<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script>
	    $(document).ready(function () {
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
	    });
	    
	    $(document).on( 'change', 'select.client_choose', function(){
	    	var selCtr = $(this);
	    	var bundleId = $(this).val();
	    	var optionHtml="<option value='all'>全部</option>";
	    	var postUrl = "${rc.contextPath}/auth/store/client/listclientversions.json";
	    	if(bundleId=="all"){
	    		$("#sel_version").html(optionHtml);
	    	}else{
	    		var posting = $.post(postUrl, {"bundleId": bundleId});
	    		posting.done(function(data){	    			
	    			for(version in data.data){
	    				optionHtml += "<option value='" + data.data[version] + "'>" + data.data[version] + "</option>";
	    			}
	    			$("#sel_version").html(optionHtml);
	    		});
	    	}	    	
	    });
	    
	    
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("default_id"); 			
 			var curValue = $(this).attr("cur_value");
 			var setValue = $(this).attr("set_value");
 			var postUrl = "${rc.contextPath}/auth/store/client/modifydynamiccodestatus.json";
 			var confirmMsg = "";
 			if(curValue == -1){
 				confirmMsg = "恢复";
 			}else{
 				confirmMsg = "删除";
 			}
	 		bootbox.confirm(confirmMsg+"【id为"+ id +"】当前记录?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": setValue});
	 			posting.error(function(resp){
	 				if(resp.status >=400 || resp.status <500){
	 					alert("请检查一下你的访问地址.HttpCode:" + resp.status + ", Info:" + resp.statusText);
	 				}else if(errMsg.status >= 500){
	 					alert("服务器错误.HttpCode:" + resp.status + ", Info:" + resp.statusText);
	 				}else{
	 					alert("未知异常.HttpCode:" + resp.status + ", Info:" + resp.statusText);
	 				}
	 				console.log(resp);
	 			});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{	 					
	 					alert("操作成功，已" + confirmMsg + "当前记录!");
	 					var fontCtl = $("#status_font_"+ id);
	 					$(eventCtl).attr("set_value", curValue);
	 					$(eventCtl).attr("cur_value", setValue);	 					
	 					if(setValue == 0){ 					
	 						$(eventCtl).attr("class", "btn delete btn-danger");
	 						$(eventCtl).html("删除");
	 						$(fontCtl).attr("color", "green");
	 						$(fontCtl).html("√");
	 					}else{
	 						$(eventCtl).attr("class", "btn delete btn-success");
	 						$(eventCtl).html("恢复");
	 						$(fontCtl).attr("color", "red");
	 						$(fontCtl).html("X");
	 					}
	 				}
	 			});
			});
		});
	</script>
</html>