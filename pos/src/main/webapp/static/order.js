 var jsonList=[];
 var updateJsonList=[];
 var map={};
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
       barcode = barcode.toLowerCase().trim();
     var url = "/pos" +  "/api/products/barcode" + "?barcode=" + barcode;
       	$.ajax({
       	   url: url,
       	   type: 'GET',
       	   success: function(data) {
       	   		if(data.mrp<mrp)
       	   		{
       	   		 handleError("selling price can't be greater then Mrp");
       	   		}
       	   		if(data.inventory<quantity)
       	   		{
       	   		   handleError("quantity exceeds the present inventory");
       	   		}
       	   		else if(barcode in map)
       	   		{
                 document.getElementById("order-input-form").reset();
       	   		  for(let i=0;i<jsonList.length;i++)
                     {
                        if(jsonList[i].barcode === barcode)
                        {
                           if(jsonList[i].mrp != mrp)
                           {
                              handleError("Price of same product can't be different");
                           }
                           else{
                           var newQuantity = parseInt(jsonList[i].quantity) + parseInt(quantity);
                           jsonList[i].quantity=  newQuantity;
                           var tableBody1 = document.getElementById("input-form-table");
                           const rowToUpdate = tableBody1.rows[i+1];
                           rowToUpdate.cells[1].innerHTML = newQuantity;
                           handleSuccessMessage("Successfully Added to cart");
                           }
                           break;
                        }
                     }
       	   		}
       	   		else{
       	   		document.getElementById("order-input-form").reset();
       	   		map[barcode] = quantity;
       	   		 jsonList.push(orderItem);
       	   		var row = tableBody.insertRow();
       	   		row.style.backgroundColor = "white";
                var cell1 = row.insertCell();
                var cell2 = row.insertCell();
                var cell3 = row.insertCell();
                var cell4 = row.insertCell();
                cell1.innerHTML = barcode;
                cell2.innerHTML = quantity;
                cell3.innerHTML = mrp;
                cell4.innerHTML = '<button class="btn btn-primary Icons tableButton-delete" onclick="deleteItemInList(\'' + barcode + '\')">Delete</button>';
                handleSuccessMessage("Successfully Added to cart");
       	   		}
       	   },
       	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
       	});


  }
