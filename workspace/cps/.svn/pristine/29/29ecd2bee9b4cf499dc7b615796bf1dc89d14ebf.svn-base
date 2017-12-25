function aclick(){ 		
	$(".top_b li a").on({
		click : function(){
			$(".type").text($(this).html());
			$("#ftype").val($(this).html());
			$(".searchKey").val("");//清空搜索框
			loadlist(1);

		}
	});
}	
//根据li个数改变list高度
function setulheight(){
	var lilen=$("#Productlist li").length; 
	if(lilen<=4){
		var liheight=$(window).height()-$("#head").height()-$("#foot").height()-$(".top").height()-$("#kkpager").height();//防止页面过短
		$(".list").height(liheight);	 				
	}
	if(lilen>4&&lilen<=8){
		$(".list").height(720);	 				
	}
	if(lilen>8&&lilen<=12){
		$(".list").height(1080);	 				
	}
	if(lilen>12&&lilen<=16){
		$(".list").height(1440);	 				
	}
	
}

function loadlist(page){
	var obj=$("#searchForm").serialize();
	$('#Productlist').empty(); 
	$.ajax({ 
		type : "POST",
		url : ROOT_PATH+"/designschemes/loadTypeSchemes.net?pageNo="+page+"&&pageSize=16",
		data :obj,
		dataType : "json",
		async : false,
		success : function(response) {
			$('#procount').text("（"+response.totalRecords+"个产品）");
			var list = response.list;

			var template = ['<li id={{fid}} onclick="ViewDetail(this)">',
			                '<div class="libox">',
			                '<div class="litop">',
			                '<img src="{{fshowpic}}" />',			             
			                '<p>{{fname}}</p></div>',
			                '<div><div class="libottom">',
			                '<div class="lileft"><img src="/cps/pages/StarProduct/images/listlogo.png"/></div>',
			                '<div class="liright"><p><span class="suppliername">{{fsuppliername}}</span>:  <span>{{fcreatetime}}</span></p>',
			                '<p>发布在{{ftype}}</p>',
			                '</div></div></li>'].join("");
			var html = getHtml(list,template,{		//定义在_common.js中
				fcreatetime:function(name)
				{
					var thisDate = new Date();//当前日期
					var mTime=thisDate.getTime(); //当前日期转为毫秒	
					var proDate=new Date(parseFloat(name));//后台传递过来的时间转换为日期
					proDate.setDate(proDate.getDate()+1);
					var time1 = new Date(proDate.toLocaleDateString()).getTime();//第二天0点的毫秒数
					var a=Number(mTime-time1);//和获取的毫秒相减
					var thisDay=Math.ceil(a/1000/60/60/24);//计算成天数
					if(a<0)
					{
						return "今天";
					}
					else if(thisDay<8){
						return thisDay+"天前";
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
								case 'dd':
									return tf(t.getDate());
									break;
								}
							});
						};
						thisDay=format(Number(name),'yyyy-MM-dd');
						return thisDay;			
					}
				},

				fshowpic: function(_,data){
					return  ROOT_PATH+"/productfilenol/getSchemeFileByParentId.net?fid="+data.fid;
				} 
			});
			if(html){
				$('#Productlist').append(html);
				getPagination(response,'kkpager',loadlist,true);
			}
			setulheight();
		}
	});	
}

//查看详情
function ViewDetail(e){		
	id=$(e).attr('id');
	var url= ROOT_PATH+"/designschemes/ProductDetial.net";
	window.open(url+"?id="+id,"_blank");

}


