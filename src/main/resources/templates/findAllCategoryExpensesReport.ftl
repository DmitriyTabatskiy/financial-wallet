<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Income Category</title>
</head>
<body>
<form name="form" action="/find-all-expenses" method="post">
    <form class="row g-3 needs-validation" novalidate>
        <div class="col-md-4">
            <label for="validationCustom01" class="form-label"></label>
            <p>Start Date</p>
            <input type="date" name="start" class="form-control" id="validationCustom01" value="2023-01-01" required><br>
            <br>
            <p>End Date</p>
            <input type="date" name="end" class="form-control" id="validationCustom01" value="2023-05-24" required><br>
            <br>
            <div class="valid-feedback">
                <input type="submit" value="OK">
            </div>
        </div>
    </form>
</form>
</body>
</html>