/*2016年3月25日15:34:50 HT*/
$(window).load(function(){ 	
  $('#fstate1 a').on('click',function(event){  
  $(this).addClass('active').siblings().removeClass('active');
  var _requireTxt=$(this).text();
  $(".schemeDesign").find("a").show();
  $("#schemeDesign_view").css({"margin-left":"0px"});//隐藏其他a之后“查看”的边距还原
  if(_requireTxt=="全部")
  {
   _requireTxt=null;   
  }
  
  
if(_requireTxt=="三月前数据")
  {
	  $("#myform a:lt(5)").addClass("black");
	  $("#myform a:lt(5)").removeClass("link");
	  $(".schemeDesign a:lt(5)").addClass("black");
	  $(".schemeDesign a:lt(5)").removeClass("link");
  }
else
{
	$("#myform a").addClass("link");
	$("#myform a").removeClass("black");
	$(".schemeDesign a:lt(5)").removeClass("black");
	$(".schemeDesign a").addClass("link");
	}
  
  $('#fstate').val(_requireTxt);
  loadData();
  
 });
 
});
/*2016年3月25日15:34:50 HT*/

function b()
{
	$("#myform a:lt(4),.schemeDesign a:lt(5)").css({"color":"#fff","background-color":"red"});
}
function a()
{
	$("#myform a:lt(4),.schemeDesign a:lt(5)").css({"color":"#ccc","background-color":"white"});
	}

$(function(){
	$('#fstate').val(null);
	/*
	$("#fstate").jqxDropDownList({ 
		width: 73, 
		height: 21,
		dropDownWidth:'70px',
		animationType:'fade',
		selectedIndex : -1,
		placeHolder:'全部',
		autoDropDownHeight:true,//下拉框展开高度
		searchMode:'contains'//模糊搜索
	});
	*/
	$("#fcustomer").jqxComboBox({
	    width: 160, 
	    height: 21,
	    animationType:'fade',
	    selectedIndex : -1,
	    placeHolder:'请选择客户',
		autoDropDownHeight:false,
		keyboardSelection : false,//下拉框键盘上下不触发选择
		autoComplete:true,
	    searchMode:'contains'//模糊搜索,
  	});
	//客户下拉支持清空输入框的搜索
	var custSel = $('#fcustomer_jqxComboBox');
	var name = custSel.attr('name');
	$('#fcustomer_jqxComboBox').removeAttr('name');
	$("#fcustomer").find('input[type=hidden]').attr('name',name);
	$('#fcustomer').find('.jqx-combobox-input').click(function(){
		$(this).select();
	});
	
	$("#ftype").jqxDropDownList({ 
		width: 90, 
		height: 21,
		animationType:'fade',
		selectedIndex : -1,
		placeHolder:'请选择类型',
		autoDropDownHeight:true,//下拉框展开高度
		searchMode:'contains'//模糊搜索
	});
	$('#fcustomer,#ftype').on('select', function (event) {
         loadData();
    });
	
	/*2016年3月18日09:35:14 HT*/
//	$('#fstate1 a').on('click',function(event){
//		$(this).addClass('active').siblings().removeClass('active');
//		var _requireTxt=$(this).text();
//		if(_requireTxt=="全部")
//		{
//			_requireTxt=null;
//		}
//		$('#fstate').val(_requireTxt);
//		loadData();
//	});
	/*2016年3月18日09:35:14 HT*/
	
    $('#fcustomer').focus(function(){
    	$(this).select();
    });
	//单击拖动
	var downMover = false;
	$('.expandTop,.top').mousedown(function(even){
		//点击向上延伸
		$('.top').click(function(){
			if($('.productdemandTable').height()>=500){
				$('.productdemandTable').height(eval($('.productdemandTable').height()-100));
			}
		});
		var height = even.clientY;
		var pdheight = $('.productdemandTable').height();
		downMover = true;
		$(document).mousemove(function(e){
			if(downMover===true){
				if(height-e.clientY){
					$('.productdemandTable').height(eval(pdheight-(height-e.clientY)));
					$('.top').unbind('click');
				}
			}
		});
	});
	$(document).mouseup(function(){
		downMover = false;
	});
	//复选框选中
	$('.productdemandTable .titleTr input[type=checkbox],.schemeDesignTable .titleTr input[type=checkbox]').click(function(){
		var bool = $(this).prop('checked')?false:true;
		var allTr = $(this).parents('tr').nextAll();
		$.each(allTr,function(i,t){
			$(t).find('input[type=checkbox]').prop('checked',!bool);
			$(t).find('input[type=checkbox]').change();
		});
	});
	// 对Date的扩展，将 Date 转化为指定格式的String
	// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
	// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
	// 例子： 
	// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
	// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
	Date.prototype.Format = function (fmt) { //author: meizz 
	    var o = {
	        "M+": this.getMonth() + 1, //月份 
	        "d+": this.getDate(), //日 
	        "h+": this.getHours(), //小时 
	        "m+": this.getMinutes(), //分 
	        "s+": this.getSeconds(), //秒 
	        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
	        "S": this.getMilliseconds() //毫秒 
	    };
	    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o)
	    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	    return fmt;
	};
	//回车搜索
	$('.filter').keypress(function(e){
		if(e.keyCode==13){
			$(this).next().click();
		}
	});
	$('.productdemand ._submit').click(function(){
		loadData(1);
	});
	$('.schemeDesign ._submit').click(function(){
		loadScheme(1);
	});
	$('#fstate,input[name=schemesearchKey]').prevAll("a").click(function(){
		var operate=$(this).data("operate");
		if($('#fstate').val()!="三月前数据")
		{
			switch(operate)
			{
			case 'receiveDemand':receiveDemand(); break;//接收
			case 'approintScheme':appointDesigner(); break;//设计师指定
			case 'backdemand':backdemand(); break;//需求退回
			case 'addSchemeDesign':addSchemeDesign(); break;//新增方案
			case 'demandproduct':demandproduct();break;//新增产品
			case 'delScheme':delScheme();break;//删除方案;
//			case 'exceldemand':exceldemand();break;//导出excel
			case 'editSchemeDesign':editSchemeDesign(); break;//修改
			case 'auditSchemeDesign':auditSchemeDesign(); break;//审核
			case 'affirmSchemeDesign':affirmSchemeDesign(this); break;//确认
			case 'unaffirmSchemeDesign':unaffirmSchemeDesign(); break;//取消确认
			
			default:
			}
		}
	});
	loadData();
});
function click()
{
	 document.getElementById("aclick").onclick = function(){
         return true;
     };
	}


