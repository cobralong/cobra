<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
<title>应用汇-AppStore管理后台</title>
<#include "lib/base_source.ftl">
</head>
<body>
<div class='whole-container'>
	<#include "/lib/store_header.ftl">
	
    <div>
        <ul class="breadcrumb">
            <li>AppStore管理后台</li><span class="divider">/</span>
            <li>值得玩作者详情</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                	<div class="well form-inline">
                         <div id="addInfoDiv" errMsg="${errMsg}">
                            <#if errMsg??>
                        		<font color="red">${errMsg}</font>
                        	</#if>
                          </div>
                    </div>
                    <div class="well form-inline">                     
                        <#if value??>                        
							<div class="control-group">
								<label class="control-label">昵称:</label>
								<div class="controls input-prepend">
									<input class="editortitle" type="text" default_id = "${value.id?c}" pre_value = "${value.name}" value="${value.name!}"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">头像:</label>
								<div class="controls input-prepend">
									<img width="50px" height="50px" src="${value.icon}" alt="${value.name}的头像" title="${value.name}的头像">
								</div>
							</div>
							<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/funny/client/author/modifyicon" method="post">
								<input type="hidden" name="id" value="${value.id?c}"/>
								<div class="fileinput fileinput-new" data-provides="fileinput">
	                                <div class="fileinput-new thumbnail" style="width: 50px; height: 50px;">
	                                    <img data-src="holder.js/100%x100%" alt="...">
	                                </div>
	                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 50px; max-height: 50px	;"></div>
	                                <div>
	                                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="icon"></span>
	                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
	                                </div>
	                        	</div>
	                        	<br/>
	                            <button class="btn btn-info add" >
	                                <i class="icon-search icon-white"></i>修改头像
	                            </button>
							</form>
                        </#if>
                    </div><!-- end div class table-content-->
                </div><!-- end div class main-content-->
            </div><!-- end div class container span12-->
        </div><!-- end div class row-fluid-->
    </div><!-- end div class container-fluid-->
</div><!-- end div class whole-container-->
</body>
		<script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
		<script type="text/javascript">
            $("input.editortitle").bind("blur", function () {
                var ctl = $(this);
                var id= $(ctl).attr("default_id");
                var preValue = $(ctl).attr("pre_value");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将昵称改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/funny/client/author/modify";
		 			var posting = $.post(postUrl, {'id': id, "name": curValue});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
		 				}else{
		 					alert("操作成功");	 					
		 					$(ctl).attr("pre_value", curValue);
		 				}
		 			});
				});
            });
            
		</script>
</html>