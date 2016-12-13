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
            <li>上架版Banner添加</li>
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
                            <font>${errMsg}</font>
                        </#if>
                        </div>
                    </div>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" enctype="multipart/form-data"
                                  action="${rc.contextPath}/auth/store/banner/add" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">Type</span>
                                	<@spring.formSingleSelect "para.type", types, "onchange='changetype(this.value)'" />                                	
                                </div>
                                <div class="input-prepend app-banner">
                                    <span class="add-on">RootId</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="Root Id"
                                           value="${para.rootId}">
                                </div>
                                <div class="input-prepend app-banner">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:600px" name="name" type="text"
                                           placeholder="应用名称" disabled value="">
                                </div>
                                <div class="input-prepend info-banner">
                                    <span class="add-on">ArticleId</span>
                                    <input class="span2 info-search" name="articleId" type="text" placeholder="Article Id"
                                           value="${para.articleId}">
                                </div>
                                <div class="input-prepend info-banner">
                                    <span class="add-on">资讯标题</span>
                                    <input id="input_article_name" class="span2" style="width:600px" name="name" type="text"
                                           placeholder="资讯标题" disabled value="">
                                </div>
								<br/><br/>
                                <div class="input-prepend">
                                    <span class="add-on">短描述</span>
                                    <input id="input_app_name" class="span2" style="width:200px" name="desc" type="text"
                                           placeholder="短描述" value="${para.desc}">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">展示状态</span>
                             		<@spring.formSingleSelect "para.showInAudit", shows, " " />
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">审核人员中的位置</span>
                                    <input class="span1" size="10" name="auditingPosition" type="text" placeholder="rank" value="">                                   
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">用户查看时的位置</span>
                                    <input class="span1" size="10" name="auditedPosition" type="text" placeholder="rank" value="">                                   
                                </div>
                                <br/>
                                <br/>
                                <div class="input-prepend">
                                    <span class="add-on">推荐客户端</span>
                             		<@spring.formSingleSelect "para.bundleId", bundleIds, " " />
                                </div>
                                <span class="add-on">开始时间</span>
                                <div id="startDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="startDateString" name="st" size="16" type="text" value="${para.st}">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                                <span class="add-on">结束时间</span>
                                <div id="endDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="endDateString" name="et" size="16" type="text" value="${para.et}">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                                <br/>
                                <br/>
                                <span class="add-on">手机轮播图:</span>

                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
                                        <img data-src="holder.js/100%x100%" alt="...">
                                    </div>
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><span
                                                class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input
                                                type="file" name="banner"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <br/>
                                <button class="btn btn-info add" disabled>
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
    $(document).ready(function(e){
        var val = $("#type").val();
        if (val == 1) {
            $(".info-banner").hide();
            $(".app-banner").show();
        } else if (val == 2) {
            $(".app-banner").hide();
            $(".info-banner").show();
        }
    });
    function changetype(val) {
        if (val == 1) {
            $(".info-banner").hide();
            $(".app-banner").show();
        } else if (val == 2) {
            $(".app-banner").hide();
            $(".info-banner").show();
        }
    }

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
                $(ctl).focus();
                $(txtCtl).val(result.message);
                $(btn).attr("disabled", "disabled");
            }
        });
    });

    $("input.info-search").bind("blur", function () {
        var ctl = $(this);
        var rid = $(ctl).val();
        var txtCtl = $("#input_article_name");
        var btn = $("button.add");
        var posting = $.post("${rc.contextPath}/auth/store/article/search", {"id": rid});
        posting.done(function (result) {
            if (result.success) {
                $(txtCtl).val(result.data.title);
                $(btn).removeAttr("disabled");
            } else {
                //$(ctl).focus();
                $(txtCtl).val(result.message);
                $(btn).attr("disabled", "disabled");
            }
        });
    });

    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("banner_id");
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
        var appTitle = $(hidden).attr("app_title");
        var appChannel = $(hidden).attr("app_channel");
        var postUrl = "${rc.contextPath}/auth/banner/modify";
        var preStatus = $(hidden).attr("promote_status");
        var actionMsg = preStatus == 0 ?
                "是否将应用【【" + appTitle + "】】从" + appChannel + "渠道的轮播列表中移除?" :
                "是否将应用【【" + appTitle + "】】添加到" + appChannel + "渠道的轮播列表?";
        var setStatus = preStatus == 0 ? -1 : 0;

        bootbox.confirm(actionMsg, function (result) {
            if (!result) {
                return;
            }
            var posting = $.post(postUrl, {'id': id, "status": setStatus});
            posting.done(function (errMsg) {
                if (errMsg) {//
                    //show msg
                    alert("操作失败,请联系该死的开发人员.Msg:" + errMsg);
                } else {
                    var responseMsg = preStatus == 0 ?
                            "成功将应用【【" + appTitle + "】】从" + appChannel + "渠道的轮播列表中移除!" :
                            "成功将应用【【" + appTitle + "】】添加到" + appChannel + "渠道的轮播列表!";
                    alert(responseMsg);
                    trCtl.remove();
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