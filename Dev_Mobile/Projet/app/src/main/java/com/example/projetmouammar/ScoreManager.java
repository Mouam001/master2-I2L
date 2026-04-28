package com.example.projetmouammar;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

/**
 * Gere la sauvegarde des scores et l'état  deja completé de chaque questionnaire.
 * stockage simple via SharedPreferences.
 */
public class ScoreManager {
    private static final String PREFS = "QCM_PREFS";

    // Deja fait
    private static final String KEY_PRE_DONE = "prehistoire_done";
    private static final String KEY_INFO_DONE = "informatique_done";
    private static final String KEY_LITT_DONE = "litterature_done";

    // Scores
    private static final String KEY_PRE_SCORE = "prehistoire_score";
    private static final String KEY_INFO_SCORE = "informatique_score";
    private static final String KEY_LITT_SCORE = "litterature_score";

    /**
     * Sauvegarde le score et l'état "deja fait" d'un questionnaire.
     * quizID = "prehistoire", "informatique", "litterature"
     */

    public static void saveScore(Context context, String quizID, int score) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        switch (quizID) {
            case "prehistoire":
                editor.putBoolean(KEY_PRE_DONE, true);
                editor.putInt(KEY_PRE_SCORE, score);
                break;
            case "informatique":
                editor.putBoolean(KEY_INFO_DONE, true);
                editor.putInt(KEY_INFO_SCORE, score);
                break;
            case "litterature":
                editor.putBoolean(KEY_LITT_DONE, true);
                editor.putInt(KEY_LITT_SCORE, score);
                break;
            default:
                // categories admins
                editor.putBoolean(quizID + "_done", true);
                editor.putInt(quizID + "_score", score);
        }
        editor.apply();
    }

    // Recupere le score d'un quiz

    public static int getScore(Context context, String quizID) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        switch (quizID) {
            case "prehistoire":
                return prefs.getInt(KEY_PRE_SCORE, 0);
            case "informatique":
                return prefs.getInt(KEY_INFO_SCORE, 0);
            case "litterature":
                return prefs.getInt(KEY_LITT_SCORE, 0);
            default:
                return prefs.getInt(quizID + "_score", 0);
        }
    }

    // Dit si un quiz a deja eté completé

    public static boolean isDone(Context context, String quizID) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        switch (quizID) {
            case "prehistoire":
                return prefs.getBoolean(KEY_PRE_DONE, false);
            case "informatique":
                return prefs.getBoolean(KEY_INFO_DONE, false);
            case "litterature":
                return prefs.getBoolean(KEY_LITT_DONE, false);
            default:
                return prefs.getBoolean(quizID + "_done", false);
        }
    }

    // Reinitialise tous les scores et états "deja fait"
    public static void resetAll(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
    }

    public static Map<String, ?> getAllScores(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return prefs.getAll();
    }


}