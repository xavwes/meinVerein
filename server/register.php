<?php
/**
 * Created by PhpStorm.
 * User: Xaver
 * Date: 13.05.15
 * Time: 13:57
 */
    $host = "localhost";
    $user = "root";
    $pw = "";
    $mysqldb = "fussball_app";

   
    $connection = mysql_connect($host, $user, $pw) or die ("Verbindung fehlgeschlagen");

    mysql_select_db($mysqldb, $connection) or die("DB nicht gefunden");

    $teamname = $_GET['name'];
    $teamlink = $_GET['link'];

    $teamname = str_replace(" ", "", $teamname);
    $teamname = str_replace("/", "", $teamname);
    $teamname = str_replace(".", "", $teamname);
    $teamname = strtolower($teamname);

    $sql = "Insert into vereine(id, teamname, link)
    SELECT * FROM (SELECT 0,'" . $teamname . "','" . $teamlink . "') as tmp
        where not exists(Select teamname from vereine where teamname='" . $teamname . "')";

    $response = array();
    if(mysql_query($sql))
    {
        $response= array("Status" =>"ok");
    }
    else
    {
        $response=array("Status" => "fail");
    }

    echo json_encode($response);




?>
