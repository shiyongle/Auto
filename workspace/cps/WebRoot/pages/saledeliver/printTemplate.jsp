<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/jquery.jqprint-0.3.js" type="text/javascript"></script>
<title>打印模板设置</title>
<style type="text/css">
*{
	margin:0px;
	padding:0px;
}
.button a{
	float:left;
	width:80px;
	height:20px;
    border: 1px solid #999;
    cursor: pointer;
    background-color: #fff;
    color: #545454;
    text-align:center;
    margin:5px 10px 5px 5px;
    font-family: "宋体";
}
.button a:hover{
	background-color: red;
	color:#fff;
}
.resizeRightDivClass
{
position:relative;
width:2;
z-index:1;
float:right;
cursor:e-resize;
 -moz-user-select:none;/*火狐*/
  -webkit-user-select:none;/*webkit浏览器*/
  -ms-user-select:none;/*IE10*/
  -khtml-user-select:none;/*早期浏览器*/
  user-select:none;
}
.resizeLeftDivClass
{
position:relative;
width:2;
z-index:1;
float:left;
cursor:e-resize;
 -moz-user-select:none;/*火狐*/
  -webkit-user-select:none;/*webkit浏览器*/
  -ms-user-select:none;/*IE10*/
  -khtml-user-select:none;/*早期浏览器*/
  user-select:none;
}
.overflowHiddenClass{
	white-space:nowrap;
	overflow:hidden;
}
td table tr{
	height:24px;
}
ul,li{margin:0;padding:0;}
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
#columnParams{
	width:340px;
	font:17px 宋体;
	padding:5px;
	display:none;
}
#columnParams input[type=text]{
	width:145px;
	outline:none;
	height:22px;
	line-height:22px;
	padding-left:5px;
}
#columnParams input[type=button]{
	float:right;
	width:70px;
	height:23px;
}
.titleButton{
	border-bottom:1px solid lightgray;
}
.titleButton img{
    margin-left:5px;
}
.titleButton span{
	cursor: pointer;
}
</style>
<script type="text/javascript">
	$(document).ready(function(){
		var ltc = false;
		window.onmouseup=function(){
			//在表格点击任意一处，隐藏右击菜单
			$('#menu').hide();
			ltc = false;
			window.onmousemove = function(){};
		};
		$.post('${ctx}/saledeliver/getFtuPrintTemplate.net','',function(responses,type){//3联的
				var layerIndex = parent.layer.load(2);
				//添加HTML到页面上
				$('.template').append(responses);
				//赋默认值
				$.ajax({
					type:'post',
					url:'${ctx}/saledeliver/getFtuConcat.net',
					dataType:'json',
					success:function(response){
						var obj = response;
						if(obj.success==true){
							try {
								var html = document.getElementById('printTemplate');
								$(html).css('overflow','');
								html.querySelector('div').textContent = obj.data[0].fsuppliername;
								html.querySelector('td:nth-child(2)').textContent = obj.data[0].fphone;
								html.querySelector('td:nth-child(4)').textContent = obj.data[0].ffax;
								html.querySelector('td:nth-child(5)').textContent = 'No.'+obj.data[0].fnumber;
								var d = new Date();
								html.querySelector('table').rows[html.querySelector('table').rows.length-1].querySelector('td:nth-child(4)').textContent = d.getFullYear()+"-"+eval(d.getMonth()+1)+"-"+d.getDate();
							} catch (e) {
								// TODO: handle exception
								console.log(layerIndex);
								parent.layer.close(layerIndex);
							}
						}else{
							Ext.Msg.alert('提示',obj.msg);
						}
					}
				});
				dragging();
				rightClick();
				parent.layer.close(layerIndex);
			},'html'
		);
		//默认数字不能编辑
		$.each($('.num input'),function(i,e){
			e.disabled=true;
		});
	});
	//动态改变列宽度
	function dragging(){
		//$('#printTemplate').css('height',eval($('#printTemplate').height()+5));
		var span = document.body.querySelectorAll('span.resizeRightDivClass,span.resizeLeftDivClass');
		$.each(span,function(ii,r){//列移动改变宽度
			if(ii==span.length-1){//最后一个移动列多余了。
				$(r).remove();
			}
			r.onmousedown = function(e){
				ltc = true;
				var pareneTdW=e.target.parentElement.offsetWidth;
				if(r.className=="resizeLeftDivClass"){
					pareneTdW = e.target.parentElement.previousSibling.offsetWidth;
				}
				var table = e.target.parentNode.parentNode.parentNode.parentNode;//表格
				var i = "";
				 $.each(table.rows,function(y,row){
					 	$.each(row.cells,function(index,cell){
					 		if(e.target.parentElement==cell){
					 			i = index;
					 		}
					 	});
					 });
				 window.onmousemove = function(event){
					 var newWidth=pareneTdW*1+event.clientX*1-e.clientX;
					 $.each(table.rows,function(y,row){
					 	if($.isEmptyObject(i)){
					 		if(!row.querySelector('table'))
					 		if(r.className=="resizeLeftDivClass"){
					 			row.cells[i-1].setAttribute('width',newWidth+'px');
					 		}else{
					 			row.cells[i].setAttribute('width',newWidth+'px');
					 		}
				 		}else{
				 			console.log(i);
				 		}
					 });
				 };
	
			};
		});
	};
	//表格列标头右击事件
	function rightClick(){
		var table = document.body.querySelector('table table');
		table.oncontextmenu = function(e){//表格右击事件
			if(e.target.parentNode.parentNode.parentNode==table.querySelector('table')){
				if(e.target.parentNode.parentNode.parentNode.querySelectorAll('tr').length == 2){
					if(e.target.parentNode == e.target.parentNode.parentNode.parentNode.querySelectorAll('tr')[1]){
						return false;
					}else{
						return false;
					}
				}else{
					return false;
				}
			}
		if($.isEmptyObject(e.target.parentNode.previousSibling)){//空就是列标题右击事情，否则就是表格行事件
			$.each($('#menu li'),function(i,li){
				//初始化菜单全部显示
				if($(li).is(":hidden")){
					$(li).show();
				}
				//配置
				$(li).children().attr('parent',e.target.getAttribute('name'));
			});
			var names = ['fcusproductname','famount','fdanjia','fprice'];
				if(names.indexOf(e.target.getAttribute('name'))>-1){
					$('#menu li').eq(2).hide();
				} 
				$("#menu").css({"left":e.clientX,"top":e.clientY}).fadeIn();
				
				return false;//取消默认右击菜单 e.x,e.y
			}
	
	};
	};
	function cloAll(){
		parent.layer.closeAll();
	}
	
	/**新增列*/
	function addColumn(me){
		var table = document.getElementById('printTemplate').querySelector('table table');
		newCellname = ['string1','string2','string3','string4','string5','string6','string7','string8','string9','string10'];//全局变量
		$.each(table.rows[0].cells,function(index,cell){
			$.each(newCellname,function(i,name){
				if(cell.getAttribute('name')==name){
					//columns.push(cell.getAttribute('name'));
					newCellname.splice(i,1);
				}
			});
		});
		if($.isEmptyObject(newCellname)){
			parent.layer.msg('只能新增10列！');
			return false;
		}
		$("#columnParams form")[0].reset();
		$("#columnParams *[name='ftuParameter.fsaledeliverentry']").val(newCellname[0]);
		$("#columnParams *[name='ftuParameter.fname']").val(newCellname[0]);
		$('#columnParams input[type=button]').attr('parent',$(me).attr('parent'));
		$('#columnParams input[type=button]').attr('fstate','add');
		showColumnParamsWin();
	}
	/**修改列*/
	function editColumn(me){
		var name = $('td[name='+$(me).attr('parent')+']').text().trim();
		$.ajax({
			type:'post',
			url:'${ctx}/saledeliver/getFtuParameterList.net',
			data:{'falias':name},
			dataType:'json',
			success:function(response){
				var obj = response;
				if(obj.success==true){
					if(obj.data){
						showColumnParamsWin();
						$('#columnParams input[type=button]').attr('fstate','edit');
						$('#columnParams input[type=button]').attr('parent',$(me).attr('parent'));
						$("input[name='ftuParameter.fdecimals']").val(obj.data[0].fdecimals);
						$("input[name='ftuParameter.fieldtype']")[eval(obj.data[0].fieldtype)].checked=true;
						$("input[name='ftuParameter.fieldtype']")[eval(obj.data[0].fieldtype)].click();//出发单击事件
						$("input[name='ftuParameter.fsaledeliverentry']").val(obj.data[0].fsaledeliverentry);
						$("input[name='ftuParameter.fcomputationalformula']").val(obj.data[0].fcomputationalformula);
						$("input[name='ftuParameter.fisprint']").attr('checked',eval(obj.data[0].fisprint)==0?false:true);
						$("input[name='ftuParameter.falias']").val(obj.data[0].falias);
						$("input[name='ftuParameter.fname']").val(obj.data[0].falias);
						$("input[name=inserttype]").parent().hide();//修改时 插入列隐藏
						if("fcusproductname,funit,famount,fdanjia,fprice".indexOf($(me).attr('parent'))>-1){
							$.each($('#columnParams input[type=radio]'),function(i,ra){
								$("input[name='ftuParameter.fieldtype']:not(:checked)").hide();
							});
						}
						
					}
				}
			}
		});
	}
	/**删除列*/
	function delColumn(me){
		var table = document.getElementById('printTemplate').querySelector('table table');
		var name = $('td[name='+$(me).attr('parent')+']').text().trim();
		var i = "";
		$.ajax({
			type:'post',
			url:'${ctx}/saledeliver/delFtuParameter.net',
			data:{'name':name},
			dataType:'json',
			success:function(response){
				var obj = response;
				if(obj.success==true){
					$.each(table.rows,function(ii,row){
						$.each(row.cells,function(index,cell){
							if($(cell).attr('name')==$(me).attr('parent')){
								i = index;
							}
						});
						if($.isEmptyObject(row.querySelector('table'))){
							row.deleteCell(i);
						}else{
							row.cells[0].setAttribute('colspan',eval(row.cells[0].getAttribute('colspan'))-1);
						};
					});
				}else{
					parent.layer.msg(obj.msg);
				};
			}
		});
		
	}
	/**显示列参数设置界面*/
	function showColumnParamsWin(){
		layerinde = layer.open({
		    type: 1,
		    title:'列参数设置',
		    skin: 'layui-layer-rim', //加上边框
		    area: ['350px', '220px'], //宽高
		    content: $('#columnParams'),
		    end :function(){
		    	$("#columnParams *:hidden").show();//修改时隐藏的列显示
		    }
		});
	}
	/**保存列参数*/
	function saveOrupdateFtuParameters(me){
		var table = document.getElementById('printTemplate').querySelector('table table');
		var fname = $("#columnParams *[name='ftuParameter.fname']");
		var value = $("input[name='ftuParameter.falias']").val();
		var text = table.rows[0].innerText||table.rows[0].textContent;
		if($(me).attr('fstate')=='edit'){
			$.each(table.rows,function(i,row){
				$.each(row.cells,function(ii,cell){
					if(i==0){
						if($(cell).attr('name')==$(me).attr('parent')){
							text = text.replace($(cell)[0].innerText,'');
						}
					}
				});
			});
		}
		if(text.indexOf(value)>-1){
			layer.alert("名称不能为空，或重复！");
			return false;
		}
		//新增fname为空时赋别名
		if($.isEmptyObject(fname.val())){
			fname.val(value);
		}
		var  formValue = $('#columnParams form').serializeArray();
		
	 	$.ajax({
	 		type:"post",
	 		url:'${ctx}/saledeliver/saveOrupdateFtuParameters.net',
	 		data:formValue,
	 		dataType:'json',
	 		success:function(response){
	 			var obj = response;
	 			if(obj.success==true){
	 				if($(me).attr('fstate')=='add'){
		 				var i ="";
						$.each(table.rows,function(indexs,row){
							$.each(row.cells,function(index,cell){
								if($(cell).attr('name')==$(me).attr('parent')){
									i = index;
								}
							});
							
							if(!$.isEmptyObject(row.querySelector('table'))){
								row.cells[0].setAttribute('colspan',eval(row.cells[0].getAttribute('colspan'))+1);
							}else{
								var c = row.insertCell(i+eval($("input[name='inserttype']:checked").val()));
								if(indexs==0){//第一行
									c.setAttribute('name',newCellname[0]);
								}
								if($.isEmptyObject($("input[name='ftuParameter.fisprint']").val())){
										c.setAttribute('noprint',true);
								}else{
									c.setAttribute('noprint','');
								}
								c.setAttribute('height','24');
								c.setAttribute('width','50');
								
								if(table.rows[0].cells.length>0){
									if(indexs==0){
										c.innerHTML=value+'<span class="resizeLeftDivClass">&nbsp;</span><span class="resizeRightDivClass">&nbsp;</span>';
									}else{
										c.innerHTML='<span class="resizeLeftDivClass">&nbsp;</span><span class="resizeRightDivClass">&nbsp;</span>';
									}
								}
							}
						});
	 				}else if($(me).attr('fstate')=='edit'){
						$.each(table.rows,function(indexs,row){
							$.each(row.cells,function(index,cell){
								if($(cell).attr('name')==$(me).attr('parent')){
									i = index;
									cell.innerHTML = value+'<span class="resizeLeftDivClass">&nbsp;</span><span class="resizeRightDivClass">&nbsp;</span>';
								}
								if($.isEmptyObject($("input[name='ftuParameter.fisprint']:checked").val())){
									if(index==i&&indexs!=table.rows.length-1){
											cell.setAttribute('noprint',true);
									}
								}else{
										cell.setAttribute('noprint','');
									}
							});
							
						});
	 				}
					layer.close(layerinde);
					dragging();
	 			}else{
	 				Ext.Msg.alert('提示',obj.msg);
	 			}
	 		}
	 	});
	}
	/**选择数字or文本的时候显示Or隐藏相关字段*/
	function numshowOrhide(me){
		$.each($('.num input'),function(i,e){
			if($(me).val()==0){
				e.disabled=true;
			}else{
				e.disabled=false;
			}
		});
	}
	/**保存模板*/
	function saveTemplate(){
		var html = document.body.querySelector('.template').innerHTML;
		$.ajax({
			type:"post",
			url:'${ctx}/saledeliver/savePrintTemplate.net',
			dataType:'json',
			data:{html:html},
			success:function(response){
				var obj = response;
				if(obj.success==true){
					parent.layer.msg(obj.msg);
					parent.layer.closeAll();
				}else{
					Ext.Msg.alert('提示',obj.msg);
				}
			}
		});
	}
	/**还原模板*/
	function resetPrintTemplate(){
		layer.confirm('还原模板后，将恢复成平台默认模板，已修改的模板无法使用，是否继续操作！', {
		    btn: ['是','否'] //按钮
		}, function(index){
			$.ajax({
				type:'post',
				url:'${ctx}/saledeliver/resetPrintTemplate.net',
				dataType:'json',
				success:function(response){
					var obj = response;
					parent.layer.msg(obj.msg);
					if(obj.success==true){
						var p = [];
						p.push({'fname':'产品名称','fieldtype':0,'fsaledeliverentry':'fcusproductname'},{'fname':'规格','fieldtype':0,'fsaledeliverentry':'fspec'},{'fname':'单位','fieldtype':0,'fsaledeliverentry':'funit'},{'fname':'数量','fieldtype':1,'fdecimals':1,'fsaledeliverentry':'famount'},{'fname':'单价','fieldtype':1,'fdecimals':3,'fsaledeliverentry':'fdanjia'},{'fname':'金额','fieldtype':1,'fdecimals':2,'fcomputationalformula':'数量*单价','fsaledeliverentry':'fprice'},{'fname':'备注','fieldtype':0,'fsaledeliverentry':'fdescription'});
						$.ajax({
							type:'post',
							url:"${ctx}/saledeliver/saveAllFtuParams.net",
							dataType:'json',
							data:{ftu:JSON.stringify(p)},
							success:function(response){
								var obj = response;
								if(obj.success==true){
									layer.close(index);
									//parent.layer.close(parent.layer.getFrameIndex(window.name));
									var win = parent.$('#iframepage')[0].contentWindow;
									win.printTemplate();
									parent.layer.style(parent.layer.index,{
										'display':'none'
									})
								}
							}
						});
					}
				}
			});
		}, function(index){
		    layer.close(index);
		});
	}
	/**打印预览*/
	function printPreview(){
		var html = document.body.querySelector('#printTemplate').parentNode;
		$(document.body.querySelector('#printTemplate')).css('overflow','hidden');
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
// 		parent.$('#panelPrint')[0].contentWindow.document.body.innerHTML ="<style>@media print {@page {margin: 2mm 10mm 0mm 10mm;}} table{table-layout:fixed;}</style>" + html.innerHTML;//this.up('window').body.dom.innerHTML);
// 		parent.$('#panelPrint')[0].contentWindow.focus();
// 		parent.$('#panelPrint')[0].contentWindow.print();

		/**2016年4月13日13:55:04 HT*/
		PageSetup_Null();
		$('#printTemplate').append("<style>@media print{@page{margin: 4mm 10mm 0mm 10mm;} nav,aside{display:none;} *{margin:0;padding:0;}} #printTemplate table table td{ border:1px solid #000000;} #printTemplate table table table td{border:0px;}</style>");
		setTimeout("$('.template').jqprint();$('#printTemplate').hide();",500);
		/**2016年4月13日13:55:04 HT*/
		
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
	
    //设置网页打印的页眉页脚和页边距
	   function PageSetup_Null() {
	            var HKEY_Root, HKEY_Path, HKEY_Key;
	            HKEY_Root = "HKEY_CURRENT_USER";
	            HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
	            try {
	                var Wsh = new ActiveXObject("WScript.Shell");
	                HKEY_Key = "header";
	                //设置页眉（为空）
	                //Wsh.RegRead(HKEY_Root+HKEY_Path+HKEY_Key)可获得原页面设置   
	                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
	                HKEY_Key = "footer";
	                //设置页脚（为空）   
	                Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");

	                //这里需要浏览器版本，8.0以下的页边距设置与8.0及以上不一样，注意注册表里的单位是英寸，打印设置中是毫米，1英寸=25.4毫米
	                if (checkIEV() < 8.0) {
	                    HKEY_Key = "margin_left";
	                    //设置左页边距
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
	                    HKEY_Key = "margin_right";
	                    //设置右页边距
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
	                    HKEY_Key = "margin_top";
	                    //设置上页边距
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
	                    HKEY_Key = "margin_bottom";
	                    //设置下页边距   
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
	                }
	                else {
	                    HKEY_Key = "margin_left";
	                    //设置左页边距
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.2");
	                    HKEY_Key = "margin_right";
	                    //设置右页边距
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
	                    HKEY_Key = "margin_top";
	                    //设置上页边距
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0.145");
	                    HKEY_Key = "margin_bottom";
	                    //设置下页边距   
	                    Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
	                }
	            }
	            catch (e) {

	            }
	        }
</script>
</head>
<body>
	<div id="menu">
        <ul>
            <li><a onclick="addColumn(this)">新增列</a></li>
	        <li><a onclick="editColumn(this)" >修改列</a></li>
	        <li><a onclick="delColumn(this)" >删除列</a></li>
	    </ul>
	</div>
	<div id="columnParams">
		<form action="">
			<table>
				<tr>
					<td width="100px">名称：</td>
					<td colspan="2"><input type="text" name="ftuParameter.falias"/><input type="hidden" name="ftuParameter.fsaledeliverentry"/><input type="hidden" name="ftuParameter.fname"/></td>
				</tr>
				<tr>
					<td>典型：</td>
					<td><input type="radio" name="ftuParameter.fieldtype" checked="checked"  value="0" onclick="numshowOrhide(this)"/>文本</td>
					<td width="80px"><input type="radio" name="ftuParameter.fieldtype"  onclick="numshowOrhide(this)" value="1"/>数字</td>
				</tr>
				<tr class="num">
					<td>小数位数：</td>
					<td><input type="text" value="0" name="ftuParameter.fdecimals"/></td>
					<td>(例如：1)</td>
				</tr>
				<tr class="num">
					<td>计算公式：</td>
					<td colspan="2"><input type="text" name="ftuParameter.fcomputationalformula"/></td>
				</tr>
				<tr>
					<td>打印显示：</td>
					<td colspan="2"><input type="checkbox" name="ftuParameter.fisprint" checked="checked" value="1"/></td>
				</tr>
				<tr>
					<td><input type="radio"  name="inserttype"  value="0"/>前面插入列</td>
					<td colspan="2"><input type="radio" name="inserttype" checked="checked" style="margin-left:10px;" value="1"/>后面插入列</td>
				</tr>
				<tr>
					<td colspan="3"><input type="button" value="保存" onclick="saveOrupdateFtuParameters(this)"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div class="titleButton">
<!-- 		<a onclick="saveTemplate()">保存</a> -->
<!-- 		<a onclick="cloAll()">关闭</a> -->
<!-- 		<a onclick="resetPrintTemplate()">还原模板</a> -->
<!-- 		<a onclick="printPreview()">打印预览</a> -->
		<span onclick="saveTemplate()"><img src="${ctx}/css/images/save.png" style="vertical-align: sub;margin-left:20px;"/>保存</span>
		<span onclick="cloAll()"><img src="${ctx}/css/images/close-hover.gif">关闭</span>
		<span onclick="resetPrintTemplate()"><img src="${ctx}/css/images/editpic.png" style="vertical-align: middle;">还原模板</span>
		<span onclick="printPreview()"><img src="${ctx}/css/images/print.png" style="vertical-align: sub;">打印预览</span>
	</div>
	<div class="template">
	</div>
</body>
</html>