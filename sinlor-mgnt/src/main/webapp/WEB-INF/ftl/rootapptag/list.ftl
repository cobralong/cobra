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
            <li>管理后台</li><span class="divider">/</span>
            <li>Root Application Tag列表</li>
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
                                    <span class="">状态</span>
                                <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <br/>
                                <button class="btn btn-info search" >
                                    <i class="icon-search icon-white"></i>查询
                                </button>
                                <div errMsg="${para.errMsg!''}">
			                        <#if para.errMsg??>
			                            <font color='red'>${para.errMsg!''}</font>
			                        </#if>
		                        </div>
                            </form>
                        </div>
                    </div>

                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/rootapptag/add" method="post">
                                <div class="input-prepend">
                                    <span class="add-on">ID</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="ID" value="">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">应用名称</span>
                                    <input id="input_app_name" class="span2" style="width:300px" name="name" type="text" placeholder="应用名称" disabled value="">
                                    <span class="add-on">OTA包</span>
                                    <input id="input_plist" class="span2" style="width:600px" name="name" type="text" placeholder="OTA包" disabled value="">
                                    <input id="input_cert" class="span2" style="width:100px" name="name" type="text" placeholder="签名" disabled value="">
                                </div>
                                <br/>
                                <div class="input-prepend">
                                    <span class="add-on">标签</span>
                                    <input class="span1" size="10" name="tag" type="text" placeholder="标签名" value="">
                                </div>
                                <div class="input-prepend">
                                    <span class="add-on">得分</span>
                                    <input class="span1" size="10" name="score" type="text" placeholder="标签名" value="">
                                </div>
                                <button class="btn btn-info add" >
                                    <i class="icon-search icon-white"></i>增加
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
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>应用</th>
                                    <th>图标</th>
                                    <th>Tag</th>
                                    <th>Score</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                        <#assign rootSimple = rootSimples[value.rootId?c]>
                                    <tr id="tr_${value.id}">
                                    	<input id="hidden_${value.id}" type="hidden" promote_status="${value.status}"/>
                                        <td>
                                            <@hrefapp value.rootId, rootSimple.name/>
                                        </td>
                                        <td>
                                            <@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                        <td class="edit_td" pid="${value.id?c}">
                                            <label for="name" class="span1"><span id="p_${value.id?c}" class="text-info" preValue="${value.tag!}">${ value.tag! }</span></label>
                                        </td>
                                        <td class="edit-score-td" pid="${value.id?c}">
                                            <label for="name" class="span1"><span id="p2_${value.id?c}" class="text-info" preValue="${value.score?string["0.#"]}">${ value.score?string["0.#"] }</span></label>
                                        </td>
                                        <td>
                                            <button id="promote_btn_${value.id}" class="btn btn-success delete glyphicon glyphicon-trash " app_id = "${value.id!}"  <#if value.status == 0>disabled</#if>>
                                                推荐
                                            </button>
                                            <button id="unpromote_btn_${value.id}" class="btn btn-danger delete" app_id = "${value.id!}"  <#if value.status != 0>disabled</#if>>
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
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });

        $("input.search").bind("blur", function(){
            var ctl = $(this);
            var rid = $(ctl).val();
            var txtCtl = $("#input_app_name");
            var btn = $("button.add");
            var posting = $.post("${rc.contextPath}/auth/promote/search", {"id" : rid});
            posting.done(function(result){
                if(result.success){
                    $(txtCtl).val(result.data.name);
                    $("#input_plist").val(result.data.downloadUrl);
                    $("#input_cert").val(result.data.certSerial);
                    $(btn).removeAttr("disabled");
                }else{
                    $(txtCtl).val(result.message);
                    $(btn).attr("disabled", "disabled");
                }
            });
        });

        $('.edit_td').click(function() {
            var pid = $(this).attr("pid");
            var preValue = $('#p_'+pid).attr("preValue");
            var input = $('<input id="attribute" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />')
            $('#p_'+pid).text('').append(input);
            input.select();
            input.blur(function() {
                var preValue = $(this).parent().attr("preValue");
                var hidden = $("#hidden_"+pid);
                var appTitle = $(hidden).attr("app_title");
                var curValue = $('#attribute').val();
                if(curValue == ""){
                    $(this).val(preValue);
                    $('#attribute').parent().text(preValue);
                    $('#attribute').remove();
                    return;
                }
                if(curValue == preValue){
                    var text = $('#attribute').val();
                    $('#attribute').parent().text(preValue);
                    $('#attribute').remove();
                    return;
                }
                bootbox.confirm("确定更改？", function(result) {
                    if(result){
                        var postUrl = "${rc.contextPath}/auth/rootapptag/modify";
                        var posting = $.post(postUrl, {'id': pid, "tag": curValue});
                        posting.done(function(errMsg){
                            if(errMsg){//
                                //show msg
                                alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
                                $('#attribute').parent().text(preValue);
                                $('#attribute').remove();
                            }else{
                                alert("修改成功.");
                                $('#attribute').parent().text(curValue).attr("preValue", curValue);
                                $('#attribute').remove();
                            }
                        });
                    }else{
                        $('#attribute').parent().text(preValue);
                        $('#attribute').remove();
                    }
                });
            });
        });

        $('.edit-score-td').click(function() {
            var pid = $(this).attr("pid");
            var preValue = $('#p2_'+pid).attr("preValue");
            var input = $('<input id="attribute2" type="text" class="span1" preValue="' + preValue + '" value="' + preValue + '" />')
            $('#p2_'+pid).text('').append(input);
            input.select();
            input.blur(function() {
                var preValue = $(this).parent().attr("preValue");
                var hidden = $("#hidden_"+pid);
                var appTitle = $(hidden).attr("app_title");
                var curValue = $('#attribute2').val();
                if(curValue == ""){
                    $(this).val(preValue);
                    $('#attribute2').parent().text(preValue);
                    $('#attribute2').remove();
                    return;
                }
                if(curValue == preValue){
                    var text = $('#attribute2').val();
                    $('#attribute2').parent().text(preValue);
                    $('#attribute2').remove();
                    return;
                }
                bootbox.confirm("确定更改？", function(result) {
                    if(result){
                        var postUrl = "${rc.contextPath}/auth/rootapptag/modify";
                        var posting = $.post(postUrl, {'id': pid, "score": curValue});
                        posting.done(function(errMsg){
                            if(errMsg){//
                                //show msg
                                alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
                                $('#attribute2').parent().text(preValue);
                                $('#attribute2').remove();
                            }else{
                                alert("修改成功.");
                                $('#attribute2').parent().text(curValue).attr("preValue", curValue);
                                $('#attribute2').remove();
                            }
                        });
                    }else{
                        $('#attribute2').parent().text(preValue);
                        $('#attribute2').remove();
                    }
                });
            });
        });

        $("button.delete").bind("click",function(){
            event.preventDefault();
            bootbox.setDefaults({
                locale: "zh_CN"
            });
            var eventCtl = $(this);
            var id = $(this).attr("app_id");
            var hidden = $("#hidden_"+id);
            var appTitle = $(hidden).attr("app_title");
            var appChannel = $(hidden).attr("app_channel");
            var postUrl = "${rc.contextPath}/auth/rootapptag/modify";
            var preStatus = $(hidden).attr("promote_status");
            var action = preStatus == 0 ? "暂停":"开始";
            var setStatus = preStatus == 0 ? -1 : 0;

            bootbox.confirm("是否在更改应用的标签状态?", function(result) {
                if(!result){
                    return;
                }
                var posting = $.post(postUrl, {'id': id, "status": setStatus});
                posting.done(function(errMsg){
                    if(errMsg){//
                        //show msg
                        alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
                    }else{
                        var statusSpan = $("#status_span_"+id);
                        var positiveEventCtl =  preStatus == 0 ? $("#promote_btn_"+id) : $("#unpromote_btn_"+id);
                        var statusInfo= preStatus == 0 ? "删除" : "正常";
                        var removeClass= preStatus == 0 ? "btn-success" : "btn-danger";
                        var addClass= preStatus == 0 ? "btn-danger" : "btn-success";
                        $(hidden).attr("promote_status", setStatus);
                        $(positiveEventCtl).removeAttr("disabled");
                        $(eventCtl).attr("disabled", "disabled");
                        $(statusSpan).removeClass(removeClass).addClass(addClass);
                        $(statusSpan).text(statusInfo);
                        alert("操作成功，已"+action+"推荐");
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