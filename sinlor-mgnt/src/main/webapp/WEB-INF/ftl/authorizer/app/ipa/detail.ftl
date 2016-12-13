<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen" />
<title>应用汇-管理后台-授权账号可下载应用详情</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
				<li>授权账号可下载应用详情</li>
			</ul>
		</div>
		<div class="container-fluid">
			<div class="row-fluid">
				<div class="container span12">
					<#include "lib/alert.ftl">
					<div class='main-content'>
						<div id="div_add" class="well form-inline">
							<div class='main-action'>
								<p>授权ipa详情</p>
								<div class="input-prepend">
									<span class="add-on">应用RootId：</span> <input disabled
										class="span2" value="${value.rootId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">应用版本：</span> <input disabled class="span2"
										value="${value.version}">
								</div>
								<div class="input-prepend">
									<span class="add-on">可安装ipa地址：</span> <@shorthref
									value.installIpa!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">可安装ipaMD5：</span> <input disabled
										class="span3" value="${value.installIpaMd5}">
								</div>
								<div class="input-prepend">
									<span class="add-on">可安装ipa plist文件地址：</span> <@shorthref
									value.ipaPlist!''/>
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">纯净版ipa地址：</span><@shorthref
									value.pureIpa!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">纯净版ipaMD5：</span> <input disabled
										class="span3" value="${value.pureIpaMd5}">
								</div>
								<div class="input-prepend">
									<span class="add-on">iTunesMeta文件地址：</span> <@shorthref
									value.itunesMetadata!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">iTunesArtwork文件地址：</span> <@iconimg
									value.itunesArtwork!''/>
								</div>
								<div class="input-prepend">
									<span class="add-on">sinf地址：</span> <@shorthref value.sinf!''/>
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">所用苹果账户：</span> <input disabled
										class="span2" value="${value.appleId}">
								</div>
								<div class="input-prepend">
									<span class="add-on">记录状态：</span> <#if value.status == 0> <font
										color="green">√</font> <#else> <font color="red">X</font>
									</#if>
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
								<br /> <br />
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
										href="${appSimple.appStoreUrl!}">前往AppStore</a>
								</div>
								</#if> </#if>
							</div>
						</div>
					</div>
					<br /> <br />
					<div class="main-content">
						<div id="div_add" class="well form-inline">
							<p>授权ipa购买账号详情</p>
							<#if authorizerAppleAccount>
							<div class='main-action'>
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