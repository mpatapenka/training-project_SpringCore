<h2>Data retrieve</h2>

<h3>Auditorium retrieve</h3>
<form name="auditoriumByName" action="/retrieve/auditorium" method="post">
    Auditorium name: <input type="text" name="name" /><br/>
    <input type="submit" value="Retrieve" />
</form>

<h3><a href="/retrieve/users">Users retrieve</a></h3>
<h3><a href="/retrieve/events">Events retrieve</a></h3>

<h3>Counters retrieve</h3>
<form name="statistic" action="/retrieve/statistic" method="post">
    Type: <input type="text" name="type" /><br/>
    Name: <input type="text" name="name" /><br/>
    Domain:
    <select name="domainsCombobox">
        <option value="">Choose one</option>
        <#list counterDomains as domain>
            <option value="${domain}">${domain}</option>
        </#list>
    </select>
    <input type="submit" value="Retrieve" />
</form>


<#if auditorium??>
    <h2>Auditorium:</h2>
    Name: ${auditorium.name} <br>
    Number of seats: ${auditorium.numberOfSeats} <br>
    Vip seats:
    <#list auditorium.vipSeats as seat>
        ${seat}
    </#list>
</#if>

<#if users??>
    <h2>Users:</h2>
    <#list users as user>
        ${user.firstName} ${user.lastName} ${user.email} <br>
    </#list>
</#if>

<#if events??>
    <h2>Events:</h2>
    <#list events as event>
        ${event.name} ${event.basePrice} ${event.rating} <br>
    </#list>
</#if>

<#if counters??>
    <h2>Counters:</h2>
    <#list counters as counter>
        Type: ${counter.type} <br>
        Name: ${counter.name} <br>
        Domain: ${counter.domain} <br>
        Count: ${counter.count.get} <br><br>
    </#list>
</#if>