package tests;

import Models.GENRE;
import Models.Profil;
import Models.Utilisateur;
import Services.ServiceProfil;
import Services.ServiceUtilisateur;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // Création du service pour l'utilisateur
        Services.ServiceUtilisateur su = new Services.ServiceUtilisateur();

        // Création d'un objet Utilisateur
       // Utilisateur u3 = new Utilisateur(2, "sana", "faychi", "sana@gmail.com", "sana", "Tunisienne", GENRE.FEMME);

        // Ajout de l'utilisateur
        //su.ajouter(u3);
        // Décommente pour d'autres tests :
        // su.supprimer(9);
        // su.modifier(u3);
        // System.out.println(su.recuperer());

        // Création du service pour le profil
        Services.ServiceProfil sp = new Services.ServiceProfil();

        // Création d'un objet Profil
        Profil p = new Profil();
        p.setId_utilisateur(2);  // Associer à l'utilisateur créé
        p.setEmail("sana@gmail.com");
        p.setNum_tel("123456789");
        p.setAdresse("Tunisienne");

        try {
            // Ajout du profil
            // sp.ajouter(p);
            // Décommente pour d'autres tests :
            // sp.supprimer(2);  // Supprimer un profil
            sp.modifier(5, "sanafaychi@gmail.com", "987654321", "Esprit");
            // System.out.println(sp.recuperer());
        } catch (SQLException e) {
            System.err.println("Erreur Profil : " + e.getMessage());
        }
    }
}
