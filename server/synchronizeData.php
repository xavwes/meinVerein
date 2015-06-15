<?php
/**
 * Created by PhpStorm.
 * User: Xaver
 * Date: 26.05.15
 * Time: 13:49
 */

    include_once('db_connection.php');

    $connection = connectToDatabase();

    $teamname = $_GET['name'];
    $json = array();
    $spiele = array();
    $teams = array();

    $sql = "Select * from " . $teamname;
    $result = mysql_query($sql);

    while($row = mysql_fetch_array($result))
    {
        $teamarray = array("id" => $row['id'], "team" => $row["mannschaft"]);
        array_push($teams,$teamarray);
    }

    $sql = "Select * from spiele_" . $teamname;
    $result = mysql_query($sql);

    while($row = mysql_fetch_array($result))
    {
        $gamearray = array("id" => $row['id'], "home" => utf8_decode($row["home"]), "away" => utf8_decode($row["away"]), "ergebnis" => utf8_decode($row["ergebnis"]), "ort" => utf8_decode($row["ort"]), "zeit" => utf8_decode($row["zeit"]));
        array_push($spiele, $gamearray);
    }

    array_push($json, $teams);
    array_push($json, $spiele);

    echo json_encode($json);

?>