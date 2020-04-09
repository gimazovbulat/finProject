<head>
    <meta charset="UTF-8">
<body>
<#if place??>
    ${place.address}<br>
    <img style="width: 400px; height: 450px" src="${place.photo.url}">

    <table>
        <tr>
            <th>Room</th>
            <th>Room price</th>
        </tr>
        <#list place.rooms as room>
            <tr>
                <td>
                    ${room.number}
                </td>
                <td>
                    ${room.price}
                </td>
            </tr>
        </#list>
    </table><br>
Free rooms
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