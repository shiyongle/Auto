<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线设计-列表</title>
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/orderDesign.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/kkpager.css"   />
<script type="text/javascript" language="javascript" src="${ctx}/js/_common.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxdropdownlist.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<script type="text/javascript">
function gridFirstproducteMandTable(page,isCcg){
		page = page || 1;
		var obj =$("#searchForm").serialize();
		var url = !isCcg? '${ctx}/firstproductdemand/load.net' : '${ctx}/firstproductdemand/loadccg.net';
		var loadDel = layer.load(2);
   		$.ajax({
				type : "POST",
				url : url + '?pageNo='+page,
				dataType:"json",
				data:obj,
				//async: false,
				success : function(response) {
					$(".alltr").remove();
				    var  template =[
				  		'<tr height="10" class="alltr">',
								'<td colspan="5"></td>',
						'</tr>',
						'<tr height="30" style="background-color:#F0F0F0;line-height:30px;" align="left" class="alltr">',
							'<td colspan="5">&nbsp;&nbsp;',
								'<span style="display:inline-table;width:210px;white-space: nowrap;">设计服务商：{{fdesignproviderid}}</span>',
								'<span style="display:inline-table;width:170px;">订单编号：{{fnumber}}</span>',
								'<span style="display:inline-table;width:190px;">提交时间：{{fauditortimeString}}</span>',
								'<span style="display:inline-table;width:120px;">设计师:{{flinkman}}</span>',
								'<span style="display:inline-table;width:190px;">设计师电话:{{flinkphone}}</span>',
								'<span style="display:inline-table;width:80px;"><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&amp;uin={{fqq}}&amp;site=qq&amp;menu=yes"><img border="0" height="25px" src="${ctx}/css/images/QQ.png" alt="点击这里给我发消息" title="点击这里给我发消息"></a></span>',
								'{{delAction}}',
							'</td>',
						'</tr>',
						'<tr height="125" style="border:1px solid lightgray;border-top:none;" class="alltr" data-fid="{{fid}}">',
							'<td><a href="javascript:void(0);"  onclick="desginDetail(\'{{fid}}\');"><img src="${ctx}/productfile/getFileSourceByParentId.net?fid={{fid}}" style="width: 144px; height:134px; padding-top:2px; padding-bottom:2px;" /></a>&nbsp;<input type="button" value="" class="downLoad"/></td>',
							'<td style="word-wrap:break-word;"><a href="javascript:void(0);" style="color:black;"  onclick="desginDetail(\'{{fid}}\');">{{fname}}</a></td>',
							'<td>{{fdescription}}</td>',
							'<td>{{fstate}}</td>',
							'<td>',
								'{{action}}',
							'</td>',
						'</tr>'].join("");
				    var html = getHtml(response.list,template,{
				    	fstate: function(fstate){
				    		if(fstate=="确认方案"){
				    			return "已设计";
				    		}else{
				    			return fstate;
				    		}
				    	},
						action: function(_,data){
							var fstate = data.fstate,
								fid = data.fid;
							var detailBtn = '<a href="javascript:void(0);"  onclick="desginDetail(\''+fid+'\');" class="ope_btn">需求详情</a>';
							var editBtn = '<a href="javascript:void(0);"  onclick="edit(\''+fid+'\');" class="ope_btn">'+(fstate=='存草稿'?'补 充':'修 改')+'</a>';
							var confirmBtn = '<a href="javascript:void(0);"  onclick="desginDetail(\''+fid+'\',1);" class="ope_btn">确认方案</a>';
							var payBtn = '<a href="javascript:void(0);"  onclick="finishFp(\''+fid+'\');" class="ope_btn">确认付款</a>';
							if(fstate=='未接收' || fstate=='已分配' || fstate=='存草稿'){
						    	return detailBtn + editBtn;
						    }else if(fstate == '已设计'){
						    	return detailBtn + confirmBtn;
						    }else if(fstate == '确认方案'){
						    	return detailBtn + payBtn;
						    }else{
						    	return detailBtn;
						    }
						},
						delAction: function(_,data){
							var fstate = data.fstate;
							if(fstate=='未接收' || fstate=='已分配'){
								return '<input type="button" value="" style="margin-top:8px;display:inline-table;float:right;width:25px;height:25px;border:none;background:url(${ctx}/css/images/iconfont-lajitong10.png) 0px 0px no-repeat;cursor:pointer;" title="删除" onclick="del(\''+data.fid+'\')"/>';
							}else if(fstate=='存草稿'){
								return '<input type="button" value="" style="margin-top:8px;display:inline-table;float:right;width:25px;height:25px;border:none;background:url(${ctx}/css/images/iconfont-lajitong10.png) 0px 0px no-repeat;cursor:pointer;" title="删除" onclick="del(\''+data.fid+'\',true)"/>';
							}else{
								return '';
							}
						}
					});
				    if(html){
						$(html).appendTo("#tbl1");
						getPagination(response,'kkpager',gridFirstproducteMandTable);
						initEvent();
						getHtmlLoadingAfterHeight();
						$("#ccg_num").val(response.countCcg);
					}
					getPagination2();
					layer.close(loadDel); 
				}
		});
}
function initEvent(){
	// 删除 del(fid)
}
/*** 完成需求*/
function finishFp(fid){
	$.ajax({
			type : "POST",
			url : "${ctx}/firstproductdemand/finishFp.net?id="+fid,
			dataType:"text",
			success : function(response) {
				if (response == "success") {
						parent.layer.alert('操作成功！', function(alIndex){
						parent.layer.close(alIndex);
						gridFirstproducteMandTable(1);
						/* parent.location.href="${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004"; */
				    });
				}else {
						parent.layer.alert(response, function(alIndex){
						parent.layer.close(alIndex);
				    });
				}
			}
    });
}
function getPagination2(){
	var prev = $('#page_top .prev'),
		next = $('#page_top .next');
	if(kkpager.totalRecords == 0){
		$('#page_top').hide();
	}else{
		$('#page_top').show();
		if(kkpager.pno==1){
			prev.addClass('disable');
		}else{
			prev.removeClass('disable');
		}
		if(kkpager.pno==kkpager.total){
			next.addClass('disable');
		}else{
			next.removeClass('disable');
		}
	}
	prev.click(function(){
		var me = $(this);
 		if(me.hasClass('disable')){
 			return;
 		}
	});
	$('#page_top a').click(function(){
 		var me = $(this);
 		if(me.hasClass('disable')){
 			return;
 		}
 		me.addClass('active').siblings().removeClass('active');
 	});
}
function initPageBtn(){
	var prev = $('#page_top .prev'),
		next = $('#page_top .next');
	prev.click(function(){
		var me = $(this);
 		if(me.hasClass('disable')){
 			return;
 		}else{
 			me.addClass('active');
 			next.removeClass('active disable');
 		}
 		if(kkpager.pno>1){
 			$('#kkpager .curr').prev().click();
 		}
 		if(kkpager.pno==1){
 			me.addClass('disable');
 			next.addClass('active');
 		}
	});
	next.click(function(){
		var me = $(this);
 		if(me.hasClass('disable')){
 			return;
 		}else{
 			me.addClass('active');
 			prev.removeClass('active disable');
 		}
 		if(kkpager.pno<kkpager.total){
 			$('#kkpager .curr').next().click();
 		}
 		if(kkpager.pno==kkpager.total){
 			me.addClass('disable');
 			prev.addClass('active');
 		}
	});
}