function loadData(pageNo, resetScrollFlg){
	pageNo = pageNo || 1;
	var params =$("#myform").serialize();
	$("#kkpager").html("");
	$.ajax({
		url: ROOT_PATH+'/schemedesign/loaddemand.net?pageNo='+pageNo,
		data: params,
		type: 'post',
		dataType: 'json',
		success: function(res){
			var list = res.list;
			var template = ['<tr class="data dynamicTR" data-fid={{fid}}>',
							'<td><input type="checkbox"/></td>',
							'<td><a href="javascript:void(0);" class="fnumber">{{fnumber}}</a></td>',
							'<td>{{cname}}</td>',
							'<td>{{fname}}</td>',
							'<td>{{fdescription}}</td>',
							'<td>{{fauditortime}}</td>',
							'<td>{{flinkphone}}</td>',
							'<td>{{freceiver}}</td>',
							'<td>{{fisdemandpackage}}</td>',
							'<td class="fstate">{{fstate}}</td>',
							'<td>{{action}}</td>',
							'</tr>'].join("");
			var html = getHtml(list,template,{		//定义在_common.js中
				fdescription:function(name){
					if(name.length>15){
						var text = name.substring(0,15)+"...";
						return "<span tipscontent='"+name+"'>"+text+"</span>";
					}else{
						return name||'';
					}
				},
				fname:function(name){
					if(name.length>18){
						var text = name.substring(0,15)+"...";
						return "<span tipscontent='"+name+"'>"+text+"</span>";
					}else{
						return name||'';
					}
				},
				freceiver:function(name)
				{
					return name||'设计咨询';
				},
				fisdemandpackage: function(name){
					name = parseInt(name);
					switch(name){
					case 1: return '需求包';
					default: return '高端设计';
					}
				},
				action: function(_,data){
					if(data.fqq){
						a1 = '<a  onMouseDown=\'javascript:window.open("http://wpa.qq.com/msgrd?v=3&uin='+data.fqq+'&site=qq&menu=yes","_blank");\'><img border="0" src="http://wpa.qq.com/pa?p=2:'+data.fqq+':51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>';
					return a1;
					}
					return '';
				}
			});
			$('.dynamicTR').remove();
			if(html){
				$('#demandtable').append(html);
				getPagination(res,'kkpager',loadData,true);
				initEvent();
				
				
				$(".demandtabletitle").remove();
				var titleTr = $("#demandtable").find(".titleTr").clone(true);
				var titleTb = $("<table class=\"demandtabletitle\" style=\"position:absolute;\"></table>").append(titleTr);
				titleTb.prependTo("#divTitle");
				if(!(resetScrollFlg === false)){
					$('#demandtable').parent().scrollTop(0);
				}
				$.each(titleTb.find("td"), function(i, td){
					$(td).width($('#demandtable .titleTr').find("td:eq("+ i+")").width());
				});
				titleTb.find(":checkbox").unbind().change(function(event)
				{
					$('#demandtable .titleTr').find(":checkbox").prop("checked", $(this).prop("checked"));
					$('#demandtable .titleTr').find(":checkbox").click();
				});
				getHtmlLoadingAfterHeight();
			}
		}
	});
}
function getSchemeParam(demandid){
	var ret = {},
		searchKey = $.trim($("input[name=schemesearchKey]").val());
	if(searchKey){
		ret['schemesearchKey'] = searchKey;
	}
	if(demandid){
		ret['ffirstproductid'] = demandid;
	}
	return ret;
}
function loadScheme(pageNo,demandid){
	var queryHistory=$('#fstate').val();	
	queryHistory=queryHistory=='三月前数据'?'history':null;
	pageNo = pageNo || 1;
	var params =getSchemeParam(demandid);
	$("#kkpager2").html("");
	$.ajax({
		url: ROOT_PATH+'/schemedesign/loadSchemedesign.net?pageNo='+pageNo+'&queryHistory='+queryHistory,
		data: params,
		type: 'post',
		dataType: 'json',
		success: function(res){
			var list = res.list;
			var template = [' <tr class="data schemedynamicTR" data-fid={{fid}} data-faudited={{faudited}} data-entryfid="{{entryfid}}" data-fconfirmed={{fconfirmed}}>',
							'<td><input type="checkbox"></td>',
							'<td>{{fnumber}}</td>',
							'<td>{{fcustomer}}</td>',
							'<td>{{fname}}</td>',
							'<td>{{fdesginsupplier}}</td>',
							'<td>{{fcreator}}</td>',
							'<td>{{fcreatetime}}</td>',
							'<td>{{auditoraction}}</td>',
							'<td class="fconfirmed">{{fconfirmed}}</td>',
							'<td>{{fconfirmer}}</td>',
							'<td>{{fconfirmtime}}</td>',
							'<td>{{action}}</td>',
							'</tr> '].join("");
			var html = getHtml(list,template,{		//定义在_common.js中
				fconfirmed: function(name){
					name = parseInt(name);
					switch(name){
					case 1: return '是';
					default: return '否';
					}
				},
				fname:function(name){
				     if(name.length>18){
				      var text = name.substring(0,15)+"...";
				      return "<span tipscontent='"+name+"'>"+text+"</span>";
				     }else{
				      return name||'';
				     }
				    },
				action: function(_,data){
					if(data.fqq){
						a1 = '<a  onMouseDown=\'javascript:window.open("http://wpa.qq.com/msgrd?v=3&uin='+data.fqq+'&site=qq&menu=yes","_blank");\'><img border="0" src="http://wpa.qq.com/pa?p=2:'+data.fqq+':51" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>';
					return a1;
					}
					return '';
				},
				auditoraction:function(_,data)
				{
					if(data.entryfid){
						return data.fauditor||'';
					}
						return '——';
				}
			});
			$('.schemedynamicTR').remove();
			$('.schemeDesignTable .titleTr input[type=checkbox]').unbind('click');
			$('.schemeDesignTable .titleTr input[type=checkbox]').click(function(){
				var bool = $(this).is(':checked')?false:true;
				var allTr = $(this).parents('tr').nextAll();
				$.each(allTr,function(i,t){
					$(t).find('input[type=checkbox]').attr('checked',!bool);
				});
			});
			if(html){
				$('#schemetable tbody').append(html);
				getPagination(res,'kkpager2',loadScheme,true,demandid);
				initEvent();
			//	getHtmlLoadingAfterHeight();
			}
		}
	});
}

