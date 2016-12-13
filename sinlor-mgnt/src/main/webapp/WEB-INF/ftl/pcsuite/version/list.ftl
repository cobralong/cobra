<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-PC助手版本列表</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "/lib/header.ftl">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li><span class="divider">/</span>
            <li>PC助手版本号列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="searchForm" name="searchForm" action="" method="post">
		                        <input type="hidden" id="pager_page" name="pager.page" value="${para.pager.page}">
		                        <input type="hidden" id="pager_size" name="pager.size" value="${para.pager.size}">
		                        <input type="hidden" id="pager_total" name="pager.total" value="${para.pager.total}">
                                <div class="input-prepend">
                                	<span class="add-on">Pc助手客户端渠道</span>
                                    <select name="channel">
                                		<#list channels as channel>
                                			<option value="${channel.channel}">${channel.name}-----${channel.channel}</option>
                                		</#list>
                                	</select>
                                </div>                                
                                <div class="input-prepend">
                                	<span class="">状态</span>
                                     <@spring.formSingleSelect "para.status", status, " " />
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>过滤
                                </button>
                            </form>
                        </div>
                    </div>
                    <div class="well form-inline">
                        <div class='main-action'>
                            <form id="addForm" name="addForm" action="${rc.contextPath}/auth/pcsuite/version/add" method="post">
                                <div class="input-prepend">
                                	<span class="add-on">Pc助手客户端渠道</span>
                                	<select name="channel">
                                		<#list channels as channel>
                                			<option value="${channel.channel}">${channel.name}-----${channel.channel}</option>
                                		</#list>
                                	</select>
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">版本号：</span>
                                	<input class="span2" name="version" type="text" placeholder="1.0.0" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">版本代码：</span>
                                	<input class="span2" name="versionCode" type="text" placeholder="越大版本越高，数值" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">版本称号：</span>
                                	<input class="span2" name="versionName" type="text" placeholder="版本称号" value="">
                                </div>
                                <br/>
                                <br/>
                                <div class="input-prepend">
                                	<span class="add-on">更新文件地址：</span>
                                	<input class="span4" name="url" type="text" placeholder="文件CDN地址" value="">
                                </div>
                                <div class="input-prepend">
                                	<span class="add-on">更新文件Md5值：</span>
                                	<input class="span2" name="md5" type="text" placeholder="文件MD5值" value="">
                                </div>                                
                                <div class="input-prepend">
                                	<span class="add-on">升级时的IP开关：</span>
                                	<@spring.formSingleSelect "para.productIpSwitch", productIpSwitches, " " />
                                </div>
                                <br/><br/>
                                <div class="input-prepend">
                                	<span class="add-on">更新说明：</span>
                                	<textarea name="upgradeInfo" class="span5" cols="360" rows="4"></textarea>
                                </div>
                                <br/><br/>
                                <div class="input-prepend">
                                	<span class="add-on">更新说明(En)：</span>
                                	<textarea name="upgradeInfoEn" class="span5" cols="360" rows="4" placeholder="不填，则采用上面已填的更新说明"></textarea>
                                </div>
                                <button class="btn btn-info query" >
                                    <i class="icon-search icon-white"></i>添加
                                </button>
                            </form>
                            <#if errMsg>
                            	<font color="red">添加失败，原因：${errMsg}</font> 
                            </#if>
                        </div>                        
                    </div>
                    <div class="table-content">
                    <#-- table -->
                    	<h4>当前有记录<font color="green">${para.pager.total}</font>条<h4>
                        <form id="itemForm" name="itemForm" action="" method="post">
                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
                                <thead>
                                <tr>                                    
                                    <th>Id</th>
                                    <th>渠道</th>
                                    <th>版本号</th>
                                    <th>版本名称</th>
                                    <th>下载地址</th>
                                    <th>文件MD5值</th>
                                    <th>更新IP限制</th>
                                    <th>升级IP开关</th>
                                    <th>状态</th>
                                    <th>操作</th>
                                    <th>详情</th>
                                </tr>
                                </thead>
                                <tbody class="sortable">
                                <#if datas??>
                                    <#list datas as data>
                                    <tr>                                        
                                        <td>
                                        	${data.id}
                                        </td>
                                        <td>${ data.channel! }</td>
                                        <td>${ data.version! }</td>
                                        <td>${data.versionName! }</td>                                        
                                        <td><@shorthref data.url!/></td>
                                        <td>${data.md5}</td>                                        
                                        <td id="td_product_ip_switch_${data.id?c}">${productIpSwitches[data.productIpSwitch?c]}</td>                                                                                
                                        <td>
                                        	<#if data.productIpSwitch == 0>
                                    			<span default_id = ${data.id} property = "productIpSwitch" set_value="1" cur_value = "${data.productIpSwitch}" class="btn delete btn-danger">关闭</span>
                                    		<#else>
                                    			<span default_id = ${data.id} property = "productIpSwitch" set_value="0" cur_value = "${data.productIpSwitch}" class="btn delete btn-success">开启</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if data.status == 0>
                                        		<font id="status_font_${data.id}" color="green">√</font>
                                    		<#else>
                                    			<font id="status_font_${data.id}" color="red">X</font>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<#if data.status == 0>
                                    			<span default_id = ${data.id} property = "status" set_value = "-1"  cur_value = "${data.status}" class="btn delete btn-danger">删除</span>
                                    		<#else>
                                    			<span default_id = ${data.id} property = "status" set_value = "0" cur_value = "${data.status}" class="btn delete btn-success">正常</span>
                                    		</#if>
                                        </td>
                                        <td>
                                        	<a class="btn btn-info query" href="${rc.contextPath}/auth/pcsuite/version/detail?id=${data.id?c}">详情</a>
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
	<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
     <script>
	    $(document).ready(function(){
	        var currPageDiv = "#pager_page";
	        var totalDiv = "#pager_total";
	        var sizeDiv = "#pager_size";
	        $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
	    });
	    
 		$("span.delete").bind("click",function(){
 			event.preventDefault();
 			bootbox.setDefaults({
 				locale: "zh_CN"
 			});
 			var eventCtl = $(this);
 			var id = $(this).attr("default_id"); 			
 			var preStatus = $(this).attr("status");
 			var postUrl = "${rc.contextPath}/auth/pcsuite/version/modify.json";
 			var confirmMsg = "";
 			var property = $(this).attr("property");
 			var setValue = $(this).attr("set_value");
 			var preValue = $(this).attr("cur_value");
 			if(property == "status" ){
 				if(setValue == -1){
 					confirmMsg = "将Id:"+ id + "的记录状态设置为删除状态";
 				} else {
 					confirmMsg = "将Id:"+ id + "的记录状态设置为正常状态";
 				}
 			}else{
 				if(setValue == 1){
 					confirmMsg = "将Id:"+ id + "的对升级时IP控制开关设置为关闭状态";
 				} else {
 					confirmMsg = "将Id:"+ id + "的对升级时IP控制开关设置为开启状态";
 				}
 			}
	 		bootbox.confirm(confirmMsg+"?", function(result) {
	 			if(!result){
	 				return;
	 			}
	 			var postData = "id=" + id + "&" + property + "=" + setValue;
	 			var posting = $.post(postUrl, postData);
	 			posting.done(function(resp){
	 				if(!resp.data){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+resp.message);
	 				}else{
	 					alert("操作成功，已" + confirmMsg + "!");	 					
	 					$(eventCtl).attr("set_value", preValue);
	 					$(eventCtl).attr("cur_value", setValue);
	 					if(property == "status"){
	 						var fontCtl = $("#status_font_"+ id);
		 					if(setValue == 0){
		 						$(eventCtl).attr("class", "btn delete btn-danger");
		 						$(eventCtl).html("删除");
		 						$(fontCtl).attr("color", "green");
		 						$(fontCtl).html("√");
		 					}else{
		 						$(eventCtl).attr("class", "btn delete btn-success");
		 						$(eventCtl).html("正常");
		 						$(fontCtl).attr("color", "red");
		 						$(fontCtl).html("X");
		 					}
	 					}
	 					if(property == "productIpSwitch"){
	 						if(setValue == 0){
		 						$("#td_product_ip_switch_"+ id).html("只有特许IP方可升级");
		 						$(eventCtl).attr("class", "btn delete btn-danger");
		 						$(eventCtl).html("关闭");
	 						}else {
	 							$("#td_product_ip_switch_"+ id).html("无IP升级限制");
		 						$(eventCtl).attr("class", "btn delete btn-success");
		 						$(eventCtl).html("开启");
	 						}
	 					}
	 				}
	 			});
			});
		});
    </script>
</body>
</html>