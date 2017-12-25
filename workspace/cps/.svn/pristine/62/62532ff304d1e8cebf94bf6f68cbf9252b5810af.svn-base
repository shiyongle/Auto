$(document).ready(function() {	
	//发布日期、天数
	var mTime=$(".dateNum").text();
	getCurrentDay(mTime);
});

//发布天数计算,超过7天则显示为年月日	
function getCurrentDay(day){
	var thisDate = new Date();//当前日期
	var mTime=thisDate.getTime(); //当前日期转为毫秒
	var proDate=new Date(parseFloat(day));//后台传递过来的时间转换为日期
	proDate.setDate(proDate.getDate()+1);
	var time1 = new Date(proDate.toLocaleDateString()).getTime();//第二天0点的毫秒数
	var a=mTime-time1;//和传递进来的毫秒相减
	var thisDay=Math.ceil(a/1000/60/60/24);//计算成天数
	if(a<0)
	{
		$(".dateNum").text("今天");
	}
	else if(thisDay<8)
	{
		$(".dateNum").text(thisDay+"天前");
	}
	else
	{
		var format = function(time, format){
			var t = new Date(time);
			var tf = function(i)
			{
				return (i < 10 ? '0' : '') + i;
			};
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
			});
		};
		thisDay=format(Number(day),'yyyy-MM-dd HH:mm:ss');
		$(".dateNum").text(thisDay);
	}
}

