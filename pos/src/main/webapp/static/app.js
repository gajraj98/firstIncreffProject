function getBrandCategoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}
//HELPER METHOD
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
function myFunction(divID, varType) {
  // Declare variables
  var input, filter, div, a, i, txtValue;
  input = document.getElementById(varType);
  filter = input.value.toUpperCase();
  div = document.getElementById(divID);

  if (div.children.length === 0) {
    fetchItems(divID, varType);
  }

  for (i = 0; i < div.children.length; i++) {
    txtValue = div.children[i].textContent || div.children[i].innerText;
    if (txtValue.toUpperCase().indexOf(filter) > -1) {
      div.children[i].style.display = "";
    } else {
      div.children[i].style.display = "none";
    }
  }
}

function fetchItems(divID, varType) {
  // Fetch the items from the backend
  var url = getBrandCategoryUrl();
  $.ajax({
    url: url,
    type: 'GET',
    success: function(data) {
      var div = document.getElementById(divID);
      // Check that the data is an array of objects with a "brand" property
      if (Array.isArray(data) && data.every(item => item.hasOwnProperty("brand"))) {
        for (var i in data) {
          var e = data[i];
          var a = document.createElement("a")
          if (varType == "inputBrand") {
            a.innerText = e.brand;
          } else {
            a.innerText = e.category;
          }
          a.classList.add("dropdown-item");
          a.href = "#";
          div.appendChild(a);
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

function checkInput(divID) {
  var input = document.getElementById(divID).previousElementSibling;
  var dropdown = document.getElementById(divID);

  if (input && input.value === "") {
    dropdown.classList.remove("show");
  }
}

function toggleDropdown(divID) {
  var input = document.getElementById(divID).previousElementSibling;
  var dropdown = document.getElementById(divID);

  if (input && input.value === "") {
    dropdown.classList.remove("show");
  } else {
    dropdown.classList.add("show");
  }
}

var inputElement = document.getElementById("inputBrand");
inputElement.addEventListener("keyup", function() {
  toggleDropdown("myDropdown");
});

var inputElement = document.getElementById("inputCategory");
inputElement.addEventListener("keyup", function() {
  toggleDropdown("myDropdown1");
});


// Hide dropdown menus when clicking outside of them
window.onclick = function(event) {
  if (!event.target.matches('.dropdown-input')) {
    var dropdowns = document.getElementsByClassName("dropdown-menu");
    for (var i = 0; i < dropdowns.length; i++) {
      var openDropdown = dropdowns[i];
      if (openDropdown.classList.contains('show')) {
        openDropdown.classList.remove('show');
      }
    }
  }
}
