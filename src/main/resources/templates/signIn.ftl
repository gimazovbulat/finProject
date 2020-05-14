<form action="/signIn" method="post">
    <input name="email" placeholder="email" type="text">
    <input name="password" type="password" placeholder="password">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    Remember me<input type="checkbox" name="remember-me">
    <input type="submit">
    <#if err??>err</#if>
</form>
