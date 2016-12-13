<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-AppStore管理后台</title>
		<#include "lib/base_source.ftl">		
		<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/switchery.min.css">
		<style type="text/css">
			.small_sel{
				width: 120px;
			}
		</style>
	</head>	
	<body>
		<#include "/lib/store_header.ftl">
		<div>
	        <ul class="breadcrumb">
	            <li>AppStore管理后台</li><span class="divider">/</span>
	            <li>客户端动态代码文件管理</li>
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
	                <div class='main-content'>
	                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
	                    <div id="div_add" class="well form-inline">
	                        <div class='main-action'>
	                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/store/client/addconf" method="post">
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
	                            	</div></br></br>
	                                <div class="input-prepend">
	                                    <span class="add-on">应用类型</span>
	                                     <@spring.formSingleSelect "para.appModel", models, " " />
	                            	</div>
	                            	<div class="input-prepend">
	                                    <span class="add-on">本地授权安装</span>
	                                    <select name="localAuthInstallSupport" value="${para.localAuthInstallSupport}" style="width:100px"/>
										    <option value="1" selected>支持</option>
										    <option value="0">不支持</option>
										</select>
	                            	</div>
	                            	<div class="input-prepend">
	                                    <span class="add-on">引荐应用支持</span>
	                                    <select name="promoteAppSupport" value="${para.promoteAppSupport}" style="width:100px"/>
										    <option value="1" selected>支持</option>
										    <option value="0">不支持</option>
										</select>
	                            	</div>
	                            	<br/><br/>
	                                <div class="input-prepend">
	                                    <span class="add-on">Browser Url:</span>
	                                     <input type="text" name="browserUrl" class="span3" value="${para.browserUrl}">
	                            	</div>
	                                <div class="input-prepend">
	                                    <span class="add-on">WebClip Url:</span>
	                                     <input type="text" name="webClipUrl" class="span3" value="${para.webClipUrl}">
	                            	</div>
	                                <div class="input-prepend">
	                                    <span class="add-on">WebClip 描述:</span>
	                                     <input type="text" name="webClipDesc" class="span3" value="${para.webClipDesc}">
	                            	</div>
	                            	<br/><br/>
	                                <div class="input-prepend">
	                                    <span class="add-on">默认展示模块</span>
	                                     <@spring.formSingleSelect "para.index", btnItems, " " />
	                            	</div>
	                                <div class="input-prepend">
	                                    <span class="add-on">首页样式</span>
	                                     <@spring.formSingleSelect "para.homeModel", homePageModels, " " />
	                            	</div>
	                                <div class="input-prepend">
	                                    <span class="add-on">分享网页</span>
	                                     <input type="text" name="shareUrl" class="span3" value="${para.shareUrl}">
	                            	</div>
	                                <div class="input-prepend">
	                                    <span class="add-on">配置权重</span>
	                                     <input type="text" name="weight" class="span2" value="${para.weight}">
	                            	</div>
	                                <button class="btn btn-info add" >
	                                    <i class="icon-add icon-white"></i>增加
	                                </button>
	                                <div id="addInfoDiv" errMsg="${errMsg}">
	                                	<#if errMsg??>
	                        				<font>${errMsg}</font>
	                        			</#if>
	                                </div>
	                            </form>
	                        </div>
	                    </div>
	                    <div class="table-content">           
	                    <#-- table -->
	                        <form id="itemForm" name="itemForm" action="" method="post">
	                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
	                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
	                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">         
	                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
	                                <thead>
		                                <tr>
		                                    <th>Id</th>
		                                    <th>客户端</th>
		                                    <th>客户端模型</th>
		                                    <th>首页样式</th>	                                    
		                                    <th>默认展示页面</th>
		                                    <th>本地授权安装</th>
		                                    <th>引荐应用支持</th>
		                                    <th>浏览器模型地址</th>
		                                    <th>WebClip地址</th>
		                                    <th>WebClip描述</th>
		                                    <th>分享网页</th>
		                                    <th>配置权重</th>
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
	                                        <td>
	                                        	<select id="sel_appmodel_${value.id}" tr_name="客户端样式" name="appModel" default_id="${value.id}" pre_value="${value.appModel}" client_version = "${value.clientVersion}" class="small_sel">
			                                		<#list models?keys as key>
			                                			<#if key == value.appModel>
			                                				<option value="${key}" selected>${models[key]}</option>
			                                			<#else>
			                                				<option value="${key}">${models[key]}</option>
			                                			</#if>
			                                			
			                                		</#list>
			                                	</select>
	                                        </td>
	                                        <td>
	                                        	<select id="sel_showmodel_${value.id}" tr_name="首页样式" name="homeModel" default_id="${value.id}" pre_value="${value.homepageShowModel}" client_version = "${value.clientVersion}" class="small_sel">
			                                		<#list homePageModels?keys as key>
			                                			<#if key == value.homepageShowModel>
			                                				<option value="${key}" selected>${homePageModels[key]}</option>
			                                			<#else>
			                                				<option value="${key}">${homePageModels[key]}</option>
			                                			</#if>
			                                		</#list>
			                                	</select>
	                                        </td>
	                                        <td>
	                                        	<select id="sel_index_search_${value.id}" tr_name="默认展示样式" name="index" default_id="${value.id}" pre_value="${value.buttomDefaultItemIndex}" client_version = "${value.clientVersion}" class="small_sel">
			                                		<#list btnItems?keys as key>
			                                			<#if key == value.buttomDefaultItemIndex>
			                                				<option value="${key}" selected>${btnItems[key]}</option>
			                                			<#else>
			                                				<option value="${key}">${btnItems[key]}</option>
			                                			</#if>
			                                			
			                                		</#list>
			                                	</select>
	                                        </td>
	                                        <td><#if value.localAuthInstallSupport == 1>
											<button id="btn_local_auth_install_support_${value.id?c}"
												class="btn btn-success modify" default_id="${value.id?c}"
												set_value="0" cur_value="1" flag="0">支持</button> <#else>
											<button id="btn_local_auth_install_support_${value.id?c}"
												class="btn btn-danger modify" default_id="${value.id?c}"
												set_value="1" cur_value="0" flag="0">不支持</button> </#if>
											</td>
											<td><#if value.promoteAppSupport == 1>
											<button id="btn_promote_app_support_${value.id?c}"
												class="btn btn-success modify" default_id="${value.id?c}"
												set_value="0" cur_value="1" flag="1">支持</button> <#else>
											<button id="btn_promote_app_support_${value.id?c}"
												class="btn btn-danger modify" default_id="${value.id?c}"
												set_value="1" cur_value="0" flag="1">不支持</button> </#if>
											</td>
	                                        <td class="edit_td" default_id="${value.id?c}" for="p_bu_${value.id?c}" name="browserUrl" client_version = "${value.clientVersion}">
	                                        	<label for="name" class="span3">
	                                        	<span id="p_bu_${value.id?c}" class="text-info span3" default_id = "${value.id?c}" pre_value="${value.browserUrl!}">
	                                        		${value.browserUrl!}
	                                        	</span>
	                                        	</label>
	                                        </td>	                                        
	                                        <td class="edit_td" default_id="${value.id?c}" for="p_wu_${value.id?c}" name="webClipUrl" client_version = "${value.clientVersion}">
	                                        	<label for="name" class="span3">
	                                        	<span id="p_wu_${value.id?c}" class="text-info span3" default_id = "${value.id?c}" pre_value="${value.webClipUrl!}">
	                                        		${value.webClipUrl!}
	                                        	</span>
	                                        	</label>
	                                        </td>	                                        
	                                        <td class="edit_td" default_id="${value.id?c}" for="p_wd_${value.id?c}" name="webClipDesc" client_version = "${value.clientVersion}">
	                                        	<label for="name" class="span3">
	                                        	<span id="p_wd_${value.id?c}" class="text-info span4" default_id = "${value.id?c}" pre_value="${value.webClipDesc!}">
	                                        		${value.webClipDesc!}
	                                        	</span>
	                                        	</label>
	                                        </td>
	                                        <td class="edit_td" default_id="${value.id?c}" for="p_su_${value.id?c}" name="shareUrl" client_version = "${value.clientVersion}">
	                                        	<label for="name" class="span3">
	                                        	<span id="p_su_${value.id?c}" class="text-info span3" default_id = "${value.id?c}" pre_value="${value.shareUrl!}">
	                                        		${value.shareUrl!}
	                                        	</span>
	                                        	</label>
	                                        </td>
	                                        <td class="edit_td" default_id="${value.id?c}" for="p_wg_${value.id?c}" name="weight" client_version = "${value.clientVersion}">
	                                        	<label for="name" class="span2">
	                                        	<span id="p_wg_${value.id?c}" class="text-info span3" default_id = "${value.id?c}" pre_value="${value.weight?c}">
	                                        		${value.weight?c}
	                                        	</span>
	                                        	</label>
	                                        </td>
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
	     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#itemForm",currPageDiv,totalDiv,sizeDiv);
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
	    
	    $(document).on( 'change', 'select.index_choose', function(){
	    	var selCtr = $(this);
	    	var id = $(this).attr('conf_id');
	    	var preConfIndex = $(this).attr('conf_inex');
	    	var index = $(this).val();
	    	var postUrl = "${rc.contextPath}/auth/store/client/modifyconf";
 			var posting = $.post(postUrl, {'id': id, "index": index});
 			posting.done(function(errMsg){
 				if(errMsg){//
 					//show msg
 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
 					$(selCtr).attr("conf_inex", preConfIndex);
 					$(selCtr).val(preConfIndex);
 				}else{
 					alert("修改成功.");
 				}
 			});
	    });
		
 		$('.edit_td').click(function() {
 			var id = $(this).attr("default_id");
 			var clientVersion = $(this).attr("client_version");
 			var forCtr = $(this).attr("for");
 			var preValue = $("#" + forCtr).attr("pre_value");
 			var paraName = $(this).attr("name"); 			
 			var input = $('<input id="attribute" type="text" class="span2" pre_value="' + preValue + '" value="' + preValue + '" />')
 			$("#" + forCtr).text('').append(input);
 			input.select();
 			input.blur(function() {
 				var preValue = $(this).parent().attr("pre_value");				
 				var curValue = $('#attribute').val();
 				if(curValue == preValue){
		   			$('#attribute').parent().text(preValue);
		   			$('#attribute').remove();
	   				return;
	   			}
 				var typeName =  paraName == "weight" ? "配置权重" : "地址";
 				bootbox.confirm("将版本【"+clientVersion+"】的"+typeName+"由【" + preValue + "【改为】" + curValue + "】?", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/auth/store/client/modifyconf";
 						var postData = "id=" + id + "&" + paraName + "=" + encodeURIComponent(curValue);
			 			var posting = $.post(postUrl, postData);
			 			posting.done(function(errMsg){
			 				if(errMsg){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
					   			$('#attribute').parent().text(preValue);
					   			$('#attribute').remove();
			 				}else{
			 					alert("修改成功.");
					   			$('#attribute').parent().text(curValue);
					   			 $('#p_' + id).attr("pre_value", curValue);
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
	    
	    
	    $(document).on( 'change', 'select.small_sel', function(){
	    	var selCtr = $(this);
	    	var id = $(this).attr('default_id');
	    	var ctrlId = $(this).attr("id");
	    	var preValue = $(this).attr('pre_value');
	    	var paraName = $(this).attr("name");
	    	var trName = $(this).attr("tr_name");
	    	var clientVersion = $(this).attr("client_version");
	    	var curValue = $(this).val();
	    	var preDesc = "";
	    	$("#" + ctrlId + " option").each(function(){
	    		if(preValue == $(this).val()){
	    			preDesc = $(this).text();
	    			return false;
	    		}
	    	});
	    	var curDesc = $("#" + ctrlId + " option:selected").text();
	    	bootbox.confirm("将版本【"+clientVersion+"】的" + trName + "由【" + preDesc + "】改为【" + curDesc + "】?", function(result) {
	    		if(!result){return;}
		    	var postUrl = "${rc.contextPath}/auth/store/client/modifyconf";
		    	var postData = "id=" + id + "&" + paraName + "=" + curValue;
	 			var posting = $.post(postUrl, postData);
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(selCtr).attr("pre_value", preValue);
	 					$(selCtr).val(preValue);
	 				}else{
	 					$(selCtr).attr("pre_value", curValue);
	 					alert("修改成功.");
	 				}
	 			});
	 		});
	    });
	    
	    $("button.modify").bind("click", function() {
		    event.preventDefault();
		    bootbox.setDefaults({
		        locale: "zh_CN"
		    });
		    var eventCtl = $(this);
		    var id = $(this).attr("default_id");
		    var trCtl = $("#tr_" + id);
		    var curValue = $(this).attr("cur_value");
		    var setValue = $(this).attr("set_value");
		    var flag = $(this).attr("flag");
		    var postUrl = "${rc.contextPath}/auth/store/client/modifyconf";
		    var installMsg = curValue == 1 ? "是否取消客户端对本地授权安装的支持?" : "是否添加客户端对本地授权安装的支持?";
		    var promoteMsg = curValue == 1 ? "是否取消客户端对引荐应用插入的支持?" : "是否添加客户端对引荐应用插入的支持?";
		    var actionMsg = flag==1 ? promoteMsg : installMsg;
		    bootbox.confirm(actionMsg, function(result) {
		        if (!result) {
		            return;
		        }
		        var posting = flag==1 ? $.post(postUrl, {'id': id,"promoteAppSupport": setValue}): $.post(postUrl, {'id': id,"localAuthInstallSupport": setValue});
		        posting.done(function(errMsg) {
		            if (errMsg) { //
		                //show msg
		                alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
		            } else {
		                var installResMsg = curValue == 1 ? "已取消客户端对本地授权安装的支持!" : "已添加客户端对本地授权安装的支持!";
		                var promoteResMsg = curValue == 1 ? "已取消客户端对引荐应用插入的支持!" : "已添加客户端对引荐应用插入的支持!";
		                var responseMsg = flag==1 ? promoteResMsg : installResMsg;
		                alert(responseMsg);
		                trCtl.remove();
		                location.reload();
		            }
		        });
		    });
		});
    </script>
	</body>
</html>