function initEvent(){
	$('.data').unbind('click mouseout mouseover');
	$('.data').mouseover(function(){
		if(!$(this).hasClass("selectRow"))$(this).addClass("focusRow");
 	}).mouseout(function(){
		 $(this).removeClass("focusRow");
	}).click(function(even){
		var checkbox = $(this).find('input[type=checkbox]');
		//	checkbox.prop('checked',!checkbox.prop('checked'));
		$(this).parents("table").find(':checkbox:checked').not(checkbox).prop('checked',false);
		$(this).parents("table").find('tr:gt(0)').not(this).removeClass("selectRow");
		checkbox.prop('checked',true);
		checkbox.change();
 		trclickEvent($(this)); 
	});
	$('.data input[type=checkbox]').unbind().click(function(event){
		var tr=$(this).parents("tr");
		 trclickEvent(tr);
		event.stopPropagation();//禁止冒泡 
	}).change(function(event)
	{
		var tr=$(this).parents("tr");
		if($(this).prop("checked")) tr.removeClass("focusRow").addClass("selectRow");
		else{tr.removeClass("selectRow");}
	});
	$('.fnumber').unbind('click').bind('click',function(){
		getProductDemanView($(this));
	});
	$('.data').find('[tipscontent]').on('mouseover', function(){
	    var index = layer.tips($(this).attr("tipscontent"), this, {
	        tips: [3, "#BBBBBB"],
	        time: 0
	    });
	    $(this).attr("tipsIndex", index);
	}).on('mouseout', function(){
		layer.close($(this).attr("tipsIndex"));
		$(this).removeAttr("tipsIndex");
	});
}
function trclickEvent(tr)//点击需求刷新方案列表
{
	 if ( tr.hasClass("dynamicTR") ){
		 	window.demanid = tr.data("fid");//把需求ID存入全局变量中
			loadScheme(1,tr.data("fid"));
		 }
}

