function getDashboardData() {
    var xhttp1 = new XMLHttpRequest();
    xhttp1.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            // Typical action to be performed when the document is ready:
            var response = xhttp1.responseText;
            var result = JSON.parse(response);
            console.log(result);
            for(var i=0;i<result.data.length;i++){

               document.getElementById("todaySTCount").innerHTML=result.data[i].todayTotalIn;
               document.getElementById("totalSTCount").innerHTML=result.data[i].todayIn;
                document.getElementById("todayPTCount").innerHTML=result.data[i].totalIn;
                document.getElementById("totalPTCount").innerHTML=result.data[i].TotalOut;

            }}
            }
            xhttp1.open("GET", "/api/getTotalInTotalOut", true);

                xhttp1.send();
            }

window.onload=getDashboardData();