$(document).ready(function(e) {
	window.getHtmlLoadingBeforeHeight();
  	if(localStorage.gotoPage){//跳转
		$(localStorage.gotoPage).click();
		localStorage.gotoPage = ''; //跳转成功清空
	}
	window.gridFirstproducteMandTable(1);
 	$('#searchKey').keyup(function(e){
		if(e.keyCode==13){
			window.gridFirstproducteMandTable(1);
			return false;
		}
	});
 	$("#searchButton").click(function() {
		window.gridFirstproducteMandTable(1);
	});
 	$('#fstate a').click(function(){
 		searchByState($(this));
 	});
 	initPageBtn();
});

//删除
function del(obj,isCcg){
	parent.layer.confirm('确认删除?', function(index){
		$.ajax({
			type : "POST",
			url : "${ctx}/firstproductdemand/delete.net",
			data : {"fids":obj},
			success : function(response) {
				if (response == "success") {
					parent.layer.alert('操作成功！', function(alIndex){
                   		window.gridFirstproducteMandTable(1,isCcg);
						parent.layer.close(alIndex);
				    });
				}else {
					parent.layer.alert('操作失败！', function(alIndex){
						parent.layer.close(alIndex);
				    });
				}
			}
	});
		
	layer.close(index);
	});  
}
// 状态查询
function searchByState($this){
	state = $this.text();
	$this.addClass('active').siblings().removeClass('active');
	/* if(state=='确认方案'){
		state = '已设计';
	} */
	$('#queryState').val(state);
	window.getHtmlLoadingBeforeHeight();
    window.gridFirstproducteMandTable(1,state.indexOf('草稿箱')!=-1);
}
//2015-10-28 下载
function downLoad(obj){
	window.open("${ctx}/productfile/downProductdemandFiles.net?pfid="+obj+"&ftype=pdinfo","_blank");
}

