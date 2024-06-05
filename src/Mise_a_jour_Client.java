import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Mise_a_jour_Client {

    private static Integer IDProduitCounter = 1;

    private Integer IDClient;
    private String NomClient;
    private String AdresseClient;
    private String EmailClient;
    private String TelClient;
    private static JPanel panel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());

    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Gestion de Stock");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Ajout d'un titre grand
        JLabel titleLabel = new JLabel("Mise à jour Client");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Ajustez la police et la taille selon vos besoins
        titleLabel.setHorizontalAlignment(JLabel.CENTER);

        frame.getContentPane().add(BorderLayout.NORTH, titleLabel); // Ajout du titre au nord
        panel = new JPanel(new GridBagLayout()); // Initialize panel

        frame.getContentPane().add(BorderLayout.CENTER, panel);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        JButton ajouterButton = new JButton("Ajouter Client");
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(ajouterButton, constraints);

        JButton chercherButton = new JButton("Chercher Client");
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(chercherButton, constraints);

        JButton modifierButton = new JButton("Modifier Client");
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(modifierButton, constraints);

        JButton supprimerButton = new JButton("Supprimer Client");
        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(supprimerButton, constraints);

        frame.getContentPane().add(BorderLayout.CENTER, panel);

        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddClientDialog(frame);
            }
        });

        chercherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "KHADIJA";
                String clientName = JOptionPane.showInputDialog(frame, "Nom du Client à Chercher:");

                // Appeler une fonction pour rechercher le produit par son nom ici
                Client clientcherche = new Client();
                clientcherche.setNomClient(clientName);
                boolean success = clientcherche.chercherClient(url, utilisateur, motDePasse);

                // Exemple: searchProduct(productName);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Le client existe.");
                } else {
                    JOptionPane.showMessageDialog(frame, "Le client n'existe pas.");
                }
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientName = JOptionPane.showInputDialog(frame, "Nom du Client  à Modifier:");
                showModifyClientDialog(frame, clientName);
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String clientName = JOptionPane.showInputDialog(frame, "Nom du Client à Supprimer:");
                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "KHADIJA";

                // Appeler une fonction pour supprimer le produit par son nom ici
                Client clientsuprime = new Client();
                clientsuprime.setNomClient(clientName);
                //suprimer dans la table
                boolean success = clientsuprime.supprimerClient(url, utilisateur, motDePasse);
                // Exemple: searchProduct(productName);
                if (success) {
                    JOptionPane.showMessageDialog(frame, "Le client est supprimé avec succes .");
                } else {
                    JOptionPane.showMessageDialog(frame, "La supression a echoué ou le client n'existe pas .");
                }


            }
        });

        frame.setSize(300, 150);
        frame.setVisible(true);
    }

    private static void showAddClientDialog(JFrame parent) {
        JDialog dialog = new JDialog(parent, "Ajouter Client", true);
        JPanel panel = new JPanel(new GridLayout(6, 2));

        JTextField idClientField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField adressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField numeroField = new JTextField();

        panel.add(new JLabel("ID Client:"));
        panel.add(idClientField);
        panel.add(new JLabel("Nom du Client:"));
        panel.add(nomField);
        panel.add(new JLabel("Adresse du client"));
        panel.add(adressField);
        panel.add(new JLabel("Email du client"));
        panel.add(emailField);
        panel.add(new JLabel("Numero de telephone du client"));
        panel.add(numeroField);

        JButton addButton = new JButton("Ajouter");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les valeurs saisies
                Integer IDClient= Integer.parseInt(idClientField.getText());
                String nom = nomField.getText();
                String adress = adressField.getText();
                String email = emailField.getText();
                String numero =numeroField.getText();

                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "KHADIJA";

                Client clientcree = new Client(IDClient, nom, adress, email,numero);
                //ajouter dans la base
                boolean success = clientcree.ajouterClient(url, utilisateur, motDePasse);
                // Use parent instead of frame
                if (success) {
                    JOptionPane.showMessageDialog(parent, "Le client est ajouté avec succes .");
                } else {
                    JOptionPane.showMessageDialog(parent, "Le client n'est pas ajouté .");
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

    private static void showModifyClientDialog(JFrame parent, String clientName) {
        JDialog dialog = new JDialog(parent, "Modifier Client", true);
        JPanel panel = new JPanel(new GridLayout(5, 2));

        JTextField idClientField = new JTextField();
        JTextField nomField = new JTextField();
        JTextField adressField = new JTextField();
        JTextField emailField = new JTextField();
        JTextField numeroField = new JTextField();

        panel.add(new JLabel("Nom du Client:"));
        panel.add(nomField);
        panel.add(new JLabel("Adresse du client"));
        panel.add(adressField);
        panel.add(new JLabel("Email du client"));
        panel.add(emailField);
        panel.add(new JLabel("Numero de telephone du client"));
        panel.add(numeroField);

        JButton modifyButton = new JButton("Modifier");
        modifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Récupérer les nouvelles valeurs saisies
                String newNom = nomField.getText();
                String newadress = adressField.getText();
                String newemail = emailField.getText();
                String newnumero =numeroField.getText();

                String url = "jdbc:postgresql://localhost:5432/gestionstock";
                String utilisateur = "postgres";
                String motDePasse = "KHADIJA";

                Client clientmodifie = new Client();
                clientmodifie.setNomClient(newNom);
                clientmodifie.setEmailClient(newemail );
                clientmodifie.setAdresseClient(newadress);
                clientmodifie.setTelClient(newnumero);
                //modifier dans la base
                boolean success = clientmodifie.modifierClient(url,utilisateur,motDePasse,clientName);
                // Use parent instead of frame
                if (success) {
                    JOptionPane.showMessageDialog(parent, "Le client  est modifié avec succes .");
                } else {
                    JOptionPane.showMessageDialog(parent, "Le client n'as pas eté modifié  .");
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