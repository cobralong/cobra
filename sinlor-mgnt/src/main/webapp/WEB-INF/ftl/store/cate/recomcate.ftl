<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/switchery.min.css">
<title>应用汇-AppStore管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
	<#include "/lib/store_header.ftl">
	
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>推荐分类列表</li>
        </ul>
    </div>
    <div class="container-fluid">
    	<form id="form" method="post" target="resultFrame"></form>
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <#if data??>
                    	<form action="${rc.contextPath}/auth/store/cate/modifyrecomcate" method="post">
							<div class="control-group">
								<input id="cate_id" type="hidden" name="id" value="${data.id?c}"/>
								<span>推荐名称:</span>
								<input id="cate_name" type="text" name="name" style="width:220px; " value="${data.name!}"/>
								<span >展示颜色:</span>
								<input type="color" name="color" style="width:220px; " value="#${data.color}"/>
								<span>推荐分类:</span>
								<select id="sel_type_${data.id}" name="type" preSel = "${data.type?c}" class="index_choose">
	                        		<#list types?keys as key>
	                        			<#if key == data.type>
	                        				<option value="${key}" selected>${types[key]}</option>
	                        			<#else>
	                        				<option value="${key}">${types[key]}</option>
	                        			</#if>	                        			
	                        		</#list>
                        		</select>
								<span>展示阶段:</span>
								<select id="sel_show_${data.id}" name="showInAudit" class="index_choose">
	                        		<#list shows?keys as key>
	                        			<#if key == data.showInAudit>
	                        				<option value="${key}" selected>${shows[key]}</option>
	                        			<#else>
	                        				<option value="${key}">${shows[key]}</option>
	                        			</#if>	                        			
	                        		</#list>
                        		</select>
							</div>
							<div class="control-group">
								<span>审核列表中展示位置:</span>
								<input class="span1" type="text" name="auditingPosition" value="${data.auditingPosition}"/>								
								<span>用户列表中展示位置:</span>								
								<input class="span1" type="text" name = "auditedPosition" value="${data.auditedPosition!}"/>
								<span>记录状态</span>
								<select id="sel_status_${data.id}" name="status" defaultId = "${data.id?c}" class="index_choose">
	                        		<#list status?keys as key>
	                        			<#if key == data.status>
	                        				<option value="${key}" selected>${status[key]}</option>
	                        			<#else>
	                        				<option value="${key}">${status[key]}</option>
	                        			</#if>	                        			
	                        		</#list>
                        		</select>				
	                            <button class="btn btn-info add" >
	                                <i class="icon-search icon-white"></i>修改
	                            </button>
                            <div id="addInfoDiv" errMsg="${errMsg}">
                            	<#if errMsg??>
                            		<font>${errMsg}</font>
                            	</#if>
                            </div>
							</div>
						</form>
							<div class="control-group">
								<label class="control-label">推荐的应用类分类:</label>								
								<#if subRecoms??>
									<table class="table table-striped table-bordered table-condensed" id="itemTable">
			                            <thead>
				                            <tr>
				                            	<th>推荐</th>
				                                <th>名称</th>
				                                <th>位置</th>
				                            </tr>
			                            </thead>
			                            <tbody class="sortable">
			                            	<#list subRecoms as value>			                            		
			                            		<tr>
			                            			<td style="width:5%">
			                            				<input  id="td_checkbox_${value.id}" for="input_position_${value.id}" type="checkbox" name="checked" default_id="${value.id?c}" desc="${value.name!}" <#if value.checked>preChecked="1"<#else>preChecked="0"</#if> <#if value.checked>checked</#if> value="1">
			                            			</td>
				                                    <td>${ value.name! }</td>
				                                    <td>				                                    	
				                                    	<input id="input_position_${value.id}" type="input" name="position" cur_value = "${value.position}" default_id="${value.id?c}" desc="${value.name!}"
				                                    		<#if !value.checked || data.status != 0>disabled</#if> value="${value.position}">
				                                    </td>
			                                	</tr>
			                                </#list>
			                            </tbody>
		                        	</table>
		                    	</#if>
							</div>
                        </#if>
                </div><!-- end div class main-content-->
            </div><!-- end div class container span12-->
        </div><!-- end div class row-fluid-->
    </div><!-- end div class container-fluid-->
