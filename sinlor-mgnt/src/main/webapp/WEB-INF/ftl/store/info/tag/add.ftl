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
<#--<!-- 配置文件 &ndash;&gt;-->
<#--<script type="text/javascript" src="${rc.contextPath}/ueditor/umeditor.config.js"></script>-->
<#--<script type="text/javascript" src="${rc.contextPath}/ueditor/umeditor.js"></script>-->
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>新建资讯标签</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
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
                        <form id="addform" name="addform" method="post" action="${rc.contextPath}/auth/store/articletag/add"
                              enctype="multipart/form-data">
                            <div class="input-prepend">
                                <span class="add-on">名称</span>
                                <input class="span2" name="name" type="text" placeholder="标签名" value="${name}">
                            </div>
                            <div class="input-prepend">
                                <span class="add-on">颜色</span>
                                <input class="span2" style="width:100px" name="color" type="color" placeholder="标签颜色"
                                       value="${color}">
                            </div>

                            <input id="submitBtn" type="submit" value="提交"/>
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
    <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
    <script type="text/javascript">

        $('#addform').submit(function (e) {
            var formData = new FormData(document.getElementById('addform'));
            $.ajax({
                type: $(this).attr('method'),
                url: $(this).attr('action'),
                data: formData,
                processData: false,
                contentType: false,
                success: function (data) {
                    if (data && data != '') {
                        alert(data);
                    }
                    else {
                        alert('添加成功');
                    }
                }
            });
            e.preventDefault();
        });

    </script>
</body>
</html>