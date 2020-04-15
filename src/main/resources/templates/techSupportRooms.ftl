<head>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>
        function getChatRooms() {
            $.ajax({
                url: "/api/techSupport/rooms",
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    $('#rooms').html("");
                    for (let i in response) {
                        $('#rooms').append('<li><a href=/techSupport/rooms/' + response[i]['id'] + '>'
                            + response[i]['name'] + '</a></li>');
                    }
                }
            })
        }</script>
    <script>
        function createRoom() {
            $.ajax({
                url: "/techSupport/room",
                method: "POST",
                dataType: "json",
                contentType: "application/json",
                complete: function (response) {
                    getChatRooms();
                }
            })
        }
    </script>
</head>
<body onload="getChatRooms()">
<button onclick="createRoom()">create new room to ask questions</button>
<ul id="rooms">

</ul>
</body>
