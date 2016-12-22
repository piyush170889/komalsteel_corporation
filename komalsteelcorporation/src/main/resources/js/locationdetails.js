function getCityList(stateid) {
	$("#cityId").html("");
	$("#cityId").prop("disabled", true);
	
	$.ajax({
        type : 'GET',
        url : 'getCity',
        dataType : 'json',
        data: "stateId="+stateid,
        success : function(data){
        	var listItems = "<option value='Select'>Select City</option>"; 
        	for (var i = 0; i < data.length; i++){
       	      listItems+= "<option value='" + data[i].locationName + "'>" + data[i].locationName + "</option>";
       	    }
        	$("#cityId").prop("disabled", false);
        	$("#cityId").html(listItems);
        }
    });
}

function getCityList1(stateid, city) {
	$("#cityId1").html("");
	$("#cityId1").prop("disabled", true);
	
	$.ajax({
        type : 'GET',
        url : 'getCity',
        dataType : 'json',
        data: "stateId="+stateid,
        success : function(data){
        	var listItems = "<option value='Select'>Select City</option>";
        	/*var listItems = "<option value='" + city + "'>" + city +"</option>";*/
        	for (var i = 0; i < data.length; i++){
       	      listItems+= "<option value='" + data[i].locationName + "'>" + data[i].locationName + "</option>";
       	    }
        	$("#cityId1").prop("disabled", false);
        	$("#cityId1").html(listItems);
        	$("#cityId1").val(city);
        }
    });
}
