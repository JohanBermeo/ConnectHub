package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.RedSocial;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Interfaz gráfica principal de la red social
 * Maneja el feed de publicaciones y la navegación principal
 */
public class RedSocialView extends JFrame {
    private User currentUser;
    private RedSocial redSocial;
    private JTextArea feedArea;
    private JButton createPostButton;
    private JButton refreshButton;
    private JButton profileButton;
    private JLabel userLabel;
    
    public RedSocialView(User currentUser) {
        this.currentUser = currentUser;
        this.redSocial = new RedSocial("ConnectHub", currentUser);
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadFeed();
    }
    
    private void initializeComponents() {
        setTitle("ConnectHub - " + currentUser.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Componentes principales
        userLabel = new JLabel("Bienvenido, " + currentUser.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        feedArea = new JTextArea();
        feedArea.setEditable(false);
        feedArea.setFont(new Font("Arial", Font.PLAIN, 12));
        feedArea.setBackground(Color.WHITE);
        
        createPostButton = new JButton("Crear Publicación");
        createPostButton.setBackground(new Color(59, 89, 182));
        createPostButton.setForeground(Color.WHITE);
        
        refreshButton = new JButton("Actualizar");
        profileButton = new JButton("Mi Perfil");
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Panel superior
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        topPanel.setBackground(new Color(245, 245, 245));
        
        topPanel.add(userLabel, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(refreshButton);
        buttonPanel.add(profileButton);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.NORTH);
        
        // Panel central - Feed
        JScrollPane feedScrollPane = new JScrollPane(feedArea);
        feedScrollPane.setBorder(new TitledBorder("Feed de Publicaciones"));
        add(feedScrollPane, BorderLayout.CENTER);
        
        // Panel inferior - Acciones
        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        bottomPanel.add(createPostButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        createPostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPublicationFactory();
            }
        });
        
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadFeed();
            }
        });
        
        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProfile();
            }
        });
    }
    
    private void loadFeed() {
        // Simular carga de publicaciones
        StringBuilder feed = new StringBuilder();
        feed.append("=== FEED DE PUBLICACIONES ===\n\n");
        feed.append("¡Bienvenido a ConnectHub!\n");
        feed.append("Aquí verás las publicaciones de tu red social.\n\n");
        feed.append("Para empezar, haz clic en 'Crear Publicación'.\n\n");
        feed.append("=== Publicaciones Recientes ===\n");
        // Aquí se cargarían las publicaciones reales desde la base de datos
        
        feedArea.setText(feed.toString());
    }
    
    private void openPublicationFactory() {
        PublicationFactoryView factoryGUI = new PublicationFactoryView(this, redSocial);
        factoryGUI.setVisible(true);
    }
    
    private void showProfile() {
        String profileInfo = String.format(
            "=== PERFIL DE USUARIO ===\n\n" +
            "Usuario: %s\n" +
            "Fecha de Registro: %s\n" +
            "Seguidores: %d\n" +
            "Siguiendo: %d",
            currentUser.getUsername(),
            new Date().toString(),
            currentUser.getFollowers().size(),
            currentUser.getFollowing().size()
        );
        
        JOptionPane.showMessageDialog(this, profileInfo, "Mi Perfil", JOptionPane.INFORMATION_MESSAGE);
    }
    
    public void updateFeed(String newPublication) {
        String currentFeed = feedArea.getText();
        String separator = "=".repeat(50);
        feedArea.setText(newPublication + "\n" + separator + "\n" + currentFeed);
    }
}