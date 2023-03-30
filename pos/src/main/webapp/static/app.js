function getBrandCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brands";
}
//HELPER METHOD
function getCheckUser(){

  var btn = document.getElementById("add-product");
  var btn1 = document.getElementById("add-brandCategory");
  var btn2 = document.getElementById("add-inventory");
    if(btn===null&&btn2===null&&btn1===null)
    {

      var editBtn = document.getElementsByClassName("editbtn");
      for (let i = 0; i < editBtn.length; i++) {
             editBtn[i].disabled = true;
      }
    }
}
function toJson($form){
    var serialized = $form.serializeArray();
    console.log(serialized);
    var s = '';
    var data = {};
    for(s in serialized){
        data[serialized[s]['name']] = serialized[s]['value']
    }
    var json = JSON.stringify(data);
    return json;
}

function handleAjaxError(response){
	var response = JSON.parse(response.responseText);
	alert(response.message);
}

function readFileData(file, callback){
	var config = {
		header: true,
		delimiter: "\t",
		skipEmptyLines: "greedy",
		complete: function(results) {
			callback(results);
	  	}	
	}
	Papa.parse(file, config);
}


function writeFileData(arr){
	var config = {
		quoteChar: '',
		escapeChar: '',
		delimiter: "\t"
	};
	
	var data = Papa.unparse(arr, config);
    var blob = new Blob([data], {type: 'text/tsv;charset=utf-8;'});
    var fileUrl =  null;

    if (navigator.msSaveBlob) {
        fileUrl = navigator.msSaveBlob(blob, 'download.tsv');
    } else {
        fileUrl = window.URL.createObjectURL(blob);
    }
    var tempLink = document.createElement('a');
    tempLink.href = fileUrl;
    tempLink.setAttribute('download', 'download.tsv');
    tempLink.click(); 
}

function fetchItems(selectID, varType) {
  // Fetch the items from the backend
  var url = getBrandCategoryUrl();
  $.ajax({
    url: url,
    type: 'GET',
    success: function(data) {
      var select = document.getElementById(selectID);
      if (Array.isArray(data) && data.every(item => item.hasOwnProperty("brand"))) {
        for (var i in data) {
          var e = data[i];
          var option = document.createElement("option");
          if (varType == "brand") {
            option.text = e.brand;
            option.value = e.brand;
          }  else if (varType === "category") {
            option.text = e.category;
            option.value = e.category;
          }
          select.add(option);
        }
      } else {
        console.error("Invalid data format received from the backend.");
      }
    },
    error: function(xhr, textStatus, errorThrown) {
      console.error("Error fetching data from the backend:", textStatus);
    }
  });
}

fetchItems("inputBrand", "brand");
fetchItems("inputCategory", "category");
function toggleLogout()
{
   $('#logout-model').modal('toggle');
}
function init()
{
  $('#logout').click(toggleLogout);
}
$(document).ready(init);