
function getBrandCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brands";
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
	     document.getElementById("page-number").value=1;
	      handleSuccessMessage("successfully added");
	   		getBrandCategoryList();
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});

	return false;
}

function updateBrandCategory(event){
    event.preventDefault();
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
	    handleSuccessMessage("Successfully Updated");
	   		getBrandCategoryList();
	   		$('#edit-brandCategory-modal').modal('toggle');
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});

	return false;
}
function getCountTotalBrands()
{
        var url = getBrandCategoryUrl() + "/total";
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
    checkPreviousNext();
}
function prevPage()
{
  var pageNo = document.getElementById("page-number").value;
  if(pageNo>0)document.getElementById("page-number").value = pageNo - 1;
  getBrandCategoryList();
}
function nextPage()
{
  var pageNo = document.getElementById("page-number").value;
  var page= parseInt(pageNo);
  document.getElementById("page-number").value = page + 1;
  getBrandCategoryList();
}

function checkLimit()
{
   var page = document.getElementById("page-number").value;
   var totalPage = document.getElementById("total-page").value;
   if(page>totalPage) document.getElementById("page-number").value=totalPage;
   getBrandCategoryList();
}
function getBrandCategoryList(){
     getCountTotalBrands();
         var pageNo=0;
         pageNo= document.getElementById("page-number").value;
         if(pageNo==0)pageNo=1;
       	var url = getBrandCategoryUrl() + "/limited" + "?pageNo=" + pageNo;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayBrandCategoryList(data);
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
	});
	checkPreviousNext();
}

function deleteBrandCategory(id){
	var url = getBrandCategoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getBrandCategoryList();
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
	var file = $('#brandCategoryFile')[0].files[0];
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
		var buttonHtml = ' <button class="btn btn-primary Icons tableButton-edit button editbtn" onclick="displayEditBrandCategory(' + e.id + ')">Edit</button>'
		var row = '<tr>'
		+ '<td>' + brand + '</td>'
		+ '<td>'  + category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
	 getCheckUser();
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
	   error: function(jqXHR, textStatus, errorThrown) {
                                    handleAjaxError(jqXHR, textStatus, errorThrown);
                            }
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
function enableUpload(){
  var btn = document.getElementById("process-data");
  btn.disabled=false;
}
function displayUploadData(){
 	resetUploadDialog();
 	 var btn = document.getElementById("process-data");
      btn.disabled=true;
	$('#upload-brandCategory-modal').modal('toggle');
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

function displayBrandCategory(data){
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

