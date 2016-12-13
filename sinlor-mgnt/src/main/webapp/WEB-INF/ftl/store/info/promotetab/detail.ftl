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
            <li>首页Tab列表</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="container span12">
            <#include "lib/alert.ftl">
                <div class='main-content'>
                    <div class="well form-inline">                     
                        <#if value??>                        
							<div class="control-group">
								<label class="control-label">名称:</label>
								<div class="controls input-prepend">
									<input class="editortitle" type="text" default_id = "${value.id?c}" pre_value = "${value.name}" value="${value.name!}"/>
								</div>
							</div>
							<div class="control-group">
								<label class="control-label">名称颜色:</label>
								<input id="color" type="color" name="color" style="width:220px; " default_id = "${value.id}" pre_value="#${value.color}" value="#${value.color}"/>
							</div>
							<div class="control-group">
								<label class="control-label">Tab位置:</label>
								<input id="input_rank" class="input_rank" type="text" default_id = "${value.id?c}" pre_value = "${value.rank}" value="${value.rank!}"/>
							</div>
							<div class="control-group">
								<label class="control-label">分类图标:</label>
								<div class="controls input-prepend">
									<img width="30px" height="30px" src="${value.icon}" alt="${value.name}的图标" title="${value.name}的图标">
								</div>
							</div>
							<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/auth/store/promotetab/modifyicon" method="post">
								<input type="hidden" name="id" value="${value.id?c}"/>
								<div class="fileinput fileinput-new" data-provides="fileinput">
	                                <div class="fileinput-new thumbnail" style="width: 30px; height: 30px;">
	                                    <img data-src="holder.js/100%x100%" alt="...">
	                                </div>
	                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 30px; max-height: 30px	;"></div>
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
                var id= $(ctl).attr("default_id");
                var preValue = $(ctl).attr("pre_value");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将Tab名称改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/store/promotetab/modify";
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
            
             $("#input_rank").bind("blur", function () {
                var ctl = $(this);
                var id= $(ctl).attr("default_id");
                var preValue = $(ctl).attr("pre_value");
                var curValue = $(ctl).val();
                if(preValue == curValue){
                	return;
                }
                var actionMsg="是否将Tab排序由"+ preValue +"改为"+curValue+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/store/promotetab/modify";
		 			var posting = $.post(postUrl, {'id': id, "rank": curValue});
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
            
            $("#color").on("blur",function(){
            	var ctl = $(this);
            	var preColor = $(this).attr("pre_value");
            	var id = $(this).attr("default_id");
            	var curColor = $(this).val();
            	if(curColor == preColor){
            		return;
            	}
            	 bootbox.confirm("是否将Tab的显示颜色修改为当前颜色?", function(result) {
		 			if(!result){
		 				$(ctl).val(preColor);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/auth/store/promotetab/modify";
		 			var posting = $.post(postUrl, {'id': id, "color": curColor});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
		 				}else{
		 					alert("操作成功");	 					
		 					$(ctl).attr("pre_value", curColor);
		 				}
		 			});
				});
			});
		</script>
</html>