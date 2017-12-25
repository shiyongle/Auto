Ext.ns("Ext.app.batchBoardDeliverapplyEdit");
// 验证时间
Ext.app.batchBoardDeliverapplyEdit.valformula=function(val) {
	if (Ext.isEmpty(val))
		return true;
	if(!new RegExp("^(\\d+(\\.?\\d*)\\+)+\\d+(\\.?\\d*)$").test(val))
	{
		return "输入格式错误，格式为A+B+C"
	}
	var tableid = this.ownerCt.findParentByType("cTable");
	var record = tableid.getSelectionModel().getSelection();
	if (this.getName() == "fhformula") {//横向公式值
		var v1 = record[0].get("fmateriallength");
	} else  {
		var v1 = record[0].get("fmaterialwidth");
	}
	if (Ext.isEmpty(v1))
		return true;
	if (this.getName() == "fhformula") {
		return eval(val) === v1 ? true : '压线公式值与总长值不相等';
	} else {
		return eval(val) === v1 ? true : '压线公式值与总高值不相等';
	}

}
Ext.app.batchBoardDeliverapplyEdit.faddressConfig = {
					beforeExpand : function() {
							//传值技巧，
							this.setDefaultfilter([{
								myfilterfield : "1",
								CompareType : "=",
								type : "string",
								value : 1
							},{
								myfilterfield : "tba.fcustomerid",
								CompareType : "like",
								type : "string",
								value : "-1"
							}]);
							this.setDefaultmaskstring(" #0 or #1 ");
						},
		MyConfig:{
			width:750,
			height:200,
			url : 'gainAddressListByUserFirst.do',
			hiddenToolbar : true,
			fields:[ {
						name : 'fid'
					}, {
						name : 'fname',
						myfilterfield : 'tba.fname',
						myfiltername : '名称',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'tba.fnumber',
						myfiltername : '地址编号',
						myfilterable : true
					}, {
						name : 'flinkman',
						myfilterfield : 'tba.flinkman',
						myfiltername : '联系人',
						myfilterable : true
					}, {
						name : 'fphone',
						myfilterfield : 'tba.fphone', 
						myfiltername : '联系电话',
						myfilterable : true
					}
			  ],
			columns:[ {
							'header' : '地址名称',
							'dataIndex' : 'fname',
							width : 405,
							sortable : true
						}, {
							'header' : '地址编号',
							'dataIndex' : 'fnumber',
							width : 80,
							sortable : true
						}, {
							'header' : '联系人',
							'dataIndex' : 'flinkman',
							width : 80,
							sortable : true
						}, {
							'header' : '联系电话',
							'dataIndex' : 'fphone',
							width : 150,
							sortable : true
						}
			]
		},
		listeners:{
			select:function(combo,record){
				Ext.getCmp('DJ.order.Deliver.Deliverboardapplys').chooseRecord.faddressComb = record[0];
			}
			
		}
};





