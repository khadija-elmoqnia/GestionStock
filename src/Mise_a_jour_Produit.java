import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;

public class Mise_a_jour_Produit {

    private static Integer IDProduitCounter = 1;

    private Integer IDProduit;
    private String NomProduit;
    private Double PrixUnitaire;
    private Integer QtéStock;
    private static JPanel panel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gestion de Stock");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        // Ajout d'un titre grand
        JLabel titleLabel = new JLabel("Mise à jour Produit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Ajustez la police et la taille selon vos besoins
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        frame.getContentPane().add(BorderLayout.NORTH, titleLabel); // Ajout du titre au nord
        panel = new JPanel(new GridBagLayout()); // Initialize panel

        frame.getContentPane().add(BorderLayout.CENTER, panel);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton ajouterButton = new JButton("Ajouter Produit");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(ajouterButton, constraints);

        JButton chercherButton = new JButton("Chercher Produit");
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(chercherButton, constraints);

        JButton modifierButton = new JButton("Modifier Produit");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(modifierButton, constraints);

        JButton supprimerButton = new JButton("Supprimer Produit");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(supprimerButton, constraints);

        frame.getContentPane().add(BorderLayout.CENTER, panel);

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddProductDialog(frame);
            }
        });

        chercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "0000";
                String productName = JOptionPane.showInputDialog(frame, "Nom du Produit à Chercher:");

                // Appeler une fonction pour rechercher le produit par son nom ici
                Produit produitcherche = new Produit();
                produitcherche.setNomProduit(productName);
                boolean success = produitcherche.chercherProduit(url, utilisateur, motDePasse);

                // Exemple: searchProduct(productName);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Le produit existe.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Le produit n'existe pas.");
                }
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = JOptionPane.showInputDialog(frame, "Nom du Produit à Modifier:");
                showModifyProductDialog(frame, productName);
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = JOptionPane.showInputDialog(frame, "Nom du Produit à Supprimer:");
                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "KHADIJA";

                // Appeler une fonction pour supprimer le produit par son nom ici
                Produit produitsuprime = new Produit();
                produitsuprime.setNomProduit(productName);
                //suprimer dans la table
                boolean success = produitsuprime.supprimerProduit(url, utilisateur, motDePasse);
                // Exemple: searchProduct(productName);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Le produit est supprimé avec succes .");
                } else {
                    JOptionPane.showMessageDialog(frame, "La supression a echoué ou le produit n'existe pas .");
                }


            }
        });

        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    private static void showAddProductDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Ajouter Produit", true);
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField idProduitField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField prixField = new JTextField();
        JTextField quantiteField = new JTextField();

        panel.add(new JLabel("ID Produit:"));
        panel.add(idProduitField);
        panel.add(new JLabel("Nom du Produit:"));
        panel.add(nomField);
        panel.add(new JLabel("Prix Unitaire:"));
        panel.add(prixField);
        panel.add(new JLabel("Quantité en Stock:"));
        panel.add(quantiteField);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les valeurs saisies
                Integer idProduit = Integer.parseInt(idProduitField.getText());
                String nom = nomField.getText();
                Double prix = Double.parseDouble(prixField.getText());
                Integer quantite = Integer.parseInt(quantiteField.getText());

                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "0000";
                Produit produitcree = new Produit(idProduit, nom, prix, quantite);
                //ajouter dans la base
                boolean success = produitcree.ajouterProduit(url, utilisateur, motDePasse);
                // Use parent instead of frame
                if (success) {
                    JOptionPane.showMessageDialog(parent, "Le produit est ajouté avec succes .");
                } else {
                    JOptionPane.showMessageDialog(parent, "Le produit n'est pas ajouté .");
                }

                // Fermer la fenêtre de dialogue
                dialog.dispose();
            }
        });

        panel.add(addButton);

        dialog.getContentPane().add(BorderLayout.CENTER, panel);
        dialog.setSize(300, 200);
        dialog.setVisible(true);
    }

    private static void showModifyProductDialog(JFrame parent, String productName) {
        JDialog dialog = new JDialog(parent, "Modifier Produit", true);
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField nomField = new JTextField();
        JTextField prixField = new JTextField();
        JTextField quantiteField = new JTextField();

        panel.add(new JLabel("Nouveau Nom du Produit:"));
        panel.add(nomField);
        panel.add(new JLabel("Nouveau Prix Unitaire:"));
        panel.add(prixField);
        panel.add(new JLabel("Nouvelle Quantité en Stock:"));
        panel.add(quantiteField);

        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les nouvelles valeurs saisies
                String newNom = nomField.getText();
                Double newPrix = Double.parseDouble(prixField.getText());
                Integer newQuantite = Integer.parseInt(quantiteField.getText());

                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "KHADIJA";

                Produit produitmodifie = new Produit();
                produitmodifie.setNomProduit(newNom);
                produitmodifie.setPrixUnitaire(newPrix );
                produitmodifie.setQtéStock(newQuantite);
                //modifier dans la base
                boolean success = produitmodifie.modifierProduit(url,utilisateur,motDePasse,productName);
                // Use parent instead of frame
                if (success) {
                    JOptionPane.showMessageDialog(parent, "Le produit est modifié avec succes .");
                } else {
                    JOptionPane.showMessageDialog(parent, "Le produit n'as pas eté modifié  .");
                }
                // Fermer la fenêtre de dialogue
                dialog.dispose();
            }
        });

        panel.add(modifyButton);

        dialog.getContentPane().add(BorderLayout.CENTER, panel);
        dialog.setSize(300, 150);
        dialog.setVisible(true);
}
}