<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线下单</title>
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/orderOnline.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager2.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager3.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.selectlist.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/pages/custproduct/new_list.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.selectlist.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<style >
#div2 .xq{
text-decoration: none;
}
#div2 .xq:hover{
	color:red;
}
.updateDetail{
	cursor:pointer;
}
.delfile {
    margin: 0px 0 0 -10px;
    position: absolute;
    background: url(${ctx}/css/images/close-hover.gif) no-repeat scroll 0 0 transparent;
    width: 15px;
   /*  top: -10px;
    right: -7px; */
    display: none;
    float:left;
    height: 16px;
    overflow: hidden;
    text-indent: -9999px;
}
/*2016年3月17日10:08:06 HT*/
#tbl3_01,#tbl4_01{
 font-size:12px;
 text-align:center;
 border-collapse:collapse;
 clear:both;
 }
a{
 text-decoration: none;
}
#fstate1,#fstate2{
 float: left;
 position: relative;
 z-index:999;
 }
#fstate1 a,#fstate2 a{
 float: left;
 padding: 4px 10px;
 color: #3a3a3a;
 border-radius: 2px 2px 0 0;
 font-size: 17px;
 font-family: 微软雅黑;
 outline:none;
}
#fstate1 a.active,#fstate2 a.active{
 color: #fff;
 background: #f76350; 
}
.require{width: 1050px;margin:0 auto; margin-top:20px;position:relative;}

#searchForm ._submit{margin-right:10px;}
/*2016年3月17日10:08:06 HT*/
</style>
<script type="text/javascript">

function pl_addCar_btn(){
	//parent.scrollTo(0,0);
	parent.location.href="${ctx}/shopcar/shoppingDetail.net";
}
/*** 快速下单*/
function orderDetail(fid){
		parent.scrollTo(0,0);
		var queryHistory=$("#fstate_hidden").val();//获取状态值(100为三月前数据)
		window.location.href="${ctx}/deliverapply/detail.net?id="+fid +"&queryHistory="+queryHistory;
}


