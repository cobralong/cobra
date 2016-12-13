(function ($) {
	$.manage = {
			pageList : function(pageDiv,formDiv,currPageDiv,totalDiv,sizeDiv){
				var currPage = $(currPageDiv).val();
				var total = $(totalDiv).val();
				var pageSize = $(sizeDiv).val();
				var pageCount = $.backend.pageCount(total,pageSize);
				if(total > 0){
					$(pageDiv).myPagination({
				      currPage: currPage,
				      pageCount: pageCount,
				      pageSize: pageSize,
				      cssStyle:'pagination',
				      ajax:{
				            onClick:function(page){
				            	$(currPageDiv).val(page);
				            	$(sizeDiv).val(pageSize);
				            	$(formDiv).submit();
				           }
				       }
				      });
				}else{
					var content = '<div class="alert alert-warning center"><span>没有符合条件的记录</span></div>';
	                $(pageDiv).parent().append(content);
				}
			},
			alertMsg : function(msgDataList){
				for(var index in msgDataList){
                	var msg = msgDataList[index]; 
                	$.alert(msg.message,msg.level);
                }
			},
			clickUnableAfterOnce : function(hrefDiv,clickTime, btnClass){
				$(hrefDiv).bind("click",function(event){
					var url=this.href;
					if (clickTime == 1) {
					  $(this).unbind(event);
					}else{
						clickTime=1;
						window.location.href=url;
						$(this).removeClass(btnClass);
						$(this).removeAttr("href");
						return false;
					}
				});
			}
	};
})(jQuery);