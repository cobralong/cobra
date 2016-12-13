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
            <li>排行榜列表</li>
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
                                <input type="hidden" id="addpara_rankId" name="rankId" value="${para.rankId}">
                                <input type="hidden" id="addpara_srcType" name="srcType" value="${para.srcType}">
                                <input type="hidden" id="addpara_srcId" name="srcId" value="${para.srcId}">
                                <input type="hidden" id="addpara_refUrl" name="refUrl" value="/auth/rootrank/list">

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
                                    <input class="span2" style="width:600px" name="pos" type="text"
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
                                <th>干预</th>
                                <th>应用</th>
                                <th>图标</th>
                                <th>下载数</th>
                                <th>更新时间</th>
                                <th>下载地址</th>
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
                                        <#if value.interventId != null><span
                                                style="color:green;">&radic;</span></#if>
                                    </td>
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
                                    <td>
                                        <@hrefipaplist rootSimple.downloadUrl!''/>
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
                                        <button id="remove_btn_${value.rootId}"
                                                class="btn btn-success glyphicon glyphicon-trash remove-btn"
                                                root_id="${value.rootId!}"
                                                <#if value.interventId != null>disabled style="display:none;"</#if>>
                                            移除
                                        </button>
                                        <button id="intervent_btn_${value.rootId}" class="btn btn-danger intervent-btn"
                                                root_id="${value.rootId!}"
                                                <#if value.interventId == null>disabled style="display:none;"</#if>>
                                            不干预
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
                if(result.data.authDownload==true){
 						$(txtStatus).val("可下载");
 					}else if(result.data.bought==true){
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

    $(".intervent-btn").bind("click", function () {
        var ctl = $(this);
        var rid = $(ctl).attr('root_id');
        var interventId = $("#hidden_" + rid).attr("intervent_id");
        bootbox.confirm("是否移除干预?", function (result) {
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

    $(".remove-btn").bind("click", function () {
        var ctl = $(this);
        var rid = $(ctl).attr('root_id');

        //Integer rootId, Integer srcId, Integer srcType,
//        Integer rankId, Integer pos, String refUrl
        var rankId = $("#addpara_rankId").val();
        var srcType = $("#addpara_srcType").val();
        var srcId = $("#addpara_srcId").val();
        bootbox.confirm("是否将应用从此排行榜移除?", function (result) {
            if (!result) {
                return;
            }
            var posting = $.post("${rc.contextPath}/auth/rootrank/intervention/remove",
                    {"srcType": srcType,
                        "srcId": srcId,
                        "rankId": rankId,
                        "rootId":rid});
            posting.done(function (result) {
                if (result == '') {
                    $(ctl).closest("tr").remove();
                } else {
                    alert(result);
                }
            });
        });
    });
</script>
</body>
</html>