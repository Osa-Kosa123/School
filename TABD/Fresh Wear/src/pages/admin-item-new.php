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
                <a href="admin-items.php" class="root"  id="selected">Produkty</a>
                <div class="dropright-content">
                    <a href="admin-items.php" class="branch">Produkty</a>
                    <a href="admin-item-new.php" class="branch">Nowy produkt</a> 
                    <a href="admin-item-discounts.php" class="branch">Przeceny</a>
                </div>
            </div>
            <div class="dropright">
                <a href="admin-categories.php" class="root">Kategorie</a>
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
            <form action="" id="newItemForm" method="post">
                <div id="imageBox">
                    <div class="file-upload">
                        <input type="file" id="fileInput" accept="image/*" name="image" hidden required/>
                        <label for="fileInput" class="upload-label">Choose an Image</label>
                        <div id="preview" class="preview-area"></div>
                    </div>
                </div>
                <div id="detailsBox">
                    <div id="itemNameBox">
                        <label for="itemName">Nazwa produktu:</label>
                        <input type="text" id="itemName" name="itemName" required>
                    </div>
                    <div id="itemDescription">
                        <label for="itemDescription">Opis produktu:</label>
                        <textarea id="itemDescription" name="itemDescription" required></textarea>
                    </div>
                    <div id="itemPriceBox">
                        <label for="itemPrice">Cena produktu:</label>
                        <input type="number" id="itemPrice" name="itemPrice"  min="0.01" step="0.01" value="0.01" required>
                    </div>
                    <div id="itemQuantityBox">
                        <label for="itemQuantity">Ilość w magazynie:</label>
                        <input type="number" id="itemQuantity" name="itemQuantity"  min="0" value="0" required>
                    </div>
                    <div id="itemCategoryBox">
                        <label for="itemCategory">Kategoria produktu:</label>
                        <select id="itemCategory" name="itemCategory" required>
                            <option value="">Wybierz kategorię</option>
                            <?php
                                $categories = mysqli_query($db, "SELECT * FROM categories");
                                while($category = mysqli_fetch_assoc($categories)){
                                    echo '<option value="'.$category['Id'].'">'.$category['Category'].'</option>';
                                }
                            ?>
                        </select>
                    </div>
                    <button onclick="uploadFiles()">Dodaj produkt</button>
            </form>
        </article>
    </main>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
    <script>
        const fileInput = document.getElementById('fileInput');
        const preview = document.getElementById('preview');

        fileInput.addEventListener('change', () => {
            const file = fileInput.files[0];
            if (file && file.type.startsWith('image/')) {
                const reader = new FileReader();
                reader.onload = (e) => {
                    preview.innerHTML = '<img src="' + e.target.result + '" alt="Image preview">';
                };
                reader.readAsDataURL(file);

                const input = document.getElementById('fileInput');
                const files = input.files;

                const formData = new FormData();
                for (let file of files) {
                    formData.append('upload[]', file);
                }
                formData.append('csrf_token', generateToken());
      
                fetch('upload.php', {
                    method: 'POST',
                    body: formData,
                })
                .then(res => res.json())
                .then(data => {
                    if (data.status === 'success') {
                    } else {
                    }
                })
                .catch(() => displayMessage());
            }else{
                preview.innerHTML = '';
            }
        });

        function displayMessage() {
        }

        function generateToken() {
            return Math.random().toString(36).slice(2);
        }
</script>
<?php
    if(isset($_POST['image'], $_POST['itemName'], $_POST['itemDescription'], $_POST['itemPrice'], $_POST['itemCategory'])){
        mysqli_query($db, "INSERT INTO items (`Name`, `Description`, `Price`) VALUES ('".$_POST['itemName']."', '".$_POST['itemDescription']."', '".$_POST['itemPrice']."')");
        $item_id = mysqli_insert_id($db);
        mysqli_query($db, "INSERT INTO `item_category`(`Item_Id`, `Category_Id`) VALUES ('".$item_id."', '".(int)$_POST['itemCategory']."')");
        mysqli_query($db, "INSERT INTO item_image (`Item_ID`, `Image_Name`) VALUES ('".$item_id."', '".$_POST['image']."')");
        mysqli_query($db, "INSERT INTO `stock`(`Item_Id`, `Quantity`) VALUES ('".$item_id."', '".(int)$_POST['itemQuantity']."')");
    }
?>
</body>
</html>
<?php
    require '../utils/close.php';
?>