<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Insert Account</title>
</head>
<body>
<form name="form" action="/insert-account" method="post">
    <form class="row g-3 needs-validation" novalidate>
        <div class="col-md-4">
            <label for="validationCustom01" class="form-label"></label>
            <p>Enter name</p>
            <input type="text" name="name" class="form-control" id="validationCustom01" value="account" required><br>
            <br>
            <p>Enter balance</p>
            <input type="number" name="balance" class="form-control" id="validationCustom01" value="0" required><br>
            <br>
            <div class="valid-feedback">
                <input type="submit" value="OK">
            </div>
        </div>
    </form>
</form>
</body>
</html>