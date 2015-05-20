<?php
/**
 * Created by PhpStorm.
 * User: Xaver
 * Date: 06.05.15
 * Time: 16:57
 */
    include('simplehtmldom_1_5/simple_html_dom.php');

    header('Access-Control-Allow-Origin: *');
    header('Access-Control-Allow-Methods: GET, POST');

    $search_input = $_GET['name'];

    $search_input = str_replace(" ", "%20", $search_input);
    $search_input = utf8_decode($search_input);

    searchTeam($search_input);

function searchTeam($search_input)
{

    $fupa_search_url = "http://www.fupa.net/index.php?page=suche_schnell&suche=" . $search_input;

    $fupa_search_response = file_get_html($fupa_search_url);

    $teams = array();

    $vereine_box = $fupa_search_response->find('div[class=right_modul]', 0);

    $substring = false;
    $counter = 0;
    foreach($vereine_box->find('tr') as $row)
    {
        if($counter>0)
        {
            if(is_null($row->find('td', 1)))
            {
                if(strlen($search_input)>7)
                {
                    $search_input_again = substr($search_input, 0, 6);
                    $substring = true;
                    searchTeam($search_input_again);
                }
                else
                {
                    $teamarray = array('teamlink' =>   "no" , 'teamname' => "no");
                    array_push($teams, $teamarray);
                }
            }
            else
            {
                $team_column = $row->find('td', 1);
                $team_link = $team_column->find('a', 0)->href;

                $team_name = $team_column->find('a', 0)->plaintext;
                $team_name = utf8_encode($team_name);

                $teamarray = array('teamlink' =>   $team_link , 'teamname' => $team_name);
                array_push($teams, $teamarray);
            }
        }

        $counter++;
    }

    if(!$substring)
    {
        $somejson = json_encode($teams);
        echo $somejson;
    }



}

?>