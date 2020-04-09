<html>
<#if user??>
    ${user.email}<br>
    Points: ${user.points}<br>
    <img src="${user.avaPath}"><br>
<a href="/users/${user.id}/bookings">My bookings</a>
</#if>

</html>
