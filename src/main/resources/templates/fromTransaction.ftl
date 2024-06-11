<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>From Account</title>
    <link href="/css/bootstrap-grid.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <div class="col-sm">
        <form action="/from-transaction" method="post">
            <div class="mb-3">
                <label for="exampleInputFromAccountId">Account Id</label>
                <@spring.formInput "form.fromAccountId" "class=\"form-control\" id=\"exampleInputFromAccountId\" placeholder=\"Enter Account Id\"" "fromAccountId"/><br>
                <@spring.showErrors "<br>"/>
            </div>
            <div class="mb-3">
                <label for="exampleInputAmount">Amount</label>
                <@spring.formInput "form.amount" "class=\"form-control\" id=\"exampleInputAmount\" placeholder=\"Amount\"" "amount"/><br>
                <@spring.showErrors "<br>"/>
            </div>
            <div class="mb-3">
                <label for="exampleInputIntegerList">Integers</label>
                <@spring.formInput "form.integerList" "class=\"form-control\" id=\"exampleInputIntegerList\" placeholder=\"Integers\"" "integerList"/><br>
                <@spring.showErrors "<br>"/>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
        </div>
        <div class="col-sm"></div>
        <br>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>