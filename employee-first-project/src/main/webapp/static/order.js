 var jsonList=[];
 var updateJsonList=[];
 var orderId=2;
function toggleAdd(){
 $('#input-order-modal').modal('toggle');
}
function toggleUpdateAdd(){
 $('#add-update-cart-order').modal('toggle');
}
  function addNameField() {
       var $form = $("#order-input-form");
       var json = toJson($form);
       var orderItem = JSON.parse(json);
       jsonList.push(orderItem);
       var tableBody = document.getElementById("input-form-table");
       var barcode = document.getElementById("inputBarcode").value;
       var quantity = document.getElementById("inputQuantity").value;
       var mrp = document.getElementById("inputMrp").value;
       var row = tableBody.insertRow();
       var cell1 = row.insertCell();
       var cell2 = row.insertCell();
       var cell3 = row.insertCell();
       cell1.innerHTML = barcode;
       cell2.innerHTML = quantity;
       cell3.innerHTML = mrp;
  }
  function addUpdateNameField() {
         var $form = $("#order-edit-form");
         var json = toJson($form);
         var orderItem = JSON.parse(json);
         updateJsonList.push(orderItem);
         var tableBody = document.getElementById("input-update-form-table");
         var barcode = document.getElementById("updateInputBarcode").value;
         var quantity = document.getElementById("updateInputQuantity").value;
         var mrp = document.getElementById("updateInputMrp").value;
         var row = tableBody.insertRow();
         var cell1 = row.insertCell();
         var cell2 = row.insertCell();
         var cell3 = row.insertCell();
         cell1.innerHTML = barcode;
         cell2.innerHTML = quantity;
         cell3.innerHTML = mrp;
    }
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addOrder(event){
//    deleting rows of table
    var tableBody = document.getElementById("input-form-table");
    tableBody.innerHTML="";

	var url = getOrderUrl();

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: JSON.stringify(jsonList),
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	         jsonList=[];
	   		getOrderList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateOrder(event){
//     deleting rows of table
     var tableBody = document.getElementById("input-update-form-table");
     tableBody.innerHTML="";

	$('#edit-order-modal').modal('toggle');
	//Get the ID
	var id = $("#order-edit-form input[name=id]").val();
	var url = getOrderUrl() + "/" + id;
     console.log(url);
	//Set the values to update

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: JSON.stringify(updateJsonList),
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getOrderList();
	   		updateJsonList=[];
	   },
	   error: handleAjaxError
	});

	return false;
}


function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   		console.log(data);
	   },
	   error: handleAjaxError
	});
}

function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#orderFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
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
	var url = getOrderUrl();

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
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

}

function downloadErrors(){
	writeFileData(errorData);
}

//UI DISPLAY METHODS

function displayOrderList(data){
var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	 var tableBody = document.getElementById("order-table");
	 tableBody.innerHTML="";
	for(var i in data){
		var e = data[i];
		 var dateAndTime = data[i].time
              var formattedDateAndTime = moment(dateAndTime,"YYYY-MM-DDTHH:mm:ss").format("MM/DD/YYYY HH:mm:ss");
		var buttonHtml = '<button onclick="deleteOrder(' + e.id + ')">delete</button>'
		buttonHtml += ' <button onclick="displayEditOrder(' + e.id + ')">edit</button>'
		buttonHtml += ' <button onclick="displayOrderItems(' + e.id + ')">view</button>'
//		console.log(dateAndTime);
		    var row = tableBody.insertRow();
              var cell1 = row.insertCell();
              var cell2 = row.insertCell();
              var cell3 = row.insertCell();
              cell1.innerHTML = e.id;
              cell2.innerHTML = formattedDateAndTime;
              cell3.innerHTML = buttonHtml;
	}
}

function displayOrderItems(id)
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    var url = baseUrl + "/api/orderItem" + "/" + id;
    	$.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		displayOrderItemList(data);
    	   		orderId = id;
    	   		$('#orderItem-modal').modal('toggle');
    	   },
    	   error: handleAjaxError
    	});
}
function displayOrderItemList(data)
{
  var $tbody = $('#orderItem-table').find('tbody');
  	$tbody.empty();
  	for(var i in data){
    		var e = data[i];
    		var row = '<tr>'
    		+ '<td>' + e.name + '</td>'
    		+ '<td>'  + e.quantity + '</td>'
    		+ '<td>' + e.sellingPrice + '</td>'
    		+ '</tr>';
            $tbody.append(row);
    	}
}
function DownLoadInvoice()
{
     var baseUrl = $("meta[name=baseUrl]").attr("content")
     var url = baseUrl + "/api/generateInvoice" + "/" + orderId;
        	$.ajax({
        	   url: url,
        	   type: 'GET',
        	   success: function(data) {
                 downloadPDF(data);
        	   },
        	   error: handleAjaxError
        	});
}
function downloadPDF(pdf) {
const linkSource = `data:application/pdf;base64,${pdf}`;
const downloadLink = document.createElement("a");
const fileName = "abc"+orderId + ".pdf";
downloadLink.href = linkSource;
downloadLink.download = fileName;
downloadLink.click();}
function displayEditOrder(id){
	var url = getOrderUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrder(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#orderFile');
	$file.val('');
	$('#orderFileName').html("Choose File");
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
	var $file = $('#orderFile');
	var fileName = $file.val();
	$('#orderFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-order-modal').modal('toggle');
}

function displayOrder(data){
	$("#order-edit-form input[name=id]").val(data.id);
	$('#edit-order-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
    $('#insert-order').click(toggleAdd);
    $('#add-cart-order').click(addNameField);
    $('#add-update-cart-order').click(addUpdateNameField);
	$('#add-order').click(addOrder);
	$('#update-order').click(updateOrder);
	$('#refresh-data').click(getOrderList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#orderFile').on('change', updateFileName);
    $('#downloadInvoice').click(DownLoadInvoice);
}

$(document).ready(init);
$(document).ready(getOrderList);

