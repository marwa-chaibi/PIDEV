package tests;

import Models.GENRE;
import Models.Utilisateur;
import Service.ServiceUtilisateur;
import tools.DataBase;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ServiceUtilisateur su = new ServiceUtilisateur();
        //Utilisateur u = new Utilisateur(0,"Amine", "souibgui", "amine@gmail.com", "amine", "Tunisien", "Homme");//
        //Utilisateur u1 = new Utilisateur(1,"eya", "rachdi", "rachdi@gmail.com", "EYA", "tunisienne", "femme");
        //Utilisateur u1 = new Utilisateur(2,"eya", "rachdi", "rachdi@gmail.com", "EYA", "tunisienne", GENRE);

        try {
            //su.ajouter(u1);
            su.supprimer(5);
           // su.modifier(1,"marwa");
           // System.out.println(sv.recuperer());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
