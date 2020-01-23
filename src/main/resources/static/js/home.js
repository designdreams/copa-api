var HOST = 'http://localhost:8080/';
 //var HOST= 'https://www.copayana.com/';


/* important */
var hosturl = window.location.href;


if(!hosturl.includes("https") && hosturl.includes("copayana")){
window.location.href='https://www.copayana.com';
}


  function signOut() {

  alert('You are signing out from this website. If you wish, sign out from your google account in your browser!');

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


    if(response) {
        var obj = JSON.parse(response);

        if(obj.respCode == 'NO_TRIPS_FOUND'){
                document.getElementById("trips").innerHTML = '<div class="no-trip">You do not have any active trips! Add using Add Trip link in the menu</div>';
        }else{
            populateTripsHtmlNew('READ', obj);
        }
    }else{
        document.getElementById("trips").innerHTML = 'SORRY! PLEASE TRY LATER!';
    }
}
});

xhr.open("POST", HOST+"readTrip");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("x-app-trace-id", "app");
xhr.setRequestHeader("Accept", "*/*");
xhr.setRequestHeader("Cache-Control", "no-cache");
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

var noteTxt = document.createElement("div");
noteTxt.id = "add-trips-in-find";
noteTxt.appendChild(document.createTextNode("Still you can add your trip in Add Trip page to get more options!"));

var tripHead = document.createElement("h3");
tripHead.class = "trip-head";
tripHead.appendChild(document.createTextNode("Matching Trips Found"));

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
var hcol_action = document.createElement('th');

hcol_from.appendChild(document.createTextNode("From"));
hcol_to.appendChild(document.createTextNode("To"));
hcol_date.appendChild(document.createTextNode("Date"));
hcol_airways.appendChild(document.createTextNode("Airways"));
hcol_with.appendChild(document.createTextNode("With"));

if(action == 'FIND'){
hcol_email.appendChild(document.createTextNode("Contact"));
}
else{
hcol_action.appendChild(document.createTextNode("Action"));
tripHead.appendChild(document.createTextNode("Your Planned Trips"));
}

hrow.appendChild(hcol_from);
hrow.appendChild(hcol_to);
hrow.appendChild(hcol_date);
hrow.appendChild(hcol_airways);
hrow.appendChild(hcol_with);

if(action == 'FIND'){
hrow.appendChild(hcol_email);
}else{
hrow.appendChild(hcol_action);
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

  var col_from = document.createElement('td');
  var col_to = document.createElement('td');
  var col_date = document.createElement('td');
  var col_airways = document.createElement('td');
  var col_travellingWith = document.createElement('td');
  var col_delete = document.createElement('td');

  if(action == 'FIND'){
  var col_email = document.createElement('td');
    }

  col_from.class = "coll";
  col_to.class = "coll";
  col_date.class = "coll";
  col_airways.class = "coll";
  col_travellingWith.class = "coll";
  col_delete.class = "coll";

  if(action == 'FIND'){
  col_email.class = "coll";
    }

  col_from.appendChild(document.createTextNode(obj.tripList[i].source));
  col_to.appendChild(document.createTextNode(obj.tripList[i].destination));

  var date = "";
  if(obj.tripList[i].travelStartDate == null ||
  obj.tripList[i].travelStartDate == ""){
  date = "Any";
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
    //col_delete.appendChild(document.createTextNode("delete"));


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
    var notes =  obj.tripList[i].notes;
    var contact_info = '<h4> Traveller Details </h4><hr><div class="info-note"> You can copy and send email to the traveller.</div><br><b>Email::</b> '+obj.tripList[i].travellerId;

    //https://mail.google.com/mail/?view=cm&fs=1&to=email@domain.com

    if(notes && notes.length > 0){
    contact_info = contact_info + "<br><b>Notes :: </b>"+notes+"";
    }


         onclick.value = "modalblock('"+contact_info+"'); return false;"

    var datatoggle = document.createAttribute("data-toggle");

        datatoggle.value = "popover";

    var datacontent = document.createAttribute("data-content");
        datacontent.value = title.value;


    detail.setAttributeNode(title);
    detail.setAttributeNode(onclick);
    detail.setAttributeNode(href);
    detail.setAttributeNode(id);
    detail.appendChild(document.createTextNode("Info"));
    col_email.appendChild(detail);

   }else{

       var delete_link = document.createElement('a');
       var delete_href = document.createAttribute("href");
       var delete_onclick = document.createAttribute("onclick");
       delete_onclick.value = "deleteTrip('"+obj.tripList[i].travellerId+"','"+obj.tripList[i].source+"','"+obj.tripList[i].destination+"','"+obj.tripList[i].travelStartDate+"'); return false;"
       delete_link.innerHTML="<i class='fa fa-trash-o'></i>";

       delete_link.setAttributeNode(delete_href);
       delete_link.setAttributeNode(delete_onclick);
       col_delete.appendChild(delete_link);

   }

    row.appendChild(col_from);
    row.appendChild(col_to);
    row.appendChild(col_date);
    row.appendChild(col_airways);
    row.appendChild(col_travellingWith);

    if(action == 'FIND'){
    row.appendChild(col_email);

    }else{
        row.appendChild(col_delete);
    }
tbody.appendChild(row);

}

table.appendChild(tbody);
if(action == 'FIND'){
listDiv.appendChild(tripHead);
}
listDiv.appendChild(table);

