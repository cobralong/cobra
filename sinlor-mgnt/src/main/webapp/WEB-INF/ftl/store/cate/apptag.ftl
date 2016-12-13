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
            <li>应用标签详情</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">                     
                        <#if data??>                        
							<div class="control-group">
								<label class="control-label">分类名称:</label>
								<div class="controls input-prepend">
									<input class="editortitle" type="text" defaultId = "${data.id?c}" preValue = "${data.name}" value="${data.name!}"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">分类位置:</label>
								${data.position!}
							</div>
							<div class="control-group">
								<label class="control-label">分类短描述:</label>
								<div class="controls input-prepend">
									${data.shortDesc}
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">分类图标:</label>
								<div class="controls input-prepend">
									<img width="72px" height="72px" src="${data.icon}" alt="${data.name}的图标" title="${data.name}的图标">
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">分类下资源数</label>
								<div class="controls input-prepend">
									${data.apps}
								</div>
							</div>
							<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/cate/modifyapptagicon" method="post">
								<input type="hidden" name="id" value="${data.id?c}"/>
								<div class="fileinput fileinput-new" data-provides="fileinput">
	                                <div class="fileinput-new thumbnail" style="width: 72px; height: 72px;">
	                                    <img data-src="holder.js/100%x100%" alt="...">
	                                </div>
	                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 72px; max-height: 72px	;"></div>
	                                <div>
	                                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="icon"></span>
	                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
	                                </div>
	                        	</div>
	                        	<br/>
	                            <button class="btn btn-info add" >
	                                <i class="icon-search icon-white"></i>修改图标
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
                var id= $(ctl).attr("defaultId");
                var preValue = $(ctl).attr("preValue");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将分类名称改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/store/cate/modifyapptagname";
		 			var posting = $.post(postUrl, {'id': id, "name": curValue});
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
</html>