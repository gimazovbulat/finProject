<#if places??>
    <table>
        <#list places as place>
            <tr>
                <td>
                    <a href="/places/${place.id}">
                        ${place.address}
                    </a>
                </td>
                <td><img style="width: 400px; height: 450px" src="${place.photo.url}"></td>
            </tr>
        </#list>
    </table>
</#if>

<#assign x = count>
<form action="/places" method="get">
    <#list 1..x as num>
        <p><input type="checkbox" class="svois" name="page" value="${num}">${num}</p>
    </#list>
    <input type=submit>
</form>