<?php
/**
 * Created by PhpStorm.
 * User: Xaver
 * Date: 11.05.15
 * Time: 17:28
 */
    $host = "localhost";
    $user = "root";
    $pw = "";
    $mysqldb = "fussball_app";

    /*
    $host = "xwes.de.mysql";
    $user = "xwes_de";
    $pw = "mpx17em0892";
    $mysqldb = "fußball_app";
    */

    $connection = mysql_connect($host, $user, $pw) or die("Verbindung fehlgeschlagen");
    mysql_select_db($mysqldb, $connection) or die("DB nicht gefunden");

    $teamname = $_GET['teamname'];
    $teamlink = $_GET['link'];

$sql = 'CREATE TABLE IF NOT EXISTS ' . $name . '(
    id INT(255) NOT NULL auto_increment,
    mannschaft text NOT NULL,
    PRIMARY KEY (id) )';

?>