
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
function getCountTotalInventory()
{
        var url = getInventoryReportUrl() + "/total";
        	$.ajax({
        	   url: url,
        	   type: 'GET',
        	   success: function(data) {
        	   		document.getElementById("total-page").value = Math.ceil(data/10);
        	   },
        	   error: function(jqXHR, textStatus, errorThrown) {
                                            handleAjaxError(jqXHR, textStatus, errorThrown);
                                    }
        	});

}
function prevPage()
{
  var pageNo = document.getElementById("page-number").value;
  if(pageNo>0)document.getElementById("page-number").value = pageNo - 1;
  getAllInventoryReportList();
}
function nextPage()
{
  var pageNo = document.getElementById("page-number").value;
  var page= parseInt(pageNo);
  document.getElementById("page-number").value = page + 1;
  getAllInventoryReportList();
}

function getAllInventoryReportList(){
     getCountTotalInventory();
         var pageNo=0;
         pageNo= document.getElementById("page-number").value;
         if(pageNo==0)pageNo=1;
       	var url = getInventoryReportUrl() + "?pageNo=" + pageNo;
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
           checkPreviousNext();
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
function checkLimit()
{
   var page = document.getElementById("page-number").value;
   var totalPage = document.getElementById("total-page").value;
   if(page>totalPage) document.getElementById("page-number").value=totalPage;
   getAllInventoryReportList();
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
function checkPreviousNext(){
    var page = document.getElementById("page-number").value;
    var totalPage = document.getElementById("total-page").value;
    var previousBtn=document.getElementById("previous-page");
    var nextBtn=document.getElementById("next-page");

    if(page==1){
        previousBtn.disabled=true;
        nextBtn.disabled=false;
    }
    else if(page==totalPage){
        nextBtn.disabled=true;
        previousBtn.disabled=false;
    }
    else if(page==1 && page==totalPage){
        previousBtn.disabled=true;
        nextBtn.disabled=true;
    }
    else{
        previousBtn.disabled=false;
        nextBtn.disabled=false;
    }
}
//INITIALIZATION CODE
function init(){
  $('#inventoryReport-form').submit(getInventoryReportList);
}

$(document).ready(init);
$(document).ready(getAllInventoryReportList);
