<#import "spring.ftl" as spring>
<html>
<meta charset="utf-8">
<body>
<@spring.bind "signUpForm"/>
<form method="post" action="/signUp">
    <@spring.message "signUp.page.email"/>
    <@spring.formInput "signUpForm.email"/>
    <@spring.showErrors "<br>","error"/>

    <@spring.message "signUp.page.password"/>
    <@spring.formInput "signUpForm.password"/>
    <@spring.showErrors "<br>","error"/>

    <@spring.message "signUp.page.confirm_password"/>
    <@spring.formInput "signUpForm.confirmPassword"/>
    <@spring.showErrors "<br>","error"/>

    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <input type="submit">
</body>
</html>