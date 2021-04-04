 function uploadSingleFile() {

    	 var file=document.getElementById("upfile").files;
    	 if(file.length==0){
           // alert("Please Select file");
           Toastify({
                           text: "Please Select file ",
                           duration: 3000,
                           gravity: "top",
                           position: 'right',
                           backgroundColor: "Red",
                           close: true
                       }).showToast();
         }
		else{
        var formData = new FormData();
        formData.append("file", file[0]);

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/api/uploadExcel",true);


           xhr.onload = function() {
                   console.log(xhr.responseText);
                   var response = xhr.responseText;
                   var result = JSON.parse(xhr.responseText);
                   console.log(result);
                   if(result.message= "Successful") {
                    Toastify({
                                              text: "File Upload Successfully ",
                                              duration: 3000,
                                              gravity: "top",
                                              position: 'right',
                                              backgroundColor: "#01cf68",
                                              close: true
                                          }).showToast();

                      /* alert("File Uploaded Successfully");*/
                     /* window.location.reload();*/
document.getElementById("upfile").value="";
document.getElementById("yourBtn").innerHTML="Click To Upload A File";

                   } else {
                    Toastify({
                                              text: "File Already Uploaded ",
                                              duration: 3000,
                                              gravity: "top",
                                              position: 'right',
                                              backgroundColor: "#01cf68",
                                              close: true
                                          }).showToast();
                  // alert("File already Uploaded");
                  /* window.location.reload();*/
                  document.getElementById("upfile").value="";
            document.getElementById("yourBtn").innerHTML="Click To Upload A File";

                              }
               }

               xhr.send(formData);
       		}

           }