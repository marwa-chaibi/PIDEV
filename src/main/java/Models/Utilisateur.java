package Models;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String mot_de_passe;
    private String nationalite;
    private  GENRE genre;

    public Utilisateur() {
    }


    public Utilisateur(int id, String nom, String prenom, String email, String mot_de_passe, String nationalite, GENRE genre) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mot_de_passe = mot_de_passe;
        this.nationalite = nationalite;
        this.genre = genre;
    }


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


    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mot_de_passe='" + mot_de_passe + '\'' +
                ", nationalite='" + nationalite + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
