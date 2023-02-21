 var jsonList=[];
 var updateJsonList=[];
 var orderId=2;
 var orderId2=2;
function toggleAdd(){
 $('#input-order-modal').modal('toggle');
}
function toggleUpdateAdd(){
 $('#add-update-cart-order').modal('toggle');
}
  function addNameField() {
       event.preventDefault();
       var $form = $("#order-input-form");
       var json = toJson($form);
       var orderItem = JSON.parse(json);
       jsonList.push(orderItem);
       var tableBody = document.getElementById("input-form-table");
       var barcode = document.getElementById("inputBarcode").value;
       var quantity = document.getElementById("inputQuantity").value;
       var mrp = document.getElementById("inputMrp").value;
       document.getElementById("order-input-form").reset();
       var row = tableBody.insertRow();
       var cell1 = row.insertCell();
       var cell2 = row.insertCell();
       var cell3 = row.insertCell();
       cell1.innerHTML = barcode;
       cell2.innerHTML = quantity;
       cell3.innerHTML = mrp;
  }

//order edit
function addOrderItem(event)
{
    event.preventDefault();
    var $form = $("#order-edit-form");
    	var json = toJson($form);
    	console.log(json);
    	var url = $("meta[name=baseUrl]").attr("content") + "/api/orderItem";
    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
                  getOrderItems2(orderId2);
    	   },
    	   error: handleAjaxError
    	});

    	return false;
}
function deleteOrderItem(id)
{
    var url = $("meta[name=baseUrl]").attr("content") + "/api/orderItem" + "/" + id;
    $.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
            getOrderItems2(orderId2);
    	   },
    	   error: handleAjaxError
    	});
}
function updateOrderItem()
{
   	//Get the ID
   	console.log("updateOrderItem");
   	var id = $("#orderItem-edit-form input[name=id]").val();
   	var url = $("meta[name=baseUrl]").attr("content") + "/api/orderItem" + "/" + id;

   	//Set the values to update
   	var $form = $("#orderItem-edit-form");
   	var json = toJson($form);
    console.log(json);
   	$.ajax({
   	   url: url,
   	   type: 'PUT',
   	   data: json,
   	   headers: {
          	'Content-Type': 'application/json'
          },
   	   success: function(response) {
   	   		getOrderItems2(orderId2);
   	   },
   	   error: handleAjaxError
   	});

   	return false;
}
function editOrder(id)
{
    getOrderItems2(id);
    $('#edit-order-modal').modal('toggle');
}
function getOrderItems2(Orderid)
{
    orderId2=Orderid;
    var baseUrl = $("meta[name=baseUrl]").attr("content")
    var url = baseUrl + "/api/orderItem" + "/" + Orderid;
    	$.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		displayOrderItemList2(data);
    	   		$('#order-edit-form input[name=orderId]').val(orderId2);
    	   },
    	   error: handleAjaxError
    	});
}
function displayOrderItemList2(data)
{
    var $tbody = $('#input-update-form-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
            		var e = data[i];
            		var buttonHtml = '<button onclick="deleteOrderItem(' + e.id + ')">delete</button>'
                    		buttonHtml += ' <button onclick="displayEditOrderItem(' + e.id + ')">edit</button>'
            		var row = '<tr>'
            		+ '<td>' + e.barcode + '</td>'
            		+ '<td>'  + e.quantity + '</td>'
            		+ '<td>'  + e.sellingPrice + '</td>'
            		+ '<td>' + buttonHtml + '</td>'
            		+ '</tr>';
                    $tbody.append(row);
            	}
}
function displayEditOrderItem(id)
{
     var baseUrl = $("meta[name=baseUrl]").attr("content")
     var url = baseUrl + "/api/orderItem/ByOrderId" + "/" + id;
     	$.ajax({
     	   url: url,
     	   type: 'GET',
     	   success: function(data) {
     	   		displayOrderItem(data);
     	   },
     	   error: handleAjaxError
     	});
}
function displayOrderItem(data)
{
    $("#orderItem-edit-form input[name=barcode]").val(data.barcode);
    $("#orderItem-edit-form input[name=quantity]").val(data.quantity);
   	$("#orderItem-edit-form input[name=mrp]").val(data.sellingPrice);
   	$("#orderItem-edit-form input[name=id]").val(data.id);
   	$('#edit-orderItem-modal').modal('toggle');
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

function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
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
function displayOrderList(data){
var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	data.reverse();
	for(var i in data){
        		var e = data[i];
        		 var dateAndTime = data[i].time
                 var formattedDateAndTime = moment(dateAndTime,"YYYY-MM-DDTHH:mm:ss").format("MM/DD/YYYY HH:mm:ss");
        		var buttonHtml = '<button onclick="deleteOrder(' + e.id + ')">delete</button>'
                		buttonHtml += ' <button onclick="editOrder(' + e.id + ')">edit</button>'
                		buttonHtml += ' <button onclick="getOrderItems(' + e.id + ')">view</button>'
        		var row = '<tr>'
        		+ '<td>' + e.id + '</td>'
        		+ '<td>'  + formattedDateAndTime + '</td>'
        		+ '<td>' + buttonHtml + '</td>'
        		+ '</tr>';
                $tbody.append(row);
        	}
}

function getOrderItems(id)
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
    		+ '<td>' + parseFloat(e.sellingPrice).toFixed(2) + '</td>'
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
         const fileName = "Invoice"+orderId + ".pdf";
         downloadLink.href = linkSource;
         downloadLink.download = fileName;
         downloadLink.click();

}




//INITIALIZATION CODE
function init(){
    $('#insert-order').click(toggleAdd);
    $('#order-input-form').submit(addNameField);
    $('#order-edit-form').submit(addOrderItem);
	$('#add-order').click(addOrder);
	$('#refresh-data').click(getOrderList);
    $('#downloadInvoice').click(DownLoadInvoice);
    $('#add-update-cart-order').click(addOrderItem);
    $('#update-orderItem-model').click(updateOrderItem);
}

$(document).ready(init);
$(document).ready(getOrderList);

