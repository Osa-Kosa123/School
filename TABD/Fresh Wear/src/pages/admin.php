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
            <form action="admin.php" method="post">
                <input type="button" value="items" id="items">
                <input type="button" value="discounts" id="discounts">
                <input type="button" value="users" id="users">
            </form>
        </aside>
        
    </main>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
</body>
</html>