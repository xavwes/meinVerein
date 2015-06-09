package de.xwes.meinverein.main.model;

/**
 * Created by Xaver on 08.06.2015.
 */
public class Team
{
        private long id;
        private String teamname;

        public Team(long id, String teamname)
        {
            this.id = id;
            this.teamname = teamname;
        }

        public Team()
        {

        }

        public long getId()
        {
            return id;
        }

        public String getTeamname()
        {
            return teamname;
        }

        public void setId(long id)
        {
            this.id = id;
        }

        public void setTeamname(String teamname)
        {
            this.teamname = teamname;
        }

        @Override
        public String toString()
        {
            return "Team{" +
                    "id=" + id +
                    ", teamname='" + teamname + '\'' +
                    '}';
        }


}
