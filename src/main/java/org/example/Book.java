package org.example;

public class Book {
    private String titre;
    private String auteur;
    private boolean disponible = true;

    public Book(String titre, String auteur) {
        this.titre = titre;
        this.auteur = auteur;
        this.disponible = true;
    }

    public String getTitre() {
        return titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void emprunter() {
        this.disponible = false;
    }

    public void retourner() {
        this.disponible = true;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}

