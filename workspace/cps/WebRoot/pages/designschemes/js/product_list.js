$(document).ready(function() {
	window.getHtmlLoadingBeforeHeight();//子页面加载前文档高度
	
	//页面加载时数据分页
    window.productList(1);
    window.getHtmlLoadingAfterHeight();//子页面加载后文档高度
	
});


//发布天数计算,超过7天则显示为年月日	
function getCurrentDay(){
	var thisDate = new Date();//当前日期
	var mTime=thisDate.getTime(); //当前日期转为毫秒
	var i=$(".dateNum").length;
	for(var j=0;j<i;j++)
	{
		var dateNum=$(".dateNum").eq(j).text();//后台传递过来的时间，为毫秒		
		var proDate=new Date(parseFloat(dateNum));//后台传递过来的时间转换为日期
		proDate.setDate(proDate.getDate()+1);
		var time1 = new Date(proDate.toLocaleDateString()).getTime();//第二天0点的毫秒数
		var a=Number(mTime-time1);//和获取的毫秒相减
		var thisDay=Math.ceil(a/1000/60/60/24);//计算成天数
		if(a<0)
		{
			$(".dateNum").eq(j).text("今天");
		}
		else if(thisDay<8){
			$(".dateNum").eq(j).text(thisDay+"天前");
		}
		else
		{
		var format = function(time, format){
			var t = new Date(time);
		var tf = function(m){return (m < 10 ? '0' : '') + m};
		return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a){
    	switch(a){
        	case 'yyyy':
            	return tf(t.getFullYear());
            break;
       		case 'MM':
            	return tf(t.getMonth() + 1);
            break;
      		case 'mm':
            	return tf(t.getMinutes());
            break;
        	case 'dd':
            	return tf(t.getDate());
            break;
        	case 'HH':
            	return tf(t.getHours());
            break;
        	case 'ss':
            	return tf(t.getSeconds());
            break;
    			}
			})
		}
		thisDay=format(Number(dateNum),'yyyy-MM-dd HH:mm:ss')
		$(".dateNum").eq(j).text(thisDay);			
	}
}
}



//分页函数
function productList(page){
	var obj =$("#searchForm").serialize();//搜索关键字
   		$.ajax({
				type : "POST",
				url : window.getBasePath()+"/designschemes/list.net?pageNo="+page+"&pageSize="+12,
				dataType:"json",
				data:obj,
				success : function(response) {
					$(".product_list ul li").remove();
					/*循环添加li开始*/
					  $.each(response.list, function(i, ev) {
	                    var html = '<li> \
                    	<div class="product_img"><a href="javascript:void(0)"><img src="'+ROOT_PATH+'/productfile/getSchemeFileByParentId.net?fid='+ev.fid+'"/></a><div class="masker" data-type="'+ev.fstate+'">该作品已下架！</div></div> \
						<div class="product_name">'+ev.fname+'</div> \
                        <div class="product_date"><span class="dateNum">'+ev.fcreatetime+'</span>　发布</div> \
                        <div class="product_option"> \
						<div class="product_edit"><img src="images/pro_edit.png"/><a href="javascript:void(0)" class="edit" onclick="proEdit(\''+ev.fid+'\');">编辑</a></div> \
						<div class="product_del"><img src="images/pro_del.png"/><a href="javascript:void(0)" onclick="proDelete(\''+ev.fid+ '\');">删除</a></div> \
						<div class="product_state"><a onclick="proState(\''+ev.fid+'\');" style="background-color:#e03d46;width:60px;text-align:center;font-size:15px;color:#fff;margin-left:12px;padding:5px 13px;border-radius:4px;" class="xj"></a> </div> \
						</div> \
						</li>';
	                    $(html).appendTo(".product_list ul");
	                    
	                    $(".product_list ul li").hover(function(){
	                		$(this).find(".product_state").show();
	                	},function(){
	                		$(this).find(".product_state").hide();
	                	});
	                    
						});
					  //下架,上架	
					  $(".masker").each(function(i) {//上下架的状态判断，0表示下架
						  var xj=$(this).parent().siblings().find(".xj");
						  if($(this).data("type")=="0"){
							  $(this).show();
							  xj.text("上架");
						  }
						  else{
							  xj.text("下架");
						  }
					  });
					  
					  	//发布天数计算
						getCurrentDay();
						window.getHtmlLoadingAfterHeight();
						/*循环添加li结束*/
						/*渲染分页主键开始*/
						kkpager.pno =response.pageNo;
						kkpager.total =Math.floor((response.totalRecords + response.pageSize -1) / (response.pageSize));
						kkpager.totalRecords =response.totalRecords;
						kkpager.generPageHtml({
							click : function(n){
									window.productList(n);
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
						/*渲染分页主键结束*/
				}
		});
}


//编辑产品
function proEdit(id){
	var url=window.getBasePath()+"/designschemes/loadEditInfo.net";
	window.location.href=url+"?fid="+id;
}

//删除产品
function proDelete(id){
	parent.layer.confirm('确认删除？', {
	    btn: ['是','否'] //按钮
	}, function(index){
		$.ajax({
			type : "POST",
			dataType:"json",
			url : window.getBasePath()+"/designschemes/delete.net?fid="+id,
			success : function(response) {
				if(response.success){
					parent.layer.msg(response.msg);
					productList(kkpager.pno);
					parent.layer.close(parent.layer.getFrameIndex(window.name));
				}else{
					layer.msg(response.msg);
				}
			}
		});
		parent.layer.close(index);
	}, function(){
	    
	});
}

//上架下架
function proState(id){
	$.ajax({
				type : "POST",
				dataType:"json",
				url : window.getBasePath()+"/designschemes/shelvesUpOff.net",
				data: {fid:id},
				success : function(response) {
					if(response.success){
						clickState();
						productList(kkpager.pno);
					}else{
						parent.layer.msg(response.msg);
					}
				}
		});
  
	}

//上下架样式；
function clickState(){
	$(".xj").live("click",function(element){
		var masker=$(this).parent().parent().siblings().find(".masker");
		masker.show();
		if($(this).data("type")=="0"){$(this).text("上架");}
		else{$(this).text("下架");}
		
	})
		
}