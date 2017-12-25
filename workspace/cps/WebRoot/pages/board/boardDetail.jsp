<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线设计</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/boardDetail.css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="${ctx}/js/onlineDesign.js" type="text/javascript"></script>
 <script type="text/javascript">

    $(document).ready(function(){
    	window.getHtmlBodyHeight();
    	parent.scrollTo(0,0);
    	$("span.boxmodel").each(function(i) {
    		 var t = $(this).text();
             switch(t)
             {
              case '1':$(this).text('普通'); break;
              case '2':$(this).text('全包'); break;
              case '3':$(this).text('半包'); break;
              case '4':$(this).text('有底无盖'); break;
              case '5':$(this).text('有盖无底'); break;
              case '6':$(this).text('围框'); break; 
              case '7':$(this).text('天地盖'); break; 
				 case '8':$(this).text('立体箱'); break;
               break;
              default:
             	 $(this).text('其它');
             }
	});
     getStates();
     if('${boardstate}'=='[]'){
    	 $('#main').css('paddingTop','0px');
    	 $('.retBtn').text('返回');
     }
     
     $("#fhstaveexp").text(
    		 formulaObj[getToken("#fhstaveexp","${boardinfo.fseries}",parseInt("${boardinfo.fboxmodel}"),"${boardinfo.fstavetype}")].join("")
    );
     $("#fvstaveexp").text(
    		 formulaObj[getToken("#vhstaveexp","${boardinfo.fseries}",parseInt("${boardinfo.fboxmodel}"),"${boardinfo.fstavetype}")].join("")
    );
     
     });
    
    //计算公式值
    function getToken(gongsi,fseries,fboxmodel,fstavetype){
		if(fstavetype=='不压线' || fboxmodel=='0'){
			return 0;
		}
		var x = gongsi==='#fhstaveexp'? 2 : 1,
			y = (x-1)* (fseries=='连做'? 2 : 1),
			z = (x-1)? (fboxmodel==1 || fboxmodel==7) ? fboxmodel : Math.min(2,fboxmodel)
					 : fboxmodel;
		return x*100+y*10+z;
	}
    //公式
    var formulaObj = {
    		0:[""],
    		221: ['${boardinfo.fhformula}','+长+宽+长+(宽-','${boardinfo.fhformula1}',')'],		/*横向连做普通箱型*/
    		211: ['${boardinfo.fhformula}','+长+(宽-','${boardinfo.fhformula1}',')'],				/*横向不连做普通箱型*/
    		222: ['${boardinfo.fhformula}','+长+宽+长+宽'],							/*横向连做剩余箱型*/
    		212: ['${boardinfo.fhformula}','+长+宽'],									/*横向不连做剩余箱型*/
    		227: ['高+长+高'],											/*横向连做天地盖箱型*/
    		/*纵向*/
    		101: ['(宽+','${boardinfo.fvformula}',')/2+高+(宽+','${boardinfo.fvformula}',')/2'],
    		102: ['(宽-','${boardinfo.fvformula}',')+高+(宽-','${boardinfo.fvformula}',')'],
    		103: ['宽+高+','${boardinfo.fvformula}'],
    		104: ['高+(宽+','${boardinfo.fvformula}',')/2'],
    		105: ['(宽+','${boardinfo.fvformula}',')/2+高'],
    		106: ['高'],
    		107: ['高+宽+高'],
    		108: ['${boardinfo.fdefine1}','+','${boardinfo.fdefine2}','+','${boardinfo.fdefine3}','+高+(宽+','${boardinfo.fvformula}',')/2']
    	};
    function getStates(){
 	   var fstate = "${boardinfo.fstate}";
 	   $("ul.status li").last().addClass("last");
		$("ul.status li").each(function(i,index){
			if($(this).data("fstate")<=fstate) $(this).addClass("done");
		});
    }
    function gobackOrderlist(){
    	history.back();
    }
 </script>  
</head>

<body >
	<div id="header">
    	  <p>
        	<a href="#">平台首页</a> &gt; <a href="#">我的业务</a> &gt; <a href="#">纸板订单</a> &gt; <a href="#">订单详情</a>
        </p>  
    </div>
    <div id="main">
    	<c:if test="${boardstate.size()!=0}">
	    	 <ul class="status">
	    	    <c:forEach  var="stateinfo" items="${boardstate}">
	    	    <li class="" data-fstate="${stateinfo.fstate}" ><em>${stateinfo.fstateValue}</em><span><fmt:formatDate value="${stateinfo.fcreatetime}" pattern="yyyy-MM-dd HH:mm"/></span> </li>
	             </c:forEach>
	    	 <!--    <li class="done"><em>提交订单</em><span></span> </li>
	            <li class="" ><em>未接收</em><span></span> </li>
	            <li class=""  ><em>未入库</em><span></span> </li>
	            <li class="last" ><em>未发货</em><span></span> </li> -->
	            <div class="clear"></div>
	        </ul> 
    	</c:if>
         <div class="content1">
        	<h3>订单信息</h3> 
        	
        	
            <div class="detail">
            <ul class="detailtopul">
            	<li class="detail_htop">制造商：<span>${boardinfo.suppliername}</span></li>
				<li class="detail_htop">订单编号：<span>${boardinfo.fnumber}</span></li>
				<li class="detail_last ">客户标签：<span>${boardinfo.flabel}</span></li>
            </ul >
             <ul class="detailul">
                <li class="detail_top">材　　料：<span>${boardinfo.fpdtname}</span></li>
                <li class="detail_top">箱　　型：<span class="boxmodel">${boardinfo.fboxmodel}</span></li>
                <li class="detail_top">压线方式：<span>${boardinfo.fstavetype}</span></li>
                <li class="detail_top">成型方式：<span>${boardinfo.fseries}</span></li>
				<li class="detail_top">纸箱规式：<span>${boardinfo.fboxlength}*${boardinfo.fboxwidth}*${boardinfo.fboxheight}</span></li>
                <li class="detail_top ">落料规格:<span><c:choose><c:when test="${boardinfo.fmateriallength!=null}">${boardinfo.fmateriallength}*${boardinfo.fmaterialwidth}</c:when><c:otherwise>0*0</c:otherwise></c:choose></span></li>
				<li class="detail_top" >横向公式：<span id="fhstaveexp"></span></li>
                <li class="detail_top " >纵向公式:<span id="fvstaveexp"></span></li>
                <li class="detail_top">横向压线：<span>${boardinfo.fhline}</span></li>
                <li class="detail_top ">纵向压线：<span>${boardinfo.fvline}</span></li>
				<li class="detail_top">配送数量：<span>${boardinfo.famount}只;${boardinfo.famountpiece}片</span></li>
                <li class="detail_top " >实际配送数量：<span><c:if test="${boardinfo.foutQty!=null}">${boardinfo.foutQty}片</c:if></span></li>
            </ul >
           </div>
        </div>
        <div class="content2">
        	<h3>收货人信息</h3>
            <p>交　　期：<span>${boardinfo.farrivetimeString}</span></p>
			<p>联 系 人：<span>${boardinfo.flinkman}</span></p>
			<p>联系电话：<span>${boardinfo.flinkphone}</span></p>
			<p>地　　址：<span>${boardinfo.faddress}</span></p>
            <p>备　　注：<span>${boardinfo.fdescription}</span></p>
        </div>
        <a href="javascript:void(0)" class="retBtn" onclick="gobackOrderlist()" >返回订单列表</a>
    </div> 
</body>
</html>
