
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
    	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
    	});

    	return false;
}


function getAllInventoryReportList(){

       	var url = getInventoryReportUrl() + "?pageNo=" + 1;
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
                   error: function(jqXHR, textStatus, errorThrown) {
                                                handleAjaxError(jqXHR, textStatus, errorThrown);
                                        }
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
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category+ '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.inventory + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function handleAjaxError(xhr, textStatus, errorThrown) {
  var errorMessage = "An error occurred while processing your request.";
  if (xhr.responseJSON && xhr.responseJSON.message) {
    errorMessage = xhr.responseJSON.message;
  }
  $('#error-modal').addClass('show');
  $('.toast-body').text(errorMessage);
  $('.toast').toast({delay: 5000});
  $('.toast').toast('show');
}
function generateTsvData(table) {
  let data = '';
  let rows = table.querySelectorAll('tr');
  for (let i = 0; i < rows.length; i++) {
    let cells = rows[i].querySelectorAll('td, th');
    for (let j = 0; j < cells.length; j++) {
      let cellData = cells[j].textContent.replace(/\r?\n|\r/g, '');
      data += cellData + '\t';
    }
    data += '\n';
  }
  return data;
}
function downloadInventoryReport(){
 let table = document.getElementById('inventoryReport-table');
  let tsvData = generateTsvData(table);
  let blob = new Blob([tsvData], {type: 'text/tab-separated-values;charset=utf-8'});
  let url = URL.createObjectURL(blob);
  let a = document.createElement('a');
  a.href = url;
  const currentDate = new Date();
  const currentDateTimeString = currentDate.toLocaleString();
  a.download = 'InventoryReport '+currentDateTimeString+'.tsv';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}
//INITIALIZATION CODE
function init(){
  $('#inventoryReport-form').submit(getInventoryReportList);
  $('#download-brand-report').click(downloadInventoryReport);
}

$(document).ready(init);
$(document).ready(getAllInventoryReportList);
