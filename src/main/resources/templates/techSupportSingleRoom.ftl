<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <head>
        <script
                src="https://code.jquery.com/jquery-3.4.1.min.js"
                integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
                crossorigin="anonymous"></script>
        <script>
            function getChat(roomId) {
                $.ajax({
                    url: "/suppMessages",
                    method: "GET",
                    dataType: "json",
                    data: {
                        "roomId": roomId
                    },
                    contentType: "application/json",
                    success: function (response) {
                        for (let i in response) {
                            $('#messages').append('<li>' + response[i]['text'] + '</li>');
                        }
                        receiveMessages(roomId)
                    }
                })
            }
        </script>
        <script>
            function sendMessage(roomId, text) {
                let body = {
                    roomId: roomId,
                    text: text
                };

                $.ajax({
                    url: "/message",
                    method: "POST",
                    data: JSON.stringify(body),
                    contentType: "application/json",
                    dataType: "json",
                    complete: function () {
                        receiveMessages(roomId)
                    }
                });
            }
        </script>

        <script>
            function receiveMessages(roomId) {
                $.ajax({
                    url: "/message",
                    method: "GET",
                    dataType: "json",
                    data: {
                        "roomId": roomId
                    },
                    contentType: "application/json",
                    success: function (response) {
                        console.log(response)
                        for (let i in response){
                            $('#messages').append('<li>' + response[i]['text'] + '</li>');
                        }
                        receiveMessages(roomId);
                    }
                })
            }
        </script>
    </head>
</head>
<body onload="getChat(${room.id})">
<div>
    <input id="message" placeholder="message">
    <button onclick="sendMessage('${room.id}',
            $('#message').val())">send
    </button>
</div>
<ul id="messages">

</ul>

</body>
</html>