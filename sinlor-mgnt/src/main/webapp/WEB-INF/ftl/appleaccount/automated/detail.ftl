<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen"
	href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-管理后台-机器注册账号详情</title> <#include "lib/base_source.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>机器注册账号详情</li>
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
										<span class="add-on">AppleId：</span> <input class="span2"
											name="platform" readonly="readonly" type="text"
											value="${value.appleId}">
									</div>
									<div class="input-prepend">
										<span class="add-on">密码：</span> <input class="span2"
											name="color" readonly="readonly" type="text"
											value="${value.password}">
									</div>
									<div class="input-prepend">
										<span class="add-on">账号名称[FirstName---LastName]：</span> <input
											class="span2" name="height" readonly="readonly" type="text"
											value="${value.firstName}---${value.lastName}">
									</div>
									<div class="input-prepend">
										<span class="add-on">密码邮箱：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityMail}">
									</div>
									<div class="input-prepend">
										<span class="add-on">密码邮箱密码：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.mailPassword}">
									</div>
									<div class="input-prepend">
										<span class="add-on">生日：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.birthday}">
									</div>
									<div class="input-prepend">
										<span class="add-on">账号类型：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${automatedTypes[value.automatedType?c]}">
									</div>
									<div class="input-prepend">
										<span class="add-on">激活状态：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${activeStatuses[value.activeStatus?c]}">
									</div>
									<div class="input-prepend">
										<span class="add-on">账号状态：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${statuses[value.status?c]}">
									</div>
									<div class="input-prepend">
										<span class="add-on">更新时间：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value=" ${ value.updateTime?string("yyyy-MM-ddHH:mm:ss") }">
									</div>

									<div class="input-prepend">
										<span class="add-on">入库时间：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value=" ${ value.createTime?string("yyyy-MM-ddHH:mm:ss") }">
									</div>
									<br />
									<div class="input-prepend">
										<span class="add-on">地址信息：</span> <input class="span4"
											name="width" readonly="readonly" type="text"
											value="${value.address}">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">安全问题1：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityQuestion0}"> <span>若为select_by_index(1)则为苹果密码问题列表中的第1个</span>
									</div>
									<div class="input-prepend">
										<span class="add-on">安全问题1答案：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityAnswer0}">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">安全问题2：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityQuestion1}"> <span>若为select_by_index(1)则为苹果密码问题列表中的第1个</span>
									</div>
									<div class="input-prepend">
										<span class="add-on">安全问题2答案：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityAnswer1}">
									</div>
									<br /> <br />
									<div class="input-prepend">
										<span class="add-on">安全问题3：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityQuestion2}"> <span>若为select_by_index(1)则为苹果密码问题列表中的第1个</span>
									</div>
									<div class="input-prepend">
										<span class="add-on">安全问题3答案：</span> <input class="span2"
											name="width" readonly="readonly" type="text"
											value="${value.securityAnswer2}">
									</div>
								</div>
								</#if>
							</div>
						</div>
					</div>
				</div>
			</div>
			<script type="text/javascript"
				src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
</body>
</html>