Ext.define('DJ.System.permissionEdit',{
	extend:'Ext.form.Panel',
	id:'DJ.System.permissionEdit',
	title:'权限设置',
	bodyPadding:'20',
	closable:true,
//	autoScroll :true,
	listeners:{
		render:function(me){
			Ext.Ajax.request({
				url:'GetUserCusMainmenu.do',
				params:{},
				success:function(response){
					var obj = Ext.decode(response.responseText);
					if(obj.success==true){
						Ext.each(obj.menus,function(record){
							if(record.fischeck==1){
								var btn = document.getElementsByName(record.fid);
								if(btn.length>0){
									btn[0].click();
								}
							}
						})
						if(obj.register==0){
							var div = me.down('container displayfield[value*=可选权限]');
							div.up().hide();
							div.up().nextSibling().show();
						}
					}else{
						Ext.Msg.alert('提示',obj.msg);
						me.down("toolbar").hide();
						me.down('container displayfield[value*=已有权限]').up().hide();
						me.down('container displayfield[value*=可选权限]').up().hide();
						Ext.getCmp("DJ.System.permissionEdit.description").setValue('<h1>只有注册用户可使用</h1>');

					
					}
				}
			})
		},
		afterrender:function(m){
					Ext.each(document.getElementsByTagName('button'),function(btn){
						function nextSibling( elem ) {
							if(!Ext.isEmpty(elem.previousSibling)){//只有首个节点 才允许全选
								return null;
							}
							var r = [];
							var n = elem.parentNode.firstChild;
							for ( ; n; n = n.nextSibling ) {
								if ( n.nodeType === 1 && n !== elem ) {
									r.push( n );
								}
							}
							return r;
						}
						function sibling( elem ) {
							var r = [];
							var n = elem.parentNode.firstChild.nextSibling;
							if(!Ext.isEmpty(elem.previousSibling)){
								for ( ; n; n = n.nextSibling ) {
									if ( n.nodeType === 1) {
										r.push( n );
									}
								}
							}
							return r;
						}
						var div = btn.parentNode.parentNode;
						if(Ext.isIE){//IE兼容
							btn.attachEvent('onclick',function(me){
								var buttonName = [];
								var checked = '';
								Ext.each(nextSibling(div),function(ele){//点击首个div
									if(btn.nextSibling.checked){
										buttonName.push(ele.childNodes[0].childNodes[0].name);
										checked = false;
										ele.childNodes[0].childNodes[1].checked = false;
									}else{
										buttonName.push(ele.childNodes[0].childNodes[0].name);
										checked = true;
										ele.childNodes[0].childNodes[1].checked = true;
									}
								})
								if(btn.nextSibling.checked){
									btn.nextSibling.checked = false;
									if(!Ext.isEmpty(btn.name)){
										buttonName.push(btn.name);
										checked = false;
									}
								}else{
									btn.nextSibling.checked = true;
									if(!Ext.isEmpty(btn.name)){
										buttonName.push(btn.name);
										checked = true;
									}
								}
								var i = 0;
								Ext.each(sibling(div),function(ele,index,array){//点击子div选择父div
									if(ele.childNodes[0].childNodes[1].checked){
										i++;
									}
									if(array.length==i){
										ele.parentNode.firstChild.childNodes[0].childNodes[1].checked = true;
									}else{
										ele.parentNode.firstChild.childNodes[0].childNodes[1].checked = false;
									}
								})
								var val = btn.getAttribute('tip')==null?'':btn.getAttribute('tip');		
								if(me.offsetX>=0){
										Ext.getCmp('DJ.System.permissionEdit.description').setValue('<h1>'+val+'</h1>');
										Ext.Ajax.request({
											url:'SaveUserCusMenu.do',
											params:{'fid':buttonName.join(','),'fischeck':checked},
											success: function(response){
												var obj = Ext.decode(response.responseText);
												if(obj.success==true){
													djsuccessmsg(obj.msg);
												}else{
													Ext.Msg.alert('提示',obj.msg);
												}
											}
										})
									}	
							})
						}else{
							
							div.addEventListener('click',function(me,ltc){
								var buttonName = [];
								var checked = '';
								Ext.each(nextSibling(div),function(ele){//点击首个div
									if(div.querySelector('input').checked){
										buttonName.push(ele.querySelector('button').name);
										checked = false;
										ele.querySelector('input').checked = false;
									}else{
										buttonName.push(ele.querySelector('button').name);
										checked = true;
										ele.querySelector('input').checked = true;
									}
								})
								var val = btn.getAttribute('tip')==null?'':btn.getAttribute('tip');				
								if(btn.nextSibling.checked){
									btn.nextSibling.checked = false;
									if(!Ext.isEmpty(btn.name)){
										buttonName.push(btn.name);
										checked = false;
									}
								}else{
									btn.nextSibling.checked = true;
									if(!Ext.isEmpty(btn.name)){
										checked = true;
										buttonName.push(btn.name);
									}
								}
								
								var i = 0;
								Ext.each(sibling(div),function(ele,index,array){//点击子div选择父div
									if(ele.querySelector('input').checked){
										i++;
									}
									if(array.length==i){
										ele.parentNode.firstChild.querySelector('input').checked = true;
									}else{
										ele.parentNode.firstChild.querySelector('input').checked = false;;
									}
								})
								if(me.detail==1){
									Ext.getCmp('DJ.System.permissionEdit.description').setValue('<h1>'+val+'</h1>');
									Ext.Ajax.request({
										url:'SaveUserCusMenu.do',
										params:{'fid':buttonName.join(','),'fischeck':checked},
										success: function(response){
											var obj = Ext.decode(response.responseText);
											if(obj.success==true){
												djsuccessmsg(obj.msg);
											}else{
												Ext.Msg.alert('提示',obj.msg);
											}
										}
									})
								}
								
							})
						}
						
					})
//				})
		}
	},
//	initComponent:function(){
//		var me = this;
//		Ext.applyIf(me,{
//			items:Ext.create('Ext.view.View',{
//				store:Ext.create('Ext.data.Store', {
//				    fields:['fid','fname'],
//				    data: [
//				        { fid:'http://www.sencha.com/img/20110215-feat-drawing.png', fname:'用户中心' },
//				        { fid:'http://www.sencha.com/img/20110215-feat-data.png', fname:'纸箱供应' }
//				    ]
//				}),
//				tpl: new Ext.XTemplate(
//							    '<tpl for=".">',
//							    	'<div style="height:50px;position:relative">',
//							    			'<div style="float:left;">',
//										       '<div style="width:100px;height:40px;position:relative;">' ,
//											       '<button  style="width:100px;height:40px;">{fname}</button>' ,
//											       '<input  style="position:absolute;right:0;bottom:0;" type="checkbox" checked="true" value="" />' ,
//										       '</div>',
//									       '</div>',
//									       '<div style="float:left;margin:0 0 0 15px;">',
//									       	 	'<div style="width:100px;height:30px;position:relative;">',
//											       '<button  style="width:100px;height:30px;">我的资料</button>' +
//											       '<input style="position:absolute;right:0;bottom:0;" type="checkbox" checked="true" value=""/>' +
//										       '</div>',
//									       '</div>',
//							       '</div>',
//							    '</tpl>'
//							),
//					listeners:{
//						show:function(me){
//							console.log(document.getElementsByTagName('button'));
//							Ext.each(document.getElementsByTagName('button'),function(btn){
//								function sibling( elem ) {
//									if(!Ext.isEmpty(elem.previousSibling)){//只有首个节点 才允许全选
//										return null;
//									}
//									var r = [];
//									var n = elem.parentNode.firstChild;
//									for ( ; n; n = n.nextSibling ) {
//										if ( n.nodeType === 1 && n !== elem ) {
//											r.push( n );
//										}
//									}
//									return r;
//								}
//								var div = btn.parentNode.parentNode;
//								if(Ext.isIE){//IE兼容
//									btn.parentNode.attachEvent('onclick',function(me){
//										Ext.each(sibling(div),function(ele){
//											if(btn.nextSibling.checked){
//				//								ele.querySelectorAll('input').checked = false;
//												ele.childNodes[0].childNodes[1].checked = false;
//											}else{
//				//								ele.querySelectorAll('input').checked = true;
//												ele.childNodes[0].childNodes[1].checked = true;
//											}
//										})
//										if(btn.nextSibling.checked){
//											btn.nextSibling.checked = false;
//										}else{
//											btn.nextSibling.checked = true;
//										}
//									})
//								}else{
//									
//									div.addEventListener('click',function(me,ltc){
//										Ext.each(sibling(div),function(ele){
//											if(div.querySelector('input').checked){
//												ele.querySelector('input').checked = false;
//											}else{
//												ele.querySelector('input').checked = true;
//											}
//										})
//										if(btn.nextSibling.checked){
//											btn.nextSibling.checked = false;
//										}else{
//											btn.nextSibling.checked = true;
//										}
//									})
//								}
//								
//							})
//						}
//					}
//			})
//		})
//		this.callParent(arguments);
//	}

	//,
	layout:'hbox',
	items:[{
		layout:'vbox',
		border:false,
		items:[{
		xtype: 'container',
//		width:700,
		items: [{
			xtype:'displayfield',
			value:'<h1>已有权限</h1>'
		},{
			layout:'hbox',
			border:false,
			defaults: {
			    margin:'0 20 0 20'
			},
			items:[{    
				xtype: 'component',
			    html: '<div style="width:100px;height:40px;position:relative"><button style="width:100px;height:40px;border-width:2px;background:#BDBDBD;" disabled="disabled">用户中心</button><input style="position:absolute;right:0;bottom:0;" disabled="disabled" type="checkbox" checked="true" value="" /></div>'
			},{
				xtype: 'component',
			    html: '<div style="width:100px;height:30px;position:relative"><button name="btn1" style="width:100px;height:30px;" disabled="disabled">我的资料</button><input  disabled="disabled" style="position:absolute;right:0;bottom:0;" type="checkbox" checked="true" value="" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button name="btn1" style="width:100px;height:30px;" disabled="disabled">我的地址</button><input  disabled="disabled" style="position:absolute;right:0;bottom:0;" type="checkbox" checked="true" value="" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button name="btn1" style="width:100px;height:30px;" disabled="disabled">密码修改</button><input  disabled="disabled" style="position:absolute;right:0;bottom:0;" type="checkbox" checked="true" value="" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button name="btn1" style="width:100px;height:30px;" disabled="disabled">参数设置</button><input  disabled="disabled" style="position:absolute;right:0;bottom:0;" type="checkbox" checked="true" value="" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button name="btn1" style="width:100px;height:30px;" disabled="disabled">权限配置</button><input  disabled="disabled" style="position:absolute;right:0;bottom:0;" type="checkbox"  checked="true" value="" /></div>'
			}]
		},{
			layout:'hbox',
			border:false,
			defaults: {
			    margin:'20 20 0 20'
			},
			items:[{
				xtype:'component',
				html: '<div style="width:100px;height:40px;position:relative"><button style="width:100px;height:40px;border-width:2px;background:#BDBDBD;">纸箱供应</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value=""  disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="替代手写的出库凭证，形成历史记录。" name="c445c5dd-f316-11e4-bd7e-00ff6b42e1e5">送货凭证</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value=""  disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="维护客户的产品资料，形成电子档案。" name="75f8d45f-0f20-11e5-9395-00ff61c9f2e3">产品档案</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="管理客户资料，并邀请客户线上使用。" name="38961fcc-0dcf-11e5-9395-00ff61c9f2e3">客户管理</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value=""  disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="管理客户地址，快速有效寻找地址。" name="8a5953bf-14ff-11e5-be46-00ff61c9f2e3">地址管理</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled" /></div>'
			}]
		
		},{
			xtype: 'component',
			style: 'margin-top:10px;margin-bottom:10px;',
			html: '<div style="border-bottom: 1px solid black;padding-bottom:10px;"></div>'
		}]
	},{
		xtype: 'container',
//		width:700,
		items: [{
			xtype:'displayfield',
			value:'<h1>可选权限</h1>'
		},{
			layout:'hbox',
			border:false,
			defaults: {
			    margin:'0 20 0 20'
			},
			items:[{    
				xtype: 'component',
			    html: '<div style="width:100px;height:40px;position:relative"><button style="width:100px;height:40px;border-width:2px;background:#BDBDBD;">纸箱供应</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled"/></div>'
			},{
				xtype: 'component',
			    html: '<div style="width:100px;height:30px;position:relative"><button  style="width:100px;height:30px;" tip="制造商接收客户下达的纸箱订单。" name="8382894c-0d74-11e5-9395-00ff61c9f2e3">我的业务</button><input  style="position:absolute;right:0;bottom:0;" type="checkbox"  value=""  disabled="disabled"/></div>'
			}]
		},{
			layout:'hbox',
			border:false,
			defaults: {
			    margin:'20 20 0 20'
			},
			items:[{
				xtype:'component',
				html: '<div style="width:100px;height:40px;position:relative"><button style="width:100px;height:40px;border-width:2px;background:#BDBDBD;">纸板供应</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="向二级厂采购纸板。" name="048ae2d2-cbab-11e4-a8a2-00ff6b42e1e5">纸板订单</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" name="dffaef21-cbaa-11e4-a8a2-00ff6b42e1e5">我的发货</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled" /></div>'
			}]
		
		},{
			layout:'hbox',
			border:false,
			defaults: {
			    margin:'20 20 0 20'
			},
			items:[{
				xtype:'component',
				html: '<div style="width:100px;height:40px;position:relative"><button style="width:100px;height:40px;border-width:2px;background:#BDBDBD;">纸板采购</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="向二级厂采购纸板。" name="6755469e-cbaa-11e4-a8a2-00ff6b42e1e5">纸板订单</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="类似购物车，暂存订单，要货时提交。" name="ef9ee2a5-fe0e-11e4-9395-00ff61c9f2e3">暂存订单</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value=""  disabled="disabled"/></div>'
			}]
		},{
			layout:'hbox',
			border:false,
			defaults: {
			    margin:'20 20 0 20'
			},
			items:[{
				xtype:'component',
				html: '<div style="width:100px;height:40px;position:relative"><button style="width:100px;height:40px;border-width:2px;background:#BDBDBD;">纸箱采购</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="快速采购纸箱，给制造商下达采购订单。" name="1728ba31-0d2a-11e5-9395-00ff61c9f2e3">快速下单</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled" /></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="时时关注纸箱订单的状态。" name="2296fc76-1ace-11e5-be46-00ff61c9f2e3">订单监控</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value=""  disabled="disabled"/></div>'
			},{
				xtype:'component',
				html: '<div style="width:100px;height:30px;position:relative"><button style="width:100px;height:30px;" tip="纸板、纸箱的收货明细。" name="7725a04b-4034-11e3-ad63-60a44c5bbef3">我的收货</button><input style="position:absolute;right:0;bottom:0;" type="checkbox"  value="" disabled="disabled" /></div>'
			}]
		},{
			xtype: 'component',
			style: 'margin-top:10px;margin-bottom:10px;',
			html: '<div style="border-bottom: 1px solid black;padding-bottom:10px;"></div>'
		}]
	},{
		xtype: 'container',
		hidden:true,
		items: [{
			xtype:'displayfield',
			value:'<h1>可选权限</h1>'
		},{
			xtype: 'displayfield',
			margin:'20 20 0 20',
			value:'<h1>更多权限配置，请认证后在此选择。</h1>'
		}]
	}]
	},{
		xtype: 'container',
		items: [{
			margin:'100 20 0 20',
			labelWidth :40,
			xtype: 'displayfield',
			id:'DJ.System.permissionEdit.description',
			height:300,
			anchor    : '100%'
		}]
	}]
	,
	dockedItems: [{
        xtype: 'toolbar',
        dock: 'top',
//        style : {
//					background : 'white'
//		},
		items:[
		{
        	 xtype:'container',
        	 items:[{
        	 	xtype:'button',
        			height : 35,
					text : "<font size='2px'>立即应用</font>",
				handler : function() {
				window.location.reload();						
			}
        	 }]
        }]
    }]
})