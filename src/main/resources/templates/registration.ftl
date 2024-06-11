<#import "/spring.ftl" as spring />

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Registration</title>
    <link href="/css/bootstrap-grid.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-sm"></div>
        <div class="col-sm">
            <form action="/regist" method="post">
                <div class="mb-3">
                    <p>Registration</p>
                    <label for="exampleInputEmail">Email address</label>
                    <@spring.formInput "form.email" "class=\"form-control\" id=\"exampleInputEmail\" placeholder=\"Enter email\"" "email"/><br>
                    <@spring.showErrors "<br>"/>
                </div>
                <div class="mb-3">
                    <label for="exampleInputPassword1">Password</label>
                    <@spring.formInput "form.password" "class=\"form-control\" id=\"exampleInputPassword1\" placeholder=\"Password\"" "password"/><br>
                    <@spring.showErrors "<br>"/>
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form><br>
<#--            <a href="/regist">Click for registration</a>-->
        </div>
        <div class="col-sm"></div>
    </div>
</div>
<script src="/js/bootstrap.min.js"></script>
</body>
</html>