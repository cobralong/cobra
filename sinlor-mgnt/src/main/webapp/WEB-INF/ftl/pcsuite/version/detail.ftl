<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台-PC助手版本详情</title> <#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/jasny-bootstrap.min.css">
		<link rel="stylesheet" type="text/css" media="screen"
			href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
		<div>
			<ul class="breadcrumb">
				<li>管理后台</li>
				<span class="divider">/</span>
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
								<div class="input-prepend">
									<span class="add-on">Pc助手客户端渠道</span> <select name="channel"
										disabled> <#list channels as channel> <#if
										value.channel == channel.channel>
										<option value="${channel.channel}" selected>${channel.name}-----${channel.channel}</option>
										<#else>
										<option value="${channel.channel}">${channel.name}-----${channel.channel}</option>
										</#if> </#list>
									</select>
								</div>
								<div class="input-prepend">
									<span class="add-on">版本号：</span> <input class="span2" disabled
										name="version" type="text" placeholder="1.0.0"
										value="${value.version}">
								</div>
								<div class="input-prepend">
									<span class="add-on">版本代码：</span> <input class="span2" disabled
										name="versionCode" type="text" placeholder="越大版本越高，数值"
										value="${value.versionCode}">
								</div>
								<div class="input-prepend">
									<span class="add-on">版本称号：</span> <input class="span2" disabled
										name="versionName" type="text" placeholder="版本称号"
										value="${value.versionName}">
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">更新文件地址：</span> <input class="span4"
										disabled name="url" type="text" placeholder="文件CDN地址"
										value="${value.url}">
								</div>
								<div class="input-prepend">
									<span class="add-on">更新文件Md5值：</span> <input class="span2"
										disabled name="md5" type="text" placeholder="文件MD5值"
										value="${value.md5}">
								</div>
								<div class="input-prepend">
									<span class="add-on">升级时的IP开关：</span> <select name="channel"
										disabled> <#list productIpSwitches?keys as key> <#if
										value.productIpSwitch == key>
										<option selected>${productIpSwitches[key]}</option><#else>
										<option selected>${productIpSwitches[key]}</option> </#if>
										</#list>
									</select>
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">更新说明：</span>
									<textarea name="upgradeInfo" class="span5" cols="360" rows="8"
										default-id="${value.id?c}" pre-value="${value.upgradeInfo}">${value.upgradeInfo}</textarea>
								</div>
								<br /> <br />
								<div class="input-prepend">
									<span class="add-on">更新说明(En)：</span>
									<textarea name="upgradeInfoEn" class="span5" cols="360"
										rows="8" placeholder="不填，则采用上面已填的更新说明"
										default-id="${value.id?c}" pre-value="${value.upgradeInfoEn}">${value.upgradeInfoEn}</textarea>
								</div>
								<#if errMsg> <font color="red">添加失败，原因：${errMsg}</font> </#if>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript"
		src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
	<script type="text/javascript"
		src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
	<script>
		$("textarea").bind("blur", function(){
			var id = $(this).attr("default-id");
			var preValue = $(this).attr("pre-value");
			var setValue =  $(this).val();
			if(preValue == setValue){
				return;
			}
			if(setValue == ""){
				alert("升级内容不能为空");
				return;
			}
			var eventCtrl = $(this);
			bootbox.confirm("确认修改当前更新说明？", function(result) {
	 			if(!result){
	 				return;
	 			}
				var property = $(eventCtrl).attr("name");
				var postUrl = "${rc.contextPath}/auth/pcsuite/version/modify.json";
				var postData = "id=" + id + "&" + property + "=" + setValue;
				var posting = $.post(postUrl, postData);
				posting.done(function(resp){
	 				if(!resp.data){				
	 					alert("操作失败,请联系该死的开发人员.Msg:"+resp.message);
	 				}else{
	 					alert("修改成功.");
	 					$(eventCtrl).attr("pre-value",setValue);
					}
				});
			});
		});		
	</script>
</body>
</html>