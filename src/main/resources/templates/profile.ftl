<html>
<#if user??>
    ${user.email}
    <img src="${user.avaPath}">

<#list user.bookings as booking>
    <#list booking.rooms as room>
        place address: ${room.place.address}<br>
        room: ${room.number}
    </#list>

</#list>
</#if>

</html>
