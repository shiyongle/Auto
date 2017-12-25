Ext.define('DJ.order.Deliver.MainDeliversBoardEdit', {
	id : 'DJ.order.Deliver.MainDeliversBoardEdit',
	extend : 'Ext.Window',
	title : '纸板订单编辑界面',
	autoScroll : false,
	resizable:false,
	layout : 'border',
	width : 1030,
	height : 390,
	modal:true,
	selModel:Ext.create('Ext.selection.CheckboxModel'),
	listeners:{
		show:function(){
			var me = this;
			var edit = me.down('window');
			var ctable = me.down('grid');
			me.ctableListeners(me,ctable,edit);//右边cTable的事件处理
			
			edit.down('button').show();
			edit.on('beforeclose',function(){
				me.close();
			},this,{single: true});
			
			Ext.Ajax.request({
									url:'getCustaccountbalanceInfo.do',
									success:function(response,opts){
										var obj = Ext.decode(response.responseText);
										if(obj.success == true){
//											djsuccessmsg(obj.msg);
//											me.grid.store.load();
											Ext.MessageBox.alert('提示',obj.msg/*,function(btn){alert(btn);}*/);
										}
										
									}
								})
		}
	},
	setComboboxValue:function(formpanel,record){
		formpanel.down('textfield[name=fid]').setValue();//必须的
		formpanel.down('cCombobox[name=faddressid]').setmyvalue(
				"\"fid\":\""
				+ record.get("faddressid_fid")
				+ "\",\"fname\":\""
				+ record
						.get("faddressid_fname").replace(/\"/g,"'")
				+ "\"");
	},
	ctableListeners:function(me,ctable,edit){
		ctable.getStore().load();
		ctable.on('itemclick',function(m,record,item, index, e){
			edit.down('cCombobox[name=fmaterialfid]').MyDataChanged(record);
			edit.getform().form.loadRecord(record);
			edit.editdata=record.data;
			edit.onloadfields();
			me.setComboboxValue(edit,record);
		})
		ctable.down('button[text=添  加]').hide();
		ctable.down('button[text=删    除]').hide();
		
		ctable.down('toolbar').add(0,{iconCls : 'refresh',text:'刷新',handler:function(){
			ctable.getStore().load();
		}});
		
		ctable.getStore().on('load',function(){
//					if(localStorage.$CommonBoardOrderedFid){
//						var fid = localStorage.$CommonBoardOrderedFid.split(',');
//						var fname = localStorage.$CommonBoardOrderedFname.split(',');
//						ctable.getStore().each(function(record){
//								Ext.each(fid,function(id,indexs,countriesItSelf){
//									if(record.get('fid')==id){
//										record.set('CommonBoardOrderedFname',fname[indexs])	;						
//									}
//								})
//						});	
//					}
					
//					ctable.getStore().doSort(function(a,b){
//						if(a.get('counts') > b.get('counts')){
//							return -1;
//						}else{
//							return 1;
//						}
//					});
		});
	},
	initComponent : function() {
		var me = this;
		
		Ext.applyIf(me, {
			items: [{
			    region : 'west',
			    width:'59%',
			    height:'100%',
			    items:[constrainedWin = Ext.create('DJ.order.Deliver.singleBoardDeliverapplyEdit', {
	            constrain: true,
	            layout: 'fit',
	            title:'',
	            closable : false,
	            draggable:false,
	            items: {
	                border: false
	            }
	        })]
			},{
			    	region : 'east',
					xtype : 'cTable',
					title : '常用订单',
					width:'40%',
					height:'100%',
					pageSize : 100,
					custbar:[{
						text:'取消常用',
						handler:function(me){
							var type = 0;
							var fids = '';
							var record  = this.up('grid').getSelectionModel().getSelection();
							if(record.length!=1){
								Ext.Msg.alert('提示','请选择1条数据！');
								return;
							}
							for(var i = 0;i<record.length;i++){
								fids += record[i].get('fid');
								if(i<record.length-1){
									fids += ',';
								}
							}
							Ext.Ajax.request({
								url:'setCommonorder.do',
								params:{type:type,fids:fids},
								success: function(response, opts) {
							        var obj = Ext.decode(response.responseText);
							        if(obj.success===true){
							        	me.up('grid').getStore().loadPage(1);
							        }else{
							        	Ext.Msg.alert('提示',obj.msg);
							        }
							        
							    }
							})
						}
					}],
					url : "selectCommonorderDeliverapply.do",
					plugins:[Ext.create("Ext.grid.plugin.RowEditing",{
						clicksToEdit:2,
						saveBtnText : "保存",
			 			cancelBtnText : "取消",
						listeners:{
							edit:function( editor, context, eOpts ){
								var value = context.record.data.fcommonBoardOrder,me = this,fid = me.grid.getSelectionModel().getSelection()[0].get('fid');
								Ext.Ajax.request({
									url:'setCommonBoardOrderName.do',
									params:{'fid':fid,'name':value},
									success:function(response,opts){
										var obj = Ext.decode(response.responseText);
										if(obj.success == true){
											djsuccessmsg(obj.msg);
											me.grid.store.load();
										}else{
											Ext.MessageBox.alert('提示',obj.msg);
										}
										
									}
								})
							}
						}
					})],
					fields : [{
						name : 'fmaterialname'
					}, {
						name : 'fvstaveexp'
					}, {
						name : 'fhstaveexp'
					}, {
						name : 'flayer'
					}, {
						name : 'fhformula1'
					}, {
						name : 'fhformula'
					}, {
						name : 'fvformula'
					}, {
						name : 'fdefine1'
					}, {
						name : 'fdefine2'
					}, {
						name : 'fdefine3'
					}, {
						name : 'fmaterialfid_fid'
					}, {
						name : 'fmaterialfid_fname'
					},{
						name : 'fmaterialfid_flayer'
					},{
						name : 'fmaterialfid_ftilemodelid'
					},'flabel','fcommonBoardOrder','counts','fid','fiscreate','fstate','fcreatorid','fcreatetime','fnumber','fsupplierid_fname','fsupplierid','fboxmodel','fmateriallength','fmaterialwidth','fseries','fhline','farrivetime','fstavetype','fboxlength','fboxwidth','fboxheight','famount','famountpiece','fvline','faddressid_fid','faddressid_fname','fdescription',{'name':'fiscommonorder','type':'boolean'}
					],
					columns : {items:[{
						'header' : '常用订单名称',
//						width : 120,
						'dataIndex' : 'fcommonBoardOrder',
						sortable : true,
						editor:{
							
						}
					},{
						'header' : '材料',
						width : 120,
						'dataIndex' : 'fmaterialname',
						sortable : true
					},{
						'header' : '纸箱规格(CM)',
						 columns: [{
							'header' : '长',
							'dataIndex' : 'fboxlength',
							width : 50,
							sortable : true
						 },{
							'header' : '宽',
							'dataIndex' : 'fboxwidth',
							width : 50,
							sortable : true
						 },{
							'header' : '高',
							'dataIndex' : 'fboxheight',
							width : 50,
							sortable : true
						 }]
						 	
					},{
						'header' : '下料规格(CM)',
						 columns: [{
						 'header' : '总长',
						'dataIndex' : 'fmateriallength',
						width : 60,
						sortable : true
						 },{
						 'header' : '总高',
						 'dataIndex' : 'fmaterialwidth',
						 width : 60,
						 sortable : true
						 }]
					}
					]}
			    }]
		});
		me.callParent(arguments);
	}
})