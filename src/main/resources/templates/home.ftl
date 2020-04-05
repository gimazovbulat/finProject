Welcome

<#list places as place>
    <a href="/places/${place.id}"> ${place.address}
        <img style="width: 400px; height: 450px" src="${place.photo.url}"> </a>
</#list>
