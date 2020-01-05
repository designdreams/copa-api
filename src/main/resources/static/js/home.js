var HOST = 'http://localhost:8080/';
// HOST= 'https://dd-project-c.appspot.com/';
 //var HOST= 'https://www.copayana.com/';


  function signOut() {

  alert('You are signing out from this website. If needed, Please sign out from google in your browser!');

    logOut();
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }

function logOut(){
document.location.href = "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue="+HOST;
}


function readTrip(email){

var data = JSON.stringify({
  "userId": email
});

var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.addEventListener("readystatechange", function () {

  if (this.readyState > 0) {
   document.getElementById("trips").innerHTML = 'Please wait! Pulling your trips.....';
    }

  if (this.readyState === 4) {
    console.log("got response success");
    var response = this.responseText;


//alert('got ');
    if(response) {
        var obj = JSON.parse(response);

        if(obj.respCode == 'NO_TRIPS_FOUND'){
                document.getElementById("trips").innerHTML = '<span class="no-trip">You do not have any active trips! Add using the menu.</span>';
        }else{
            //populateTripsHtml(obj);
            populateTripsHtmlNew('READ', obj);
        }
    }else{
    //alert(response);
        document.getElementById("trips").innerHTML = 'SORRY! PLEASE TRY LATER!';
    }
}
});

xhr.open("POST", HOST+"readTrip");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("x-app-trace-id", "app");
xhr.setRequestHeader("Accept", "*/*");
xhr.setRequestHeader("Cache-Control", "no-cache");
xhr.setRequestHeader("Accept-Encoding", "gzip, deflate");
xhr.setRequestHeader("Content-Length", "31");
xhr.setRequestHeader("Connection", "keep-alive");
xhr.setRequestHeader("cache-control", "no-cache");

xhr.send(data);

}





