<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
 <link rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxdata.js"></script> 
    <script type="text/javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js"></script>
    <script type="text/javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js"></script>
<title>送货凭证编辑界面</title>
<style type="text/css">
*{
	padding:0px;
	margin:0px;
}
.titleButton a{
	float:left;
	margin-left:10px;
	margin-top:5px;
	width:80px;
	height:20px;
	 border: 1px solid #999;
    cursor: pointer;
    background-color: #fff;
    color: #545454;
    text-align:center;
    font-family: "宋体";
}
.titleButton{
	border-bottom:1px solid lightgray;
}
.titleButton img{
    margin-left:5px;
}
.titleButton span{
	cursor: pointer;
	margin-left:15px;
}
.titleButton a:hover{
	background-color: red;
	color:#fff;
}
.template{
	/*float:left;*/
	margin:0 auto;/*2016年3月24日15:47:11 HT*/
   overflow: auto;
   overflow-x: hidden;
}
li{list-style:none;}
#menu a{color:#333;text-decoration:none;}
#menu a:hover{color:#008000;}
#class-list{width:200px;margin:100px;padding:10px;border:1px solid #DDD;border-radius:5px;}
#class-list li{text-indent:10px;margin-bottom:1px;}
#class-list li a{display:block;}
#class-list li a:hover,#class-list li.an a,#menu li a:hover{background:#F4F4F4;color:#008000;}
#menu{width:50px;display:none;position:absolute;top:0;left:0;padding:5px;z-index:100;}
#menu ul{background:#A7E4CB;border-radius:5px;box-shadow:0 1px 3px rgba(0, 0, 0, 0.2)}
#menu ul li{line-height:30px;border-bottom:1px solid #F2F2F2;text-align:center;}
#menu ul li a{display:block;}
#menu ul li a:hover{background:red;cursor: pointer;}
.delete{ display:inline-block; width:16px; height:16px; background:#ff0000 center; border:0px; border-radius:50%; color:#ffffff; line-height:16px; text-align:center; font-size:14px; outline:none; cursor:pointer; padding:0;}
</style>
<script type="text/javascript">
var $_input,rightRow;
$_input = $('<input>').css({
	border: 'none',
	background: 'transparent',
	textAlign: 'center',
	outline: 'none',
	width: '100%',
	height: '100%'
});
function deleteData(dom,type)
{
	/* var value=$(dom).parent().text();
 	if(!$.trim(value)) return; */
 	layer.msg("aaaa");
	//var loadIndex = layer.load(3);
	/* $.ajax({
		type:"post",
		url:'${ctx}/saledeliver/deleteFtuRelation.net',
		data:{'ftype':type,'fname':value},
		dataType:'json',
		success:function(response){
			layer.close(loadIndex);
			var obj = response;
			if(obj.success==true){
				
			}else
			{
				parent.layer.alert('删除失败！', function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			layer.close(loadIndex); 
			parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
		}
	}); */
}
function _text(dom){
	return $.trim($(dom).text());
}
function isEmpty(text){
	if(typeof text === 'object'){
		return $.isEmptyObject(text);
	}else{
		text = String(text);
		return text==null || text==='';
	}
}
function resetInput(){
	var pCell = $_input.pCell;
	if(pCell){
		pCell = $(pCell);
		pCell.empty().text($_input.val());
		$_input.val('').pCell = null;
		pCell.trigger('compute');
	}
}
function getFocus(cell){
	if(cell == $_input.pCell){	//input已经在cell中，则不做处理
		$_input.focus();
		return;
	}
	cell = $(cell);
	resetInput();
	var text = _text(cell);
	cell.empty().append($_input);
	$_input.val(text).pCell = cell[0];
	$_input.focus();
	$_input.off('blur');
	$_input.on('blur',resetInput); //input被移除后，事件需要重新添加
	$_input.off('keypress');
	$_input.on('keypress',inputKeypress);
}

function querySelector(dom,s){
	if($.browser.msie){
		return $(dom).find(s)[0];
	}else{
		return dom.querySelector(s);
	}
}
function querySelectorAll(dom,s){
	if($.browser.msie){
		return $(dom).find(s).get();
	}else{
		return dom.querySelectorAll(s);
	}
}
$(document).ready(function(){
	document.onmouseup=function(){
		//在表格点击任意一处，隐藏右击菜单
		$('#menu').hide();
	};
	$.ajax({
		type:"post",
		url:'${ctx}/saledeliver/getCfgByFkey.net',
		data:{'key':'thePrintTP'},
		dataType:'json',
		success:function(response){
			var obj = response;
			if(obj.success==true){
				if(obj.data=='2'){//2等分
					req(2);
				}else if(obj.data=='3'){//3等分
					req(3);
				}else{
					parent.layer.style(parent.layer.index,{
						display:'hidden'
					});
					parent.layer.msg('请先设置打印模板！');
					
					//2016年3月28日14:23:58 HT 用户第一次登录后新增送货凭证界面还原Start
					var p = [];
					p.push({'fname':'产品名称','fieldtype':0,'fsaledeliverentry':'fcusproductname'},{'fname':'规格','fieldtype':0,'fsaledeliverentry':'fspec'},{'fname':'单位','fieldtype':0,'fsaledeliverentry':'funit'},{'fname':'数量','fieldtype':1,'fdecimals':1,'fsaledeliverentry':'famount'},{'fname':'单价','fieldtype':1,'fdecimals':3,'fsaledeliverentry':'fdanjia'},{'fname':'金额','fieldtype':1,'fdecimals':2,'fcomputationalformula':'数量*单价','fsaledeliverentry':'fprice'},{'fname':'备注','fieldtype':0,'fsaledeliverentry':'fdescription'});
					$.ajax({
						type:'post',
						url:"${ctx}/saledeliver/saveAllFtuParams.net",
						dataType:'json',
						data:{ftu:JSON.stringify(p)},
						success:function(response){
			
						}
					});
					//2016年3月28日14:23:58 HT 用户第一次登录后新增送货凭证界面还原End
					
					var win = parent.$('#iframepage')[0].contentWindow;
					win.printTemplate();
				}
			}
		}
	});
});
//为界面赋HTML
function req(param){
	$.ajax({
		url:'${ctx}/saledeliver/getFtuPrintTemplate.net',
		data:{key:param},
		dataType:'html',
		success:function(response){
			$('.template').append(response);
			if(param==2){
				parent.layer.style(parent.layer.index,{
					width:'810px',
					height:'590px'
				});
			}
			if('${fstate}'!='print'){
				setEdit();
			}
			contextmenu();
			setValueBystate();
			//addEditableEvent();//2016年3月23日14:40:25 HT注释掉了这句
		}
	});
}
function inputKeypress(e){
	var cell = $_input.pCell;
	var d = cell.numberParam;
	var textContent = this.value;
	if(cell.hasVerifyTD){  //..1
		var keycode = e.which;
		var realkey = String.fromCharCode(keycode);
		 if(!Number(realkey)&&keycode!=48&&keycode!=46&&keycode!=8){
		 	return false;
		 }else{//小数位数控制
		 	if(keycode==8||keycode==13){//火狐删除
		 		return true;
		 	}
		 	if(textContent){
		 		if(keycode==46){
		 			if(d.fdecimals==0){
		 				return false;
		 			}
		 		}
		 		if(textContent.split('.')[1]){
		 			if(textContent.split('.')[1].trim().length>=d.fdecimals){
		 				 if(document.getSelection){//非IE浏览器,在.前面还能输入数字
			                var selectText  = document.getSelection();
			                if(selectText.focusOffset<=textContent.indexOf('.')){
			                	return true;
			                }
			            }
		 				return false;
		 			}
		 		}
		 	}
		 }
	}
}
//td添加可编辑事件
function addEditableEvent(tr){
	var tds;
	if(tr){ //对新增行添加
		tds = $(tr).find('td[contenteditable=true]');
	}else{
		tds = $('#printTemplate table td[contenteditable=true]').not('[name=fcustAddress]');
		$('#printTemplate table td[name=fcustAddress]').attr("contenteditable","false");//2016年4月13日10:49:07 最开始加载进来的时候送货地址不可编辑
	}
	tds.each(function(){
		if($(this).find('input').length==0){
			$(this).mouseup(function(){	// ie浏览器获取焦点，添加input
			    getFocus(this);
		    }).focus(function(){ // 非ie浏览器获取焦点
		    	getFocus(this);
		   });
		}
	 });
}

function setValueBystate(){
	if('${fstate}'=='add'||'${fstate}'=='edit'||'${fstate}'=='copy'){
		var com = ['fcustomername','fcustAddress','fcusproductname','funit','fclerk','fdriver'];
		var tables = document.body.querySelector('table');
		combobox({//客户下拉框
			parent:querySelector(tables.rows[1],'td:nth-child(2)'),
			url:'${ctx}/saledeliver/selectFtuCustomer.net',
			displayMember: "fname",
	        valueMember: "fid",
	        datafields:[{ name: 'fid' },{ name: 'fname' },{name:'ftel'}],
	       	autoOpen:false,
	       	dropDownHeight : 200,//2016年3月23日16:00:40 HT
	     	renderSelectedItem: function(index, item)
		     {
		         if (item != null) {
		        	 var customerrecord = this.source.records[index];
		        	 $(this.element).nextAll("td[name=fphone]").text(customerrecord["ftel"]);
		             var label = item.fname;
		             var addreeEl=querySelector(tables.rows[1],'td:nth-child(6)');
		             combobox({//选择客户后 为送货地址赋下拉框
		     			parent:addreeEl,//地址下拉框
		     			url:'${ctx}/saledeliver/getFTUCustAddress.net',
		     			autoOpen:false,
		     			isSelected:'${fstate}'!='add'&&$(addreeEl).jqxComboBox("source")==null?false:true,
		     			data:{
		     	            fcustomer: $('table td input[name=fcustomername]').val()
		     	        }
		     		});
		             var table = querySelector(document.body,'table table[contenteditable=false]');
		         	$.each(table.rows,function(indexs,row){
		        		$.each(row.cells,function(index,cell){
		        			if(indexs==0){
		        				if(cell.getAttribute('name')=='fcusproductname'){
		        					cusindex = index;
		        				}
		        			}
		        			if(indexs>0&&!cell.querySelector('table')){
		        				if(index==cusindex){
		        					$(cell).css('border','1px solid #999');
		        					combobox({
		        						parent:cell,
		        						url:'${ctx}/saledeliver/selectProductsByCustomers.net',
		        						displayMember:'fviewname',
		        						datafields:[{ name: 'fid' },{ name: 'fname' },{name:'fdanjia'},{name:'fspec'},{name:'funit'},{name:'fviewname'}],
		        						data: {
		        		     	            fcustomer: $('table td input[name=fcustomername]').val()
		        		     	        },
		        		     	       autoOpen:false,
		        		     	       renderSelectedItem: function(index, item)
		        		                {
		        		                    if (item != null) {
		        		                        var label = item.label;
		        		                        if(label){
		        		                        	var record = this.source.records[index];
		        		                        	var nextCell = $(cell).next(),i=0,name="";
		        		                        	while(nextCell.length>0){
		        		                        		i++;
		        		                        		name = $(table.rows[0].cells[i]).attr('name');
		        		                        		if(nextCell.find('input[type=textarea],input[type=text]').length>0){
		        		                        			nextCell.find('input[type=textarea],input[type=text]').val(record[name]);
		        		                        			nextCell.find('input[type=hidden]').val(record[name]);
		        		                        		}else{
		        		                        			$(nextCell).text(record[name]);
		        		                        		}
		        		                        		/*产品名称改变时若单价改变后，金额自动计算  by ht 2016年5月25日14:37:31*/
		        		                        		if(name=='fdanjia'){
		        		                        			nextCell.focus().blur();
		        		                        		}
		        		                        		/*产品名称改变时若单价改变后，金额自动计算  by ht 2016年5月25日14:37:31*/
		        		                        		nextCell = nextCell.next();
		        		                        	}
			        		                        //return label.split('/')[0].trim();
			        		                        return record["fname"].trim();
		        		                        }
		        		                        return label;
		        		                    }
		        		                    return "";   
		        		                }
		        					});
		        				}
		        			}
		        		});
		         	});
		             return label;
		         }
		         return "";   
		     }
		});
		combobox({//司机下拉框
			parent:querySelector(tables.rows[tables.rows.length-1],'td:nth-child(6)'),
			url:'${ctx}/saledeliver/selectFtuDriver.net',
			displayMember: "fname",
	        valueMember: "fname",
	        autoOpen:false,
	        isSelected:true,
	        renderer: function (index, label, value) {
	        	return !value?"":(label+'<input type="button" value="X" class="delete" onmousedown="deleteData(this,1);"/>');
	        } 
		}); 
		combobox({
			parent:querySelector(tables.rows[tables.rows.length-1],'td:nth-child(2)'),
			url:'${ctx}/saledeliver/selectFtuClerk.net',
			displayMember: "fname",
	        valueMember: "fname",
	        isSelected:true,
	        autoOpen:false,
	        renderer: function (index, label, value) {
	        	//$(this.element.innerHTML).find("span").after('<input type="button" value="X" class="delete" onclick="deleteData(this,2);"/>');
	        	//return label;
	        	return !value?"":(label+'<input type="button" value="X" class="delete" onclick="deleteData(this,2);"/>');
	        }
		});
		addtableCom();//添加内部combobox
		verifyTD();//校验数字列格式
		autoCount();//自动计算公式
	}
	//赋初值 制造商 编号 ID
	function setDefaultValue(){
		$.ajax({
			type:'post',
			url:'${ctx}/saledeliver/getFtuConcat.net',
			dataType:'json',
			success:function(response){
				var obj = response;
				if(obj.success==true){
					var html = document.getElementById('printTemplate');
					table = html.querySelector('table');
					html.querySelector('div').innerHTML = obj.data[0].fsuppliername;
					querySelector(html,'td:nth-child(2)').innerHTML = obj.data[0].fphone;
					querySelector(html,'td:nth-child(4)').innerHTML = obj.data[0].ffax;
					querySelector(html,'td:nth-child(5)').innerHTML = 'No.'+obj.data[0].fnumber;
					var d  = new Date();
					querySelector(table.rows[table.rows.length-1],'td:nth-child(4)').innerHTML = d.getFullYear()+"-"+eval(d.getMonth()+1)+"-"+d.getDate();
				}else{
					layer.alert(obj.msg+"<br/>联系电话：85391777");
				}
			}
		});
	}
	if('${fstate}'=='add'){	
		setDefaultValue();
		addEditableEvent();	
	}else if('${fstate}'=='view'||'${fstate}'=='edit'||'${fstate}'=='copy'){//查看,编辑，复制
		var layerIndex = layer.load(3);
		$.ajax({
			type:'post',
			url:'${ctx}/saledeliver/getFtusaledelivers.net',
			data:{fid:'${fid}'},
			dataType:'json',
			success:function(response){
				var obj = response;
				if(obj.success==true){
					if(obj.data){
						document.getElementById('printTemplate').querySelector('div').innerHTML = obj.data[0].fsuppliername;
						querySelector(document.getElementById('printTemplate'),'div:nth-child(2)').innerHTML = obj.data[0].fid;
						var table = querySelector(document.body,'table table[contenteditable=false]');
						var tables = document.body.querySelector('table');
						$.each(tables.rows,function(index,row){//为编辑界面赋数据
							if(!row.querySelector('table')){//外面表格赋值
								$.each(row.cells,function(indexs,cell){
									if(obj.data[0]){
											if(!cell.querySelector('input[type=textarea],input[type=text]')){
												if($(cell).attr('name')){
													cell.innerHTML = obj.data[0][tables.rows[index].cells[indexs].getAttribute('name')];
												}
											}else{
												$(cell).find('input[type=textarea],input[type=text]').attr('value',obj.data[0][tables.rows[index].cells[indexs].getAttribute('name')]);
												if(tables.rows[index].cells[indexs].getAttribute('name')=='fcustomername'){//客户特殊处理
													//先给下拉框添加数据
													var selectitem=$(cell).jqxComboBox('getItemByValue',obj.data[0].fcustomer);
													 if(!selectitem)
													{
														$(cell).jqxComboBox('source').records.splice(0, 0,{fname:obj.data[0][tables.rows[index].cells[indexs].getAttribute('name')],fid:obj.data[0].fcustomer});
														$(cell).jqxComboBox('insertAt',{label: obj.data[0][tables.rows[index].cells[indexs].getAttribute('name')],value: obj.data[0].fcustomer},0);
														selectitem=$(cell).jqxComboBox('getItem',0); 
													} 
													//选中下拉框值
													$(cell).jqxComboBox('selectItem',selectitem);//$(cell).jqxComboBox('selectItem',obj.data[0].fcustomer);
													//$(cell).find('input[type=hidden]').val(obj.data[0].fcustomer);

													}
													
											}
										}
								});
							}
						});
						if('${fstate}'=='copy'){
							$('#printTemplate div[name=fid]').text('');
							setDefaultValue();//复制功能重新赋值
						}
						for(var j =table.rows.length-2;j< obj.data.length;j++){//查看、修改数据超出模板行时，自动新增行
							var length= table.rows.length;
						   var tr=document.createElement("tr");
						   tr.setAttribute('height',table.rows[0].cells[0].height);
						   tr.setAttribute('align','center');
						   tr.id=length+1;
						   for(var i=0;i<table.rows[0].cells.length;i++){
						   		var td = document.createElement("td");
								tr.appendChild(td);
						   }
						   $.each(tr.cells,function(index,cell){
							   	if('${fstate}'=='view'){
									cell.setAttribute('style','white-space:nowrap;overflow:hidden;border: 1px solid;');
							   	}else{
							   		cell.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
							   	}
								cell.setAttribute('contenteditable','true');
						   });
						   function insertAfter(newEl, targetEl)
							{
							      var parentEl = targetEl.parentNode;
							
							      if(parentEl.lastChild == targetEl)
							      {
							           parentEl.appendChild(newEl);
							      }else
							      {
							           parentEl.insertBefore(newEl,targetEl.nextSibling);
							      }            
							}
						   insertAfter(tr,table.rows[0]);
						   if('${fstate}'!='view'){
							   	contextmenu();//新增行右击事件
							   	addtableCom(tr);//添加内部combobox
								verifyTD();//校验数字列格式
								autoCount();//自动计算公式
							}
						}
						
						if('${fstate}'=='view'){//查看界面不能编辑数据
							$.each(table.rows,function(i,r){
								r.oncontextmenu = function(){return false;};//查看状态不能新增行
							});
							$.each(querySelectorAll(document.body,'table td[contenteditable=true]'),function(index,td){
								td.setAttribute('style','white-space:nowrap;overflow:hidden;');
							});
							var temp = $('.titleButton span');
							temp.eq(0).hide();
							temp.eq(1).hide();
							temp.eq(3).show();
							$.each(querySelectorAll(document.body,'td[contenteditable=true],tr[contenteditable=true],div[contenteditable=true]'),function(i,td){
								td.setAttribute('contenteditable',false);
							});
						}
						$.each(querySelector(document.body,'table table[contenteditable=false]').rows,function(index,row){//内表格编辑界面赋数据
							if(index==0){
								$(row).css('background-color','#f0f0f0');
							}
							if(index>0){
								$.each(row.cells,function(indexs,cell){
									if(obj.data[index-1]){
											if(!cell.querySelector('input[type=textarea],input[type=text]')){
												cell.innerHTML = obj.data[index-1][querySelector(document.body,'table table[contenteditable=false]').rows[0].cells[indexs].getAttribute('name')];
											}else{
												$(cell).find('input[type=textarea],input[type=text]').attr('value',obj.data[index-1][querySelector(document.body,'table table[contenteditable=false]').rows[0].cells[indexs].getAttribute('name')]);
											}
										}
								});
								if(row.querySelector('table')){
									$(row).css('background-color','#f0f0f0');
									var sumFamount = 0,sumFprice = 0;
									for(var i =0;i<obj.data.length;i++){
										sumFamount += eval(isEmpty(obj.data[i].famount)?0:obj.data[i].famount);
										sumFprice += eval(isEmpty(obj.data[i].fprice)?0:obj.data[i].fprice);
									}
									if(index==querySelector(document.body,'table table[contenteditable=false]').rows.length-1){
										row.querySelector('tr').cells[0].innerHTML = "合计金额(大写):"+toRMB(Math.round(sumFprice * 100) / 100);
										row.querySelector('tr').cells[1].innerHTML = "&nbsp;总数量:"+(Math.round(sumFamount * 1000) / 1000==0?'':Math.round(sumFamount * 1000) / 1000);
										row.querySelector('tr').cells[2].innerHTML = "&nbsp;&yen;:"+(Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100);
									}
								}		
							}
						});
					
					}
				}
				/*2016年3月23日14:39:58 HT*/
				addEditableEvent();															
				/*2016年3月23日14:39:58 HT*/
				layer.close(layerIndex);
			}
		});
	}else if('${fstate}'=='print'){
		parent.layer.style(parent.layer.index,{
			display:'none'
		});
			function onload(){
				var me = this;
				var data = parent.window.data;
	  			var dom,temp;
	  			var printTemplate =document.body.querySelector('#printTemplate');
				for(var i =0;i<data.length;i++){
					if(i==0){
						continue;
					}
					dom = $.clone(printTemplate);
					dom.setAttribute('id',dom.getAttribute('id')+i);
					printTemplate.parentNode.appendChild(dom);
				}
				for(i =0;i<data.length;i++){
					var div = document.body.querySelector('#printTemplate'.concat(i==0?'':i));
					div.querySelector('div').innerHTML = data[i].fsuppliername;
					$.each(querySelector(div,'table table').rows,function(index,row){//表格内容
						if(index>0){
							$.each(row.cells,function(indexs,cell){
								if(data[i].product[index-1]){
									if(!cell.querySelector('table')){
										cell.innerHTML = data[i].product[index-1][querySelector(div,'table table').rows[0].cells[indexs].getAttribute('name')];
									}
								}
								if(cell.querySelector('table')){
									$.each(cell.querySelector('table').rows,function(y,row){
										$.each(row.cells,function(ii,cell){
											if(cell.getAttribute('name')){
												temp = _text(cell);
												cell.innerHTML = temp.replace(temp,temp+data[i][cell.getAttribute('name')]);
											}
										});
									});
								}
							});
						}
					});
					$.each(div.querySelector('table').rows,function(index,row){//表格标题
						$.each(row.cells,function(indexs,cell){
							if(!cell.querySelector('table')){
								if(cell.getAttribute('name')){
									cell.innerHTML = isEmpty(data[i][cell.getAttribute('name')])?'':data[i][cell.getAttribute('name')];
								}
							}
						});
					});
				}
			}
			onload();
			$('.titleButton a').eq(0).click();
			parent.layer.closeAll();
	}
}
function addtableCom(tr){//表格内部批量添加下拉框
	var table = querySelector(document.body,'table table[contenteditable=false]'),unitindex;
	$.each(table.rows,function(indexs,row){
		$.each(row.cells,function(index,cell){
			if(indexs==0){
				if(cell.getAttribute('name')=='fcusproductname'){
					cusindex = index;
				}
				if(cell.getAttribute('name')=='funit'){
					unitindex = index;
				}
				$(row).css('background-color','#f0f0f0');
			}
			if(tr){
				if(tr==row){
					if(index==cusindex){
						$(cell).css('border','1px solid #999');
						combobox({
							parent:cell,
							url:'${ctx}/saledeliver/selectProductsByCustomers.net',
							datafields:[{ name: 'fid' },{ name: 'fname' },{name:'fdanjia'},{name:'fspec'},{name:'funit'},{name:'fviewname'}],
							displayMember: "fviewname",
							data: {
			     	            fcustomer: $('table td input[name=fcustomername]').val()
			     	        },
			     	        autoOpen:false,
			     	       renderSelectedItem: function(index, item)
	   		                {
	   		                    if (item != null) {
	   		                        var label = item.label;
	   		                        if(label){
	   		                        	var record = this.source.records[index];
	   		                        	var nextCell = $(cell).next(),i=0,name="";
	   		                        	while(nextCell.length>0){
	   		                        		i++;
	   		                        		name = $(table.rows[0].cells[i]).attr('name');
	   		                        		if(nextCell.find('input[type=textarea],input[type=text]').length>0){
	   		                        			nextCell.find('input[type=textarea],input[type=text]').val(record[name]);
	   		                        			nextCell.find('input[type=hidden]').val(record[name]);
	   		                        		}else{
	   		                        			$(nextCell).text(record[name]);
	   		                        		}
	   		                        		nextCell = nextCell.next();
	   		                        	}
	       		                        //return label.split('/')[0].trim();
	   		                        	return record["fname"].trim();
	   		                        }
	   		                        return label;
	   		                    }
	   		                    return "";   
	   		                }
						});
					}
					if(index==unitindex){
						$(cell).css('border','1px solid #999');
						combobox({
							parent:cell,
							displayMember: "fname",
					        valueMember: "fname",
							url:'${ctx}/saledeliver/getFTUunit.net',
							height:eval($(cell.parentNode).height()-2.5),
							autoOpen:false
						});
					}
				}
			}else if(indexs>0&&!cell.querySelector('table')){
				if(index==cusindex){
					$(cell).css('border','1px solid #999');
					combobox({
						parent:cell,
						url:'${ctx}/saledeliver/selectProductsByCustomers.net',
						datafields:[{ name: 'fid' },{ name: 'fname' },{name:'fdanjia'},{name:'fspec'},{name:'funit'},{name:'fviewname'}],
						displayMember: "fviewname",
						data: {
		     	            fcustomer: $('table td input[name=fcustomername]').val()
		     	        },
		     	       autoOpen:false
					});
				}
				if(index==unitindex){
					$(cell).css('border','1px solid #999');
					combobox({
						parent:cell,
						displayMember: "fname",
				        valueMember: "fname",
						url:'${ctx}/saledeliver/getFTUunit.net',
						height:eval($(cell.parentNode).height()-2.5),
						autoOpen:false
					});
				}
			}else if(cell.querySelector('table')){
				$(row).css('background-color','#f0f0f0');
			}
		});
	});
}
//关闭页面
function closeFrom(){
	parent.layer.closeAll();
}
//表格新增、删除行，回车下一步
function contextmenu(){
	var tables = document.body.querySelector('table');
	var table = querySelector(document.body,'table table[contenteditable=false]');
	$.each(tables.rows,function(index,row){
		$.each(row.cells,function(indexs,cell){
			if(cell.querySelector('table[contenteditable=false]')){
				return true;
			}
			if(cell.getAttribute('contenteditable')){
				cell.onkeydown = function(e){//回车换行
				e = e || window.event;
				var keycode = e.which||e.keyCode;
				 e.target= e.srcElement ?  e.srcElement:e.target ;
				if(keycode==13||keycode==9){//兼容了IE8下的tab键  回车键   2016-4-13 09:56:35 by HT
					if(e.target.tagName=="INPUT"){
						var m = e.target;
						while(m.parentNode){
							m = m.parentNode;
							if(m.getAttribute('contenteditable')=="true"){
								  /* if(m.querySelector("input.jqx-combobox-input")){//产品选择后赋值,下拉框值选中
									 	var index = $(cell).jqxComboBox("getSelectedIndex");
										$(cell).jqxComboBox("close");
										 if(index>-1){
											$(cell).jqxComboBox("selectIndex",index);
										}   
									}  */
								var nextnode=m.nextSibling;
								while(true){
									if(nextnode!=null){
									if(nextnode.getAttribute('contenteditable')=="true"){
										if(nextnode.querySelector("input[type=text],input[type=textarea]"))
										{
											resetInput();
											//nextnode.querySelector("input[type=text],input[type=textarea]").focus();
											$(nextnode).jqxComboBox("focus");

										}else
										{
											getFocus(nextnode);
											if($.browser.msie){setTimeout(function(){getFocus(nextnode);},100);}
										}
										break;
									}
										nextnode = nextnode.nextSibling;
									}else{
										var tr = m.parentNode.nextSibling;
										if(tr)
										{
											var t = m.parentNode.nextSibling.querySelector('table');
											if(t){
												if(t.rows[1].cells[0].querySelector('input[type=text],input[type=textarea]')){
													resetInput();
													// t.rows[1].cells[0].querySelector('input[type=text],input[type=textarea]').focus();
													$(t.rows[1].cells[0]).jqxComboBox("focus");
												}else{
													getFocus(t.rows[1].cells[0]);
												}
												break;
											}
											if(tr.firstChild){nextnode=tr.firstChild;}
										}
										if(tr==null){
										layer.confirm('是否保存？',{
											btn: ['是','否'] //按钮
										},function(index){
											$('.titleButton span').eq(0).click();
											layer.close(index);
										},function(index){
											
										});
										break;
										}
									}
								}
								break;
							}
						}
					}else{
						console.log('不执行的方法..');
						if(e.target.nextSibling){
							/* if(!e.target.nextSibling.querySelector('input[type=text],input[type=textarea]')){
								var nextnode = e.target;
								while(nextnode){
									nextnode = nextnode.nextSibling;
									if(nextnode==null){
										if(e.target.parentNode.nextSibling){
											if(e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text],input[type=textarea]')){
												e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text],input[type=textarea]').focus();
												break;
											}else{
												var nextTr = e.target.parentNode.nextSibling.cells[0];
												while(nextTr){
													if(nextTr.getAttribute('contenteditable')=="true"){
														if(nextTr.querySelector('input[type=text],input[type=textarea]')){
															nextTr.querySelector('input[type=text],input[type=textarea]').focus();
														}else{
															nextTr.focus();
														}
														break;
													}
													nextTr = nextTr.nextSibling;
												}
												
												break;
											}
										}
									}
									if(nextnode.getAttribute('contenteditable')=="true"){
										if(nextnode.querySelector('input[type=text],input[type=textarea]')){
											nextnode.querySelector('input[type=text],input[type=textarea]').focus();
										}else{
											nextnode.focus();
										}
										break;
									}
								}
							} */
						}else{
							if(e.target.parentNode){
								querySelectorAll(e.target.parentNode.nextSibling,'table tr')[1].cells[0].focus();
							}
						}
					}
					return false;
				}
			};
			}
		});
	});
	$.each(table.rows,function(index,row){
		$.each(row.cells,function(indexs,cell){
			cell.onkeydown = function(e){//回车换行
				e = e || window.event;
				var keycode = e.which||e.keyCode;
				 e.target= e.srcElement ?  e.srcElement:e.target ;//IE兼容
				if(keycode==13){//回车键
					if(e.target.tagName=="INPUT"){
						var m = e.target;
						while(m.parentNode){
							m = m.parentNode;
							if(m.getAttribute('contenteditable')=="true"){
								 if(m.querySelector("input.jqx-combobox-input")){//产品选择后赋值,下拉框值选中
								 	var index = $(cell).jqxComboBox("getSelectedIndex");
									$(cell).jqxComboBox("close");
									if(index>-1){
										$(cell).jqxComboBox("selectIndex",index);
									}  
								} 
								if(m.nextSibling==null)
								{
									var tr=m.parentNode.nextSibling;
									if(tr)
									{
										if(tr.cells[0].getAttribute('contenteditable')=="true"){
											m=tr.cells[0];
										}else
										{
											m=tables.rows[tables.rows.length-1].cells[1];
										}
									}
								}else
								{
									m=m.nextSibling;
								}
							if(m!=null){
								var t = m.querySelector('input[type=text],input[type=textarea]');
								if(t){
									resetInput();
									//m.querySelector('input[type=text],input[type=textarea]').focus();
									$(m).jqxComboBox("focus");//IE兼容

									break;
								}else{
									getFocus(m);
									if($.browser.msie){setTimeout(function(){getFocus(m);},100);}
									break;
								}
								}
							}
						}
					/* }else{
						console.log('不执行的方法..');
						if(e.target.nextSibling){
							if(!e.target.nextSibling.querySelector('input[type=text],input[type=textarea]')){
								e.target.nextSibling.focus();
							}else{
								e.target.nextSibling.querySelector('input[type=text],input[type=textarea]').focus();
							}
						}else{
							if(e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text],input[type=textarea]')){
								e.target.parentNode.nextSibling.cells[0].querySelector('input[type=text],input[type=textarea]').focus();
							}else{
								var nexttd = e.target.parentNode.nextSibling;
								if(nexttd.cells[0].getAttribute('contenteditable')=="true"){
									nexttd.cells[0].focus();
								}else{
									if(tables.rows[tables.rows.length-1].cells[1].querySelector('input[type=text],input[type=textarea]')){
										tables.rows[tables.rows.length-1].cells[1].querySelector('input[type=text],input[type=textarea]').focus();
									}else{
										tables.rows[tables.rows.length-1].cells[1].focus();
									}
								}
								
							}
						} */
					}
					return false;
				}
			};
		});
		/* $(row).on('contextmenu',function(e){//右击菜单，新增行，删除行
			if(index==table.rows.length-1||index==0){
				return false;
			}
			rightRow = $(e.target).parents('tr')[0];//全局变量记录右击行
			$("#menu").css({"left":e.clientX,"top":e.clientY}).fadeIn();
			return false;//取消默认右击菜单 e.x,e.y
		}); */
	});
	$(table).delegate('tr','contextmenu',function(e){
		var me = $(this);
		if(me.parents('table')[0]==table && this!=table.rows[0] && this!=table.rows[table.rows.length-1]){
			rightRow = this;//全局变量记录右击行
			$("#menu").css({"left":e.clientX,"top":e.clientY}).fadeIn();
			return false;//取消默认右击菜单 e.x,e.y
		}
	});
}
function setEdit(){
	$('#printTemplate').css('overflow','');
	//$('#printTemplate').css('height',eval($('#printTemplate').height()+22));2016年3月23日15:11:13 HT注释这句
	$('#printTemplate').parent().css('width',eval($('#printTemplate').width()+15));
	//$('#printTemplate').attr('style','font-family:黑体;height:95mm; width:210mm;overflow: auto;');//去掉部分样式
	//$('#printTemplate').attr('style','height:90mm;');//2016年3月23日15:11:57 HT
	$('#printTemplate').css('height',eval($('#printTemplate').height()+20));//2016年3月24日16:43:33 HT 
	$.each(querySelectorAll(document.body,'span.resizeRightDivClass,span.resizeLeftDivClass'),function(i,r){
		r.setAttribute('style','display:none');
	});
	$.each(querySelector(document.body,'table table').rows,function(index,row){
		$.each(row.cells,function(i,cell){
			if(index>0){
				cell.setAttribute('style',"white-space:nowrap;overflow:hidden;");
				if(index>0&&index<querySelector(document.body,'table table').rows.length-1){
					cell.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
					cell.setAttribute('contenteditable',true);
				}
			}
		});
	});
	$.each(querySelectorAll(document.body,'table table table td[contenteditable=true]'),function(i,e){
		e.setAttribute('contenteditable',false);
	});
	$.each(document.body.querySelectorAll('div[contenteditable=true]'),function(i,div){
		div.setAttribute('contenteditable',false);
	});
	$.each(document.body.querySelector('table').rows,function(indexs,row){//编辑不能编辑
		$.each(row.cells,function(index,cell){
			if(cell.getAttribute('contenteditable')){
				cell.setAttribute('contenteditable',false);
			}else{
				if(index%2 !=0){
					cell.setAttribute('contenteditable',true);
				}
			}
		});
	});
	$.each(querySelectorAll(document.body,'table td[contenteditable=true]'),function(index,td){
		td.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
	});
}
/**新增行*/
function addRecord(me){
	   var table = querySelector(document.body,'table table[contenteditable=false]');
	   var length= table.rows.length;
	   var tr=document.createElement("tr");
	   tr.setAttribute('height',table.rows[0].cells[0].height);
	   tr.setAttribute('align','center');
	   tr.id=length+1;
	   for(var i=0;i<table.rows[0].cells.length;i++){
	   		var td = document.createElement("td");
			tr.appendChild(td);
	   }
	   $.each(tr.cells,function(index,cell){
//			cell.setAttribute('style',"white-space:nowrap;overflow:hidden;");
			cell.setAttribute('style','white-space:nowrap;overflow:hidden;background: #ffffff;border: 1px solid;');
			cell.setAttribute('contenteditable','true');
	   });
	   function insertAfter(newEl, targetEl)
		{
		      var parentEl = targetEl.parentNode;
		
		      if(parentEl.lastChild == targetEl)
		      {
		           parentEl.appendChild(newEl);
		      }else
		      {
		           parentEl.insertBefore(newEl,targetEl.nextSibling);
		      }            
		}
	   insertAfter(tr,rightRow);
	   contextmenu();//新增行右击事件
	   addtableCom(tr);//添加内部combobox
		verifyTD();//校验数字列格式
		autoCount();//自动计算公式
		addEditableEvent(tr);
}
/**删除行*/
function delRecord(me){
	var table = querySelector(document.body,'table table[contenteditable=false]');
	var i = "";
	$.each(table.rows,function(index,row){
		if(row==rightRow){
			i = index;
		}
	});
	table.deleteRow(i);
	 contextmenu();

}
function toRMB(Num) 
{  Num = Num + "";
      Num.replace(/[,\s]/g,'');
      Num = Num.replace("￥","");//替换掉可能出现的￥字符 
        if(isNaN(Num))    
        { 
     //验证输入的字符是否为数字 
     alert("请检查小写金额是否正确"); 
     return; 
        } 
        //---字符处理完毕，开始转换，转换采用前后两部分分别转换---// 
        part = String(Num).split("."); 
        newchar = "";    
        //小数点前进行转化 
        for(i=part[0].length-1;i>=0;i--) 
        { 
         if(part[0].length > 10){ alert("位数过大，无法计算");return "";}//若数量超过拾亿单位，提示 
     tmpnewchar = "" ;
     perchar = part[0].charAt(i); 
     switch(perchar){ 
     case "0": tmpnewchar="零" + tmpnewchar ;break; 
     case "1": tmpnewchar="壹" + tmpnewchar ;break; 
     case "2": tmpnewchar="贰" + tmpnewchar ;break; 
     case "3": tmpnewchar="叁" + tmpnewchar ;break; 
     case "4": tmpnewchar="肆" + tmpnewchar ;break; 
     case "5": tmpnewchar="伍" + tmpnewchar ;break; 
     case "6": tmpnewchar="陆" + tmpnewchar ;break; 
     case "7": tmpnewchar="柒" + tmpnewchar ;break; 
     case "8": tmpnewchar="捌" + tmpnewchar ;break; 
     case "9": tmpnewchar="玖" + tmpnewchar ;break; 
         } 
         switch(part[0].length-i-1) 
    { 
     case 0: tmpnewchar = tmpnewchar +"元" ;break; 
     case 1: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break; 
     case 2: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break; 
     case 3: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break;    
     case 4: tmpnewchar= tmpnewchar +"万" ;break; 
     case 5: if(perchar!=0)tmpnewchar= tmpnewchar +"拾" ;break; 
     case 6: if(perchar!=0)tmpnewchar= tmpnewchar +"佰" ;break; 
     case 7: if(perchar!=0)tmpnewchar= tmpnewchar +"仟" ;break; 
     case 8: tmpnewchar= tmpnewchar +"亿" ;break; 
     case 9: tmpnewchar= tmpnewchar +"拾" ;break; 
         } 
         newchar = tmpnewchar + newchar; 
        } 
        //小数点之后进行转化 
        if(Num.indexOf(".")!=-1) 
        { 
         if(part[1].length > 2) 
         { 
        alert("小数点之后只能保留两位,系统将自动截段"); 
        part[1] = part[1].substr(0,2);
     } 
         for(i=0;i<part[1].length;i++) 
         { 
        tmpnewchar = "";
        perchar = part[1].charAt(i);
        switch(perchar){ 
        case "0": tmpnewchar="零" + tmpnewchar ;break; 
        case "1": tmpnewchar="壹" + tmpnewchar ;break; 
        case "2": tmpnewchar="贰" + tmpnewchar ;break; 
        case "3": tmpnewchar="叁" + tmpnewchar ;break; 
        case "4": tmpnewchar="肆" + tmpnewchar ;break; 
        case "5": tmpnewchar="伍" + tmpnewchar ;break; 
        case "6": tmpnewchar="陆" + tmpnewchar ;break; 
        case "7": tmpnewchar="柒" + tmpnewchar ;break; 
        case "8": tmpnewchar="捌" + tmpnewchar ;break; 
        case "9": tmpnewchar="玖" + tmpnewchar ;break; 
     } 
     if(i==0)tmpnewchar =tmpnewchar + "角"; 
     if(i==1)tmpnewchar = tmpnewchar + "分"; 
     newchar = newchar + tmpnewchar; 
         } 
        } 
        //替换所有无用汉字 
        while(newchar.search("零零") != -1) 
            newchar = newchar.replace("零零", "零"); 
        newchar = newchar.replace("零亿", "亿"); 
        newchar = newchar.replace("亿万", "亿"); 
        newchar = newchar.replace("零万", "万");    
        newchar = newchar.replace("零元", "元"); 
        newchar = newchar.replace("零角", ""); 
        newchar = newchar.replace("零分", ""); 
    
        if (newchar.charAt(newchar.length-1) == "元" || newchar.charAt(newchar.length-1) == "角") 
         newchar = newchar+"整";
        return newchar; 
}
//批量打印
function print(){
	var html = document.body.querySelector('#printTemplate').parentNode;
	 $.each(html.querySelectorAll('.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),function(i,c){
	 	c.style.display='none';
	 });
	 var i = 0;
	 $.each(html.querySelector('table table').rows,function(y,row){
	 	$.each(row.cells,function(ii,cell){
	 		if(cell.style.display == 'none'){
	 			i++;
	 		}
	 	});
	 	if(row.querySelector('table')){//打印时隐藏不需要打印的列，里面table要改变colspan
	 		if(i>=html.querySelector('table table').rows.length-1){
	 			row.querySelector('td').setAttribute('colspan',row.querySelector('td').getAttribute('colspan')-eval((i/(html.querySelector('table table').rows.length-1))));
	 		}
	 	}
	 });
	parent.$('#panelPrint')[0].contentWindow.document.body.innerHTML ="<style>@media print {@page {margin: 2mm 10mm 0mm 10mm;}} table{table-layout:fixed;}</style>" + html.innerHTML;//this.up('window').body.dom.innerHTML);
	parent.$('#panelPrint')[0].contentWindow.focus();
	parent.$('#panelPrint')[0].contentWindow.print();
	$.each(html.querySelectorAll('.resizeLeftDivClass,.resizeRightDivClass,td[noprint=true]'),function(y,c){
	 	c.style.display='';
	 });
	 $.each(html.querySelector('table table').rows,function(y,row){
	 	$.each(row.cells,function(y,cell){
	 		if(cell.style.display == 'none'){
	 			i++;
	 		}
	 	});
	 	if(row.querySelector('table')){//还原回来
	 		if(i>=html.querySelector('table table').rows.length-1){
	 			row.querySelector('td').setAttribute('colspan',eval(row.querySelector('td').getAttribute('colspan'))+eval((i/(html.querySelector('table table').rows.length-1))));
	 		}
	 	}
	 });
}
//jqxcombobox下拉框配置
function combobox(config){
	var config = $.extend(
		//1.......
		{
		url:'',
		parent:"#jqxcombobox",
		datafields:[{ name: 'fid' },{ name: 'fname' }],
		displayMember: "fname",
        valueMember: "fid",
        isSelected:false,
        width:'',
        height:'',
        dropDownHeight : 150,//2016年3月23日15:07:38 HT
        autoOpen:true,
        data:{
            username: "jqwidgets"
        },
        renderer: function (index, label, value) {
            var item = dataAdapter.records[index];
            if (item != null) {
           	 if(item.fname){
           		 var label = "";
           		 if(config.displayMember.split(',').length>1){
           			 var name = config.displayMember.split(',');
	            		 for(var i =0;i<name.length;i++){
	            			 label += item[name[i]];
	            			 if(!$.isEmptyObject(item[name[i+1]])){
	            				 label +="/";
	            			 }
	            		 };
           		 }
           	 }
           	 if(label==="")  label = !item[config.displayMember]?"":item[config.displayMember];
           	 return label;
            }
            return "";
        },
        //2.......
        renderSelectedItem: function(index, item)
        {
            var item = dataAdapter.records[index];
            if (item != null) {
                var label = item.name;
                return label;
            }
            return "";   
        },
        //3.......
        dataAdapter:{
            formatData: function (data) {
                if ($(config.parent).jqxComboBox('searchString') != undefined) {
                    data.fname = $(config.parent).jqxComboBox('searchString');
                    return data;
                }
            },
            //数据加载完成
            loadComplete:function(record)
            {
            	if(record.data&&record.data.length==1&&config.isSelected){
            		$(config.parent).jqxComboBox('selectIndex', 0 ); 
            	}
            }
        }
	},config);
	 var source =
     {
         datatype: "json",
         datafields:config.datafields ,
         url: config.url,
         data: config.data
     };

     var dataAdapter = new $.jqx.dataAdapter(source,
         config.dataAdapter
     );
     
   	 dataAdapter.dataBind();
   	$(config.parent).on('bindingComplete',function(e){
  	 $(config.parent).jqxComboBox({autoDropDownHeight:$(config.parent).jqxComboBox("source").records.length>$(config.parent).jqxComboBox("dropDownHeight")/25?false:true});//2016年3月26日10:47:51 HT
   		 $(e.target).keyup(function(event){
   			 if(event.keyCode==40){//在下拉框中按'下键'展开下拉框
   				 $(config.parent).jqxComboBox('open');
   			 }
   		 });
   	 });
   	$(config.parent).on('change',function(e){
		if($.isEmptyObject($(e.target).val())){
			dataAdapter.dataBind();
		}
   	});
     $(config.parent).jqxComboBox(
     {
         width: config.width||config.parent.clientWidth,
         height: config.height||config.parent.clientHeight,
         source: dataAdapter,
         autoOpen : config.autoOpen,
         minLength : 0,
         keyboardSelection : false,
         remoteAutoComplete: true,
         autoDropDownHeight: false,  //2016年3月23日15:47:09 HT  TRUE改成false
         dropDownHeight :config.dropDownHeight,//2016年3月23日15:47:09 HT
         displayMember: config.displayMember,
         valueMember: config.valueMember,
         renderer:config.renderer,
         renderSelectedItem: config.renderSelectedItem,
         search: function (searchString) {
        	 dataAdapter.dataBind();
         }
     });
}
//自动计算金额
function autoCount(){
	$.ajax({
		url:'${ctx}/saledeliver/getFtuFcomputationalformula.net',
		type:'post',
		dataType:'json',
		success:function(response){
			var obj = response;
			var object = new Object();
			var temp;
			if(obj.success==true){
				$.each(obj.data,function(l,dd){
					if(dd.fcomputationalformula){//获取计算金额公式
						var fcomputationalformula = dd.fcomputationalformula;//obj.data[0].fcomputationalformula;
						if(fcomputationalformula){
							var fprice = fcomputationalformula.replace(/[\+\-\/Xx()（）]/g,'*').split("*");
						}
						var table = querySelector(document.body,'table table[contenteditable=false]');//产品表格
						$.each(table.rows,function(index,row){
							$.each(row.cells,function(indexs,cell){
								if(index==0){
									for(var i = 0;i<fprice.length;i++){
										if(_text(cell)==fprice[i].trim()){
											object[fprice[i]] = indexs;
										}
										if(cell.getAttribute('name')==dd.fsaledeliverentry){
											object[dd.fsaledeliverentry] = indexs;
											cell.setAttribute('fcomputationalformula',fcomputationalformula);
										}
										if(cell.getAttribute('name')=='famount'){
											object.famount = indexs;
										}
										if(cell.getAttribute('name')=='fprice'){
											object.fpindex = indexs;
										}
									}
								}
								if(index>0){
									if(cell.computeEvent){
										return;
									}
									if(object.fpindex ==indexs){
										function onblur(e){
											if(!Number(_text(cell))){
												cell.innerHTML = '';
											}else{
												var sumFprice = 0;
												$.each(table.rows,function(rindex,row){
													$.each(row.cells,function(cindexs,cell){
														if(rindex>0){
															if(object.fpindex ==cindexs){
																sumFprice +=eval(isEmpty(_text(cell))?0:_text(cell));
															}
														}
													});
												});
												table.rows[table.rows.length-1].querySelector('tr').cells[2].innerHTML = _text(table.rows[table.rows.length-1].querySelector('tr').cells[2]).split(':')[0]+":"+(Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100);
												table.rows[table.rows.length-1].querySelector('tr').cells[0].innerHTML = _text(table.rows[table.rows.length-1].querySelector('tr').cells[0]).split(':')[0]+":"+toRMB((Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100));
											}
											return;
										}
										temp = $(cell);
										temp.unbind('compute');
										temp.bind('compute',onblur);
										cell.computeEvent = true;
									}
									if(fcomputationalformula.indexOf(_text(table.rows[0].cells[indexs]))>-1){
										function blur(e){//失去焦点时，计算金额
											if(!Number(_text(cell))){
												cell.innerHTML = '';
												return;
											}
											var fprices = table.rows[0].cells[object[obj.data[l].fsaledeliverentry]].getAttribute('fcomputationalformula');//dd.fcomputationalformula;
											fprice = fprices.replace(/[\+\-\/Xx()（）]/g,'*').split("*");
											var sumFamount = 0,sumFprice = 0,temp;
											try{
												for(var i =0;i<fprice.length;i++){
													if(object[fprice[i]]){
														temp = _text(e.target.parentNode.cells[object[fprice[i]]]);
														fprices = fprices.replace(fprice[i],isEmpty(temp)?fprice[i]:temp);
													}
												}
												var value= Math.round(eval(fprices.replace(/[\Xx]/g,'*').replace(/[\（]/g,'(').replace(/[\）]/g,')')) * 1000) / 1000;
												e.target.parentNode.cells[object[dd.fsaledeliverentry]].innerHTML = value.toFixed(dd.fdecimals);//按照设置，取结果小数位数
												
												$.each(table.rows,function(indexs,row){
													if(indexs>0){
														$.each(row.cells,function(index,cell){
															if(index==object.famount){
																sumFamount +=eval(isEmpty(_text(cell))?0:_text(cell));
															}
															if(index==object.fprice){//计算总金额
																sumFprice +=eval(isEmpty(_text(cell))?0:_text(cell));
															}
														});
													}
												});
												table.rows[table.rows.length-1].querySelector('tr').cells[1].innerHTML = _text(table.rows[table.rows.length-1].querySelector('tr').cells[1]).split(':')[0]+":"+(Math.round(sumFamount * 1000) / 1000==0?'':Math.round(sumFamount * 1000) / 1000);
												table.rows[table.rows.length-1].querySelector('tr').cells[2].innerHTML = "&nbsp;&yen;:"+(Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100);
												table.rows[table.rows.length-1].querySelector('tr').cells[0].innerHTML = _text(table.rows[table.rows.length-1].querySelector('tr').cells[0]).split(':')[0]+":"+toRMB((Math.round(sumFprice * 100) / 100==0?'':Math.round(sumFprice * 100) / 100));
											}catch(ee){
												e.target.parentNode.cells[object.fprice].innerHTML = '';
												$.each(table.rows,function(indexs,row){
													if(indexs>0){
														$.each(row.cells,function(index,cell){
															if(index==object.famount){
																sumFamount +=eval(isEmpty(_text(cell))?0:_text(cell));
															}
														});
													}
												});
												table.rows[table.rows.length-1].querySelector('tr').cells[1].innerHTML = _text(table.rows[table.rows.length-1].querySelector('tr').cells[1]).split(':')[0]+":"+(sumFamount==0?'':sumFamount);
											}
										}
										temp = $(cell);
										temp.unbind('compute');
										temp.bind('compute',blur);
										cell.computeEvent = true;
									}
								}
							});
						});
					}
				});
			}
		}
});
}
//检验数字列
function verifyTD(){
	var table = querySelector(document.body,'table table[contenteditable=false]');
	$.ajax({
		type:'post',
		url:'${ctx}/saledeliver/getNumberParams.net',
		dataType:'json',
		success:function(response){
			var obj = response;
			if(obj.success==true){
					window.verifytd = obj.data;
					$.each(table.rows,function(indexs,row){
						$.each(row.cells,function(indexss,cell){
							if(indexs==0){
								$.each(obj.data,function(index,d){
									if(cell.getAttribute('name')==d.fsaledeliverentry){
										d[cell.getAttribute('name')]= indexss;	
									}
								});
							}else{
								$.each(obj.data,function(indexsss,d){
									if(d[d.fsaledeliverentry]==indexss && !cell.hasVerifyTD){
										cell.hasVerifyTD = true;
										cell.numberParam = d;
										//..hasVerifyTD
									}
								});
							}
						});
					});
			}
		}
	});
}
/**保存方法*/
function saveSaledeliver(state){
	var verifytd = window.verifytd;
	var newDate = [];
	var name = [];
	var bool = false,tds = '';
	var table = querySelector(document.body,'table table[contenteditable=false]');
	var temp;
	resetInput();
	$.each(table.rows,function(indexs,row){
		if(indexs==0){
			$.each(row.cells,function(index,cell){
				name.push(cell.getAttribute('name')); 
			});
		}
		if(indexs>0&&indexs<table.rows.length-1){
			var Proobject = new Object();
			$.each(row.cells,function(index,cell){
				if($.isEmptyObject(cell.querySelector('table'))){
					temp = _text(cell);
					if(!isEmpty(temp)){
						$.each(verifytd,function(i,td){
							if(td[td.fsaledeliverentry]==index){
								if(!Number(temp)&&temp!=0){
									tds = td.falias;
									bool = true;
								}
							}
						});
						Proobject[name[index]] = temp.replace(/\No./g,'');
					}else{
						var v = $(cell.querySelector('input[type=textarea],input[type=text]')).val();
						if(v){
							Proobject[name[index]] = isEmpty(v.trim())?'':v.trim();
						}
					}
				}
			});
			if(!$.isEmptyObject(Proobject)){
				newDate.push(Proobject);
			}
		}
	});
	if($.isEmptyObject(newDate)){
		//Ext.Msg.alert('提示','请填写产品信息！');
		layer.msg('请填写产品信息！');
		return false;
	}
	var FTUobject = new Object();//送货凭证头信息
	$.each(querySelectorAll(document.body,'table td.overflowHiddenClass,div:nth-child(2)'),function(indexs,td){
		if(!isEmpty(td.getAttribute('name'))){
			var v = td.querySelector('input[type=textarea],input[type=text]');//td.querySelector('input[type=hidden]'); 
			if(v){
				FTUobject[td.getAttribute('name')] = isEmpty($(v).val().trim())?$(v).val().trim():$(v).val().trim();
			}else{
				FTUobject[td.getAttribute('name')] = _text(td).replace(/\No./g,'');
			}
		}
	});
	if(isEmpty(FTUobject.fcustomername)){
		//Ext.Msg.alert('提示','请填写客户名称！');
		layer.msg('请填写客户名称！');
		return false;
	}
	if(bool){
		//Ext.Msg.alert('提示','<b>'+tds+"</b>只能输入数字！");
		layer.msg('<b>'+tds+"</b>只能输入数字！");
		return false;
	}
	$.ajax({
		type:'post',
		url:'${ctx}/saledeliver/saveOrUpdateFtusaledeliver.net',
		data:{FTUSaledeliverEntry:JSON.stringify(newDate),FTUSaledeliver:JSON.stringify(FTUobject)},
		dataType:'json',
		success:function(response){
			var obj = response;
			if(obj.success==true){
				layer.msg('保存成功');
				var win = parent.$('#iframepage')[0].contentWindow;
				parent.layer.style(parent.layer.index,{
					display:'none'
				});
				if(state=='print'){
					win.print(obj.msg);
				}else{
					parent.layer.closeAll();
				}
				win.getSendVoucherTable(win.$('#kkpager_btn_go_input').val()||1);
			}else{
				layer.msg(obj.msg);
			}
		}
	});
}
//保存并打印
function saveOrprintSaledeliver(){
	saveSaledeliver('print');
}
//查看界面转修改界面
function viewToEdit(){
	var win = parent.$('#iframepage')[0].contentWindow;
	win.dju_edit($('#printTemplate div[name=fid]').text());
}
//background-color: #DFE8F6;
</script>
</head>
<body style="overflow: hidden;">
<div id="menu">
       <ul>
           <li><a onclick="addRecord(this)">新增行</a></li>
        <li><a onclick="delRecord(this)" >删除行</a></li>
    </ul>
</div>
<div class="titleButton">
	<!-- <a onclick="saveSaledeliver()">保存</a>
	<a onclick="saveOrprintSaledeliver()">保存并打印</a>
	<a onclick="closeFrom()">关闭</a> -->
	<span onclick="saveSaledeliver()"><img src="${ctx}/css/images/save.png" style="vertical-align: sub;"/>保存</span>
	<span onclick="saveOrprintSaledeliver()"><img src="${ctx}/css/images/print.png" style="vertical-align: sub;">保存并打印</span>
	<span onclick="closeFrom()"><img src="${ctx}/css/images/close-hover.gif">关闭</span>
	<a onclick="print()" style="display:none;">打印</a>
	<span onclick="viewToEdit()" style="display:none;"><img src="${ctx}/css/images/editpic.png" style="vertical-align: middle;">修改</span>
	<!-- <a onclick="viewToEdit()" style="display:none;">修改</a> -->
</div>
<div class="template"></div>
</body>
</html>