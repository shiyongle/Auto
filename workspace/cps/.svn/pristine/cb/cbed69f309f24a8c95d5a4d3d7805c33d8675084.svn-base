$(document).ready(function() {
	
//菜单点击事件
$('#myTab li a').each(function(i, e) {
    $(e).click(function(){
		$('#myTab li a').removeClass('on');
		$(e).addClass('on');
		
		$('#myTabContent div').hide();
		$('#myTabContent div').eq(i).fadeIn(500);
		})
});



	var l1, len1, l2, len2 ,l11;
	var n1, n2, u1, u2;

	var q1, qua1, q2, qua2;
	var nq1, nq2, uq1, uq2;
	var c1,cru1,c2,cru2;
 var nc1,nc2,uc1,uc2;
 
 var nb1,nb2,ub1,ub2;
 var b1,bru1,b2,bru2;
 
 var ns1,ns2,us1,us2;
 var s1,sta1,s2,sta2;
 
 var left_select,right_select,txt_left,txt_right;
 

	var l = "", lj;
	chooseshow1();
	chooseshow2();
	chooseshow3();
	chooseshow4();
	chooseshow5();
	
	//单位切换
$('.btn').each(function(i, e) {
			$(e).click(function(){
				left_select=$(this).parent().prev().find('select');
				right_select=$(this).parent().next().find('select');
				txt_left=left_select.val();
				txt_right=right_select.val();
				left_select.val(txt_right);
				right_select.val(txt_left);

				switch(i){
					case 0:chooseshow1();length(); break;
					case 1:chooseshow2();quality(); break;
					case 2:chooseshow3();crush(); break;
					case 3:chooseshow4();bursting(); break;
					case 4:chooseshow5();stab(); break;
					}
					
				})
            
        });
		

	
	
//长度
function chooseshow1() {
	n1 = document.getElementById("numbershow1"); // 获取label的文本
	n2 = document.getElementById("numbershow2");
	u1 = document.getElementById("unitshow1");
	u2 = document.getElementById("unitshow2");
	l1 = document.all.lengthunit1;
	len1 = l1.options[l1.selectedIndex].text;// 获得前一个单位
	l2 = document.all.lengthunit2;
	len2 = l2.options[l2.selectedIndex].text;// 获得后一个单位
	// 获得改变后的select 的option值
	$("#l1").bind("change", function() {
		chooseshow1();
	});
	$("#l2").bind("change", function() {
		chooseshow1();
	});
	$("#denghao").hide();

	$("#lnumber").keyup(function() {
		length();
	});
	$("#l1").bind("change", function() {
		length();
	});
	$("#l2").bind("change", function() {
		length();
	});

}
function length() {

	l = $("#lnumber").val();
	if (l != "") {
		lengthtrans();
		n1.innerText = l;
		n2.innerText = lj;
		$("#denghao").show();
		u1.innerText = len1;
		u2.innerText = len2;
		
	}
}
function lengthtrans() {
	if (len1 == "米(m)") {
		if (len2 == "米(m)") {
			lj = l;
		}
		if (len2 == "英寸(in)") {
			lj = l * 39.37;
		}
		if (len2 == "英尺(ft)") {
			lj = l * 39.37 / 12;
		}
	}
	if (len1 == "英寸(in)") {

		if (len2 == "英寸(in)") {
			lj = l;
		}
		if (len2 == "英尺(ft)") {
			lj = l / 12;
		}
		if (len2 == "米(m)") {
			lj = l / 39.37;
		}
	}
	if (len1 == "英尺(ft)") {

		if (len2 == "英尺(ft)") {
			lj = l;
		}
		if (len2 == "英寸(in)") {
			lj = l * 12;
		}
		if (len2 == "米(m)") {
			lj = l * 12 / 39.37;
		}
	}
	
	lj=subZeroAndDot(lj);
	

}
//质量、力
function chooseshow2() {
	nq1 = document.getElementById("numbershowq1"); // 获取label的文本
	nq2 = document.getElementById("numbershowq2");
	uq1 = document.getElementById("unitshowq1");
	uq2 = document.getElementById("unitshowq2");
	q1 = document.all.qualityunit1;
	qua1 = q1.options[q1.selectedIndex].text;// 获得前一个单位
	q2 = document.all.qualityunit2;
	qua2 = q2.options[q2.selectedIndex].text;// 获得后一个单位
	// 获得改变后的select 的option值
	$("#q1").bind("change", function() {
		chooseshow2();
	});
	$("#q2").bind("change", function() {
		chooseshow2();
	});
	$("#denghaoq").hide();

	$("#qnumber").keyup(function() {
		quality();
	});
	$("#q1").bind("change", function() {
		quality();
	});
	$("#q2").bind("change", function() {
		quality();
	});
	

}
function quality() {
	
	l = $("#qnumber").val();
	if (l != "") {
		qualitytrans();
		nq1.innerText = l;
		nq2.innerText = lj;
		$("#denghaoq").show();
		uq1.innerText = qua1;
		uq2.innerText = qua2;
		
	}
}
function qualitytrans() {
	if (qua1 == "牛顿(N)") {
		if (qua2 == "千克(KG)") {
			lj = l / 9.8;

		}
		if (qua2 == "牛顿(N)") {
			lj = l;
		}
		if (qua2 == "磅(lb)") {
			lj = l * 0.225;
		}
	}
	if (qua1 == "千克(KG)") {
		if (qua2 == "千克(KG)") {
			lj = l;

		}
		if (qua2 == "牛顿(N)") {
			lj = l * 9.8;
		}
		if (qua2 == "磅(lb)") {
			lj = l * 9.8 * 0.225;
		}
	}
	if (qua1 == "磅(lb)") {
		if (qua2 == "千克(KG)") {
			lj = l / 0.225 / 9.8;

		}
		if (qua2 == "牛顿(N)") {
			lj = l / 0.225;
		}
		if (qua2 == "磅(lb)") {
			lj = l;
		}
	}
	
	lj=subZeroAndDot(lj);
	

}
//环压、边压强度单位
function chooseshow3() {
	nc1 = document.getElementById("numbershowc1"); // 获取label的文本
	nc2 = document.getElementById("numbershowc2");
	uc1 = document.getElementById("unitshowc1");
	uc2 = document.getElementById("unitshowc2");
	c1 = document.all.crushunit1;
	cru1 = c1.options[c1.selectedIndex].text;// 获得前一个单位
	c2 = document.all.crushunit2;
	cru2 = c2.options[c2.selectedIndex].text;// 获得后一个单位
	// 获得改变后的select 的option值
	$("#c1").bind("change", function() {
		chooseshow3();
	});
	$("#c2").bind("change", function() {
		chooseshow3();
	});
	$("#denghaoc").hide();

	$("#cnumber").keyup(function() {
		crush();
	});
	$("#c1").bind("change", function() {
		crush();
	});
	$("#c2").bind("change", function() {
		crush();
	});

}
function crush() {

	l = $("#cnumber").val();
	if (l != "") {
		crushtrans();
		nc1.innerText = l;
		nc2.innerText = lj;
		$("#denghaoc").show();
		uc1.innerText = cru1;
		uc2.innerText = cru2;

	}
}
function crushtrans() {
	if (cru1 == "千牛/米(KN/m)") {
		if (cru2 == "千牛/米(KN/m)") {
			lj = l;

		}
		if (cru2 == "磅/英寸(lbs/in)") {
			lj = l * 5.71;

		}

	}
	if (cru1 == "磅/英寸(lbs/in)") {
		if (cru2 == "千牛/米(KN/m)") {
			lj = l / 5.71;

		}
		if (cru2 == "磅/英寸(lbs/in)") {
			lj = l;

		}

	}
	
	lj=subZeroAndDot(lj);
	
}
//耐破强度
function chooseshow4() {
	nb1 = document.getElementById("numbershowb1"); // 获取label的文本
	nb2 = document.getElementById("numbershowb2");
	ub1 = document.getElementById("unitshowb1");
	ub2 = document.getElementById("unitshowb2");
	b1 = document.all.burstingunit1;
	bur1 = b1.options[b1.selectedIndex].text;// 获得前一个单位
	b2 = document.all.burstingunit2;
	bur2 = b2.options[b2.selectedIndex].text;// 获得后一个单位
	// 获得改变后的select 的option值
	$("#b1").bind("change", function() {
		chooseshow4();
	});
	$("#b2").bind("change", function() {
		chooseshow4();
	});
	$("#denghaob").hide();

	$("#bnumber").keyup(function() {
		bursting();
	});
	$("#b1").bind("change", function() {
		bursting();
	});
	$("#b2").bind("change", function() {
		bursting();
	});

}
function bursting() {

	l = $("#bnumber").val();
	if (l != "") {
		burstingtrans();
		nb1.innerText = l;
		nb2.innerText = lj;
		$("#denghaob").show();
		ub1.innerText = bur1;
		ub2.innerText = bur2;
	}
}
function burstingtrans() {
	if (bur1 == "千帕(Kpa)") {
		if (bur2 == "千帕(Kpa)") {
			lj = l;

		}
		if (bur2 == "千克力/平方厘米(kgf/cm²)") {
			lj = l * 0.0102;
		}
		if (bur2 == "磅/平方英寸(Lbf/in²)") {
			lj = l * 0.145;
		}
	}
	if (bur1 == "千克力/平方厘米(kgf/cm²)") {
		if (bur2 == "千帕(Kpa)") {
			lj = l * 98.1;

		}
		if (bur2 == "千克力/平方厘米(kgf/cm²)") {
			lj = l;
		}
		if (bur2 == "磅/平方英寸(Lbf/in²)") {
			lj = l * 98.1 * 0.145;
		}
	}
	if (bur1 == "磅/平方英寸(Lbf/in²)") {
		if (bur2 == "千帕(Kpa)") {
			lj = l * 6.89476;

		}
		if (bur2 == "千克力/平方厘米(kgf/cm²)") {
			lj = l * 6.89476 * 0.0102;
		}
		if (bur2 == "磅/平方英寸(Lbf/in²)") {
			lj = l;
		}
	}
	lj=subZeroAndDot(lj);

	
	

}
//戳创强度
function chooseshow5() {
	ns1 = document.getElementById("numbershows1"); // 获取label的文本
	ns2 = document.getElementById("numbershows2");
	us1 = document.getElementById("unitshows1");
	us2 = document.getElementById("unitshows2");
	s1 = document.all.stabunit1;
	sta1 = s1.options[s1.selectedIndex].text;// 获得前一个单位
	s2 = document.all.stabunit2;
	sta2 = s2.options[s2.selectedIndex].text;// 获得后一个单位
	// 获得改变后的select 的option值
	$("#s1").bind("change", function() {
		chooseshow5();
	});
	$("#s2").bind("change", function() {
		chooseshow5();
	});
	$("#denghaos").hide();

	$("#snumber").keyup(function() {
		stab();
	});
	$("#s1").bind("change", function() {
		stab();
	});
	$("#s2").bind("change", function() {
		stab();
	});

}
function stab() {

	l = $("#snumber").val();
	if (l != "") {
		stabtrans();
		ns1.innerText = l;
		ns2.innerText = lj;
		$("#denghaos").show();
		us1.innerText = sta1;
		us2.innerText = sta2;
	}
}
function stabtrans() {

	if (sta1 == "焦耳(J)") {
		if (sta2 == "焦耳(J)") {
			lj = l;

		}
		if (sta2 == "千克*米(kg*cm)") {
			lj = l * 0.102;
		}
		if (sta2 == "磅*英寸(LBS*INCH)") {
			lj = l * 8.85;
		}
	}
	if (sta1 == "千克*米(kg*cm)") {
		if (sta2 == "焦耳(J)") {
			lj = l / 0.102;

		}
		if (sta2 == "千克*米(kg*cm)") {
			lj = l;
		}
		if (sta2 == "磅*英寸(LBS*INCH)") {
			lj = l / 0.102 * 8.85;
		}
	}
	if (sta1 == "磅*英寸(LBS*INCH)") {
		if (sta2 == "焦耳(J)") {
			lj = l / 8.85;

		}
		if (sta2 == "千克*米(kg*cm)") {
			lj = l / 8.85 * 0.102;
		}
		if (sta2 == "磅*英寸(LBS*INCH)") {
			lj = l;
		}
	}
	
	lj=subZeroAndDot(lj);
	;
	
}

function subZeroAndDot(s){ 
	if(!(/^(\+|-)?\d+$/.test(s))){ //如果不是整数		  
		s =Number(s).toFixed(9); 
		
		var str = s.toString();
        if(str.toString().indexOf(".") > 0){    
        	 str =	str.replace(/(0+)$/,'');//去掉多余的0 
		      	        }    
        return str;    
    } 
	else{
		return s;
	}
}
	  

});