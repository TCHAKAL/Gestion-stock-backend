package dz.tchakal.gds.exception;

public enum ErrorCode {
    //TODO complete error codes

    //Gestion des articles
    ARTICLE_NOT_FOUND(1000),
    ARTICLE_NOT_VALIDE(1001),
    //Gestion des catégories
    CATEGORIE_NOT_FOUND(2000),
    CATEGORIE_NOT_VALIDE(2001),
    //Gestion des Clients
    CLIENT_NOT_FOUND(3000),
    CLIENT_NOT_VALIDE(3001),
    COMMANDE_CLIENT_NOT_FOUND(4000),
    COMMANDE_CLIENT_NOT_VALIDE(4001),
    LIGNE_COMMANDE_CLIENT_NOT_FOUND(5000),

    //Gestion des fournisseur
    FOURNISSEUR_NOT_FOUND(6000),
    COMMANDE_FOURNISSEUR_NOT_FOUND(7000),
    LIGNE_COMMANDE_FOURNISSEUR_NOT_FOUND(8000),

    //Gestion des ventes
    VENTE_NOT_FOUND(9000),
    VENTE_NOT_VALIDE(9001),
    LIGNE_VENTE_NOT_FOUND(10000),

    //Gestion des mouvements
    MVT_NOT_FOUND(11000),

    //Administration
    ENTREPRISE_NOT_FOUND(12000),
    UTILISATEUR_NOT_FOUND(13000),
    ROLE_NOT_FOUND(14000);

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
