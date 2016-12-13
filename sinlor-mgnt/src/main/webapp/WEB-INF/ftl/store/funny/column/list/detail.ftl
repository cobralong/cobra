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
            <li>值得玩专栏列表详情</li>
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
							<div class="input-prepend">
								<label class="add-on">标题</label>
								<div class="controls input-prepend">
									<input class="editortitle" type="text" default_id = "${value.id?c}" flag="0" pre_value = "${value.title}" value="${value.title!}"/>
								</div>
							</div>
							<div class="input-prepend">
								<label class="add-on">发布时间</label>
								<div class="controls input-prepend">
                                    <input class="editortitle" type="text" default_id = "${value.id?c}" flag="1" pre_value = "${value.relaeseTime?string("yyyy-MM-dd HH:mm:ss") }" 
                                     value="${value.relaeseTime?string("yyyy-MM-dd HH:mm:ss") }"/>
                                </div>
							</div></br></br>
							<div class="input-prepend">               
                                <span class="add-on">作者</span>
                                <select id="sel_author" name="authorId" type='add' class="client_choose" default_sel="${value.authorId}" default_id = "${value.id?c}" 
                                onchange='change(this.value,0)'>
                                <#list authors?keys as key>
                                	<option id="aopt_${key}" value="${key}" <#if value.authorId == key>selected="selected"</#if>>${authors[key]}</option>
                            	</#list>
                                </select>
                            </div>
                            <div class="input-prepend">	                                    
                                <span class="add-on">类型</span>
                                <select id="sel_type" name="ctype" type='add' class="client_choose" default_sel="${value.ctype}" default_id = "${value.id?c}" 
                                onchange='change(this.value,1)'>
                                <#list ctypes?keys as key>
                                	<option id="topt_${key}" value="${key}" <#if value.ctype == key>selected="selected"</#if>>${ctypes[key]}</option>
                            	</#list>
                                </select>
                            </div></br></br>
							<div class="control-group">
								<label class="control-label">背景图:</label></br></br>
								<div class="controls input-prepend">
									<img width="600px" height="280px" src="${value.img}" alt="${value.title}的背景图" title="${value.title}的背景图">
								</div>
							</div>
							<form id="searchForm" name="searchForm" enctype="multipart/form-data" action="${rc.contextPath}/funny/client/modifyimg" method="post">
								<input type="hidden" name="id" value="${value.id?c}"/>
								<div class="fileinput fileinput-new" data-provides="fileinput">
	                                <div class="fileinput-new thumbnail" style="width: 600px; height: 280px;">
	                                    <img data-src="holder.js/100%x100%" alt="...">
	                                </div>
	                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 600px; max-height: 280px	;"></div>
	                                <div>
	                                    <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span class="fileinput-exists">Change</span><input type="file" name="image"></span>
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
                var flag = $(ctl).attr("flag");
                if(preValue == curValue){
                	return;
                }
                var actionMsg=flag==0?"是否将标题改为【"+curValue+"】?":"是否将发布时间改为【"+curValue+"】?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				$(ctl).val(preValue);
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/funny/client/modify";
		 			var posting = flag==0?$.post(postUrl, {'id': id, "title": curValue}):$.post(postUrl, {'id': id, "st": curValue});
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
            
            function change(val,flag) {
		        var ctl = flag==0?$("#sel_author"):$("#sel_type");
                var id= $(ctl).attr("default_id");
                var sel = $(ctl).attr("default_sel");
                var def = flag==0?$("#aopt_"+sel):$("#topt_"+sel);
                var name = flag==0?$("#aopt_"+val).text():$("#topt_"+val).text();
                if(val == sel){
                	return;
                }
                var actionMsg= flag==0?"是否将作者改为"+name+"?":"是否将类型改为"+name+"?";
                bootbox.confirm(actionMsg, function(result) {
		 			if(!result){
		 				def.attr("selected","selected");
		 				return;
		 			}
		 			var postUrl = "${rc.contextPath}/funny/client/modify";
		 			var posting = flag==0?$.post(postUrl, {'id': id, "authorId": val}):$.post(postUrl, {'id': id, "ctype": val});
		 			posting.done(function(errMsg){
		 				if(errMsg){//
		 					//show msg	 					
		 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);
		 				}else{
		 					alert("操作成功");	
		 					$(ctl).attr("default_sel",val);			
		 				}
		 			});
				});
    		}
    		
    		$("#startDatetimePicker, #endDatetimePicker").datetimepicker({
        		timeFormat: 'yyyy-mm-dd',
        		stepYear: 1,
       			stepMonth: 1,
        		stepDay: 1
    		});
            
		</script>
</html>