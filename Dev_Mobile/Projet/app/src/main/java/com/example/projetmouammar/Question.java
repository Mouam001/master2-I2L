package com.example.projetmouammar;

import java.util.List;

public class Question {
    private final String categorie;
    private final String ennonce;
    private final List<String> reponses;
    private final int bonneReponse;

    public Question(String categorie, String ennonce, List<String> reponses, int bonneReponse) {
        this.categorie = categorie;
        this.ennonce = ennonce;
        this.reponses = reponses;
        this.bonneReponse = bonneReponse;
    }


    public String getCategorie() {
        return categorie;
    }

    public String getEnnonce() {
        return ennonce;
    }

    public List<String> getReponses() {
        return reponses;
    }

    public int getBonneReponse() {
        return bonneReponse;
    }
}
