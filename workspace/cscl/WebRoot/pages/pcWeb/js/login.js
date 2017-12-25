$(document).ready(function(){
	$(".banner_in").height($(window).height())
	
	// 登录界面显示
	$("#lk_login").hide();
	$("#banner_bg").hide();
	$("#banner_bg").height($("#banner01 img").height());
	$("#login-btn").click(function(){
		if($("#lk_login").hide())
		{
			$("#lk_login").show();
			$("#banner_bg").show();
		}

	})
	$(".login-close").click(function(){
		$("#lk_login").hide();
		$("#banner_bg").hide();
	})
	//登录界面和二维码切换
	var lk_Sw = true;

	$("#lk_switch").click(function(){
		if(lk_Sw == true)
		{
			$(this).stop(true,true).animate({backgroundPositionX:"-260px",backgroundPositionY:"-260px"},200);
			$(".lk_next_login").css("display","none");
			$(".lk_code_login").css("display","block");
			lk_Sw = false;
			return false;
		}
		if(lk_Sw == false)
		{
			$(this).stop(true,true)
			.animate({backgroundPositionX:"-200px",backgroundPositionY:"-200px"},200);
			$(".lk_next_login").css("display","block");
			$(".lk_code_login").css("display","none");
			lk_Sw = true;
			return false;
		}
	})
	// 使用帮助图片切换
	$("#lk_help").hover(function(){
		$(".lk_code").css("display","none");
		$(".lk_help_pic").css({display:"block"});
		$(".lk_help_pic").stop(true,true).animate({right:"65px"},200);
	},function(){

		$(".lk_code").css({display:"block",zIndex:"10"});
		//$(".lk_code").css("z-index","10");
		$(".lk_help_pic").stop(true,true).animate({right:"20px"},200,function()
			{
				$(this).css("display","none");
			});
		
	})
	$(".lk_login_btn").hover(function(){

		$(this).css("background-color","#c10b15");
	},function(){
		$(this).css("background-color","#D80C18");
	});
})
//ie显示表单提示
$(document).ready(function(){
    var doc=document,inputs=doc.getElementsByTagName('input'),
    	supportPlaceholder='placeholder'in doc.createElement('input'),
    	placeholder=function(input)
    	{
    		var text=input.getAttribute('placeholder'),
    		defaultValue=input.defaultValue;
		    if(defaultValue=='')
		    {
		        input.value=text
		    }
		        input.onfocus=function()
		        {
		            if(input.value===text)
		            	{
		            		this.value=''
		            	}
		        };
		            input.onblur=function()
		            {
		            	if(input.value==='')
		            		{
		            			this.value=text
		            		}
		            }
	 	};
        if(!supportPlaceholder)
        {
            for(var i=0,len=inputs.length;i<len;i++)
            {
            	var input=inputs[i],text=input.getAttribute('placeholder');
            	if(input.type==='text'&&text)
            		{
            			placeholder(input)
           			}
	    	}	
		}
});