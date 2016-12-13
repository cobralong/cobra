(function() {
	CKEDITOR.plugins.add("insertApplicationSets", {
		requires : [ "dialog" ],
		init : function(a) {
			a.addCommand("insertApplicationSets",
					new CKEDITOR.dialogCommand("insertApplicationSets"));
			a.ui.addButton("insertApplicationSets", {
				label : "插入应用集",
				command : "insertApplicationSets",
				icon : this.path + "images/phoneSets.png"
			});
			CKEDITOR.dialog.add("insertApplicationSets", this.path
					+ "dialogs/insertApplicationSets.js");
		}
	})

})();