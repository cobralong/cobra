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
            <li>上架版启动页墙纸添加</li>
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
                                  action="${rc.contextPath}/auth/store/wallpaper/batchAdd" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">壁纸目录</span>
                                    <input class="span2" style="width:600px" name="filePath" type="text"
                                           placeholder="壁纸目录">
                                </div>
                                <br/>
                                <button class="btn btn-info add">
                                    <i class="icon-search icon-white"></i>批量处理
                                </button>
                            </form>
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data"
                                  action="${rc.contextPath}/auth/store/wallpaper/add" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">壁纸名称</span>
                                    <input class="span2" style="width:600px" name="title" type="text"
                                           placeholder="壁纸名称">
                                </div>
                                <br/>
                                <span class="add-on">壁纸图:</span>

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
</body>
</html>