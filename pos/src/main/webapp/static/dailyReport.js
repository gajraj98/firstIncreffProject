
function getDailyReportUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/dailyReport";
}

//BUTTON ACTIONS
function getDailyReportList(){
   var url=getDailyReportUrl();
	$.ajax({
	   url: url,
	    type: 'GET',
         headers: {
               	'Content-Type': 'application/json'
               },
	   success: function(data) {
	   		displayDailyReportList(data);
	   },
	   error:  function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
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


//UI DISPLAY METHODS

function displayDailyReportList(data){
	var $tbody = $('#dailyReport-table').find('tbody');
	$tbody.empty();
	for(var i in data){
	    e=data[i];
		var row = '<tr>'
		+ '<td>' +e.date + '</td>'
		+ '<td>' + e.totalInvoice + '</td>'
		+ '<td>' + e.totalItems + '</td>'
		+ '<td>' + parseFloat(e.totalRevenue).toFixed(2) + '</td>'
		+ '</tr>';
        $tbody.append(row);
        }

}



//INITIALIZATION CODE
function init(){

}

$(document).ready(init);
$(document).ready(getDailyReportList)
