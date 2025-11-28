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
        <section id="searchOptions">
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
    					<option value="popularity" >Sortuj wg popularności</option>
    					<option value="priceLow" >Sortuj po cenie od najniższej</option>
    					<option value="priceHigh" >Sortuj po cenie od najwyższej</option>
                    </select>
                </form>
            </div>
            <div id="right">
                <form action="index.php" method="post" id="pageForm">
                </form>
            </div>
        </section>
        <section id="products">

        </section>
    </main>
    <footer>
        <?php
            require '../components/footer.php';
        ?>
    </footer>
</body>
</html>