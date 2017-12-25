Ext.define('DJ.supplier.MyCustomerEdit',{
	extend : 'Ext.form.Panel',
	title : "手动新增",
	id : 'DJ.supplier.MyCustomerEdit',
	closable : true,// 是否现实关闭按钮,默认为false
	bodyPadding:'15 20',
	defaults: {
//    	labelAlign: 'right',
    	margin:'10 0',
    	labelWidth : 50
	},
	layout: {
	    type: 'vbox',
	    align: 'left'
	},
	items:[{
		layout : "column",
		baseCls : "x-plain",
		items : [{// title:"列1",
			baseCls : "x-plain",
			columnWidth : .5,
			defaults: {
				width : 320,
				labelWidth : 70,
		    	margin:'10 15 10 0'
			},
			items : [{
				name:'fid',
				xtype : 'textfield',
				hidden:true
			},{
				name : 'fname',
				xtype : 'textfield',
				fieldLabel : '<font color="red">*</font> 客户名称',
				allowBlank:false,
				blankText :'请填写客户名称',
				listeners:{
					blur : function(me, The, eOpts ){
						if(!Ext.isEmpty(me.getValue())){
							Ext.Ajax.request({
								url:'queryMyCustomerByName.do',
								params:{'cname':me.getValue()},
								success:function(response){
									var obj = Ext.decode(response.responseText);
									if(obj.success==true){
										
									}else{
										if(!Ext.isEmpty(me.up('panel'))){
											Ext.Msg.alert('提示',obj.msg,function(){
												var task = new Ext.util.DelayedTask(function(){
													me.focus();
												})
												task.delay(600);
											});
										}
									}
								}
							})
						}
					}
				}
			}, {
				name : 'fphone',
				xtype : 'textfield',
				fieldLabel : '手机号'
			}, {
				name : 'ffax',
				xtype : 'textfield',
				fieldLabel : '传真'
			}]
		}, {	// title:"列2",
			baseCls : "x-plain",
			columnWidth : .5,
			defaults: {
				width : 330,
				labelWidth : 40,
		    	margin:'10 0'
			},
			items : [{
				name : 'flinkman',
				xtype : 'textfield',
				fieldLabel : '联系人'
			}, {
				name : 'fartificialpersonphone',
				xtype : 'textfield',
				fieldLabel : '座机号'
			}]
		}]
	},{
				xtype : 'cTable',
				name : "Address",
				width:'100%',
				height : 50,
				width:660,
				pageSize : 100,
				columnLines:true,
				margin:'0',
				url : "GetsupplierscustAddressList.do",
//				parentfield : "e.fid",
				plugins: [Ext.create('Ext.grid.plugin.CellEditing', {
					clicksToEdit: 1
				})],
				onload:function(){
					var me = this;
					this.store.on('load',function( store, records, index, eOpts ){
						Ext.each(records,function(){
							me.setHeight(me.height+22);
						})
					})
					this.down('toolbar').hide();
					this.store.add(0);
				},
				fields : [{
					name : "fid"
				}, {
					name : "fname"
				}, {
					name : "flinkman"
				},{
					name:"fphone"
				}],
				columns : [{
					dataIndex : "fname",
					text : "地址",
					width:300,
					editor:{
					}
				}, {
					dataIndex : "flinkman",
					text : "收货人",
					flex:1,
					editor:{
					}
				}, {
					dataIndex : "fphone",
					text : "电话",
					flex:1,
					editor:{
						xtype:'numberfield',
						hideTrigger :true
					}
				},{
					xtype:'actioncolumn',
					width:100,
					align:'center',
					text:'操作',
					flex:1,
					id:'DJ.order.Deliver.productdemandfile.productActions',
			        items: [
				        {
				            icon: 'images/delete.gif',
				            tooltip: '删除',
				            handler: function(grid, rowIndex, colIndex) {
				            	grid.store.removeAt(rowIndex);
					            grid.up().setHeight(grid.up().height-22);
				            	if(grid.store.getCount()==0){
				            		grid.up().next().handler();
				            	}
				            }
				        }]
				}]
	},{
		xtype:'button',
		text:'<b>添加地址</b>',
		handler:function(){
			var ctable = this.prev('cTable');
			ctable.setHeight(ctable.height+22);
			ctable.store.add(0);
		}
	},{
		xtype:'label',
		text:'备注:',
		margin:'0'
	},{
		name : 'fdescription',
		xtype : 'textareafield',
		margin:'0',
//		fieldLabel : '备注',
		width : '100%'
	},{
		name:'fcreatetime',
		xtype:'textfield',
		hidden:true
	},{
		name:'fcreatorid',
		xtype:'textfield',
		hidden:true
	},{
		
	}]
})
