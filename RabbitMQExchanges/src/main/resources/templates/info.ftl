<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://fonts.googleapis.com/css2?family=Yeseva+One&display=swap" rel="stylesheet">
    <title>Info</title>
</head>
<body>
<header style="letter-spacing: 3px">
    <h1>Информация</h1>
</header>
<div>
    <form method="post">
        <div>
            <input type="text" placeholder="firstName" name="firstName">
        </div>
        <div>
            <input type="text" placeholder="lastName" name="lastName">
        </div>
        <div>
            <input type="text" placeholder="middleName" name="middleName">
        </div>
        <div>
            <input type="text" placeholder="birthday" name="birthday">
        </div>
        <!--        <input type="submit" value="Машина">-->
    </form>
    <button onclick="send()">
        Оправить
    </button>
</div>
</body>
</html>