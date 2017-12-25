Ext.define('DJ.order.Deliver.MyStockAddEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.Deliver.MyStockAddEdit',
	title : "批量新增编辑界面",
	width : 1000,
	minHeight : 250,
	closable : true,
	modal : true,
	layout:'fit',
	url:'saveMyStockInBulk.do',
	requires: ['Ext.ux.form.MyDateField','Ext.ux.form.DateTimeField'],
	onload:function(){
		var grid = Ext.getCmp('DJ.order.Deliver.MyStocks');
		grid.removeDocked(grid.down('toolbar'));
		this.down('button[text=新增行]').handler();
		var c0 = this;
		Ext.getCmp('DJ.order.Deliver.MyStockAddEdit.savebutton').setHandler(
			
			function() {
				try {
					if(c0.Action_BeforeSubmit(c0)===false){
						Ext.MessageBox.confirm('提示', '制造商未选择，是否继续保存?',function(btn, text){
							if(btn=="yes"){
								c0.Action_Submit(c0);
								c0.Action_AfterSubmit(c0)
							}else{
								return;
							}
						})
					}else{
						c0.Action_Submit(c0);
						c0.Action_AfterSubmit(c0)
					}
				
					
				} catch (e) {
					Ext.MessageBox.alert('提示', e);
				}
			}
		)
	},
	Action_BeforeSubmit:function(me){
		var edit = this.down('grid');
		var store = this.down('grid').getStore(),
			records = [];
		var fsupplier = true;
		store.each(function(record){
			if(record.get('fcustproductname')==''){
				records.push(record);
			}else if(record.get('fplanamount')==''){
				throw '计划数量不能为空！';
			}
			
		});
		Ext.Array.each(records,function(item){
			store.remove(item);
		})
		if(store.getCount()<1){
			throw '请先添加备货再保存！';
		}
		store.each(function(record){
			if(record.get('fsupplierid')==''){
				fsupplier = false;
			}
		})
		Ext.getCmp('DJ.order.Deliver.MyStockAddEdit.ProductnameCombo').inputEl.dom.value='0';
		return fsupplier;
	},
	custbar:['','-','',{
		xtype:'textfield',
		height:20,
		width:30,
		emptyText:'行数',
		value:5,
		id:'DJ.order.Deliver.MyStockAddEdit.RowCount'
	},{
		text:'新增行',
		height:30,
		handler:function(){
			var store = this.up('window').down('grid').getStore(),
				rowCount = this.previousSibling().getValue(),
				arr = [],i;
			Ext.Ajax.request({
				url:'getSupplierListOfCustomer.do',
				success:function(response,option){
					var obj = Ext.decode(response.responseText);
					if (obj.success == true && obj.total==1) {
						for(i=0;i<5;i++){
							arr.push({
								ffinishtime:Ext.Date.add(new Date(),Ext.Date.DAY,7),
								fconsumetime:Ext.Date.add(new Date(),Ext.Date.DAY,7),
								fsupplierid:obj.data[0].fid,
								fsuppliername:obj.data[0].fname
							})
							
						}
						store.add.apply(store,arr);
					}
					// 2015-06-04 by lu  关联东经，默认东经
					else if (obj.success == true && obj.total > 1) {
						var newArry = Ext.Array.filter(obj.data,function(item,index,array){ 
							  if(item.fname=='东经')
								  return true;
						  });
						for(i=0;i<5;i++){
							if(newArry.length==1){
								arr.push({
									ffinishtime:Ext.Date.add(new Date(),Ext.Date.DAY,7),
									fconsumetime:Ext.Date.add(new Date(),Ext.Date.DAY,7),
									fsupplierid:newArry[0].fid,
									fsuppliername:newArry[0].fname
								})	
							}
							else{
								arr.push({
									ffinishtime:Ext.Date.add(new Date(),Ext.Date.DAY,7),
									fconsumetime:Ext.Date.add(new Date(),Ext.Date.DAY,7)
								})						
							}		
						}
						store.add.apply(store,arr);
					}
					else{
						for(i=0;i<5;i++){
							arr.push({
								ffinishtime:Ext.Date.add(new Date(),Ext.Date.DAY,7),
								fconsumetime:Ext.Date.add(new Date(),Ext.Date.DAY,7)
							})
							
						}
						store.add.apply(store,arr);
					}
				}
			})
		
			
		}
	},'','-','',{
		text:'删除行',
		height:30,
		handler:function(){
			var grid = this.up('window').down('grid');
			grid.getStore().remove(grid.getSelectionModel().getSelection());
		}
	},'','-','',{

		xtype:'textfield',
		fieldLabel : '采购订单号',
		labelWidth : 70,
		emptyText:'按回车键结束',
		enableKeyEvents:true,
		listeners:{
			blur:function( me, The, eOpts ){
				var store = this.up('window').down('cTable').getStore();
				store.each(function(model){
					model.set('fpcmordernumber',me.value);
				})
			},
			keypress:function( me, e, eOpts ){
				if(e.getKey()==13){
					var store = this.up('window').down('cTable').getStore();
					store.each(function(model){
						model.set('fpcmordernumber',me.value);
					})
				}
			}
		}
	
	},{
		xtype:'datetimefield',
		fieldLabel:'要求完成时间',
		format:'Y-m-d H:i',
//		itemId:'time',
		labelWidth:80,
		listeners:{
			select:function(field,val){
				var store = this.up('window').down('cTable').getStore();
				store.each(function(record){
					record.set('ffinishtime',val);
				})
			}
		}
	},{
		xtype:'datetimefield',
		fieldLabel:'预计消耗时间',
		format:'Y-m-d H:i',
//		itemId:'time',
		labelWidth:80,
		listeners:{
			select:function(field,val){
				var store = this.up('window').down('cTable').getStore();
				store.each(function(record){
					record.set('fconsumetime',val);
				})
			}
		}
	}],
	initComponent:function(){
		Ext.apply(this,{
			items:[{
				xtype:'cTable',
				name:'Mystock',
				height:200,
				pageSize:100,
				url:'',
				id:'DJ.order.Deliver.MyStocks',
				selModel : Ext.create('Ext.selection.CheckboxModel'),
				plugins:[Ext.create("Ext.grid.plugin.CellEditing",{
					clicksToEdit:1,
					listeners:{
						edit:function(editor, e){
							var record = e.record,
							cRecord = e.grid.chooseRecord;
							if(e.colIdx==2 && cRecord!=''){	//第一列（包装物名称）编辑后
								record.set('fcustproductid',cRecord.data.fid);
								record.set('fcustproductname',cRecord.data.fname);
								record.set('fcustproductnumber',cRecord.data.fnumber);
								e.grid.chooseRecord = '';
								Ext.getCmp('DJ.order.Deliver.MyStockAddEdit.ProductnameCombo').inputEl.dom.value='';
							}
							if(e.colIdx==7 && cRecord!=''){//选择制造商名称时
								record.set('fsupplierid',cRecord.data.fid);
								record.set('fsuppliername',cRecord.data.fname);
							}
						}
					}
				})],
				chooseRecord:'',
				fields:[{
					name:'fid'
				},{
					name:'fcustproductid'
				},{
					name:'fcustproductname'
				},{
					name:'fcustproductnumber'
				},{
					name:'fplanamount'
				},{
					name:'funit'
				},{
					name:'ffinishtime'
				},{
					name:'fconsumetime'
				},{
					name:'fsupplierid'
				},{
					name:'fremark'
				},{
					name:'fsuppliername'
				},{
					name:'fstate'
				},{
					name:'fremark'
				},{
					name:'fpcmordernumber'
				}],
				columns:[{
					dataIndex:'fpcmordernumber',
					text:'采购订单号',
					sortable:true,
					editor:{
						
					}
				},{
					dataIndex:'fcustproductname',
					text:'包装物名称',
					sortable:true,
					flex:2,
					editor:{
						xtype:'cCombobox',
						displayField:'fname',
						valueField:'fid',
						id:'DJ.order.Deliver.MyStockAddEdit.ProductnameCombo',
						allowBlank:false,
						blankText:'请选择包装物',
						beforeExpand:function(){
							var grid = Ext.getCmp('DJ.order.Deliver.MyStockAddEdit.CustProductList');
							grid.down('toolbar').hide();
						},
						MyConfig:{
							width:400,
							height:200,
							id:'DJ.order.Deliver.MyStockAddEdit.CustProductList',
							url : 'GetCustproductList.do',
							ShowImg: true,
							fields:[{
								name:'fid'
							},{
								name : 'fname',
								myfilterfield : 't_bd_Custproduct.fname',
								myfiltername : '名称',
								myfilterable : true
							},{
								name:'fnumber'
							}],
							columns:[{
								dataIndex:'fname',
								header:'包装物名称',
								sortable:true,
								flex:1
							},{
								dataIndex:'fnumber',
								header:'包装物编号',
								sortable:true,
								flex:1
							}]
						},
						listeners:{
							select:function(combo,record){
								Ext.getCmp('DJ.order.Deliver.MyStocks').chooseRecord = record[0];
							}
						}
					}
				},{
					dataIndex:'fcustproductnumber',
					text:'包装物编号',
					sortable:true,
					flex:2
				},{
					dataIndex:'fplanamount',
					text:'计划数量',
					xtype:'numbercolumn',
					width:80,
					editor:{
						xtype:'numberfield',
						allowBlank:false,
						minValue:1,
						allowDecimals:false,
						blankText:'请填写计划数量'
					}
				},{
					dataIndex:'funit',
					text:'单位',
					width:40,
					editor:{
						xtype:'textfield'
					}
				},{
					dataIndex:'fsupplierid',
					text:'制造商ID',
					hidden:true
				},{
					dataIndex:'fsuppliername',
					text:'制造商名称',
					width:80,
					editor:{
						xtype:'combobox',
						displayField:'fname',
						valueField:'fid',
						editable:false,
						listeners:{
							select : function(combo,records,eOpts){
								Ext.getCmp('DJ.order.Deliver.MyStocks').chooseRecord = records[0];
							}
						
						},
						store:Ext.create('Ext.data.Store',{
							fields: ['fid', 'fname'],
							proxy:{
								type:'ajax',
								url: 'getSupplierListOfCustomer.do',
						         reader: {
						             type: 'json',
						             root: 'data'
						         }
							}
					})
					}
				},{
					dataIndex:'ffinishtime',
					text:'要求完成时间',
					xtype:'datecolumn',
					format:'Y-m-d H:i',
					flex:1,
					editor:{
						xtype:'mydatefield',
						format:'Y-m-d H:i'
					}
				},{
					dataIndex:'fconsumetime',
					text:'预计消耗时间',
					xtype:'datecolumn',
					format:'Y-m-d H:i',
					flex:1,
					editor:{
						xtype:'mydatefield',
						format:'Y-m-d H:i'
					}
				},{
					dataIndex:'fremark',
					text:'备注',
					editor:{
						xtype:'textfield',
						format:'Y-m-d'
					}
				}]
			}]
		});
		this.callParent(arguments);
	}
});