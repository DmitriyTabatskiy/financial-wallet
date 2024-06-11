
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
      <form action="/from-account-to-account-transaction" method="post">
        <div class="mb-3">
          <label for="exampleInputFromAccountId">From Account Id</label>
          <@spring.formInput "form.fromAccountId" "class=\"form-control\" id=\"exampleInputFromAccountId\" placeholder=\"Enter Account Id\"" "fromAccountId"/><br>
          <@spring.showErrors "<br>"/>
        </div>
        <div class="mb-3">
          <label for="exampleInputToAccountId">To Account Id</label>
          <@spring.formInput "form.toAccountId" "class=\"form-control\" id=\"exampleInputToAccountId\" placeholder=\"Enter Account Id\"" "toAccountId"/><br>
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
</html><#--<!DOCTYPE html>-->
<#--<html lang="en">-->
<#--<head>-->
<#--  <meta charset="UTF-8">-->
<#--  <title>From Account to Account</title>-->
<#--</head>-->
<#--<body>-->
<#--<form name="form" action="/from-account-to-account-transaction" method="post">-->
<#--  <form class="row g-3 needs-validation" novalidate>-->
<#--    <div class="col-md-4">-->
<#--      <label for="validationCustom01" class="form-label"></label>-->
<#--      <p>Enter from Account</p>-->
<#--      <input type="number" name="fromAccountId" class="form-control" id="validationCustom01" value="0" required><br>-->
<#--      <br>-->
<#--      <p>Enter to Account</p>-->
<#--      <input type="number" name="toAccountId" class="form-control" id="validationCustom01" value="0" required><br>-->
<#--      <br>-->
<#--      <p>Enter amount</p>-->
<#--      <input type="number" name="amount" class="form-control" id="validationCustom01" value="0" required><br>-->
<#--      <br>-->
<#--      <p>Enter numbers categories separated by commas</p>-->
<#--      <input type="text" name="integerList" class="form-control" id="validationCustom01" value="0" required><br>-->
<#--      <br>-->
<#--      <div class="valid-feedback">-->
<#--        <input type="submit" value="OK">-->
<#--      </div>-->
<#--    </div>-->
<#--  </form>-->
<#--</form>-->
<#--</body>-->
<#--</html>-->