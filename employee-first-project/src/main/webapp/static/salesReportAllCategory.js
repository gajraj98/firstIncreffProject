
function getSalesReportAllCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/salesReportAllCategory";
}

//BUTTON ACTIONS
function getSalesReportAllCategoryList(){
	var url = getSalesReportAllCategoryUrl();
	var $form = $("#salesReportAllCategory-form");
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
	   		displaySalesReportAllCategoryList(data);
	   },
	   error: handleAjaxError
	});
}


//UI DISPLAY METHODS

function displaySalesReportAllCategoryList(data){
	var $tbody = $('#salesReportAllCategory-table').find('tbody');
	$tbody.empty();
     for(var i in data){
     		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
		+ '</tr>';
        $tbody.append(row);
        }
}



//INITIALIZATION CODE
function init(){
 $('#add-salesReportAllCategory').click(getSalesReportAllCategoryList);

}

$(document).ready(init);

