function  selRecord(year){
	$.ajax({
		async:true,
		url:contextPath+"/record/recordIndex.do?selYear="+year,
		type:"post", 
		success:function(data){
			$("#gradeListDiv").html(data);
		}
	});
}

function getUserRecordByGradeId(gradeId,gradeName){
	if(gradeId!=null && gradeId!=''){
		$.ajax({
			async:true,
			url:contextPath+"/record/getUserRecordByGradeId.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
			type:"post", 
			success:function(data){
				$("#gradeListDiv").html(data);
			}
		});
	}
}

function getRecordThesisList(gradeId,gradeName){
	if(gradeId!=null && gradeId!=''){
		$.ajax({
			async:true,
			url:contextPath+"/record/getRecordThesisList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
			type:"post", 
			success:function(data){
				$("#gradeListDiv").html(data);
			}
		});
	}
	
}

function getRecordExamList(gradeId,gradeName){
	if(gradeId!=null && gradeId!=''){
		$.ajax({
			async:true,
			url:contextPath+"/record/getRecordExamList.do?gradeId="+gradeId+"&gradeName="+encodeURI(gradeName),
			type:"post", 
			success:function(data){
				$("#gradeListDiv").html(data);
			}
		});
	}
}