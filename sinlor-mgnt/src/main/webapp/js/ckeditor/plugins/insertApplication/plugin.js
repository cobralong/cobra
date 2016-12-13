(function() {
	CKEDITOR.plugins.add("insertApplication", {
		requires : [ "dialog" ],
		init : function(a) {
			a.addCommand("insertApplication",
					new CKEDITOR.dialogCommand("insertApplication"));
			a.ui.addButton("insertApplication", {
				label : "插入应用",
				command : "insertApplication",
				icon : this.path + "images/phone.png"
			});
			CKEDITOR.dialog.add("insertApplication", this.path
					+ "dialogs/insertApplication.js");
		}
	})

})();