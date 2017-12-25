
function ScrollBox(Context,cScrollPic,sDot,pCur,nCur,cWidth,cHeight,itemWidth,speedTime,auto){
this.Context=[];
this.sDot={};
this.prevCur={};
this.nextCUr={};
this.cScrollPic={};
this.cWidth=800;
this.cHeight=400;
this.currentDot=2;
this._ready="ready";
this.scrollLength=0;
this.itemWidth=400;
this.itemHeight=200;
this.itemData=[];
this.itemData[5]=[];
this.itemData[3]=[];
this.speed=10;
this.tweenTime=30;
this.speedTime = 3000;
this.auto = false;
}
ScrollBox.prototype = {
//返回action后索引位置 参数依次是:当前索引位置,action,偏移量
funtureDot: function (currentNum, dir, spaceNum) {          //0,next,1
var funtureNode = 0;
if (dir == "prev") {                                        //向上翻
funtureNode = currentNum - spaceNum;
if (funtureNode < 0) {
funtureNode = funtureNode + this.scrollLength;
}
} else {                                                    //向下翻
funtureNode = currentNum + spaceNum;
if (funtureNode >= this.scrollLength) {
funtureNode = funtureNode - this.scrollLength;
}
}
return funtureNode;                                         //返回action后索引位置 参数依次是:当前索引位置,action,偏移量
},
//action后返回的值 参数依次是:原始位置,新旧位置差,action次数,允许action次数
tween: function (a, b, t, d) {
return a + b * t / d;
},
//changData重置图片的左边距,宽度,高度,上边距/下边距
changData: function () {
var spaceWidth = 0;
var spaceHeight = 0;
var spaceHeight2 = 0;
spaceWidth = this.cWidth - this.itemWidth;      //scrollBox-scrollPic 900-800=100
if (this.scrollLength == 3) {                   //当数组长度为3
spaceWidth = spaceWidth / 2;                    //获取scrollPic与scrollBox的边距    100/2=50
spaceHeight = this.itemHeight / 6 / 2;          //400/6/2=33        这里取6/1
} else if (this.scrollLength == 5) {            //当数组长度为5
spaceWidth = spaceWidth / 4;                    //获取scrollPic与scrollBox的边距    100/4=25
spaceHeight = this.itemHeight / 6 / 2;          //400/6/2=33        这里取6/1
spaceHeight2 = this.itemHeight / 3 / 2;         //400/3/2=66        这里取3/1
}
//参数依次为:左边距,宽度,高度,上边距/下边距
//PS:这里需要特别解释一下为什么下面会有2 / 3,5 / 6 建议:当看到这样变量反复操作的时候,最好反向逆推,算出结果,根据结果找规律,再回头看代码,你会发现豁然明了
//      先看上面:spaceHeight 取6/1
//               spaceHeight2 取3/1
//      那么图片所占的宽高就为:原始图片宽高的6/5,3/2
this.itemData[5] = [
{ dLeft: 0, dWidth: Math.floor(this.itemWidth * 2 / 3), dHeight: Math.floor(this.itemHeight * 2 / 3), dMarginTop: Math.floor(spaceHeight2), dZIndex: 1 }, 
{ dLeft: Math.floor(spaceWidth), dWidth: Math.floor(this.itemWidth * 5 / 6), dHeight: Math.floor(this.itemHeight * 5 / 6), dMarginTop: Math.floor(spaceHeight), dZIndex: 10 }, 
{ dLeft: Math.floor(spaceWidth * 2), dWidth: Math.floor(this.itemWidth), dHeight: Math.floor(this.itemHeight), dMarginTop: 0, dZIndex: 100 }, 
{ dLeft: Math.floor(this.itemWidth + spaceWidth * 3 - this.itemWidth * 5 / 6), dWidth: Math.floor(this.itemWidth * 5 / 6), dHeight: Math.floor(this.itemHeight * 5 / 6), dMarginTop: Math.floor(spaceHeight), dZIndex: 10 }, 
{ dLeft: Math.floor(this.cWidth - this.itemWidth * 2 / 3), dWidth: Math.floor(this.itemWidth * 2 / 3), dHeight: Math.floor(this.itemHeight * 2 / 3), dMarginTop: Math.floor(spaceHeight2), dZIndex: 1}
];
this.itemData[3] = [
{ dLeft: 0, dWidth: Math.floor(this.itemWidth * 5 / 6), dHeight: Math.floor(this.itemHeight * 5 / 6), dMarginTop: Math.floor(spaceHeight), dZIndex: 1 }, 
{ dLeft: Math.floor(spaceWidth), dWidth: Math.floor(this.itemWidth), dHeight: Math.floor(this.itemHeight), dMarginTop: 0, dZIndex: 10 }, 
{ dLeft: Math.floor(this.cWidth - this.itemWidth * 5 / 6), dWidth: Math.floor(this.itemWidth * 5 / 6), dHeight: Math.floor(this.itemHeight * 5 / 6), dMarginTop: Math.floor(spaceHeight), dZIndex: 1}
];
},
//selectClassName返回图片Div class等于className的项
selectClassName: function (o, className, tagName) {
var tag = o.getElementsByTagName("div");        //获取图片Div
var l = tag.length;                             //获取数组
var r = [];                                     //初始数组
for (var i = 0; i < l; i++) {                   //循环数组
var classTag = tag[i].className.split(" ");     //分割class数组
var ll = classTag.length;                       //获取class数组
for (var j = 0; j < ll; j++) {                  //循环数组
if (classTag[j] == className) {                 //如果循环项等于className,添加到数组中
r.push(tag[i]);
}
}
}
return r;                                       //返回图片Div class等于className的项
},
//当前索引位置数组
currentArr: function () {
var rArr = [];
var sPicBox = document.getElementById(this.cScrollPic);     //获取scrollPic
var sPicBoxItem = this.selectClassName(sPicBox, "item", "div"); //获取指定calss的图片Div
for (var i = 0; i < this.scrollLength; i++) {
for (var j = 0; j < this.scrollLength; j++) {
var tempLeft = sPicBoxItem[j].style.left;                       //图片Div左边距 如:0px
tempLeft = tempLeft.replace(/[^\d.]/g, '');                     //去除px 如:0px->0
if (this.itemData[this.scrollLength][i].dLeft == tempLeft) {    //左边距相等时,将j添加到数组中
rArr.push(j);
}
}
}
return rArr;                                                    //返回索引位置数组
},
//设置样式
whichDot: function (n) {
var m = n + 2;                                                  //移动后的索引都传进来了,这里还要取m值,不知何解?
if (n == 0) m = this.scrollLength - 1;
if (n == (this.scrollLength - 1)) m = 0;
if (n == (this.scrollLength - 2)) m = 1;
if (n == (this.scrollLength - 3)) m = 2;
var dot = document.getElementById(this.sDot);                   //获取scrollDot
var dotItem = dot.getElementsByTagName("span");                 //获取scrollDot下span
var splitBox = document.getElementById(this.cScrollPic);        //获取scrollPic
var split = this.selectClassName(splitBox, 'item', 'div');      //获取指定calss的图片Div
for (var i = 0; i < this.scrollLength; i++) {                   //循环数组
dotItem[i].className = "";                                      //初始样式
split[i].className = "item";                                    //初始样式
if (i == m) {
dotItem[i].className = "on";                                    //设置选中样式
split[i].className = "item current";                            //设置选中样式
}
}
m = null;                                                       //初始m值
},
//action 参数依次为:action,原始索引位置,新索引位置,当前项,偏移量,左边距差值,宽度差值,高度差值,上边距/下边距差值,z-index差值,原始左边距,原始宽度,原始高度,原始上边距/下边距,原始z-index
scrolling: function (dir, currentone, funtureNode, n, spaceNum, changeLeft, changeWidth, changeHeight, changeMarginTop, changeZIndex, tempLeft, tempWidth, tempHeight, tempMarginTop, tempZIndex) {
var flag = null;
if (flag) {         //flag!=null 强制清除定时器
clearTimeout(flag);
}
var sPicBox = document.getElementById(this.cScrollPic);     //获取scrollPic
var sPicBoxItem = this.selectClassName(sPicBox, "item", "div"); //获取指定calss的图片Div
var ctempLeft = ctempWidth = ctempHeight = ctempMarginTop = ctempZIndex = 0;
var _this = this;
var t = 0;
(function () {
ctempLeft = _this.tween(tempLeft, changeLeft, t, _this.speed);      //action后左边距
ctempWidth = _this.tween(tempWidth, changeWidth, t, _this.speed);   //action后宽度
ctempHeight = _this.tween(tempHeight, changeHeight, t, _this.speed);    //action后高度
ctempMarginTop = _this.tween(tempMarginTop, changeMarginTop, t, _this.speed);   //action后上/下边距
ctempZIndex = _this.tween(tempZIndex, changeZIndex, t, _this.speed);    //action后z-index
sPicBoxItem[n].style.cssText = "left:" + parseInt(ctempLeft) + "px;width:" + parseInt(ctempWidth) + "px;height:" + parseInt(ctempHeight) + "px;margin-top:" + parseInt(ctempMarginTop) + "px;z-index:" + parseInt(ctempZIndex); //设置图片Div样式
sPicBoxItem[n].getElementsByTagName("img")[0].style.cssText = "width:" + parseInt(ctempWidth) + "px;height:" + parseInt(ctempHeight) + "px";    //设置图片样式
t++;    //action操作次数
flag = setTimeout(arguments.callee, _this.tweenTime);   //定时任务
if (t > _this.speed) {      //action次数大于最大值
sPicBoxItem[n].style.cssText = "left:" + _this.itemData[_this.scrollLength][funtureNode].dLeft + "px;width:" + _this.itemData[_this.scrollLength][funtureNode].dWidth + "px;height:" + _this.itemData[_this.scrollLength][funtureNode].dHeight + "px;margin-top:" + _this.itemData[_this.scrollLength][funtureNode].dMarginTop + "px;z-index:" + _this.itemData[_this.scrollLength][funtureNode].dZIndex;
sPicBoxItem[n].getElementsByTagName("img")[0].style.cssText = "width:" + _this.itemData[_this.scrollLength][funtureNode].dWidth + "px;height:" + _this.itemData[_this.scrollLength][funtureNode].dHeight + "px";
t = 0;  //初始action次数
clearTimeout(flag); //清除计时器
flag = null;        //清除计时器对象
if (n == (_this.scrollLength - 1)) {
_this._ready = "ready";
}
}
})();
},
//action
act: function (current, dir) {
if (this._ready == "scrolling") {
return;
}
this._ready = "scrolling";
var rArr = this.currentArr();   //当前索引位置数组
var changeLeft = changeWidth = changeHeight = changeMarginTop = changeZIndex = 0;
var tempLeft = tempWidth = tempHeight = tempMarginTop = tempZIndex = 0;
var spaceNum = 1;
var tempCurrent = 0;
if (dir == "") {                                    //span点击事件 传入的current应该是span索引
var orgspaceNum = current - this.currentDot;
spaceNum = Math.abs(orgspaceNum);                   //获取绝对值
if (orgspaceNum > 0) {                              //3/4-2>0 向下翻
dir = "next";
} else if (orgspaceNum < 0) {                       //0/1-2<0 向上翻
dir = "prev";
}
}
for (var i = 0; i < this.scrollLength; i++) {               //循环数组
var funtureNode = this.funtureDot(i, dir, spaceNum);
changeLeft = this.itemData[this.scrollLength][funtureNode].dLeft - this.itemData[this.scrollLength][i].dLeft;       //左边距差值
changeWidth = this.itemData[this.scrollLength][funtureNode].dWidth - this.itemData[this.scrollLength][i].dWidth;    //宽度差值
changeHeight = this.itemData[this.scrollLength][funtureNode].dHeight - this.itemData[this.scrollLength][i].dHeight; //高度差值
changeMarginTop = this.itemData[this.scrollLength][funtureNode].dMarginTop - this.itemData[this.scrollLength][i].dMarginTop;    //上边距/下边距差值
changeZIndex = this.itemData[this.scrollLength][funtureNode].dZIndex - this.itemData[this.scrollLength][i].dZIndex; //z-index差值
tempLeft = this.itemData[this.scrollLength][i].dLeft;       //原始左边距
tempWidth = this.itemData[this.scrollLength][i].dWidth;     //原始宽度
tempHeight = this.itemData[this.scrollLength][i].dHeight;   //原始高度
tempMarginTop = this.itemData[this.scrollLength][i].dMarginTop; //原始上边距/下边距
tempZIndex = this.itemData[this.scrollLength][i].dZIndex;   //原始z-index
//参数依次为:action,原始索引位置,新索引位置,当前索引所在位置,偏移量,左边距差值,宽度差值,高度差值,上边距/下边距差值,z-index差值,原始左边距,原始宽度,原始高度,原始上边距/下边距,原始z-index
this.scrolling(dir, i, funtureNode, rArr[i], spaceNum, changeLeft, changeWidth, changeHeight, changeMarginTop, changeZIndex, tempLeft, tempWidth, tempHeight, tempMarginTop, tempZIndex);
}
tempCurrent = this.funtureDot(this.currentDot, dir, spaceNum);  //返回action后索引位置
this.whichDot(tempCurrent);     //设置样式
this.currentDot = tempCurrent;  //设置action后索引为当前索引
},
//loadAct初始化标签
loadAct: function () {
this.scrollLength = this.Context.length;        //当前数组长度
var sPicBox = document.getElementById(this.cScrollPic);     //存放图片Div
var elem = elemText = elemLink = elemImg = 0;
var sDotBox = document.getElementById(this.sDot);           //scrollDot
var dotElem = 0;
for (var i = 0; i < this.scrollLength; i++) {               //循环数组
//拼接Div
//<div class="item" style="left: 25px; width: 666px; height: 333px; margin-top: 33px;
//    z-index: 10;">
//    <a href="http://news.qq.com/zt2012/living/shuangfei.htm" target="_blank">
//        <img src="http://mat1.gtimg.com/news/huozhe/collection/shuangfei.jpg" style="width: 666px;
//            height: 333px;"></a><div class="iText">
//            </div>
//</div>
elem = document.createElement("div");                       //创建初始Div
elem.className = "item";                                    //class='item'
elemLink=document.createElement('a');                       //创建超链接
//elemLink.href=this.Context[i].href;                         //获取数组参数:{"img":"http://mat1.gtimg.com/news/huozhe/collection/12suimama.jpg","href":"http://news.qq.com/zt2012/living/12mother.htm"}
//elemLink.target="_blank";                                   //超链接打开方式
elem.appendChild(elemLink);                                 //将超链接添加到Div中
elemImg = document.createElement("img");                    //创建Img
elemImg.src = this.Context[i].img;                          //获取数组参数:{"img":"http://mat1.gtimg.com/news/huozhe/collection/12suimama.jpg","href":"http://news.qq.com/zt2012/living/12mother.htm"}
elemLink.appendChild(elemImg);                              //将图片添加到超链接中
elemText = document.createElement("div");                   //创建左上Icon图片Div
elemText.className = "iText";                               //class='iText'
elem.appendChild(elemText);                                 //将Div添加到初始Div中
sPicBox.appendChild(elem);                                  //将初始Div添加到图片Div
//拼接span
//<span class=""></span>
dotElem = document.createElement("span");                   //创建初始Span
sDotBox.appendChild(dotElem);                               //将初始Span添加到scrollDot中
}
var sPicBoxItem = this.selectClassName(sPicBox, "item", "div"); //返回图片Div class等于className的项
this.currentDot = Math.floor(this.scrollLength / 2);            //取中间数
sPicBoxItem[this.currentDot].className = "item current";        //设置中间数class='item current' 即选中状态
sDotBox.getElementsByTagName("span")[this.currentDot].className = "on"; //设置对应scrollDot里面的span class='on'
this.changData();                                                       //左边距,宽度,高度,上边距/下边距
for (var n = 0; n < this.scrollLength; n++) {                           //循环数组
var _tempData = this.itemData[this.scrollLength][n];                    //获取图片对象 this.itemData[this.scrollLength]=itemData[5]/itemData[3]
sPicBoxItem[n].style.cssText = "left:" + _tempData.dLeft + "px;width:" + _tempData.dWidth + "px;height:" + _tempData.dHeight + "px;margin-top:" + _tempData.dMarginTop + "px;z-index:" + _tempData.dZIndex;     //设置图片Div样式
sPicBoxItem[n].getElementsByTagName("img")[0].style.cssText = "width:" + _tempData.dWidth + "px;height:" + _tempData.dHeight + "px";                //设置图片样式
}
},
init: function () {
var flag1 = flag2 = flag3 = flag4 = flag5 = flag6 = null;
if (flag1 || flag2 || flag3) {
clearInterval(flag1);
clearTimeout(flag2);
clearTimeout(flag3);
flag1 = flag2 = flag3 = null;
}
var _this = this;
this.loadAct();             //初始化标签
window.onload = function () {   //清除定时器
clearTimeout(flag2);
clearTimeout(flag3);
flag2 = flag3 = null;
if (_this.auto == true) {       //this.auto=true 设置定时器
flag1 = setInterval(function () {
_this.act("", "next");
}, _this.speedTime);            //_this.speedTime 间隔时间
}
};
document.getElementById(this.pCur).onclick = function () {      //向上翻点击事件
if (flag1 || flag2 || flag3 || flag4 || flag5 || flag6) {
clearInterval(flag1);
clearTimeout(flag2);
clearTimeout(flag3);
clearTimeout(flag4);
clearTimeout(flag5);
clearTimeout(flag6);
flag1 = flag2 = flag3 = flag4 = flag5 = flag6 = null;
}
_this.act("", "prev");                                          //向上翻action
flag4 = setTimeout(function () {
if (_this.auto == true) {                                       //this.auto == true 设置定时任务
flag1 = setInterval(function () {
_this.act("", "next");
}, _this.speedTime);
}
}, 1000);
}
document.getElementById(this.nCur).onclick = function () {      //向下翻点击事件
if (flag1 || flag2 || flag3 || flag4 || flag5 || flag6) {
clearInterval(flag1);
clearTimeout(flag2);
clearTimeout(flag3);
clearTimeout(flag4);
clearTimeout(flag5);
clearTimeout(flag6);
flag1 = flag2 = flag3 = flag4 = flag5 = flag6 = null;
}
_this.act("", "next");                                          //向下翻action
flag4 = setTimeout(function () {
if (_this.auto == true) {                                       //this.auto == true 设置定时任务
flag1 = setInterval(function () {
_this.act("", "next");
}, _this.speedTime);
}
}, 1000);
}
var sDotBox = document.getElementById(this.sDot);               //获取scrollDot
var sDotBoxItem = sDotBox.getElementsByTagName("span");         //获取span
for (var i = 0; i < this.scrollLength; i++) {
sDotBoxItem[i].onclick = (function (m) {                        //设置span点击事件(这里由于样式问题span点击事件在这里起不到作用)
return function () {
if (flag1 || flag2 || flag3 || flag4 || flag5 || flag6) {
clearInterval(flag1);
clearTimeout(flag2);
clearTimeout(flag3);
clearTimeout(flag4);
clearTimeout(flag5);
clearTimeout(flag6);
flag1 = flag2 = flag3 = flag4 = flag5 = flag6 = null;
}
_this.act(m, "");                                               //猜测传入的应该是span索引
flag6 = setTimeout(function () {                                //设置定时任务
flag1 = setInterval(function () {
_this.act("", "next");
}, _this.speedTime);
}, 1000);
}
})(i);
}
}
}
/* |xGv00|4b6fb41690ab6674e5b100289265e74a */
