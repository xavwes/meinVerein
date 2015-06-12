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
        $gamearray = array("home" => $row["home"], "away" => $row["away"], "ergebnis" => $row["ergebnis"], "ort" => $row["ort"], "zeit" => $row["zeit"]);
        array_push($spiele, $gamearray);
    }

    array_push($json, $teams);
    array_push($json, $spiele);

    echo json_encode($json);

?>