$(document).ready(function() {
	//bootstrap引入后 layer的圆角样式失效
	/*var mylayer=parent.document.body.querySelector(".layui-layer-iframe .layui-layer-content");
	mylayer.setAttribute("style","border-radius:10px");*/
	
	var a, b, c, d;
	var i, j,k;
	var w="", r;
	var sd ;
	var sdt ;
	var arr = [ 
	            [ '0', '5', '0', '700', '250'], //'单瓦楞',
	            [ '5', '10', '701', '1000', '320' ], 
	            [ '10', '20', '1001', '1400', '360' ], 
	            [ '20', '30', '1401', '1750', '420' ], 
	            [ '30', '40', '1751', '2000', '500' ] 
	            ];
	var arrd = [ 
	             [ '0', '15', '0', '1000', '375' ], //'双瓦楞'
	             [ '15', '20', '1001', '1400', '450' ], 
	             [ '20', '30', '1401', '1750', '560' ], 
	             [ '30', '40', '1751', '2000', '640' ], 
	             [ '40', '55', '2001', '2500', '700' ] 
	             ];
	var cy=[
	        ['250','BS-1.1','S-1.1','650','3','BS-2.1','S-2.1','450','2'],
	        ['320','BS-1.2','S-1.2','800','3.5','BS-2.2','S-2.2','600','2.5'],//储运
	        ['360','BS-1.3','S-1.3','1000','4.5','BS-2.3','S-2.3','750','3'],
	        ['420','BS-1.4','S-1.4','1150','5.5','BS-2.4','S-2.4','850','3.5'],
	        ['500','BS-1.5','S-1.5','1500','6.5','BS-2.5','S-2.5','1000','4.5'],
	        ['375','BD-1.1','D-1.1','800','4.5','BD-2.1','D-2.1','600','2.8'],
	        [' 450','BD-1.2','D-1.2','1100','5','BD-2.2','D-2.2','800','3.2'],
	        [' 560','BD-1.3','D-1.3','1380','7','BD-2.3','D-2.3','1100','4.5'],
	        ['640','BD-1.4','D-1.4','1700','8','BD-2.4','D-2.4','1200','6'],
            ['700','BD-1.5','D-1.5','1900','9','BD-2.5','D-2.5','1300','6.5']
	        ];

	choose();
	function choose() {
		// 获得页面初始化时的select 的option值
		sd = document.all.choose;
		sdt = sd.options[sd.selectedIndex].text;// 获得瓦楞类型

		// 获得改变后的select 的option值
		$("#changechoose").bind("change", function() {
			choose();

		});

		$("#weight").keyup(function() {
			w = $('#weight').val();
			
			wchao();				
			ration();
			right();
			if(w==""){
				clean();
			}
		});
		$("#a").keyup(function() {
			a = $('#a').val();
			
			ration();
			right();
		});
		$("#b").keyup(function() {
			b = $('#b').val();
			ration();
			right();
		});
		$("#c").keyup(function() {
			c = $('#c').val();
			ration();
			right();
		});
		$("#changechoose").bind("change", function() {
			wchao();
			ration();
			right();
		});

	}
	function ration() {
		if (sdt == "单瓦楞") {
			for (i = 0; i < 5; i++) {
				var i0 = arr[i][0];
				var i1 = arr[i][1];
				if (parseFloat(w) > parseFloat(i0) && parseFloat(w) <= parseFloat(i1)) {
					for (j = i; j < 5; j++) {						
						var i4 = arr[j][4];			
							r = i4;
							$('#ration').val(r);
							return;
						}
					}
				}

			}
			

		if (sdt == "双瓦楞") {

			for (i = 0; i < 5; i++) {
				var i0 = arrd[i][0];
				var i1 = arrd[i][1];
				if (parseFloat(w) > parseFloat(i0) && parseFloat(w) <= parseFloat(i1)) {
					for (j = i; j < 5; j++) {
						var i4 = arrd[j][4];
						r = i4;
						$('#ration').val(r);
						return;
					}
				}
			}

		}
	}

	
	function right(){
		var ra=$('#ration').val();
		
		for(k=0;k<10;k++){
			var k0 = cy[k][0];
			if(parseFloat(k0)==parseFloat(ra)){
				var k1 = cy[k][1];
				var k2 = cy[k][2];			
				var k3 = cy[k][3];
				var k4 = cy[k][4];
				var k5 = cy[k][5];
				var k6 = cy[k][6];
				var k7 = cy[k][7];
				var k8 = cy[k][8];
				
				
				$('#el1').val(k1);
				$('#el2').val(k2);
				$('#el3').val(k3);
				$('#el4').val(k4);
				
				$('#yb1').val(k5);
				$('#yb2').val(k6);
				$('#yb3').val(k7);
				$('#yb4').val(k8);
				
			}
		}
	}
	function wchao() {
		if (sdt == "单瓦楞") {

			if (parseFloat(w) >= 40|| parseFloat(w)<=0) {

				$('#ration').val("参数超出范围");
				clean();
			}
		}
		if (sdt == "双瓦楞") {

			if (parseFloat(w) >= 55 || parseFloat(w)<=0) {
				$('#ration').val("参数超出范围");
				clean();

			}
		}
	}
	
	function clean(){
		$('#el1').val("");
		$('#el2').val("");
		$('#el3').val("");
		$('#el4').val("");
		
		$('#yb1').val("");
		$('#yb2').val("");
		$('#yb3').val("");
		$('#yb4').val("");
		
		
	}
	

});
