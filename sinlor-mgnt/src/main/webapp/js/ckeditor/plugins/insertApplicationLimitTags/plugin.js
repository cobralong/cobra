(function() {
	CKEDITOR.plugins.add("insertApplicationLimitTags", {
		requires : [ "dialog" ],
		init : function(a) {
			a.addCommand("insertApplicationLimitTags",
					new CKEDITOR.dialogCommand("insertApplicationLimitTags"));
			a.ui.addButton("insertApplicationLimitTags", {
				label : "限免应用标签",
				command : "insertApplicationLimitTags",
				icon : this.path + "images/add.gif"
			});
			CKEDITOR.dialog.add("insertApplicationLimitTags", this.path
					+ "dialogs/insertApplicationLimitTags.js");
		}
	})

})();