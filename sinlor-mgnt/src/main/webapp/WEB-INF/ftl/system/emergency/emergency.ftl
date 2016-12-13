<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>系统紧急信息</li>
        </ul>
    </div>

    <div>
    <#if errMsg??>
        <font color="red">${errMsg}</font>
    </#if>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="editForm" name="editForm" enctype="multipart/form-data"
                                  action="${rc.contextPath}/auth/emergency/save" method="post">
                                <input type="hidden" name="id" value="${data.id!}"/>

                                <div class="input-prepend">
                                    <span class="add-on">标题：</span>
                                    <input class="span2" style="width:400px" name="title" type="text"
                                           placeholder="标题" value="${data.title!}">
                                </div>
                                <br/>

                                <div class="input-prepend">
                                    <span class="add-on">客户端：</span>
                                <#if data.id??><input type="hidden" name="bundleId" value="${data.bundleId!}" /></#if>
                                    <input class="span2" style="width:400px" name="bundleId" type="text"
                                           <#if data.id??>disabled</#if>
                                           placeholder="BundleId" value="${data.bundleId!}">
                                </div>
                                <br/>

                                <div class="input-prepend">
                                    <span class="add-on">内容</span>
                                    <input class="span2" style="width:600px" name="content" type="text"
                                           placeholder="内容" value="${data.content!}">
                                </div>
                                <br/>

                                <div class="input-prepend">
                                    <span class="add-on">按钮文字</span>
                                    <input class="span2" style="width:400px" name="btnContent" type="text"
                                           placeholder="按钮名字" value="${data.btnContent!}">
                                </div>
                                <br/><br/>

                                <div class="input-prepend">
                                    <span class="add-on">链接</span>
                                    <input class="span2" style="width:400px" name="linkUrl" type="text"
                                           placeholder="链接" value="${data.linkUrl!}">
                                </div>
                                <br/><br/>

                                <div class="input-prepend">
                                    <span class="">状态</span>
                                <@spring.formSingleSelect "data.status", status, " " />
                                </div>
                                <br/><br/>
                                <button class="btn btn-info add">
                                    <i class="icon-search icon-white"></i>保存
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>