if(action == 'FIND'){
listDiv.appendChild(noteTxt);
}

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

     if(response) {
         var obj = JSON.parse(response);

         if(obj.respCode == 'NO_TRIPS_FOUND'){
                 document.getElementById("find-trips").innerHTML = '<div class="no-trips-in-find">You do not have any matching trips! Try again later or search for other locations! You can also Add your Trip, so other travellers can contact you.</div>';
         }else{
             //populateTripsHtml(obj);
             populateTripsHtmlNew('FIND', obj);
         }
     }else{
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

 function findTripOpen(){

 var source = document.getElementById("src").value.substring(0, 3);
 var destination = document.getElementById("dest").value.substring(0, 3);
 var date = "";

 var data = JSON.stringify({
 	"source": source,
 	"destination": destination,
 	"travelStartDate" : date
 	});

 var xhr = new XMLHttpRequest();
 xhr.withCredentials = true;

 xhr.addEventListener("readystatechange", function () {


   if (this.readyState === 4) {
     console.log("response is success");
     var response = this.responseText;

     if(response) {
         var obj = JSON.parse(response);

         if(obj.respCode == 'NO_TRIPS_FOUND'){
                 document.getElementById("find-trips-open").innerHTML = '<div class="trips-in-find-open">  No matching trips currently! Add your Trip by <a id="sign-lnk" href="#sign">signing in</a>, let other travellers find you.</div>';
         }else{
         var count = obj.count;
                 document.getElementById("find-trips-open").innerHTML = '<div class="trips-in-find-open"> Awesome! There are <span class="count">'+ count +' </span> travellers on this route! Please <a id="sign-lnk" href="#sign">sign-in</a> and connect to get more details.</div>';
         }
     }else{
         document.getElementById("find-trips-open").innerHTML = 'We got some Work! Please Try later!';
     }
 }
 });

 xhr.open("POST", HOST+"findTripOpen");
 xhr.setRequestHeader("Content-Type", "application/json");
 xhr.setRequestHeader("x-app-trace-id", "app");
 xhr.setRequestHeader("Accept", "*/*");
 xhr.setRequestHeader("Cache-Control", "no-cache");
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
if(airways=="Others"){
	airways=document.getElementById("other_airways").value;
}

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

  if (this.readyState === 4) {

    console.log("success response");
    var response = this.responseText;

    if(response) {
        var obj = JSON.parse(response);
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
xhrr.setRequestHeader("cache-control", "no-cache");
xhrr.send(data);

}

function populateResult(obj){

if(obj && obj.respCode == "SUCCESS"){
openAddResponseModal("Great! Your trip is added successfully!");
window.location.href = HOST+"home";
}else if(obj && obj.respCode == "INVALID_SAME_DAY_DUPLICATE_TRIP"){

openAddResponseModal("Trip already added!!");

}else if(obj && obj.respCode.includes("DUPLICATE_TRIP")){
openAddResponseModal("Trip already added!!");

 }else{
openAddResponseModal("Could not add Trip. Please Try Again!");

}

}



/* contact info modal */

var modal = document.getElementById("myModal");
var btn = document.getElementById("myBtn");
var span = document.getElementsByClassName("close")[0];


function modalblock(email){
modal = document.getElementById("myModal");
span = document.getElementById("modal-close");
document.getElementById("contact-info").innerHTML = email;
  modal.style.display = "block";

}

// When the user clicks on <span> (x), close the modal
function closemodal(){
  modal.style.display = "none";
}

var response_modal ="";

var span = document.getElementsByClassName("close")[0];

function openAddResponseModal(addResponse) {
  response_modal = document.getElementById("myModal");
  document.getElementById("add-response-info").innerHTML = addResponse;
  response_modal.style.display = "block";
}

function closeAddResponseModal() {
  response_modal.style.display = "none";
}

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


function deleteTrip(email, src, dest, date){
var data = JSON.stringify({
    "userId": email,
	"source": src,
	"destination": dest,
	"travelStartDate" : date
	});

var xhr = new XMLHttpRequest();
xhr.withCredentials = true;

xhr.addEventListener("readystatechange", function () {


  if (this.readyState === 4) {
    console.log("response is success");
    var response = this.responseText;

    if(response) {
        var obj = JSON.parse(response);

        if(obj.respCode == 'NO_TRIPS_FOUND'){
            alert("Trip Not Found!");
        }else if(obj.respCode == 'SUCCESS'){
            alert("Trip Deleted Successfully!");
            window.location.href = HOST+"home";

       } else{
            alert("Sorry! Cannot Delete Trip, Try Later!");
        }
    }else{
            alert("Sorry! Cannot Delete Trip, Try Later!");
    }
}
});


var res = confirm("Do you want to delete the Trip?");

if(res){
xhr.open("POST", HOST+"deleteTrip");
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("x-app-trace-id", "app");
xhr.setRequestHeader("Accept", "*/*");
xhr.setRequestHeader("Cache-Control", "no-cache");
xhr.setRequestHeader("cache-control", "no-cache");
xhr.send(data);
}

}

function showAirwaysText(){
	if(document.getElementById("airways").value=="Others"){
		document.getElementById("other_airways").style.display="block";
	}
	else {
		document.getElementById("other_airways").style.display="none";
	}
}

function resetAll(){
document.getElementById("trip-date").value = "";
document.getElementById("find-trips").innerHTML = "";
}

function resetOpenAll(){
document.getElementById("find-trips-open").innerHTML = "";

}