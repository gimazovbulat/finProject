
<#if places??>
<#list places as place>
    <a href="/places/${place.id}"> ${place.address}
        <img style="width: 400px; height: 450px" src="${place.photo.url}"> </a>
</#list>
</#if>

<#assign x = count>
<form action="/places" method="get">
    <#list 1..x as num>
    <p><input type="checkbox" class="svois" name="page" value="${num}">${num}</p>
    </#list>
    <input type=submit>
</form>