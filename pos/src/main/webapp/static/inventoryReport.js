
function getInventoryReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventoryReport";
}
function getBrandCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}
//BUTTON ACTIONS

function getInventoryReportList(){
	event.preventDefault();
	var url = getInventoryReportUrl();
    	var $form = $("#inventoryReport-form");
    	var json = toJson($form);
    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(data) {
    	   document.getElementById("inventoryReport-form").reset();
    	   		displayInventoryReportList(data);
    	   },
    	   error: handleAjaxError
    	});

    	return false;
}

function getAllInventoryReportList(){

     var url = getInventoryReportUrl();
         $.ajax({
                   url: url,
                   type: 'GET',
                   headers: {
                        'Content-Type': 'application/json'
                    },
                   success: function(data) {
                   document.getElementById("inventoryReport-form").reset();
                        displayInventoryReportList(data);
                   },
                   error: handleAjaxError
         	});

         	return false;
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
  $('#inventoryReport-form').submit(getInventoryReportList);
}

$(document).ready(init);
$(document).ready(getAllInventoryReportList);
