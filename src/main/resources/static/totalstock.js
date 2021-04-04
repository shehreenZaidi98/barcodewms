function getTotalStockManagementData() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            console.log(this.responseText);

            Toastify({
                text: "Data Fetched Successfully",
                duration: 3000,
                gravity: "top",
                position: 'right',
                backgroundColor: "#01cf68",
                close: true
            }).showToast();

            var result = JSON.parse(this.responseText);
            for (var i = 0; i < result.sum.length; i++) {
                document.getElementById("todayStockTable").innerHTML +=
                    "<tr>" +
                    '<td>' + (i + 1) + '</td>' +
                    '<td>' + result.sum[i].name_of_item + '</td>' +
                    '<td>' + result.sum[i].no_of_pcs + '</td>' +
                    '<td>' + result.sum[i].per_pcs_weight + '</td>' +
                    '<td>' + result.sum[i].packaging + '</td>' +
                    '<td>' + result.sum[i].carton_gross_weight + '</td>' +
                    '<td>' + result.sum[i].hsn + '</td>' +
                    '</tr>';
            }
        }
    };
    xhttp.open("GET", "/api/getNameOfItem", true);
    xhttp.send();
}
