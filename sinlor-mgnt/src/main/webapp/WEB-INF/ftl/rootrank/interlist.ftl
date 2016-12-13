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
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>排行榜 干预 列表</li>
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
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
                                <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
                                <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
                                <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">

                                <div class="input-prepend">
                                    <span class="add-on">排行榜类型</span>
                                <@spring.formSingleSelect "para.rankId", rankTypes, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">排行榜来源</span>
                                <@spring.formSingleSelect "para.srcType", rankSrcTypes, "onchange='changesrc(this)'" />
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">排行榜来源类型</span>
                                <@spring.formSingleSelect "para.srcId", srcMap, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="">状态</span>
                                <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info query">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                                <div id="addInfoDiv" errMsg="${para.errMsg}">
                                <#if para.errMsg??>
                                    <font>${para.errMsg}</font>
                                </#if>
                                </div>
                            </form>
                        </div>
                    </div>

                    <button class="btn queryToggle  btn-info">
                        <span class="glyphicon glyphicon-search"></span>新增干预
                    </button>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/rootrank/intervention/add"
                                  method="post">
                                <input type="hidden" id="addpara.rankId" name="rankId" value="${para.rankId}">
                                <input type="hidden" id="addpara.srcType" name="srcType" value="${para.srcType}">
                                <input type="hidden" id="addpara.srcId" name="srcId" value="${para.srcId}">
                                <input type="hidden" id="addpara.refUrl" name="refUrl"
                                       value="/auth/rootrank/intervention/list">

                                <div><span>新增到下面的当前列表，不与修改后的查询框栏数据对应</span></div>
                                <div class="input-prepend">
                                    <span class="add-on">ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">授权应用</span>
                                    <input id="input_app_auth_status" class="span2" style="width:200px" name="authStatus" type="text" placeholder="应用授权状态" disabled value="">
                                </div>
                            	<br/>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="name" type="text"
                                           placeholder="应用名称" disabled value="">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">位置</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="pos" type="text"
                                           placeholder="位置" value="">
                                </div>
                                <br/>

                                <button class="btn btn-info query">
                                    <i class="icon-search icon-white"></i>新增
                                </button>
                                <div id="addInfoDiv" errMsg="${para.errMsg}">
                                <#if para.errMsg??>
                                    <font>${para.errMsg}</font>
                                </#if>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="table-content">
                    <#-- table -->
                        <table class="table table-striped table-bordered table-condensed" id="itemTable">
                            <thead>
                            <tr>
                                <th>应用</th>
                                <th>图标</th>
                                <th>下载数</th>
                                <th>更新时间</th>
                                <th>位置</th>
                                <th>授权应用</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody class="sortable">
                            <#if values??>
                                <#list values as value>
                                <tr id="tr_${value.rootId}">
                                    <#assign rootSimple = rootSimples[value.rootId?c]>
                                    <#assign statusRespMap = statusRespMaps[value.rootId?c]>
                                    <input id="hidden_${value.rootId}" type="hidden" root_id="${value.rootId!}"
                                           intervent_id="${value.interventId!}"
                                           app_title="${rootSimple.name}"/>
                                    <td>
                                        <@hrefapp value.rootId, rootSimple.name/>
                                    </td>
                                    <td>
                                        <@appicon rootSimple.icon, rootSimple.name/>
                                    </td>
                                    <td>
                                    ${rootSimple.downloadCount}
                                    </td>
                                    <td>
                                        <#if value.releaseDate ??>
                                        ${ value.releaseDate?string("yyyy-MM-dd HH:mm:ss") }
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td class="edit_td" pid="${value.interventId?c}">
                                        <label for="name" class="span1">
                                            <span id="p_${value.interventId?c}"
                                                  class="text-info"
                                                  preValue="${value.pos?c}">${ value.pos! }</span></label>
                                    </td>
                                    <td>
                                        <#if statusRespMap.download==true>
                                    		<span id="auth_status_span_${value.id}" class="btn btn-success" disabled>可下载</span>
                                    	<#elseif statusRespMap.bought==true>
                                    		<span id="auth_status_span_${value.id}" class="btn btn-warning" disabled>已购买/下载异常</span>
                                    	<#else>
                                    		<span id="auth_status_span_${value.id}" class="btn btn-danger" disabled>未购买</span>
                                        </#if>
                                    </td>
                                    <td>
                                        <button id="disable_intervent_btn_${value.rootId}"
                                                class="btn btn-success glyphicon glyphicon-trash intervent-disable-btn"
                                                intervent_id="${value.interventId!}"
                                                <#if para.status == -1>disabled
                                                style="display:none;"</#if>>
                                            禁用
                                        </button>
                                        <button id="intervent_btn_${value.rootId}" class="btn btn-danger pos-intervent-btn"
                                                intervent_id="${value.interventId!}"
                                                <#if para.status ==0 || para.status == -2>disabled style="display:none;"</#if>>
                                            启用
                                        </button>
                                    </td>
                                </tr>
                                </#list>
                            </#if>
                            </tbody>
                        </table>
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

    function changesrc() {
        var posting = $.post("${rc.contextPath}/auth/rootrank/querysrc", {"srcType": $("#srcType").val()});
        posting.done(function (result) {
            if (result) {
                $("#srcId").empty();
                $.map(result, function (v, k) {
                    $("#srcId").append($('<option value="' + k + '">' + v + '</option>'));
                });
            } else {
                // TODO
            }
        });
    }

    $("input.search").bind("blur", function () {
        var ctl = $(this);
        var rid = $(ctl).val();
        var txtCtl = $("#input_app_name");
        var txtStatus = $("#input_app_auth_status");
        var btn = $("button.add");
        var posting = $.post("${rc.contextPath}/auth/promote/search", {"id": rid});
        posting.done(function (result) {
            if (result.success) {
                $(txtCtl).val(result.data.name);
                if(result.data.authDownload){
 						$(txtStatus).val("可下载");
 					}else if(result.data.bought){
 						$(txtStatus).val("已购买/下载异常");
 					}else{
 						$(txtStatus).val("未购买");
 					}
                $(btn).removeAttr("disabled");
            } else {
                $(txtCtl).val(result.message);
                $(txtStatus).val("id无效,状态未知");
                $(btn).attr("disabled", "disabled");
            }
        });
    });

    $(".pos-intervent-btn").bind("click", function () {
        var ctl = $(this);
        var interventId = $(ctl).attr('intervent_id');
        var pos = $('#p_' + interventId).attr('preValue');
        if(pos <= 0){
            alert("无效的位置，请先修改位置");
            return;
        }
        bootbox.confirm("是否启用干预?", function (result) {
            if (!result) {
                return;
            }
            var posting = $.post("${rc.contextPath}/auth/rootrank/intervention/modify", {"interventId": interventId, "status": 0});
            posting.done(function (result) {
                if (result == '') {
                    $(ctl).closest("tr").remove();
                } else {
                    alert(result);
                }
            });
        });
    });

    $(".intervent-disable-btn").bind("click", function () {
        var ctl = $(this);
        var interventId = $(ctl).attr('intervent_id');
        bootbox.confirm("是否禁用干预?", function (result) {
            if (!result) {
                return;
            }
            var posting = $.post("${rc.contextPath}/auth/rootrank/intervention/modify", {"interventId": interventId, "status": -1});
            posting.done(function (result) {
                if (result == '') {
                    $(ctl).closest("tr").remove();
                } else {
                    alert(result);
                }
            });
        });
    });

    $('td.edit_td').click(function () {
        var interventId = $(this).attr("pid");
        var preValue = $('#p_' + interventId).attr("preValue");
        var input = $('<input id="attribute" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />');
        $('#p_' + interventId).text('').append(input);
        input.select();
        input.blur(function () {
            var preValue = $(this).parent().attr("preValue");
            var curValue = $('#attribute').val();
            if (curValue == "") {
                $(this).val(preValue);
                $(this).focus();
                return;
            }
            if (curValue == preValue) {
                var text = $('#attribute').val();
                $('#attribute').parent().text(preValue);
                $('#attribute').remove();
                return;
            }
            bootbox.confirm("将推荐干预位置由" + preValue + "------>" + curValue + "?", function (result) {
                if (result) {
                    var posting = $.post("${rc.contextPath}/auth/rootrank/intervention/modifypos",
                            {"interventId": interventId, "pos": curValue});
                    posting.done(function (result) {
                        if (result == '') {
                            $('#attribute').parent().attr("preValue", curValue).text(curValue);
                            $('#attribute').remove();
                        } else {
                            alert(result);
                        }
                    });
                }
                else {
                    $('#attribute').parent().text(preValue);
                    $('#attribute').remove();
                }
            });
        });
    });


</script>
</body>
</html>