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
            <li>分发策略列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<button class="btn queryToggle  btn-info">
                		<span class="glyphicon glyphicon-search"></span>查询框
                	</button>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="">渠道</span>
                                	<select id="sel_root_channel_search" name="rc" type="search">
                                		<#list topChannels?keys as key>
                                			<option value="${key}">${topChannels[key]}</option>
                                		</#list>
                                	</select>
                                	
                                </div>
                                <div id="div_first_channel_search" class="input-prepend">
                                </div>
                                <div id="div_second_channel_search" class="input-prepend">
                                </div>
                                <div id="div_leaf_channel_search" class="input-prepend">
                                </div>
                                <br/>
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>查询
                                </button>
                            </form>
                        </div>
                    </div>
                    <br/>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="searchForm" action="${rc.contextPath}/auth/account/adddispatchconfigure">		                      
                                <div class="input-prepend">
                                	<span class="">渠道</span>
                                	<select id="sel_root_channel_add" name="rc" type='add'>
                                		<#list topChannels?keys as key>
                                			<option value="${key}">${topChannels[key]}</option>
                                		</#list>
                                	</select>
                                	
                                </div>
                                <div id="div_first_channel_add" class="input-prepend">
                                </div>
                                <div id="div_second_channel_add" class="input-prepend">
                                </div>
                                <div id="div_leaf_channel_add" class="input-prepend">
                                </div>
                                <br/>
                                <span class="add-on">开始时间</span>
							    <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <input class="span2" id="startDateString" name="st" size="16" type="text" value="${st!}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
							    <span class="add-on">结束时间</span>
							    <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
							      <input class="span2" id="endDateString" name="et" size="16" type="text" value="${et!}">
							      <span class="add-on"><i class="icon-calendar"></i></span>
							    </div>
                                <div class="input-prepend">
                                	<span class="add-on">每日分发数</span>
                                    <input id="input_text_dd" class="span1" size="10" name="dd" type="text" placeholder="分发数" value="">                                   
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">更新存在数据</span>
                                    <input id="input_checkbox_rewirte" class="checkbox" name="rw" type="checkbox" value="1" checked>
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">更新存在数据及状态</span>
                                    <input id="input_checkbox_reinit" class="checkbox" name="ri" type="checkbox" value="1" checked>
                                </div>
                                <button id="btn_add_configure" class="btn btn-info add">
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${para.errMsg}">
                                	<#if para.errMsg??>
                                		<font>${para.errMsg}</font>
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
                                	<th>分发渠道</th>
                                    <th>每日分发量</th>
                                    <th>分发开始时间</th>
                                    <th>分发结束时间</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td>
                                        	<#assign channel = channels[value.dispatchChannel?c]>
                                        	${channel}
                                        </td> 
                                        <td>
                                        	${value.dayDispatchs}
                                        </td>
                                        <td>
                                            <#if value.actionStartTime ??>
                                            ${ value.actionStartTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>  
                                        <td>
                                            <#if value.actionEndTime ??>
                                            ${ value.actionEndTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        <td>
                                        	<#if value.status == 0>
                                    			<span class="label label-success">√</span>
                                    		<#else>
                                    			<span class="label label-danger" disabled>X</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<button id="btn_recover_${value.id}" class="btn btn-success recover" configureId = "${value.id?c}"  <#if value.status == 0>disabled</#if>>
                                        		恢复分发                                   	
                                			</button>
                                        	<button id="btn_delete_${value.id}" class="btn btn-danger delete" configureId = "${value.id?c}"  <#if value.status != 0>disabled</#if>>
                                        		禁止分发
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
	   	    
	    function rootChannelChange(type){
	    	var value = $("#sel_root_channel_" + type + " option:selected").val();
    		$("#div_first_channel_"+ type).empty();
    		$("#div_second_channel_"+ type).empty();
    		$("#div_leaf_channel_"+ type).empty();
	    	if(value == -8888){
		    	var posting = $.post("${rc.contextPath}/auth/client/topchannel.json");
	 			posting.done(function(result){
	 				if(result.status == 0){
	 					var list = result.data;
	 					var selectHtml = "<select id ='sel_first_channel_"+ type +"' name='fc' type='"+ type +"'><option value='-1' leaf='1'>全部</option>";
	 					$.each(list, function(index, value){
	 						selectHtml = selectHtml.concat("<option value = '" + value.id + "' leaf = '" + value.leaf + "'>"  + value.desc + "</option>");
	 					});
	 					selectHtml = selectHtml.concat("</select>");
	    				$("#div_first_channel_"+ type).html(selectHtml);
	 				}
	 			});	    		
	    	}
	    }
	    
	    
	    function firstChannelChange(type){
	    	var value = $("#sel_first_channel_" + type + " option:selected").val();
    		$("#div_second_channel_" + type).empty();
    		$("#div_leaf_channel_" + type).empty();
	    	var leaf = $("#sel_first_channel_" + type + " option:selected").attr("leaf");
	    	if(leaf == 1){
	    		//直接子渠道
	    		return;
	    	}
	    	var posting = $.post("${rc.contextPath}/auth/client/childchannel.json", {'channelId': value});
 			posting.done(function(result){
 				if(result.status == 0){
 					var list = result.data;
 					var selectHtml = "<select id ='sel_second_channel_" + type + "' name='sc' type='"+ type +"'><option value='-1' leaf='1'>全部</option>";
 					$.each(list, function(index, value){
 						selectHtml = selectHtml.concat("<option value = '" + value.id + "' leaf = '"+ value.leaf +"'>"  + value.desc + "</option>");
 					});
 					selectHtml = selectHtml.concat("</select>");
    				$("#div_second_channel_"+ type).html(selectHtml);
 				}
 			});
	    }
	    
	    function secondChannelChange(type){
	    	var value = $("#sel_second_channel_" + type + " option:selected").val();	    	
    		$("#div_leaf_channel_"+ type).empty();
	    	var leaf = $("#sel_second_channel_" + type + " option:selected").attr("leaf"); 
	    	if(leaf == 1){
	    		//直接子渠道
	    		return;
	    	}
	    	var posting = $.post("${rc.contextPath}/auth/client/childchannel.json", {'channelId': value});
 			posting.done(function(result){
 				if(result.status == 0){
 					var list = result.data;
 					var selectHtml = "<select id ='sel_leaf_channel_" + type +"' name='lc' type='"+ type +"'><option value='-1'>全部</option>";
 					$.each(list, function(index, value){
 						selectHtml = selectHtml.concat("<option value = '" + value.id + "' leaf='"+ value.leaf +"'>"  + value.desc + "</option>");
 					});
 					selectHtml = selectHtml.concat("</select>");
    				$("#div_leaf_channel_"+ type).html(selectHtml);
 				}
 			});
	    }
	    
	    $(document).on( 'change', 'select', function(){	    
	    	var selectCtl = $(this);
	    	var id = $(selectCtl).attr('id');
	    	var type = $(selectCtl).attr('type');
	    	if(id == 'sel_root_channel_'+type){
	    		rootChannelChange(type);
	    	}else if(id == 'sel_first_channel_'+type){
	    		firstChannelChange(type);
	    	}else if(id == 'sel_second_channel_'+type){
	    		secondChannelChange(type);
	    	}
	    });
 		$("button.queryToggle").bind("click", function(){
 			$("#div_query").toggle();
 		});
 		
 		$("button.addToggle").bind("click", function(){
 			$("#div_add").toggle();
 		});
 		
 		
 		$("#addForm").submit(function(){ 		
 			var startDateString = $("#startDateString").val();
 			if(!startDateString){
 				alert("开始时间不能为空!");
 				return false;
 			}
 			var endDateString = $("#endDateString").val();
 			if(!endDateString){
 				alert("结束时间不能为空!");
 				return false;
 			}
 			var dayDispatchs = $("#input_text_dd").val();
 			if(!dayDispatchs){
 				alert("日分发量不能为空");
 				return false;
 			}else if(isNaN(dayDispatchs)){
 				alert("日分发量必须为数字");
 				return false;
 			}
 			var reinit = $("#input_checkbox_reinit").is(":checked");
 			var rewrite = $("#input_checkbox_rewirte").is(":checked");
 			if(reinit){
 				return confirm("此次增加会更新已存在数据的时间，日分发量及状态.确定？");
 			}else if(rewrite){ 			
				return confirm("此次增加会更新已存在数据的时间，日分发量但不会变更状态.确定？");
 			}
 			return true; 				
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