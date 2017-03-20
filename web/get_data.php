<?php

include "auth.php";
include "conn.php";

$data = array();

//Kategorien
$query = $conn->prepare("SELECT * FROM kategorie");
if ($query->execute()) {
    $result = $query->get_result();
    while ($row = mysqli_fetch_array($result)) {
        $data[] = array("ID" => $row["ID"],
                        "name" => $row["name"],
                        "beschreibung" => $row["beschreibung"]);
    }
}

//Unterthemen
foreach ($data as $key => $entry) {
    $current_id = $entry["ID"];
    $query = $conn->prepare("SELECT * FROM thema WHERE FK_kat = ".$current_id);
    if ($query->execute()) {
        $result = $query->get_result();
        while ($row = mysqli_fetch_array($result)) {
            $thema = array("titel" => $row["titel"],
                            "text" => $row["text"],
                            "bild" => $row["bild"]);
            $data[$key]["themen"][] = $thema;
        }
    }
}


echo json_encode(array("data" => $data), JSON_FORCE_OBJECT);
?>
