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
        <li>上架版推荐列表</li>
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
                            <span class="">推荐页</span>
                        <@spring.formSingleSelect "para.tabId", tabs, " " />
                        </div>
                        <div class="input-prepend">
                            <span class="">状态</span>
                        <@spring.formSingleSelect "para.status", status, " " />
                        </div>
                        <span class="">开始时间</span>

                        <div id="startDatetimePicker" class="input-append date datepicker"
                             data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                            <input class="span2" id="search_startDateString" name="st" size="16" type="text"
                                   value="${para.st}">
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                        <span class="">结束时间</span>

                        <div id="endDatetimePicker" class="input-append date datepicker"
                             data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                            <input class="span2" id="endDateString" name="et" size="16" type="text"
                                   value="${para.et}">
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                        <button class="btn btn-info search">
                            <i class="icon-search icon-white"></i>查询
                        </button>
                        <button id="btn_now_promote" class="btn btn-info search">
                            <i class="icon-search icon-white"></i>正在推广
                        </button>
                    </form>
                </div>
            </div>
            <br/>
            <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
            <div id="div_add" class="well form-inline">
                <div class='main-action'>
                    <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/store/promote/add"
                          method="post">
                        <div class="input-prepend">
                            <span class="add-on">ID</span>
                            <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">应用名称</span>
                            <input id="input_app_name" class="span2" style="width:600px" name="name" type="text"
                                   placeholder="应用名称" disabled value="">
                        </div>
                        <br/>

                        <div class="input-prepend">
                            <span class="add-on">推荐页</span>
                        <@spring.formSingleSelect "para.tabId", tabs, "" />
                        </div>
                        <span class="add-on">开始时间</span>

                        <div id="startDatetimePicker" class="input-append date datepicker"
                             data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                            <input class="span2" id="startDateString" name="st" size="16" type="text"
                                   value="${st!}">
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                        <span class="add-on">结束时间</span>

                        <div id="endDatetimePicker" class="input-append date datepicker"
                             data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                            <input class="span2" id="endDateString" name="et" size="16" type="text"
                                   value="${et!}">
                            <span class="add-on"><i class="icon-calendar"></i></span>
                        </div>
                        <div class="input-prepend">
                            <span class="add-on">位置</span>
                            <input class="span1" size="10" name="rank" type="text" placeholder="rank" value="">
                        </div>
                        <button class="btn btn-info add" disabled>
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
                    在<b><font color="green">${para.st}</font></b>时间在进行展示的应用列表(总数<font
                        color="red">${para.pager.total}</font>).
                </#if>
                <#if para.status != -1>
                    <button id="btn_all_delete" class="btn btn-danger" disabled>
                        停止推荐所选应用
                    </button>
                </#if>
                </div>
            <#-- table -->
                <form id="itemForm" name="itemForm" method="post">
                    <table class="table table-striped table-bordered table-condensed" id="itemTable">
                        <thead>
                        <tr>
                            <th><input type="checkbox" value="" id="selectAll"/></th>
                            <th>位置</th>
                            <th>应用</th>
                            <th>图标</th>
                            <th>排行</th>
                            <th>推荐页</th>
                            <th>状态</th>
                            <th>下载地址</th>
                            <th>开始时间</th>
                            <th>结束时间</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody class="sortable">
                        <#if values??>
                            <#list values as value>
                            <tr id="tr_${value.id?c}">
                                <td><input type="checkbox" name="ids" class='selectOne' value="${ value.id! }"/>
                                </td>
                                <#assign rootSimple = rootSimples[value.rootId?c]>
                                <input id="hidden_${value.id}" type="hidden" app_id="${value.id!}"
                                       app_title="${rootSimple.name}" promote_tabId="${value.tabId}"
                                       promote_tab="${tabs[value.tabId?string]}"
                                       promote_status="${value.status}"/>
                                <td>
                                ${para.start + value_index + 1}
                                </td>
                                <td>
                                    <@hrefapp value.rootId, rootSimple.name/>
                                </td>
                                <td>
                                    <@appicon rootSimple.icon, rootSimple.name/>
                                </td>
                                <td class="edit_td" pid="${value.id?c}">
                                    <label for="name" class="span1"><span id="p_${value.id?c}" class="text-info"
                                                                          preValue="${value.rank?c}">${ value.rank! }</span></label>
                                </td>
                                <td>${ tabs[value.tabid?string] }</td>
                                <td>
                                    <#if value.status == 0>
                                        <span id="status_span_${value.id}" class="btn btn-success">正常</span>
                                    <#else>
                                        <span id="status_span_${value.id}" class="btn btn-danger"
                                              disabled>删除</span>
                                    </#if>
                                </td>
                                <td>
                                    <@hrefipaplist rootSimple.downloadUrl!''/>
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
                                    <button id="promote_btn_${value.id}"
                                            class="btn btn-success delete glyphicon glyphicon-trash "
                                            app_id="${value.id!}"  <#if value.status == 0>disabled</#if>>
                                        推荐
                                    </button>
                                    <button id="unpromote_btn_${value.id}" class="btn btn-danger delete"
                                            app_id="${value.id!}"  <#if value.status != 0>disabled</#if>>
                                        停止
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
    $(document).ready(function () {
        var currPageDiv = "#pager_page";
        var totalDiv = "#pager_total";
        var sizeDiv = "#pager_size";
        $.manage.pageList(".pagination", "#searchForm", currPageDiv, totalDiv, sizeDiv);
    });
    $(document).find(':checkbox').click(function () {
        if ($(this).is(':checked')) {
            $("#btn_all_delete").removeAttr("disabled");
        } else {
            var checkboxes = $(this).closest('form').find(':checkbox');
            var leftChecked = false;
            checkboxes.each(function () {
                if ($(this).is(':checked')) {
                    if ($(this).attr("id") == "selectAll") {
                        return;
                    } else {
                        leftChecked = true;
                        return true;
                    }
                }
            });
            if (leftChecked) {
                $("#btn_all_delete").removeAttr("disabled");
            } else {
                $("#btn_all_delete").prop("disabled", "disabled");
            }
        }
    });
    $('#selectAll').change(function () {
        var checkboxes = $(this).closest('form').find(':checkbox');
        if ($(this).is(':checked')) {
            checkboxes.prop('checked', true);
            $("#btn_all_delete").removeAttr("disabled");
        } else {
            checkboxes.prop('checked', false);
            $("#btn_all_delete").prop("disabled", "disabled");
        }
    });

    $("#btn_all_delete").bind("click", function () {
        var selected = 0;
        var checkboxes = $("#selectAll").closest('form').find(':checkbox');
        var ids = "";
        var idArray = [];
        checkboxes.each(function () {
            if ($(this).is(':checked')) {
                if ($(this).attr("id") == "selectAll") {
                    return;
                }
                selected = selected + 1;
                var addid = $(this).val();
                ids = ids + addid + ",";
                idArray.push(addid);
            }
        });

        bootbox.confirm("是否停止推荐" + selected + "个应用?", function (result) {
            if (!result) {
                return;
            }
            var postUrl = "${rc.contextPath}/auth/store/promote/modify";
            var posting = $.post(postUrl, {'ids': ids, "status": -1});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    $.each(idArray, function (index, value) {
                        $("#tr_" + value).remove();
                    });
                    alert("操作成功，已停止推荐相关应用.");
                }
            });
        });
    });

    $("input.search").bind("blur", function () {
        var ctl = $(this);
        var rid = $(ctl).val();
        var txtCtl = $("#input_app_name");
        var btn = $("button.add");
        var posting = $.post("${rc.contextPath}/auth/promote/search", {"id": rid});
        posting.done(function (result) {
            if (result.success) {
                $(txtCtl).val(result.data.name);
                $(btn).removeAttr("disabled");
            } else {
                $(txtCtl).val(result.message);
                $(btn).attr("disabled", "disabled");
            }
        });
    });
    $("button.queryToggle").bind("click", function () {
        $("#div_query").toggle();
    });

    $("button.addToggle").bind("click", function () {
        $("#div_add").toggle();
    });
    $("#btn_now_promote").bind("click", function () {
        $("#search_startDateString").val("");
    });
    $('.edit_td').click(function () {
        var pid = $(this).attr("pid");
        var preValue = $('#p_' + pid).attr("preValue");
        var input = $('<input id="attribute" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />')
        $('#p_' + pid).text('').append(input);
        input.select();
        input.blur(function () {
            var preValue = $(this).parent().attr("preValue");
            var hidden = $("#hidden_" + pid);
            var appTitle = $(hidden).attr("app_title");
            var curValue = $('#attribute').val();
            if (curValue == "") {
                $(this).val(preValue);
                $(this).focus();
                reutrn;
            }
            if (curValue == preValue) {
                var text = $('#attribute').val();
                $('#attribute').parent().text(preValue);
                $('#attribute').remove();
                return;
            }
            bootbox.confirm("将推荐应用" + appTitle + "的推荐位置由" + preValue + "------>" + curValue + "?(当前值并不一定为其所在位置，具体位置请修改后刷新页面查看位置项.)", function (result) {
                if (result) {
                    var postUrl = "${rc.contextPath}/auth/store/promote/modifyrank";
                    var posting = $.post(postUrl, {'id': pid, "rank": curValue});
                    posting.done(function (errMsg) {
                        if (errMsg) {//
                            //show msg
                            alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                            $('#attribute').parent().text(preValue);
                            $('#attribute').remove();
                        } else {
                            alert("修改成功.");
                            $('#attribute').parent().text(curValue);
                            $('#attribute').remove();
                        }
                    });
                } else {
                    $('#attribute').parent().text(preValue);
                    $('#attribute').remove();
                }
            });
        });
    });

    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("app_id");
        var hidden = $("#hidden_" + id);
        var appTitle = $(hidden).attr("app_title");
        var promoteTab = $(hidden).attr("promote_tab");
        var postUrl = "${rc.contextPath}/auth/store/promote/modify";
        var preStatus = $(hidden).attr("promote_status");
        var action = preStatus == 0 ? "暂停" : "开始";
        var setStatus = preStatus == 0 ? -1 : 0;

        bootbox.confirm("是否在推荐页" + promoteTab + action + "推荐应用" + appTitle + "?", function (result) {
            if (!result) {
                return;
            }
            var posting = $.post(postUrl, {'id': id, "status": setStatus});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    var statusSpan = $("#status_span_" + id);
                    var positiveEventCtl = preStatus == 0 ? $("#promote_btn_" + id) : $("#unpromote_btn_" + id);
                    var statusInfo = preStatus == 0 ? "删除" : "正常";
                    var removeClass = preStatus == 0 ? "btn-success" : "btn-danger";
                    var addClass = preStatus == 0 ? "btn-danger" : "btn-success";
                    $(hidden).attr("promote_status", setStatus);
                    $(positiveEventCtl).removeAttr("disabled");
                    $(eventCtl).attr("disabled", "disabled");
                    $(statusSpan).removeClass(removeClass).addClass(addClass);
                    $(statusSpan).text(statusInfo);
                    alert("操作成功，已" + action + "推荐");
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