Ext.define('DJ.System.supplier.AddressManageList', {
			extend : 'Ext.c.GridPanel',
			title : "地址管理",
			id : 'DJ.System.supplier.AddressManageList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'GetsupplierscustAddressList.do',
			Delurl : "DelAddressList.do",
			EditUI : "DJ.System.supplier.AddressManageEdit",
			selModel : {selType:'checkboxmodel'},
			onload : function() {
				// 加载后事件，可以设置按钮，控件值等
				var me = this;
				this.down('button[text*=查]').hide();
				this.down('button[text*=导]').hide();
//				this.down('button[text*=询]').hide();
//				this.query('toolbar[dock=top] button[id*='+this.id+']').forEach(function(button){
//					button.hide();
//				})
				this.store.on('beforeload',function(m, options, eOpts){
					if(Ext.isEmpty(me.down('combobox[name=fcustomerid]').getValue())){
						return false;
					}
				})
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
			,custbar : [{
					xtype : 'button',
					text : '地址管理',
					hidden:true,
					width : 100,
					menu : {
						xtype : 'menu',
						items : [{
							xtype : 'menuitem',
							text : '增加',
							handler : function(item, e) {
								this.up('grid').down('button[text*=新]').handler();
							}

						},{
							xtype : 'menuitem',
							text : '修改',
							handler : function() {
								this.up('grid').down('button[text*=修]').handler();
							}
						},{
							xtype : 'menuitem',
							text : '删除',
							handler : function() {
								this.up('grid').down('button[text*=删]').handler();
							}
						}
						]
					}},'|',
				,{
					hideEmptyLabel: false,
//					fieldLabel: '客户',
					editable:true,
					labelWidth:0,
					name : 'fcustomerid',
					width : 200,
					xtype : 'combobox',
					displayField : 'fname',
					valueField : 'fid',
//					pageSize : 50,
					queryMode : 'local',
					typeAhead : true,
//					forceSelection : true,
				    doRawQuery : function() {
						if(Ext.isIE){
							this.doQuery(this.getRawValue(), false, true);	
						}else{
							this.doQuery(this.getRawValue().trim(), false, true);	
						}
					},
					changeAction : function(comb, record, eOpts) {

						var grid = comb.up("grid");

						var storeT = grid.getStore();
						if(record.get('fid')!=0){
							var myfilter = [];
							myfilter.push({
								myfilterfield : 'tba.fcustomerid',
								CompareType : "=",
								type : "string",
								value : record.get('fid')
							});
	
							maskstring = "#0";
	
							storeT.setDefaultfilter(myfilter);
							storeT.setDefaultmaskstring(maskstring);
						}else{
							storeT.setDefaultfilter([]);
							storeT.setDefaultmaskstring([]);
						}

						storeT.loadPage(1);

					},

					listeners : {

						select : function(comb, record, eOpts) {

							comb.changeAction(comb, record[0], eOpts);

						},
						expand:function( field, eOpts ){
							field.store.load();
						}
					},

					store : Ext.create('Ext.data.Store', {
						fields : ['fid', 'fname'],
						proxy : {							
							type : 'ajax',
							url : 'GetStanderCustomersOfSupplier.do',
							reader : {
								type : 'json',
								root : 'data'
							}
						},

						autoLoad : true,
						listeners : {
							load : function(me, records) {
								if (records && records.length && records.length > 0) {
									var com = Ext.getCmp('DJ.System.supplier.AddressManageList').down("combobox[name=fcustomerid]");
									me.insert(0,{fname:'全部',fid:'0'});
									if(Ext.isEmpty(com.getValue())){
										com.setValue(me.data.items[0].get('fid'));
										com.fireEvent('select',com,[me.data.items[0]],com);
									}
								}
							}
						}
					})
				}]
			,fields : [{
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'tba.fname',
						myfiltername : '地址名称',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'tba.fnumber',
						myfiltername : '地址编码',
						myfilterable : true
					}, {
						name : 'fcreatorid'
					}, {
						name : 'flastupdateuserid'
					}, {
						name : 'fcontrolunitid'
					}, {
						name : 'fdetailaddress'
					}, {
						name : 'fcountryid'
					}, {
						name : 'fcityidid'
					}, {
						name : 'femail'
					}, {
						name : 'flinkman',
						myfilterfield : 'tba.flinkman',
						myfiltername : '联系人',
						myfilterable : true
					}, {
						name : 'fphone',
						myfilterfield : 'tba.fphone',
						myfiltername : '电话',
						myfilterable : true
					}, {
						name : 'fprovinceid'
					}, {
						name : 'fdistrictidid'
					}, {
						name : 'fpostalcode'
					}, {
						name : 'ffax',
						myfilterfield : 'tba.ffax',
						myfiltername : '传真',
						myfilterable : true
					}, {
						name : 'fcreatetime'
					}, {
						name : 'flastupdatetime'
					}, {
						name : 'fcustomerid'
					}, {
						name : 'customerName',
						myfilterfield : 'c.fname',
						myfiltername : '客户',
						myfilterable : true
					}
					
					],
			columns : [{
						xtype : 'rownumberer',
						text : '序号',
						width : 35
					},{
						'header' : 'fid',
						'dataIndex' : 'fid',
						hidden : true,
						hideable : false,
						sortable : true

					},{
						'header' : 'fcreatorid',
						'dataIndex' : 'fcreatorid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'flastupdateuserid',
						'dataIndex' : 'flastupdateuserid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fprovinceid',
						'dataIndex' : 'fprovinceid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcountryid',
						'dataIndex' : 'fcountryid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcityidid',
						'dataIndex' : 'fcityidid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fdistrictidid',
						'dataIndex' : 'fdistrictidid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : 'fcontrolunitid',
						'dataIndex' : 'fcontrolunitid',
						hidden : true,
						hideable : false,
						sortable : true

					}, {
						'header' : '地址名称',
						'dataIndex' : 'fname',
						sortable : true
					}, {
						'header' : '客户',
						'dataIndex' : 'customerName',
						sortable : true
					}, {
						'header' : '地址编码',
						'dataIndex' : 'fnumber',
						sortable : true
					}, {
						'header' : '邮政编码',
						'dataIndex' : 'fpostalcode',
						sortable : true
					}, {
						'header' : '邮箱',
						'dataIndex' : 'femail',
						sortable : true
					}, {
						'header' : '联系人',
						'dataIndex' : 'flinkman',
						sortable : true
					},{
						'header' : '电话',
						'dataIndex' : 'fphone',
						sortable : true
					},{
						'header' : '传真',
						'dataIndex' : 'ffax',
						sortable : true
					},{
						'header' : '详细地址',
						'dataIndex' : 'fdetailaddress',
						sortable : true
					}, {
						'header' : '创建时间',
						'dataIndex' : 'fcreatetime',
						width : 200,
						sortable : true
					}, {
						'header' : '修改时间',
						'dataIndex' : 'flastupdatetime',
						width : 200,
						sortable : true
					}]
		})