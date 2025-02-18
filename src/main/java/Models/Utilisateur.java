package Models;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;
    private String nationalite;
    private GENRE genre;
    private Profil p;
    private String role;  // Ajout de l'attribut role

    // Constructeur avec les paramètres nécessaires, y compris le rôle
    public Utilisateur(String nom, String prenom, String email, String mot_de_passe, String nationalite, String genreStr, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.nationalite = nationalite;
        this.role = role;  // Initialisation du rôle

        // Conversion du genre en énumération
        try {
            this.genre = GENRE.valueOf(genreStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de la conversion du genre : " + genreStr);
            this.genre = null;
        }
    }

    // Constructeur sans rôle, avec valeur par défaut pour le rôle
    public Utilisateur(String firstName, String lastName, String email, String password, String nationality, String genderStr) {
        this.nom = firstName;
        this.prenom = lastName;
        this.email = email;
        this.mot_de_passe = password;
        this.nationalite = nationality;
        this.role = "utilisateur";  // Valeur par défaut du rôle

        // Conversion du genre en énumération
        try {
            this.genre = GENRE.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur lors de la conversion du genre : " + genderStr);
            this.genre = null;
        }
    }

    // Constructeur vide
    public Utilisateur() {
    }

    // Getter pour le rôle
    public String getRole() {
        return role;
    }

    // Setter pour le rôle
    public void setRole(String role) {
        this.role = role;
    }

    // Getters et setters pour les autres attributs
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return mot_de_passe;
    }

    public void setMotDePasse(String mot_de_passe) {
        this.mot_de_passe = mot_de_passe;
    }

    public String getNationalite() {
        return nationalite;
    }

    public void setNationalite(String nationalite) {
        this.nationalite = nationalite;
    }

    public GENRE getGenre() {
        return genre;
    }

    public void setGenre(GENRE genre) {
        this.genre = genre;
    }

    public Profil getP() {
        return p;
    }

    public void setP(Profil p) {
        this.p = p;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", genre=" + genre +
                ", role='" + role + '\'' +  // Affichage du rôle dans toString()
                '}';
    }
}
