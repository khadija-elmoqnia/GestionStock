import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Client {
    private Integer IDClient;
    private String NomClient;
    private String AdresseClient;
    private String EmailClient;
    private String TelClient;

    // Constructeurs
    public Client() {
    }

    public Client(Integer idClient, String nomClient, String adresseClient, String emailClient, String telClient) {
        this.IDClient = idClient;
        this.NomClient = nomClient;
        this.AdresseClient = adresseClient;
        this.EmailClient = emailClient;
        this.TelClient = telClient;
    }

    // Getters et Setters
    public Integer getIDClient() {
        return IDClient;
    }

    public void setIDClient(Integer IDClient) {
        this.IDClient = IDClient;
    }

    public String getNomClient() {
        return NomClient;
    }

    public void setNomClient(String nomClient) {
        this.NomClient = nomClient;
    }

    public String getAdresseClient() {
        return AdresseClient;
    }

    public void setAdresseClient(String adresseClient) {
        this.AdresseClient = adresseClient;
    }

    public String getEmailClient() {
        return EmailClient;
    }

    public void setEmailClient(String emailClient) {
        this.EmailClient = emailClient;
    }

    public String getTelClient() {
        return TelClient;
    }

    public void setTelClient(String telClient) {
        this.TelClient = telClient;
    }


    // Méthodes de mise à jour (Chercher, Ajouter, Modifier, Supprimer)
    public boolean chercherClient(String url, String utilisateur, String motDePasse) {
        // Requête de recherche de clients
        String query = "SELECT * FROM Client WHERE NomClient = ?";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètre pour la recherche par nom de client
            statement.setString(1, this.NomClient);

            // Exécution de la requête de recherche
            try (ResultSet resultSet = statement.executeQuery()) {
                // Affichage des résultats
                while (resultSet.next()) {
                    int idClient = resultSet.getInt("IDClient");
                    String nomClient = resultSet.getString("NomClient");
                    String adresseClient = resultSet.getString("AdresseClient");
                    String emailClient = resultSet.getString("EmailClient");
                    String telClient = resultSet.getString("TelClient");

                    success = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean ajouterClient(String url, String utilisateur, String motDePasse) {

        // Requête d'ajout de client
        String query = "INSERT INTO Client (IDClient, NomClient, AdresseClient, EmailClient, TelClient) VALUES (?, ?, ?, ?, ?)";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètres pour l'ajout de client
            statement.setInt(1, this.IDClient);
            statement.setString(2, this.NomClient);
            statement.setString(3, this.AdresseClient);
            statement.setString(4, this.EmailClient);
            statement.setString(5, this.TelClient);

            // Exécution de la requête d'ajout
            int rowsAffected = statement.executeUpdate();

            success = (rowsAffected > 0);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean modifierClient(String url, String utilisateur, String motDePasse, String nomAncien) {
        // Requête de modification de client
        String query = "UPDATE Client SET NomClient = ?, AdresseClient = ?, EmailClient = ?, TelClient = ? WHERE  NomClient= ?";
        boolean success = false;
        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètres pour la modification de client
            statement.setString(1, this.NomClient);
            statement.setString(2, this.AdresseClient);
            statement.setString(3, this.EmailClient);
            statement.setString(4, this.TelClient);
            statement.setString(5, nomAncien);

            // Exécution de la requête de modification
            int rowsAffected = statement.executeUpdate();

            success = (rowsAffected > 0);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public boolean supprimerClient(String url, String utilisateur, String motDePasse) {
        // Requête de suppression de client
        String query = "DELETE FROM Client WHERE NomClient = ?";
        boolean success = false;

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse);
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Paramètre pour la suppression de client
            statement.setString(1,this.NomClient);

            // Exécution de la requête de suppression
            int rowsAffected = statement.executeUpdate();

            success = (rowsAffected > 0);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
}
}