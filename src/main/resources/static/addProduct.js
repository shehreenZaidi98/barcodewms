function skuList(){
	var xhttp1 = new XMLHttpRequest();
	xhttp1.onreadystatechange = function() {
	    if (this.readyState == 4 && this.status == 200) {
	       // Typical action to be performed when the document is ready:
	        var response = xhttp1.responseText;
	        var result=JSON.parse(response);
	        console.log(result);
	      var select1=document.getElementById("select1");
	      select1.innerHTML="";
          for(var key in result.vendor){
        	  select1.innerHTML+='<option value='+result.vendor[key]+'>';
          }
	    }
	};
	xhttp1.open("GET", "/api/getVendorData", true);

	xhttp1.send();
	}

window.onload=skuList();