        <div id="logoBox">
            <a href="index.php">
                <img src="../assets/logo.png" alt="logo strony" id="logo">
            </a>
        </div>
        <div id="searchBox">
            <form action="index.php" method="post" id="searchForm">
                <input type="text" name="search" id="searchField" placeholder="Szukaj produktów...">
                <button type="submit" id="searchButton">
                    <img src="../assets/search.png" alt="search icon" id="searchIcon">
                </button>
            </form>
        </div>
        <div id="buttonBox">
            <button id="cartButton">
                <a href="cart.php">
                    <img src="../assets/cart.png" alt="cart icon" id="cartIcon">
                </a>
            </button>
                <div id="userButton">
                    <p>Konto</p>
                <div id="userOptions">
                    <?php
                        $users = mysqli_query($db, "SELECT * FROM users WHERE `Email` = '".$_COOKIE['user']."'");
                        $user = mysqli_fetch_assoc($users);
                        if(strpos($user['Email'], "@")){
                            echo "<a href='account.php' id='top'>Zarządzaj kontem</a>";
                            if($user['Role'] == "a"){
                                echo "<a href='admin-items.php'>Panel administracyjny</a>";
                            }
                            echo "<a href='logout.php' id='bottom'>Wylogój</a>";
                        }else{
                            echo "<a href='login.php' id='top'>Zalogój się</a>";
                            echo "<a href='register.php' id='bottom'>Zarejestrój się</a>";
                        }
                    ?>
                </div>
                </div>
        </div>