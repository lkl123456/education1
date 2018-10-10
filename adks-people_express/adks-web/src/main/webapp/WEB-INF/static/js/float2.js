
document.write(jcode); 
//window.setInterval("heartBeat()",1);
var xPos = 20;
var yPos = 0;
var step = 1;
var delay = 40;
var height = 0;
var Hoffset = 0;
var Woffset = 0;
var yon = 0;
var xon = 0;
var pause = true;
var interval;
var img = document.getElementById("img");
img.style.top = yPos;
function changePos() {
width=document.documentElement.clientWidth;
height=document.documentElement.clientHeight;
Hoffset=img.offsetHeight; Woffset=img.offsetWidth;
img.style.left=xPos+document.body.scrollLeft+"px";
img.style.top=yPos+getScrollTop()+"px";
if(yon) {
yPos=yPos+step;
}else {
yPos=yPos-step;
}if(yPos<0) {
yon=1; yPos=0;
}if(yPos>=(height-Hoffset)) {
yon=0; yPos=(height-Hoffset);
}if(xon) {
xPos=xPos+step;
}else {
xPos=xPos-step;
}if(xPos<0) {
xon=1; xPos=0;
}if(xPos>=(width-Woffset)) {
xon=0; xPos=(width-Woffset);
}
}
function getScrollTop()
{
    var scrollTop=0;
    if(document.documentElement&&document.documentElement.scrollTop)
    {
        scrollTop=document.documentElement.scrollTop;
    }
    else if(document.body)
    {
        scrollTop=document.body.scrollTop;
    }
    return scrollTop;
}
function start() {
img.style.visibility="visible"; interval=setInterval('changePos()',delay);
}
img.onmouseover = function() {
        clearInterval(interval);
        interval = null;
}
img.onmouseout = function() {
	interval = setInterval('changePos()', delay);
}
function closeimg(){
	clearInterval(interval);
    interval = null;
	img.style.display="none";
}
start();