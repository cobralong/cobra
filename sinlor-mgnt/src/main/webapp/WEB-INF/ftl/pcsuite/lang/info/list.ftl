<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-AppStore管理后台-PC助手多语言信息配置列表</title> <#include
"lib/base_source.ftl">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<style type="text/css">
.small_sel {
	width: 120px;
}
</style>
</head>
<body>
	<#include "/lib/header.ftl">
	<div>
		<ul class="breadcrumb">
			<li>AppStore管理后台</li>
			<span class="divider">/</span>
			<li>PC助手多语言信息配置列表</li>
		</ul>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="container span12">
				<#include "lib/alert.ftl">
				<div class='main-content'>
					<div class="table-content">
						<#-- table -->
						<p>关于langId的更多帮助请查看<a target="_blank" src="https://msdn.microsoft.com/en-us/library/windows/desktop/dd318693(v=vs.85).aspx">这里</a></p>
						<form id="itemForm" name="itemForm" action="" method="post">
							<input type="hidden" id="pager_page" name="pager.page"
								value="${para.pager.page}"> <input type="hidden"
								id="pager_size" name="pager.size" value="${para.pager.size}">
							<input type="hidden" id="pager_total" name="pager.total"
								value="${para.pager.total}">							
							<table class="table table-striped table-bordered table-condensed"
								id="itemTable">
								<thead>
									<tr>
										<th>LangId</th>
										<th>OxValue</th>
										<th>描述</th>
										<th>更新时间</th>
										<th>入库时间</th>										
									</tr>
								</thead>
								<tbody class="sortable">
									<#if values??> <#list values as value>
									<tr>
										<td>${value.id}</td>
										<td>${value.hexValue}</td>
										<td>
											<select default-id="${value.id?c}" pre-value="${value.language}">
												<#list appLanguages?keys as key>
													<#if key == value.language>
														<option value="${appLanguages[key]}" selected>${key}</option>
													<#else>
														<option value="${appLanguages[key]}">${key}</option>
													</#if>
												</#list>
											</select>
										</td>
										<td><#if value.updateTime ??> ${
											value.updateTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
											</#if></td>
										<td><#if value.createTime ??> ${
										value.createTime?string("yyyy-MM-dd HH:mm:ss") } <#else> -
										</#if></td>
									</tr>
									</#list> </#if>
								</tbody>
							</table>
						</form>
						<div class="pagination" id="itemPage"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script>
		$(document).ready(function() {
		    var currPageDiv = "#pager_page";
		    var totalDiv = "#pager_total";
		    var sizeDiv = "#pager_size";
		    $.manage.pageList(".pagination", "#itemForm", currPageDiv, totalDiv, sizeDiv);
		});
		$(document).on( 'change', 'select', function(){
	    	var selCtr = $(this);
	    	var id = $(this).attr('default-id');
	    	var preValue = $(this).attr('pre-value');
	    	var curValue = $(this).val();
	    	bootbox.confirm("将Id【" + id + "】的指代语言改为【" +  curValue + "】?", function(result) {
	    		if(!result){$(selCtr).val(preValue);return;}
		    	var postUrl = "${rc.contextPath}/auth/pcsuite/lang/info/modify.json";
		    	var postData = "id=" + id + "&language=" + curValue;
	 			var posting = $.post(postUrl, postData);
	 			posting.done(function(resp){
	 				if(!resp.data){//
	 					//show msg
	 					alert("操作失败,请联系该死的开发人员.Msg:"+resp.message);
	 					$(selCtr).val(preValue);
	 				}else{
	 					$(selCtr).attr("pre-value", curValue);
	 					alert("修改成功.");
	 				}
	 			});
	 		});
	    });
	</script>
</body>
</html>