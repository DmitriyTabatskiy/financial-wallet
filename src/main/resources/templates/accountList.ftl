<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Accounts</title>
</head>
<body>
<h1>Accounts list</h1>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>balance</th>
    </tr>
    <#list accounts as account>
        <tr>
            <td>${account.id}</td>
            <td>${account.name}</td>
            <td>${account.balance}</td>
            <td><a href="/delete/${account.id}">Delete</a></td>
        </tr>
    </#list>
</table>
<a href="/insert-account">Insert Account</a>
</body>
</html>