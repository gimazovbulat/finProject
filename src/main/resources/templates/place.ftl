<head>
    <meta charset="UTF-8">
<body>
<#if place??>
    ${place.address}
    <img style="width: 400px; height: 450px" src="${place.photo.url}">

    <#list place.rooms as room>
        Room number : ${room.number}
        Room cost : ${room.price}
    </#list>

    <form method="post" id="form" action="/booking/place/${place.id}">
        <p><b></b></p>
        <#list place.freeRooms as room>
            <p><input type="checkbox" class="svois" name="roomNumbers" value="${room.number}">${room.number}</p>
        </#list>
        <p>
            <label for="startDate">Start date and time: </label>
            <input type="date" id="startDate" name="startDate"/>
        </p>
        <p>
            <label for="endDate">End date and time: </label>
            <input type="date" id="endDate" name="endDate"/>
        </p>
        <p><input type="submit" value="book"></p>

    </form>


</#if>

<#if booking??>
    you booked rooms:
    <#list booking.rooms as room>
        ${room.number}
    </#list>
</#if>
</body>