
function getUserUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/supervisor/user";
}

//BUTTON ACTIONS
function addUser(event){
	//Set the values to update
	 event.preventDefault();
	var $form = $("#user-form");
	var json = toJson($form);
	var url = getUserUrl();
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },	   
	   success: function(response) {
	    document.getElementById("user-form").reset();
	     handleSuccessMessage("User Added");
	   		getUserList();    
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                            handleAjaxError(jqXHR, textStatus, errorThrown);
                    }
	});

	return false;
}
function getCountTotalUsers()
{
        var url = getUserUrl() + "/total";
        	$.ajax({
        	   url: url,
        	   type: 'GET',
        	   success: function(data) {
        	   		document.getElementById("total-page").value = Math.ceil(data/10);
        	   		checkPreviousNext();
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
  getUserList();
}
function nextPage()
{
  var pageNo = document.getElementById("page-number").value;
  var page= parseInt(pageNo);
  document.getElementById("page-number").value = page + 1;
  getUserList();
}

function checkLimit()
{
   var page = document.getElementById("page-number").value;
   var totalPage = document.getElementById("total-page").value;
   if(page>totalPage) document.getElementById("page-number").value=totalPage;
   getUserList();
}
function getUserList(){
     getCountTotalUsers();
     var pageNo=0;
     pageNo= document.getElementById("page-number").value;
     if(pageNo==0)pageNo=1;
    var url = getUserUrl() + "/limited" + "?pageNo=" + pageNo;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayUserList(data);   
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                    handleAjaxError(jqXHR, textStatus, errorThrown);
            }
	});
	checkPreviousNext();
}

function deleteUser(id){
	var url = getUserUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getUserList();
	   		handleSuccessMessage("User successFully Deleted");
	   },
	   error: function(jqXHR, textStatus, errorThrown) {
                      handleAjaxError(jqXHR, textStatus, errorThrown);
                    }

	});
}

//UI DISPLAY METHODS

function displayUserList(data){
	var $tbody = $('#user-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml = '<button class="btn btn-primary Icons tableButton-delete button tableButton-delete" onclick="deleteUser(' + e.id + ')">Delete</button>'
		var row = '<tr>'
		+ '<td>' + e.email + '</td>'
		+ '<td>' + e.role + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

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
    if(page==1 && page==totalPage){
        previousBtn.disabled=true;
        nextBtn.disabled=true;
    }
    else if(page==1){
        previousBtn.disabled=true;
        nextBtn.disabled=false;
     }
    else if(page==totalPage){
        nextBtn.disabled=true;
        previousBtn.disabled=false;
    }
    else{
        previousBtn.disabled=false;
        nextBtn.disabled=false;
    }
}

//INITIALIZATION CODE
function init(){
	$('#user-form').submit(addUser);
	$('#refresh-data').click(getUserList);
}

$(document).ready(init);
$(document).ready(getUserList);

