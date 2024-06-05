import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Produit {
    private static Map<String, Integer> quantiteStockParNom = new HashMap<>();
    private static Map<String, Double> prixParNom = new HashMap<>();

    private Integer IDProduit;
    private String NomProduit;
    private Double PrixUnitaire ;
    private Integer QtéStock;

    // Constructeurs
    public Produit() {
        this.PrixUnitaire = 0.0; // Initialize PrixUnitaire to 0.0
        this.QtéStock = 0;       // Initialize QtéStock to 0
        this.IDProduit = null;
        this.NomProduit = null;
    }

    public Produit(Integer idProduit, String nomProduit, Double prixUnitaire, Integer qtéStock) {
        this.IDProduit = idProduit;
        this.NomProduit = nomProduit;
        this.PrixUnitaire = prixUnitaire;
        this.QtéStock = qtéStock;

        // Si le nom n'est pas encore dans la Map, ajoutez-le avec une quantité de stock et un prix initial
        quantiteStockParNom.putIfAbsent(nomProduit, 0);
        prixParNom.putIfAbsent(nomProduit, 0.0);
    }

    // Méthode statique pour obtenir la quantité de stock par nom
    public static Integer getQuantiteStockParNom(String nomProduit) {
        return quantiteStockParNom.getOrDefault(nomProduit, 0);
    }

    public static void setQuantiteStockParNom(String nomProduit, Integer nouvelleQuantite) {
        quantiteStockParNom.put(nomProduit, nouvelleQuantite);
    }
    public static void setPrixParNom(String nomProduit, Double nouveauPrix) {
        prixParNom.put(nomProduit, nouveauPrix);
    }

    // Méthode statique pour obtenir le prix par nom
    public static Double getPrixParNom(String nomProduit) {
        return prixParNom.getOrDefault(nomProduit, 0.0);
    }


    // Getters
    public Integer getIDProduit() {
        return IDProduit;
    }

    public String getNomProduit() {
        return NomProduit;
    }

    public double getPrixUnitaire() {
        return PrixUnitaire;
    }

    public Integer getQtéStock() {
        return QtéStock;
    }

    // Setters
    public void setIDProduit(Integer IDProduit) {
        this.IDProduit = IDProduit;
    }

    public void setNomProduit(String NomProduit) {
        this.NomProduit = NomProduit;
    }

    public void setPrixUnitaire(Double PrixUnitaire) {
        this.PrixUnitaire = PrixUnitaire;
    }

    public void setQtéStock(Integer QtéStock) {
        this.QtéStock = QtéStock;
    }

    // Méthodes de mise à jour (Chercher, Ajouter, Modifier, Supprimer)
    public boolean chercherProduit(String url, String utilisateur, String motDePasse) {
        // Requête de recherche de produits
        String query = "SELECT * FROM Produit WHERE NomProduit = ?";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètre pour la recherche par nom de produit
            statement.setString(1, this.NomProduit);

            // Exécution de la requête de recherche
            try (ResultSet resultSet = statement.executeQuery()) {
                // Affichage des résultats
                while (resultSet.next()) {
                    Integer idProduit = resultSet.getInt("IDProduit");
                    String nomProduit = resultSet.getString("NomProduit");
                    Double prixUnitaire = resultSet.getDouble("PrixUnitaire");
                    Integer quantiteStock = resultSet.getInt("QtéStock");

                    success = true;                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }


    public boolean ajouterProduit(String url, String utilisateur, String motDePasse) {
        // Requête d'ajout de produit
        String query = "INSERT INTO Produit (IDProduit, NomProduit, PrixUnitaire, QtéStock) VALUES (?, ?, ?, ?)";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètres pour l'ajout de produit
            statement.setInt(1, this.IDProduit);
            statement.setString(2, this.NomProduit);
            statement.setDouble(3, this.PrixUnitaire);
            statement.setInt(4, this.QtéStock);

            // Exécution de la requête d'ajout
            int rowsAffected = statement.executeUpdate();

            success = (rowsAffected > 0);

            // Mettez à jour la quantité de stock partagée lors de l'ajout d'un nouveau produit
            Integer quantiteExistante = Produit.getQuantiteStockParNom(this.NomProduit);
            Produit.setQuantiteStockParNom(this.NomProduit, quantiteExistante + this.QtéStock);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }




    public boolean modifierProduit(String url, String utilisateur, String motDePasse, String nomAncien) {
        // Requête de modification de produit
        String query = "UPDATE Produit SET PrixUnitaire = ?, QtéStock = ?, NomProduit = ? WHERE NomProduit = ?";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètres pour la modification de produit
            if (this.PrixUnitaire != null) {
                statement.setDouble(1, this.PrixUnitaire);
            }

            if (this.QtéStock != null) {
                statement.setInt(2, this.QtéStock);
            }

            if (this.NomProduit != null) {
                statement.setString(3, this.NomProduit);
            }
            // Ancien nom pour la clause WHERE
            statement.setString(4, nomAncien);
            // Exécution de la requête de modification
            int rowsAffected = statement.executeUpdate();

            success = (rowsAffected > 0);

            // Mettez à jour la quantité de stock et le prix partagés lors de la modification du produit
            if (success && this.NomProduit != null && this.QtéStock != null) {
                Produit.setQuantiteStockParNom(this.NomProduit, this.QtéStock);
            }
            if (success && this.NomProduit != null && this.PrixUnitaire != null) {
                Produit.setPrixParNom(this.NomProduit, this.PrixUnitaire);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }






    public boolean supprimerProduit(String url, String utilisateur, String motDePasse) {
        // Requête de suppression de produit
        String query = "DELETE FROM Produit WHERE NomProduit = ?";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètre pour la suppression de produit
            statement.setString(1, this.NomProduit);

            // Exécution de la requête de suppression
            int rowsAffected = statement.executeUpdate();

            success = (rowsAffected > 0);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
}

}