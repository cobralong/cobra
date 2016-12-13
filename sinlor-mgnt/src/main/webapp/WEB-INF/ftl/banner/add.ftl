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
            <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
            <div>
                <ul class="breadcrumb">
                    <li>管理后台</li><span class="divider">/</span>
                    <li>轮播图添加</li>
                </ul>
            </div>

           
            <div class="container-fluid">
                <div class="row-fluid">
                    <div class="container span12">
                        <#include "lib/alert.ftl">
                        <div class='main-content'>
                            <div class="well form-inline">       
                             <div id="addInfoDiv">
				                <#if errMsg??>
				                <font color="red">${errMsg}</font>
				                </#if>
				            </div>                     
				            <#if values??>
					            <div class="table-content">
					            	<table class="table table-striped table-bordered table-condensed" id="itemTable">
					                    <thead>
						                    <tr>
						                        <th>RootID</th>
						                        <th>Icon</th>
						                        <th>Rank</th>
						                        <th>渠道</th>
						                        <th>状态</th>
						                        <th>图片</th>
						                        <th>开始时间</th>
						                        <th>结束时间</th>
						                        <th>操作</th>
						                    </tr>
					                    </thead>
					                    <tbody class="sortable">
                                			<#list values as value>
						                        <tr id="tr_${value.id}">
						                        	<input id="hidden_${value.id}" type="hidden" app_id = "${value.id!}" app_title="${simple.name}" app_channel="${value.channel}" promote_status="${value.status}"/>
						                            <td>
						                            	<@hrefapp value.rootId, simple.name/>
						                            </td> 
						                            <td>
						                            	<@appicon simple.icon, simple.name/>
						                            </td>
						                            <td>${ value.rank! }</td>
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
						                                <#if value.startTime ??>
						                                ${ value.startTime?string("yyyy-MM-dd HH:mm:ss") }
						                                <#else>
						                                    -
						                                </#if>
						                            </td>  
						                            <td>
						                                <#if value.endTime ??>
						                                ${ value.endTime?string("yyyy-MM-dd HH:mm:ss") }
						                                <#else>
						                                    -
						                                </#if>
						                            </td>
			                                        <td>
			                                        	<button id="promote_btn_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " banner_id = "${value.id!}"  <#if value.status == 0>disabled</#if>>
			                                        		推荐                                        	
			                                			</button>
			                                        	<button id="unpromote_btn_${value.id}" class="btn btn-danger delete" banner_id = "${value.id!}"  <#if value.status != 0>disabled</#if>>
			                                        		停止
			                                			</button>
			                                        </td>
						                        </tr>
						                	</#list>
					                    </tbody>
					                </table>
					            </div>
					        </#if>
					        </div>					        
                            <div class="well form-inline">
                                <div class='main-action'>
                                    <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/banner/add" method="post">		                      
                                        <div class="input-prepend">
                                            <span class="add-on">ID</span>
                                            <input class="span2 search" name="rootId" type="text" placeholder="ID" value="${rid}">
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">应用名称</span>
                                            <input id="input_app_name" class="span2" style="width:200px" name="name" type="text" placeholder="应用名称" disabled value="">
                                            <span class="add-on">OTA包</span>
		                                    <input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="">
		                                    <input id="input_cert" class="span2" style="width:100px" name="name" type="text" placeholder="签名" disabled value="">
                                        </div>
                                        <br/>
                                        <div class="input-prepend">
                                            <span class="add-on">渠道</span>
                                     		<@spring.formSingleSelect "para.channel", channels, " " />
                                        </div>
                                        <span class="add-on">开始时间</span>
                                        <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                            <input class="span2" id="startDateString" name="st" size="16" type="text" value="">
                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>
                                        <span class="add-on">结束时间</span>
                                        <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                            <input class="span2" id="endDateString" name="et" size="16" type="text" value="">
                                            <span class="add-on"><i class="icon-calendar"></i></span>
                                        </div>								    
                                        <div class="input-prepend">
                                            <span class="add-on">位置</span>
                                            <input class="span1" size="10" name="rank" type="text" placeholder="rank" value="">                                   
                                        </div>
                                        <br/>
<!--                                        <span class="add-on">网站轮播图:</span>
                                        <div class="fileinput fileinput-new" data-provides="fileinput">
                                            <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
                                                <img data-src="holder.js/100%x100%" alt="...">
                                            </div>
                                            <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 600px; max-height: 280px;"></div>
                                            <div>
                                                <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="webBanner"></span>
                                                <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                            </div>
                                        </div>-->
                                        <br/>
                                        <span class="add-on">手机轮播图:</span>
                                        <div class="fileinput fileinput-new" data-provides="fileinput">
                                            <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
                                                <img data-src="holder.js/100%x100%" alt="...">
                                            </div>
                                            <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 600px; max-height: 280px	;"></div>
                                            <div>
                                                <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="banner"></span>
                                                <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                            </div>
                                        </div>
                                        <br/>
                                        <button class="btn btn-info add" disabled >
                                            <i class="icon-search icon-white"></i>增加
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
        <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
        <script>
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
                        $(btn).removeAttr("disabled");
                    } else {
                        $(ctl).focus();
                        $(txtCtl).val(result.message);
                        $(btn).attr("disabled", "disabled");
                    }
                });
            });
            
	 		$("button.delete").bind("click",function(){
	 			event.preventDefault();
	 			bootbox.setDefaults({
	 				locale: "zh_CN"
	 			});
	 			var eventCtl = $(this);
	 			var id = $(this).attr("banner_id");
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
            $("#startDatetimePicker, #endDatetimePicker").datetimepicker({
                timeFormat: 'yyyy-mm-dd',
                stepYear: 1,
                stepMonth: 1,
                stepDay: 1
            });


        </script>
    </body>
</html>