function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function updateInventory1(event){
	//Set the values to update
	event.preventDefault();
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getInventoryUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   document.getElementById("inventory-form").reset();
	   		getInventoryList();
//	   		handleSuccessMessage("Successfully Added");
	   },
	   error:function(jqXHR, textStatus, errorThrown) {
                    handleAjaxError(jqXHR, textStatus, errorThrown);
                   }
	});

	return false;
}

function updateInventory(event){
    event.preventDefault();
	//Get the ID
	var id = $("#updateId").val();
	var url = getInventoryUrl() + "/" + id;

	//Set the values to update
	var $form = $("#inventory-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getInventoryList();
	   		$('#edit-inventory-modal').modal('toggle');
	   		handleSuccessMessage("Successfully Updated");
	   },
	   error:function(jqXHR, textStatus, errorThrown) {
                     handleAjaxError(jqXHR, textStatus, errorThrown);
                    }
	});

	return false;
}

function getCountTotalInventory()
{
        var url = getInventoryUrl() + "/total";
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
  getInventoryList();
}
function nextPage()
{
  var pageNo = document.getElementById("page-number").value;
  var page= parseInt(pageNo);
  document.getElementById("page-number").value = page + 1;
  getInventoryList();
}
function checkLimit()
{
   var page = document.getElementById("page-number").value;
   var totalPage = document.getElementById("total-page").value;
   if(page>totalPage) document.getElementById("page-number").value=totalPage;
   getInventoryList();
}
function getInventoryList(){
        getCountTotalInventory();
         var pageNo=0;
         pageNo = document.getElementById("page-number").value;
         if(pageNo==0)pageNo=1;
       	var url = getInventoryUrl() + "/limited" + "?pageNo=" + pageNo;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventoryList(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                      handleAjaxError(jqXHR, textStatus, errorThrown);
                     }
	});
	checkPreviousNext();
}

function deleteInventory(id){
	var url = getInventoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getInventoryList();
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                      handleAjaxError(jqXHR, textStatus, errorThrown);
                     }
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	if(fileData.length==0)
    	{
    	   handleError("File is Empty");
    	}else{
    	uploadRows();
    	}
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getInventoryUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   },
	   error: function(response){
	   		row.error=JSON.parse(response.responseText).message;
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	  if(errorData.length==0)
        {
           handleError("Nothing to download");
        }
        else{
    	writeFileData(errorData);
    	}
}

//UI DISPLAY METHODS

function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = ' <button class="btn btn-primary Icons tableButton-edit button" onclick="displayEditInventory(' + e.id + ')">edit</button>';
		var row = '<tr>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.name + '</td>'
		+ '<td>' + e.inventory + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	console.log(data);
}

function displayEditInventory(id){
console.log(id);
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayInventory(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                      handleAjaxError(jqXHR, textStatus, errorThrown);
              }
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}
function enableUpload(){
  var btn = document.getElementById("process-data");
  btn.disabled=false;
}
function displayUploadData(){
 	resetUploadDialog();
	$('#upload-inventory-modal').modal('toggle');
}

function displayInventory(data){
	$("#updateId").val(data.id);
	$("#inventory-edit-form input[name=barcode]").val(data.barcode);
	$("#inventory-edit-form input[name=inventory]").val(data.inventory);
	$('#edit-inventory-modal').modal('toggle');
}
function handleAjaxError(xhr, textStatus, errorThrown) {
  var errorMessage = "An error occurred while processing your request.";
  if (xhr.responseJSON && xhr.responseJSON.message) {
    errorMessage = xhr.responseJSON.message;
  }
  $('#error-modal').addClass('show');
  $('.toast-body').text(errorMessage);
  $('.error').toast({delay: 10000});
  $('.error').toast('show');
}
function handleError(errorMessage) {
  $('#error-modal').addClass('show');
  $('.toast-body').text(errorMessage);
  $('.error').toast({delay: 10000});
  $('.error').toast('show');
}
function handleSuccessMessage(successMessage) {
  $('#error-modal1').addClass('show');
  $('.toast-body1').text(successMessage);
  $('.success').toast({delay: 5000});
  $('.success').toast('show');
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
	$('#inventory-form').submit(updateInventory1);
	$('#inventory-edit-form').submit(updateInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#inventoryFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getInventoryList);

