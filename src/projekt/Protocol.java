
package com.example.menu;


/**
 * Created by Kuba on 2016-06-14.
 */

/**
 * Klasa, ktora definiuje polecenia komunikacji między serwerem a klientem.
 */

public class Protocol {
    static final String LOGIN = "Login";
    static final String LOGGEDIN = "LoggedIn";
    static final String LOGOUT = "Logout";
    static final String LOGGEDOUT = "LoggedOut";
    static final String SEND_LISTA = "SendLista";
    static final String HIGHSCORE_LIST = "HighScoreList";
    static final String HIGHSCORE_COUNTER= "HighScoreCounter";
    static final String HIGHSCORE_LIST_FINISHED = "HighScoreListFinished";
    static final String NEW_SCORE = "NewScore";
    static final String NEW_SCORE_SAVED = "NewScoreSaved";
    static final String NEW_SCORE_UNSAVED = "NewScoreUnsaved";
    static final String STOP = "Stop";
    static final String STOPPED = "Stopped";
    static final String NULLCOMMAND = "NullCommand";

}

