<head>
    <meta charset="UTF-8">
    <script src='https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js'></script>

    <script type="application/javascript">
        function book() {
            var seatNumbers = [];
            $(".svois:checked").each(function (index) {
                seatNumbers.push($(this).val());
            });
            let formData = new FormData();
            formData.append("seatNumbers", seatNumbers);
            console.log(formData.get("seatNumbers"));
            $.ajax({
                type: "POST",
                url: "/booking/place/${place.id}",
                data: formData,
                dataType: "json",
            })
                .done(function () {

                })
                .fail(function () {
                    alert('Error')
                });
        }
    </script>
</head>
<body>
<#if place??>
    ${place.address}
    <img style="width: 400px; height: 450px" src="${place.photo.url}">

    <#list place.seats as seat>
        ${seat.number}
    </#list>

    <form method="post" id="form" action="/booking/place/${place.id}" onsubmit="book(); return false;">
        <p><b></b></p>
        <#list place.freeSeats as seat>
            <p><input type="checkbox" class="svois" name="seatNumbers" value="${seat.number}">${seat.number}</p>
        </#list>
        <p><input type="submit" value="book"></p>
    </form>


</#if>

<#if booking??>
    you booked seats:
    <#list booking.seats as seat>
        ${seat.number}
    </#list>
</#if>
</body>