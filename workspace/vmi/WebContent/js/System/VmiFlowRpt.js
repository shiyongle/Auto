Ext.require(["Ext.ux.form.DoubleDateField"]);
var dateType,pdttype,bgdate,eddate,year;
var VmiFlowStore = new Ext.data.JsonStore({
	storeId : 'VmiFlowStore',
	fields : [{
				name : 'date',
				type : "String"
			}, {
				name : 'times',
				type : "int"
			}],
	autoLoad : false,			
	proxy : {
		type : 'ajax',
		url : 'getVmiFlowRpt.do',
		reader : {
			type : 'json',
			root : 'data'
		}
	}
	});

var VmiFlowGridStore = new Ext.data.JsonStore({
			storeId : 'VmiFlowGridStore',
			pageSize : 1000,
			fields : [{
						name : 'fcustname',
						type : "String"
					}, {
						name : 'ftype',
						type : "String"
					}, {
						name : 'firstlogin',
						type : "String"
					}, {
						name : 'flatestlogin',
						type : "String"
					}, {
						name : 'flogincount',
						type : "String"
					}],
			autoLoad : false,
			proxy : {
				timeout : 600000,
				type : 'ajax',
				url : 'getVmiFlowRptdetail.do',
				reader : {
					type : 'json',
					root : 'data'
				}
			}
		});

//var pagingToolbar = new Ext.PagingToolbar({
////			pageSize : 200,
//			store : VmiFlowGridStore,
//			displayInfo : true,
//			displayMsg : '第{0}-第{1}条，一共{2}条',
//			emptyMsg : "没有记录"
//		});

/*VmiFlowGridStore.on('beforeload', function(store, options) {
			Ext.apply(VmiFlowGridStore.proxy.extraParams, {
						sdate : str,
						date : date
					});

		});*/
