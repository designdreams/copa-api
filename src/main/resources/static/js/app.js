var id_token = '';
var email = '';
var id = '';

var clicked=false;//Global Variable
function ClickLogin()
{
    clicked=true;
}

function onSignIn(googleUser) {

if (clicked) {
  var profile = googleUser.getBasicProfile();
  console.log('ID: ' + profile.getId()); // Do not send to your backend! Use an ID token instead.
  id_token = googleUser.getAuthResponse().id_token;
  document.getElementById("x-app-token-param").value = id_token;

    email = profile.getEmail();
    id = profile.getId();
   postForm(id_token);
   //postAuth();
}
}

function postForm(token){
document.getElementById("login-auth").submit();
}

//function postAuth(){
//    var data = JSON.stringify({
//      "user": {
//        "emailId": email,
//         "id": id
//      }
//    });
//
//    var xhr = new XMLHttpRequest();
//    xhr.open('POST',APP_URL);
//    xhr.setRequestHeader("Content-Type", "application/json");
//    xhr.setRequestHeader("cache-control", "no-cache");
//    xhr.setRequestHeader("x-app-auth-token", id_token);
//    xhr.send(data);
//}

  function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
      console.log('User signed out.');
    });
  }



