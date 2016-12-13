<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen" />
<title>应用汇-管理后台-授权应用下载反馈详情</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权应用下载反馈详情</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<p>下载反馈详情</p>
								<div class="input-prepend">
									<span class="add-on">应用Root Id：</span> <input disabled
										class="span2" value="${value.rootId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">使用账号：</span> <input disabled class="span2"
										value="${value.appleId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">纯净版ipa下载地址：</span> <@shorthref
									value.pureIpaUrl!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">iTunes Metadata文件下载地址：</span> <@shorthref
									value.itunesMetadataUrl!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">iTunes artwork文件地址：</span> <@shorthref
									value.itunesArtworkUrl!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">sinf地址：</span> <@shorthref
									value.sinfUrl!''/>
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">反馈处理说明：</span> <input disabled
										class="span3" value="${value.info}">
								</div>
								<div class="input-prepend">
									<span class="add-on">反馈处理状态：</span> <input disabled
										class="span2"
										value="${feedbackStatus[value.feedbackStatus?c]}">
								</div>
								<div class="input-prepend">
									<span class="add-on">反馈信息：</span> <input disabled class="span4"
										value="${value.errMsg}">
								</div>
								<div class="input-prepend"> <span class="add-on">记录状态：</span>
									<#if value.status == 0> <font color="green">√</font> <#else> <font
										color="red">X</font> </#if>
								</div>
							</div>
						</div>
					</div>
					<br /> <br />
					<div class="main-content">
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<#if appSimple>
								<p>授权ipa应用详情</p>
								<div class="input-prepend">
									<span class="add-on">应用RootId：</span> <input disabled
										class="span2" value="${appSimple.rootId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用标题：</span> <input disabled class="span2"
										value="${appSimple.name!}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用图标：</span> <img
										style="width: 45px; height: 45px" src="${appSimple.icon!}" />
								</div>
								<div class="input-prepend">
									<span class="add-on">应用版本：</span> <input disabled class="span2"
										value="${appSimple.version!}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用网站地址：</span><@hrefapp appSimple.rootId,
									appSimple.name/>
								</div>
								<div class="input-prepend">
									<span class="add-on">iTunes地址：</span><@machrefapp
									rootApplication.itemId, appSimple.name/>
								</div>
								<#else> <#if rootApplication>
								<p>授权ipa应用详情---市场已下线</p>
								<div class="input-prepend">
									<span class="add-on">应用RootId：</span> <input disabled
										class="span2" value="${rootApplication.rootId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用标题：</span> <input disabled class="span2"
										value="${rootApplication.editorTitle!}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用图标：</span>
								</div>
								<div class="input-prepend">
									<span class="add-on">应用版本：</span>
								</div>
								<div class="input-prepend">
									<span class="add-on">应用网站地址：</span>
								</div>
								<div class="input-prepend">
									<span class="add-on">iTunes地址：</span><a
										href="${rootApplication.appStoreUrl!}">前往AppStore</a>
								</div>
								</#if> </#if>
							</div>
						</div>
					</div>
					<br /> <br />
					<div class='main-content'>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<p>授权应用下载任务详情</p>
								<div class="input-prepend">
									<span class="add-on">任务Id：</span> <input disabled class="span2"
										value="${authorizerAppDownloadTask.id?c}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用ItemId：</span> <input disabled
										class="span2" value="${authorizerAppDownloadTask.itemId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用名称：</span> <input disabled class="span3"
										value="${itemIdRootSimples[authorizerAppDownloadTask.itemId?c]}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用版本：</span> <input disabled class="span1"
										value="${authorizerAppDownloadTask.version}">
								</div>
								<div class="input-prepend">
									<span class="add-on">是否下载ipa：</span> <#if
									authorizerAppDownloadTask.pureIpa == 0> <font color="red">是</font>
									<#else> <font color="green">否</font> </#if>
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">任务执行状态：</span> <input disabled
										class="span2"
										value="${taskStatus[authorizerAppDownloadTask.taskStatus?c]}" />
								</div>
								<div class="input-prepend">
									<span class="add-on">任务执行说明：</span> <input disabled
										class="span4" value="${authorizerAppDownloadTask.info}" />
								</div>
								<div class="input-prepend">
									<span class="add-on">任务更新时间版本：</span> <input disabled
										class="span2"
										value="${ authorizerAppDownloadTask.updateTime?string("yyyy-MM-ddHH:mm:ss") }">
								</div>
								<div class="input-prepend">
									<span class="add-on">任务生成时间版本：</span> <input disabled
										class="span2"
										value="${ authorizerAppDownloadTask.createTime?string("yyyy-MM-ddHH:mm:ss") }">
								</div>
								<div class="input-prepend">
									<span class="add-on">记录状态：</span> <#if
									authorizerAppDownloadTask.status == 0> <font color="green">√</font>
									<#else> <font color="red">X</font> </#if>
								</div>
							</div>
						</div>
					</div>
					<br /> <br />
					<div class="main-content">
						<div id="div_add" class="well form-inline">
							<#if authorizerAppleAccount>
							<div class='main-action'>
								<p>授权ipa购买账号详情</p>
								<div class="input-prepend">
									<span class="add-on">用户Id：</span> <input disabled class="span2"
										value="${authorizerAppleAccount.id?c}">
								</div>
								<div class="input-prepend">
									<span class="add-on">用户AppleId：</span> <input disabled
										class="span2" value="${authorizerAppleAccount.appleId!}">
								</div>
								<div class="input-prepend">
									<span class="add-on">用户名称：</span> <input disabled class="span2"
										value="${authorizerAppleAccount.name!}">
								</div>
								<div class="input-prepend">
									<span class="add-on">用户图标：</span> <img
										style="width: 45px; height: 45px"
										src="${authorizerAppleAccount.icon!}" />
								</div>
								<div class="input-prepend">
									<span class="add-on">账号所在区：</span> <input disabled
										class="span2" value="${authorizerAppleAccount.locale!}">
								</div>
								<div class="input-prepend">
									<span class="add-on">账号状态：</span> <#if
									authorizerAppleAccount.status == 0> <font color="green">√</font>
									<#else> <font color="red">X</font> </#if>
								</div>
							</div>
							</#if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>