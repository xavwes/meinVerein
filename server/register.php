<?php
/**
 * Created by PhpStorm.
 * User: Xaver
 * Date: 13.05.15
 * Time: 13:57
 */
    include_once('db_connection.php');

    $connection = connectToDatabase();

    $teamname = $_GET['name'];
    $teamlink = $_GET['link'];

    $teamname = str_replace(" ", "", $teamname);
    $teamname = str_replace("/", "", $teamname);
    $teamname = str_replace(".", "", $teamname);
    $teamname = str_replace("-", "", $teamname);
    $teamname = strtolower($teamname);

    $sql = "Select * from vereine where teamname='" . $teamname . "' and link='" . $teamlink . "'";
    $ergebnis = mysql_query($sql);
    $response = "";
    if(mysql_num_rows($ergebnis) == 0 )
    {
        $sql = "Insert into vereine(id, teamname, link)
            SELECT * FROM (SELECT 0,'" . $teamname . "','" . $teamlink . "') as tmp
            where not exists(Select teamname from vereine where teamname='" . $teamname . "')";


        if(mysql_query($sql))
        {
            $response= "Status=ok";
        }
        else
        {
            $response="Status=fail";
        }
    }
    else
    {
        $response = "Status=existing";
    }



    echo $response;




?>
