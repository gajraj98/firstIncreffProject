
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/salesReport";
}

//BUTTON ACTIONS
function getSalesReportList(){
     event.preventDefault();
     var start = $('#startDate').val();
     var  end =$('#endDate').val();
     if(Date.parse(start)>Date.parse(end))
     {
        alert("Invalid Date Range");
     }
     else{
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
	   document.getElementById("salesReport-form").reset();
	   		displaySalesReportList(data);
	   },
	   error: handleAjaxError
	});
	}
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
 $('#salesReport-form').submit(getSalesReportList);

}

$(document).ready(init);

