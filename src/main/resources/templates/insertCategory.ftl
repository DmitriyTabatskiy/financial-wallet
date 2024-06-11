<#import "/spring.ftl" as spring />

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Insert Category</title>
</head>
<body>
<form name="form" action="/insert-category" method="post">
    <form class="row g-3 needs-validation" novalidate>
        <div class="col-md-4">
            <label for="validationCustom01" class="form-label"></label>
            <p>Enter name</p>
            <input type="text" name="name" class="form-control" id="validationCustom01" value="name" required><br>
            <br>
            <div class="valid-feedback">
                <input type="submit" value="OK">
            </div>
        </div>
    </form>
</form>
</body>
</html>