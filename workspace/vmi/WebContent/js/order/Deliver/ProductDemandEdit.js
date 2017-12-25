Ext.define('DJ.order.Deliver.productdemandfile',{
//	id:'DJ.order.Deliver.Fistproductdemand',
	ctype:'Productdemandfile',
	extend : 'Ext.c.GridPanel',
	pageSize : 50,
	alias:'widget.productdemandfile',
	closable : false,// 是否现实关闭按钮,默认为false
	url:'',
	exporturl : "",// 导出为EXCEL方法
	height:200,
	selModel : Ext.create('Ext.selection.CheckboxModel'),
	onload:function(){
	
	},
	noLoad:true,
	fields:[{
			name:'fid'
		},{
			name:'fname'
		},{
			name:'fpath'
		},{
			name:'fparentid'
		}],
		columns:[{
			dataIndex:'fid',
			hidden:true,
			hideable:false
		},{
			text:'路径',
			dataIndex:'fpath',
			id:'DJ.order.Deliver.productdemandfile.fpath',
			hidden:true,
			hideable:false
		},{
			xtype:'templatecolumn',
			text:'附件名称',
			dataIndex:'fname',
			tpl : '<a href="downloadProductdemandFile.do?fid={fid}" target="_blank" style="text-decoration:none;">{fname}</a>',
			flex:1
		},{
			xtype:'actioncolumn',
			width:100,
//			hidden:true,
//			hideable:false,
			align:'center',
			text:'操作',
			id:'DJ.order.Deliver.productdemandfile.productActions',
        items: [
//                {
//            icon: 'images/image_add.png',  
//            tooltip: '上传附件',
//            handler: function(grid, rowIndex, colIndex) {
//            	var filename = grid.getStore().getAt(rowIndex).get('fname');
//            	if(filename!=null&&filename!=""){
//            		Ext.Msg.alert('提示','请先删除附件！');
//                	return ;
//            	}
//            	var fid = grid.getStore().getAt(rowIndex).get('fid');
//            	var id = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fid').getValue();
//            	Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fparentid').setValue(id);
//            	Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.state').setValue(fid);//上传唛稿分录附件
//            	document.getElementById('DJ.order.Deliver.ProductDemandEdit.file-button-fileInputEl').click();
//            }
//        },
		
        {
            icon: 'images/delete.gif',
            tooltip: '删除附件',
            handler: function(grid, rowIndex, colIndex) {
                var fid = grid.getStore().getAt(rowIndex).get('fid');
                var fpath = grid.getStore().getAt(rowIndex).get('fpath');
                var fparentid = grid.getStore().getAt(rowIndex).get('fparentid');
                if(grid.getStore().getAt(rowIndex).get('fname')==""){
                	Ext.Msg.alert('提示','此分录未上传附件！');
                	return ;
                }
                Ext.Ajax.request({
                	url:'delProductdemandfile.do',
                	params:{
                		fid:fid,
                		fpath:fpath,
                		fparentid:fparentid
//                		fname:fname
                		},
                	success:function(res){
                		var obj = Ext.decode(res.responseText);
                		if(obj.success){
                			djsuccessmsg(obj.msg);
                			Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore().load({params:{fparentid:fparentid}});
                		}else{
                			Ext.Msg.alert('提示',obj.msg);
                		}
                		//Ext.getCmp('DJ.System.MaigaoEntryGrids').getStore().reload();
                	},
                	failure:function(){
                		Ext.Msg.alert('错误','系统故障，请检查网络状况或联系管理员！');
                	}
                })
            }
        }]
		},{
			dataIndex:'fparentid',
			hidden:true,
			hideable:false
		}]
})

