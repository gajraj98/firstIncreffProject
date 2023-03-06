
function getBrandCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addBrandCategory(event){
    event.preventDefault();
	var $form = $("#brandCategory-form");
	var json = toJson($form);
	var url = getBrandCategoryUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   document.getElementById("brandCategory-form").reset();
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});

	return false;
}

function updateBrandCategory(event){
    event.preventDefault();
	$('#edit-brandCategory-modal').modal('toggle');
	//Get the ID
	var id = $("#brandCategory-edit-form input[name=id]").val();
	var url = getBrandCategoryUrl() + "/" + id;
	//Set the values to update
	var $form = $("#brandCategory-edit-form");
	var json = toJson($form);

	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   document.getElementById("brandCategory-edit-form").reset();
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});

	return false;
}


function getBrandCategoryList(){
	var url = getBrandCategoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandCategoryList(data);
	   },
	   error: handleAjaxError
	});
}

function deleteBrandCategory(id){
	var url = getBrandCategoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getBrandCategoryList();
	   },
	   error: handleAjaxError
	});
}

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandCategoryFile')[0].files[0];
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
	var url = getBrandCategoryUrl();

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

function displayBrandCategoryList(data){
	var $tbody = $('#brandCategory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
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
		var buttonHtml = '<button  class="btn btn-primary Icons tableButton-delete button" onclick="confirmDelete(' + e.id + ')">delete</button>'
		buttonHtml += ' <button class="btn btn-primary Icons tableButton-edit button" onclick="displayEditBrandCategory(' + e.id + ')">edit</button>'
		var row = '<tr>'
		+ '<td>' + brand + '</td>'
		+ '<td>'  + category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}
function confirmDelete(id) {
  if (confirm("Are you sure you want to delete this Brand Category?")) {
    deleteBrandCategory(id);
  }
  }
function displayEditBrandCategory(id){
	var url = getBrandCategoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandCategory(data);
	   },
	   error: handleAjaxError
	});
}

function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandCategoryFile');
	$file.val('');
	$('#brandCategoryFileName').html("Choose File");
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
	var $file = $('#brandCategoryFile');
	var fileName = $file.val();
	$('#brandCategoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-brandCategory-modal').modal('toggle');
}

function displayBrandCategory(data){
    console.log(data);
	$("#brandCategory-edit-form input[name=brand]").val(data.brand);
	$("#brandCategory-edit-form input[name=category]").val(data.category);
	$("#brandCategory-edit-form input[name=id]").val(data.id);
	$('#edit-brandCategory-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#brandCategory-form').submit(addBrandCategory);
	$('#edit-brandCategory-modal').submit(updateBrandCategory);
	$('#refresh-data').click(getBrandCategoryList);
	$('#upload-data').click(displayUploadData);
	$('#process-data').click(processData);
	$('#download-errors').click(downloadErrors);
    $('#brandCategoryFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getBrandCategoryList);

