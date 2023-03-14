
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
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
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
//INITIALIZATION CODE
function init(){
 $('#salesReport-form').submit(getSalesReportList);

}

$(document).ready(init);

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