//指定设计师
function appointDesigner()
{
	var statefunction=function(fstate){
	if(!(fstate=="已分配"||fstate=="已接收")) {
		parent.layer.msg('只有已分配、已接收的需求能指定！'); 
		return false;
		}
		return true;
	};
	var Ids=getIds("dynamicTR",statefunction,'.fstate');
	if(!Ids)	return false;
	if(Ids.length!=1)
	{
		parent.layer.msg('请选择一条记录操作！');
		return false;
	}
	var i = parent.layer.open({
	    title:'',move:false,
	    type: 2,
	    anim:true,
 	    area: ['385px', '230px'],
	    content:  window.getRootPath()+"/schemedesign/loadDesigner.net?id="+Ids[0],
	     success: function(layero, index){
	     	var content=layero.find("iframe").contents();
	     	var error=layero.find("iframe").contents().find("body").html();
		     if(content.find(".desiginForm").length===0) {
		     	parent.layer.close(i);
		     		 parent.layer.msg(error);
		     }
   	 }
	});
	parent.layer.style(i,{
		'border-radius':'15px'
	});
}
//退回需求
function backdemand()
{
	var Ids=getIds("dynamicTR");
	if(Ids.length===0)
	{
		parent.layer.msg('请选择记录操作！',{ time: 3000});
		return false;
	}
	var cidex=parent.layer.confirm('是否退回需求?方案未确认则直接删除方案！',{btn: ['是', '否']}, function(index){
	$.ajax({
		url:"${ctx}/schemedesign/unReceiveProductdemand.net",
		type:"post",
		dataType:"json",
		traditional:true,//不深度序列化参数对象
		data:{id:Ids.toString()},
		success:function(data){
			parent.layer.close(cidex);
			if(data.success){
				parent.layer.alert('操作成功！',function(alIndex){
					refreshDemandData();
				loadScheme(1,"null");
					parent.layer.close(alIndex);
			    });
			}else{
				parent.layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'),{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.close(cidex);
			parent.layer.alert('操作失败！',{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
		}
	});
	});
}

function getIds(parent,fnc,fnvalue) {
	var Ids=[];
	$('.data input:checkbox:checked').each(function(i){
		var tr=$(this).parents("tr");
		if(tr.hasClass(parent))
		{
			if(typeof fnc ==="function"&&!fnc(tr.find(fnvalue).text())) {Ids=false;return false;}
			Ids.push(tr.data("fid"));
		}
	});
	return Ids;
}
function delScheme()
{
	var valifnc=function(fconfirmed){
		if(fconfirmed==="是") {
		parent.layer.msg('已确认的方案设计不能删除！'); 
		return false;
		}
		return true;
	};
	var Ids=getIds("schemedynamicTR",valifnc,'.fconfirmed');
	if(!Ids)	return false;
	if(Ids.length==0)
	{
		parent.layer.msg('请选择记录操作！！',{ time: 3000});
		return false;
	}
	var cidex=parent.layer.confirm('<div style="text-align:center">是否确定删除</div>',{btn: ['是', '否']}, function(index){
	$.ajax({
		url:"${ctx}/schemedesign/delSchemeDesign.net",
		type:"post",
		dataType:"json",
		traditional:true,//不深度序列化参数对象
		data:{id:Ids.toString()},
		success:function(data){
			parent.layer.close(cidex);
			if(data.success){
				parent.layer.msg('操作成功！',{ time: 2000},function(){
					$('.schemeDesign .data input:checkbox:checked').parents('tr').remove();
				
				});
			}else{
				parent.layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'),{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.close(cidex);
			parent.layer.alert('操作失败！',{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
		}
	});
	});
}
//查看详情
function getProductDemanView(me){
	var queryHistory=$('#fstate').val();	
	queryHistory=queryHistory=='三月前数据'?'history':null;
	var m = $(me);
	if(!me){
		if($('.productdemandTable .data input:checkbox:checked').length!=1){
			parent.layer.alert('请选择一条数据！');
			return false;
		}
		m = $('.productdemandTable .data input:checkbox:checked');
	}
	var fid = m.parents('tr').data('fid');
	var type = m.parents('tr').find('td').eq(8).text();
	console.log(type);
	var url;
	if(type=="高端设计"){
		//window.location.href = getBasePath()+"/schemedesign/detail.net?id="+fid;
		url=getBasePath()+"/schemedesign/detail.net?id="+fid;
	}else if(type=="需求包"){
		//window.location.href = getBasePath()+"/schemedesign/packageDetail.net?id="+fid;
		url=getBasePath()+"/schemedesign/packageDetail.net?id="+fid;
	}
	parent.layer.open({
		type: 2,
	    title: false,
	    shade: 0.8,
	    scrollbar:false,
	    area: ['1087px', '90%'],
	    end:function(){
	    	parent.$('#iframepage')[0].contentWindow.getHtmlLoadingAfterHeight();
	    },
	    content: url+"&queryHistory="+queryHistory
	});
}
//新增方案
function addSchemeDesign(){
	var c = $('.productdemandTable .data input:checkbox:checked');
	if(c.length!=1){
		parent.layer.alert('请选择一条数据！');
		return false;
	}
	var fid = c.parents('tr').data('fid');
	var type = c.parents('tr').find('td').eq(8).text();
	var fstate = c.parents('tr').find('td.fstate').text();
	if(fstate==="已分配"){
		parent.layer.msg('未接收不能新增方案'); 
		return false;
		}
	var url = '';
	if(type=="高端设计"){
		url = getBasePath()+"/schemedesign/designCreate.net?fdid="+fid;
	}else if(type=="需求包"){
		url = getBasePath()+"/schemedesign/designCreate.net?fdid="+fid;
	}
	parent.layer.open({
		type: 2,
	    title: false,
	    shade: 0.8,
	    scrollbar:false,
	    area: ['1060px', '90%'],
	    end:function(){
	    	parent.$('#iframepage')[0].contentWindow.getHtmlLoadingAfterHeight();
	    },
	    content: url
	});
}

function demandproduct()
{
	var Ids=getIds("dynamicTR");
	if(Ids.length!=1)
	{
		parent.layer.msg('请选择一条记录操作！！',{ time: 3000});
		return false;
	}
	$.ajax({
		url:"${ctx}/schemedesign/demandProduct.net",
		type:"post",
		dataType:"json",
		data:{id:Ids.toString()},
		success:function(data){
			if(data.success){
				parent.layer.open({
					type: 2,
				    title: false,
				    shade: 0.8,
				    scrollbar:false,
				    area: ['1070px', '90%'],
				    end:function(){
				    	parent.$('#iframepage')[0].contentWindow.getHtmlLoadingAfterHeight();
				    },
				    content: getBasePath()+"/schemedesign/demandProductAdd.net?id="+Ids[0]
				});
					//window.location.href = getBasePath()+"/schemedesign/demandProductAdd.net?id="+Ids[0];
			}else{
				parent.layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'),{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
			}
		},
		error:function (){
			parent.layer.alert('操作失败！',{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
		}
	});
}
//修改方案
function editSchemeDesign(){
	var checkbox = $('#schemetable .data input[type=checkbox]:checked');
	if(checkbox.length!=1){
		parent.layer.alert("请选择一条数据");
		return false;
	}
	var fconfirmed = checkbox.parents('tr').find('.fconfirmed').text();
	if(fconfirmed=="是"){
		parent.layer.alert("已确认的不能修改！");
		return false;
	}
	var fid = checkbox.parents('tr').data('fid');
	var type = "edit";
	//window.location.href = getBasePath()+"/schemedesign/schemeDetail.net?id="+fid+"&type="+type;
	parent.layer.open({
		type: 2,
	    title: false,
	    shade: 0.8,
	    scrollbar:false,
	    area: ['1060px', '90%'],
	    end:function(){
	    	parent.$('#iframepage')[0].contentWindow.getHtmlLoadingAfterHeight();
	    },
	    content: getBasePath()+"/schemedesign/schemeDetail.net?id="+fid+"&type="+type
	});
}
//查看方案
function viewSchemeDesign(){
	var checkbox = $('#schemetable .data input[type=checkbox]:checked');
	if(checkbox.length!=1){
		parent.layer.alert("请选择一条数据");
		return false;
	}
	var fid = checkbox.parents('tr').data('fid');
	var type = "view";
	var queryHistory=$('#fstate').val();
	queryHistory=queryHistory=='三月前数据'?'history':null;
	var index = parent.layer.open({
		type: 2,
	    title: false,
	    shade: 0.8,
	    scrollbar:false,
	    area: ['1060px', '90%'],
	    end:function(){
	    	parent.$('#iframepage')[0].contentWindow.getHtmlLoadingAfterHeight();
	    },
	    content: getBasePath()+"/schemedesign/schemeDetail.net?id="+fid+"&type="+type+"&queryHistory="+queryHistory
	});
	//window.location.href = getBasePath()+"/schemedesign/schemeDetail.net?id="+fid+"&type="+type;
}

function exceldemand()
{
		 $("#hideschemeexcelform").empty();
      $("#hideschemeexcelform").attr("action",ROOT_PATH+"/schemedesign/execlSchemeDesignlist.net");  
        var html=[].join("");
        var data=$("#myform").serializeArray();
       	$('.dynamicTR input:checkbox:checked').parents("tr").each(function(i){
       		html+=['<input type="hidden" name="fid" value="',$(this).data("fid"),'"/>'].join("");
       	});
       $.each(data,function(index, filed){
    	   html+=['<input type="hidden" name="',filed.name,'" value="',filed.value,'"/>'].join("");
      });
       $(html).appendTo("#hideschemeexcelform");
        //打开窗体，并post提交页面  
        $("#hideschemeexcelform").submit(); 
}
//审核方案
function auditSchemeDesign(){
	var checkbox = $('#schemetable .data input[type=checkbox]:checked');
	if(checkbox.length!=1){
		parent.layer.alert("请选择一条数据");
		return false;
	}
	var auditText = checkbox.parents('tr').find('td')[7].innerHTML;
	if(auditText=="——"){
		parent.layer.alert("无需审核！");
		return false;
	}else if(auditText!=""){
		parent.layer.alert("已经审核！");
		return false;
	}
	var fid = checkbox.parents('tr').data('fid');
	var type = "audit";
	parent.layer.open({
		type: 2,
	    title: false,
	    shade: 0.8,
	    scrollbar:false,
	    area: ['1060px', '90%'],
	    end:function(){
	    	parent.$('#iframepage')[0].contentWindow.getHtmlLoadingAfterHeight();
	    },
	    content: getBasePath()+"/schemedesign/schemeDetail.net?id="+fid+"&type="+type
	});
	//window.location.href = getBasePath()+"/schemedesign/schemeDetail.net?id="+fid+"&type="+type;
}
//确认方案
function affirmSchemeDesign(me){
	var checkbox = $('#schemetable .data input[type=checkbox]:checked');
	if(checkbox.length<1){
		parent.layer.alert("请选择数据");
		return false;
	}
	var fid = '';
	var bool = true;
	checkbox.each(function(i,c){
		var tr = $(this).parents('tr');
		if((tr.data('faudited')==0&&!$.isEmptyObject(tr.data('entryfid')))||tr.data('fconfirmed')=='是'){
			parent.layer.alert("编号："+tr.find('td')[1].innerHTML+"未审核或已确认！");
			bool = false;
			return false;
		}
		fid +="'"+tr.data('fid')+"'";
		if(i<checkbox.length-1){
			fid+=',';
		}
	});
	if(!bool){
		return bool;
	}
	parent.layer.confirm('是否确认？', {
	    btn: ['是','否'] //按钮
	}, function(index){
	 me.onclick=function(){}
		$.ajax({
			url:getBasePath()+"/schemedesign/affirmSchemeDesign.net",
			data:{fids:fid},
			dataType:'text',
			success:function(response){
				if(response=="success"){
					loadScheme(1,demanid);//刷新
					parent.layer.msg('确认成功！');
				}else{
					parent.layer.alert(response);
				}
				 me.onclick=function(){affirmSchemeDesign(me);}
			}
		});
	    parent.layer.close(index);
	}, function(){
	});
}
//取消确认方案
function unaffirmSchemeDesign(){
var checkbox = $('#schemetable .data input[type=checkbox]:checked');
	if(checkbox.length<1){
		parent.layer.alert("请选择数据");
		return false;
	}
	var fid = '';
	var bool = true;
	checkbox.each(function(i,c){
		var tr = $(this).parents('tr');
		if(tr.data('fconfirmed')!='是'){
			parent.layer.alert("编号："+tr.find('td')[1].innerHTML+"未确认！");
			bool = false;
			return false;
		}
		fid +="'"+tr.data('fid')+"'";
		if(i<checkbox.length-1){
			fid+=',';
		}
	});
	if(!bool){
		return bool;
	}
	parent.layer.confirm('是否取消确认？', {
	    btn: ['是','否'] //按钮
	}, function(index){
		$.ajax({
			url:getBasePath()+"/schemedesign/unaffirmSchemeDesign.net",
			data:{fids:fid},
			dataType:'text',
			success:function(response){
				if(response=="success"){
					loadScheme(1,demanid);//刷新
					parent.layer.msg('取消确认成功！');
				}else{
					parent.layer.alert(response);
				}
			}
		});
	    parent.layer.close(index);
	}, function(){
	});
}

//接收需求
function receiveDemand(){
	var Ids=getIds("dynamicTR");
	if(Ids.length===0)
	{
		parent.layer.msg('请选择记录操作！',{ time: 3000});
		return false;
	}
	var cidex=parent.layer.confirm('是否接收需求?',{btn: ['是', '否']}, function(index){
		$.ajax({
			url:"${ctx}/schemedesign/receiveProductdemand.net",
			type:"post",
			dataType:"json",
			traditional:true,//不深度序列化参数对象
			data:{id:Ids.toString()},
			success:function(data){
				parent.layer.close(cidex);
				if(data.success){
					parent.layer.alert('操作成功！',function(alIndex){
						refreshDemandData();
						loadScheme(1,"null");
						parent.layer.close(alIndex);
				    });
				}else{
					parent.layer.alert(!data.msg?"操作失败！":('<div style="text-align:center">'+data.msg+'</div>'),{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
				}
			},
			error:function (){
				parent.layer.close(cidex);
				parent.layer.alert('操作失败！',{offset: Math.max(window.screen.height-442,550)/2+'px'}, function(index){parent.layer.close(index);});
			}
		});
	});
};

//刷新数据，滚动条、页数不变
function refreshDemandData(){
	loadData(kkpager.pno, false);
}