
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/salesReport";
}

//BUTTON ACTIONS
function getSalesReportList(){
	var url = getSalesReportUrl();
	var $form = $("#salesReport-form");
    	var json = toJson($form);
    	console.log(json);
	$.ajax({
	   url: url,
	    type: 'POST',
        data: json,
         headers: {
               	'Content-Type': 'application/json'
               },
	   success: function(data) {
	   		displaySalesReportList(data);
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displaySalesReportList(data){
	var $tbody = $('#salesReport-table').find('tbody');
	$tbody.empty();

		var row = '<tr>'
		+ '<td>' + data.category + '</td>'
		+ '<td>' + data.quantity + '</td>'
		+ '<td>' + data.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
}



//INITIALIZATION CODE
function init(){
 $('#add-salesReport').click(getSalesReportList);

}

$(document).ready(init);

