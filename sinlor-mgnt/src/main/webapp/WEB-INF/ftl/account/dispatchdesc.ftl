<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
<title>应用汇-管理后台</title>
<#include "lib/base_source.ftl">
<#include "ftl_macro.ftl">
</head>
<body>
	<div class='whole-container'>
		<#include "/lib/header.ftl">
	    <div>
	        <ul class="breadcrumb">
	            <li>管理后台</li><span class="divider">/</span>
	            <li>苹果帐号分发反馈语列表</li>
	        </ul>
	    </div>
	    <div class="container-fluid">
	        <div class="row-fluid">
	            <div class="container span12">
	            <#include "lib/alert.ftl">
	                <div class='main-content'>
	                    <div class="table-content">                    
	                    <#-- table -->
	                        <form id="itemForm" name="itemForm" method="post">
	                            <table class="table table-striped table-bordered table-condensed" id="itemTable">
	                                <thead>
		                                <tr>
		                                    <th>Id</th>
		                                    <th>描述</th>
		                                    <th>含义</th>
		                                    <th>状态</th>
		                                    <th>修改时间</th>
		                                    <th>入库时间</th>
		                                </tr>
	                                </thead>
	                                <tbody class="sortable">
	                                <#if values??>
	                                    <#list values as value>
	                                    <tr>                      	
	                                        <td>
	                                        	${value.id?c}
	                                        </td>
	                                        <td class="edit_td" pid="${value.id?c}">
	                                        	<label for="name" class="span4"><span id="span_${value.id?c}" class="text-info" preValue="${value.desc!}">${ value.desc! }</span></label>
	                                        </td>
	                                        <td id="default_${value.id?c}">
	                                        	${value.defaultDesc}
	                                        </td>
	                                        <td>
	                                        	<#if value.status == 0>
	                                    			<span class="label label-success">√</span>
	                                    		<#else>
	                                    			<span class="label label-danger">X</span>
	                                    		</#if>
	                                        </td>
	                                        <td>
	                                            <#if value.updateTime ??>
	                                            ${ value.updateTime?string("yyyy-MM-dd HH:mm:ss") }
	                                            <#else>
	                                                -
	                                            </#if>
	                                        </td>
	                                        <td>
	                                            <#if value.createTime ??>
	                                            ${ value.createTime?string("yyyy-MM-dd HH:mm:ss") }
	                                            <#else>
	                                                -
	                                            </#if>
	                                        </td>
	                                    </tr>
	                                    </#list>
	                                </#if>
	                                </tbody>
	                            </table>
	                        </form>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </div>
	</div>
</body>

	<script>
 		$('.edit_td').click(function() {
 			var id = $(this).attr("pid");
 			var spanCtr = $("#span_" + id);
 			var preValue = $(spanCtr).attr("preValue");
 			var input = $('<input id="attribute" type="text" class="span4" preValue="' + preValue + '" value="' + preValue + '" />')
 			$(spanCtr).text('').append(input);
 			input.select();
 			input.blur(function() {
 				var preValue = $(this).parent().attr("preValue");
 				var defaultCtl = $("#default_"+id);
 				var defaultDesc = $(defaultCtl).html();			
 				var curValue = $('#attribute').val();
 				if(curValue == ""){
 					$(this).val(preValue);
 					$(this).focus();
 					return ;
 				}
 				if(curValue == preValue){
		   			var text = $('#attribute').val();
		   			$('#attribute').parent().text(preValue);
		   			$('#attribute').remove();
	   				return ;
	   			}
 				bootbox.confirm("将领取帐号的结果"+defaultDesc+"的反馈语由【"+preValue+"】改为【"+curValue+"】?", function(result) {
		 			if(result){
 						var postUrl = "${rc.contextPath}/auth/account/modifydispatchdesc";
			 			var posting = $.post(postUrl, {'id': id, "desc": curValue});
			 			posting.done(function(errMsg){
			 				if(errMsg){//
			 					//show msg	 					
			 					alert("操作失败,请联系该死的开发人员.Msg:"+errMsg);			 					
					   			$('#attribute').parent().text(preValue);
					   			$('#attribute').remove();
			 				}else{
			 					alert("修改成功.");
			 					$('#attribute').parent().attr("preValue", curValue);
					   			$('#attribute').parent().text(curValue);
					   			$('#attribute').remove();
			 				}
			 			});
		 			}else{
			   			$('#attribute').parent().text(preValue);
			   			$('#attribute').remove();
		 			}
				});
 			});
		});
	</script>
</html>