</div><!-- end div class whole-container-->
</body>
<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
<script type="text/javascript" src='${rc.contextPath}/js/switchery.min.js'></script>
<script type="text/javascript">
		$("select[name='type']").bind("change", function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var selectCtl = $(this);
 			var preSel = $(this).attr("preSel");
 			bootbox.confirm("请注意，你正在修改此推荐的展示分类型，若进行修改，则会删除掉先前进行的子分类推荐？若要继续，请点击确认再点击修改按钮！", function(result){
 				if(!result){
 					$(selectCtl).val(preSel);
 					return;
 				}
 			});
		});
		$("input[type='checkbox']").bind("click", function(){
			var eventCtrl=$(this);
			var forCtrl = $("#" + $(this).attr("for"));
			if($(this).is(':checked')){
				$(forCtrl).prop("disabled",false);
			}else{
				$(forCtrl).prop("disabled",true);
				var defaultId = $(eventCtrl).attr("default_id");
				var subCateName = $(eventCtrl).attr("desc");
				var cateName = $("#cate_name").val();			
				var cateId = $("#cate_id").val();
				var actionMsg="是否将子分类【" + subCateName + "】在推荐分类【" + cateName + "】的推荐子分类列表中移除？";
				bootbox.confirm(actionMsg, function(result) {
				if(!result){
					$(ctrl).val(curValue);
					return;
				}
				var postUrl = "${rc.contextPath}/auth/store/cate/modifysubcate";
	 			var posting = $.post(postUrl, {'id': cateId, "subCateId": defaultId, "checked": false});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+ errMsg);		 					
		 				}else{
		 					alert("操作成功");
		 				}
		 				location.reload();
		 			});
				
			});
			}
		});
		
		$("input[id^='input_position']").bind("blur", function(){
			var ctrl =  $(this);
			var defaultId = $(ctrl).attr("default_id");
			var curValue = $(ctrl).attr("cur_value");
			var setValue = $(ctrl).val();
			if(setValue == "" || setValue == curValue){
				return;
			}
			var cateName = $("#cate_name").val();			
			var cateId = $("#cate_id").val();
			var subCateName = $(this).attr("desc");
			var actionMsg = "将子分类【" + subCateName + "】在推荐分类【" + cateName + "】的位置由" + curValue + "改为" + setValue+"?此处位置会根据现有位置对自己和其它子分类的位置动态调整,请查看页面最后显示位置。";
			bootbox.confirm(actionMsg, function(result) {
				if(!result){
					$(ctrl).val(curValue);
					return;
				}
				var postUrl = "${rc.contextPath}/auth/store/cate/modifysubcate";
	 			var posting = $.post(postUrl, {'id': cateId, "subCateId": defaultId, "position": setValue, "checked": true});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+ errMsg);		 					
		 				}else{
		 					alert("操作成功");
		 				}
		 				location.reload();
		 			});
				
			});
			
		});
		
		$("input[type='button']").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var checkBoxCtl = $(this);
 			var id = $(this).attr("defaultId");
 			var subCateId = $(this).attr("subCateId");
 			var subCateName= $(this).attr("subCateName");
 			var cateName= $(this).attr("cateName");
 			var checkboxCtl = $("#td_checkbox_"+subCateId);
 			var inputCtl = $("#input_position_"+subCateId);
 			var checked = $(checkboxCtl).is(':checked'); 			
 			var preCheckedValue = $(checkboxCtl).attr('preChecked');
 			var preChecked = preCheckedValue == 1 ? true : false;
			var position = $(inputCtl).val();
			var prePosition = $(inputCtl).attr("prePosition");
 			if(checked){
 				actionMsg="你正在将【"+ subCateName +"】添加到分类【" + cateName + "】的子推荐分类中";
 			}else{
 				actionMsg="你正在将【"+ subCateName +"】从分类【" + cateName + "】的子推荐分类中移除";
 			}
	 		bootbox.confirm(actionMsg, function(result) {
	 			if(!result){
 					$(checkboxCtl).prop("checked", preChecked);
 					$(inputCtl).val(prePosition)
 					return;
	 			}
	 			var postUrl = "${rc.contextPath}/auth/store/cate/modifysubcate";
	 			var posting = $.post(postUrl, {'id': id, "subCateId": subCateId, "position": position, "checked": checked});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+ errMsg);
		 					if(checked){
 								$(checkboxCtl).attr("preChecked", 1);
 							}else{
 								$(checkboxCtl).attr("preChecked", 0); 								
 							}
 							$(checkboxCtl).prop("checked", preChecked);
 							$(inputCtl).attr("prePosition", position)
		 				}else{
		 					alert("操作成功");
		 				}
		 			});
 			});
		});
		
		</script>
</html>