ltc = function(params){
		var xhr = new XMLHttpRequest();
		var index = 0;
		var files = document.getElementById('fileToUpload').files;
		var p;
		if(files.length==0){
			return;
		}
		var fileSize;
		 function uploadProgress(evt) {
		        if (evt.lengthComputable) {
		          var percentComplete = Math.round(evt.loaded * 100 / evt.total);
		        	p.updateProgress(percentComplete/100);
		        	if(percentComplete==100){
		        		var task = new Ext.util.DelayedTask(function(){
//		        			console.log(p);
			        		Ext.Msg.hide();
			        		var file = Ext.getCmp('DJ.order.Deliver.productdemandfile');
	        				file.store.load();
	        				if(index >= files.length) {
							    return;
							}
							upload();
		        		})
		        		task.delay(600);
		        		index++;
		        	}
		        	
		        }
		        else {
//		          document.getElementById('progressNumber').innerHTML = 'unable to compute';
		        }
	      }
	     
	      function uploadComplete(evt) {
	        /* 服务器端返回响应时候触发event事件*/
//	        alert(evt.target.responseText);
	      	var obj = evt.target.responseText;
	      	obj = eval("("+obj+")"); 
	      	if(!obj.success){
	      		Ext.Msg.alert('错误',obj.msg);
	      	}
	      }
	      function uploadFailed(evt) {
//	        alert("There was an error attempting to upload the file.");
	      }
	      function uploadCanceled(evt) {
//	        alert("The upload has been canceled by the user or the browser dropped the connection.");
	      }
	     function upload(){
     		p = Ext.MessageBox.show({  
					  title:'请等待',  
					  msg:'文件上传中……',  
					  width:240,  
					  progress:true,  
					  closable:false,
					  fn:function(){
					  	console.log(123);
					  }
			});  
	     	var fd = new FormData();
	     	 if (files[index].size > 1024 * 1024) {
                fileSize = (Math.round(files[index].size * 100 / (1024 * 1024)) / 100).toString();// + 'MB';
                if(fileSize>10){
                	Ext.Msg.alert('提示','上传文件不能超过10MB');
                	return;
                }
            } else {
                fileSize = (Math.round(files[index].size * 100 / 1024) / 100).toString() + 'KB';
            }
	        fd.append("fileToUpload", files[index]);
		    var task = new Ext.util.DelayedTask(function(){
		        xhr.upload.addEventListener("progress", uploadProgress, false);
		        xhr.addEventListener("load", uploadComplete, false);
		        xhr.addEventListener("error", uploadFailed, false);
		        xhr.addEventListener("abort", uploadCanceled, false);
		        xhr.open("POST", "uploadProductFile.do?parentid="+params);//修改成自己的接口
		        xhr.send(fd);
	        })
	       task.delay(600);
	     }
	   upload();
}


      
Ext.QuickTips.init();
Ext.define('DJ.order.Deliver.ProductDemandEdit',{
	extend : 'Ext.c.BaseEditUI',
	id:'DJ.order.Deliver.ProductDemandEdit',
	title : "包装需求编辑界面",
	resizable : false,
	width : 830,
	height : 600,
	ctype:'Firstproductdemand',
	bodyPadding:'20 15',
	autoScroll:true,
	resizable : false,
	closable : true,
	modal : true,
	url:'SaveOrupdateFirstproductdemand.do',
	viewurl:'getFirstproductdemandInfo.do',
	onload:function(){
		 	 Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.savebutton').setText("存草稿");
			var fdescription = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fdescription');
			fdescription.on('change',function(me,value,oldvalue){
				var fdescription = value.replace(/<br\/>/g,'\r\n');
				this.setValue(fdescription);
			},fdescription,{single:true});
			var fid = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fid');
			fid.on('change',function(){
				
				var me = Ext.getCmp('DJ.order.Deliver.productdemandfile');
				var store = me.getStore();
				store.setDefaultfilter([{
					myfilterfield : "fparentid",
					CompareType : " = ",
					type : "string",
					value :fid.getValue()
				}]);
				store.setDefaultmaskstring(" #0 ");
				me.noLoad=false;
				store.loadPage(1);
				//文件上传按钮的添加
				var btn = document.getElementById('DJ.order.Deliver.productdemandfile.addfile');
				var div = document.createElement('div');
				var frag = document.createDocumentFragment();
				var params = 'parentid='+this.getValue();//this.parentNode.submit()
				if(!Ext.isIE){
					div.innerHTML = '<form action="uploadProductFile.do?'+params+'" method="post" enctype="multipart/form-data" target="hideFileTarget"><input type="file" multiple="multiple" name="fileToUpload" id="fileToUpload" onchange="ltc(\''+this.getValue()+'\')" style="height:40px;outline:medium none;position:absolute;display:block;top:0;right:0;opacity:0;-moz-opacity:0;filter:alpha(opacity=0);cursor:pointer;" ></input></form><iframe id="ProductDemand.hideFileTarget" name="hideFileTarget" style="display:none;" />';
				}else{
					div.innerHTML = '<form action="uploadProductFile.do?'+params+'" method="post" enctype="multipart/form-data" target="hideFileTarget"><input type="file" multiple="multiple" name="fileToUpload" id="fileToUpload" onchange="this.parentNode.submit()" style="height:40px;outline:medium none;position:absolute;display:block;top:0;right:0;opacity:0;-moz-opacity:0;filter:alpha(opacity=0);cursor:pointer;" ></input></form><iframe id="ProductDemand.hideFileTarget" name="hideFileTarget" style="display:none;" />';
				}
				while(div.firstChild){
					frag.appendChild(div.firstChild);
				}
				btn.appendChild(frag);
				var iframe = document.getElementById('ProductDemand.hideFileTarget');
				 if (iframe.attachEvent){	//ie
                     iframe.attachEvent('onload',function(){
                    	 Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore().load();
                     })
	             }else{
	                 iframe.onload = function() { 
	                	 Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore().load(); 
	                } 
	             } 
			});
	
		var close = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.closebutton');
		close.addListener("click", function (com) {
			
			var panel = com.up("[id=DJ.order.Deliver.ProductDemandEdit]");
			
			if(panel.editstate=="add"){
				var fparentid = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fid').getValue();
//				var fpath = Ext.getCmp('DJ.order.Deliver.productdemandfile.fpath').getValue();
				Ext.Ajax.request({
						url:'getFileByfid.do',
						params:{
							fparentid:fparentid
							},
						success : function(response, option) {
							var obj = Ext.decode(response.responseText);
							if (obj.success == true) {
//								djsuccessmsg(obj.msg);
//								Ext.getCmp("DJ.order.Deliver.FistproductdemandList").store
//												.load();
								} else {
									Ext.MessageBox.alert('错误', obj.msg);

								}
								}
							})
			}
			
		})
		
	},
	custbar : [{
						text : '发布',
						height : 30,
						id : "DJ.order.Deliver.ProductDemandEdit.Audit",
						finishSubmit: function(form,edit){
							if(!form.findField("fsupplierid").getValue()){
								Ext.defer(function(){
									Ext.MessageBox.confirm('提示','制造商未指定，是否继续发布？',function(id){
										if(id=='yes'){
											edit.url="editauditorFproductdemand.do";
											edit.Action_Submit(edit);
										}
									})
								},50);
							}else{
								edit.url="editauditorFproductdemand.do";
								edit.Action_Submit(edit);
							}
						},
						handler : function() {
							var me = this;
							var edit=Ext.getCmp('DJ.order.Deliver.ProductDemandEdit');
							var  form=edit.getform().form,field;
							field = form.findField("fname");
							if(Ext.String.trim(field.getValue()).length==0)
							{
								field.markInvalid('请填写需求名称');
								Ext.MessageBox.alert('错误','需求未完成编辑。请填写需求名称。');
								return;
								
							}
							if(Ext.getCmp('DJ.order.Deliver.productdemandfile').getStore().getCount()==0&&Ext.String.trim(form.findField("fdescription").getValue()).length==0)
							{
								Ext.MessageBox.alert('错误',"需求未完成编辑。请上传附件或添加描述。");
								return;
							}
							field = form.findField("flinkphone");
							if(Ext.String.trim(field.getValue()).length==0){
								field.markInvalid('请填写联系电话');
								Ext.MessageBox.confirm('提示','填写联系电话的需求将优先处理，是否填写？',function(id){
									if(id=='no'){
										me.finishSubmit(form,edit);
									}
								});
							}else{
								me.finishSubmit(form,edit);
							}
							
						
						
							
	}},{
		text:'打印',
		hidden:true,
		handler:function(){
			Ext.getCmp(this.up('panel').parent).down('button[text=打印]').handler();
		}
	}],
	listeners:{

		show:function(){
			var me = this;
			var editstate = me.editstate;
			if(editstate === 'add'){
				Ext.Ajax.request({
					url: 'getUserContact.do',
					success: function(res){
						var obj = Ext.decode(res.responseText);
						if(obj.success){
							me.down('textfield[name=flinkphone]').setValue(obj.msg);
						}
					}
				});
			}
			if(editstate=="edit"||editstate=="view"){
				this.down('button[text=打印]').show();
				var record = Ext.getCmp(Ext.getCmp('DJ.order.Deliver.ProductDemandEdit').parent).getSelectionModel().getSelection();
				if (record[0].get('fiszhiyang')=='true'){ 
						Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.true').setValue(true); 
					}else{
						Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.false').setValue(true);
					}

				var grid = Ext.getCmp('DJ.order.Deliver.productdemandfile');
//				var myfilter = [];
//				myfilter.push({
//					myfilterfield : "fparentid",
//					CompareType : " = ",
//					type : "string",
//					value : record[0].get('fid')
//				});
//				var store = grid.getStore();
//				store.setDefaultfilter(myfilter);
//				store.setDefaultmaskstring(" #0 ");
//				store.load();
				grid.setDisabled(false);
			}
			if(editstate=="view"){
				Ext.getCmp('DJ.order.Deliver.productdemandfile.addfile').hide();
				Ext.getCmp('DJ.order.Deliver.productdemandfile.productActions').hide();
				Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.Audit').hide();
			}
		}
	},
	initComponent:function(){
		var win = this;
		var productdemandfile = Ext.widget({

			xtype:'productdemandfile',
			id:'DJ.order.Deliver.productdemandfile',
			url:'getProductdemandfileList.do',
		
			onload:function(){
				Ext.getCmp('DJ.order.Deliver.productdemandfile.addbutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.editbutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.viewbutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.delbutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.refreshbutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.querybutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.exportbutton').setVisible(false);
				Ext.getCmp('DJ.order.Deliver.productdemandfile.exportbutton').setVisible(false);
			 },
			custbar:[{
				text:'上传附件',
				height : 30,
				preventDefault:false,
				id:'DJ.order.Deliver.productdemandfile.addfile'
				/*
				handler : function(){
					
					var grid = Ext.getCmp('DJ.order.Deliver.productdemandfile');
	            	var id = Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fid').getValue();
	            	Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fparentid').setValue(id);
//	            	Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.state').setValue("0");//上传唛稿分录附件
	            	document.getElementById('DJ.order.Deliver.ProductDemandEdit.file-button-fileInputEl').click();
				}*/
				}]
			
		});
		productdemandfile.getStore().addListener('beforeload',function(){
			if(productdemandfile.noLoad){
				return false;
			}
		});
		Ext.apply(this,{
			items:[
			  {name:'isfauditor',xtype:'textfield',id:'DJ.order.Deliver.ProductDemandEdit.isfauditor',hidden:true},
			  {name:'fid',id:'DJ.order.Deliver.ProductDemandEdit.fid',xtype:'textfield',hidden:true},
			  {name:'fcreatid',xtype:'textfield',hidden:true},
			  {name:'fcreatetime',xtype:'textfield',hidden:true,value:'2014-05-27 00:00:00.000'},
			  {name:'fupdateuserid',xtype:'textfield',hidden:true},
			  {name:'fcustomerid',xtype:'textfield',hidden:true},
//			  {name:'fsupplierid',xtype:'textfield',hidden:true},
			  {name:'falloted',xtype:'textfield',hidden:true},
			  {name:'freceived',xtype:'textfield',hidden:true},
			  {name:'freceiver',xtype:'textfield',hidden:true},
			  {name:'fstate',xtype:'textfield',hidden:true,value:'存草稿'},
			  {name:'fisaccessory',id:'DJ.order.Deliver.ProductDemandEdit.fisaccessory',xtype:'textfield',hidden:true},
			  {name:'fpreproductdemandid',xtype:'textfield',hidden:true},
			  {
				layout:{
					type:'table',
					columns:2
				},
				defaultType:'textfield',
				baseCls:'x-plain',
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[{
					fieldLabel:'需求编号',id:'DJ.order.Deliver.ProductDemandEdit.fnumber',name:'fnumber',labelWidth:130,width:380
				},{
					fieldLabel:'需求名称',name:'fname',labelWidth:130,width:380,labelStyle:'padding-left:30px;'
				},{
					fieldLabel:'是否制样',xtype:'radiogroup',
					items:[{
						boxLabel:'是',
						id:'DJ.order.Deliver.ProductDemandEdit.true',
						name:'fiszhiyang',
						inputValue:'true'
					},{
						boxLabel:'否',
						id:'DJ.order.Deliver.ProductDemandEdit.false',
						name:'fiszhiyang',
						inputValue:'false',
						checked:true
					}],
					labelWidth:130,width:380,
					listeners:{
						change:function(newVal,oldVal,eOpts){
							if(oldVal.fiszhiyang=='true'){
								Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.foverdate').setValue(Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,3));
								Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.farrivetime').setValue(new Date(Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,4)));
							}else if(oldVal.fiszhiyang=='false'){
								Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.foverdate').setValue(Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,2));
								Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.farrivetime').setValue('');
							}
						}
					}
				
				},{
					fieldLabel:'订单数量',xtype:'numberfield',name:'famount',labelWidth:130,width:380,labelStyle:'padding-left:30px;'
				},    Ext.create('Ext.ux.form.DateTimeField', {
					fieldLabel : '方案入库日期',
					labelWidth:130,
					width:380,
					format:'Y-m-d',
//					renderer:ltc(),
//					value:new Date((new Date()/1000+259200)*1000),
					value:Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,2),
					name : 'foverdate',
					allowBlank : false,
					
					id:'DJ.order.Deliver.ProductDemandEdit.foverdate',
					blankText : '方案入库日期不能为空',
					onExpand : function() {
//						var a = this.getValue();
						this.picker.setValue( new Date(new Date().setHours(17,0,0,0)));
					}
				}),Ext.create('Ext.ux.form.DateTimeField', {
					fieldLabel : '发货日期',
					id:'DJ.order.Deliver.ProductDemandEdit.farrivetime',
					labelWidth:130,
					width:380,
					format:'Y-m-d',
					name : 'farrivetime',
					value:Ext.Date.add(new Date(new Date().setHours(17,0,0,0)),Ext.Date.DAY,2),
//					allowBlank : false,
//					blankText : '配送日期不能为空',
//					preventMark:true,
					labelStyle:'padding-left:30px;',
					onExpand : function() {
//						var a = this.getValue();
						this.picker.setValue( new Date(new Date().setHours(17,0,0,0)));
					}
				}),{
					fieldLabel:'联系人',name:'flinkman',labelWidth:130,width:380,id:'DJ.order.Deliver.ProductDemandEdit.flinkman'
					   	
				},{
					fieldLabel:'联系电话',name:'flinkphone',labelWidth:130,width:380,labelStyle:'padding-left:30px;',id:'DJ.order.Deliver.ProductDemandEdit.flinkphone'
				},{
					fieldLabel:'制造商名称',
					name:'fsupplierid',
					id:'DJ.order.Deliver.ProductDemandEdit.fsupplierid',
					labelWidth:130,
					width:380,
					xtype:'combobox',
					displayField:'fname',
					valueField:'fid',
					editable:false,
					store:Ext.create('Ext.data.Store',{
						fields: ['fid', 'fname'],
						proxy:{
							type:'ajax',
							url: 'getSupplierListOfCustomer.do',
					         reader: {
					             type: 'json',
					             root: 'data'
					         }
						},
						listeners:{
							load:function(me,records){
								if(records.length==1){
									Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fsupplierid').setValue(records[0].get('fid'));
								}
								// 2015-06-04 by lu  关联东经，默认东经
								else if(records.length>1 && win.editstate=='add'){
									var newArry = Ext.Array.filter(records,function(item,index,records){ 
										  if(item.get('fname')=='东经')
											  return true;
									  });
									if(newArry.length==1){
										Ext.getCmp('DJ.order.Deliver.ProductDemandEdit.fsupplierid').setValue(newArry[0].get('fid'));
									}
								}
							}
						}
				})
			}]
			},{
				xtype:'displayfield',
				value:'<b>附件分录</b>'
			},
			productdemandfile			   		
			,{
				xtype:'displayfield',
				value:'<br/><B>需求描述</B>:(当填写包装要求、特殊要求、现状问题，填写内容准确、具体，设计效率越高.)'
			},{
				layout:{
					type:'table',
					columns:2
				},
				baseCls:'x-plain',
				frame:true,
				defaults:{
					width:350,
					labelWidth:75,
					bodyStyle:'padding:20;'
				},
				items:[
				       {name:'fdescription',id:'DJ.order.Deliver.ProductDemandEdit.fdescription'
				    	   ,xtype:'textareafield',width:785,height:100}
				       ]
			}]
		});
		this.callParent(arguments);
				}
	
})