function populateTripsHtmlNew(action, obj){



if(action == 'FIND'){
var listDiv = document.getElementById("find-trips");
document.getElementById("find-trips").innerHTML = "";

}else{
var listDiv = document.getElementById("trips");
document.getElementById("trips").innerHTML = "";
}


var tripHead = document.createElement("h3");
tripHead.class = "trip-head";

var table = document.createElement('table');
table.class = "trip-table";

var thead = document.createElement('thead');
var tbody = document.createElement('tbody');

var hrow = document.createElement('tr');

var hcol_from = document.createElement('th');
var hcol_to = document.createElement('th');
var hcol_date = document.createElement('th');
var hcol_airways = document.createElement('th');
var hcol_with = document.createElement('th');
var hcol_email = document.createElement('th');
var hcol_trash = document.createElement('th');

hcol_from.appendChild(document.createTextNode("From"));
hcol_to.appendChild(document.createTextNode("To"));
hcol_date.appendChild(document.createTextNode("Date"));
hcol_airways.appendChild(document.createTextNode("Airways"));
hcol_with.appendChild(document.createTextNode("With"));
hcol_trash.appendChild(document.createTextNode(""));
if(action == 'FIND'){
hcol_email.appendChild(document.createTextNode("Contact"));
}
else{
tripHead.appendChild(document.createTextNode("Your Planned Trips"));
}

hrow.appendChild(hcol_from);
hrow.appendChild(hcol_to);
hrow.appendChild(hcol_date);
hrow.appendChild(hcol_airways);
hrow.appendChild(hcol_with);
hrow.appendChild(hcol_trash);

if(action == 'FIND'){
hrow.appendChild(hcol_email);
}

thead.appendChild(hrow);
table.appendChild(thead);

var list = obj.tripList;
var len = 0;

if(list){
len = obj.tripList.length;
}

for (var i = 0; i < len; ++i) {

  var row = document.createElement('tr');

    if(i%2 == 0){
        row.className = "roww";
    }else{
        row.className = "aroww";
    }

    //row.padding="2%";

  var col_from = document.createElement('td');
  var col_to = document.createElement('td');
  var col_date = document.createElement('td');
  var col_airways = document.createElement('td');
  var col_travellingWith = document.createElement('td');
  var col_trash = document.createElement('td');

  if(action == 'FIND'){
  var col_email = document.createElement('td');
    }

  col_from.class = "coll";
  col_to.class = "coll";
  col_date.class = "coll";
  col_airways.class = "coll";
  col_travellingWith.class = "coll";
  col_trash.class= "coll";

  if(action == 'FIND'){
  col_email.class = "coll";
//<a tabindex="0" class="btn btn-lg btn-danger" role="button" data-toggle="popover" data-trigger="focus" title="Dismissible popover" data-content="And here's some amazing content. It's very engaging. Right?">Dismissible popover</a>
    }

  col_from.appendChild(document.createTextNode(obj.tripList[i].source));
  col_to.appendChild(document.createTextNode(obj.tripList[i].destination));

  var date = "";
  if(obj.tripList[i].travelStartDate == null ||
  obj.tripList[i].travelStartDate == ""){
  date = "Not Planned";
  }else{
  date = obj.tripList[i].travelStartDate;
  }

  col_date.appendChild(document.createTextNode(date));

    var airways = "";
    if(obj.tripList[i].airways == null ||
    obj.tripList[i].airways == ""){
    airways = "Any";
    }else{
    airways = obj.tripList[i].airways;
    }

  col_airways.appendChild(document.createTextNode(airways));
  col_travellingWith.appendChild(document.createTextNode(obj.tripList[i].travellingWith));

  if(action == 'FIND'){
  //col_email.appendChild(document.createTextNode(obj.tripList[i].travellerId));

    var detail = document.createElement('a');


    var title = document.createAttribute("title");
    title.value = obj.tripList[i].travellerId;

     var href = document.createAttribute("href");
        href.value = "#";

        var id = document.createAttribute("id");
                id.value = "myBtn";

    var onclick = document.createAttribute("onclick");
       // onclick.value = "openInfo(); return false;";
       var contact_info = "<b>Email Me ::</b> "+obj.tripList[i].travellerId+"<br>"+
       	"<b>Notes :: </b>"+obj.tripList[i].notes;
      
         onclick.value = "modalblock('"+contact_info+"'); return false;"

    var datatoggle = document.createAttribute("data-toggle");

        datatoggle.value = "popover";

    var datacontent = document.createAttribute("data-content");
        datacontent.value = title.value;


    detail.setAttributeNode(title);
    detail.setAttributeNode(onclick);
    detail.setAttributeNode(href);
    detail.setAttributeNode(id);

    //detail.setAttributeNode(datatoggle);
   // detail.setAttributeNode(datacontent);

    detail.appendChild(document.createTextNode("Info"));
   col_email.appendChild(detail);

   }else{
	   var trashIt = document.createElement('a');


	     var href = document.createAttribute("href");
	        href.value = "#";

	        var id = document.createAttribute("id");
	                id.value = "delBtn";

	    var onclick = document.createAttribute("onclick");
	    onclick.value="deleteTrip()";
	       

	    var datatoggle = document.createAttribute("data-toggle");

	        datatoggle.value = "popover";

	   // var datacontent = document.createAttribute("data-content");
	     //   datacontent.value = title.value;


	       // trashIt.setAttributeNode(title);
	        trashIt.setAttributeNode(onclick);
	        trashIt.setAttributeNode(href);
	        trashIt.setAttributeNode(id);
	        trashIt.innerHTML="<i class='fa fa-trash-o'></i>";

	    
	        col_trash.appendChild(trashIt);
   }

    row.appendChild(col_from);
    row.appendChild(col_to);
    row.appendChild(col_date);
    row.appendChild(col_airways);
    row.appendChild(col_travellingWith);
    row.appendChild(col_trash);

    if(action == 'FIND'){
    row.appendChild(col_email);

    }
tbody.appendChild(row);

}

table.appendChild(tbody);
//tableDiv.appendChild(table);

listDiv.appendChild(tripHead);
listDiv.appendChild(table);
}

//
//<div class="table-responsive"><table class="table"><thead>
//<tr><th>#</th><th>From</th><th>To</th><th>Date</th><th>Airways</th><th>Email</th></tr>
//</thead><tbody>
//    <tr><td>1</td><td>Anna</td><td>Pitt</td> <td>35</td><td>New York</td><td>USA</td></tr>
//</tbody></table></div>


function openInfo(){
alert("hello");
//alert(this)
}

function findTrip(){

var source = document.getElementById("src").value.substring(0, 3);
var destination = document.getElementById("dest").value.substring(0, 3);
var date = document.getElementById("trip-date").value;

var data = JSON.stringify({
	"source": source,
	"destination": destination,
	"travelStartDate" : date
	});

var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.addEventListener("readystatechange", function () {

  if (this.readyState > 0) {
   document.getElementById("find-trips").innerHTML = 'Please wait! Pulling trips.....';
    }

  if (this.readyState === 4) {
    console.log("response is success");
    var response = this.responseText;


//alert('got ');
    if(response) {
        var obj = JSON.parse(response);

        if(obj.respCode == 'NO_TRIPS_FOUND'){
                document.getElementById("find-trips").innerHTML = 'You do not have any matching trips!';
        }else{
            //populateTripsHtml(obj);
            populateTripsHtmlNew('FIND', obj);
        }
    }else{
    //alert(response);
        document.getElementById("find-trips").innerHTML = 'SORRY! PLEASE TRY LATER!';
    }
}
});

xhr.open("POST", HOST+"findTrip");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("x-app-trace-id", "app");
xhr.setRequestHeader("Accept", "*/*");
xhr.setRequestHeader("Cache-Control", "no-cache");
xhr.setRequestHeader("Accept-Encoding", "gzip, deflate");
xhr.setRequestHeader("Content-Length", "31");
xhr.setRequestHeader("Connection", "keep-alive");
xhr.setRequestHeader("cache-control", "no-cache");

xhr.send(data);

}


