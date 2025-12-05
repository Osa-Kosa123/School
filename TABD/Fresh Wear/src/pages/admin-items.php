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
    <link rel="stylesheet" href="../assets/adminStyle.css">
    <link rel="stylesheet" href="../assets/footerStyle.css">
</head>
<body>
    <header>
        <?php
            require '../components/header.php';
        ?>
    </header>
    <main>
        <aside>
            <a href="admin-items.php" class="root"><button>Produkty</button></a>
            <input type="button" value="Produkty" id="items">
            <div>
                <a href="admin-items.php"><button>Produkty</button></a>
                <a href="admin-newItem.php"><button>Nowy produkt</button></a>
                <a href="admin-discounts.php"><button>Przeceny</button></a>
            </div>
            <a href="admin-users.php" class="root"><button>Urzytkownicy</button></a>
        </aside>
        <article>

        </article>
    </main>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
</body>
</html>
<?php
    require '../utils/close.php';
?>