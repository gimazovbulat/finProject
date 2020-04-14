<head>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script>
        function getChatRooms() {
            $.ajax({
                url: "/rooms",
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                success: function (response) {
                    for (let i in response) {
                        $('#rooms').first().after('<li><a href=/rooms/techSupport/' + response[i]['id'] + '>'
                            + response[i]['name'] + '</a></li>');
                    }
                }
            })
        }</script>
</head>
<body onload="getChatRooms()">
<ul id="rooms">

</ul>
</body>
