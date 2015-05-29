<?php
/**
 * Created by PhpStorm.
 * User: Xaver
 * Date: 13.05.15
 * Time: 14:57
 */

    include('simplehtmldom_1_5/simple_html_dom.php');
    include_once('db_connection.php');

    $team = $_GET['team'];
    $linkGet = $_GET['link'];

    $name = $team;
    $name = str_replace(".", "", $name);
    $name = str_replace("/", "", $name);
    $name = str_replace(" ", "", $name);
    $name = str_replace("ü", "ue", $name);


    $teams = array();
    $spiele = array();
    $json = array();

    $connection = connectToDatabase();

    $sql = 'CREATE TABLE IF NOT EXISTS ' . $name . '(
        id INT(255) NOT NULL auto_increment,
        mannschaft text NOT NULL,
        PRIMARY KEY (id) )';

    mysql_query($sql) or die("Tabelle not created1");

    $sql = 'CREATE TABLE IF NOT EXISTS spiele_' . $name . '(
        id INT(255) NOT NULL auto_increment,
        home text NOT NULL,
        away text NOT NULL,
        ergebnis text NOT NULL,
        ort text NOT NULL,
        zeit text NOT NULL,
        PRIMARY KEY (id) )';

    mysql_query($sql) or die("Tabelle not created2");

    $linkGet = "http://" . $linkGet;
    $html_team = file_get_html($linkGet);
    $i = 0;
    foreach($html_team->find('div[class=content_team_header]') as $team_div)
    {
        $team_name = $team_div->find('a',0);
        $team_name = $team_name->plaintext;
        $team_name = str_replace("(2.)", "", $team_name);

        if($i > 0)
        {
            $sql = 'Insert into ' . $name . ' (id, mannschaft)
                SELECT * FROM (SELECT 0,"' . $team_name . '") AS tmp
                    WHERE NOT EXISTS(
                    SELECT mannschaft FROM ' . $name . ' WHERE mannschaft ="' . $team_name . '") Limit 1';

            mysql_query($sql) or die("Anfrage failed");

            $team_name = utf8_decode($team_name);
            $teamarray = array('team' => $team_name);
            array_push($teams, $teamarray);

            $link_team = $team_div->find('a',0);
            $link_team = $link_team->href;
            $link_team = substr($link_team, 0, (strlen($link_team)-5));
            $link_team = "http://www.fupa.net" . $link_team . "/spielplan.html";

            $link_spielort_home = substr($linkGet, 0, strlen($linkGet)-5) . '/spielorte.html';

            $spielplan_html = file_get_html($link_team);

            $ligaspielplan = $spielplan_html->find('table[class=content_table_std]', 1);


            $html_spielort = file_get_html($link_spielort_home);
            $content_table = $html_spielort->find('table[class=content_table_std]', 0);
            if(!is_null($content_table->find('span',0)))
            {
                $spielort_home = $content_table->find('span', 0 )->plaintext;
            }
            else
            {
                $spielort_home = "";
            }


            $j = 0;
            foreach($ligaspielplan->find('tr') as $game_complete)
            {
                if($j>0)
                {
                    $zeit = $game_complete->find('td', 1)->plaintext;
                    $heimrecht = $game_complete->find('td', 2)->plaintext;
                    $gegner = $game_complete->find('td', 4)->plaintext;
                    $link_gegner_spielstätte = $game_complete->find('a', 1)->href;
                    $link_gegner_spielstätte = str_replace("teams", "vereine", $link_gegner_spielstätte);
                    $link_gegner_spielstätte = substr($link_gegner_spielstätte, 0, strlen($link_gegner_spielstätte)-12) . "/spielorte.html";

                    $ergebnis = $game_complete->find('a', 2)->plaintext;

                    //utf8 encoden
                    $gegner = utf8_decode($gegner);
                    $team_name = utf8_decode($team_name);
                    $ergebnis = utf8_decode($ergebnis);
                    $zeit = utf8_decode($zeit);

                    if($heimrecht == 'A')
                    {
                        $html_spielort = file_get_html($link_gegner_spielstätte);
                        $content_table = $html_spielort->find('table[class=content_table_std]', 0);
                        if(!is_null($content_table->find('span',0)))
                        {
                            $spielort = $content_table->find('span', 0 )->plaintext;
                        }
                        else
                        {
                            $spielort = "";
                        }

                        $spielort = utf8_decode($spielort);
                        $spielort = str_replace("\r\n", "", $spielort);

                        /**update von ergebnissen*/
                        /**insert in Table spiele_team*/
                        $sql = 'Insert into spiele_' . $name . ' (id, home, away, ergebnis, ort, zeit)
                                    SELECT * FROM (SELECT 0,"' . $gegner . '","'. $team_name . '","'. $ergebnis . '","'. $spielort . '","'. $zeit . '") AS tmp
                                     WHERE NOT EXISTS(
                                     SELECT home FROM spiele_' . $name .  ' WHERE home ="' . $gegner . '") Limit 1';

                        mysql_query($sql) or die("Anfrage failed");
                        $spielearray = array('home' => $gegner, 'away' => $team_name, 'ergebnis' => $ergebnis, 'spielort' => $spielort, 'zeit' => $zeit);
                    }
                    else
                    {
                        $spielort_home = utf8_decode($spielort_home);
                        $spielort_home = str_replace("\r\n", "", $spielort_home);
                        /**insert in Table spiele_team*/
                        $sql = 'Insert into spiele_' . $name . ' (id, home, away, ergebnis, ort, zeit)
                                    SELECT * FROM (SELECT 0,"' . $team_name . '","'. $gegner . '","'. $ergebnis . '","'. $spielort_home . '","'. $zeit . '") AS tmp
                                     WHERE NOT EXISTS(
                                     SELECT away FROM spiele_' . $name .  ' WHERE away ="' . $gegner . '") Limit 1';
                        mysql_query($sql) or die("Anfrage failed");
                        $spielearray = array('home' => $team_name, 'away' => $gegner, 'ergebnis' => $ergebnis, 'spielort' => $spielort_home, 'zeit' => $zeit);
                    }

                    array_push($spiele, $spielearray);
                }
                $j++;
            }
        }
        $i++;
    }

    array_push($json, $teams);
    array_push($json, $spiele);

    echo json_encode($json);

?>