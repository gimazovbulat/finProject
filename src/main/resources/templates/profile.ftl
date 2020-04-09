<html>
<#if user??>
    ${user.email}<br>
    Points: ${user.points}<br>
    <img style="width: 100px; height: 100px" src="${user.avaPath}"><br>
<a href="/users/${user.id}/bookings">My bookings</a>
</#if>

</html>
