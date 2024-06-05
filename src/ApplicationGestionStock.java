import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class  ApplicationGestionStock {
    private static Produit produitEnCoursDeCommande;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }


    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Application de Gestion de Stock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Application pour Gestion de Stock", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(titleLabel, constraints);

        JButton gestionClientButton = new JButton("Gestion Client");
        gestionClientButton.setPreferredSize(new Dimension(200, 50));
        gestionClientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension windowSize = frame.getSize(); // Obtenir la taille de la fenêtre principale
                Mise_a_jour_Client.main(null); // Afficher la page de gestion des clients avec la même taille
            }
        });
        constraints.gridy = 1;
        mainPanel.add(gestionClientButton, constraints);

        JButton gestionProduitButton = new JButton("Gestion Produit");
        gestionProduitButton.setPreferredSize(new Dimension(200, 50));
        gestionProduitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Dimension windowSize = frame.getSize(); // Obtenir la taille de la fenêtre principale
                // Appel de la méthode pour afficher la page de gestion des produits
                Mise_a_jour_Produit.main(null); // Appel de la méthode main de la classe Mise_a_jour_Produit
            }
        });
        constraints.gridy = 2;
        mainPanel.add(gestionProduitButton, constraints);
        JButton quitButton = new JButton("Quitter");
        quitButton.setPreferredSize(new Dimension(200, 50)); // Ajuster la largeur du bouton Quitter
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Fermer l'application lors du clic sur "Quitter"
            }
        });
        constraints.gridy = 3;
        mainPanel.add(quitButton, constraints);

        // Ajout du panneau principal au centre de la fenêtre
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);

        // Rendre la fenêtre en plein écran
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}