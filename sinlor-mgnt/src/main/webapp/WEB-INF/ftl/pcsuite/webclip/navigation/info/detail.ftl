<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-Pc助手WebClip导航信息详情</title> <#include
"lib/base_source.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>Pc助手WebClip导航信息详情</li>
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
										<span class="add-on">Id：</span> <input class="span1" name="id"
											id="data-id" readonly="readonly" type="text"
											value="${value.id}">
									</div>
									<div class="input-prepend">
										<span class="add-on">标题：</span> <input class="span2"
											cur-value="${value.title}" name="title" type="text"
											value="${value.title}">
									</div>
									<div class="input-prepend">
										<span class="add-on">描述：</span> <input class="span4"
											cur-value="${value.desc}" name="desc" type="text"
											value="${value.desc}">
									</div>
									<div class="input-prepend">
										<span class="add-on">底部描述：</span> <input class="span3"
											cur-value="${value.bottom}" name="bottom" type="text"
											value="${value.bottom}">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">标题【繁体】：</span> <input class="span2"
											cur-value="${value.titleTw}" name="titleTw" type="text"
											value="${value.titleTw}">
									</div>
									<div class="input-prepend">
										<span class="add-on">描述【繁体】：</span> <input class="span4"
											cur-value="${value.descTw}" name="descTw" type="text"
											value="${value.descTw}">
									</div>
									<div class="input-prepend">
										<span class="add-on">底部描述【繁体】：</span> <input class="span3"
											cur-value="${value.bottomTw}" name="bottomTw" type="text"
											value="${value.bottomTw}">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">标题【英文】：</span> <input class="span2"
											cur-value="${value.titleEn}" name="titleEn" type="text"
											value="${value.titleEn}">
									</div>
									<div class="input-prepend">
										<span class="add-on">描述【英文】：</span> <input class="span4"
											cur-value="${value.descEn}" name="descEn" type="text"
											value="${value.descEn}">
									</div>
									<div class="input-prepend">
										<span class="add-on">底部描述【英文】：</span> <input class="span3"
											cur-value="${value.bottomEn}" name="bottomEn" type="text"
											value="${value.bottomEn}">
									</div>
								</div>
								</#if>
							</div>
							<!-- main-action -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript">
		$('input')
				.bind(
						"blur",
						function(e) {
							var id = $("#data-id").val();
							var preValue = $(this).attr("cur-value");
							var curValue = $(this).val();
							if (preValue == curValue) {
								return;
							}
							var eventCtl = $(this);
							var properties = $(this).attr("name");
							bootbox
									.confirm(
											"是否对" + properties + "进行更改,由【"
													+ preValue + "】改为【"
													+ curValue + "】?",
											function(result) {
												if (!result) {
													$(eventCtl).val(preValue);
												}
												var postUrl = "${rc.contextPath}/auth/pcsuite/webclip/navigation/info/modify.json";
												var postData = "id="
														+ id + "&"
														+ properties + "="
														+ curValue;
												var posting = $.post(postUrl,
														postData);
												posting
														.done(function(resp) {
															if (!resp.data) {//
																//show msg	 					
																alert("操作失败,请联系该死的开发人员.Msg:"
																		+ resp.message);
																$(eventCtl)
																		.val(
																				preValue);
															} else {
																alert("操作成功");
																$(eventCtl)
																		.attr(
																				"cur-value",
																				curValue);
															}
														});
											});
						});
	</script>
</body>
</html>