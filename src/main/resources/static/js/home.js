
var HOST = 'http://localhost:8080/';
// HOST= 'https://dd-project-c.appspot.com/';


  function signOut() {

  alert('Logging out');

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
                document.getElementById("trips").innerHTML = 'You do not have any active trips! Add now..';
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

hcol_from.appendChild(document.createTextNode("From"));
hcol_to.appendChild(document.createTextNode("To"));
hcol_date.appendChild(document.createTextNode("Date"));
hcol_airways.appendChild(document.createTextNode("Airways"));
hcol_with.appendChild(document.createTextNode("With"));

if(action == 'FIND'){
hcol_email.appendChild(document.createTextNode("Email"));
}
else{
tripHead.appendChild(document.createTextNode("Your Planned Trips"));
}

hrow.appendChild(hcol_from);
hrow.appendChild(hcol_to);
hrow.appendChild(hcol_date);
hrow.appendChild(hcol_airways);
hrow.appendChild(hcol_with);

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

  if(action == 'FIND'){
  var col_email = document.createElement('td');
    }

  col_from.class = "coll";
  col_to.class = "coll";
  col_date.class = "coll";
  col_airways.class = "coll";
  col_travellingWith.class = "coll";

  if(action == 'FIND'){
  col_email.class = "coll";
    }

  col_from.appendChild(document.createTextNode(obj.tripList[i].source));
  col_to.appendChild(document.createTextNode(obj.tripList[i].destination));
  col_date.appendChild(document.createTextNode(obj.tripList[i].travelStartDate));
  col_airways.appendChild(document.createTextNode(obj.tripList[i].airways));
  col_travellingWith.appendChild(document.createTextNode(obj.tripList[i].travellingWith));

  if(action == 'FIND'){
  col_email.appendChild(document.createTextNode(obj.tripList[i].travellerId));
   }

    row.appendChild(col_from);
    row.appendChild(col_to);
    row.appendChild(col_date);
    row.appendChild(col_airways);
    row.appendChild(col_travellingWith);

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


function findTrip(){

var source = document.getElementById("src").value;
var destination = document.getElementById("dest").value;

var data = JSON.stringify({
	"source": source,
	"destination": destination
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

var source = document.getElementById("trip-from").value;
var destination = document.getElementById("trip-to").value;
var date = document.getElementById("trip-date").value;
var airways = document.getElementById("airways").value;
var tripWith = document.getElementById("trip-with").value;
var isTicketBooked = document.getElementById("trip-box-0").checked;
var isFinalDestination = document.getElementById("trip-box-1").checked;
var canTakePackageInd = document.getElementById("trip-box-2").checked;


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
        "isFinalDestination": isFinalDestination
    }
	});

var xhrr = new XMLHttpRequest();
xhrr.withCredentials = true;

xhrr.addEventListener("readystatechange", function () {

  if (this.readyState > 0) {
   document.getElementById("add-trip-result").innerHTML = 'Please wait! Pulling trips.....';
    }

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
xhrr.setRequestHeader("Accept-Encoding", "gzip, deflate");
xhrr.setRequestHeader("Content-Length", "31");
xhrr.setRequestHeader("Connection", "keep-alive");
xhrr.setRequestHeader("cache-control", "no-cache");

xhrr.send(data);

}

function populateResult(obj){

var listDiv = document.getElementById("add-trip-result");

if(obj && obj.respCode == "SUCCESS"){
listDiv.innerHTML = "Great! Your trip is added successfully!";
}else if(obj && obj.respCode == "INVALID_SAME_DAY_DUPLICATE_TRIP"){
listDiv.innerHTML = "Trip already added!!";

}else if(obj && obj.respCode == "DUPLICATE_TRIP"){
 listDiv.innerHTML = "Trip already added!!";

 }else{
listDiv.innerHTML = "Could not add Trip. Please Try Again!";

}

}


//function authcon(){
//
//alert('see');
//document.getElementById("authcon-form").submit();
//}
//
//$('#sandbox-container .input-group.date').datepicker({
//});

//$('.datepicker').datepicker({
//    format: 'mm/dd/yyyy',
//    startDate: '-3d'
//});