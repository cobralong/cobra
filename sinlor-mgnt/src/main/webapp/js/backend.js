(function($) {
	$.backend = {
		opts : {

		},
		init : function(options) {
			$.backend.opts = $.extend($.backend.opts, $.backend.defaults,
					options);
		},

		// 获取菜单数据
		menu : function(parentId) {
			var params = {};
			if (parentId != null) {
				params = {
					"parentId" : parentId
				};
			}
			var menus = new Array();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + $.backend.opts.menuApi,
				async : false,
				data : params,
				dataType : "json",
				success : function(result) {
					if (result.success) {
						menus = result.data;
					} else {
						console.log("SERVER ERROR: " + result.message);
					}
				},
				error : function(result) {
					console.log("ERROR: ", result);
				}
			});
			return menus;
		},

		getMenu : function(id) {
			var params = {};
			if (id != null) {
				params = {
					"id" : id
				};
			}
			var menu = new Object();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + $.backend.opts.menuApiGet,
				async : false,
				data : params,
				dataType : "json",
				success : function(result) {
					if (result.success) {
						menu = result.data;
						console.log("Got menu: ", menu);
					} else {
						console.log("SERVER ERROR: ", result.message);
					}
				},
				error : function(result) {
					console.log("ERROR: ", result);
				}
			});
			return menu;
		},

		pageCount : function(size, pageSize) {
			if (size % pageSize != 0) {
				return size / pageSize + 1;
			} else {
				return size / pageSize;
			}
		},

		getAppInfoByAppId : function(appid) {// 根据appid自动填充数据
			var appdetail = new Object();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + $.backend.opts.apiAppId,
				async : false,
				data : {
					applicationId : appid
				},
				dataType : "json",
				success : function(result) {
					if (result.success) {
						console.log(result);
						appdetail = result.data;
					} else {
						console.error(result.message);
					}
				},
				error : function(result) {
					console.log("ERROR: " + result);
				}
			});
			return appdetail;
		},

		getAppInfoByBlock : function(blockId) {// 根据appid自动填充数据
			var appdetail = new Object();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + $.backend.opts.apiBlockId,
				async : false,
				data : {
					blockId : blockId
				},
				dataType : "json",
				success : function(result) {
					if (result.success) {
						appdetail = result.data;
					} else {
						console.log("ERROR: " + result.message);
					}
				},
				error : function(result) {
					console.log("ERROR: " + result);
				}
			});
			return appdetail;
		},

		getAppInfoByPackageName : function(packageName) {// 根据appid自动填充数据
			var appdetail = new Object();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + $.backend.opts.apiPackageName,
				async : false,
				data : {
					packageName : packageName
				},
				dataType : "json",
				success : function(result) {
					if (result.success) {
						appdetail = result.data;
					} else {
						console.log("ERROR: " + result.message);
					}
				},
				error : function(result) {
					console.error("ERROR: ", result);
				}
			});
			return appdetail;
		},
		/**
		 * 这个方法是用来智能匹配参数用来取得，原理如下
		 * 
		 * 1. 通过正则表达式判断参数类型 2.
		 * 如果是数字，则先以参数为appId取得appDetail，如果appDetail为空，则以参数为blockId取得appDetail 3.
		 * 如果不是数字，则以参数为packageName取得appDetail
		 * 
		 * @param param
		 *            appId,blockId或packageName
		 */
		getAppInfo : function(param) {
			var appDetail = new Object();
			var num_pat = /^\d+$/g; // 如果都是数字
			if (param.match(num_pat) == null) {
				var packageName = param;
				appDetail = $.backend.getAppInfoByPackageName(packageName);
				console.log('Get appDetail by packageName', appDetail);
			} else {
				var appId = param;
				appDetail = $.backend.getAppInfoByAppId(appId);
				console.log('Get appDetail by appId', appDetail);
				if (appDetail == null) {
					var blockId = param;
					var appDetail = $.backend.getAppInfoByBlock(blockId)
					console.log('Get appDetail by blockId', appDetail);
				}
			}
			return appDetail;
		},
		// 获取全部菜单数据
		allMenu : function(parentId) {
			var params = {};
			if (parentId != null) {
				params = {
					"id" : parentId
				};
			}
			var menus = new Array();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + $.backend.opts.allMenu,
				async : false,
				data : params,
				dataType : "json",
				success : function(result) {
					if (result.success) {
						menus = result.data;
					} else {
						console.log("SERVER ERROR: ", result.message);
					}
				},
				error : function(result) {
					console.log("ERROR: ", result);
				}
			});
			console.log("get all menu:", menus);
			return menus;
		},
		// 获取全部菜单数据
		allMenuByName : function(name, type) {
			var params = {};
			if (name != null) {
				params = {
					"name" : name
				};
			}
			var url = "";
			if (typeof type == 'undefined' || type == 0) {
				// for enterprise
				url = $.backend.opts.base + $.backend.opts.allMenuByName;
			} else {
				url = $.backend.opts.base + $.backend.opts.allSotreMenuByName;
			}
			var menus = new Array();
			$.ajax({
				type : "POST",
				url : url,
				async : false,
				data : params,
				dataType : "json",
				success : function(result) {
					if (result.success) {
						menus = result.data;
					} else {
						console.log("SERVER ERROR: ", result.message);
					}
				},
				error : function(result) {
					console.log("ERROR: ", result);
				}
			});
			console.log("get all menu by name:", menus);
			return menus;
		},
		// 全选
		selectAll : function() {
			var selectAllId = $.backend.opts.selectAllId;
			var selectOneClass = $.backend.opts.selectOneClass;
			$(selectAllId).live(
					"click",
					function() {
						var selectAll = $(this);
						var selectItems = $(selectOneClass);
						selectItems.attr('checked', this.checked);// 全选/全不选
						selectItems.live('click', function(e) {
							e.stopPropagation();// 阻止冒泡,避免行点击事件中,直接选择选框无效
							selectAll.attr('checked',
									selectItems.size() == selectItems.filter(
											':checked').size());// 判断选中个数与实际个数是否相同,以确定全选/全不选状态
						});
					});
		},
		// Ajax 请求
		sendAjax : function(url, params) {
			var data = new Object();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + url,
				async : false,
				data : params,
				dataType : "json",
				success : function(result) {
					if (result.success) {
						console.debug("result", result);
						$.alert(result.message);
						data = result.data;
					} else {
						console.log("SERVER ERROR:", result.message);
						$.alert(result.message, "error");
					}
				},
				error : function(result) {
					console.log("ERROR:", result);
					$.alert(result);
				}
			});
			return data;
		},
		// Ajax 请求
		ajax : function(url, params) {
			var data = new Object();
			$.ajax({
				type : "POST",
				url : $.backend.opts.base + url,
				async : false,
				data : params,
				dataType : "json",
				success : function(result) {
					if (result.success) {
						console.debug("result", result);
						data = result.data;
					} else {
						console.log("SERVER ERROR:", result.message);
					}
				},
				error : function(result) {
					console.log("ERROR:", result);
				}
			});
			return data;
		},
		// 将选中的checkbox值链接起来
		linkIds : function(checkedList, sign) {
			var selectIds = "";
			if (!sign) {
				sign = ",";
			}
			for (var i = 0; i < checkedList.length; i++) {
				var checked = checkedList[i];
				console.debug("checked:", checked);
				selectIds += checked.value + sign;
			}
			selectIds = selectIds.substring(0, selectIds.length - 1)
			console.log("selectIds:", selectIds);
			return selectIds;
		},

		getDataFromLocalStorage : function(key) {
			var json = localStorage.getItem(key);
			return json ? JSON.parse(json) : undefined;
		},

		saveDataToLocalStorage : function(key, data) {
			var json = JSON.stringify(data);
			localStorage.setItem(key, json);
		}
	};

	$.fn.loadHtml = function(url, params, type) {
		var container = $(this);
		if (type == null) {
			type = "POST";
		}
		$.ajax({
			type : type,
			url : $.backend.opts.base + url,
			async : false,
			data : params,
			dataType : "html",
			success : function(result) {
				container.html(result);
			},
			error : function(result, textStatus, errorThrown) {
				console.error("ERROR: ");
				console.error(result);
				container.html("<span style='color: red'>" + textStatus + ":"
						+ errorThrown + "</span>");
			}
		});
	};

	$.backend.defaults = {
		rootMenuName : "MANAGE_PROJECT",
		base : "",
		menuApi : "/auth/menu",
		allMenu : "/auth/menu/allMenu",
		allMenuByName : "/auth/menu/allMenuByName",
		allSotreMenuByName : "/auth/menu/allStoreMenuByName",
		menuApiGet : "/auth/menu/get",
		apiAppId : "/market/detail/appId",
		apiBlockId : "/market/detail/blockId",
		apiPackageName : "/market/detail/packageName",
		selectAllId : "#selectAll",
		selectOneClass : ".selectOne"
	};

})(jQuery);