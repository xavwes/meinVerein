package de.xwes.meinverein.test;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import de.xwes.meinverein.main.model.Game;
import de.xwes.meinverein.main.model.GameDataSource;
import de.xwes.meinverein.main.model.MySQLiteHelper;

/**
 * Created by Xaver on 01.07.2015.
 */
public class DBTesting extends AndroidTestCase
{
    private MySQLiteHelper mySQLiteHelper;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        mySQLiteHelper = new MySQLiteHelper(context);
    }

    public void testInsertData()
    {
        Game testGame = new Game();
        testGame.setId(200);
        testGame.setHome("1. Mannschaft");
        testGame.setAway("Testgegner");
        testGame.setErgebnis("1:1");
        testGame.setZeit("So. 24.08.14 15:00");
        testGame.setOrt("Testweg 4 99999 Musterstadt");

        GameDataSource gameDataSource = new GameDataSource(getContext());
        gameDataSource.open();
        gameDataSource.createOrUpdateGame(testGame.getId(), testGame);

        Game game = gameDataSource.getGame(testGame.getId());

        assertEquals(true, testGame.equals(game));
    }
}
