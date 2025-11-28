<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fresh Wear</title>
    <link rel="shortcut icon" href="../../public/favicon.png" type="image/png">
    <link rel="stylesheet" href="../assets/headerStyle.css">
    <link rel="stylesheet" href="../assets/loginStyle.css">
    <link rel="stylesheet" href="../assets/footerStyle.css">
</head>
<body>
    <header>
        <?php
            require '../components/header.php';
        ?>
    </header>
    <main>
        <h2>Logowanie</h2>
        <form action="index.php" method="post" id="login">
            <label for="email">Adres E-mail</label><br>
            <input type="email" id="email">
            <label for="password">Hasło</label><br>
            <input type="password" id="password">
            <div id="links">
                <a href="">Odzyskaj hasło</a>
                <a href="register.php">Zarejestrój</a>
            </div>
            <div id="submitBox">
                <button>Zalogój się</button>
            </div>
        </form>
    </main>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
</body>
</html>