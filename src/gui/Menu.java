package gui;

import javax.swing.*;

import model.User;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

public class Menu extends JFrame {
    private User usuario;
    private Consumer<Boolean> onChangeUserCallback;
    private RedSocialGUI instagramGUI;
    private RedSocialGUI tikTokGUI;
    private RedSocialGUI facebookGUI;
    
    public Menu(User usuario, Consumer<Boolean> callback) {
        this.usuario = usuario;
        this.onChangeUserCallback = callback;
        this.instagramGUI = new RedSocialGUI(usuario, "Instagram", () -> {
            this.setVisible(true);
        });
        this.tikTokGUI = new RedSocialGUI(usuario, "TikTok", () -> {
            this.setVisible(true);
        });
        this.facebookGUI = new RedSocialGUI(usuario, "Facebook", () -> {
            this.setVisible(true);
        });
        initializeComponents();
    }
    
    private void initializeComponents() {
        // Configuración de la ventana principal
        setTitle("Menú de Redes Sociales - " + usuario.getUsername());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        // Panel superior con información del usuario
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Panel central con las redes sociales
        JPanel socialPanel = createSocialPanel();
        add(socialPanel, BorderLayout.CENTER);
        
        // Panel inferior con botón de salir
        JPanel footerPanel = createFooterPanel();
        add(footerPanel, BorderLayout.SOUTH);

        JPanel footerPanel2 = createFooterPanel();
        add(footerPanel2, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(60, 90, 120));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        
        JLabel welcomeLabel = new JLabel("Bienvenido, " + usuario.getUsername());
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
        welcomeLabel.setForeground(Color.WHITE);
        
        panel.add(welcomeLabel);
        return panel;
    }
    
    private JPanel createSocialPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 30, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        panel.setBackground(new Color(245, 245, 245));
        
        // Panel de Instagram
        JPanel instagramPanel = createSocialNetworkPanel(
            "Instagram", 
            new Color(225, 48, 108), 
            "Comparte fotos y momentos especiales",
            e -> showRed("Instagram")
        );
        
        // Panel de TikTok
        JPanel tikTokPanel = createSocialNetworkPanel(
            "TikTok", 
            new Color(0, 0, 0), 
            "Crea videos cortos y divertidos",
            e -> showRed("TikTok")
        );
        
        // Panel de Facebook
        JPanel facebookPanel = createSocialNetworkPanel(
            "Facebook", 
            new Color(24, 119, 242), 
            "Conecta con amigos y familia",
            e -> showRed("Facebook")
        );
        
        panel.add(instagramPanel);
        panel.add(tikTokPanel);
        panel.add(facebookPanel);
        
        return panel;
    }
    
    private JPanel createSocialNetworkPanel(String nombre, Color color, String descripcion, ActionListener clickListener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 3),
            BorderFactory.createEmptyBorder(25, 20, 25, 20)
        ));
        panel.setBackground(Color.WHITE);
        
        // Agregar efecto de hover
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panel.setBackground(new Color(250, 250, 250));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panel.setBackground(Color.WHITE);
            }
        });
        
        // Ícono simulado con texto
        JLabel iconLabel = new JLabel("📱");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Título de la red social
        JLabel titleLabel = new JLabel(nombre);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(color);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Descripción
        JLabel descLabel = new JLabel("<html><center>" + descripcion + "</center></html>");
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.GRAY);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Botón de acceso
        JButton accessButton = new JButton("Acceder");
        accessButton.setBackground(color);
        accessButton.setForeground(Color.WHITE);
        accessButton.setFont(new Font("Arial", Font.BOLD, 16));
        accessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        accessButton.setMaximumSize(new Dimension(140, 40));
        accessButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        accessButton.setFocusPainted(false);
        accessButton.addActionListener(clickListener);
        
        // Efecto hover en el botón
        accessButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                accessButton.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                accessButton.setBackground(color);
            }
        });
        
        // Agregar componentes al panel
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(iconLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(descLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));
        panel.add(accessButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        
        return panel;
    }
    
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setBackground(new Color(60, 90, 120));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton exitButton = new JButton("Salir");
        exitButton.setBackground(new Color(200, 50, 50));
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 14));
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> System.exit(0));

        JButton changeUserButton = new JButton("Cambiar Usuario");
        changeUserButton.setBackground(new Color(50, 150, 50));
        changeUserButton.setForeground(Color.WHITE);
        changeUserButton.setFont(new Font("Arial", Font.BOLD, 14)); 
        changeUserButton.setFocusPainted(false);
        changeUserButton.addActionListener(e -> {
            // Llamar al callback para cambiar de usuario
            if (onChangeUserCallback != null) {
                onChangeUserCallback.accept(true);
            }
            // Cerrar el menú actual
            dispose();
        });
        
        panel.add(Box.createHorizontalGlue()); // Espacio flexible para alinear a la derecha
        panel.add(changeUserButton);
        panel.add(exitButton);
        return panel;
    }
    
    /**
     * Método que se ejecuta al hacer clic en cualquier red social
     * @param redSocial Nombre de la red social seleccionada
     */
    private void showRed(String redSocial) {
            // Aquí puedes agregar la lógica específica para cada red social
            switch (redSocial) {
                case "Instagram":
                    instagramGUI.setVisible(true);
                    break;
                case "TikTok":
                    tikTokGUI.setVisible(true);
                    break;
                case "Facebook":
                    facebookGUI.setVisible(true);
                    break;
            }
    }
}