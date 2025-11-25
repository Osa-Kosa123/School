<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fresh Wear</title>
    <link rel="shortcut icon" href="favicon.png" type="image/png">
    <link rel="stylesheet" href="../src/assets/style.css">
</head>
<body>
    <!-- <div>
        <div>
            <img src="../src/assets/logo.png" alt="logo" id="logo">
        </div>
        <div>
            <form action="search.php" method="post">
                <div>
                    <input type="text" name="search" id="search" placeholder="Szukaj produktów...">
                    <button>
                        <img src="../src/assets/search.png" alt="serch" id="serch">
                    </button>
                </div>
            </form>
        </div>
        <div>
            <button></button>
        </div>
    </div> -->
    <header>
        <div id="logoBox">
            <img src="../src/assets/logo.png" alt="logo" id="logo">
        </div>
        <div id="searchBox">
            <form action="search.php" method="post" id="searchForm">
                <input type="text" name="search" id="searchField" placeholder="Szukaj produktów...">
                <button type="submit" id="searchButton">
                    <img src="../src/assets/search.png" alt="search icon" id="searchIcon">
                </button>
            </form>
        </div>
        <div id="buttonBox">
            <button id="cart"></button>
        </div>
    </header>
    <main>
        <section id="searchOptions">
            <form action="index.php" method="post" id="viewForm">
                <label>
                    <input type="radio" name="view" value="gridView">
                    <img src="../src/assets/gridView.svg" alt="button to wiew products in grid view">
                </label>
                <label>
                    <input type="radio" name="view" value="listView">
                    <img src="../src/assets/listView.svg" alt="button to wiew products in list view">
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
        </section>
        <section id="products">

        </section>
    </main>
    <footer>
        <br>
    </footer>
</body>
</html>