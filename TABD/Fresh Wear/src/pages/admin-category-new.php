<?php
    require '../utils/connect.php';
    
    $users = mysqli_query($db, "SELECT * FROM users WHERE `Email` = '".$_COOKIE['user']."'");
    $user = mysqli_fetch_assoc($users);
    if($user['Role'] != "a"){
        header('Location: index.php');
        exit();
    }
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
            <div class="dropright">
                <a href="admin-items.php" class="root">Produkty</a>
                <div class="dropright-content">
                    <a href="admin-items.php" class="branch">Produkty</a>
                    <a href="admin-item-new.php" class="branch">Nowy produkt</a> 
                    <a href="admin-item-discounts.php" class="branch">Przeceny</a>
                </div>
            </div>
            <div class="dropright">
                <a href="admin-categories.php" class="root"  id="selected">Kategorie</a>
                <div class="dropright-content">
                    <a href="admin-categories.php" class="branch">Kategorie</a>
                    <a href="admin-category-new.php" class="branch">Nowa kategoria</a>
                </div>
            </div>
            <div class="dropright">
                <a href="admin-users.php" class="root">Urzytkownicy</a>
            </div>
        </aside>
        <article>
            <form action="admin-category-new.php" id="newCategoryForm" method="post">
                <label for="categoryName">Nazwa kategorii:</label>
                <input type="text" id="categoryName" name="categoryName" required>
                <button type="submit" id="createCategoryButton">Utwórz kategorię</button>
            </form>
            <?php
                if(isset($_POST['categoryName'])){
                    mysqli_query($db, "INSERT INTO categories (`Category`) VALUES ('".$_POST['categoryName']."')");
                }
            ?>
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