
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
function downloadDailyReport(){
 let table = document.getElementById('dailyReport-table');
  let tsvData = generateTsvData(table);
  let blob = new Blob([tsvData], {type: 'text/tab-separated-values;charset=utf-8'});
  let url = URL.createObjectURL(blob);
  let a = document.createElement('a');
  a.href = url;
  const currentDate = new Date();
  const currentDateTimeString = currentDate.toLocaleString();
  a.download = 'DailyReport '+currentDateTimeString+'.tsv';
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  URL.revokeObjectURL(url);
}


//INITIALIZATION CODE
function init(){
$('#download-brand-report').click(downloadDailyReport);
}

$(document).ready(init);
$(document).ready(getDailyReportList)
