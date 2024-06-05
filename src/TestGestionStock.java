import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestGestionStock {

    // Informations de connexion à la base de données
    static String url = "jdbc:postgresql://localhost:5432/gestionstock";
    static String utilisateur = "postgres";
    static String motDePasse = "KHADIJA";

    public static void main(String[] args) {

        // Création d'objets Client et ajout des clients à la base de données
        Client client1 = new Client(1, "Khadija", "Adresse1", "khadija@email.com", "123-456-7890");
        Client client2 = new Client(2, "Meriem", "Adresse2", "Meriem@email.com", "987-654-3210");
        Client client3 = new Client(3, "Mohammed", "Adresse3", "Mohammed@email.com", "111-222-3333");
        Client client4 = new Client(4, "Yasmine", "Adresse4", "Yasmine@email.com", "555-666-7777");
        Client client5 = new Client(5, "Amina", "Adresse5", "Amina@email.com", "888-999-0000");
        Client client6 = new Client(6, "Amal", "Adresse6", "Amal@email.com", "444-333-2222");
        Client client7 = new Client(7, "Zakaria", "Adresse7", "Zakaria@email.com", "123-789-4560");
        Client client8 = new Client(8, "Ibrahim", "Adresse8", "Ibrahim@email.com", "987-654-3210");
        Client client9 = new Client(9, "Nabila", "Adresse9", "Nabila@email.com", "111-222-3333");
        Client client10 = new Client(10, "Othmane", "Adresse10", "Othmane@email.com", "555-666-7777");

        // Appels des méthodes d'ajout pour chaque client
        client1.ajouterClient(url, utilisateur, motDePasse);
        client2.ajouterClient(url, utilisateur, motDePasse);
        client3.ajouterClient(url, utilisateur, motDePasse);
        client4.ajouterClient(url, utilisateur, motDePasse);
        client5.ajouterClient(url, utilisateur, motDePasse);
        client6.ajouterClient(url, utilisateur, motDePasse);
        client7.ajouterClient(url, utilisateur, motDePasse);
        client8.ajouterClient(url, utilisateur, motDePasse);
        client9.ajouterClient(url, utilisateur, motDePasse);
        client10.ajouterClient(url, utilisateur, motDePasse);

        // Création d'objets Produit et ajout des produits à la base de données
        Produit produit1 = new Produit(1, "Toyota Camry", 150000.00, 10);
        Produit produit2 = new Produit(2, "Mercedes Classe G", 300000.00, 5);
        Produit produit3 = new Produit(3, "Benz AMG", 400000.00, 3);
        Produit produit4 = new Produit(4, "GLE Coupe", 350000.00, 7);
        Produit produit5 = new Produit(5, "Audi A6", 200000.00, 8);
        Produit produit6 = new Produit(6, "BMW X5", 250000.00, 6);
        Produit produit7 = new Produit(7, "Tesla Model S", 500000.00, 2);
        Produit produit8 = new Produit(8, "Lamborghini Huracan", 600000.00, 1);
        Produit produit9 = new Produit(9, "Ford Mustang", 100000.00, 12);
        Produit produit10 = new Produit(10, "Chevrolet Corvette", 120000.00, 9);

        // Appels des méthodes d'ajout pour chaque produit
        produit1.ajouterProduit(url, utilisateur, motDePasse);
        produit2.ajouterProduit(url, utilisateur, motDePasse);
        produit3.ajouterProduit(url, utilisateur, motDePasse);
        produit4.ajouterProduit(url, utilisateur, motDePasse);
        produit5.ajouterProduit(url, utilisateur, motDePasse);
        produit6.ajouterProduit(url, utilisateur, motDePasse);
        produit7.ajouterProduit(url, utilisateur, motDePasse);
        produit8.ajouterProduit(url, utilisateur, motDePasse);
        produit9.ajouterProduit(url, utilisateur, motDePasse);
        produit10.ajouterProduit(url, utilisateur, motDePasse);

        testGestionClients();
        testGestionProduits();

    }

    public static void testGestionClients() {
        // Tests pour la gestion des clients
        System.out.println("--------------- Gestion des Clients ---------------");

        // Test d'ajout d'un client
        System.out.println("------------------------");
        System.out.println("Test d'ajout d'un client :");
        Client nouveauClient = new Client();
        nouveauClient.setIDClient(15);
        nouveauClient.setNomClient("NomTest"); // Remplacez "NouveauNomClient" par le nom du nouveau client
        nouveauClient.setAdresseClient("AdresseTest"); // Remplacez "AdresseClient" par l'adresse du nouveau client
        nouveauClient.setEmailClient("EmailTest"); // Remplacez "EmailClient" par l'email du nouveau client
        nouveauClient.setTelClient("NumeroTest");
        boolean ajoutClientSuccess = nouveauClient.ajouterClient(url, utilisateur, motDePasse);
        if (ajoutClientSuccess) {
            System.out.println("Le client " + nouveauClient.getNomClient() + " a été ajouté avec succès.");
        } else {
            System.out.println("Échec de l'ajout du client " + nouveauClient.getNomClient() + ".");
        }

        // Test de recherche d'un client
        System.out.println("------------------------");
        System.out.println("Test de recherche d'un client :");
        Client clientRecherche = new Client();
        clientRecherche.setNomClient("Yasmine");
        boolean rechercheClientSuccess = clientRecherche.chercherClient(url, utilisateur, motDePasse);
        if (rechercheClientSuccess) {
            System.out.println("Le client " + clientRecherche.getNomClient() + " a été trouvé dans la base de données.");
        } else {
            System.out.println("Le client " + clientRecherche.getNomClient() + " n'a pas été trouvé dans la base de données.");
        }

        // Test de suppression d'un client
        System.out.println("------------------------");
        System.out.println("Test de suppression d'un client :");
        Client clientASupprimer = new Client();
        clientASupprimer.setNomClient("NomClientTeest");
        boolean suppressionClientSuccess = clientASupprimer.supprimerClient(url, utilisateur, motDePasse);
        if (suppressionClientSuccess) {
            System.out.println("Le client " + clientASupprimer.getNomClient() + " a été supprimé avec succès.");
        } else {
            System.out.println("Échec de la suppression du client " + clientASupprimer.getNomClient() + "ou le client n'existe pas.");
        }
        System.out.println("------------------------");

        // Test de Modification  d'un client
        System.out.println("------------------------");
        System.out.println("Test de Modification d'un client :");
        String clientAmodifier="Zakaria";
        Client clientmodifié = new Client();
        clientmodifié.setNomClient("AICHAA"); // Remplacez "NouveauNomClient" par le nom du nouveau client
        clientmodifié.setAdresseClient("Adressemodifie"); // Remplacez "AdresseClient" par l'adresse du nouveau client
        clientmodifié.setEmailClient("Emailtmodifie"); // Remplacez "EmailClient" par l'email du nouveau client
        clientmodifié.setTelClient("Numeromodifie");

        boolean Success = clientmodifié.modifierClient(url, utilisateur, motDePasse,clientAmodifier);
        if (Success) {
            System.out.println("Le client " + clientAmodifier + " a été modifié avec "+ clientmodifié.getNomClient() +"  avec succès.");
        } else {
            System.out.println("Échec de la modification du client " + clientAmodifier + ".");
        }
        System.out.println("------------------------");
    }
    public static void testGestionProduits() {
        // Tests pour la gestion des produits
        System.out.println("--------------- Gestion des Produits ---------------");

        // Test d'ajout d'un produit
        System.out.println("------------------------");
        System.out.println("Test d'ajout d'un produit :");
        Produit nouveauProduit = new Produit();
        nouveauProduit.setIDProduit(101); // ID du nouveau produit
        nouveauProduit.setNomProduit("ProduitTest"); // Nom du nouveau produit
        nouveauProduit.setPrixUnitaire(100.00); // Prix unitaire du nouveau produit
        nouveauProduit.setQtéStock(10); // Quantité en stock du nouveau produit
        boolean ajoutProduitSuccess = nouveauProduit.ajouterProduit(url, utilisateur, motDePasse);
        if (ajoutProduitSuccess) {
            System.out.println("Le produit " + nouveauProduit.getNomProduit() + " a été ajouté avec succès.");
        } else {
            System.out.println("Échec de l'ajout du produit " + nouveauProduit.getNomProduit() + ".");
        }

        // Test de recherche d'un produit
        System.out.println("------------------------");
        System.out.println("Test de recherche d'un produit :");
        Produit produitRecherche = new Produit();
        produitRecherche.setNomProduit("Toyota Camry");
        boolean rechercheProduitSuccess = produitRecherche.chercherProduit(url, utilisateur, motDePasse);
        if (rechercheProduitSuccess) {
            System.out.println("Le produit " + produitRecherche.getNomProduit() + " a été trouvé dans la base de données.");
        } else {
            System.out.println("Le produit " + produitRecherche.getNomProduit() + " n'a pas été trouvé dans la base de données.");
        }

        // Test de suppression d'un produit
        System.out.println("------------------------");
        System.out.println("Test de suppression d'un produit :");
        Produit produitASupprimer = new Produit();
        produitASupprimer.setNomProduit("NomProduitTest");
        boolean suppressionProduitSuccess = produitASupprimer.supprimerProduit(url, utilisateur, motDePasse);
        if (suppressionProduitSuccess) {
            System.out.println("Le produit " + produitASupprimer.getNomProduit() + " a été supprimé avec succès.");
        } else {
            System.out.println("Échec de la suppression du produit " + produitASupprimer.getNomProduit() + ".");
        }

        System.out.println("------------------------");


        // Test de Modification  d'un produit
        System.out.println("------------------------");
        System.out.println("Test de Modification d'un Produit :");
        String produitAmodifier="BMW X5";
        Produit produitmodifié = new Produit();
        produitmodifié.setNomProduit("Tesla Model S");
        produitmodifié.setPrixUnitaire(500000.00);
        produitmodifié.setQtéStock(2);

        boolean Success =produitmodifié.modifierProduit(url, utilisateur, motDePasse,produitAmodifier);
        if (Success) {
            System.out.println("Le produit" + produitAmodifier + " a été modifié avec "+ produitmodifié.getNomProduit() +"  avec succès.");
        } else {
            System.out.println("Échec de la modification du produit " + produitAmodifier + ".");
        }
        System.out.println("------------------------");
    }

}