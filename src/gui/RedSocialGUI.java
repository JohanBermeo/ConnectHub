package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import model.User;
import model.RedSocial;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * Interfaz gráfica principal de la red social
 * Maneja el feed de publicaciones y la navegación principal
 */
public class RedSocialGUI extends JFrame {
    private User currentUser;
    private RedSocial redSocial;
    private JPanel feedArea;
    private JButton createPostButton;
    private JButton refreshButton;
    private JButton profileButton;
    private JLabel userLabel;
    private String networkName;
    
    public RedSocialGUI(User currentUser, String networkName, Runnable onCloseCallback) {
        this.currentUser = currentUser;
        this.networkName = networkName;
        this.redSocial = new RedSocial(networkName, currentUser);
        initializeComponents();
        setupLayout();
        setupEventListeners();
        loadFeed();

        // Cambia el comportamiento de cierre para ejecutar el callback y solo cerrar esta ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
            onCloseCallback.run();
            }
        });
    }
    
    private void initializeComponents() {
        setTitle(networkName + " " + currentUser.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        // Componentes principales
        userLabel = new JLabel("Bienvenido, " + currentUser.getUsername());
        userLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        feedArea = new JPanel();
        
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
    
    public void loadFeed() {

        feedArea.removeAll();
        feedArea.setLayout(new BoxLayout(feedArea, BoxLayout.Y_AXIS));

        for (var publication : redSocial.getPublications()) {
            JPanel publicationPanel = PublicationGUI.renderPublication(publication);
            publicationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            feedArea.add(publicationPanel);
            feedArea.add(Box.createVerticalStrut(10));
        }
    }
    
    private void openPublicationFactory() {
        PublicationFactoryGUI factoryGUI = new PublicationFactoryGUI(this, redSocial);
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
    
}