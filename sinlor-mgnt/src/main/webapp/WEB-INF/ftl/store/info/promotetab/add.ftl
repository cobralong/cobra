<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <link href="${rc.contextPath}/ueditor/themes/default/css/umeditor.css" type="text/css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">

    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>新建推荐列表页</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
        <#include "lib/alert.ftl">
            <div class='main-content'>
                <div class="well form-inline">
                	<div id="addInfoDiv">
                    	<#if errMsg??>
                    		<font>${errMsg}</font>
                    	</#if>
                    </div>
                    <div class='main-action'>
                        <form id="addform" name="addform" method="post"
                              action="${rc.contextPath}/auth/store/promotetab/add"
                              enctype="multipart/form-data">
                            <div class="input-prepend">
                                <span class="add-on">名称</span>
                                <input class="span2" name="name" type="text" placeholder="标签名" value="${para.name}">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">类型</span>
                            <@spring.formSingleSelect "para.type", types, "" />
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">位置</span>
                                <input class="span2" name="rank" type="text" placeholder="Rank" value="${para.rank}">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">字体颜色</span>
                                <input type="color" name="color" style="width:220px; " value="#${data.color}">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">展示Icon</span>
	                            <div class="fileinput fileinput-new" data-provides="fileinput">
	                                <div class="fileinput-new thumbnail" style="width: 30px; height: 30px;">
	                                    <img data-src="holder.js/100%x100%" alt="...">
	                                </div>
	                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 30px; max-height: 30px	;"></div>
	                                <div>
	                                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="icon"></span>
	                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
	                                </div>
	                            </div>
                            </div>                            
                            <input id="submitBtn" class="btn btn-info add" type="submit" value="提交"/>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
    <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
</body>
</html>