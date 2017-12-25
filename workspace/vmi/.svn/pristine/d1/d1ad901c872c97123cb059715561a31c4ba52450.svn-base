Ext.define('DJ.System.supplier.SproductToMaterialEdit', {
	extend : 'Ext.c.BaseEditUI',
	id : 'DJ.System.supplier.SproductToMaterialEdit',
	modal : true,
	title : "关联材料",
	ctype : "sproducttomaterial",
	width : 350,// 230, //Window宽度
	height : 180,// 137, //Window高度
	resizable : false,
	url : 'saveSpdtTomaterial.do',
	infourl : 'GetSpMatInfobyID.do',
	viewurl : 'GetSpMatInfobyID.do',
	closable : true, // 关闭按钮，默认为true
	onload : function() {
		
	},
	listeners: {
		show: function(){
			Ext.getCmp(this.id+'.closebutton').setText('关    闭');
		}
	},
	initComponent : function() {
		var win = this;
		Ext.apply(this, {
			layout : {
				type : 'vbox',
				align : 'center',
				pack : 'center'
			},
			items : [{
				xtype : 'combobox',
				fieldLabel : '供应商名称',
				labelWidth : 80,
				width : 280,
				allowBlank : false,
				blankText : '请选择供应商',
				name: 'fsupplierid',
				valueField : 'fid',
				displayField : 'fname',
				editable:false,
				allowBlank: false,
				store:Ext.create('Ext.data.Store',{
					fields: ['fid', 'fname'],
					proxy:{
						type:'ajax',
						url: 'getSupplierForDeliverApply.do',
				         reader: {
				             type: 'json',
				             root: 'data'
				         }
					}
				}),
				listeners:{
					afterrender: function(){ //自动设置制造商
						var me = this,
						store = me.getStore();
						store.load({
							callback: function(records, operation, success){
								if(success && records.length==1){
									me.setValue(records[0]);
									me.setEditable(false);
								}
							}
						});
					},
					select: function(combo,records){
						var materialfidField = win.down('combobox[name=fmaterialfid]');
						materialfidField.setHiddenValue('');
						materialfidField.setRawValue('');;
						win.down('[name=fsuppliername]').setValue(records[0].data.fname);
					}
				}
			}, {
				name : 'fmaterialfid',
				fieldLabel : '材料',
				labelWidth : 80,
				width : 280,
				allowBlank : false,
				blankText : '请选择材料',
				xtype : 'cCombobox',
				displayField : 'fname', // 这个是设置下拉框中显示的值
				valueField : 'fid', // 这个可以传到后台的值
				beforeExpand : function(){
					var fsupplierid = win.down('combobox[name=fsupplierid]').getValue();
					if(!fsupplierid){
						fsupplierid = -1;
					}
					this.setDefaultfilter([{
						myfilterfield : "fsupplierid",
						CompareType : "=",
						type : "string",
						value : fsupplierid
					},{
						myfilterfield : "flayer",
						CompareType : "=",
						type : "int",
						value : win.flayer
					},{
						myfilterfield : "ftilemodelid",
						CompareType : "=",
						type : "string",
						value : win.ftilemodel
					}]);
					this.setDefaultmaskstring(" #0 and #1 and #2");
				},
				listeners:{
					select: function(combo,records){
						win.down('[name=fmaterialname]').setValue(records[0].data.fname);
					}
				},
				MyConfig : {
					width : 260,// 下拉界面
					height : 200,// 下拉界面
					url : 'getCardboardList.do', // 下拉数据来源
					hiddenToolbar:true,
					fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'fname',
						myfiltername : '材料名',
						myfilterable : true
					}, {
						name : 'flayer'
					}, {
						name : 'ftilemodelid'
					}],
					columns : [{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						autoHeight : true,
						autoWidth : true,
						sortable : true
					}, {
						'header' : '材料',
						'dataIndex' : 'fname',
						sortable : true

					}, {
						'header' : '层数',
						width : 70,
						'dataIndex' : 'flayer',
						sortable : true
					}, {
						'header' : '楞型',
						width : 70,
						'dataIndex' : 'ftilemodelid',
						sortable : true
					}]
				}

			}, {
				name : 'fsuppliername',
				xtype : 'hidden'
				//xtype : 'hidden'
			},{
				name : 'fmaterialname',
				xtype : 'hidden'
			}, {
				name : 'fcustpdtid',
				xtype : 'hidden'
			},{
				name : 'fid',
				xtype : 'hidden'
			}]
		}), this.callParent(arguments);
	}

});