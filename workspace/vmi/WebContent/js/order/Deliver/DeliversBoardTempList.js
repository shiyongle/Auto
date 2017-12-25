Ext.define('DJ.order.Deliver.DeliversBoardTempList', {
			extend : 'Ext.c.GridPanel',
			title : "暂存订单",
			id : 'DJ.order.Deliver.DeliversBoardTempList',
			pageSize : 50,
			closable : true,// 是否现实关闭按钮,默认为false
			url : 'selectTempBoardDeliverapplyCustsMV.do',
			Delurl : "DelBoardDeliverapplyList.do",
			EditUI : "DJ.order.Deliver.singleBoardDeliverapplyEdit",
			exporturl:"",//导出为EXCEL方法
			features : [{
				ftype : 'summary'
			}],
			onload: function(){
				var me = this;
				me.down('button[id='+me.id+'.viewbutton]').hide();
				me.down('button[id='+me.id+'.exportbutton]').hide();
				me.down('button[id='+me.id+'.addbutton]').hide();
				me.down('button[id='+me.id+'.querybutton]').hide();
			},
			listeners:{//修改合并样式;
			    render:function(me){
			     me.store.on('load',function(){
			      me = Ext.getDom(me.id);
			      me.getElementsByTagName('tfoot')[0].setAttribute('class','x-grid-row  x-grid-data-row');
			     })
			    }
			   },
			custbar : [{
				text: '提交订单',
				handler: function(){
					Ext.djRequest('updateBoardStateToCreate.do',this,{
						fstate: '7'
					});
				}
			}],
			fields : [{
						name : 'fid'
					}, {
						name : 'faddress',
						myfilterfield : 'faddress',
						myfiltername : '配送地址',
						myfilterable : true
					}, {
						name : 'fnumber',
						myfilterfield : 'fnumber',
						myfiltername : '申请单号',
						myfilterable : true,
						type : "String",
						sortDir : "DESC"
					}, {
						name : 'fcustomerid'
					}, {
						name : 'fcustname',
						myfilterfield : '_custname',
						myfiltername : '客户名称',
						myfilterable : true
					},{
						name : 'famount',
						type : 'int'
					},{
						name : 'famountpiece',
						type : 'int'
					}, {
						name : 'farrivetime',
						myfilterfield : 'farrivetime',
						myfiltername : '配送时间',
						myfilterable : true
					},{
						name : 'fsuppliername'
					},{
						name: 'fstockinqty'
					},{
						name : 'stateinfo'
					},{
						name: 'fstate'
					},'flabel','foutQty','fmaterialname','fboxmodel','fboxlength','fboxwidth','fboxheight','fmateriallength','fmaterialwidth','fstavetype','fvline','fhline','foutQtypiece','fseries','fdescription','fordesource'
					
					
					],
			columns : {items:[Ext.create('DJ.Base.Grid.GridRowNum'),
					{
						header: '客户标签',
						width: 100,
						dataIndex: 'flabel',
						sortable: true
					},
					{
						'header' : '制造商',
						width : 80,
						'dataIndex' : 'fsuppliername',
						sortable : true
					},{
						'header' : '材料',
						width : 120,
						'dataIndex' : 'fmaterialname',
						sortable : true
					},{
						'header' : '箱型',
						width : 80,
						'dataIndex' : 'fboxmodel',
						draggable : false,
						sortable : true,
						renderer : function(value) {
							if (value == 1) {
								return '普通';
							}else if(value == 2){
								return '全包';
							}else if(value == 3){
								return '半包';
						    }else if(value == 4){
								return '有底无盖';
						    }else if(value == 5){
								return '有盖无底';
						    }else if(value == 6){
								return '围框';
						    }else if(value == 7){
								return '天地盖';
						    }else if(value == 8){
								return '立体箱';
						    }else if(value == 0){
						    	return '其它';
						    }
						}
					},{
						'header' : '压线方式',
						'dataIndex' : 'fstavetype',
						width : 60,
						sortable : true
					},{
						'header' : '纸箱规格(CM)',
						hideable: false,
						draggable : false,
						
						stateId : 'cartonSize',
						
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
						hideable: false,
						draggable : false,
						
						stateId : 'theMaterialSpecifications',
						
						 columns: [{
						 'header' : '总长',
						'dataIndex' : 'fmateriallength',
						width : 60,
						sortable : true
						 },{
						 'header' : '总高',
						 'dataIndex' : 'fmaterialwidth',
						 width : 60,
						 sortable : true,
						summaryRenderer : function(value) {
						
							return "合计：";
							
						}
						 }]
					},{
						'header' : '配送数量',
						hideable: false,
						draggable : false,
						
						stateId : 'quantityofthestore',
						
						 columns: [{
						 'header' : '只',
						'dataIndex' : 'famount',
						width : 50,
						sortable : true,
						summaryType : 'sum',
						summaryRenderer : function(value) {
						
							return value;
							
						}
						 },{
						 'header' : '片',
						 'dataIndex' : 'famountpiece',
						 width : 50,
						 sortable : true,
						summaryType : 'sum',
						summaryRenderer : function(value) {
						
							return value;
							
						}
						 }]
					},{
						'header' : '成型方式',
						'dataIndex' : 'fseries',
						width : 80,
						sortable : true
					},{
						'header' : '压线公式',
						hideable: false,
						draggable : false,
						
						stateId : 'linePressingFormula',
						
						 columns: [{
							 'header' : '横向公式',
							 'dataIndex' : 'fhline',
							 width : 100,
							 sortable : true
						},{
						 'header' : '纵向公式',
						'dataIndex' : 'fvline',
						width : 100,
						sortable : true
						 }]
					},{
						'header' : '配送时间',
						'dataIndex' : 'farrivetime',
						width : 120,
						sortable : true
					},{
						'header' : '配送地址',
						'dataIndex' : 'faddress',
						width : 300,
						sortable : true
					},{
						'header' : '特殊要求',
						'dataIndex' : 'fdescription',
						width : 140,
						sortable : true
					},{
						'header' : '来源订单类型',
						'dataIndex' : 'fordesource',
						renderer:function(val){
							switch(val){
								case 'ios':return "苹果"
								case 'android':return "安卓"
								default:return "网页"
							}
						},
						sortable : true
					}]
			},
			selModel:Ext.create('Ext.selection.CheckboxModel')
})
		
		


		