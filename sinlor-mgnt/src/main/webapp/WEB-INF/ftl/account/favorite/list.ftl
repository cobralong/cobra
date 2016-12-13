<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
		<title>应用汇-管理后台-WebClip用户反馈列表</title>
		<#include "lib/base_source.ftl">		
		<#include "ftl_macro.ftl">
	</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>WebClip用户反馈列表</li>
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
                                	<span class="add-on">Uid</span>
                                	<input name="uid" value="" type="input"/>
                                </div>
							   	<span class="add-on">开始时间</span>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
							      <input class="span2" id="startDateString" name="st" size="16" type="text" value="${para.st!}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <span class="add-on">结束时间</span>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd" data-date-autoclose="true">
							      <input class="span2" id="endDateString" name="et" size="16" type="text" value="${para.et!}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
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
                    <div class="table-content">
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>用户Id</th>
                                    <th>用户反馈</th>
                                    <th>联系方式</th>
                                    <th>反馈时间</th>
                                    <th>回复内容</th>
                                    <th>回复状态</th>
                                    <th>操作</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<td>${value.uid}</td>
                                    	<td>${value.feedback}</td>
                                    	<td>${value.contact}</td>
                                        <td>
                                        	${ value.updateTime?string("yyyy-MM-dd hh:mm:ss") }
                                        </td>
                                        <td>
                                        	${value.responses}
                                        </td>
                                        <td>
	                                    	<#if value.responses??>
	                                    		<font id="status_font_${value.id}" color="green">√</font>
	                                		<#else>
	                                			<font id="status_font_${value.id}" color="red">X</font>
	                                		</#if>
	                                    </td>
                                        <td>
	                                    	<#if value.status != -1>
	                                			<span default_id = ${value.id} uid = "${value.uid}" pre_status = "${value.status}"  set_status = "-1" set_text="删除" class="btn delete btn-danger">删除</span>
	                                		<#else>
	                                			<#if value.responses??>
	                                				<span default_id = ${value.id} uid = "${value.uid}" pre_status = "${value.status}" set_status= "1" set_text = "回复" class="btn delete btn-success">正常</span>
	                                			<#else>
	                                				<span default_id = ${value.id} uid = "${value.uid}" pre_status = "${value.status}" set_status= "0" set_text = "正常" class="btn delete btn-success">正常</span>
	                                			</#if>
	                                		</#if>
                                        </td>
                                        <td>
                                        	<a class="btn btn-info query" href="${rc.contextPath}/auth/account/feedback/detail?id=${value.id}">详情</a>
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
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    
        $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
            'date-format': 'yyyy-mm-dd',
            stepYear: 1,
            stepMonth: 1,
            stepDay: 1,
            startView: 2,
            minView: 2,
            maxView: 2           
        });

	    function modifyPage(){
	    	$("#pager_page").val(1);
	    }
	    
		$("span.btn").bind("click", function(){
			event.preventDefault();
			bootbox.setDefaults({ locale: "zh_CN" }); 
			var id = $(this).attr("default_id");
			var preStatus = $(this).attr("pre_status");
			var setStatus =  $(this).attr("set_status");
			var text = $(this).attr("set_text");
			var uid = $(this).attr("uid");
			
			var trCtrl = $("#tr_" + id);
			bootbox.confirm("确认将用户【" + uid  +"】的反馈状态改为【" + text + "】状态？", function(result) {
	 			if(!result){
	 				return ;
	 			}
				var postUrl = "${rc.contextPath}/auth/account/feedback/status/modify.json";
				var postData = "id=" + id + "&status=" + setStatus;
				var posting = $.post(postUrl, postData);
				posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
	 					alert("修改成功.");
	 					$(trCtrl).remove();
 					}
 				});
			});
		});
	</script>
</body>
</html>