Ext.define('DJ.System.VmiFlowRpt', {
	extend : 'Ext.panel.Panel',
	id : 'DJ.System.VmiFlowRpt',
	autoScroll : true,
	border : false,
	title : '平台流量报表',
	closable : true,
	items : [{
		xtype : 'panel',
		title : '客户家数统计',
		height : 600,
		layout : 'fit',
		tbar : new Ext.Toolbar({
			layout : 'vbox',
			items : [{
		        xtype: 'label',
		        text: '筛选条件',
		        margin: '10 0 0 50',
		        style :{
		        	fontWeight: 'bold',
		        	fontSize: '10px'
		        }
			},{
				xtype :'panel',
				baseCls:'x-plain',
				width : 700,
				layout : 'hbox',
				margin: '10 0 0 50',
				items:[
				 {
					xtype: 'doubledatefield',
					useDefaultfilter : true,
					fieldLabel: '创建时间',
					itemId: 'fcreatetime',
					margin: '0 30 0 0',
					width: 250,
					labelWidth: 55,
					listeners: {
						render:function(me){
							var myDate = new Date();  
							var year = myDate.getFullYear();
							var month = myDate.getMonth()+1;
							if (month<10){
								month = "0"+month;
							}
							var month_first =year+'-'+month+'-01';
							var today = Ext.Date.format(myDate,"Y-m-d");
							var defaultDt = month_first+" 到 "+today
							me.setValue(defaultDt);
						},
						change: function(me,val){
							var datavalue=val.split(me.separator);
							//var checkgroup=me.next();
							var dataArray=Ext.Array.map(datavalue,function(item)
							{
								return new Date(item);
							});
							if(Ext.Date.add(dataArray[0], Ext.Date.MONTH, 1)>=dataArray[1])
							{
									me.next().down("radio[inputValue=daytype]").setValue(true);
							}else if(Ext.Date.add(dataArray[0], Ext.Date.MONTH, 7)>=dataArray[1])
							{
									me.next().down("radio[inputValue=weektype]").setValue(true);
							}else if(Ext.Date.add(dataArray[0], Ext.Date.MONTH, 7)<dataArray[1]){
								me.next().down("radio[inputValue=monthtype]").setValue(true);
							}else
							{
								me.next().down("radio[inputValue=daytype]").setValue(true);
							}
						}
					}
				},{
					xtype : 'radiogroup',
					columns :　[100,100,100],
					itemId: 'datetype',
					items: [
				            { boxLabel: '按日计', name: 'dateType', inputValue: 'daytype',checked: true },
				            { boxLabel: '按周计', name: 'dateType', inputValue: 'weektype' },
				            { boxLabel: '按月计', name: 'dateType', inputValue: 'monthtype' }
				        ]
				}	         
				]
			},{
				margin: '10 0 0 50',
				xtype : 'checkboxgroup',
				itemId: 'pdttype',
				fieldLabel: '产品',
				columns :　[100,100,100,200],
				items: [
			            { boxLabel: '纸板订单', name: 'pdtType', inputValue: 0,checked: true },
			            { boxLabel: '在线设计', name: 'pdtType', inputValue: 1,checked: true },
			            { boxLabel: '送货凭证', name: 'pdtType', inputValue: 2,checked: true },
			            { boxLabel: '快速下单（含需求包下单）', name: 'pdtType', inputValue: 3,checked: true}
			        ]
			},{
				xtype :'panel',
				baseCls:'x-plain',
				width : 700,
				layout : 'hbox',
				margin: '0 0 0 650',
				items:[{
					xtype : 'button',
			        text: '清空',
			        handler : function() {
			        	this.up('panel').up('panel').down("radio[inputValue=daytype]").setValue(true);
			        	this.up('panel').up('panel').down('[itemId=pdttype]').setValue({
			        		pdtType : [0,1,2,3]
			        	});
			        }
				},{
					xtype : 'button',
					text: '确定',
					style:'margin-left:10px;',
					handler : function() {
						var dateValues =  this.up('panel').up('panel').down('doubledatefield').getSubmitValues();
						dateType =  this.up('panel').up('panel').down('[itemId=datetype]').getValue().dateType
						var pdtTypeArray =  this.up('panel').up('panel').down('[itemId=pdttype]').getValue().pdtType;
						if(Ext.isArray(pdtTypeArray)){
							pdttype = pdtTypeArray.join(",");
						}
						else{
							pdttype = pdtTypeArray;
						}
						bgdate = dateValues[0];
						eddate = dateValues[1];
						year = bgdate.substr(0,4);
						//consolo.log(pdttype);
						VmiFlowStore.load({
							params : {
								dateType : dateType,
								pdtType : pdttype,
								bgdate : bgdate,
								eddate : eddate
							}
						})

					}
				}]				     

			}
			]	
		}),		
		items : [{
			xtype : 'chart',
			id : 'DJ.System.VmiFlow.Chart',
			store : VmiFlowStore,
			animate : true,// 是否启用动画效果
			legend : {
				position : 'bottom' // 图例位置
			},
			shadow : true,
			axes : [{
						type : 'Numeric',
						position : 'left',
		                majorTickSteps:5,  
		                minorTickSteps:4,  
		                minimum: 0,  
						fields : ['times'],
						title : '客户家数'
					}, {
						type : 'Category',
						position : 'bottom',
						fields : ['date'],
						title : '日期'
					}],
			series : [{
				type : 'line',// 折线图表序列
				axis : 'left',
				xField : 'date',
				yField : 'times',
				title : '使用情况',
				markerCfg : {// 节点标识配置
					type : 'circle', // 节点形状，圆形
					radius : 2
					// 半径为4像素
				},
				highlight : {
					size : 4,
					radius : 4
				},
				// selectionTolerance:Number,
				showMarkers : true,
				tips : {
					trackMouse : true,
					width : 160,
					height : 40,
					smooth : true,
					lineWidth : 1,
					showMarkers : false,
					renderer : function(storeItem) {
						this.setTitle(storeItem.get('date') + " : "
								+ storeItem.get('times') + "家<br>"
								+ "（单击显示详情）");
					}
				},
				listeners : {
					itemmouseup : function(storeItem) {
						var date = storeItem.storeItem.data.date;
						Ext.getCmp("DJ.System.VimFlow.grid.totalLable").setText("累计不重复客户数:"+storeItem.storeItem.data.times);
						VmiFlowGridStore.load({			
							params : {
								start : 0,
								limit : 1000,
								page : 1,
								dateType : dateType,
								pdtType : pdttype,
								date : date,
								year : year
							}
						});
					}
				}
			}]

		}]
	}, {
		xtype : 'panel',
		height : 337,
		layout : 'fit',
		items : [{
					xtype : 'grid',
					title : '登录明细',
					id : 'DJ.System.VimFlow.grid',
					store : VmiFlowGridStore,
					columns : [{
								header : '客户',
								dataIndex : 'fcustname',
								width : 140
							}, {
								header : '产品',
								dataIndex : 'ftype',
								width : 300,
								renderer:function(val){
									return val.replace(0,'纸板订单').replace(1,'在线设计').replace(2,'送货凭证').replace(3,'快速下单');
								}
							
							},{
								header : '首次登录',
								dataIndex : 'firstlogin',
								width : 160
							},{
								header : '最近登录',
								dataIndex : 'flatestlogin',
								width : 160
							},{
								header : '登录次数',
								dataIndex : 'flogincount',
								flex : 1
							}],
				 dockedItems : {					 
					 xtype : "label",
					 id : "DJ.System.VimFlow.grid.totalLable",
					 height : '30px',
					 text : '',
					 dock: 'bottom'
				 },
				 tbar :[{
					 xtype : 'myexcelexportercuscfgbutton'
				 }] 
				}]
	}]
});