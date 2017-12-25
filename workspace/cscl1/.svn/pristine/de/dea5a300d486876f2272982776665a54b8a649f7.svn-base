//判断银行卡归属及类别（储蓄卡/信用卡）
//function testcard(cardNumber){
////中国银行 储蓄卡
//var zgyh_cxk=[456351,601382];
////中国银行 信用卡
//var zgyh_xyk=[518378,518474,524864,524865,525745,525746,547766,558868,622752,622753,622755,622756,622757,622758,622759,622761,622762,622763,552742,553131,356833,356835,409665,409666,409667,409668,409669,409670,409671,409672,438088,512315,512316,512411,512412,514957,514958,622760];
////工商银行 储蓄卡
//var gsyh_cxk=[622200,622202,622203,622208];
////工商银行 信用卡
//var gsyh_xyk=[622210,622211,622212,622213,622214,622215,622220,622225,370248,370249,489734,489735,489736,622230,622235,622240,622245,438126,524091,525498,451804,370246,370247,402791,427010,427018,427019,427020,427028,427029,427038,427039,427062,427064,438125,451804,451810,451810,458060,458060,458071,458071,510529,524047,530970,530980,530980,530990,548259,558360];
////农业银行 储蓄卡
//var nyyh_cxk=[622846,103000,622821,622822,622823,622824,622825,622826,622840,622844,622845,622847,622848];
////农业银行 信用卡
//var nyyh_xyk=[491020,491020,535910,403361,404117,519412,520082,552599,558730];
////建设银行 储蓄卡
//var jsyh_cxk=[436742,421349,434061,434062,524094,526410,552245,622280,622700];
////建设银行 信用卡
//var jsyh_xyk=[436728,453242,491031,532420,532430,544033,622725,622728,553242,436718,436745,436748,532450,532458,489592,622166,622168,436738,552801,558895];
////交通银行 储蓄卡
//var jtyh_cxk=[405512,601428,622258,622259,622260,622261,622254];
////交通银行 信用卡
//var jtyh_xyk=[491040,537830,622250,622251,622252,622253,458123,458124,520169,521899,552853];
////光大银行 储蓄卡
//var gdyh_cxk=[622660,303000,900300];
////光大银行 信用卡
//var gdyh_xyk=[356837,356838,356839,356840,406252,406254,425862,481699,486497,622650,622655,622658];
////招商银行 储蓄卡
//var zsyh_cxk=[690755,402658,410062,468203,512425,524011,622580,622588,622598];
////招商银行 信用卡
//var zsyh_xyk=[439188,439225,439227,518710,518718,545619,545623,545947,356885,356886,356887,356888,356889,356890,479228,479229,552534,552587,622575,622576,622577,622578,622579,622581,622582];
//
//var card=[zgyh_cxk,zgyh_xyk,gsyh_cxk,gsyh_xyk,nyyh_cxk,nyyh_xyk,jsyh_cxk,jsyh_xyk,jtyh_cxk,jtyh_xyk,gdyh_cxk,gdyh_xyk,zsyh_cxk,zsyh_xyk];
//var bank=["中国银行","中国工商银行","中国农业银行","中国建设银行","交通银行","中国光大银行","招商银行"];
//
//
//cardNumber=cardNumber.substring(0,6);
//for(var i=0;i<card.length;i++){
//	for(var j=0;j<card[i].length;j++){
//		if(cardNumber==card[i][j]){
//			if(i%2==0){
//				return bank[i/2]+"储蓄卡";
//			}else{
//				return bank[parseInt(i/2)]+"信用卡";
//			}
//		}
//	}
//}
//}

//验证信用卡卡号是否合法，返回银行名称
//function testcredit(num){
//	if(num.length<12||num.length>19){	//卡号为12-19位
//		if(num.length==0){return;}	//长度为0时不提示“格式错误”
//		return false;
//	}
//	if(num.length>=12&&num.length<19){	//信用卡的luhn算法
//		var num_array=num.split("");
//		for(var i=num_array.length-2;i>=0;i=i-2){	//从卡号最后一位数字开始,偶数位乘以2,如果乘以2的结果是两位数，将结果减去9
//			if(num_array[i]*2<10){
//				num_array[i]=num_array[i]*2;
//			}else{
//				num_array[i]=num_array[i]*2-9;
//			}
//		}
//		var num_add=0;
//		for(var j=0;j<num_array.length;j++){	//求和
//			num_add+=parseInt(num_array[j]);
//		}
//		if(num_add%10!=0){	//若信用卡合法，总和可以被10整除
//			return false;
//		}
//	}
//	var cardbank=testcard(num);
//	//获取银行卡归属
//	if(cardbank.length==7){
//		cardbank=cardbank.substring(0,4);
//	}else if(cardbank.length==9){
//		cardbank=cardbank.substring(0,6);
//	}
//	return cardbank;
//}

//银行卡图标
function card_img(cardbank){
//	if(cardbank=="中国银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/zgyh_bank.png";}
//	else if(cardbank=="中国工商银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/gsyh_bank.png";}
//	else if(cardbank=="中国农业银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/nyyh_bank.png";}
//	else if(cardbank=="中国建设银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/jsyh_bank.png";}
//	else if(cardbank=="交通银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/jtyh_bank.png";}
//	else if(cardbank=="中国光大银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/gdyh.png";}
//	else if(cardbank=="招商银行"){return getBasePath()+"/pages/pcWeb/css/images/wallet/zs_bank.png";}
	
	switch(cardbank){
	case "渤海银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/bh_bank.png";break;
	case "北京银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/bj_bank.png";break;
	case "中国光大银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/gd_bank.png";break;
	case "广东发展银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/gf_bank.png";break;
	case "工商银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/gs_bank.png";break;
	case "华夏":return getBasePath()+"/pages/pcWeb/css/images/wallet/hx_bank.png";break;
	case "华夏银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/hx_bank.png";break;
	case "建设银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/js_bank.png";break;
	case "中国建设银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/js_bank.png";break;
	case "交通银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/jt_bank.png";break;
	case "民生银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/ms_bank.png";break;
	case "中国民生银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/ms_bank.png";break;
	case "宁波市商业银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/nb_bank.png";break;
	case "南京市商业银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/nj_bank.png";break;
	case "农业银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/ny_bank.png";break;
	case "平安银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/pa_bank.png";break;
	case "上海浦东发展银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/pf_bank.png";break;
	case "温州商业银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/wz_bank.png";break;
	case "兴业银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/xy_bank.png";break;
	case "国家邮政局":return getBasePath()+"/pages/pcWeb/css/images/wallet/yzx_bank.png";break;
	case "中国银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/zg_bank.png";break;
	case "浙江农商银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/zjns_bank.png";break;
	case "招商银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/zs_bank.png";break;
	case "中信实业银行":return getBasePath()+"/pages/pcWeb/css/images/wallet/zx_bank.png";break;
	case "中信实业银行信用卡中心":return getBasePath()+"/pages/pcWeb/css/images/wallet/zx_bank.png";break;
	default:break;							
	} 
}


