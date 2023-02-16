
function getInventoryReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventoryReport";
}

//BUTTON ACTIONS

function getInventoryReportList(){
	var url = getInventoryReportUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryReportList(data);
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displayInventoryReportList(data){
	var $tbody = $('#inventoryReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category+ '</td>'
		+ '<td>' + e.inventory + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}



//INITIALIZATION CODE
function init(){
}

$(document).ready(init);
$(document).ready(getInventoryReportList);

