<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Categories</title>
</head>
<body>
<h1>Categories list</h1>
<table>
    <tr>
        <th>id</th>
        <th>name</th>
    </tr>
    <#list categories as category>
        <tr>
            <td>${category.id}</td>
            <td>${category.name}</td>
            <td><a href="/delete-category/${category.id}">Delete</a></td>
        </tr>
    </#list>
</table>
<br>
<a href="/insert-category">Insert Category</a><br>
<br>
<a href="/update-category">Update Category</a>
</body>
</html>