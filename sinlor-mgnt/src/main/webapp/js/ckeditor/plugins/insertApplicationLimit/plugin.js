(function() {
	CKEDITOR.plugins.add("insertApplicationLimit", {
		requires : [ "dialog" ],
		init : function(a) {
			a.addCommand("insertApplicationLimit",
					new CKEDITOR.dialogCommand("insertApplicationLimit"));
			a.ui.addButton("insertApplicationLimit", {
				label : "插入限免应用",
				command : "insertApplicationLimit",
				icon : this.path + "images/phoneRed.jpg"
			});
			CKEDITOR.dialog.add("insertApplicationLimit", this.path
					+ "dialogs/insertApplicationLimit.js");
		}
	})

})();