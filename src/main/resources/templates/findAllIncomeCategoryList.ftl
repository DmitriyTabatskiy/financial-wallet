<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Income Category</title>
</head>
<body>
<h1>All Income Category</h1>
<table>
    <tr>
        <th>Name category</th>
        <th>Summa</th>
    </tr>
    <#list categoryIncome as categoryReport>
        <tr>
            <td>${categoryReport.name}</td>
            <td>${categoryReport.amount}</td>
        </tr>
    </#list>
</table>
</body>
</html>