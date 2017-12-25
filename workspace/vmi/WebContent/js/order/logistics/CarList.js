Ext.define("DJ.order.logistics.CarList",{
	extend : 'DJ.myComponent.grid.MySimpleConciseGrid',
	id:'DJ.order.logistics.CarList',
	title:'车辆登记',
	pageSize : 15,
	closable:true,
	url : 'getCarList.do',
	Delurl : "delCarList.do",
	EditUI : "DJ.order.logistics.CarEdit",
	mixins : ['DJ.tools.grid.MySimpleGridMixer',
			'DJ.tools.grid.mixer.MyGridSearchMixer'],
	pagingtoolbarDock : 'bottom',
	isFirststateShower : true,
	showSearchAllBtn : false,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	
	onload : function() {
		this.magnifier = Ext.create('Ext.ux.form.Magnifier');
	},
	statics : {

		showImgPreview : function() {
			var grid = Ext.getCmp("DJ.order.logistics.CarList");

			var records = MyCommonToolsZ.pickSelectItems(grid);

			if (records == -1) {

				return;

			}

			var fid = records[0].get("fid");

			grid.magnifier.loadImages({
				fid : fid
			});

			// var coord = e.getXY();

			var ev =  window.event;

			var mousePosition = function(ev) {
				if (ev.pageX || ev.pageY) {
					return {
						x : ev.pageX,
						y : ev.pageY
					};
				}
				return {
					x : ev.clientX + document.body.scrollLeft
							- document.body.clientLeft,
					y : ev.clientY + document.body.scrollTop
							- document.body.clientTop
				};
			}

			var mousePos = mousePosition(ev);

			var topLength = mousePos.y + 5;

			if (topLength + 300 > document.body.clientHeight) {

				topLength = document.body.clientHeight - 300;

			}

			grid.magnifier.showAt({
				left : mousePos.x + 80,
				top : topLength
			});

		},
		uploadOrdeleteImg : function(fid,type) {
			var gridMain = Ext.getCmp("DJ.order.logistics.CarList");

			var grid = Ext.create("DJ.quickOrder.QuickOrderImgShower",{
				parent:gridMain.id,
				imgid:fid
			}), store = grid
					.getStore(), me = this;
					
			grid.itemId = fid;//record[0].get("fid");

			var widthT = 500;

			var winT = Ext.create('Ext.window.Window', {
				// title: '',
				modal : true,
				resizable : false,
				height : widthT / 16 * 9,
				width : widthT,
				layout : 'fit',
				items : [grid],

				listeners : {

					close : function(panel, eOpts) {
						if(Ext.getCmp(type).xtype=='component'){
							Ext.getCmp(type).prev().setSrc('getFileSourceByParentId.do?fid='+fid+'&_dc='+new Date().getTime())
						}else{
							gridMain.getStore().load();
						}

					}
				}

			});

			if (Ext.isIE) {

				winT.showAt(500, 300);
			} else {

				winT.show();

			}
			store.loadPage(1, {
				params : {

					fid : fid//record[0].get("id")
				}
			});

		},
		editAction:function(){
			var me = Ext.getCmp("DJ.order.logistics.CarList");
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
				}
				try {
					me.Action_BeforeEditButtonClick(me);
					me.Action_EditButtonClick(me);
					me.Action_AfterEditButtonClick(me);
				} catch (e) {

					Ext.MessageBox.alert("提示", e);

				}
		},
		delAction:function(){
			var me = Ext.getCmp("DJ.order.logistics.CarList");
			if (me.Delurl == null || Ext.util.Format.trim(me.Delurl) == "") {
					Ext.MessageBox.alert("错误", "没有指定删除路径");
					return;
				}
				var record = me.getSelectionModel().getSelection();
				if (record.length == 0) {
					Ext.MessageBox.alert("提示", "请先选择您要操作的行!");
					return;
				}
				
					Ext.MessageBox.confirm("提示", "是否确认删除选中的内容?", function(btn) {
								if (btn == "yes") {
									try {
									me.Action_BeforeDelButtonClick(me, record);
									me.Action_DelButtonClick(me, record);
									me.Action_AfterDelButtonClick(me, record);
									} catch (e) {
										Ext.MessageBox.alert("提示", e);
									}
								}
							});
		}

	},
	cusTopBarItems : [{
		text:'新增',
		iconCls:'addnew',
		handler:function(){
			var me = this.up('grid');
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
				}
				me.Action_BeforeAddButtonClick(me);
				me.Action_AddButtonClick(me);
				me.Action_AfterAddButtonClick(me);
		}
	},{
		text:'查看',
		iconCls:'view',
		handler:function(){
			var me = this.up('grid');
			if (me.EditUI == null || Ext.util.Format.trim(me.EditUI) == "") {
					Ext.MessageBox.alert("错误", "没有指定编辑界面");
					return;
				}
				try {
					var record = me.getSelectionModel().getSelection();
					if (record.length == 0) {
						throw "请先选择您要操作的行!";
					};
					me.Action_BeforeViewButtonClick(me);
					var editui = Ext.create(me.EditUI,{
						editstate:'view',
						parent:me.id,
						fid:record[0].get("fid")
					});
					editui.loadfields(record[0].get("fid"));
					editui.seteditstate("view");
					editui.setparent(me.id);
					me.Action_AfterViewButtonClick(me);
					editui.show();
					var mask = document.querySelectorAll('div.x-mask');
					Ext.each(mask,function(m){
						m.setAttribute('style','display:none');//隐藏所有阴影部分
					})
				} catch (e) {

					Ext.MessageBox.alert("提示", e);

				}
		}
	},{
		text:'刷新',
		iconCls:'refresh',
		handler:function(){
			this.up('grid').store.loadPage(1);
		}
	},{
		xtype:'tbfill'
	},{
		xtype : 'mymixedsearchbox',
		condictionFields : ['fcarKG', 'fname', 'fcarnumber',
				'fdrivername', 'fdriverphone','fdrivingexperience'],
		tip : '请输入车辆信息及载重重量、车主信息'
		,
		useDefaultfilter : true,
		filterMode : true
		
	}],//工具栏按钮
	Action_AddButtonClick : function(me) {
		Ext.Ajax.request({
			url:'getCarId.do',
			success:function(response){
				var obj = Ext.decode(response.responseText);
				if(obj.success==true){
					var editui = Ext.create(me.EditUI,{
						editstate:'add',
						parent:me.id,
						fid:obj.msg
					});
					editui.seteditstate("add");
					editui.setparent(me.id);
					editui.show();
				}
			}
		})
		
	},
	Action_EditButtonClick : function(me) {
		var record = me.getSelectionModel().getSelection();
		if (record.length == 0) {
			throw "请先选择您要操作的行!";
		};
		var editui = Ext.create(me.EditUI,{
			editstate:'edit',
			parent:me.id,
			fid:record[0].get("fid")
		});
		editui.loadfields(record[0].get("fid"));
		editui.seteditstate("edit");
		editui.setparent(me.id);
		editui.show();
	},
	fields : ['fid','fcarKG','fcartype','fcarnumber','fdrivername','fdriverphone','fcarpicture','fname','fdrivingexperience','fcarlength','fcarwidth','fcarheight','fdescription','fcarbody','fseats','fpath'],
	columns:[{
		xtype:'templatecolumn',
		text:'车辆信息',
//		dataIndex:'fname',
		align:'center',
		width:250,
		tpl:'',
		onclick : 'DJ.order.logistics.CarList.showImgPreview(event)',
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			var w = metaData.column.width/1.5;
			var href = "javascript:DJ.order.logistics.CarList.uploadOrdeleteImg('"+record.get('fid')+"','"+this.id+"')";
			return "<table><tr><td height='164px'><img onclick="+metaData.column.onclick+" width="+w+" src='../"+record.get('fpath')+"'/><br/><a href=\""+href+"\">编辑图片</a></td><td><span>"+record.get('fname')+"<br/><br/>"+record.get('fcarnumber')+"<br/><br/>"+record.get('fdrivingexperience')+"</span></td></tr></table>"
		}
	},{
		text:'车辆载重信息',
		align:'center',
//		dataIndex:'fcarKG',
		flex:1,
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			return record.get('fcarKG')+"吨<br/><br/>"+record.get('fseats')+"人<br/><br/>"+record.get("fcarbody")+"米"
		}
	},{
		text:'车主信息',
		align:'center',
		dataIndex:'fcarKG',
		flex:1,
		renderer:function(val,metaData,record,rowIndex,colIndex,store,view){
			return record.get('fdrivername')+"<br/><br/>"+record.get('fdriverphone')+"<br/><br/>"+record.get("fdrivingexperience")+"年"
		}
	},{
		text:'备注',
		align:'center',
		dataIndex:'fdescription',
		flex:1
	},{
		stateId : 'actions',
		header : '操作',
		xtype : 'mysimplevmultitextactioncolumn',
		flex:1,
		align : 'center',

		textActions : [{
			text : '修改',
			action : 'DJ.order.logistics.CarList.editAction'
		},{
			text : '删除',
			action : 'DJ.order.logistics.CarList.delAction'

		}]
			
	}]
})