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
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>设备型号列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/client/addplatform" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">平台型号</span>
                                    <input class="span2" name="platform" type="text" placeholder="平台型号" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">型号描述</span>
                                    <input id="input_app_name" class="span4" name="content" type="text" placeholder="型号描述" value="">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">默认型号</span>
                                    <input id="input_app_name" class="checkbox" name="defaultStatus" type="checkbox" placeholder="型号描述" value="1">
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
                    </div>
                    <div class="table-content">                    
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>平台型号</th>
                                    <th>描述</th>
                                    <th>默认型号</th>
                                    <th>状态</th>
                                    <th>修改时间</th>
                                    <th>入库时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>                      	
                                        <td>
                                        	${value.platform}
                                        </td> 
                                        <td>
                                        	${value.content}
                                        </td>
                                        <td>
                                        	<#if value.defaultStatus == 1>
                                    			<span class="label label-success">√</span>
                                    		<#else>
                                    			<span class="label label-danger" disabled>X</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if value.defaultStatus == 0>
                                    			<span class="label label-success">√</span>
                                    		<#else>
                                    			<span class="label label-danger" disabled>X</span>
                                    		</#if>
                                        </td>
                                        <td>
                                            <#if value.updateTime ??>
                                            ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.createTime ??>
                                            ${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                        	<button id="btn_default_${value.id?c}" class="btn btn-success defaultdelete" reverId="btn_normal_${value.id?c}" defaultId="${value.id?c}" platform="${value.platform!}" defaultStatus="1" <#if value.defaultStatus == 1>disabled</#if>>
                                        		默认型号
                                			</button>
                                        	<button id="btn_normal_${value.id?c}" class="btn btn-danger defaultdelete" reverId="btn_default_${value.id?c}" defaultId="${value.id?c}" platform="${value.platform!}" defaultStatus="0" <#if value.defaultStatus != 1>disabled</#if>>
                                        		非默认型号
                                			</button>
                                        </td>
                                        <td>
                                        	<button id="btn_recover_${value.id?c}" class="btn btn-success delete" reverId="btn_delete_${value.id?c}" defaultId="${value.id?c}" platform="${value.platform!}" status="0" <#if value.status == 0>disabled</#if>>
                                        		恢复                             	
                                			</button>
                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" reverId="btn_recover_${value.id?c}" defaultId="${value.id?c}" platform="${value.platform!}" status="-1" <#if value.status != 0>disabled</#if>>
                                        		删除
                                			</button>
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
     <script>
 		$("button.defaultdelete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var id = $(this).attr("defaultId");
 			var reverId = $(this).attr("reverId");
 			var reverIdCtl = $("#" + reverId);
 			var platform = $(this).attr("platform");
 			var defaultStatus = $(this).attr("defaultStatus"); 			 			
 			var action = defaultStatus == 1 ? "":"不再";
 			var type = 1;//1 platform 2:osversion;
 			var postUrl = "${rc.contextPath}/auth/client/modifyplatformosversion";
 			var infoMsg="将设备型号" + platform + action + "设为默认设备型号";
	 		bootbox.confirm("是否" + infoMsg + "?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {"id": id, "type": type, "defaultStatus": defaultStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					$(reverIdCtl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					alert("操作成功，已"+ infoMsg + "!");
	 				}
	 			});
			});
		}); 
		
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this); 			
 			var id = $(this).attr("defaultId");
 			var reverId = $(this).attr("reverId");
 			var reverIdCtl = $("#" + reverId);
 			var platform = $(this).attr("platform"); 			
 			var type = 1;//1 platform 2:osversion;
 			var status = $(this).attr("status");
 			var postUrl = "${rc.contextPath}/auth/client/modifyplatformosversion"; 			
 			var action = status == 0 ? "恢复":"删除";
 			var infoMsg=action + platform + "这条记录";
 			
	 		bootbox.confirm("是否" + infoMsg + "?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {"id": id, "type": type, "status": status});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					$(reverIdCtl).removeAttr("disabled");
	 					$(eventCtl).attr("disabled", "disabled");
	 					alert("操作成功，已" + infoMsg + "!");
	 				}
	 			});
			});
		}); 
    </script>
</body>
</html>