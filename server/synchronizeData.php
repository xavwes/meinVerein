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

    $sql = "Select * from spiele_" . $teamname;
    $result = mysql_query($sql);

    while($row = mysql_fetch_array($result))
    {
        $gamearray = array("id" => $row["id"], "home" => $row["home"], "away" => $row["away"], "ergebnis" => $row["ergebnis"], "ort" => $row["ort"], "zeit" => $row["zeit"]);
        array_push($spiele, $gamearray);
    }


    echo json_encode($spiele);

?>