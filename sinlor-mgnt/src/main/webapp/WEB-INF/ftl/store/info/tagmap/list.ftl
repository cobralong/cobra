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
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>上架版AppTag映射</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/store/tagmap/add"
                                  method="post">
                                <div class="input-prepend">
                                    <span class="add-on">苹果分类</span>
                                <@spring.formSingleSelect "para.appleCateId", leftAppleCategoriesMap, " " />
                                </div>
                                映射到------>
                                <div class="input-prepend">
                                    <span class="add-on">本地应用分类</span>
                                <@spring.formSingleSelect "para.appTagId", storeAppTags, " " />
                                </div>
                                <br/>

                                <button class="btn btn-info add">
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${errMsg!''}">
                                <#if errMsg??>
                                    <font color='red'>${errMsg!''}</font>
                                </#if>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                        <div>
                        <#if para.status == 0>
                        <b>有效映射(总数<font
                                color="red">${para.pager.total}</font>).
                        </#if>
                        </div>
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>本地应用分类</th>
                                    <th>苹果分类</th>
                                    <th>更新时间</th>
                                    <th>插入时间</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td>
                                        ${storeAppTags[value.storeCategoryId?string]}
                                        </td>
                                        <td>
                                        ${appleCategories[value.appleCategoryId?string]}
                                        </td>
                                        <td>
                                            <#if value.updateTime ??>
                                            	${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.createTime ??>
                                            	${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                            </#if>
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
    $(document).ready(function () {
        var currPageDiv = "#pager_page";
        var totalDiv = "#pager_total";
        var sizeDiv = "#pager_size";
        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
    });


    $("button.queryToggle").bind("click", function () {
        $("#div_query").toggle();
    });

    $("button.addToggle").bind("click", function () {
        $("#div_add").toggle();
    });


    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var appTagId = $(this).attr("appTagId");
        var appleCateId = $(this).attr("appleCateId");
        var postUrl = "${rc.contextPath}/auth/store/tagmap/modify";
        var preStatus = $(hidden).attr("promote_status");
        var action = preStatus == 0 ? "失效" : "生效";
        var setStatus = preStatus == 0 ? -1 : 0;

        bootbox.confirm("是否" + action + "Tag映射" + "?", function (result) {
            if (!result) {
                return;
            }
            var posting = $.post(postUrl, {'appTagId': appTagId, 'appleCateId': appleCateId, "status": setStatus});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    var tr = $(this).closest('tr');
                    var statusSpan = $(".status_span", tr);
                    var positiveEventCtl = preStatus == 0 ? $(".promote_btn", tr) : $(".unpromote_btn", tr);
                    var statusInfo = preStatus == 0 ? "删除" : "正常";
                    var removeClass = preStatus == 0 ? "btn-success" : "btn-danger";
                    var addClass = preStatus == 0 ? "btn-danger" : "btn-success";
                    $(positiveEventCtl).removeAttr("disabled");
                    $(eventCtl).attr("disabled", "disabled");
                    $(statusSpan).removeClass(removeClass).addClass(addClass);
                    $(statusSpan).text(statusInfo);
                    alert("操作成功，已" + action + "Tag映射");
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