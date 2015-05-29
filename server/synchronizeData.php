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

    $sql = "Select * from " . $teamname;
    $result = mysql_query($sql);

    if(mysql_num_rows($result) > 0)
    {

    }

    $sql = "Select * from spiele_" . $teamname;
    $result = mysql_query($sql);

    if(mysql_num_rows($result) > 0)
    {

    }


?>