function deleteItemInList(barcode)
{
//   var length = jsonList.length;
   for(let i=0;i<jsonList.length;i++)
   {
      if(jsonList[i].barcode === barcode)
      {
          delete map[barcode];
         jsonList.splice(i,1);
         var tableBody = document.getElementById("input-form-table");
         tableBody.deleteRow(i+1);
         break;
      }
   }
}
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order-items";
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
                  getOrderList();
                  handleSuccessMessage("Successfully Added");
    	   },
    	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
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
            handleSuccessMessage("Successfully Deleted");
    	   },
    	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
    	});
}
function updateOrderItem()
{
   	//Get the ID
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
   	   		getOrderList();
   	   		 handleSuccessMessage("Successfully Updated");
   	   },
   	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
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
    	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
    	});
}
function displayOrderItemList2(data)
{
    var $tbody = $('#input-update-form-table').find('tbody');
    	$tbody.empty();
    	for(var i in data){
            		var e = data[i];
            		var buttonHtml = '<button class="btn btn-primary Icons tableButton-delete" onclick="deleteOrderItem(' + e.id + ')">Delete</button>'
                    		buttonHtml += ' <button class="btn btn-primary Icons tableButton-edit" onclick="displayEditOrderItem(' + e.id + ')">Edit</button>'
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
     var url = getOrderItemUrl() + "?id=" + id;
     	$.ajax({
     	   url: url,
     	   type: 'GET',
     	   success: function(data) {
     	   		displayOrderItem(data);
     	   },
     	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
     	});
}
function displayOrderItem(data)
{
    var orderId = $('#order-edit-form input[name=orderId]').val();
    $("#orderItem-edit-form input[name=barcode]").val(data.barcode);
    $("#orderItem-edit-form input[name=quantity]").val(data.quantity);
   	$("#orderItem-edit-form input[name=mrp]").val(data.sellingPrice);
   	$("#orderItem-edit-form input[name=id]").val(data.id);
   	$("#orderItem-edit-form input[name=orderId]").val(orderId);
   	$('#edit-orderItem-modal').modal('toggle');
}


//BUTTON ACTIONS for orders
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orders";
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
	         map={};
	         document.getElementById("page-number").value=1;
	   		 getOrderList();
	   		 handleSuccessMessage("Order Created");
	   		 $('#input-order-modal').modal('toggle');
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});

	return false;
}
function getCountTotalOrders()
{
        var url = getOrderUrl() + "/total";
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
  getOrderList();
}
function nextPage()
{
  var pageNo = document.getElementById("page-number").value;
  var page= parseInt(pageNo);
  document.getElementById("page-number").value = page + 1;
  getOrderList();
}
function checkLimit()
{
   var page = document.getElementById("page-number").value;
   var totalPage = document.getElementById("total-page").value;
   if(page>totalPage) document.getElementById("page-number").value=totalPage;
   getOrderList();
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
function getOrderList(){
    getCountTotalOrders();
             var pageNo=0;
             pageNo= document.getElementById("page-number").value;
             if(pageNo==0)pageNo=1;
           	var url = getOrderUrl() + "/limited" + "?pageNo=" + pageNo;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
	checkPreviousNext();
}

function deleteOrder(id){
	var url = getOrderUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getOrderList();
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
}
function displayOrderList(data){
var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
	for(var i in data){
        		var e = data[i];
        		 var dateAndTime = data[i].time
        		 var lastUpdate  = data[i].lastUpdate
                 var formattedDateAndTime = moment(dateAndTime,"YYYY-MM-DDTHH:mm:ss").format("MM/DD/YYYY HH:mm:ss");
                  var formattedDateAndTime1 = moment(lastUpdate,"YYYY-MM-DDTHH:mm:ss").format("MM/DD/YYYY HH:mm:ss");
        		var buttonHtml = '<button class="btn btn-primary Icons tableButton-delete button" id="orderDelete' + i + '"  onclick="confirmDelete(' + e.id + ')">Delete</button>'
        		buttonHtml += ' <button class="btn btn-primary Icons tableButton-edit button" onclick="editOrder(' + e.id + ')">Edit</button>'
                		buttonHtml += ' <button class="btn btn-primary Icons tableButton-view button" onclick="getOrderItems(' + e.id + ')">View</button>'
        		var row = '<tr>'
        		+ '<td>' + e.id + '</td>'
        		+ '<td>'  + formattedDateAndTime + '</td>'
        		+ '<td>'  + formattedDateAndTime1 + '</td>'
        		+ '<td>' + buttonHtml + '</td>'
        		+ '</tr>';
                $tbody.append(row);
                checkInvoiceGenerated(e.isInvoiceGenerated,i);
        	}
}
function confirmDelete(id) {
  if (confirm("Are you sure you want to delete this Order?")) {
    deleteOrder(id);
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
    	   		displayOrderItemList(data,id);
    	   		$('#order-invoice-form input[name=invoice]').val(id);
    	   		$('#orderItem-modal').modal('toggle');
    	   },
    	   error: function(jqXHR, textStatus, errorThrown) {
                                        handleAjaxError(jqXHR, textStatus, errorThrown);
                                }
    	});
}
function displayOrderItemList(data,orderId)
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
     var url = baseUrl + "/api/generateInvoices" + "/" + orderId;
     window.open(url);
      getOrderList();
}
function checkInvoiceGenerated(isInvoiceGenerated, id) {
  var btn = document.getElementById('orderDelete' + id);
  if (isInvoiceGenerated > 0) {
    btn.disabled = true;
  } else {
    btn.disabled = false;
  }
}

// Call checkInvoiceGenerated for each order on page load
var orders = document.querySelectorAll('[id^="orderDelete"]');
orders.forEach(function(order) {
  var id = order.id.replace('orderDelete', '');
  var isInvoiceGenerated = order.getAttribute('data-is-invoice-generated');
  checkInvoiceGenerated(isInvoiceGenerated, id);
});
function handleAjaxError(xhr, textStatus, errorThrown) {
  var errorMessage = "An error occurred while processing your request.";
  if (xhr.responseJSON && xhr.responseJSON.message) {
    errorMessage = xhr.responseJSON.message;
  }
  $('#error-modal').addClass('show');
  $('.toast-body').text(errorMessage);
  $('.error').toast({delay: 5000});
  $('.error').toast('show');
   $('#error-modal1').removeClass('show');
}
function handleError(errorMessage) {
  $('#error-modal').addClass('show');
  $('.toast-body').text(errorMessage);
  $('.error').toast({delay: 10000});
  $('.error').toast('show');
  $('#error-modal1').removeClass('show');
}
function handleSuccessMessage(successMessage) {
  $('#error-modal1').addClass('show');
  $('.toast-body1').text(successMessage);
  $('.success').toast({delay: 5000});
  $('.success').toast('show');
  $('#error-modal').removeClass('show');
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

