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
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>轮播图列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="">渠道</span>
                                     <@spring.formSingleSelect "para.channel", channels, " " />
                                </div>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <span class="">开始时间</span>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <input class="span2" id="search_startDateString" name="st" size="16" type="text" value="${para.st}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <span class="">结束时间</span>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <input class="span2" id="endDateString" name="et" size="16" type="text" value="${para.et}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
                                <button class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                                <button id="btn_now_promote" class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>正在推广
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
		                <#if para.status == 0>	
		                	<p>在<b><font color="green">${para.st}</font></b>时间在进行展示的应用列表:</p>
		                </#if>
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>RootID</th>
                                    <th>Icon</th>
                                    <th>Rank</th>
                                    <th>渠道</th>
                                    <th>状态</th>
                                    <th>图片</th>
                                    <th>下载地址</th>
                                    <th>开始时间</th>
                                    <th>结束时间</th>
                                    <th>授权下载状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<input id="hidden_${value.id}" type="hidden" app_id = "${value.id!}" app_title="${rootSimple.name}" app_channel="${value.channel}" promote_status="${value.status}"/>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                        <td class="edit_td" pid="${value.id?c}">
                                        	<label for="name" class="span1"><span id="p_${value.id?c}" class="text-info" preValue="${value.rank?c}">${ value.rank! }</span></label>
                                        </td>
                                        <td>${ value.channel! }</td>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span id="status_span_${value.id}" class="btn btn-success" disabled>正常</span>
                                    		<#else>
                                    			<span id="status_span_${value.id}" class="btn btn-danger" disabled>删除</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<a href="${value.bannerUrl}" target="_blank"><img style="width:150px; height:70px" src="${value.bannerUrl}" alt="${value.bannerUrl}"/></a>
                                        </td>
                                        <td>
                                        	<@hrefipaplist rootSimple.downloadUrl!''/>
                                        </td>
                                        <td>
                                            <#if value.startTime ??>
                                            ${ value.startTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td class="edit_time_td" pid="${value.id?c}">
                                        	<span id="p_time_${value.id?c}" actionChannel = "${value.channel!}" preValue="${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }">${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }</span>
                                        </td>
                                        <td>${rootIdBoughtMap[value.rootId?c]}</td>
                                        <td>
                                        	<button id="promote_btn_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " app_id = "${value.id!}"  <#if value.status == 0>disabled</#if>>
                                        		推荐                                        	
                                			</button>
                                        	<button id="unpromote_btn_${value.id}" class="btn btn-danger delete" app_id = "${value.id!}"  <#if value.status != 0>disabled</#if>>
                                        		停止
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
	</div>
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    
 		$("#btn_now_promote").bind("click",function(){
 			$("#search_startDateString").val("");
 		});
 		$("button.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("app_id");
 			var trCtl = $("#tr_"+id);
 			var hidden = $("#hidden_"+id);
 			var appTitle = $(hidden).attr("app_title");
 			var appChannel = $(hidden).attr("app_channel");
 			var postUrl = "${rc.contextPath}/auth/banner/modify";
 			var preStatus = $(hidden).attr("promote_status");
 			var actionMsg = preStatus == 0 ? 
 										"是否将应用【【"+appTitle+"】】从"+appChannel+"渠道的轮播列表中移除?" :
 										"是否将应用【【"+appTitle+"】】添加到"+appChannel+"渠道的轮播列表?"; 			
 			var setStatus = preStatus == 0 ? -1 : 0;
 			
	 		bootbox.confirm(actionMsg, function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var posting = $.post(postUrl, {'id': id, "status": setStatus});
	 			posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 				}else{
		 				var responseMsg = preStatus == 0 ? 
								"成功将应用【【"+appTitle+"】】从"+appChannel+"渠道的轮播列表中移除!" :
								"成功将应用【【"+appTitle+"】】添加到"+appChannel+"渠道的轮播列表!";
	 					alert(responseMsg);	 					
	 					trCtl.remove();
	 				}
	 			});
			});
		});
		
 		$('.edit_td').click(function() {
 			var pid = $(this).attr("pid");
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
 				bootbox.confirm("将应用"+appTitle+"的Banner推荐位置由"+preValue+"------>"+curValue+"?(当前值并不一定为其所在位置，具体位置请修改后刷新页面查看位置项.)", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/auth/banner/modify";
			 			var posting = $.post(postUrl, {'id': pid, "rank": curValue});
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
 			var preValue = $('#p_time_'+pid).attr("preValue");
 			var actionChannel = $('#p_time_'+pid).attr("actionChannel");
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
 				bootbox.confirm("将应用"+appTitle+"的Banner推荐时间由"+preValue+"------>"+curValue+"?", function(result) {
		 			if(result){
		 				bootbox.confirm("同时将应用"+appTitle+"的Banner推荐时间由"+preValue+"------>"+curValue+"作用于所有渠道!点击'取消'将只作用于" + actionChannel + ",否则将用于所有渠道?", function(result) {
		 					var allChannel = 0;
		 					if(result){
		 						allChannel = 1;
		 					}
	 						var postUrl = "${rc.contextPath}/auth/banner/modify";
				 			var posting = $.post(postUrl, {'id': pid, "endTime": curValue, "allChannel": allChannel});
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
		 				});
		 			}else{
			   			$('#timeattribute').parent().text(preValue);
			   			$('#timeattribute').remove();
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