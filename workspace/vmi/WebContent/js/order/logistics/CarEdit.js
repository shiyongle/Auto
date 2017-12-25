Ext.define("DJ.order.logistics.CarEdit",{
	id:'DJ.order.logistics.CarEdit',
	extend:'Ext.c.BaseEditUI',
	title:'车辆登记',
	width : 780,
	minHeight : 450,
	bodyPadding:'20',
	fid:'',
	ctype:'Car',
	viewurl : "getCarInfo.do",
	infourl : "getCarInfo.do",
	url : "saveOrUpdateCar.do",
	requires:['Ext.ux.form.SimpleLocalCombo','Ext.ux.form.SimpleCombo','Ext.ux.form.OneClickFileField'],
	listeners:{
		close:function(me){
			Ext.Ajax.request({
				url:'delCarImage.do',
				params:{'fid':me.fid},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						
					}
				}
			})
		}
	},
	onload:function(){
		var me = this;
		if(this.editstate=='view'||this.editstate=='edit'){
			var record = Ext.getCmp(this.parent).getSelectionModel().getSelection()[0];
			this.down('image').setSrc('../'+record.get('fpath'));
			this.down('component')
		}
		if(!Ext.isEmpty(this.fid)){
			this.down('textfield[name=fid]').setValue(this.fid);
		}
		window.onbeforeunload = function(){
		    me.close();
		}
	},
	statics: {
	},
	items:[{
				xtype: 'container',
				itemId: 'container1',
//				style: 'border-bottom: 1px solid #000;padding-bottom:10px;',
				items: [{
				xtype : 'fieldset',
				title : '<b>车主信息  <font color="red">*</font></b>',
				border:false,
				name : 'deliverapply',
				collapsible : false,
				layout:{
					type:'hbox'
				},
				fieldDefaults : {
					labelAlign : 'left',
					labelWidth : 55,
					margin:'5 10 5 20'
				},
				items : [{
						name : 'fdrivername',
						xtype : 'textfield',
						fieldLabel : '姓名'
					},{
						name : 'fdriverphone',
						xtype : 'textfield',
						fieldLabel : '联系电话'
					},{
						name : 'fdrivingexperience',
						xtype : 'numberfield',
						fieldLabel : '驾龄'
					},{
						name:'fcreatetime',
						xtype:'textfield',
						hidden:true
					},{
						name:'fcreaterid',
						xtype:'textfield',
						hidden:true
					},{
						name:'fcustomerid',
						xtype:'textfield',
						hidden:true
					},{
						name:'fid',
						xtype:'textfield',
						hidden:true
					}]
				},{
					xtype: 'component',
					style: 'margin-top:10px;margin-bottom:10px;',
					html: '<div style="border-bottom: 1px solid #000;padding-bottom:10px;"></div>'
				},{
					xtype : 'fieldset',
					border:false,
					title : '<b>车辆信息  <font color="red">*</font></b>',
					name : 'deliverapply',
					collapsible : false,
					layout:{
						type:'hbox'
					},
					fieldDefaults : {
						labelAlign : 'left',
						labelWidth : 55,
						margin:'5 10 5 20'
					},
					items : [{
						xtype: 'container',
						layout:'vbox',
						items:[{
							xtype: 'image',
							src: 'images/cps.jpg',
							width: 140,
							height: 120,
							style: 'border:1px solid #bbb',
							listeners : {
						        render : function(p) {//渲染后给el添加mouseover事件
						        	var me = this;
						        	 var magnifier = Ext.create('Ext.ux.form.Magnifier');
							         p.getEl().on('click', function(p){ 
							             magnifier.loadImages({
							             	fid: me.up('window').down('textfield[name=fid]').getValue()
								         });
								         var coord = p.getXY();
								         magnifier.showAt({
								            left: coord[0] + 80,
								            top: coord[1] + 5
								         });
							         })
						        }
						   }
					},{
							xtype: 'component',
							style: 'margin-top:15px',
							html: '<div style="margin:0 auto"><a href="" style="text-decoration:none;color:black;">上传图片</a><a href="" style="margin-left:45px;text-decoration:none;color:black;">删除图片</a></div>',
							listeners:{
								afterrender:function(me){//查看状态不允许修改图片
									var div = document.getElementById(me.id);
									if(me.up('window').editstate=='view'){
										Ext.each(div.querySelectorAll('a'),function(a){
											a.setAttribute('onclick','return false');
										})
									}
									var fid = me.up('window').down('textfield[name=fid]').getValue();
									Ext.each(div.querySelectorAll('a'),function(a){
											a.setAttribute('href',"javascript:DJ.order.logistics.CarList.uploadOrdeleteImg('"+fid+"','"+me.id+"')");
										})
								}
							}
					}]},{
						xtype: 'container',
						layout:'vbox',
						items:[{
							name : 'fname',
							xtype:'textfield',
							fieldLabel: '车型 '
						},{
							name : 'fcarnumber',
							xtype:'textfield',
							fieldLabel: '车号 '
						},{
							name : 'fcarage',
							xtype:'textfield',
							fieldLabel: '车龄 '
						}]
					},{
						xtype: 'container',
						layout:'vbox',
						items:[{
							name : 'fcarKg',
							xtype:'textfield',
							labelWidth : 70,
							fieldLabel: '最大载重量 '
						},{
							xtype: 'container',
							layout:'hbox',
							items:[{
								labelWidth : 70,
								xtype:'displayfield',
								fieldLabel: '车身长度 ',
								margin:'5 0 5 20'
							},{
								name : 'fcarlength',
								xtype:'numberfield',
								width:70,
								margin:'5 0 5 0',
								emptyText :'长'
							},{
								xtype:'displayfield',
								value:'X',
								margin:'5 0 5 0'
							},{
								name : 'fcarwidth',
								xtype:'numberfield',
								width:70,
								margin:'5 0 5 0',
								emptyText :'宽'
							},{
								xtype:'displayfield',
								value:'X',
								margin:'5 0 5 0'
							},{
								name : 'fcarheight',
								xtype:'numberfield',
								width:70,
								margin:'5 0 5 0',
								emptyText :'高'
							}]
						},{
							name : 'fseats',
							xtype:'textfield',
							labelWidth : 70,
							fieldLabel: '乘坐人数 '
						}]
					}]
				},{
					name : 'fdescription',
					xtype : 'textareafield',
					margin:'5 10 5 20',
					labelWidth : 55,
					width:690,
					fieldLabel: '备注 '
				}]
			
	}]
})