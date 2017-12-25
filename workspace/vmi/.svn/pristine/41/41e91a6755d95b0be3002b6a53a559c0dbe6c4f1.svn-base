Ext.define('DJ.Inv.ProductInvControl.ProductInvControlList', {
			extend : 'Ext.c.GridPanel',
			title : "制造商产品库存控制表",
			id : 'DJ.Inv.ProductInvControl.ProductInvControlList',
			selModel : Ext.create('Ext.selection.CheckboxModel'),
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false

			url : 'getManufacturersProductInventoryControls.do',
			Delurl : "",

			EditUI : "",

			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				
				var me = this;
				
				var storeT = me.getStore();
				
				storeT.getProxy( ).timeout  = 60000;
				
				this._operateButtonsView(true, [4, 5, 6, 7]);
				
			},
			
			Action_BeforeAddButtonClick : function(EditUI) {
				// 新增界面弹出前事件
			},
			Action_AfterAddButtonClick : function(EditUI) {
				// 新增界面弹出后事件
			},
			Action_BeforeEditButtonClick : function(EditUI) {
				// 修改界面弹出前事件
			},
			Action_AfterEditButtonClick : function(EditUI) {
				// 修改界面弹出后事件
				// 可对编辑界面进行复制

			},
			Action_BeforeDelButtonClick : function(me, record) {
				// 删除前事件
			},
			Action_AfterDelButtonClick : function(me, record) {
				// 删除后事件
			}

			,
			custbar : [{

				text : '调整',
				height : 30,
				handler : function() {

					var grid = this.up("gridpanel");
					var record = grid.getSelectionModel().getSelection();

					if (record.length != 1) {
						Ext.MessageBox.alert('提示', '请选中一条记录！');
						return;
					}

					var productInvControlEdit = Ext
							.create('DJ.Inv.ProductInvControl.ProductInvControlEdit');

					productInvControlEdit.productID = record[0]
							.get("FPRODUCTID");

					productInvControlEdit.show();

				}

			}],

			fields : [{
						name : 'fid'
					}, {
						name : 'FPRODUCTID'
					}, {
						name : 'fname',
						myfilterfield : 'tppd.fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'supplier',
						myfilterfield : 'spplr.fname',
						myfiltername : '制造商',
						myfilterable : true
					}
					, {
						name : 'FCHARACTER'
					}, {
						name : 'FMINSTOCK',
						type : 'int'
					}, {
						name : 'FORDERAMOUNT',
						type : 'int'
					}, {
						name : 'stock',
						type : 'int'
					}, {
						name : 'theLock',
						type : 'int'
					}, {
						name : 'availableQuantity',
						type : 'int'
					}, {
						name : 'fallotqty',
						type : 'int'
					}],
			columns : [Ext.create('DJ.Base.Grid.GridRowNum'), {
						'header' : '制造商',
						'dataIndex' : 'supplier',
						sortable : true

					}, {
						'header' : '产品名称',
						'dataIndex' : 'fname',
						sortable : true

					}, {
						'header' : '规格',
						'dataIndex' : 'FCHARACTER',
						sortable : true

					}, {
						'header' : '建议最小库存',
						'dataIndex' : 'FMINSTOCK',
						sortable : true

					}, {
						'header' : '建议下单批量',
						'dataIndex' : 'FORDERAMOUNT',
						sortable : true

					}, {
						'header' : '存量',
						'dataIndex' : 'stock',
						sortable : true
					}, {
						'header' : '调拨在途',
						'dataIndex' : 'fallotqty',
						sortable : true

					}, {
						'header' : '锁定',
						'dataIndex' : 'theLock',
						sortable : true

					}, {
						'header' : '可用量',
						'dataIndex' : 'availableQuantity',
						sortable : true

					}],
			/**
			 * 主要调用这个方法
			 * 
			 * @param {}
			 *            show，bool，
			 * @param {}
			 *            array，元素为button ID或为数字索引（从0开始）
			 */
			_operateButtonsView : function(show, array) {

				if (Ext.typeOf(array[0]) == "number") {
					array = this._translateNumToDataIndex(array);
				}

				if (show) {
					this._showButtons(array);
				} else {
					this._hideButtons(array);
				}

			},
			_translateNumToDataIndex : function(array) {
				var arrayt = [];

				var arrayAll = this._getToolsButtonIDs();

				Ext.each(arrayAll, function(item, index, all) {
							if (Ext.Array.contains(array, index)) {
								arrayt.push(arrayAll[index]);
							}
						});

				return arrayt;
			},

			_hideButtons : function(array) {
				var t = array;
				Ext.each(t, function(item, index, all) {
							Ext.getCmp(item).hide();
						});
			},

			_showButtons : function(array) {
				var defaultButtons = this._getToolsButtonIDs();

				var tArray = Ext.Array.difference(defaultButtons, array);

				this._hideButtons(tArray);
			},

			_getToolsButtonIDs : function() {
				var defaultButtons = [];

				var t = this.dockedItems.items[2].items.items;

				Ext.each(t, function(item, index, all) {

							defaultButtons.push(item.id);

						});
				return defaultButtons;
			}
		})