<?php

include "../app/src/apikey.php";
include "../app/src/conn.php";

if (!isset($_POST["tokenID"]) || $_POST["tokenID"] == "") {
  die("error_no_token");
}
$CLIENT_ID = "617387717375-ef2np7sts30mmgbdg7dbgdlk6ujj4bjr.apps.googleusercontent.com";
$id_token = $_POST["tokenID"];
$fcm_token = $_POST["fcm_token"];

include_once("vendor/autoload.php");

$client = new Google_Client(['client_id' => $CLIENT_ID]);
$payload = $client->verifyIdToken($id_token);
if ($payload) {
  $userid = $payload['sub'];
  $new_auth_pw = hash("sha256", uniqid("fm"));
  $payload['auth_pw'] = $new_auth_pw;
  //1.if user is not already in db register him
  //2. else refresh the auth_pw

  //1. check if email is not registered yet
  $checkQuery = $conn->prepare("SELECT `email`, `ID`, `alt_email` FROM `user` WHERE email=?");
  $checkQuery->bind_param("s", $payload['email']);
  //execute query and save results
  $checkQuery->execute();
  $result = $checkQuery->get_result();
  //if internal server problem
  //if result > 0 means there is already an entry
  $uid = "";
  $email = "";
  $alt_email = "";
  while($row = mysqli_fetch_array($result)){
    $uid = $row['ID'];
    $email = $row['email'];
    $alt_email = $row['alt_email'];
  }
  if ($alt_email!="") {
    $payload['alt_email'] = $alt_email;
  }

  if ($uid == "") {
    //strings cannot be passed by reference so we need to declare them here
    $lastname = "GOOGLE_NO_LAST_NAME";
    $token = $fcm_token;
    $locale = "LOCALE_TODO";

    //register user here
    $registerQuery = $conn->prepare("INSERT INTO `user` (`ID`, `firstname`, `name`, `email`, `password`, `Token`, `acc_type`, `locale`, `email_confirmed`) VALUES (NULL, ?,?,?,?,?,1,?,1)");
    $registerQuery->bind_param("ssssss", $payload['name'], $lastname, $payload['email'], $payload['auth_pw'], $token, $locale);
    $runQuery = $registerQuery->execute();
    if (!$runQuery) {
      $payload['registration'] = "failure";
    } else {
      $payload['registration'] = "success";
    }
  } else {
    //2. login user here / refresh / update auth pw
    $loginQuery = $conn->prepare("UPDATE `user` SET `password`=? , `Token`=? WHERE user.ID = ?");
    $loginQuery->bind_param("sss", $payload['auth_pw'], $fcm_token, $uid);
    //execute query, save result
    $runQuery = $loginQuery->execute();
    if (!$runQuery) {
      $payload['login'] = "failure";
    } else {
      $payload['login'] = "success";
    }
  }


  // If request specified a G Suite domain:
  //$domain = $payload['hd'];
  die(json_encode($payload));
} else {
  // Invalid ID token
  die("error_invalid_token");
}

 ?>