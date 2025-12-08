<?php
    require '../utils/connect.php';
?>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fresh Wear</title>
    <link rel="shortcut icon" href="../../public/favicon.png" type="image/png">
    <link rel="stylesheet" href="../assets/headerStyle.css">
    <link rel="stylesheet" href="../assets/registerStyle.css">
    <link rel="stylesheet" href="../assets/footerStyle.css">
    <script>
        function showError(message){
            document.write(message);;
        }
    </script>
</head>
<body>
    <header>
        <?php
            require '../components/header.php';
        ?>
    </header>
    <main>
        <h2>Rejestracja</h2>
        <form action="register.php" method="post" id="register">
            <label for="email">Adres E-mail</label><br>
            <input type="email" id="email" name="email" required>
            <label for="password">Hasło</label><br>
            <input type="password" id="password" name="password" required>
            <label for="confirmPassword">Potwierdź Hasło</label><br>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
            <div id="links">
                <a href="login.php">Zalogój</a>
            </div>
            <div id="submitBox">
                <button>Zarejestrój się</button>
            </div>
        </form>
    </main>
    <p id="error"></p>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
    <?php
        if(isset($_POST['email']) && isset($_POST['password']) && isset($_POST['confirmPassword'])){
            if(trim($_POST['password']) == ""){
                    echo "\t<style>\n";
                    echo "\t    #password{\n";
                    echo "\t        border-color: red;\n";
                    echo "\t    }\n";
                    echo "\t</style>\n";
                    echo "\t<script>\n";
                    echo "\t    document.getElementById('error').innerHTML = 'Hasło nie może być puste';\n";
                    echo "\t</script>\n";
                } else if(trim($_POST['confirmPassword']) == ""){
                    echo "\t<style>\n";
                    echo "\t    #confirmPassword{\n";
                    echo "\t        border-color: red;\n";
                    echo "\t    }\n";
                    echo "\t</style>\n";
                    echo "\t<script>\n";
                    echo "\t    document.getElementById('error').innerHTML = 'Potwierdzenie hasła nie może być puste';\n";
                    echo "\t</script>\n";
                } else if($_POST['password'] != $_POST['confirmPassword']){
                    echo "\t<style>\n";
                    echo "\t    #password, #confirmPassword{\n";
                    echo "\t        border-color: red;\n";
                    echo "\t    }\n";
                    echo "\t</style>\n";
                    echo "\t<script>\n";
                    echo "\t    document.getElementById('error').innerHTML = 'Hasła nie są identyczne';\n";
                    echo "\t</script>\n";
                } else {
                    $users = mysqli_query($db, "SELECT * FROM users WHERE `Email` = '".$_POST['email']."'");
                    if(mysqli_num_rows($users) != 0){
                        echo "\t<style>\n";
                        echo "\t    #email{\n";
                        echo "\t        border-color: red;\n";
                        echo "\t    }\n";
                        echo "\t</style>\n";
                        echo "\t<script>\n";
                        echo "\t    document.getElementById('error').innerHTML = 'Użytkownik z podanym adresem e-mail już istnieje';\n";
                        echo "\t</script>\n";
                    }else{
                        $username = explode('@', $_POST['email'])[0];
                        $password = password_hash($_POST['password'], PASSWORD_DEFAULT);
                        mysqli_query($db, "INSERT INTO users (`Username`, `Email`, `Password`, `Role`) VALUES ('$username', '".$_POST['email']."', '$password', 'u')");
                        mysqli_query($db, "DELETE FROM `users` WHERE `Email` = '".$_COOKIE['user']."'");
                        setcookie('user', $_POST['email'], 9999999999, "/");
                        header('Location: index.php');
                    }
                }
        }
    ?>
</body>
</html>
<?php
    require '../utils/close.php';
?>