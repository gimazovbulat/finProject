<html>
<#if user??>
    ${user.email}
    <img src="${user.avaPath}">

<#list user.bookings as booking>
    <#list booking.seats as seat>
        place address: ${seat.place.address}<br>
        seat: ${seat.number}
    </#list>

</#list>
</#if>

</html>
