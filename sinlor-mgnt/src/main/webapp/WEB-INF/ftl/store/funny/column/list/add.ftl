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
            <li>专栏列表编辑</li>
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
                                  action="${rc.contextPath}/funny/client/add" method="post">
                                <input type="hidden" name="id" value="${para.id}"/>
                                <input type="hidden" name="publish" id="isPublish"/>
                                <div class="input-prepend">
									<span class="add-on">专栏详情ID</span>
									<div class="controls input-prepend">
                            			<input id="columnDetId" type="text" name="columnDetailId" value="${para.columnDetailId}" readonly="readonly"/>
                            		</div>
								</div></br></br>
                                <div class="input-prepend">
                                    <span class="add-on">标题</span>
                                    <input id="input_title" class="span2" style="width:500px" name="title" type="text"
                                           placeholder="标题" value="${para.title}">
                                </div>
                                <div class="input-prepend">	                                    
                                    <span class="add-on">类型</span>
                                    <select id="sel_type" name="ctype" type='add' class="client_choose">
                                		<#list ctypes?keys as key>
                            				<option value="${key}" <#if para.ctype==key>selected="selected"</#if>>${ctypes[key]}</option>
                            			</#list>
                                	</select>
                            	</div>
                                <br/><br/>
                                <div class="input-prepend">	                                    
                                    <span class="add-on">作者</span>
                                    <select id="sel_author" name="authorId" type='add' class="client_choose" onchange='changetype(this.value)'>
                                		<#list authors?keys as key>
                            				<option value="${key}" <#if para.authorId==key>selected="selected"</#if>>${authors[key]}</option>
                            			</#list>
                                	</select>
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">头像</span>
                                    <#if para.authorId??>
                                    	<img id="img_author_icon" style="width:66px; height:66px" src="${authoricons[para.authorId+'']}" alt="头像"/>
                                    <#else>
                                    	<img id="img_author_icon" style="width:66px; height:66px" src="${authoricons['0']}" alt="头像"/>
                                    </#if>
                                </div>
                                <br/><br/>
                                <span class="add-on">发布时间</span>
                                <div id="startDatetimePicker" class="input-append date datepicker"
                                     data-date-format="yyyy-mm-dd hh:ii:00" data-date-autoclose="true">
                                    <input class="span2" id="startDateString" name="st" size="16" type="text" value="${para.relaeseTime?string("yyyy-MM-dd HH:mm:ss") }">
                                    <span class="add-on"><i class="icon-calendar"></i></span>
                                </div>
                                <br/><br/>
                                <span class="add-on">专栏列表背景图:</span>
								<#if para.img??>
									<input type="hidden" name="img" value="${para.img}"/>
									<div class="control-group">
										<div class="controls input-prepend">
											<img width="600px" height="280px" src="${para.img}" alt="${para.title}的背景图" title="${para.title}的背景图">
										</div>
									</div>
								<#else>
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
                                        <img data-src="holder.js/100%x100%" alt="...">
                                    </div>
                                    <div class="fileinput-preview fileinput-exists thumbnail"
                                         style="max-width: 600px; max-height: 280px	;"></div>
                                    <div>
                                        <span class="btn btn-default btn-file"><span
                                                class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input
                                                type="file" name="image"></span>
                                        <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                    </div>
                                </div>
                                <br/>
                                </#if>
                                <button class="btn btn-success add" id="saveColumnList">
                                    <i class="icon-download icon-white"></i>发布线上
                                </button>
                                <button class="btn btn-warning add" id="saveColumnListEdit">
                                    <i class="icon-pencil icon-white"></i>保存草稿
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
	$(document).ready(function () {
        var cid = $("#columnDetId").val();
        var ctl = $("#addInfoDiv");
        var posting = $.post("${rc.contextPath}/funny/client/edit/exist", {"id": cid});
        posting.done(function (result) {
            if (result.success) {
            	$(ctl).append("<font color='red'>详情ID为"+cid+" 的专栏列表在草稿箱中已存在或已发布，请前往专栏列表草稿查看或更新修改!</font>");
            } else {
            	//nothing
                $(ctl).append("<font color='red'>详情ID:"+cid+"的数据--"+result.message+"</font>");
            }
        });
    });
    
    $("#saveColumnList").click(function(){
	        	$("#isPublish").val(0);
	        	$("#searchForm").submit();
	 });
	 $("#saveColumnListEdit").click(function(){
	        	$("#isPublish").val(1);
	        	$("#searchForm").submit();
	 });

    function changetype(val) {
        var ctl = $("#img_author_icon");
        var posting = $.post("${rc.contextPath}/funny/client/author/search", {"id": val});
        posting.done(function (result) {
            if (result.success) {
                $(ctl).attr('src',result.data.icon);
            } else {
                $(ctl).attr('src',result.message);
            }
        });
    }

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