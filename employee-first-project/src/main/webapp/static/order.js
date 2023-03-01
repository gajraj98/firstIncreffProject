 var jsonList=[];
 var updateJsonList=[];
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
       var tableBody = document.getElementById("input-form-table");
       var barcode = document.getElementById("inputBarcode").value;
       var quantity = document.getElementById("inputQuantity").value;
       var mrp = document.getElementById("inputMrp").value;
       document.getElementById("order-input-form").reset();
     var url = "/employee" +  "/api/product" + "/" + "byBarcode" + "/" + barcode;
       	$.ajax({
       	   url: url,
       	   type: 'GET',
       	   success: function(data) {
       	   		if(data.mrp<mrp)
       	   		{
       	   		  alert("selling price can't be greater then Mrp");
       	   		}
       	   		else{
       	   		 jsonList.push(orderItem);
       	   		var row = tableBody.insertRow();
                var cell1 = row.insertCell();
                var cell2 = row.insertCell();
                var cell3 = row.insertCell();
                var cell4 = row.insertCell();
                cell1.innerHTML = barcode;
                cell2.innerHTML = quantity;
                cell3.innerHTML = mrp;
                cell4.innerHTML = '<button class="Icons tableButton-delete" onclick="deleteItemInList()">delete</button>';
       	   		}
       	   },
       	   error: handleAjaxError
       	});


  }
function deleteItemInList()
{
   var length = jsonList.length;
   if(length>0)
   {
     jsonList.splice(length-1,1);
     var tableBody = document.getElementById("input-form-table");
     var lastRowIndex = tableBody.rows.length - 1; // get index of last row
     tableBody.deleteRow(lastRowIndex);
   }
}
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}
//order edit
function addOrderItem(event)
{
    event.preventDefault();
    var $form = $("#order-edit-form");
    	var json = toJson($form);
    	console.log(json);
    	var url = getOrderItemUrl();
    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
                  getOrderItems2();
    	   },
    	   error: handleAjaxError
    	});

    	return false;
}
function deleteOrderItem(id)
{
    var url = getOrderItemUrl() + "/" + id;
    $.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
            getOrderItems2();
    	   },
    	   error: handleAjaxError
    	});
}
function updateOrderItem()
{
   	//Get the ID
   	console.log("updateOrderItem");
   	var id = $("#orderItem-edit-form input[name=id]").val();
   	var url =getOrderItemUrl() + "/" + id;

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
   	   		getOrderItems2();
   	   },
   	   error: handleAjaxError
   	});

   	return false;
}
function editOrder(orderId)
{
     $('#order-edit-form input[name=orderId]').val(orderId);
    getOrderItems2();
    $('#edit-order-modal').modal('toggle');
}
function getOrderItems2()
{
    var orderId = $('#order-edit-form input[name=orderId]').val();
    var url = getOrderItemUrl() + "/" + orderId;
    	$.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		displayOrderItemList2(data);
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
            		var buttonHtml = '<button class="Icons tableButton-delete" onclick="deleteOrderItem(' + e.id + ')">delete</button>'
                    		buttonHtml += ' <button class="Icons tableButton-edit" onclick="displayEditOrderItem(' + e.id + ')">edit</button>'
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
     var url = getOrderItemUrl() + "/ByOrderId" + "/" + id;
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


//BUTTON ACTIONS for orders
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function addOrder(event){
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
        		var buttonHtml = '<button class="Icons tableButton-delete button" onclick="deleteOrder(' + e.id + ')">delete</button>'
                		buttonHtml += ' <button class="Icons tableButton-edit button" onclick="editOrder(' + e.id + ')">edit</button>'
                		buttonHtml += ' <button class="Icons tableButton-view button" onclick="getOrderItems(' + e.id + ')">view</button>'
        		var row = '<tr>'
        		+ '<td>' + e.id + '</td>'
        		+ '<td>'  + formattedDateAndTime + '</td>'
        		+ '<td>' + buttonHtml + '</td>'
        		+ '</tr>';
                $tbody.append(row);
        	}
}

//for Invoice
function getOrderItems(id)
{
    var url = getOrderItemUrl() + "/" + id;
    	$.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		displayOrderItemList(data);
    	   		$('#order-invoice-form input[name=invoice]').val(id);
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
     var orderId = $('#order-invoice-form input[name=invoice]').val();
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
          var orderId = $('#order-invoice-form input[name=invoice]').val();
         const linkSource = `data:application/pdf;base64,${pdf}`;
         const downloadLink = document.createElement("a");
         const fileName = "Invoice"+ orderId + ".pdf";
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