Ext.define('DJ.order.Deliver.batchBoardDeliverapplyEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.Deliver.batchBoardDeliverapplyEdit',
	title : "纸板订单编辑界面",
	width : 1280,
	minHeight : 350,
	closable : true,
	modal : true,
	resizable:false,
	myobj: {},
	layout:'fit',
	url:'SaveBoardbatchDeliverapply.do',
	cautoverifyinput:false,
//	requires:'Ext.ux.form.DateTimeField',
	onload:function(){
		var grid = Ext.getCmp('DJ.order.Deliver.Deliverboardapplys');
		grid.removeDocked(grid.down('toolbar'));
				
		
		var me = this;
		Ext.Ajax.request({
			timeout : 60000,
			url : "getSupplierAndAddressByUser.do",
			success : function(response, option) {

				var obj = Ext.decode(response.responseText);
				if (obj.success == true) {
					if (obj.data != undefined) {
						if(obj.data[0].fsupplier_fid!=undefined)
						{
							me.myobj.fsupplierid= obj.data[0].fsupplier_fid;
							me.myobj.fsuppliername= obj.data[0].fsupplier_fname;
						}
						if(obj.data[0].faddress_fid!=undefined)
						{
						me.myobj.faddressid = obj.data[0].faddress_fid;
						me.myobj.faddress = obj.data[0].faddress_fname;
						me.myobj.flinkphone = obj.data[0].faddress_fphone;
						me.myobj.flinkman = obj.data[0].faddress_flinkman;
						}
					}
				} 
				
				var store = Ext.getCmp('DJ.order.Deliver.Deliverboardapplys').getStore(),
				obj = me.myobj ,
				arr = [] ;
				if(store.getCount()==0){
				for(i=0;i<5;i++){
					arr.push({
//						farrivetime:Ext.Date.add(new Date(new Date().setHours(14,0,0,0)),Ext.Date.DAY,5),
						famount:0,
						faddressid:Ext.isEmpty(obj.faddressid)?'':obj.faddressid,
						faddress:Ext.isEmpty(obj.faddress)?'':obj.faddress,
						flinkphone:Ext.isEmpty(obj.flinkphone)?'':obj.flinkphone,
						flinkman:Ext.isEmpty(obj.flinkman)?'':obj.flinkman,
//						fstavetype:"不压线",
						fboxmodel:1,
						famountpiece:0,
//						fhformula:'无',
//						fvformula:'无',
						fsuppliername:Ext.isEmpty(obj.fsuppliername)?'':obj.fsuppliername,
						fsupplierid:Ext.isEmpty(obj.fsupplierid)?'':obj.fsupplierid
					});
				}
				store.add.apply(store,arr);
			}
			}
		});
		var c0 = this;
			Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.savebutton').setHandler(
			function() {
				try {
					if(c0.Action_BeforeSubmit(c0)===false){
						Ext.MessageBox.show({title:'提示',msg: '配送数量存在异常,是否修改?', buttons: Ext.MessageBox.YESNO,buttonText:{  yes: "修改",   no: "不修改" },fn:function(btn, text){
							if(btn=="no"){
								c0.Action_Submit(c0);
								c0.Action_AfterSubmit(c0)
							}else{
								return;
							}
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
	}
	,
	Action_BeforeSubmit:function(){
		var store = this.down('grid').getStore();
			records = [],fisright=true;
		store.each(function(record){
			//批量黏贴时，是没有id的
			if(Ext.isEmpty(record.get("fmaterialfid"))){
				records.push(record);
			}
		});
		Ext.Array.each(records,function(item){
			store.remove(item);
		});	
		store.each(function(record,index){
			//批量黏贴时，是没有id的
			if( Ext.isEmpty(record.get("fmaterialfid"))){
				throw '请选择材料';
			}else if( Ext.isEmpty(record.get("famount"))){
				throw '请填写配送数量(只)';
			}else if( Ext.isEmpty(record.get("famountpiece"))){
				throw '请填写配送数量(片)';
			}else if(record.get('famount')==0||record.get('famount')==0){
				throw '配送数量填写错误';
			}else if(Ext.isEmpty(record.get("fsupplierid"))){
				throw '请选择制造商！';
			}else if(Ext.isEmpty(record.get("fstavetype"))){
				throw '请选择压线方式';
			}else if(Ext.isEmpty(record.get("fvformula"))){
				throw '请填写纵向压线方式';
			}else if(Ext.isEmpty(record.get("fhformula"))){
				throw '请填写横向压线公式！';
			}else if(Ext.isEmpty(record.get("farrivetime"))){
				throw '请选择配送时间！';
			}else if(Ext.isEmpty(record.get("faddressid"))){
				throw '请选择配送地址！';
			}else if(record.get('fseries')==''&&Ext.isEmpty(record.get("fseries"))){
				throw '请选择成型方式！';
			}else if((Ext.isEmpty(record.get("fboxlength"))||Ext.isEmpty(record.get("fboxwidth"))||Ext.isEmpty(record.get("fboxheight")))&&(Ext.isEmpty(record.get("fmaterialwidth"))||Ext.isEmpty(record.get("fmateriallength"))))
			{
				throw '纸箱规格与下料规格必填一项';
			}else if(record.get("fstavetype")!=="不压线")
			{
				
				if((!Ext.isEmpty(record.get("fmateriallength"))&& eval(record.get("fhformula"))!==record.get("fmateriallength"))||(!Ext.isEmpty(record.get("fmaterialwidth"))&&eval(record.get("fvformula"))!==record.get("fmaterialwidth")))
				{
					throw '压线公式值与总长、总高值不相等';
				}
			}
			var series=0;
			switch(record.get('fseries'))
			{
				case "一片":series=1;break;
				case "二片":series=2;break;
				case "四片":series=4;break;
			}
			if(record.get("famount")*series!=record.get("famountpiece")*1){
					fisright=false;
					return ;
			}
		});

	
		if(store.getCount()<1){
			throw '请先添加记录再保存！';
		}

		if(Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.fmaterialComb')!=undefined){
			Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.fmaterialComb').inputEl.dom.value='0';
		}
		if(Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.faddressComb')!=undefined){
			Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.faddressComb').inputEl.dom.value='0';
		}
		return fisright;
		
	},
	custbar:['','-','',{
		xtype:'textfield',
		height:20,
		width:30,
		emptyText:'行数',
		value:5,
		id:'DJ.order.Deliver.batchBoardDeliverapplyEdit.RowCount'
	},{
		text:'新增行',
		height:30,
		handler:function(){
			var store = this.up('window').down('grid').getStore(),
				rowCount = this.previousSibling().getValue(),
				arr = [],i;
			var obj = this.up('window').myobj ;
			if(store.getCount()==0){
			for(i=0;i<rowCount;i++){
				arr.push({
					famount:0,
						faddressid:Ext.isEmpty(obj.faddressid)?'':obj.faddressid,
						faddress:Ext.isEmpty(obj.faddress)?'':obj.faddress,
						flinkphone:Ext.isEmpty(obj.flinkphone)?'':obj.flinkphone,
						flinkman:Ext.isEmpty(obj.flinkman)?'':obj.flinkman,
						fboxmodel:1,
						famountpiece:0,
						fsuppliername:Ext.isEmpty(obj.fsuppliername)?'':obj.fsuppliername,
						fsupplierid:Ext.isEmpty(obj.fsupplierid)?'':obj.fsupplierid
				});
			}
			}else
			{
				var lastrecord=store.last();
				for(i=0;i<rowCount;i++){
				arr.push({
						famount:0,
						fsuppliername:Ext.isEmpty(lastrecord.get("fsuppliername"))?'':lastrecord.get("fsuppliername"),
						fsupplierid:Ext.isEmpty(lastrecord.get("fsupplierid"))?'':lastrecord.get("fsupplierid"),
						faddressid:Ext.isEmpty(lastrecord.get("faddressid"))?'':lastrecord.get("faddressid"),
						faddress:Ext.isEmpty(lastrecord.get("faddress"))?'':lastrecord.get("faddress"),
						flinkphone:Ext.isEmpty(lastrecord.get("flinkphone"))?'':lastrecord.get("flinkphone"),
						flinkman:Ext.isEmpty(lastrecord.get("flinkman"))?'':lastrecord.get("flinkman"),
						farrivetime:Ext.isEmpty(lastrecord.get("farrivetime"))?null:lastrecord.get("farrivetime"),
						fboxmodel:1,
						famountpiece:0
				});
			}
			}
			store.add.apply(store,arr);
		}
	},'','-','',{
		text:'删除行',
		height:30,
		handler:function(){
			var grid = this.up('window').down('grid');
			grid.getStore().remove(grid.getSelectionModel().getSelection());
			grid.getStore().loadData([],true)
		}
	},'','-','',{
		text:'新增地址',
		height:30,
		handler:function(){
				var editui = Ext.getCmp("DJ.System.AddressEdit");
							if (editui == null) {
								editui = Ext.create('DJ.System.AddressEdit');
							}
							editui.seteditstate("add");
						    editui.show();
		}
		},'->',{
			text: '切换下单方式',
			height: 30,
			handler: function(){
				localStorage.$singleBoardOrder = '1';
				var newWin = Ext.create('DJ.order.Deliver.singleBoardDeliverapplyEdit');
				newWin.setparent('DJ.order.Deliver.DeliversBoardList');
				newWin.seteditstate("edit");
				this.up('window').close();
				newWin.show();
			}
		}
	],
	initComponent:function(){
		Ext.apply(this,{
			items:[{
				xtype:'cTable',
				name:'Deliverapply',
				height:300,
				pageSize:100,
				url:'getUpdateBoardApplyList.do',
				id:'DJ.order.Deliver.Deliverboardapplys',
				selModel : Ext.create('Ext.selection.CheckboxModel'),
				plugins : [
				Ext.create("Ext.grid.plugin.CellEditing",{
					
					onSpecialKey : function(ed, field, e) {
						var sm;

						if (e.getKey() === e.TAB || e.getKey() === e.ENTER) {
							e.stopEvent();

							if (ed) {
								// Allow the field to act on tabs before
								// onEditorTab, which ends
								// up calling completeEdit. This is useful for
								// picker type fields.
								ed.onEditorTab(e);
							}
							sm = ed.up('tablepanel').getSelectionModel();
							if (sm.onEditorTab) {
								if(ed.field.name=="fdescription")//切换到下一行时，把材料赋值过去
								{
									var bstore=ed.up('tablepanel').getStore();
									var srecord=sm.getSelection()[0];
									var recordb =bstore.getAt(bstore.indexOf(srecord)+1);
									if(recordb&&Ext.isEmpty(recordb.get("fmaterialfid"))){//没有选过把材料赋值
									recordb.set('fmaterialfid',srecord.get("fmaterialfid"));
									recordb.set('fmaterialname',srecord.get("fmaterialname"));
									recordb.set('farrivetime',srecord.get("farrivetime"));
									}
									
								}
								
								return sm.onEditorTab(ed.editingPlugin, e);
							}
						}
					},
					
					clicksToEdit:1,
					listeners:{
						beforeedit:function(editor, e){
		            		if(e.column.dataIndex=='fhformula'&& e.record.get('fhformula')=='无'){
		            			return false;
		            		}
		            		if(e.column.dataIndex=='fvformula'&& e.record.get('fhformula')=='无'){
		            			return false;
		            		}
		            	},
						edit:function(editor, e){
							var record = e.record,
							cRecord = e.grid.chooseRecord;
							if(e.column.dataIndex=='fstavetype' ){
								
								if(e.value==="不压线")
								{
									record.set('fhformula',"无");
									record.set('fvformula',"无");
								}
								else if (e.originalValue==="不压线"){
									record.set('fhformula',"");
									record.set('fvformula',"");

								}
								
							}else if(e.column.dataIndex=="famount"&&record.get("famount")>0&&record.get("famountpiece")==0)
							{
									record.set('famountpiece',record.get("famount"));
									record.set('fseries',"一片");
									
							}else if((e.column.dataIndex=="famount"||e.column.dataIndex=="famountpiece")&&record.get("famount")>0&&record.get("famountpiece")>0)
							{
								if(record.get("famountpiece")/record.get("famount")==1)
									record.set('fseries',"一片");
								else if(record.get("famountpiece")/record.get("famount")==2)
									record.set('fseries',"二片");
								else if(record.get("famountpiece")/record.get("famount")==4)
									record.set('fseries',"四片");
								else record.set('fseries',"");
							}else if((e.column.dataIndex=="fboxlength"||e.column.dataIndex=="fboxwidth" ||e.column.dataIndex=="fboxheight")&&!Ext.isEmpty(record.get("fboxlength"))&&!Ext.isEmpty(record.get("fboxwidth"))&&!Ext.isEmpty(record.get("fboxheight")))
							{
									record.set('fstavetype',"普通压线");
									if(record.get('fhformula')==="无")
									{
										record.set('fhformula',"");
									} 
									if(record.get('fvformula')==="无")
									{
										record.set('fvformula',"");
									} 
							}
							else if((e.column.dataIndex=="fboxlength"||e.column.dataIndex=="fboxwidth" ||e.column.dataIndex=="fboxheight")&&(Ext.isEmpty(record.get("fboxlength"))||Ext.isEmpty(record.get("fboxwidth"))||Ext.isEmpty(record.get("fboxheight"))))
							{
								if(!Ext.isEmpty(record.get("fmaterialwidth"))&&!Ext.isEmpty(record.get("fmateriallength")))
								{
									record.set('fstavetype',"不压线");
									record.set('fhformula',"无");
									record.set('fvformula',"无");
								}
							}
							else if((e.column.dataIndex=="fmateriallength"||e.column.dataIndex=="fmaterialwidth")&&!Ext.isEmpty(record.get("fmaterialwidth"))&&!Ext.isEmpty(record.get("fmateriallength")))
							{
								if(!Ext.isEmpty(record.get("fboxlength"))&&!Ext.isEmpty(record.get("fboxwidth"))&&!Ext.isEmpty(record.get("fboxheight")))
								{
									record.set('fstavetype',"普通压线");
									if(record.get('fhformula')==="无")
									{
										record.set('fhformula',"");
									} 
									if(record.get('fvformula')==="无")
									{
										record.set('fvformula',"");
									} 
								}else
								{
									record.set('fstavetype',"不压线");
									record.set('fhformula',"无");
									record.set('fvformula',"无");
								}
								

							}
							else if(e.column.dataIndex=='fmaterialname'&&!Ext.isEmpty(cRecord.fmaterialComb)){
								record.set('fmaterialfid',cRecord.fmaterialComb.data.fid);
								record.set('fmaterialname',cRecord.fmaterialComb.data.fothername);
								e.grid.chooseRecord.fmaterialComb = '';
								Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.fmaterialComb').inputEl.dom.value='';

								}
							else if(e.column.dataIndex=='faddress' && !Ext.isEmpty(cRecord.faddressComb)){
								record.set('faddressid',cRecord.faddressComb.data.fid);
								record.set('faddress',cRecord.faddressComb.data.fname);
								record.set('flinkman',cRecord.faddressComb.data.flinkman);
								record.set('flinkphone',cRecord.faddressComb.data.fphone);
								e.grid.chooseRecord.faddressComb = '';
								Ext.getCmp('DJ.order.Deliver.batchBoardDeliverapplyEdit.faddressComb').inputEl.dom.value='';
							}else if(e.column.dataIndex=='fsuppliername' && !Ext.isEmpty(cRecord.fsupplierRecord)){
//								console.log(cRecord.fsupplierRecord);
								record.set('fsupplierid',cRecord.fsupplierRecord.data.fid);
								record.set('fsuppliername',cRecord.fsupplierRecord.data.fname);
								e.grid.chooseRecord.fsupplierRecord=null;
						}
						
						//非store默认传值时使用，去除左上方的红色三角形
						e.record.commit();
//						e.record.phantom = false;
						
						}
					}
				})],
				chooseRecord:{},
				fields : ['fid', 'fcreatorid', 'fcreatetime', 'fupdateuserid',
						'fupdatetime', 'fnumber', 'fcustomerid',
						'fcusproductid', 'farrivetime', 'flinkman',
						'flinkphone', 'famount', 'faddress', 'fdescription',
						//'fordered', 'fordermanid', 'fordertime',
						'fsaleorderid', 'fordernumber', 'forderentryid',
						//'fimportEas', 'fimportEasuserid', 'fimportEastime',
						'fouted', 'foutorid', 'fouttime', 'faddressid',
						//'feasdeliverid', 'fistoPlan', 'fplanTime',
						//'fplanNumber', 'fplanid', 'falloted', 
						'fiscreate','fcusfid', 'fstate', 'ftype', 'ftraitid', 'fcharacter',
						'foutQty', 'fsupplierid', 'fmaterialfid', 'fboxmodel',
						'fboxlength', 'fboxwidth', 'fboxheight',
						'fmateriallength', 'fmaterialwidth', 'fhformula',
						'fvformula', 'fseries', 'fstavetype', 'famountpiece',
						'fboxtype', 'fsuppliername', 'fmaterialname'
				    	],
						columns:[
							{text:'序号',
						xtype:'rownumberer',
						width:40},{
								header : '制造商',
								dataIndex : 'fsuppliername',
								sortable : true,
								editor:{
									xtype : 'combobox',
									valueField : 'fid',
									displayField : 'fname',
									editable:false,
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
										select:function(combo,record){
											Ext.getCmp('DJ.order.Deliver.Deliverboardapplys').chooseRecord.fsupplierRecord = record[0];
										var record=this.up('grid').getSelectionModel().getSelection();
										record[0].set("fmaterialname",'');
										record[0].set("fmaterialfid",'');
										record[0].set("farrivetime",'');
										}
									}
								}
						},{

							dataIndex:'fmaterialname',
							text:'材料',
							sortable:true,
							width : 120,
							editor:{
								xtype:'cCombobox',
								displayField:'fothername',
								id:'DJ.order.Deliver.batchBoardDeliverapplyEdit.fmaterialComb',
								valueField:'fid',
								allowBlank:false,
								blankText:'请选择材料',
								beforeExpand : function() {
										//传值技巧，
//									var tableid = this.ownerCt.findParentByType("cTable");
//									var record = tableid.getSelectionModel().getSelection();
									var record=this.up('grid').getSelectionModel().getSelection();
										this.setDefaultfilter([{
											myfilterfield : "fsupplierid",//f.fsupplierid
											CompareType : "=",
											type : "string",
											value :Ext.isEmpty(record[0].get("fsupplierid"))?"":record[0].get("fsupplierid")
										}]);
										this.setDefaultmaskstring("#0 ");
									},
								MyDataChanged : function(com) {
								var record=this.up('grid').getSelectionModel().getSelection();
								if (record.length >0 &&!Ext.isEmpty(record[0].get("fsupplierid"))&&com!==null) 
								{
								Ext.Ajax.request({
								timeout : 60000,
								url : "GetFirstDateofMaterial.do",
								params:{
									fmaterialfid:com[0].data.fid,
									fsupplierid:record[0].get("fsupplierid")
								},
								success : function(response, option) {

									var obj = Ext.decode(response.responseText);
									if (obj.success == true) {
										if (obj.data != undefined) {
 										record[0].set("farrivetime",Ext.Date.format(new Date(obj.data[0].farrivetime),'Y-m-d'));
										}
									}
								}
								});
								}
					},
								MyConfig:{
									width:400,
									height:200,
									hiddenToolbar:true,
//									url : 'getCardboardListByUser.do',
									url:'getCardboardListByAllocationrules.do',
									fields : [ {
											name : 'fid'
										}, {
											name : 'fname',
											myfilterfield : 'fname',
											myfiltername : '材料名称',
											myfilterable : true
										}, {
											name : 'flayer'
										}, {
											name : 'ftilemodelid'
										}, {
											name : 'feffect'
										}, {
											name : 'fcreatetime'
										},{
											name:'fothername'  ,convert: function(v, record){
											var fname="";
											if(!Ext.isEmpty(record.data.fname))
											{
												fname=record.data.fname;
											}
//											if(!Ext.isEmpty(record.data.fnumber))
//											{
//												if(fname.length>0) {fname=fname+" "}
//												fname+=record.data.fnumber;
//											}
												fname+="/"+record.data.flayer+record.data.ftilemodelid;
												return fname;
											}
										} ],
										columns:[{
											text:'序号',
											xtype:'rownumberer',
											width:40
										},{
											dataIndex:'fid',
											hidden : true
										},{
											text:'材料',
											dataIndex:'fname'
//										},{
//											text:'代码',
//											dataIndex:'fnumber'
										},{
											text:'层数',
											dataIndex:'flayer'
										},{
											text:'楞型',
											dataIndex:'ftilemodelid'
										},{
											text:'状态',
											dataIndex:'feffect',
											renderer:function(value){
												return value == '1' ? '是' : '否';
											}
										},{
											text:'创建时间',
											dataIndex:'fcreatetime'
										}]
								},
								listeners:{
									select:function(combo,record){
									Ext.getCmp('DJ.order.Deliver.Deliverboardapplys').chooseRecord.fmaterialComb = record[0];
									
									}
								}
							}
						}, {
								'header' : '箱型',
								width : 60,
								'dataIndex' : 'fboxmodel',
								sortable : true,
								allowBlank : false,
								editor:{
								xtype:'combo',
								editable:false,
								displayField:'boxstate',
								valueField:'boxvalue',
								store:Ext.create('Ext.data.Store',{
									fields:['boxvalue','boxstate'],
									data:[
									      {'boxvalue':'0','boxstate':'其他'},
									      {'boxvalue':'1','boxstate':'普通'},
									      {'boxvalue':'2','boxstate':'全包'},
									      {'boxvalue':'3','boxstate':'半包'}
									]
								}),
								value:"1"
								},
								renderer : function(value) {
										if (value == 1) {
											return '普通';
										}else if(value == 2){
											return '全包';
										}else if(value == 3){
											return '半包';
									    }else {
											return '其他';
										}
									}
							}, {
								'header' : '纸箱规格(CM)',
						 columns: [{
						 'header' : '长',
						'dataIndex' : 'fboxlength',
						width : 60,
						sortable : true,
						editor : {
									xtype : 'numberfield',
									minValue :0.1,
									decimalPrecision:1,
//									allowBlank : false,
									minText:"不允许为负数或0"
								}
						 },{
						 'header' : '宽',
						'dataIndex' : 'fboxwidth',
						width : 60,
						sortable : true,
						editor : {
									xtype : 'numberfield',
									minValue :0.1,
									decimalPrecision:1,
									minText:"不允许为负数或0"
								}
						 },{
						 'header' : '高',
						'dataIndex' : 'fboxheight',
						width : 60,
						sortable : true,
						editor : {
									xtype : 'numberfield',
									minValue :0.1,
									decimalPrecision:1,
									minText:"不允许为负数或0"
								
								}
						 }]
						 	},{
						'header' : '下料规格(CM)',
						 columns: [{
						 'header' : '总长',
						'dataIndex' : 'fmateriallength',
						width : 70,
						sortable : true,
						editor : {
									xtype : 'numberfield',
									minValue :0.1,
									minText:"不允许为负数或0",
									decimalPrecision:1
									
								}
						 },{
						 'header' : '总高',
						 'dataIndex' : 'fmaterialwidth',
						 width : 70,
						 sortable : true,
						 editor : {
									xtype : 'numberfield',
									minValue :0.1,
									minText:"不允许为负数或0",
									decimalPrecision:1
								}
						 }]
						 },{
								'header' : '压线方式',
								width : 70,
//								id:'DJ.order.Deliver.Deliverapplys.balance',
								'dataIndex' : 'fstavetype',
								sortable : true,
								editor:{
								xtype:'combo',
								editable:false,
								displayField:'state',
								allowBlank : false,
								valueField:'value',
								store:Ext.create('Ext.data.Store',{
										fields:['value','state'],
										data:[
										      {value:'不压线',state:'不压线'},
										      {value:'普通压线',state:'普通压线'},//原：纵向内压
										      {value:'内压线',state:'内压线'},//原：纵向平压
										      {value:'外压线',state:'外压线'},//原：纵向外压
										      {value:'横压',state:'横压'}
										]
								})
								}
						 },{
						'header' : '压线公式',
						draggable:false,
						 columns: [{
						 		 
						 'header' : '横向公式',
						 'dataIndex' : 'fhformula',
						draggable:false,
						 width : 150,
						 sortable : true,
						 listeners:{
							render:function(me){
								Ext.tip.QuickTipManager.register({
									   target: me.id,
									   text: '横向压线公式:结舌+长+宽+长+宽'
								});
								}
						},
						editor : {
									emptyText : '结舌+长+宽+长+宽'
//									,regex : /^(\d+(\.?\d*)\+)+\d+(\.?\d*)$/
									,validator : Ext.app.batchBoardDeliverapplyEdit.valformula
						}
						 },{	
						 'header' : '纵向公式',
						'dataIndex' : 'fvformula',
						width : 150,
						sortable : true,
						 draggable:false,
						 listeners:{
							render:function(me){
								Ext.tip.QuickTipManager.register({
									   target: me.id,
									   text: '纵向压线公式:(宽+加边系数)/2+高+(宽+加边系数)/2'
								});
								}
						},
//						renderer : function(value) {
//							var title='<ul><li>结舌+长+宽+长+宽</li></ul>';
//							return '<span data-qtip="ggg">'+1111+'</span>';
//						}
//						,
						editor : {
					emptyText:'(宽+加边系数)/2+高+(宽+加边系数)/2'
//					regex : /^(\d+(\.?\d*)\+)+\d+(\.?\d*)$/
					,validator : Ext.app.batchBoardDeliverapplyEdit.valformula
//					regexText : ''
							}
						 }]
						},{
						'header' : '配送数量',
						 columns: [{
						 'header' : '只',
						'dataIndex' : 'famount',
						width : 70,
						sortable : true,
							editor : {
									xtype : 'numberfield',
									minValue :1,
									minText:"不允许为负数或0",
									decimalPrecision:0
								}
						 },{
						 'header' : '片',
						 'dataIndex' : 'famountpiece',
						 width : 70,
						 sortable : true,
						 	editor : {
									xtype : 'numberfield',
									minValue :1,
									minText:"不允许为负数或0",
									decimalPrecision:0
								}
						 }]
						 
							},{
								'header' : '成型方式',
								width : 60,
//								id:'DJ.order.Deliver.Deliverapplys.balance',
								'dataIndex' : 'fseries',
								sortable : true,
								editor:{
								xtype:'combo',
								editable:false,
								allowBlank : false,
								displayField:'state',
								valueField:'value',
								store:Ext.create('Ext.data.Store',{
									fields:['value','state'],
									data:[
									      {value:'一片',state:'一片'},
									      {value:'二片',state:'二片'},
									      {value:'四片',state:'四片'}
									]
								})
								}
							}, {
								'header' : '配送时间',
								'dataIndex' : 'farrivetime',
								xtype : "datecolumn",
//								format : "Y-m-d",
								width : 120,
								sortable : true,
								renderer:function(value){
								if(!Ext.isEmpty(value)){
								if (value instanceof Date) {
										value = Ext.Date.format(value, "Y-m-d");
									}else
									{
										value=value.substr(0,10);
									}
								return value;
								}
								},
								editor : {
									xtype : 'datefield',
									allowBlank : false,
									format : 'Y-m-d'
								}
							}, {
								dataIndex:'faddress',
								text:'配送地址',
								sortable:true,
								width : 160,
								editor:
								Ext.apply({
									xtype:'cCombobox',
									displayField:'fname',
									valueField:'fid',
									id:'DJ.order.Deliver.batchBoardDeliverapplyEdit.faddressComb',
									allowBlank:false,
									blankText:'请选择送货地址'
									},
									Ext.app.batchBoardDeliverapplyEdit.faddressConfig)
						
							},{
								dataIndex:'fdescription',
								text:'特殊要求',
								sortable:true,
								width : 160,
								editor:{
										xtype : 'textfield'
								}
							}]
			}]
		});
		this.callParent(arguments);
	}
});