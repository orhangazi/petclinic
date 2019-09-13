<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>PetClinic Home Page</title>
</head>
<body>
<h1>PetClinic Home Page Header</h1>
<form action="logout" method="post">
    <input type="submit" value="Logout">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
</form>
</body>
</html>