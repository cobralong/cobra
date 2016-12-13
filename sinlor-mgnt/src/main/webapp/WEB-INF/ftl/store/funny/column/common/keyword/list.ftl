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
            <li>亚亚详情应用跳转关键字控制页面</li>
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
                            <form id="addForm" name="addForm" action="${rc.contextPath}/funny/client/common/keywordsearch/add" method="post">
                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                    <span class="add-on">应用RootId</span>
                                    <input class="span2 search" name="rootId" type="text" placeholder="rootId" value="">
                            	</div>
                            	<div class="input-prepend">
	                                    <span class="add-on">授权应用</span>
	                                    <input id="input_app_auth_status" class="span2" style="width:200px" name="authStatus" type="text" placeholder="应用授权状态" disabled value="">
	                            </div>
                            	<div class="input-prepend">
										<span class="add-on">应用名称</span> <input id="input_app_name"
											class="span2" style="width: 200px" name="name" type="text"
											placeholder="应用名称" disabled value=""> <span
											class="add-on">OTA包</span> <input id="input_plist"
											class="span2" style="width: 200px" name="name" type="text"
											placeholder="OTA包" disabled value=""> <input
											id="input_cert" class="span2" style="width: 100px"
											name="name" type="text" placeholder="签名" disabled value="">
								</div>
								<br /> <br />
                            	<div class="input-prepend">
										<span class="add-on">关键字</span>
										<input class="span2" name="keyword" type="text" placeholder="关键字" value="">
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
                                    <th>应用RootId</th>
                                    <th>应用名称</th>
                                    <th>应用图标</th>
                                    <th>关键字</th>
                                    <th>搜索链接</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr id="tr_${value.id}">
                                    	<#assign rootSimple = rootSimples[value.rootId?c]>
                                    	<td>
                                    		${value.rootId}                                     
                                        </td>
                                        <td>
                                        	<@hrefapp value.rootId, rootSimple.name/>
                                        </td> 
                                        <td>
                                        	<@appicon rootSimple.icon, rootSimple.name/>
                                        </td>
                                         <td class="edit_td" default_id="${value.id?c}" for="keyword_${value.id?c}" name="keyword">
	                                        	<label for="keyword" class="span3">
	                                        	<span id="keyword_${value.id?c}" class="text-info span3" default_id = "${value.id?c}" pre_value="${value.keyword!}">
	                                        		${value.keyword!}
	                                        	</span>
	                                        	</label>
	                                    </td>
	                                    <td>
	                                    	<a href="https://itunes.apple.com/WebObjects/MZStore.woa/wa/search?mt=8&submit=edit&term=${value.keyword!}">搜索链接</a>
	                                    </td>
                                        <td><#if value.status == 0> <font
											id="font_status_${value.id}" color="green">√</font> <#else> <font
											id="font_status_${value.id}" color="red">X</font> </#if>
										</td>
                                        <td>
                                    	<#if value.status == 0>
                                        	<button id="btn_delete_${value.id?c}" class="btn btn-danger delete" default_id="${value.id?c}" set_value="-1" cur_value="0" flag="1">
                                        		删除
                                			</button>
                            			<#else>                                			
                                    		<button id="btn_delete_${value.id?c}" class="btn btn-success delete" default_id="${value.id?c}" set_value="0" cur_value="-1" flag="1">
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

    $("button.delete").bind("click", function () {
        event.preventDefault();
        bootbox.setDefaults({
            locale: "zh_CN"
        });
        var eventCtl = $(this);
        var id = $(this).attr("default_id");
        var name= $("#keyword_"+id).attr("pre_value");
        var trCtl = $("#tr_" + id);
        var hidden = $("#hidden_" + id);
        var postUrl = "${rc.contextPath}/funny/client/common/keywordsearch/modify";
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
 				bootbox.confirm("将关键字由【" + preValue + "【改为】" + curValue + "】?", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/funny/client/common/keywordsearch/modify";
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
		
		$("input.search").bind("blur", function() {
		    var ctl = $(this);
		    var rid = $(ctl).val();
		    var txtCtl = $("#input_app_name");
		    var txtStatus = $("#input_app_auth_status");
		    var btn = $("button.add");
		    var posting = $.post("${rc.contextPath}/auth/promote/search", {
		        "id": rid
		    });
		    posting.done(function(result) {
		        if (result.success) {
		            $(txtCtl).val(result.data.name);
		            $("#input_plist").val(result.data.downloadUrl);
		            $("#input_cert").val(result.data.certSerial);
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
</script>
</body>
</html>