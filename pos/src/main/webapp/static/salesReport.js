
function getSalesReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/sales-reports";
}
function getSalesReportAllCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/salesReportAllCategory";
}
//BUTTON ACTIONS
function getSalesReportList(){

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
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
	}
}
function getSalesReportAllCategoryList(){
      event.preventDefault();
      var start = $('#startDate').val();
           var  end =$('#endDate').val();
           if(((start!=="")&&(end!==""))&&Date.parse(start)>Date.parse(end))
           {
               alert("Invalid Date Range");
           }
           else{
	var url = getSalesReportUrl() + "/brand";
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
       	   error: function(jqXHR, textStatus, errorThrown) {
                           handleAjaxError(jqXHR, textStatus, errorThrown);
                   }
       	});
	}
}

function getAllSalesReportList(){
	var url = getSalesReportUrl();
	$.ajax({
	   url: url,
	    type: 'GET',
         headers: {
               	'Content-Type': 'application/json'
               },
	   success: function(data) {
	  displaySalesReportList(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                    handleAjaxError(jqXHR, textStatus, errorThrown);
            }
	});

}


//UI DISPLAY METHODS

function displaySalesReportList(data){
	var $tbody = $('#salesReport-table').find('tbody');
	$tbody.empty();
 for(var i in data){
     		var e = data[i];
		var row = '<tr>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>' + e.category + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.revenue + '</td>'
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
function downloadSalesReport(){
 let table = document.getElementById('salesReport-table');
  let tsvData = generateTsvData(table);
  let blob = new Blob([tsvData], {type: 'text/tab-separated-values;charset=utf-8'});
  let url = URL.createObjectURL(blob);
  let a = document.createElement('a');
  a.href = url;
  const currentDate = new Date();
  const currentDateTimeString = currentDate.toLocaleString();
  a.download = 'SalesReport '+currentDateTimeString+'.tsv';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}
//INITIALIZATION CODE
function init(){
 $('#add-salesReport').click(getSalesReportList);
 $('#download-brand-report').click(downloadSalesReport);
}

$(document).ready(init);
$(document).ready(getAllSalesReportList);
$(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();

    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var maxDate = year + '-' + month + '-' + day;
    $('.date').attr('max', maxDate);
});
