<?php
    require '../utils/connect.php';
?>
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
        <h2>Logowanie</h2>
        <form action="login.php" method="post" id="login">
            <label for="email">Adres E-mail</label><br>
            <input type="email" id="email" name="email" required>
            <label for="password">Hasło</label><br>
            <input type="password" id="password" name="password" required>
            <div id="links">
                <a href="">Odzyskaj hasło</a>
                <a href="register.php">Zarejestrój</a>
            </div>
            <div id="submitBox">
                <button>Zalogój się</button>
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
        if(isset($_POST['email']) && isset($_POST['password'])){
            if(trim($_POST['password']) == ""){
                    echo "\t<style>\n";
                    echo "\t    #password{\n";
                    echo "\t        border-color: red;\n";
                    echo "\t    }\n";
                    echo "\t</style>\n";
                    echo "\t<script>\n";
                    echo "\t    document.getElementById('error').innerHTML = 'Hasło nie może być puste';\n";
                    echo "\t</script>\n";
                } else {
                    $users = mysqli_query($db, "SELECT * FROM users WHERE `Email` = '".$_POST['email']."'");
                    if(mysqli_num_rows($users) == 0){
                        echo "\t<style>\n";
                        echo "\t    #email{\n";
                        echo "\t        border-color: red;\n";
                        echo "\t    }\n";
                        echo "\t</style>\n";
                        echo "\t<script>\n";
                        echo "\t    document.getElementById('error').innerHTML = 'Użytkownik z podanym adresem e-mail nie istnieje';\n";
                        echo "\t</script>\n";
                    }else{
                        $user = mysqli_fetch_assoc($users);
                        if(!password_verify($_POST['password'], $user['Password'])){
                            echo "\t<style>\n";
                            echo "\t    #password{\n";
                            echo "\t        border-color: red;\n";
                            echo "\t    }\n";
                            echo "\t</style>\n";
                            echo "\t<script>\n";
                            echo "\t    document.getElementById('error').innerHTML = 'Podano nieprawidłowe hasło';\n";
                            echo "\t</script>\n";
                        } else {
                            mysqli_query($db, "DELETE FROM `users` WHERE `Email` = '".$_COOKIE['user']."'");
                            setcookie('user', $_POST['email'], 9999999999, "/");
                            header('Location: index.php');
                        }
                    }
                }
        }
    ?>
</body>
</html>
<?php
    require '../utils/close.php';
?>