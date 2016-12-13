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
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>标题</th>
                                    <th>BundleId</th>
                                    <th>内容</th>
                                    <th>按钮</th>
                                    <th>链接</th>
                                    <th>状态</th>
                                    <th>编辑</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <input id="hidden_${value.id}" type="hidden" bundle_id="${value.bundleId!}"
                                               title="${value.title}" promote_status="${value.status}"/>
                                        <td>
                                        ${value.title}
                                        </td>
                                        <td>
                                        ${value.bundleId}
                                        </td>
                                        <td>
                                        ${value.content}
                                        </td>
                                        <td>
                                        ${value.btnContent}
                                        </td>
                                        <td>
                                        ${value.linkUrl}
                                        </td>

                                        <td>
                                            <#if value.status == 0>
                                                <span id="status_span_${value.id}" class="btn btn-success">生效</span>
                                            <#else>
                                                <span id="status_span_${value.id}" class="btn btn-danger"
                                                      disabled>失效</span>
                                            </#if>
                                        </td>
                                        <td><a class="btn"
                                               href="${rc.contextPath}/auth/emergency/detail?id=${value.id!}">编辑</a>
                                        </td>
                                    </tr
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
</body>
</html>