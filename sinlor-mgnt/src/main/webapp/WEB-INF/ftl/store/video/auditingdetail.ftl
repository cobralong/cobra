<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
<div class='whole-container'>
<#include "lib/store_header.ftl">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/jasny-bootstrap.min.css">
    <link rel="stylesheet" type="text/css" media="screen" href="${rc.contextPath}/css/bootstrap-datetimepicker.css">
    <div>
        <ul class="breadcrumb">
            <li>管理后台</li>
            <span class="divider">/</span>
            <li>审核视频</li>
        </ul>
    </div>
    <div class="container-fluid">
        <div class="row-fluid">
        <#include "lib/alert.ftl">
            <div class='main-content'>
                <div class="well form-inline">
                    <div class='main-action'>
                        <form id="addform" name="addform" method="post" action="${rc.contextPath}/auth/store/video/audit" onsubmit="checkdata()" enctype="multipart/form-data">
                            <input type="hidden" name="id" value="${para.id}"/>
                            <div class="input-prepend">
                                <span class="add-on">视频原始名称</span>
                                <input class="span4" disabled value="${value.originName}">
                            </div>
                            <br/>
                            <br/>
                            <div class="input-prepend">
                                <span class="add-on">视频标题</span>
                                <input id="title" name="title" type="text" class="span4" style="margin-left:30px;" placeholder="视频标题" value="${para.title}">
                            </div>
                            <br/>
                            <br/>
                            <div class="input-prepend">
                                <span class="add-on" style=margin-right:20px;">推荐客户端</span>
		                       	<#list bundleIds?keys as key>
	                       			<input name="bundleIds" class="bundleId" type="checkbox" value="${key}"><span  style="margin-right:20px;">${bundleIds[key]}</span>		                        	  
		        				</#list>
                            </div>                            
                            <br/>
                            <br/>
                            <div class="input-prepend">
                                <span class="add-on">视频主分类</span>
                                <@spring.formSingleSelect "para.mainCategory", cateMap, "style='margin-left:17px;'"/>
                            </div>
                            <br/>
                            <br/>
                            <div class="input-prepend">
                            	<span class="add-on">所属分类:</span>
                            	<input id="hidden_categorys" type="hidden" name="categorys" value="">								
								<#if cates??>
									<table class="table table-striped table-bordered table-condensed" style="margin-left:100px; margin-top:-30px;" id="itemTable">
			                            <thead>
				                            <tr>
				                            	<th></th>
				                                <th>名称</th>
				                            	<th></th>
				                                <th>名称</th>
				                            	<th></th>
				                                <th>名称</th>
				                            </tr>
			                            </thead>
			                            <tbody class="sortable">
			                            	<#list cates as cate>
			                            		<#assign index = cate_index>
			                            		<#if index == 0 || (index % 3  == 0)>
			                            			<tr>
			                            		</#if>
			                            			<td style="width:5%">
			                            				<input class="cate_check" type="checkbox" name="categoryIds" value="${cate.id}">
			                            			</td>
			                            			<#if (index+1)%3 !=0 && (index+1) == cates?size>
				                                    	<td style="border-right: 1px solid #dddddd;">${ cate.name! }</td>
				                                    <#else>
				                                    	<td>${ cate.name! }</td>				                                    
				                                    </#if>
				                              	<#if (index+1) == cates?size || (index+1) % 3  == 0>
			                                		</tr>
			                                	</#if>
			                                </#list>
			                            </tbody>
		                        	</table>
		                    	</#if>		                    
                            </div>
                            <br/>
                            <div class="fileinput fileinput-new" data-provides="fileinput">                            	
                                <div class="fileinput-new thumbnail" style="width: 320px; height: 180px; margin-left:100px; ">
                                    <img data-src="holder.js/100%x100%" alt="..." src="${para.iconUrl}" />
                                </div>
                                <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 320px; max-height: 180px; margin-left:140px; "></div>
                                <div>
                                	<span class="add-on">Banner:</span>
                                    <span class="btn btn-default btn-file" style="margin-left:40px;" ><span class="fileinput-new" >Select image</span><span class="fileinput-exists">Change</span>
                                        <input type="file" name="screen"></span>
                                    <a href="#" class="btn btn-default fileinput-exists" data-dismiss="fileinput">Remove</a>
                                </div>
                            </div>
                            <br/>
                            <div class="input-prepend">
                                <span class="add-on">片头</span>
                                <input id="check_firstvideo" name="headVideoChecked" style="margin-left:30px;" type="checkbox" value="1">
                                <input id="input_firstvideo" name="headVideoUrl" class="span6" readonly="readonly" value="${headVideoUrl}">
                                <input id="hidden_input_firstVideo" name="videoUrl0" type="hidden">
                            </div><br/>
                            <br/>
                            <div class="input-prepend">
                                <span class="add-on">播放Video</span>
                                <input class="span6" name="videoUrls" readonly="readonly"  value="${value.videoUrl}"><a href="${value.videoUrl}" target="_blank">观看视频</a><br/>
                                <input name="videoUrls" class="store_url span6" style="margin-left:75px; margin-top:5px;" value="${para.videoUrl2}"><br/>
                                <input name="videoUrls" class="store_url span6" style="margin-left:75px; margin-top:5px;" value="${para.videoUrl3}"><br/>
                                <input name="videoUrls" class="store_url span6" style="margin-left:75px; margin-top:5px;" value="${para.videoUrl4}"><br/>
                                <input name="videoUrls" class="store_url span6" style="margin-left:75px; margin-top:5px;" value="${para.videoUrl5}"><br/>
                            </div><br/>
                            <div class="input-prepend">
                                <span class="add-on">相关应用</span>
                                <input name="relateRootIds" class="span6" style="margin-left:7px; margin-top:5px;" placeholder="1,2,3..." value="${para.relateRootIds}">
                            </div><br/>
                            <div class="input-prepend">
                                <span class="add-on">内容描述</span>
                                <textarea name="content" cols="120" rows="14" class="span6" style="margin-left:10px; margin-top:5px;" value="${para.content}"></textarea>                       	 
                            </div><br/>
                            <div class="input-prepend">
                                <input id="submitBtn" type="submit" class="btn btn-info add" value="提交"/>
                        		<#if errMsg??>
                            		<font id="font_errmsg" color="red">${errMsg}</font>
                            	</#if>
                        		
                            </div>  
                        </form>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <script type="text/javascript" src='${rc.contextPath}/js/bootstrap-datetimepicker.js'></script>
    <script type="text/javascript" src='${rc.contextPath}/js/jasny-bootstrap.min.js'></script>
    <script type="text/javascript">
        $(document).ready(function () {     
        	$("select").attr("cur_value", $("select").val());   	         	
            $(".cate_check").each(function(e){
            	var curSelValue = $("select").val();
            	var curCheckValue = $(this).val();
            	if(curCheckValue == curSelValue){
            		$(this).prop("checked", true);
            	}
            });
        });
        $("select").bind("change", function(e){
        	var curSelValue = $(this).val();
       		var preSelValue = $(this).attr("cur_value");
       		$("select").attr("cur_value", curSelValue);
   		    $(".cate_check").each(function(e){
	        	var curCheckValue = $(this).val();
	        	if(curCheckValue == curSelValue){
	        		$(this).prop("checked", true);
	        	}else if(preSelValue == curCheckValue){
	        		$(this).prop("checked", false);
	        	}
            });      	
        });
		$("input.store_url").bind("blur", function(e){
			var url = $(this).val();
			if(url){
				$("<a href='"+url+"' target='_blank'>观看视频</a>").insertAfter(this);
			}
		});

		$("input.bundleId[type='checkbox']").bind("click",function(){
			var eventCtl = $(this);
			var value = $(eventCtl).val();			
			if(value=="all"){
				if($(this).is(":checked")){					
					$("input.bundleId[type='checkbox']").each(function(){
						$(this).prop("checked", true);
					});	
				}else{
					$("input.bundleId[type='checkbox']").each(function(){
						$(this).prop("checked", false);
					});
				}
			}else{
				if(!$(this).is(":checked")){
					$("input.bundleId[type='checkbox']").each(function(){
						if($(this).val() == "all"){
							$(this).prop("checked", false);	
						}
					});
				}
			}
		});

    </script>
</body>
</html>