<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
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
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>上架版启动页广告添加</li>
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
                    </div>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data"
                                  action="${rc.contextPath}/auth/store/ad/add" method="post">
                                <div class="input-prepend">	                                    
                                    <span class="add-on">客户端</span>
                                    <select id="sel_bundleid" name="bundleId" type='add' class="client_choose">
                                		<#list bundleIds?keys as key>
                            				<option value="${key}">${bundleIds[key]}</option>
                            			</#list>
                                	</select>
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">RootId</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="Root Id"
                                           value="${para.rootId}">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="name" type="text"
                                           placeholder="应用名称" disabled value="">
                                </div>
                                <br/>
                                <span class="add-on">开始时间</span>

                                <div id="startDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="startDateString" name="st" size="16" type="text"
                                           value="${para.st}">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                                <span class="add-on">结束时间</span>

                                <div id="endDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="endDateString" name="et" size="16" type="text"
                                           value="${para.et}">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                                <br/>
                                <span class="add-on">广告图:</span>

                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
                                        <img data-src="holder.js/100%x100%" alt="...">
                                    </div>
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><span
                                                class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input
                                                type="file" name="img"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <br/>
                                <button class="btn btn-info add">
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
	    var rid = $(this).val();
	    var nameCtl = $("#input_app_name");
	    var btn = $("button.add");
        var posting = $.post("${rc.contextPath}/auth/promote/search", {"id": rid});
	    posting.done(function (result) {
	        if (result.success) {
	            $(nameCtl).val(result.data.name);
//	            $(btn).removeAttr("disabled");
	        } else {
	            $(nameCtl).val(result.message);
//	            $(btn).attr("disabled", "disabled");
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