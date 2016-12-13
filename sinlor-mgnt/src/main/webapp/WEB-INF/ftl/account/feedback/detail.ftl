<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-用户反馈详情</title> <#include "lib/base_source.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>用户反馈详情</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div class="well form-inline">
							<div class='main-action'>
								<#if value??>
								<div class='main-action'>
									<div class="input-prepend">
										<span class="add-on">反馈序列号：</span> <input class="span2"
											name="platform" readonly="readonly" type="text"
											value="${value.id}">
									</div>
									<div class="input-prepend">
										<span class="add-on">反馈渠道：</span> <input class="span2"
											name="color" readonly="readonly" type="text"
											value="${value.channel}">
									</div>
									<div class="input-prepend">
										<span class="add-on">反馈者Ip：</span> <input class="span2"
											name="height" readonly="readonly" type="text"
											value="${value.ip}">
									</div>
									<div class="input-prepend">
										<span class="add-on">联系方式：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.contact}">
									</div>
									<div class="input-prepend">
										<span class="add-on">反馈处理状态：</span> <@spring.formSingleSelect
										"value.status", status />
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">反馈者Uid：</span> <input id="uid"
											class="span4" name="width" readonly="readonly" type="text"
											value="${value.uid}">
									</div>
									<div class="input-prepend">
										<span class="add-on">反馈信息：</span> <input class="span8"
											name="width" readonly="readonly" type="text"
											value="${value.feedback}">
									</div>
									<br /> <br />
									<form id="searchForm" name="searchForm"
										action="${rc.contextPath}/auth/account/feedback/modify"
										method="post">
										<input id="hidden_id" type="hidden" name="id"
											value="${value.id?c}" />
										<div class="input-prepend">
											<span class="add-on">回复：</span>
											<textarea name="responses" class="input-block-level"
												cols="360" rows="4">${value.responses}</textarea>
										</div>
										<br />
										<button class="btn btn-info add">
											<i class="icon-search icon-white"></i>回复
										</button>
									</form>
								</div>
								</#if> <#if errMsg?? && errMsg!=''> <font color="red">回复失败，原因：${errMsg}</font>
								</#if>
							</div>
						</div>
					</div>
				</div>
			</div>
</body>

	<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script>
		$(document).ready(function () {
			$("select option:selected").attr("preSel", $("select").text());
	    });
		$("select").bind("change", function(){
			event.preventDefault();
			bootbox.setDefaults({ locale: "zh_CN" }); 
			var status = $(this).val();
			var preSel = $(this).attr("preSel");
			var text = $("select option:selected").text();
			var id = $("#hidden_id").val();
			var uid = $("#uid").val();
			var selectCtl = $(this);
			bootbox.confirm("确认将用户【" + uid  +"】的反馈状态改为【" + text + "】改为?", function(result) {
	 			if(!result){
	 				$(selectCtl).val(preSel);
	 				return ;
	 			}
				var postUrl = "${rc.contextPath}/auth/account/feedback/status/modify.json";
				var postData = "id=" + id + "&status=" + status;
				var posting = $.post(postUrl, postData);
				posting.done(function(errMsg){
	 				if(errMsg){//
	 					//show msg	 					
	 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
	 					$(selectCtl).val(preSel);
	 				}else{
	 					alert("修改成功.");
	 					$("select").attr("preSel", text);
 					}
 				});
			});
		});
	</script>
</html>