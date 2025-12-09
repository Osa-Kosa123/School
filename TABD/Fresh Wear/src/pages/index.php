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
    <link rel="stylesheet" href="../assets/indexStyle.css">
    <link rel="stylesheet" href="../assets/footerStyle.css">
</head>
<body>
    <header>
        <?php
            require '../components/header.php';
        ?>
    </header>
    <main>
        <!-- <section id="searchOptions">
            <div id="left">
                <form action="index.php" method="post" id="viewForm">
                    <label>
                        <input type="radio" name="view" value="gridView">
                        <img src="../assets/gridView.svg" alt="button to wiew products in grid view">
                    </label>
                    <label>
                        <input type="radio" name="view" value="listView">
                        <img src="../assets/listView.svg" alt="button to wiew products in list view">
                    </label>
                </form>
                <form action="index.php" method="post" id="sortForm">
                    <select name="sort" id="sortOption">
    					<option value="date" selected='selected'>Sortuj od najnowszych</option>
    					<option value="priceLow" >Sortuj po cenie od najniższej</option>
    					<option value="priceHigh" >Sortuj po cenie od najwyższej</option>
                    </select>
                    <select name="category" id="categoryOption">
    					
                    </select>
                </form>
            </div>
            <div id="right">
                <form action="index.php" method="post" id="pageForm">
                </form>
            </div>
        </section> -->
        <section id="products">
            <?php
                // require '../components/items.php';
                if(isset($_POST['search'])){
                    $items = mysqli_query($db, "SELECT i.Id, i.Name, CAST((i.Price * (100-i.Discount) / 100) AS DOUBLE(16, 2)) AS ThePrice, `i-i`.Image_Name FROM items i INNER JOIN item_image `i-i` on i.Id = `i-i`.Item_Id WHERE i.Name LIKE '%".$_POST['search']."%' OR i.Description LIKE '%".$_POST['search']."%'");
                } else {
                    $items = mysqli_query($db, "SELECT i.Id, i.Name, CAST((i.Price * (100-i.Discount) / 100) AS DOUBLE(16, 2)) AS ThePrice, `i-i`.Image_Name FROM items i INNER JOIN item_image `i-i` on i.Id = `i-i`.Item_Id");
                }
                if(mysqli_num_rows($items) == 0){
                    echo "<h2 id='noResults'>Brak wyników wyszukiwania</h2>";
                }
                while($item = mysqli_fetch_assoc($items)) {
                    // require '../components/itemBox.php';
                    echo "<div class='itemBox'>";
                    echo "    <a href='item.php?id=".$item['Id']."'>";
                    echo "        <img src='../assets/items/".$item['Image_Name']."' alt='product image' class='itemImage'>";
                    echo "        <div class='itemInfo'>";
                    echo "            <h3 class='itemName'>".$item['Name']."</h3>";
                    echo "            <p class='itemPrice'>".$item['ThePrice']." PLN</p>";
                    echo "        </div>";
                    echo "    </a>";
                    echo "</div>";
                }
            ?>
        </section>
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