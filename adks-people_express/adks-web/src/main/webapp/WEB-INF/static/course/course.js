//点击全部分类
function courseCateAllView(orgId,courseSortCode){
	document.getElementById("topNew").style.color="";
	
	
	var temp = document.getElementsByName('cataname');
	for(var te1=0;te1<temp.length;te1++){
		temp[te1].style.color="";
	}
	document.getElementById("cateAll").style.color="#000000";
	$("#courseSortCode").val(courseSortCode);
	getCoursesList();
}
//点击 分类查询
function courseCateView(orgId,courseSortCode){
	document.getElementById("topNew").style.color="";
	$("#courseSortCode").val(courseSortCode);
	$("#orgId").val(orgId);
	getCoursesList();	
} 

function courseInfo(courseId,orgId){
	//alert(contextPath +"/course/courseInfo/"+courseId+"/"+orgId+".shtml");
	window.location.href=contextPath +"/course/courseInfo/"+courseId+"/"+orgId+".shtml";
}

// 课程类型 查询 
function courseTypeAllView(type){
	document.getElementById("topNew").style.color="";
	
	var temp2 = document.getElementsByName('courseType');
	for(var te1=0;te1<temp2.length;te1++){
		temp2[te1].style.color="";
	}
	document.getElementById("courseType_"+type).style.color="#000000"; 
	if(type=='all'){
		$("#courseType").val("");
	}else {
		$("#courseType").val(type);
	}
	getCoursesList();	
}
//执行搜索
function getCoursesList(){
	var courseSortCode=$("#courseSortCode").val();
	var searchKeyValue=$("#searchKeyValue").val();
	var searchKey=$("#searchKey").val();
	var courseType=$("#courseType").val();
	
	var orgId=$("#orgId").val();
	var showStyle=$("#showStyle").val();
	
	if(courseSortCode==null || courseSortCode=='undefined' ||courseSortCode==undefined){
		courseSortCode=""
	}
	if(searchKeyValue==null || searchKeyValue=='undefined' ||searchKeyValue==undefined){
		if(searchKey!=null&&searchKey!=""&&searchKey!="null"){
			searchKeyValue=searchKey;
		}else{
			searchKeyValue=""
		}
	}else{
		$("#searchKey").val("");
	}
	if(courseType==null || courseType=='undefined' ||courseType==undefined){
		courseType=""
	}
	
	if(showStyle==null || showStyle=='undefined' ||showStyle==undefined){
		showStyle="style1";
	}
	searchKeyValue= strEnc(searchKeyValue,"adks1","adks2","adks3");//中文乱码 加密;
		//alert(contextPath+"/course/getCoursesList.do?searchKeyValue="+encodeURI(searchKeyValue)+"&courseSortCode="+courseSortCode+"&courseType="+courseType+"&showStyle="+showStyle+"&orgId="+orgId);
		$.ajax({
			async:true,
			url:contextPath+"/course/getCoursesList.do?searchKeyValue="+searchKeyValue+"&courseSortCode="+courseSortCode+"&courseType="+courseType+"&showStyle="+showStyle+"&orgId="+orgId,
			type:"post", 
			success:function(data){
				$("#courseListDiv").html(data);
			}
		});
}

function getTopNewCourseList(){
	var temp = document.getElementsByName('cataname');
	for(var te1=0;te1<temp.length;te1++){
		temp[te1].style.color="";
	}
	//document.getElementById("topNew").style.color="#000000";
	
	var temp2 = document.getElementsByName('courseType');
	for(var te1=0;te1<temp2.length;te1++){
		temp2[te1].style.color="";
	}
	
	$("#courseSortId").val("");
	$.ajax({
			async:true,
			url:contextPath+"/course/getTopNewCourseList.do",
			type:"post", 
			success:function(data){
				$("#courseListDiv").html(data);
			}
	});
}

function dj1() {
	document.getElementById("xs_y").className = "xs1y";
	document.getElementById("xs_e").className = "xs2";
	document.getElementById("xs_s").className = "xs3";
	document.getElementById("xianshi").className = "xianshi1";
	document.getElementById("xs_y_2").className = "xs1y";
	document.getElementById("xs_e_2").className = "xs2";
	document.getElementById("xs_s_2").className = "xs3";
	
	$("#showStyle").val("style1");
}
function dj2() {
	document.getElementById("xs_y").className = "xs1";
	document.getElementById("xs_e").className = "xs2y";
	document.getElementById("xs_s").className = "xs3";
	document.getElementById("xianshi").className = "xianshi2";
	document.getElementById("xs_y_2").className = "xs1";
	document.getElementById("xs_e_2").className = "xs2y";
	document.getElementById("xs_s_2").className = "xs3";
	$("#showStyle").val("style2");
	
}
function dj3() {
	document.getElementById("xs_y").className = "xs1";
	document.getElementById("xs_e").className = "xs2";
	document.getElementById("xs_s").className = "xs3y";
	document.getElementById("xianshi").className = "xianshi3";
	document.getElementById("xs_y_2").className = "xs1";
	document.getElementById("xs_e_2").className = "xs2";
	document.getElementById("xs_s_2").className = "xs3y";
	$("#showStyle").val("style3");
	
}