function addTrip(id){

var source = document.getElementById("trip-from").value.substring(0, 3);
var destination = document.getElementById("trip-to").value.substring(0, 3);
var date = document.getElementById("trip-date").value;
var airways = document.getElementById("airways").value;
var tripWith = document.getElementById("trip-with").value;
var isTicketBooked = document.getElementById("trip-box-0").checked;
var isFinalDestination = document.getElementById("trip-box-1").checked;
var canTakePackageInd = document.getElementById("trip-box-2").checked;
var notes = document.getElementById('notes').value;

var data = JSON.stringify({
    "trip": {
        "travellerId": id,
        "source": source,
        "destination": destination,
        "travelStartDate": date,
        "mode": '',
        "airways": airways,
        "travellingWith": tripWith,
        "isTicketBooked": isTicketBooked,
        "isDomestic": false,
        "canTakePackageInd": canTakePackageInd,
        "isFinalDestination": isFinalDestination,
        "notes": notes
    }
	});

var xhrr = new XMLHttpRequest();
xhrr.withCredentials = true;

xhrr.addEventListener("readystatechange", function () {

//  if (this.readyState > 0) {
//   document.getElementById("add-trip-result").innerHTML = 'Please wait! Pulling trips.....';
//    }

  if (this.readyState === 4) {

    console.log("success response");
    var response = this.responseText;

    if(response) {
        var obj = JSON.parse(response);
//           document.getElementById("add-trip-result").innerHTML = '';
           populateResult(obj);
    }else{
        document.getElementById("trips").innerHTML = 'SORRY! PLEASE TRY LATER!';
    }
}
});

xhrr.open("POST", HOST+"createTrip");
xhrr.setRequestHeader("Content-Type", "application/json");
xhrr.setRequestHeader("x-app-trace-id", "app");
xhrr.setRequestHeader("Accept", "*/*");
xhrr.setRequestHeader("Cache-Control", "no-cache");
xhrr.setRequestHeader("Accept-Encoding", "gzip, deflate");
xhrr.setRequestHeader("Content-Length", "31");
xhrr.setRequestHeader("Connection", "keep-alive");
xhrr.setRequestHeader("cache-control", "no-cache");

xhrr.send(data);

}

function populateResult(obj){

//var listDiv = document.getElementById("add-trip-result");

if(obj && obj.respCode == "SUCCESS"){

//listDiv.innerHTML = "Great! Your trip is added successfully!";
openAddResponseModal("Great! Your trip is added successfully!");

// redirect to home
window.location.href = HOST+"home";


}else if(obj && obj.respCode == "INVALID_SAME_DAY_DUPLICATE_TRIP"){

//listDiv.innerHTML = ;
openAddResponseModal("Trip already added!!");

}else if(obj && obj.respCode.includes("DUPLICATE_TRIP")){

 //listDiv.innerHTML = "Trip already added!!";
openAddResponseModal("Trip already added!!");

 }else{
//listDiv.innerHTML = "Could not add Trip. Please Try Again!";
openAddResponseModal("Could not add Trip. Please Try Again!");

}

}



/* contact info modal */

var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementsByClassName("close")[0];


function modalblock(email){
modal = document.getElementById("myModal");
//span = document.getElementsByClassName("close")[0];
span = document.getElementById("modal-close");
document.getElementById("contact-info").innerHTML = email;
  modal.style.display = "block";

}

// When the user clicks on <span> (x), close the modal
function closemodal(){
  modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
//window.onclick = function(event) {
//  if (event.target == modal || event.target == response_modal) {
//    modal.style.display = "none";
//  }
//}

/* add trip result  modal */

// Get the modal
var response_modal ="";

// Get the button that opens the modal
//var btn = document.getElementById("response-modal-btn");

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("close")[0];

// When the user clicks on the button, open the modal
function openAddResponseModal(addResponse) {
  response_modal = document.getElementById("myModal");
  document.getElementById("add-response-info").innerHTML = addResponse;
  response_modal.style.display = "block";
}

// When the user clicks on <span> (x), close the modal
function closeAddResponseModal() {
  response_modal.style.display = "none";
}


function deleteTrip(){
	window.alert("Trip Deleted!!")
}



// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
  if (event.target == modal || event.target == response_modal) {
  if(response_modal.style){
      response_modal.style.display = "none";
  }

    if(modal.style){
        modal.style.display = "none";
    }

  }
}
