

function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/products";
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	 event.preventDefault();
	var $form = $("#product-form");
	var json = toJson($form);
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   document.getElementById("product-form").reset();
	   		getProductList();
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});

	return false;
}

function updateProduct(event){
    event.preventDefault();
	//Get the ID
	 console.log(id);
	var id = $("#product-edit-form input[name=id]").val();
	var url = getProductUrl() + "/" + id;

	//Set the values to update
	var $form = $("#product-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	    document.getElementById("product-edit-form").reset();
	   		getProductList();
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProductList(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
}

function deleteProduct(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getProductList();
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
	var file = $('#productFile')[0].files[0];
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
	var url = getProductUrl();

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

function displayProductList(data){
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	console.log(data);
	for(var i in data){
		var e = data[i];
		var barcodeLength = (e.brand).length;
        var barcode=e.barcode;
   		if(barcodeLength>20)
   		{
           barcode = (e.barcode).slice(0,20)+'...';
      	}

        var brandLength = (e.brand).length;
        var brand=e.brand;
   		if(brandLength>20)
   		{
           brand = (e.brand).slice(0,20)+'...';
      	}

      	var categoryLength = (e.category).length;
        var category = e.category;
        if(categoryLength>20)
        {
           category = (e.category).slice(0,20)+'...';
        }

        var nameLength = (e.name).length;
        var name=e.name;
        if(nameLength>20)
        {
           name = (e.name).slice(0,20)+'...';
        }
		var buttonHtml = ' <button class="btn btn-primary  Icons tableButton-edit button" onclick="displayEditProduct(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + barcode + '</td>'
		+ '<td>'  + brand + '</td>'
		+ '<td>'  + category + '</td>'
		+ '<td>'  + name + '</td>'
		+ '<td>'  + parseFloat(e.mrp).toFixed(2) + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	paging();
}
function confirmDelete(id) {
  if (confirm("Are you sure you want to delete this Product?")) {
    deleteProduct(id);
  }
  }
function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayProduct(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#productFile');
	$file.val('');
	$('#productFileName').html("Choose File");
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
	var $file = $('#productFile');
	var fileName = $file.val();
	$('#productFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-product-modal').modal('toggle');
}

function displayProduct(data){
    $("#product-edit-form input[name=id]").val(data.id);
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=brand]").val(data.brand);
	$("#product-edit-form input[name=category]").val(data.category);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$('#edit-product-modal').modal('toggle');
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
	$('#product-form').submit(addProduct);
	$('#product-edit-form').submit(updateProduct);
	$('#refresh-data').click(getProductList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#productFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getProductList);

// Set the number of rows per page
var rowsPerPage = 10;
function paging(){
// Get the table element
var table = document.getElementById("product-table");

// Get the table body element
var tableBody = table.getElementsByTagName("tbody")[0];

// Get the number of rows in the table
var numRows = tableBody.rows.length;

// Calculate the number of pages
var numPages = Math.ceil(numRows / rowsPerPage);
console.log(numRows);
// Create the pagination links
var pagination = document.getElementById("pagination");
for (var i = 1; i <= numPages; i++) {
  var link = document.createElement("a");
  link.href = "#";
  link.textContent = i;
  link.onclick = function() {
    showPage(this.textContent);
    return false;
  };
  pagination.appendChild(link);
}

// Function to show the specified page
function showPage(pageNum) {
  // Calculate the start and end rows for the page
  var startRow = (pageNum - 1) * rowsPerPage;
  var endRow = pageNum * rowsPerPage;

  // Loop through all rows, hiding or showing them as necessary
  for (var i = 0; i < numRows; i++) {
    if (i < startRow || i >= endRow) {
      tableBody.rows[i].style.display = "none";
    } else {
      tableBody.rows[i].style.display = "";
    }
  }
}
}