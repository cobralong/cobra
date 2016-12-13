$(document).ready(function(){
    $.backend.selectAll();
    var currPageDiv = "input:[name='pager.page']";
    var totalDiv = "input:[name='pager.total']";
    var sizeDiv = "input:[name='pager.size']";
    $.manage.pageList(".pagination","#searchForm",currPageDiv,totalDiv,sizeDiv);
});

$("#batch-on-shelf").live("click", function() {
    console.debug("Start to batch on shelf ...");
    $("#batch-modal").fadeOut();
    var reason = $("#reason").val();
    var checkedList = checkedCheckbox();
    var selectIds = $.backend.linkIds(checkedList);
    var params = {
        "reason": reason,
        "ids": selectIds
    };
    var url = "/admin/app/batchOnShelf";
    var messages = $.backend.sendAjax(url, params);
    console.debug("messages", messages);
    for(var i=0; i<messages.length; i++) {
        var message = messages[i];
        $.alert(message.message, message.level);
    }
});

$("#batch-off-shelf").live("click", function() {
    console.debug("Start to batch off shelf ...");
    $("#batch-modal").fadeOut();
    var reason = $("#reason").val();
    var checkedList = checkedCheckbox();
    var selectIds = $.backend.linkIds(checkedList);
    var params = {
        "reason": reason,
        "ids": selectIds
    };
    var url = "/admin/app/batchOffShelf";
    var messages = $.backend.sendAjax(url, params);
    console.debug("messages", messages);
    for(var i=0; i<messages.length; i++) {
        var message = messages[i];
        $.alert(message.message, message.level);
    }
});

$("#on-shelf-reason").click(function(){
    $("#itemForm").attr("action", $.backend.opts.base + "/admin/app/batchOnShelf");
    var checked = checkedCheckbox();
    if(checked.length > 0) {
        var content = "您选择了" + checked.length + "条记录，请输入批量上架原因";
        $("#batch-operate-title").html(content);
        $("#batch-modal").fadeIn();
    } else {
        $.alert("您没有选中任何条目。", "warning");
    }
    $(".batch-submit").attr("id", "batch-on-shelf");
    console.debug("End to batch on shelf ...");
});

$("#off-shelf-reason").click(function(){
    console.debug("Start to batch off shelf ...");
    $("#itemForm").attr("action", $.backend.opts.base + "/admin/app/batchOffShelf");
    var checked = checkedCheckbox();
    if(checked.length > 0) {
        var content = "您选择了" + checked.length + "条记录，请输入批量下架原因";
        $("#batch-operate-title").html(content);
        $("#batch-modal").fadeIn();
    } else {
        $.alert("您没有选中任何条目。", "warning");
    }
    $(".batch-submit").attr("id", "batch-off-shelf");
    console.debug("End to batch off shelf ...");
});

$(".close-modal").click(function() {
    console.debug("Start to close modal ...");
    $("#batch-modal").fadeOut();
    console.debug("End to close modal ...");
});

function checkedCheckbox() {
    return $(".selectOne:checked");
}

//根据appId 添加快速审核
$("#batch_favorite").click(function() {
    console.log("Start to batch add favorite.");
    var checkedList = checkedCheckbox();
    var checkedIds = $.backend.linkIds(checkedList);
    var url = "/admin/fav/addByAppList";
    var params = {
        ids: checkedIds
    };
    var messages = $.backend.sendAjax(url, params);
    console.debug("messages", messages);
    for(var i=0; i<messages.length; i++) {
        var message = messages[i];
        $.alert(message.message, message.level);
    }
});

$(".query").click(function() {
    $("#pager_page").val(1); // 重置分页
});