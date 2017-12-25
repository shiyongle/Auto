var isDrag = false;
function isIE() {
	if (navigator.userAgent.indexOf("MSIE") > 0) {
		return true;
	} else {
		return false;
	}
}
function addListener(element, e, fn) {
	
	if (isIE()) {
		element.attachEvent("on" + e, fn);
	} else {
		element.addEventListener(e, fn, false);
	}
}
function drag(e) {
	var e = e || window.event;
	var element = e.srcElement || e.target;
	if (e.preventDefault)
		e.preventDefault();
	else
		e.returnvalue = false;
	isDrag = true;
	
	var relLeft = e.clientX - parseInt(element.style.left);  //鼠标与图片位置的差值
	var relTop = e.clientY - parseInt(element.style.top);
	
	element.onmouseup = function() {
		isDrag = false;
		
	}
	document.onmousemove = function(e_move) {
		var e_move = e_move || window.event;
		if (isDrag) {
			element.style.left = e_move.clientX - relLeft + "px";  //移动后的图片位置
			element.style.top = e_move.clientY - relTop + "px";
			$("#XY").val("X:"+element.style.left+" Y:"+element.style.top);
			return false;
			
		}
		
	}
	
}
window.onload = function() {
	
	/*var element = document.getElementById("elimg");*/
	var element2 = document.getElementById("eldiv");
	/*addListener(element, "mousedown", drag);*/
	addListener(element2, "mousedown", drag);
}