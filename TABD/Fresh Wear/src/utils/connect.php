<?php
    $db = mysqli_connect("localhost", "root", "", "shop");

    function generateUserTag($db) {
        $userTag = bin2hex(random_bytes(rand(8, 16) / 2));
        $users = mysqli_query($db, "SELECT * FROM users WHERE `Email` = '".$userTag."'");
        if(mysqli_num_rows($users) != 0){
            return generateUserTag();
        } else {
            return $userTag;
        }
    }
    if(!isset($_COOKIE['user'])) {
        $userTag = generateUserTag($db);
        setcookie('user', $userTag, time() + 86400, "/");
        mysqli_query($db, "INSERT INTO users (`Email`, `Username`, `Password`, `Role`) VALUES ('".$userTag."', '', '', 'u')");
        header("Refresh:0");
    }
?>