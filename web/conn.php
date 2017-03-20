<?php

    $host_name  = "localhost";
    $database   = "informatikapp";
    $user_name  = "informatikapp";
    $password   = "dbpw1";

    $conn = mysqli_connect($host_name, $user_name, $password, $database);

    if(mysqli_connect_errno()) {
        die("<p>SQL Server Error</p>");
    }

    $utf = $conn->prepare("SET NAMES 'utf8'");
    $utf->execute();
    $utf8 = $conn->prepare("SET CHARACTER SET 'utf8'");
    $utf8->execute();

?>
