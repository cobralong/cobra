<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-AppStore管理后台</title>
        <#include "lib/base_source.ftl">
		<#include "ftl_macro.ftl">
    </head>
    <body>
        <div class='whole-container'>
            <#include "/lib/store_header.ftl">
            <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
            <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
            <div>
                <ul class="breadcrumb">
                    <li>AppStore管理后台</li><span class="divider">/</span>
                    <li>每日精选添加</li>
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
						                        <th>标题</th>
						                        <th>资源</th>
						                        <th>客户端</th>
						                        <th>主标签</th>
						                        <th>展示范围</th>
						                        <th>展示状态</th>
						                        <th>审核人员所见位置</th>
						                        <th>用户所见位置</th>
						                        <th>Banner</th>
						                        <th>开始时间</th>
						                        <th>结束时间</th>
						                    </tr>
					                    </thead>
					                    <tbody class="sortable">
					                    <#if values??>
                                    		<#list values as value>
						                        <tr id="tr_${value.id}">
				                                    <td>
				                                    	${value.title}
				                                    </td> 
				                                    <td>
				                                    	<#if value.type == 0>
				                                    		<a href="${rc.contextPath}/auth/store/video/videoinfodetail?id=${value.recomId}">
				                                    	<#elseif value.type == 1>
				                                    		<a href="${rc.contextPath}/auth/store/app/detail?id=${value.recomId}">
				                                    	<#elseif vlaue.type == 4>
				                                    		<a href="${rc.contextPath}/auth/store/app/tagapps?id=${value.recomId}">
				                                    	</#if>
				                                    	${types[value.type?c]}</a>
				                                    </td>
				                                    <td>
				                                    	${value.bundleId}
				                                    </td>
				                                    <td>
				                                    	${value.mainTag}
				                                    </td>
				                                    <td>
				                                    	${shows[value.showInAudit?c]}
				                                    </td>
				                                    <td>
				                                    	${value.auditingPosition}
				                                    </td>
				                                    <td>
				                                    	${value.auditedPosition}
				                                    </td>
				                                    <td>
				                                    	<a href="${value.img}" target="_blank"><img style="width:150px; height:70px" src="${value.img}" alt="${value.img}"/></a>
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
						                        </tr>
						                      </#list>
				                      		</#if>
					                    </tbody>
					                </table>
					            </div>
					        </#if>
					        </div>					        
                            <div class="well form-inline">
                                <div class='main-action'>
                                    <form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/dailyrecom/add" method="post">
                                        <div class="input-prepend">
                                            <span class="add-on">推荐标题</span>
                                            <input id="input_recom_title" class="span3" name="title" type="text" placeholder="推荐标题" value="">                                            
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">推荐类型</span>
                                     		<@spring.formSingleSelect "para.type", types, " " />
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">推荐客户端</span>
                                     		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                                        </div>
                                        <br/>
                                        <br/>
                                        <div class="input-prepend">
                                            <span class="add-on">展示状态</span>
                                     		<@spring.formSingleSelect "para.showInAudit", shows, " " />
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">审核人员中的位置</span>
                                            <input class="span1" size="10" name="auditingPosition" type="text" placeholder="rank" value="">                                   
                                        </div>
                                        <div class="input-prepend">
                                            <span class="add-on">用户查看时的位置</span>
                                            <input class="span1" size="10" name="auditedPosition" type="text" placeholder="rank" value="">                                   
                                        </div>
                                        <br/>
                                        <br/>
                                        <div class="input-prepend">
                                            <span class="add-on">资源ID</span>
                                            <input class="span2 search" name="recomId" type="text" placeholder="ID" value="">
                                            <input id="input_res_tag"  type="hidden" name="mainTag" value="">
                                            <span class="add-on">资源名称</span>
                                            <input id="input_res_name" class="span2" style="width:400px" name="name" type="text" placeholder="资源名称" disabled value="">                                            
                                            <span class="add-on">链接</span>
                                            <input id="input_res_link" class="span2" style="width:400px" name="link" type="text" placeholder="资源链接" disabled value="">
                                        </div>
                                        <br/>
                                        <br/>
                                        <div class="input-prepend">
	                                        <span class="add-on">开始时间</span>
	                                        <div id="startDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
	                                            <input class="span2" id="startDateString" name="st" size="16" type="text" value="">
	                                            <span class="add-on"><i class="icon-calendar"></i></span>
	                                        </div>
                                        </div>                                        
                                        <div class="input-prepend">
	                                        <span class="add-on">结束时间</span>
	                                        <div id="endDatetimePicker" class="input-append date datepicker" data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
	                                            <input class="span2" id="endDateString" name="et" size="16" type="text" value="">
	                                            <span class="add-on"><i class="icon-calendar"></i></span>
	                                        </div>
	                                    </div>
                                        <br/>
                                        <br/>
                                        <span class="add-on">推荐展示图:</span>
                                        <div class="fileinput fileinput-new" data-provides="fileinput">
                                            <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
                                                <img data-src="holder.js/100%x100%" alt="...">
                                            </div>
                                            <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 600px; max-height: 280px	;"></div>
                                            <div>
                                                <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="img"></span>
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
                var nameCtl = $("#input_res_name");
                var tagCtl = $("#input_res_tag");
                var linkCtl = $("#input_res_link");
                var btn = $("button.add");                
	    		var type = $("#type option:selected").val();
                var posting = $.post("${rc.contextPath}/auth/store/dailyrecom/search", {"id": rid, "type": type});
                posting.done(function (result) {
                    if (result.success) {
                        $(nameCtl).val(result.data.name);
                        $(tagCtl).val(result.data.mainTag);
                        $(linkCtl).val(result.data.link);
                        $(btn).removeAttr("disabled");
                    } else {
                        $(nameCtl).val(result.message);
                        $(btn).attr("disabled", "disabled");
                    }
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