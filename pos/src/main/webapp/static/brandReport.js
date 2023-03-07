
function getBrandReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS

function getBrandReportList(){
	var url = getBrandReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandReportList(data);
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displayBrandReportList(data){
	var $tbody = $('#brandReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category+ '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



//INITIALIZATION CODE
function init(){
}

$(document).ready(init);
$(document).ready(getBrandReportList);

