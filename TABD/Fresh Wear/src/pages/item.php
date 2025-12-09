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
    <link rel="stylesheet" href="../assets/itemStyle.css">
    <link rel="stylesheet" href="../assets/footerStyle.css">
</head>
<body>
    <header>
        <?php
            require '../components/header.php';
        ?>
    </header>
    <main>
        <?php
            if(isset($_GET['id'])){
                $imagesTable = [];
                $categoryNames = [];
                $items = mysqli_query($db, "SELECT * FROM items WHERE `Id` = '".$_GET['id']."'");
                $item = mysqli_fetch_assoc($items);
                $images = mysqli_query($db, "SELECT * FROM item_image WHERE `Item_Id` = '".$_GET['id']."'");
                $categories = mysqli_query($db, "SELECT * FROM categories WHERE Id = (SELECT Category_Id FROM item_category WHERE `Item_Id` = '".$_GET['id']."')");
                $stock = mysqli_query($db, "SELECT * FROM stock WHERE `Item_Id` = '".$_GET['id']."'");
                $avalible = mysqli_fetch_assoc($stock);
                echo "<div id='imageBox'>";
                echo "    <div id='imagelist'>";
                while($image = mysqli_fetch_assoc($images)){
                    array_push($imagesTable, $image['Image_Name']);
                    echo "<img src='../assets/items/".$image['Image_Name']."' alt='".$image['Image_Name']." image' class='thumbnail' onclick='showImage(this.src)'>";
                }
                echo "    </div>";
                echo "    <div id='Image'>";
                echo "        <img src='../assets/items/".$imagesTable[0]."' alt='".$imagesTable[0]." image' id='itemImage'>";
                echo "    </div>";
                echo "</div>";
                echo "<div id='detailsBox'>";
                echo "    <h2 id='itemName'>".$item['Name']."</h2>";
                echo "    <hr>";
                echo "    <p id='price'>".$item['Price']." PLN</p>";
                echo "    <p id='description'>".$item['Description']."</p>";
                echo "    <hr>";
                echo "    <form action='item.php?id=".$_GET['id']."' method='post' id='addToCartForm'>";
                echo "        <input type='number' name='Quantity' min='1' max='".$avalible['Quantity']."' value='1' required>";
                echo "        <button type='submit' id='addToCartButton'>Dodaj do koszyka</button>";
                echo "    </form>";
                echo "    <hr>";
                while($category = mysqli_fetch_assoc($categories)){
                    array_push($categoryNames, $category['Category']);
                }
                echo "    <p id='categories'>Kategorie: ".implode(", ", $categoryNames)."</p>";
                echo "</div>";
            }
        ?>
    </main>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
    <script>
        function showImage(src){
            document.getElementById("itemImage").src = src;
        }
    </script>
    <?php
        if(isset($_POST['Quantity'])){
            $user_email = $_COOKIE['user'];
            $users = mysqli_query($db, "SELECT * FROM users WHERE `Email` = '".$user_email."'");
            $user = mysqli_fetch_assoc($users);
            mysqli_query($db, "INSERT INTO `cart`(`User_Id`, `Item_Id`, `Item_Quantity`, `Price`) VALUES ('".$user['Id']."', '".$_GET['id']."', ".(int)$_POST['Quantity'].", (".$item['Price']." * (100-".$item['Discount'].") / 100))");
            mysqli_query($db, "UPDATE `stock` SET `Quantity` = `Quantity` - ".(int)$_POST['Quantity']." WHERE `Item_Id` = '".$_GET['id']."'");
            echo "<script>alert('Dodano produkt do koszyka!');</script>";
        }
    ?>
</body>
</html>
<?php
    require '../utils/close.php';
?>