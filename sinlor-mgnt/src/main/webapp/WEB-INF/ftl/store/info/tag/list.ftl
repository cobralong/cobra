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
            <li>标签列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div id="div_query" class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
                                <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
                                <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
                                <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">

                                <div class="input-prepend">
                                    <span class="">状态</span>
                                <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info search">
                                    <i class="icon-search icon-white"></i>过滤
                                </button>

                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>颜色</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                        <input id="hidden_${value.id}" type="hidden" tagName="${value.name}"
                                               status="${value.status}"/>
                                        <td class="edit_td" default_id="${value.id?c}" property="name" td_name="${value.name!}" for_class="span4">
                                       		<label for="name" class="span4">
                                       			<span id="span_name_${value.id?c}" class="text-info" pre_value="${value.name!}">${ value.name! }</span>
                                       		</label>
                                       	</td>
                                        <td class="edit_td" default_id="${value.id?c}" property="color" td_name="${value.name!}" for_class="span1">
                                            <label for="name" class="span1">
                                                <span id="span_color_${value.id?c}" class="text-info" pre_value="${value.color!''}"
                                                	 style="background-color: ${value.color!}">${ value.color! }</span>
                                            </label>
                                        </td>
                                        <#--<td>-->
                                            <#--<input type="color" value=""/>-->
                                        <#--</td>-->
                                        <td>
                                            <button id="promote_btn_${value.id}"
                                                    class="btn btn-success delete glyphicon glyphicon-trash "
                                                    app_id="${value.id?c}"  <#if value.status == 0>disabled</#if>>
                                                上线
                                            </button>
                                            <button id="unpromote_btn_${value.id}" class="btn btn-danger delete"
                                                    app_id="${value.id?c}"  <#if value.status != 0>disabled</#if>>
                                                下线
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


    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("app_id");
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
        var title = $(hidden).attr("tagname");
        var postUrl = "${rc.contextPath}/auth/store/articletag/modify";
        var preStatus = $(hidden).attr("status");
        var actionMsg = preStatus == 0 ?
                "是否将应用【【" + title + "】】从上线列表中移除?" :
                "是否将应用【【" + title + "】】添加到线上列表?";
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
                            "成功将应用【【" + title + "】】从上线列表中移除!" :
                            "成功将应用【【" + title + "】】添加到线上列表!";
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

    

		$('.edit_td').click(function() {
			var id = $(this).attr("default_id");
			var property = $(this).attr("property");
			var spanCtr = $("#span_" + property + "_" + id);
			var preValue = $(spanCtr).attr("pre_value");
			var forClass = $(this).attr("for_class");
			var tdName = $(this).attr("td_name");
			var input = $('<input id="attribute" type="text" class="' + forClass + '" preValue="' + preValue + '" value="' + preValue + '" />')
			$(spanCtr).text('').append(input);
			input.select();
			input.blur(function() {			
				var curValue = $('#attribute').val();
				if(curValue == ""){
					$(this).val(preValue);
					$(this).focus();
					return ;
				}
				if(curValue == preValue){
	   			var text = $('#attribute').val();
	   			$('#attribute').parent().text(preValue);
	   			$('#attribute').remove();
   				return ;
   			}
   			var typeName = (property == "title") ? "标题" : "颜色";
				bootbox.confirm("将标签【" + tdName  +"】的" + typeName + "由【" + preValue + "】改为【" + curValue + "】?", function(result) {
	 			if(result){
						var postUrl = "${rc.contextPath}/auth/store/articletag/modify";
						var postData = "id=" + id + "&" + property + "=" + curValue;
		 			var posting = $.post(postUrl, postData);
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
				   			$('#attribute').parent().text(preValue);
				   			$('#attribute').remove();
		 				}else{
		 					alert("修改成功.");
		 					$('#attribute').parent().attr("pre_value", curValue);
				   			$('#attribute').parent().text(curValue);				   			
				   			if(property == 'color'){
			   					$('#attribute').parent().css("background-color",curValue );
				   			}
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
</script>
</body>
</html>