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
            <li>值得玩专栏作者</li>
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
                    
                    <button class="btn addToggle glyphicon-pushpin btn-primary">新增框</button>
                    <div id="div_add" class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/funny/client/author/add" method="post">
                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
		                        <div class="input-prepend">
                                    <span class="add-on">用户名</span>
                                    <input class="span2" name="userName" type="text" placeholder="用户名" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">昵称</span>
                                    <input class="span2" name="name" type="text" placeholder="昵称" value="">
                            	</div>
                            	<div class="input-prepend">
                            		<span class="add-on">头像</span>
                            		<input id="input_icon" class="span2 search" name="icon" type="text" placeholder="头像地址" value="">
                            		<img id="img_author_icon" style="width:66px; height:66px" src="" alt="作者头像"/>
                            	</div>
                                <button class="btn btn-info add" >
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${errMsg}">
                                	<#if errMsg??>
                        				<font color="red">${errMsg}</font>
                        			</#if>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                	<th>用户名</th>
                                    <th>昵称</th>
                                    <th>头像</th>
                                    <th>状态</th>
                                    <th>编辑</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<td id="user_name_${value.id}">${value.userName}</td>
                                        <td id="name_${value.id}">${value.name}</td>
	                                    <td><a href="${value.icon}" target="_blank"><img
												style="width: 50px; height: 50px" src="${value.icon}"
												alt="${value.icon}" /></a></td>
                                        <td><#if value.status == 0> <font
											id="font_status_${value.id}" color="green">√</font> <#else> <font
											id="font_status_${value.id}" color="red">X</font> </#if>
										</td>
                                        <td>
                                            <a class="btn btn-info query" href="${rc.contextPath}/funny/client/author/detail?id=${value.id}">编辑</a>                                            
                                        </td>
                                        <td>
                                    	<#if value.status == 0>
                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" default_id="${value.id?c}" set_value="-1" cur_value="0">
                                        		删除
                                			</button>
                            			<#else>                                			
                                    		<button id="btn_delete_${value.id?c}" class="btn btn-success delete" default_id="${value.id?c}" set_value="0" cur_value="-1">
                                    			恢复                             	
                            				</button>
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
    
    $("input.search").bind("blur", function () {
        var ctl = $(this);
        var url = $(ctl).val();
        var txtCtl = $("#img_author_icon");
        $(txtCtl).attr('src',url);
    });


    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("default_id");
        var name= $("#name_"+id).text();
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
        var postUrl = "${rc.contextPath}/funny/client/author/modify";
        var preStatus = $(this).attr("cur_value");
        var actionMsg = preStatus == 0 ?
                "是否将【【" + name + "】】从Tab列表中移除?" :
                "是否将【【" + name + "】】添加到Tab列表?";
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
                            "成功将【【" + name + "】】从Tab列表中移除!" :
                            "成功将【【" + name + "】】添加到Tab列表!";
                    alert(responseMsg);
                    trCtl.remove();
                }
            });
        });
    });
    
    $('.edit_td').click(function() {
 			var id = $(this).attr("default_id");
 			var forCtr = $(this).attr("for");
 			var preValue = $("#" + forCtr).attr("pre_value");
 			var paraName = $(this).attr("name"); 			
 			var input = $('<input id="attribute" type="text" class="span2" pre_value="' + preValue + '" value="' + preValue + '" />')
 			$("#" + forCtr).text('').append(input);
 			input.select();
 			input.blur(function() {
 				var preValue = $(this).parent().attr("pre_value");				
 				var curValue = $('#attribute').val();
 				if(curValue == preValue){
		   			$('#attribute').parent().text(preValue);
		   			$('#attribute').remove();
	   				return;
	   			}
 				var typeName =  "分类名称";
 				bootbox.confirm("将"+typeName+"由【" + preValue + "【改为】" + curValue + "】?", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/funny/client/author/modify";
 						var postData = "id=" + id + "&" + paraName + "=" + encodeURIComponent(curValue);
			 			var posting = $.post(postUrl, postData);
			 			posting.done(function(errMsg){
			 				if(errMsg){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
					   			$('#attribute').parent().text(preValue);
					   			$('#attribute').remove();
			 				}else{
			 					alert("修改成功.");
					   			$('#attribute').parent().text(curValue);
					   			 $('#p_' + id).attr("pre_value", curValue);
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