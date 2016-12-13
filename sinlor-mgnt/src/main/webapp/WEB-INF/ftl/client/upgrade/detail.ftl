<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
        <title>应用汇-管理后台</title>
		<#include "lib/base_source.ftl">
	</head>
	<body>
		<#include "/lib/header.ftl">
		<div>
	        <ul class="breadcrumb">
	            <li>管理后台</li><span class="divider">/</span>
	            <li>上传客户端版本详情查看</li>
	        </ul>
    	</div>
        <#include "lib/alert.ftl">
        <div class="container">
			<div class = "well form-horizontal">
					<div class="control-group">
						<label class="control-label">软件包名:</label>
						<div class="controls">
							${data.bundleId}
						</div>
					</div>
					<div class="control-group">
						<label class="control-label">更新信息:</label>
						<div class="controls">
							${data.upgradeInfo}
						</div>
					</div>
			</div>
		</div>
		<script type="text/javascript">
			 
            $("input.editortitle").bind("blur", function () {
                var ctl = $(this);
                var rootId= $(ctl).attr("rootId");
                var preValue = $(ctl).attr("preValue");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将应用的编辑名称(应用展示名称)改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/app/modifyeditortitle";
		 			var posting = $.post(postUrl, {'rootId': rootId, "editorTitle": curValue});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
		 				}else{
		 					alert("操作成功");	 					
		 					$(ctl).attr("preValue", curValue);
		 				}
		 			});
				});
            });
            
			 
            $("input.shortdesc").bind("blur", function () {
                var ctl = $(this);
                var rootId= $(ctl).attr("rootId");
                var preValue = $(ctl).attr("preValue");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将应用的短描述改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/app/modifyshortdesc";
		 			var posting = $.post(postUrl, {'rootId': rootId, "shortDesc": curValue});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
		 				}else{
		 					alert("操作成功");	 					
		 					$(ctl).attr("preValue", curValue);
		 				}
		 			});
				});
            });
            
		</script>
	</body>
</html>