//快速下单
function getQuickOrderTable(page){
		var obj =$("#searchForm").serialize();
		var loadIndex = parent.layer.load(2);
		$("#kkpager").html("");
		$.ajax({
				type : "POST",
				url : "${ctx}/custproduct/load.net?pageNo="+page,
				dataType:"json",
				async: false,
				data:obj,
				success : function(response) {
					$(".alltr").remove();
					if(response.list.length>0){
						$.each(response.list, function(i, ev) {
							var  html;
								if(ev.fiscommon==true){
									html =[
										'<tr height="30" style="background-color:#F0F0F0;line-height:30px;" align="left" class="alltr">',
											'<td colspan="2">&nbsp;&nbsp;制造商：',ev.supplierName,'</td>',
											'<td>创建时间：',ev.fcreatetimeString,'</td>',
											'<td colspan="2">最近下单时间：',(ev.balanceqty&&!ev.flastordertime&&ev.fcreatetimeString)?ev.fcreatetimeString.substring(0,10):ev.flastordertime,'</td>',
											'<td>库存：',!ev.balanceqty?0:ev.balanceqty,'</td>',
										'</tr>',
										'<tr height="125" style="border:1px solid lightgray;border-top:none;" class="alltr">',
											'<td><input type="checkbox" name="product" productid="'+ev.fid+'"/></td>',
											 '<td><img height="87" width="87" src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ev.fid,'"/>&nbsp;<input type="button" value="" class="downLoad" onclick="downLoad(\''+ev.fid+'\',\'ksinfo\');"/></td>', 
											'<td>',ev.fname,'</td>',
											'<td>',ev.fspec,'</td>', 
											'<td>',ev.fmaterial,'</td>',
											'<td>',
												'<a href="javascript:void(0);" class="xd"    onclick="sure_xd(\''+ev.fid+'\',\''+ev.supplierId+'\');">下单</a><br /><br />',
												'<a href="javascript:void(0);" class="swcy"  onclick="newcancelCommon(\''+ev.fid+'\');">取消常用</a><br /><br />',
												'<a href="javascript:void(0);" class="jrgwc" onclick="addCar(\''+ev.fid+'\',\''+ev.supplierId+'\');">加入购物车</a>',
											'</td>',
										'</tr>',
										'<tr height="10" class="alltr">',
											'<td colspan="6"></td>',
										'</tr>'
									  ].join("");
								}else if(ev.fiscommon==false||$.isEmptyObject(ev.fiscommon)){
									  html =[
										'<tr height="30" style="background-color:#F0F0F0;line-height:30px;" align="left" class="alltr">',
											'<td colspan="2">&nbsp;&nbsp;制造商：',ev.supplierName,'</td>',
											'<td>创建时间：',ev.fcreatetimeString,'</td>',
											'<td colspan="2">最近下单时间：',(ev.balanceqty&&!ev.flastordertime&&ev.fcreatetimeString)?ev.fcreatetimeString.substring(0,10):ev.flastordertime,'</td>',
											'<td >库存：',!ev.balanceqty?0:ev.balanceqty,'</td>',
										'</tr>',
										'<tr height="125" style="border:1px solid lightgray;border-top:none;" class="alltr">',
											'<td><input type="checkbox" name="product" productid="'+ev.fid+'"/></td>',
											'<td><img height="87" width="87" src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ev.fid,'"/>&nbsp;<input type="button" value="" class="downLoad" onclick="downLoad(\''+ev.fid+'\',\'ksinfo\');"/></td>',
											'<td>',ev.fname,'</td>',
											'<td>',ev.fspec,'</td>',
											'<td>',ev.fmaterial,'</td>',
											'<td>',
												'<a href="javascript:void(0);" class="xd"    onclick="sure_xd(\''+ev.fid+'\',\''+ev.supplierId+'\');">下单</a><br /><br />',
												'<a href="javascript:void(0);" class="swcy"  onclick="setcommon(\''+ev.fid+'\');">设为常用</a><br /><br />',
												'<a href="javascript:void(0);" class="jrgwc" onclick="addCar(\''+ev.fid+'\',\''+ev.supplierId+'\');">加入购物车</a>',
											'</td>',
										'</tr>',
										'<tr height="10" class="alltr">',
											'<td colspan="6"></td>',
										'</tr>'
									  ].join("");
								}
							$(html).appendTo(".quickOrderTable");
						});
						$("input[name=product]").click(function(){
							if($(this).is(":checked")==false){
									$("#product").attr("checked",false);	
							}
							else{
								if($("input[name='product']").size()==$("input[name='product']:checked").size()){
									$("#product").attr("checked",true);
									return;
								}
							}
						});
						//子页面设置父级iframe高度
						window.getHtmlLoadingAfterHeight();
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager', //divID
							click : function(n){
								window.getQuickOrderTable(n);
								this.selectPage(n);
							},
							pno : response.pageNo,//当前页码
							total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
							totalRecords : response.totalRecords,//总数据条数
							lang : {
								prePageText : '上一页',
								nextPageText : '下一页',
								totalPageBeforeText : '共',
								totalPageAfterText : '页',
								totalRecordsAfterText : '条数据',
								gopageBeforeText : '转到',
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
					}
					parent.layer.close(loadIndex); 
				}
		});
}
/*********************************************************************************************************************************************************************************************************/
//订单记录--要货
function getOrderRecordTable(page){
		$("#kkpager2").html("");//页面里页码内容设置为空
		var obj =$(".dlick_form").serialize();//序列化表单 name=value的形式
		var loadIndex = parent.layer.load(2);//加载的动画
		$.ajax({
				type : "POST",
				url : "${ctx}/deliverapply/load.net?pageNo="+page,
				dataType:"json",
				async: false,//同步
				data:obj,
				success : function(response) {
					$(".tbl1tr").remove();//移除所有的$(".tbl1tr")
					if(response.fbacthstock){
						$('#stocksing').show();//显示备货按钮
					}else{
						$('#stocksing').hide();//隐藏备货按钮
					}
					if(response.list.length>0){//获取的数据
						$.each(response.list, function(i, ev) {
							var delId = ev.fid+"_del";
						    var editId = ev.fid+"_edit";
						    var phoneId = ev.fid+"_phone";
							var html =[
							           '<tr height="10" class="tbl1tr">',
							           		'<td colspan="5"></td>',
							           '</tr>',
							           '<tr height="30" style="background-color:#F0F0F0;line-height:30px;" align="left" class="tbl1tr">',
							               '<td colspan="5">&nbsp;&nbsp;',
									           '<span style="display:inline-table;width:175px;">制造商：',ev.suppliername,'</span>',
									           '<span style="display:inline-table;width:180px;">订单编号：',ev.fnumber,'</span>',
									           '<span style="display:inline-table;width:240px;">交付时间：',ev.farrivetimeString,'</span>',
									           '<span style="display:inline-table;width:300px;">数量：',ev.famount,'</span>',
									           '<span style="display:inline-table;width:100px;"><a href="javascript:void(0);"  onclick="orderDetail(\''+ev.fid+'\');"   class="xq">详情</a></span>',
									           '<input type="button" id='+delId+' onclick="del_yh(\''+ev.fid+'\');" value="" style="display:inline-table;float:right;width:25px;height:25px;border:none;background:url(${ctx}/css/images/iconfont-lajitong9.png) 0px 0px no-repeat;cursor:pointer;" title="删除"/>',
									           '</td>',
							           '</tr>',
							           '<tr height="125" style="border:1px solid lightgray;border-top:none;" class="tbl1tr">',

									       '<td><img src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ev.fcusproductid,'" style="width: 87px; height: 87px; padding-top:2px; padding-bottom:2px;" />&nbsp;<input type="button" value="" class="downLoad" onclick="downLoad(\''+ev.fcusproductid+'\',\'ddinfo\');"/></td>',
								           '<td>',ev.fpdtname,'</td>',
								           '<td>',ev.fpdtspec,'</td>',
								           '<td id="fstateTr" class='+ev.fid+'>',ev.fstate,'</td>',
								           '<td>',
								           '<span class="phone" id='+phoneId+'>',ev.supplierPhone,'</span>',
								           '<span id='+editId+' class="updateDetail" onclick="edit_yh(\''+ev.fid+'\');">修改</span>',
								           '</td>',
							           ' </tr>'
								        ].join("");
							$(html).appendTo(".orderRecordTable");
						});
						//子页面设置父级iframe高度
						window.getHtmlLoadingAfterHeight();
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager2', //divID
							click : function(n){
								window.getOrderRecordTable(n);
								this.selectPage(n);
							},
							pno : response.pageNo,//当前页码
							total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
							totalRecords : response.totalRecords,//总数据条数
							lang : {
								prePageText : '上一页',
								nextPageText : '下一页',
								totalPageBeforeText : '共',
								totalPageAfterText : '页',
								totalRecordsAfterText : '条数据',
								gopageBeforeText : '转到',
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
						$(".orderRecordTable tr #fstateTr").each(function(i) {
			                var t = $(this).text();//获取文本
			                var tdId = $(this).attr("class");//获取类的值
			                if($("#"+tdId+"_phone").text() =='' || $("#"+tdId+"_phone").text() ==null){
			                	$("#"+tdId+"_phone").remove();
			                }
			                if(t==0){
			                	$(this).text('未接收');
			                }else if(t==1 || t==2 || t==3){
			                	$(this).text('已接收');
			                	 $("#"+tdId+"_del").remove();
						    	 $("#"+tdId+"_edit").remove();
			                }else if(t==4){
			                	$(this).text('已入库');
			                	$("#"+tdId+"_del").remove();
						    	 $("#"+tdId+"_edit").remove();
			                }else if(t==5){
			                	$(this).text('部分发货');
			                	$("#"+tdId+"_del").remove();
						    	 $("#"+tdId+"_edit").remove();
			                }else if(t==6){
			                	$(this).text('全部发货');
			                	$("#"+tdId+"_del").remove();
						    	$("#"+tdId+"_edit").remove();
			                }
            		});
					}
					parent.layer.close(loadIndex); 
				}
		});
}

//订单记录--备货
function getOrderRecordTable_BH(page){
		var obj =$(".dlick_form").serialize();//序列化表单
		var loadIndex = parent.layer.load(2);
		$("#kkpager2").html("");
		$.ajax({
				type : "POST",
				url : "${ctx}/mystock/load.net?pageNo="+page,
				dataType:"json",
				async: false,
				data:obj,
				success : function(response) {
					$(".tbl1tr").remove();
					if(response.list.length>0){
						$.each(response.list, function(i, ev) {
							var delId = ev.fid+"_del";
						    var editId = ev.fid+"_edit";
						    var phoneId = ev.fid+"_phone";
							var html =[
							           '<tr height="10" class="tbl1tr">',
							           		'<td colspan="5"></td>',
							           '</tr>',
							           '<tr height="30" style="background-color:#F0F0F0;line-height:30px;" align="left" class="tbl1tr">',
							               '<td colspan="5">&nbsp;&nbsp;',
									           '<span style="display:inline-table;width:175px;">制造商：',ev.suppliername,'</span>',
									           '<span style="display:inline-table;width:180px;">订单编号：',ev.fnumber,'</span>',
									           '<span style="display:inline-table;width:240px;">交付时间：',ev.ffinishtime,'</span>',
									           '<span style="display:inline-table;width:300px;">数量：',ev.fplanamount,'</span>',
									           '<span style="display:inline-table;width:100px;"><a href="javascript:void(0);"  onclick="orderDetail_BH(\''+ev.fid+'\');"   class="xq">详情</a></span>',
									           '<input type="button" id='+delId+' onclick="del_bh(\''+ev.fid+'\');" value="" style="display:inline-table;float:right;width:25px;height:25px;border:none;background:url(${ctx}/css/images/iconfont-lajitong9.png) 0px 0px no-repeat;cursor:pointer;" title="删除"/>',
									           '</td>',
							           '</tr>',
							           '<tr height="125" style="border:1px solid lightgray;border-top:none;" class="tbl1tr">',
							               '<td><img src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ev.fcustproductid,'" style="width: 87px; height:87px; padding-top:2px; padding-bottom:2px;" />&nbsp;<input type="button" value="" class="downLoad" onclick="downLoad(\''+ev.fcustproductid+'\',\'ddinfo\');"/></td>',
								           '<td>',ev.fpdtname,'</td>',
								           '<td>',ev.fpdtspec,'</td>',
								           '<td id="fstateTr" class='+ev.fid+'>',ev.fstate,'</td>',
								           '<td>',
								           '<span id='+phoneId+' class="phone">',ev.supplierPhone,'</span>',
								           '<span id='+editId+' class="updateDetail" onclick="edit_bh(\''+ev.fid+'\');">修改</span>',
								           '</td>',
							           ' </tr>'
								        ].join("");
							$(html).appendTo(".orderRecordTable");
						});
						//子页面设置父级iframe高度
						window.getHtmlLoadingAfterHeight();
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager2', //divID
							click : function(n){
								window.getOrderRecordTable_BH(n);
								this.selectPage(n);
							},
							pno : response.pageNo,//当前页码
							total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
							totalRecords : response.totalRecords,//总数据条数
							lang : {
								prePageText : '上一页',
								nextPageText : '下一页',
								totalPageBeforeText : '共',
								totalPageAfterText : '页',
								totalRecordsAfterText : '条数据',
								gopageBeforeText : '转到',
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
						$(".orderRecordTable tr #fstateTr").each(function(i) {
			                var t = $(this).text();
			                var tdId = $(this).attr("class");
			                if($("#"+tdId+"_phone").text() =='' || $("#"+tdId+"_phone").text() ==null){
			                	$("#"+tdId+"_phone").remove();
			                }
			                if(t==0){
			                	$(this).text('未接收');
			                }else if(t==1 || t==2 || t==3){
			                	$(this).text('已接收');
			                	$("#"+tdId+"_del").remove();
						    	$("#"+tdId+"_edit").remove();
			                }
            		});
					}
					parent.layer.close(loadIndex); 
				}
		});
}

function dlick_goods(obj){
	if(obj==0){//要货
		$("#search").html("");
		$("#search").html("<input type=\"button\" value=\"\" class=\"_submit\" id=\"searchButtonOrderRecord\" onclick=\"query_yh();\"/>");
		$("#demanding").attr("class","allPro");//要货
		$("#stocksing").attr("class","common");//备货
		var $numb3=$("#fstate option").size();
		for(var i =0;i<$numb3;i++){
			$("#fstate option[index='"+i+"']").remove();
		}
		$("#fstate").empty();
		$("#fstate").attr("name","deliverapplyQuery.fstate");
		$("#searchKey1").attr("name","deliverapplyQuery.searchKey");
		$("#searchButtonQuickOrder").bind("click","query_yh();");
		$("#fstate").append("<option value=''>全部</option><option value='0'>未接收</option><option value='1'>已接收</option><option value='4'>已入库</option><option value='5'>部分发货</option><option value='6'>全部发货</option>"); 
		$("#fstate").selectlist({
			zIndex: $numb3,
			width: 235,
			height: 25
		});
		$("#fstate li").click(function(){
	    	var state = $(this).text();
			$("#fstate").val(state);
				window.getOrderRecordTable(1);
		});	
		window.getOrderRecordTable(1);
	}else if(obj==1){//备货
		$("#search").html("");
		$("#search").html("<input type=\"button\" value=\"\" class=\"_submit\" id=\"searchButtonOrderRecord\" onclick=\"query_bh();\"/>");
		$("#demanding").attr("class","common");//要货
		$("#stocksing").attr("class","allPro");//备货
		var $numb3=$("#fstate option").size();
		for(var i =0;i<$numb3;i++){
			$("#fstate option[index='"+i+"']").remove();
		}
		$("#fstate").empty();
		$("#fstate").attr("name","myStockQuery.fstate");
		$("#searchKey1").attr("name","myStockQuery.searchKey");
		$("#fstate").append("<option value=''>全部</option><option value='0'>未接收</option><option value='1'>已接收</option>"); 
		$("#fstate").selectlist({
			zIndex: $numb3,
			width: 100,
			height: 25
		});	
		$("#fstate li").click(function(){
	    	var state = $(this).text();
			$("#fstate").val(state);
				window.getOrderRecordTable_BH(1);
		});	
		window.getOrderRecordTable_BH(1);
	}
}


//需求包列表
function getMandPackageTable(page){
	var obj =$(".mandPackageForm").serialize();
		var loadIndex = parent.layer.load(2);
		$("#kkpager3").html("");
		$.ajax({
				type : "POST",
				url : "${ctx}/firstproductdemand/loadpackage.net?pageNo="+page,
				dataType:"json",
				async: false,
				data:obj,
				success : function(response) {
					$(".mandTr").remove();
					if(response.list.length>0){
						$.each(response.list, function(i, ev) {
							var qqbtn =ev.fid+"_qq";//QQ交谈
							var delbtn =ev.fid+"_del";//删除按钮
							var editbtn = ev.fid+"_edit";//修改
							var authorbtn = ev.fid+"_author";//设计师
							var html =[
							           '<tr height="10" class="mandTr">',
											'<td colspan="5"></td>',
									   '</tr>',
									   '<tr height="30" style="background-color:#F0F0F0;line-height:30px;" align="left" class="mandTr">',
										   '<td colspan="5">&nbsp;&nbsp;',
										      '<span style="display:inline-table;width:175px;">制造商：',ev.supplierName,'</span>',
										      '<span style="display:inline-table;width:180px;">订单编号：',ev.fnumber,'</span>',
										      '<span style="display:inline-table;width:530px;">交付时间：',ev.farrivetimeString,'</span>',
										      '<span style="display:inline-table;width:90px;"><a href="javascript:void(0);"  onclick="packageDetail(\''+ev.fid+'\',false);"   class="xq">详情</a></span>',
										      '<input id='+delbtn+' type="button" value="" onclick="del_mandpackage(\''+ev.fid+'\');" style="display:inline-table;float:right;width:25px;height:25px;border:none;background:url(${ctx}/css/images/iconfont-lajitong9.png) 0px 0px no-repeat;cursor:pointer;" title="删除"/>',
										  '</td>',
									   '</tr>',
										'<tr height="125" style="border:1px solid lightgray;border-top:none;" class="mandTr">',
											'<td><img src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ev.fid,'" style="width: 87px; height:87px; padding-top:2px; padding-bottom:2px;" />&nbsp;<input type="button" value="" class="downLoad" onclick="downLoad(\''+ev.fid+'\',\'pdinfo\');"/></td>',
											'<td>',ev.fname,'</td>',
											'<td>',ev.fdescription,'</td>',
											'<td id="status" class='+ev.fid+'>',ev.fstate,'</td>',
											'<td>',
												'<span class="phone">',ev.flinkphone,'</span>',
												'<span id='+editbtn+' class="updateDetail" onclick="edit_mandpackage(\''+ev.fid+'\');" >补充</span>',
												'<span id='+authorbtn+' class="author">设计师:',ev.flinkman,'</span>',
												'<span id='+qqbtn+' class="linkQQ">',
													'<a  target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin='+ev.fqq+'&site=qq&menu=yes">',
														'<img border="0" height="25px" src="${ctx}/css/images/QQ.png" alt="点击这里给我发消息" title="点击这里给我发消息" />',
													'</a>',
												'</span>',
										   '</td>',
									'</tr>'].join("");
							$(html).appendTo(".mandPackageTable");
						});
						//子页面设置父级iframe高度
						window.getHtmlLoadingAfterHeight();
						$("#mand_ccg_count").val("   草稿箱("+response.countCcg+")");
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager3', //divID
							click : function(n){
								window.getMandPackageTable(n);
								this.selectPage(n);
							},
							pno : response.pageNo,//当前页码
							total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
							totalRecords : response.totalRecords,//总数据条数
							lang : {
								prePageText : '上一页',
								nextPageText : '下一页',
								totalPageBeforeText : '共',
								totalPageAfterText : '页',
								totalRecordsAfterText : '条数据',
								gopageBeforeText : '转到',
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
						$(".mandPackageTable tr #status").each(function(i) {
			                var t = $(this).text();
			                var tdId = $(this).attr("class");
			                if(t=='未接收' || t=='已分配'){
			                	$(this).text('未接收');
			                	$("#"+tdId+"_qq").remove();
			                	$("#"+tdId+"_author").remove();
			                }else if(t=='已设计' || t=='已接收' || t=='关闭' || t=='已完成' || t=='已生成要货'){
			                	$(this).text('已接收');
			                	$("#"+tdId+"_del").remove();
			                	$("#"+tdId+"_edit").remove();
			                }
            		});
					}
					$('input:visible._submit').unbind('click');
					$('input:visible._submit').click(function(){
						getMandPackageTable(1);
					}).attr('onclick','');
					parent.layer.close(loadIndex); 
				}
		});
}

//需求包详情
function packageDetail (obj,isCcg){
	localStorage.designList_toDraft = isCcg;
	var queryHistory=$("#supplier4_hidden").val()=="三月前数据"?"history":null;
	location.href="${ctx}/firstproductdemand/packageDetail.net?id="+obj+"&queryHistory="+queryHistory;
}

//点击草稿箱，加载需求包列表信息
function getMandPackage_ccg(page){
		var obj =$(".mandPackageForm").serialize();
		var loadIndex = parent.layer.load(2);
		$("#kkpager3").html("");
		$.ajax({
				type : "POST",
				url : "${ctx}/firstproductdemand/loadpackageccg.net?pageNo="+page,
				dataType:"json",
				async: false,
				data:obj,
				success : function(response) {
					$(".mandTr").remove();
					if(response.list.length>0){
						$.each(response.list, function(i, ev) {
							var html =[
							           '<tr height="10" class="mandTr">',
											'<td colspan="5"></td>',
									   '</tr>',
									   '<tr height="30" style="background-color:#F0F0F0;" align="left" class="mandTr">',
										   '<td colspan="5">&nbsp;&nbsp;',
										      '<span style="display:inline-table;width:175px;">制造商：',ev.supplierName,'</span>',
										      '<span style="display:inline-table;width:180px;">订单编号：',ev.fnumber,'</span>',
										      '<span style="display:inline-table;width:530px;">交付时间：',ev.farrivetimeString,'</span>',
										      '<span style="display:inline-table;width:90px;"><a href="javascript:void(0);"  onclick="packageDetail(\''+ev.fid+'\',false);"   class="xq">详情</a></span>',
										      '<input type="button" value="" onclick="del_mandpackage_cgx(\''+ev.fid+'\');" style="display:inline-table;width:25px;height:25px;border:none;background:url(${ctx}/css/images/iconfont-lajitong9.png) 0px 0px no-repeat;cursor:pointer;" title="删除"/>',
										  '</td>',
									   '</tr>',
										'<tr height="125" style="border:1px solid lightgray;border-top:none;" class="mandTr">',
										'<td><img src="${ctx}/productfile/getFileSourceByParentId.net?fid=',ev.fid,'" style="width: 87px; height:87px; padding-top:2px; padding-bottom:2px;" />&nbsp;<input type="button" value="" class="downLoad" onclick="downLoad(\''+ev.fid+'\',\'pdinfo\');"/></td>',
											'<td>',ev.fname,'</td>',
											'<td>',ev.fdescription,'</td>',
											'<td>',ev.fstate,'</td>',
											'<td>',
												'<span class="phone">',ev.flinkphone,'</span>',
												'<span class="updateDetail" onclick="edit_mandpackage(\''+ev.fid+'\');">补充</span>',
												'<span class="author">设计师:',ev.flinkman,'</span>',
												'<span class="linkQQ">',
													'<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=1718733793&site=qq&menu=yes">',
														'<img border="0" height="25px" src="${ctx}/css/images/QQ.png" alt="点击这里给我发消息" title="点击这里给我发消息" />',
													'</a>',
												'</span>',
										   '</td>',
									'</tr>'].join("");
							$(html).appendTo(".mandPackageTable");
							if(ev.fstate=='存草稿'){
								$(".author").remove();		
						    	$(".linkQQ").remove();
						    }
						});
						//子页面设置父级iframe高度
						window.getHtmlLoadingAfterHeight();
						$("#mand_ccg_count").val("   草稿箱("+response.countCcg+")");
						/********************************************循环添加TR结束******************************/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							pagerid : 'kkpager3', //divID
							click : function(n){
								window.getMandPackageTable(n);
								this.selectPage(n);
							},
							pno : response.pageNo,//当前页码
							total : Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize)),//总页码
							totalRecords : response.totalRecords,//总数据条数
							lang : {
								prePageText : '上一页',
								nextPageText : '下一页',
								totalPageBeforeText : '共',
								totalPageAfterText : '页',
								totalRecordsAfterText : '条数据',
								gopageBeforeText : '转到',
								gopageButtonOkText : '确定',
								gopageAfterText : '页',
								buttonTipBeforeText : '第',
								buttonTipAfterText : '页'
							}
					    });
						/********************************************渲染分页主键结束******************************/
					}
					$('input:visible._submit').unbind('click');
					$('input:visible._submit').click(function(){
						getMandPackage_ccg(1);
					}).attr('onclick','');
					
					parent.layer.close(loadIndex); 
				}
		});
}

function valRequire(){
	if($("#flinkphone").val()!=''){
		var reg=/^1[3|4|5|8][0-9]{9}$/;
		if(!(reg.test($("#flinkphone").val()))){
			layer.tips('输入的手机格式错误！', '#flinkphone', {tips: [1, '#F7874E'],time: 4000});
			return false;
		}
	}
   if($(".requireName").val()==""){
   		layer.tips('需求名称不能为空！', '#fname', {
			    tips: [1, '#F7874E'],
			    time: 4000
		});
		return false;
   }else if($("._date").val()==""){
   		layer.tips('交货日期不能为空！', '#farrivetime', {
			    tips: [1, '#F7874E'],
			    time: 4000
		});
		return false;
   }else{
   		return true;
   }
}

$(document).ready(function(e) {
	//初始化iframe高度
	window.getHtmlLoadingBeforeHeight();
	$("#flinkphone").focusout(function(){
		  	if($("#flinkphone").val()!=''){
				var reg=/^1[3|4|5|8][0-9]{9}$/;
				if(!(reg.test($("#flinkphone").val()))){
					layer.tips('输入的手机格式错误！', '#flinkphone', {tips: [1, '#F7874E'],time: 4000});
				}
			}
     });
	/*选项卡*/
	var $li = $("#tab_list li");
	$("#tab_list li").click(function(){
		var $this = $(this);
		var $t = $this.index();
		$li.removeClass();
		$this.addClass("current");
		$("#div0").css('display','none');
		$("#div1").css('display','none');
		$("#div2").css('display','none');
		$("#div3").css('display','none');
		$("#div"+$t).css('display','block');
		if($t==0){
		   window.getQuickOrderTable(1);
		}else if($t==1){
		   window.getHtmlLoadingAfterHeight();
		   if(!WebUploader.Uploader.support()){
				parent.layer.open({
				    type: 1,
				    area: ['400px', '100px'],
				    closeBtn: 0,
				    title: false, //不显示标题
				    content: '<div style="width:397px;height:97px;border:1px solid #ccc;overflow:hidden;background-color:#eee;position:relative;display:table-cell;text-align:center;vertical-align:middle;"><p style="position:static;+position:relative;top:50%;*top:40%;font-size:12px;margin-left:auto;margin-right:auto;">为了更好体验,请安装/升级Flash,现在<a style="color:blue;font-size:12px;" href="https://get.adobe.com/flashplayer/?loc=cn"  target="_blank">安装</a>?安装后请刷新页面！</p></div>'
				});
			}
			$("#supplier2").jqxComboBox({
				width: 200, 
				height: 25,
				animationType:'fade',
				autoDropDownHeight:true,//下拉框展开高度
				searchMode:'contains'//模糊搜索
			});
		   /***********************************上传****************开始*******************************/
		   $("#picker").html("添加附件");
		   $("#thelist").empty();
			var $list = $("#thelist");
			var fileId = null;
			var uploader = WebUploader.create({
					    swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
					    server: window.getBasePath()+'/productfilenol/uploadImg.net?fid='+$("#mandPackageId").val(),  // 文件接收服务端。
					    pick: '#picker',
					    fileSingleSizeLimit: 10485760,
					    auto : true //设置为 true后,不需要手动调用上传,有文件选择即开始上传
		    });
		    /*** 当文件被加入队列以后触发*/
			uploader.on( 'fileQueued', function( file ) {
			    $list.append( '<div id="' + file.id + '" class="item">'+
			    					'<p class="info">' + file.name + '<a id="' + file.id + '" href="javascript:void(0);"  class="delfile" >删除</a></p>'+
			    					'<p class="state">等待上传...</p>'+
			    			'</div>' );
			});
			/*** 上传过程中触发，携带上传进度*/
			uploader.on( 'uploadProgress', function( file, percentage ) {
		    	var $li = $( '#'+file.id ),
		        $percent = $li.find('.progress .progress-bar');
			    // 避免重复创建
			    if ( !$percent.length ) {
			        $percent = $('<div class="progress progress-striped active">' +
			          				'<div class="progress-bar" role="progressbar" style="width: 0%"></div>' +
			        			'</div>').appendTo( $li ).find('.progress-bar');
			    }
			    $li.find('p.state').text('上传中');
			    $percent.css( 'width', percentage * 100 + '%' );
			});
			/*** 当文件上传成功时触发*/
			uploader.on( 'uploadSuccess', function( file ,response) {
		    	$( '#'+file.id ).find('p.state').text('已上传');
		    	$( '#'+file.id ).find('.delfile').attr('id',response._raw);
		    	$( '#'+file.id ).attr("class","itemafter");
		    	$( '#'+file.id ).find('p').first().attr("class","infoafter");
		    	$('.infoafter').mouseover(function(){
			    	$(this).find('a').css('display','inline-block');
			    	var aId = $(this).find('a').attr('id');
			    	$('#'+aId).unbind().click( function () {
			    			$.ajax({
									type : "POST",
									url : "${ctx}/productfilenol/deleteImg.net",
									dataType:"text",
									async:false,
									data: {"fid":aId},  
									success : function(response) {
										if(response =="success"){
												var divId =$("#"+aId).parent("p").parent("div").attr('id');
												uploader.removeFile(divId,true);
												$("#"+divId).remove();
										}else{
											layer.alert('操作失败！', function(index){layer.close(index);});
										}
									},
									error:function (){
										layer.alert('操作失败！', function(index){layer.close(index);});
									}
							});
			    	});
			    });
			    $('.infoafter').mouseout(function(){
			    	$(this).find('a').css('display','none');
			    });
			    
			});
			
			/*** 不管成功或者失败，文件上传完成时触发*/
			uploader.on( 'uploadComplete', function( file ) {
			    $( '#'+file.id ).find('.progress').fadeOut();
			    $( '#'+file.id ).find('.state').fadeOut();
			});
			
			/*** 当validate不通过时，会以派送错误事件的形式通知调用者*/
			uploader.onError = function( type ) {
				if(type=='F_EXCEED_SIZE'){
				    layer.msg("单个文件不允许超过10M！");
				}
			};
			/***********************************上传****************结束*******************************/
		}else if($t==2){
		   window.getOrderRecordTable(1);
		}else if($t==3){
		   window.getMandPackageTable(1);
		}	

    });
	$(".forSubmit").keyup(function(e){
		if(e.keyCode==13){
			$(this).parent("form#searchForm").find("._submit")[0].click();
			return false;
		}
	});

	//针对下拉框的样式
	var $num1=$("#supplier1 option").size();
	$("#supplier1").selectlist({
		zIndex: $num1,
		width: 235,
		height: 25
	});
	
	$("#supplier1 li").click(function(){
    	var value =$(this).attr("data-value");
    	$("#supplier1").val(value);
		window.getQuickOrderTable(1);
	});
	
	
	
	
/* 	var $num2=$("#supplier2 option").size();
	$("#supplier2").selectlist({
		zIndex: $num2,
		width: 390,
		height: 30
	}); */
	
	
	
	
	
	
	var $num4=$("#supplier4 option").size();
	$("#supplier4").selectlist({
		zIndex: $num4,
		width: 235,
		height: 25
	});
//2016年3月18日14:02:05 需求包记录下拉框
// 	$("#supplier4 li").click(function(){
//     	var value =$(this).attr("data-value");
//     	$("#supplier4").val(value);
// 		window.getMandPackageTable(1);
// 	});
	$("#fstate2 a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");
    	var value =$(this).text();
    	$("#supplier4_hidden").val(value);
		window.getMandPackageTable(1);
	});
	
	var $num3=$("#fstate option").size();
	$("#fstate").selectlist({
		zIndex: $num3,
		width: 235,
		height: 25
	});	
// 2016-3-18 下拉框改为选项卡
// 	$("#fstate li").click(function(){
//     	var state = $(this).text();
// 		$("#fstate").val(state);
// 		if($("#searchButtonOrderRecord").attr("onclick")=="query_yh();"){
// 			window.getOrderRecordTable(1);
// 		}else if($("#searchButtonOrderRecord").attr("onclick")=="query_bh();"){
// 			window.getOrderRecordTable_BH(1);
// 		}
// 	});
	$("#fstate1 a").click(function(){
		$(this).addClass('active').siblings().removeClass("active");
    	var state = $(this).attr("state");
		$("#fstate_hidden").val(state);
		if($("#searchButtonOrderRecord").attr("onclick")=="query_yh();"){
			window.getOrderRecordTable(1);
		}else if($("#searchButtonOrderRecord").attr("onclick")=="query_bh();"){
			window.getOrderRecordTable_BH(1);
		}
	});


	if(localStorage.gotoPage){
		$(localStorage.gotoPage).click();
		localStorage.gotoPage = "";
	}else{
		//加载快速下单列表
		window.getQuickOrderTable(1);
	}
	$("#searchButtonQuickOrder").click(function() {
		window.getQuickOrderTable(1);
	});
	$("#pack_ccg").click(function(){
		$("input[name='isfb']").val("0");
		if(valRequire()==true){
			var options = {  
	                    url : "${ctx}/firstproductdemand/save.net?isfb=2",
	                    dataType:"json",
	                    type : "POST",
	                    success : function(msg) {
	                        if(msg.success == "success"){
	                        	parent.layer.alert('操作成功！', function(alIndex){
										parent.layer.close(alIndex);
										$("#fname").val("");
										$("#fpurordernumber ").val("");
										$("#flinkman").val("");
										$("#flinkphone").val("");
										$("#fdescription").val("");
										$("#file").val("");
										$("#fileName").html("");
										$("#mandPackageId").val(msg.mandPackageId);
										$("#tab_list li").removeClass();
										$("#mand_record").addClass("current");
										$("#div0").css('display','none');
										$("#div1").css('display','none');
										$("#div2").css('display','none');
										$("#div3").css('display','block');
										window.getMandPackage_ccg(1);
								});
	                        }else{
	                        	parent.layer.alert('操作失败！', function(alIndex){
										parent.layer.close(alIndex);
								});
	                        }
	                    },
	                    error:function(){
	                    	parent.layer.alert('操作失败！', function(alIndex){
										parent.layer.close(alIndex);
							});
	                    }
	            };  
	            $("#mandForm").ajaxSubmit(options);//绑定页面中form表单的id
		}
	});
	$("#pack_fb").click(function(){
		$("input[name='isfb']").val("1");
		if(valRequire()==true){
			var options = {  
	                    url : "${ctx}/firstproductdemand/save.net?isfb=1",
	                    dataType:"json",
	                    type : "POST",
	                    success : function(msg) {
	                        if(msg.success == "success"){
	                        	parent.layer.alert('操作成功！', function(alIndex){
										parent.layer.close(alIndex);
										$("#fname").val("");
										$("#fpurordernumber ").val("");
										$("#flinkman").val("");
										$("#flinkphone").val("");
										$("#fdescription").val("");
										$("#file").val("");
										$("#fileName").html("");
										$("#mandPackageId").val(msg.mandPackageId);
										$("#tab_list li").removeClass();
										$("#mand_record").addClass("current");
										$("#div0").css('display','none');
										$("#div1").css('display','none');
										$("#div2").css('display','none');
										$("#div3").css('display','block');
										window.getMandPackageTable(1);
								});
	                        }else{
	                        	parent.layer.alert('操作失败！', function(alIndex){
										parent.layer.close(alIndex);
								});
	                        }
	                    },
	                    error:function(){
	                    	parent.layer.alert('操作失败！', function(alIndex){
										parent.layer.close(alIndex);
							});
	                    }
	        };  
	        $("#mandForm").ajaxSubmit(options);//绑定页面中form表单的id
		}
	});
	
});

//2015-10-30 下载
function downLoad(obj,typeinfo){
	window.open("${ctx}/productfile/downProductdemandFiles.net?pfid="+obj+"&ftype="+typeinfo,"_blank");
}

function query_yh(){
	window.getOrderRecordTable(1);
}
function query_bh(){
	window.getOrderRecordTable_BH(1);
}
//需求包查询
/* function searchMand(){
	window.getMandPackageTable(1);
} */

/********************************************************************************/
	 


</script>
</head>

<body>
	<div id="nav">
    	<p class="frame_title">平台首页 &gt; 我的业务 &gt; 快速下单</p>
    	<ul id="tab_list">
        	<li class="current">快速下单</li>
            <li>需求包下单</li>
            <li>订单记录</li>
            <li id="mand_record">需求包记录</li>
        </ul>
        <!--第一个table:****************************************************************************快速下单***********************************************begin******************-->
        <div id="div0">
	        <table cellpadding="0" cellspacing="0" border="0" width="1045" class="quickOrderTable" id="tbl1">
	        	<tr align="left">
	            	<td colspan="6">
	                	<form  id="searchForm" method="post"  onsubmit="return false">
	                		<input type="hidden" id="fiscommon" name="custproductQuery.fiscommon" value="0"/>
	                        <select id="supplier1" name="custproductQuery.supplierId" >
	                            <c:forEach var="entry" items="${supplier}">
	                            <c:choose>
	                            <c:when test="${entry.fid=='39gW7X9mRcWoSwsNJhU12TfGffw='}">
	                            <option value="${entry.fid}" id="${entry.fid}" selected = "selected" >${entry.fname}</option>
	                            </c:when>
	                          <c:otherwise>
									<option value="${entry.fid}" id="${entry.fid}" >${entry.fname}</option>
								</c:otherwise>
								</c:choose>
								</c:forEach>
	                        </select>
	                        <input type="button" value="购物车" class="shopCar" onclick="pl_addCar_btn();"/>
	                        <input type="button" value="" class="_submit" id="searchButtonQuickOrder"/>
	                        <input type="text" class="forSubmit" id="searchKey" name="custproductQuery.searchKey"  placeholder="搜索"/>
	       				</form>
	                </td>
	            </tr>
	        	<tr height="30" style="background-color:#F0F0F0;">
	            	<td width="60"><input type="checkbox" id="product"/></td>
	                <td width="200">
	                	<p style="height:24px;line-height:24px;width:198px;border-left:1px solid lightgray;border-right:1px solid lightgray;">附件信息</p>
	                </td>
	                <td width="243">
	                	<p style="height:24px;line-height:24px;width:241px;border-right:1px solid lightgray;">名称</p>
	                </td>
	                <td width="160">规格</td>
	                <td width="190">
	                	<p style="height:24px;line-height:24px;width:188px;border-left:1px solid lightgray;border-right:1px solid lightgray;">材料</p>
	                </td>
	                <td>操作</td>
	            </tr>
	            <tr height="45">
	            	<td colspan="6">
	                	<a href="javascript:void(0);" class="settingCommon" onclick="pl_setCommon()">设为常用</a>
	                    <a href="javascript:void(0);" class="addCar" onclick="pl_addCar()">加入购物车</a>
	                    <a href="javascript:void(0);" id="cy_btn" class="common" onclick="pl_common(1)">常用</a>
	                    <a href="javascript:void(0);" id="qb_btn" class="allPro" onclick="pl_common(0)">全部</a>
	                </td>
	            </tr>
          </table>
          <div id="kkpager"></div>
       </div>
        <!--第一个table:****************************************************************************快速下单***********************************************end******************-->
        <!--第二个table***************************************************************************需求包下单*********************************************begin******************-->
        <div id="div1" style="display:none;">
	        <form id="mandForm" >
	        	<input type="hidden" id="fisdemandpackage" name="fisdemandpackage" value="1"/>
	        	<input type="hidden" id="isfb" name="isfb" value="1"/>
	        	<input type="hidden" id="mandPackageId" name="fid" value="${mandPackageId}"/>
		        <table cellpadding="0" cellspacing="0" border="0" width="1045" id="tbl2" style="float:left;margin-left:20px;width:1045px;">
		        	<tr height="84">
		            	<td colspan="3"><span style="font-size:20px;">订单需求信息</span></td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">需求名称:<span style="color:red;">*</span></td>
		                <td width="430"><input type="text" class="requireName" id="fname" name="fname"/></td>
		                <td width="515">&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">采购单号</td>
		                <td><input type="text" class="orderNum" id="fpurordernumber" name="fpurordernumber"/></td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">交货日期:<span style="color:red;">*</span></td>
		                <td>
		                	<input type="text" id="farrivetime"  name="farrivetime" class="_date" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${newday}'})" value="${foverdate}"/>
                            <a class="for_date"  onclick="WdatePicker({el:$dp.$('farrivetime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${newday}'})"><img  src="${ctx}/css/images/sj.png"/></a>
		                </td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">联系人</td>
		                <td><input type="text" style="width:200px;height:30px;line-height:30px;" id="flinkman" name="flinkman" value="${currentUser.fname}"/></td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">联系电话</td>
		                <td><input type="text" style="width:200px;height:30px;line-height:30px;" id="flinkphone" name="flinkphone" value="${currentUser.ftel}"/></td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">供应商:<span style="color:red;">*</span></td>
		                <td>
		                	<select id="supplier2" name="fsupplierid">
		                    	<c:forEach var="entry" items="${supplier}">
		                    	<c:choose>
	                            <c:when test="${entry.fid=='39gW7X9mRcWoSwsNJhU12TfGffw='}">
	                            <option value="${entry.fid}" id="${entry.fid}" selected = "selected" >${entry.fname}</option>
	                            </c:when>
	                          <c:otherwise>
									<option value="${entry.fid}" id="${entry.fid}" >${entry.fname}</option>
								</c:otherwise>
								</c:choose>
								</c:forEach>
		                   	</select>
		                </td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td colspan="3" height="58">说说您的具体要求:</td>
		            </tr>
		            <tr>
		            	<td colspan="3"><textarea class="demand" id="fdescription" name="fdescription" maxlength="2000"></textarea></td>
		            </tr>
		            <!--此行是上传插件-->
		            <tr>
						<td colspan="3">
								<div id="uploader" class="wu-example">
								    <div class="btns">
								    	<span style="font-family: 微软雅黑; font-size: 14px;">&nbsp;单个大小不超过10MB&nbsp;</span>
								        <div id="picker" >添加附件</div>
								    </div>
								    <div id="thelist" class="uploader-list">
								    </div>
								</div>
						</td>
		            </tr>
		            <tr height="100">
		            	<td colspan="3">
		                	<input type="button" value="存草稿" class="tbl2_ccg"  id="pack_ccg"/>
		                	<input type="button" value="提交" class="tbl2_submit" id="pack_fb"/>
		                </td>
		            </tr>
		        </table>
	        </form>
        </div>
        <!--第二个table***************************************************************************需求包下单*********************************************end******************-->
        <!--第三个table:**************************************************************************订单记录***********************************************begin****************-->
        <div id="div2" style="display:none;">
	        <table cellpadding="0" cellspacing="0" border="0" width="1045" id="tbl1" class="orderRecordTable">
<!-- 	        	<tr align="left"> -->
<!-- 	            	<td colspan="5"> -->
<!-- 	                	<form  id="searchForm" class="dlick_form"  onsubmit="return false"> -->
<!-- 	                         <select id="fstate" name="deliverapplyQuery.fstate"> -->
<!-- 	                            <option value="">全部</option> -->
<!-- 	                            <option value="0">未接收</option> -->
<!-- 	                            <option value="1">已接收</option> -->
<!-- 	                            <option value="4">已入库</option> -->
<!-- 	                            <option value="5">部分发货</option> -->
<!-- 	                            <option value="6">全部发货</option> -->
<!-- 	                        </select> -->
<!-- 	                        <a href="javascript:void(0);" class="common">全部</a> -->
<!-- 	                        <a href="javascript:void(0);" class="common" id="stocksing" onclick="dlick_goods(1)">备货</a> -->
<!-- 	                    	<a href="javascript:void(0);" class="allPro" id="demanding" onclick="dlick_goods(0)">要货</a> -->
<!-- 	                       	<span id="search"><input type="button" value="" class="_submit" id="searchButtonOrderRecord" onclick="query_yh();"/></span> -->
<!-- 	                        <input type="text" id="searchKey1" name="deliverapplyQuery.searchKey" class="forSubmit" placeholder="搜索"/> -->
<!-- 	       				</form> -->
<!-- 	                </td> -->

<!-- 	            </tr> -->

<!--2016年3月17日10:04:19  ht-->
<tr>
<td colspan="5">
<div class="require">
<table cellpadding="0" cellspacing="0" border="0" width="1055" id="tbl3_01" style="table-layout: fixed; ">    <tr align="left">
    <td colspan="5" style="border-bottom: 2px solid #f76350;">
     <p id="fstate1" class="fstate_tab">
      <a href="javascript: void(0)" class="active" state="">全部</a> 
      <a href="javascript: void(0)" state="0">未接收</a> 
      <a href="javascript: void(0)" state="1">已接收</a> 
      <a href="javascript: void(0)" state="4">已入库</a> 
      <a href="javascript: void(0)" state="5">部分发货</a>
      <a href="javascript: void(0)" state="6">全部发货</a>
      <a href="javascript: void(0)" state="100">三月前数据</a>
     </p>  
     
	 <div style="float:left;position: absolute;top:-15px;"><form  id="searchForm" class="dlick_form"  onsubmit="return false">
<!-- 	                         <select id="fstate" name="deliverapplyQuery.fstate"> -->
<!-- 	                            <option value="">全部</option> -->
<!-- 	                            <option value="0">未接收</option> -->
<!-- 	                            <option value="1">已接收</option> -->
<!-- 	                            <option value="4">已入库</option> -->
<!-- 	                            <option value="5">部分发货</option> -->
<!-- 	                            <option value="6">全部发货</option> -->
<!-- 	                        </select> -->
<!-- 	                        <a href="javascript:void(0);" class="common">全部</a> -->

	                        <a href="javascript:void(0);" class="common" id="stocksing" onclick="dlick_goods(1)">备货</a>
	                    	<a href="javascript:void(0);" class="allPro" id="demanding" onclick="dlick_goods(0)">要货</a>
	                    	<input type="hidden" id="fstate_hidden" name="deliverapplyQuery.fstate"/>
 	                       	<span id="search"><input type="button" value="" class="_submit" id="searchButtonOrderRecord" onclick="query_yh();"/></span>
	                        <input type="text" id="searchKey1" name="deliverapplyQuery.searchKey" class="forSubmit" placeholder="搜索"/>

	       				</form>
	       				</div>
    </td>
   </tr> 
</table>
</div>
</td>
</tr>
<!--2016年3月17日10:04:19  ht-->

	        	<tr height="30" style="background-color:#F0F0F0;">
	                <td width="187">
	                	附件信息
	                </td>
	                <td width="292">
	                	<p style="height:24px;line-height:24px;width:290px;border-left:1px solid lightgray;border-right:1px solid lightgray;">名称</p>
	                </td>
	                <td width="245">规格</td>
	                <td width="127">
	                	<p style="height:24px;line-height:24px;width:125px;border-left:1px solid lightgray;border-right:1px solid lightgray;">状态</p>
	                </td>
	                <td>操作</td>
	            </tr>
	            <!--内容开始三个tr是一条数据显示-->
	        </table>
	        <div id="kkpager2"></div>
       </div>
       <!--第三个table:**************************************************************************订单记录***********************************************end****************-->
       <!--第四个table:**************************************************************************需求包记录*********************************************begin**************-->
       <div id="div3" style="display:none;">
	        <table cellpadding="0" cellspacing="0" border="0" width="1045" id="tbl1" class="mandPackageTable">
<!-- 	        	<tr align="left"> -->
<!-- 	            	<td colspan="5"> -->
<!-- 	                	<form  id="searchForm" class="mandPackageForm"  onsubmit="return false"> -->
<!-- <!-- 	                        <select id="supplier4" name="firstproductdemandQuery.fstate"> --> 
<!-- <!-- 	                            <option>全部</option> --> 
<!-- <!-- 	                            <option>未接收</option> --> 
<!-- <!-- 	                            <option>已接收</option> --> 
<!-- <!-- 	                        </select> --> 
<!-- 	                        <input type="button" id="mand_ccg_count" value="&nbsp;&nbsp;草稿箱" class="tbl4_cgx" onclick="getMandPackage_ccg(1)"/> -->
<!-- 	                        <input type="button" value="" class="_submit" onclick="searchMand()"/> -->
<!-- 	                        <input type="text" id="searchKey3" name="firstproductdemandQuery.searchKey" class="forSubmit" placeholder="搜索"/> -->
<!-- 	       				</form> -->
<!-- 	                </td> -->
<!-- 	            </tr> -->
<!--2016年3月17日10:04:19  ht-->
<tr>
<td colspan="5">
<div class="require">
<table cellpadding="0" cellspacing="0" border="0" width="1055" id="tbl4_01" style="table-layout: fixed; ">    <tr align="left">
    <td colspan="5" style="border-bottom: 2px solid #f76350;">
     <p id="fstate2" class="fstate_tab">
      <a href="javascript: void(0)" class="active">全部</a> 
      <a href="javascript: void(0)">未接收</a> 
      <a href="javascript: void(0)">已接收</a> 
      <a href="javascript: void(0)">三月前数据</a> 
     </p>  
     <div style="float:left;position: absolute;top:-15px;">
     <form  id="searchForm" class="mandPackageForm"  onsubmit="return false">
<!-- 	                        <select id="supplier4" name="firstproductdemandQuery.fstate"> -->
<!-- 	                            <option>全部</option> -->
<!-- 	                            <option>未接收</option> -->
<!-- 	                            <option>已接收</option> -->
<!-- 	                        </select> -->
	                        <input type="button" id="mand_ccg_count" value="&nbsp;&nbsp;草稿箱" class="tbl4_cgx" onclick="getMandPackage_ccg(1)"/>
	                        
	                        <!--2016年3月18日14:01:24-->
	                        <input type="hidden" name="firstproductdemandQuery.fstate" id="supplier4_hidden" />
	                        <!--2016年3月18日14:01:24-->
	                        
	                        <input type="button" value="" class="_submit" onclick="searchMand()"/>
	                        <input type="text" id="searchKey3" name="firstproductdemandQuery.searchKey" class="forSubmit" placeholder="搜索"/>
	       				</form>
     </div>
    </td>
   </tr> 
</table>
</div>
</td>
</tr>
	        	<tr height="30" style="background-color:#F0F0F0;">
	            	<td width="181">附件</td>
	                <td width="260">
	                	<p style="height:24px;line-height:24px;width:258px;border-left:1px solid lightgray;border-right:1px solid lightgray;">名称</p>
	                </td>
	                <td width="268">描述 </td>
	                <td width="100">
	                	<p style="height:24px;line-height:24px;width:158px;border-left:1px solid lightgray;border-right:1px solid lightgray;">状态</p>
	                </td>
	                <td width="236">
	                	操作
	                </td>
	            </tr>
	            <!--内容开始三个tr是一条数据显示-->
	        </table>
 			<div id="kkpager3"></div>
	     </div>
  	</div>
<script type="text/javascript">
	//需求包下单的上午和下午的点击事件。
	$(".am").click(function(){
		$(".pm").removeClass("am_color");
		$(this).addClass("am_color");
	});
	$(".pm").click(function(){
		$(".am").removeClass("am_color");
		$(this).addClass("am_color");
	});
	//复选框的全选按钮
	var $numm;
	$("#product").click(function(){	
		if($(this).is(":checked")){
		$("input[name='product']").attr("checked",true);
		$numm=$("input[name='product']").length;
		}else{
		$("input[name='product']").attr("checked",false);
		}
	});
//   2015-10-29  事件移入页面加载中
// 	$("input[name='product']").click(function(){
// 		if($("input[name='product']").size()==$("input[name='product']:checked").size()){
// 			$("#product").attr("checked",true);
// 			return;
// 		}
// 		$("input[name='product']").each(function(index, e) {
// 	        if($(this).is(":checked")==false){
// 				$("#product").attr("checked",false);
// 					return;
// 			}
//    		});
// 	});

</script>
</body>
</html>