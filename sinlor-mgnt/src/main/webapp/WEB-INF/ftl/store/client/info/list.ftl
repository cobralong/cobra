<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/store_header.ftl">
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>客户端列表</li>
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
                            <form id="searchForm" name="searchForm" action="${rc.contextPath}/auth/store/client/info/add" method="post">
                            	<input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                    <span class="add-on">客户端名称</span>
                                    <input class="span2" name="name" type="text" placeholder="客户端名称" value="">
                            	</div>
                                <div class="input-prepend">
                                    <span class="add-on">客户端包名</span>
                                    <input id="input_app_name" class="span4" name="bundleId" type="text" placeholder="包名" value="">
                                </div>
                                <button class="btn btn-info add" >
                                    <i class="icon-search icon-white"></i>增加
                                </button>
                                <div id="addInfoDiv" errMsg="${errMsg}">
                                	<#if errMsg??>
                        				<font>${errMsg}</font>
                        			</#if>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="table-content">                    
                    <#-- table -->
                        <form id="itemForm" name="itemForm" method="post">
                        	<h4>当前拥有应用<font color="green">${para.pager.total}</font>个上架应用</h4>
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>
                                    <th>客户端名称号</th>
                                    <th>客户端包名</th>
                                    <th>状态</th>
                                    <th>修改时间</th>
                                    <th>入库时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if values??>
                                    <#list values as value>
                                    <tr>
                                        <td class="edit_td" default_id="${value.id?c}"
												property="name"
												td_name="${value.bundleId!}"
												for_class="span3"><label for="name" class="span3"><span
													id="span_name_${value.id?c}" class="text-info"
													preValue="${value.name!}">${ value.name! }</span></label></td> 
                                        <td>
                                        	${value.bundleId}
                                        </td>
                                        <td>
                                        	<#if value.status == 0>
                                        		<font color="green">√</font>
                                        	<#else>
                                        		<font color="red">X</font>
                                        	</#if>
                                        </td>
                                        <td>
                                            <#if value.updateTime ??>
                                            ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                            <#if value.createTime ??>
                                            ${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
                                            <#else>
                                                -
                                            </#if>
                                        </td>
                                        <td>
                                        	<#if value.status !=0>                                        	
		                                    	<button  class="btn btn-danger" disabled>
		                                    		已下架                                  	
		                            			</button>
                                			<#else>
                                				<button id="btn_delete_${value.id}" class="btn btn-danger delete" default_name = "${value.name}" default_id = "${value.id?c}" >
	                                        		被下架
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
     <script>     
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    $('.edit_td').click(function() {
		    var id = $(this).attr("default_id");
		    var property = $(this).attr("property");
		    var spanCtr = $("#span_" + property + "_" + id);
		    var preValue = $(spanCtr).attr("preValue");
		    var tdName = $(this).attr("td_name");
		    var spanClass = $(this).attr('for_class')
		    var input = $('<input id="attribute" type="text" class="' + spanClass + '" preValue="' + preValue + '" value="' + preValue + '" />')
		    $(spanCtr).text('').append(input);
		    input.select();
		    input.blur(function() {
		        var curValue = $('#attribute').val();
		        if (curValue == preValue) {
		            var text = $('#attribute').val();
		            $('#attribute').parent().text(preValue);
		            $('#attribute').remove();
		            return;
		        }
		        if (curValue == "") {
		            $(this).val(preValue);
		            $(this).focus();
		            return;
		        }
		        bootbox.confirm("将应用【" + tdName + "】的名称由【" + preValue + "】改为【" + curValue + "】?", function(result) {
		            if (result) {
		                var postUrl = "${rc.contextPath}/auth/store/client/info/modify.json";
		                var postData = "id=" + id + "&" + property + "=" + curValue;
		                var posting = $.post(postUrl, postData);
		                posting.done(function(resp) {
		                    if (!resp.data) { //
		                        //show msg	 					
		                        alert("操作失败,请联系该死的开发人员.Msg:" + resp.message);
		                        $('#attribute').parent().text(preValue);
		                        $('#attribute').remove();
		                    } else {
		                        alert("修改成功.");
		                        $('#attribute').parent().attr("preValue", curValue);
		                        $('#attribute').parent().text(curValue);
		                        $('#attribute').remove();
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