//2015-10-28 补充
function edit(obj){
	window.open("${ctx}/lineDesign/_edit.net?id="+obj,"_blank");
} 
  
    
function new_desgin (){
	parent.location.href="${ctx}/lineDesign/_create.net";
}

function desginDetail (obj,confirm){
	var queryHistory=($('#queryState').val()=="三月前数据")?"history":null;
	location.href="${ctx}/firstproductdemand/detail.net?id="+obj+"&confirm="+confirm+"&queryHistory="+queryHistory;
}
</script>

</head>
<body>
	<div id="nav">
    	<p class="frame_title">平台首页 &gt; 我的业务 &gt; 在线设计</p>
        <table cellpadding="0" cellspacing="0" border="0" width="1045" id="tbl1" style="table-layout:fixed;">
        	<tr height="43" >
            	<td colspan="5" align="left">
            		<a href="javascript:void(0)" onclick="new_desgin();" id="ope_add"><span>+</span>发布需求</a>
                </td>
            </tr>
        	<tr align="left">
            	<td colspan="5" style="border-bottom:2px solid #f76350;">
            		<p id="fstate">
            			<a href="javascript: void(0)" class="active">全部</a>
            			<a href="javascript: void(0)">草稿箱<span id="ccg_num"></span></a>
            			<a href="javascript: void(0)">未接收</a>
            			<a href="javascript: void(0)">已接收</a>
            			<a href="javascript: void(0)">已设计</a>
            			<a href="javascript: void(0)">已完成</a>
      					<a href="javascript: void(0)">三月前数据</a>
            		</p>
                	<form action="#" method="post" id="searchForm" onsubmit="return false">
                        <input name="firstproductdemandQuery.fstate" type="hidden" id="queryState" value="全部"/>
                        <!-- <input type="button" id="ccg_count" value="&nbsp;&nbsp;草稿箱" class="tbl4_cgx" onclick="ccg_query(1);"/> -->
                        <input type="button" value="" class="_submit" id="searchButton"/>
                        <input type="text" class="forSubmit" id="searchKey" name="firstproductdemandQuery.searchKey" placeholder="搜索"/>
       				</form>
                </td>
            </tr>
        	<tr height="30" style="background-color:#F0F0F0;">
            	<td width="200">附件</td>
                <td width="240" style="word-wrap:break-word;">
                	<p style="height:24px;line-height:24px;width:238px;border-left:1px solid lightgray;border-right:1px solid lightgray;">名称</p>
                </td>
                <td width="236">需求描述</td>
                <td width="190">
                	<p style="height:24px;line-height:24px;width:188px;border-left:1px solid lightgray;border-right:1px solid lightgray;">状态</p>
                </td>
                <td width="179">
                	操作
                </td>
            </tr>
            <tr>
            	<td colspan="5">
            		<div id="page_top">
	            		<a href="javascript:void(0)" class="prev disable">上一页</a>
	            		<a href="javascript:void(0)" class="next active">下一页</a>
            		</div>
            	</td>
            </tr>
        </table>
	    <div id="kkpager"></div>
    </div>
</body>
</html>

