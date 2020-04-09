<head>
    <script type="application/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script type="application/javascript">
        function pay(bookingId) {
            console.log(bookingId);
            $.ajax({
                url: "/payment/booking/" + bookingId,
                data: "",
                type: "post",
                dataType: "json",
                success: function (response) {
                    if (response === true){
                        $('#status').html("paid");
                        $('#payButton').attr("hidden", true);
                        alert("success");
                    }
                }
            })
        }
    </script>
</head>
<body>
Your bookings<br>
<#if bookings ??>
<#list bookings as booking>
    Address: ${booking.rooms[0].place.address}<br>
    <table>
        <tr>
            <th>Room</th>
            <th>Room price</th>
        </tr>
        <#list booking.rooms as room>
            <tr>
                <td>
                    ${room.number}
                </td>
                <td>
                    ${room.price}
                </td>
            </tr>
        </#list>
    </table>
    <#if booking.paid>
        <#assign status = "paid">
    <#else>
        <#assign status = "not paid">
    </#if>

    Cost: ${booking.cost} points<br>
    Status: <div id="status"> ${status}</div> <br>
    From: ${booking.startDate}<br>
    To: ${booking.endDate}<br> <#if !booking.paid><button id="payButton" onclick="pay(${booking.id})">pay</button><br></#if>
    -----------------------------------------------<br>
</#list>
</#if>
</body>