<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fresh Wear</title>
    <link rel="shortcut icon" href="../../public/favicon.png" type="image/png">
    <link rel="stylesheet" href="../assets/headerStyle.css">
    <link rel="stylesheet" href="../assets/registerStyle.css">
    <link rel="stylesheet" href="../assets/footerStyle.css">
    <?php
        if(isset($_POST['email']) && isset($_POST['password']) && isset($_POST['confirmPassword'])){
            if($_POST['email'].trim() == ""){
                echo "<style>";
                echo "    #email{";
                echo "        border-color: red;";
                echo "    }";
                echo "</style>";
            }
            if($_POST['password'].trim() == ""){
                echo "<style>";
                echo "    #password{";
                echo "        border-color: red;";
                echo "    }";
                echo "</style>";
            }
            if($_POST['confirmPassword'].trim() == ""){
                echo "<style>";
                echo "    #confirmPassword{";
                echo "        border-color: red;";
                echo "    }";
                echo "</style>";
            }
            if($_POST['password'] == $_POST['confirmPassword']){
                echo "<style>";
                echo "    #confirmPassword{";
                echo "        border-color: green;";
                echo "    }";
                echo "</style>";
            }
        }
    ?>
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
            <input type="email" id="email" required>
            <label for="password">Hasło</label><br>
            <input type="password" id="password" required>
            <label for="confirmPassword">Potwierdź Hasło</label><br>
            <input type="password" id="confirmPassword" required>
            <div id="links">
                <a href="login.php">Zalogój</a>
            </div>
            <div id="submitBox">
                <button>Zarejestrój się</button>
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