$(function(){
	getHtmlLoadingBeforeHeight();
	$('#queryState').val('');
//	djSelect('fstate',{
//		readonly:true,
//		afterSelect: function(){
//			loadData();
//		}
//	});
	//点击后tabButton取值
	$('#fstate1 a').click(function(){
		searchByState($(this));
	});
	// 状态查询
	function searchByState($this){
		state = $this.attr("fstate");
		$this.addClass('active').siblings().removeClass('active');
		$('#queryState').val(state);
		window.getHtmlLoadingBeforeHeight();
	    window.loadData(1,state);
	}

	/*//订单状态查询
	$('#fstate').on('selected',function(){
		loadData();
	});*/
	//输入框查询
	$('#searchText').on('keyup',function(e){
		if(e.which == 13){
			loadData();
		}
	});
	$('#searchButton').on('click',function(){
		loadData();
	});
	//时间查询
	$('#querytime').on('apply.daterangepicker',function(){
		loadData();
    }).daterangepicker({
		format: 'YYYY-MM-DD',
		showCustomBtn: true,
		separator : " 到 "
	});
		$("#excelBoard").on('click',function()
	{
	excelBoard();
	});
	loadData();
});

function carddetails($this)
{
	var queryHistory=$('#queryState').val();
	var id=$this.getAttribute("data-id");
	location.href="${ctx}/productplan/detail.net?id="+id+"&queryHistory="+queryHistory;
}

function loadData(pageNo,state){
	pageNo = pageNo || 1;
	var params = getParam(state);
	$("#kkpager").html("");
	$.ajax({
		url: ROOT_PATH+'/productplan/loadlist.net?pageNo='+pageNo,
		data: params,
		type: 'post',
		dataType: 'json',
		success: function(res){
			var list = res.list;
			var template = ['<tr class="order dynamicTR" >',
			         '	<td colspan="7" class="order_info">',
			         '		客户名称：<span>{{cname}}</span>采购订单号：<span>{{fordernumber}}</span>制造订单号：<span>{{fnumber}}</span>订单状态：<span>{{fstate}}</span>',
			         '	</td>',
			         '	<td class="order_detail">',
//			         '		<a href="'+ROOT_PATH+'/productplan/detail.net?id={fid}&queryHistory='+state+'">详情</a>',
			         '		<a href="javascript:" onclick="carddetails(this)" data-id="{{fid}}">详情</a>',
			         '	</td>',
			         '</tr>',
			         '<tr class="product dynamicTR" data-fid="{{fid}}">',
			         '	<td><input type="checkbox"  class="choose"/></td>',
			         '	<td>',
			         '		<img src="'+ROOT_PATH+'/productfile/getFileSourceByParentId.net?fid={{fcustproductid}}" alt="图片不存在" />',
			         '		<p class="material">{{fname}}<br/>{{fspec}}</p>',
			         '	</td>',
			         '	<td>',
			         '		{{farrivetime}}',
			         '	</td>',
			         '	<td>{{faddress}}</td>',
			         '	<td>入：{{fstockinqty}}<br/>出：{{fstockoutqty}}</td>',
			         '	<td>{{famount}}</td>',
			         '	<td>',
			         '		{{fdescription}}',
			         '	</td>',
			         '	<td class="buttonTR">',
			         '		{{action}}',
			         '	</td>',
			         '</tr>',
			         '<tr class="space dynamicTR"></tr>'];
			var html = getHtml(list,template,{		//定义在_common.js中
				fstate: function(name){
					name = parseInt(name);
					switch(name){
					case 0: return '未接收';
					case 1: return '已接收';
					case 2:;
					case 3: return '已入库';
					default: return '';
					}
				},
				action: function(_,data){
					var fstate = data.fstate,
						fstockinqty = data.fstockinqty,
						a1 = '<a href="javascript:void(0)" class="stockOpe" data-fid="'+data.fid+'">出入库</a>',
						a2 = '<a href="javascript:void(0);" class="receiveplan" data-fid="'+data.fid+'">接收</a>',
						a3 = '<a href="javascript:void(0);" class="unreceiveplan" data-fid="'+data.fid+'">取消接收</a>';
					if(fstockinqty!=0){
						return a1;
					}else if(fstate==0){
						return a2;
					}else{
						return a3+a1;
					}
				}
			});
			$('.dynamicTR').remove();
			if(html){
				$('#cardList').append(html);
				getPagination(res,'kkpager',loadData);
				initEvent();
				getHtmlLoadingAfterHeight();
			}
		}
	});
}

function getParam(fstate){
	var ret = {},
		time = $.trim($('#querytime').val()),
		searchKey = $.trim($('#searchText').val());
//		fstate = $('#fstate').data('djselect').value();
	if(time){
		ret['productplanQuery.timeQuantum'] = time;
	}
	if(searchKey){
		ret['productplanQuery.searchKey'] = searchKey;
	}
	if(fstate){
		ret['productplanQuery.fstate'] = fstate;
	}
	return ret;
}

function initEvent(){
	$('.product').click(function(){
		$check = $(this).find('.choose');
		$check.prop('checked',!$check.prop('checked'));
		$check.change();
	});
	addAllCheckEvent($('.all_check'),$('.choose'));
	$('.stockOpe').click(function(){
			parent.layer.open({
				title: ['订单出入库','font-size:14px;color:#fff;background:#d80c18;height:30px;line-height:30px;font-weight:bold;'],
				closeBtn:1,
				type: 2,
				anim:true,
				area: ['460px', '180px'],
				content: ROOT_PATH+'/productplan/createOrderInOut.net?id='+$(this).data('fid')
			});
	});
	$('.receiveplan,.unreceiveplan').click(function(e){
		var loadIndex = parent.layer.load(2);
		var obj = $(this).data('fid');
		var urlpath=ROOT_PATH+"/productplan/"+$(this).attr('class')+".net";
		$.ajax({
			url:urlpath,
			type:"post",
			dataType:"json",
			data:{id:obj},
			success:function(data){
				parent.layer.close(loadIndex); 
				if(data.success){
					   parent.layer.alert('操作成功！', function(index){
						loadData(1);
						parent.layer.close(index);
						});
				}else{
					parent.layer.alert('<div style="text-align:center">'+data.msg+'</div>', function(index){parent.layer.close(index);});
				}
			},
			error:function (){
				parent.layer.close(loadIndex); 
				parent.layer.alert('操作失败！', function(index){parent.layer.close(index);});
			}
		});
	});
}
function excelBoard(){
 	 $("#hidecardorderform").empty();
      $("#hidecardorderform").attr("action",ROOT_PATH+"/productplan/execplan.net?");  
        var html=[].join("");
       var data=getParam();
       	$('input:checkbox[class=choose]:checked').parents(".product.dynamicTR").each(function(i){
       		html+=['<input type="hidden" name="fid" value="',$(this).data("fid"),'"/>'].join("");
       	});
       $.each(data,function(field, value){
    	   html+=['<input type="hidden" name="',field,'" value="',value,'"/>'].join("");
      });
       $(html).appendTo("#hidecardorderform");
        //打开窗体，并post提交页面  
        $("#hidecardorderform").submit(); 
 }