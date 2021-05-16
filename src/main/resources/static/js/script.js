var server = document.getElementById("server");
var channel = document.getElementById("channel");
var message = document.getElementById("message");

function sendMessage() {

    let json = {
        server: server.value,
        channel: channel.value,
        message: message.value
    };

    var xhr = new XMLHttpRequest();
    xhr.withCredentials = true;
    xhr.open("POST", "http://localhost:8081/message", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.send(JSON.stringify(json));

    message.value = "";
    return false;
}