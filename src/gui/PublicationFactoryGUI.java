package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.RedSocial;
import model.publications.PublicationType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Interfaz gráfica para la creación de publicaciones
 * Utiliza el patrón Factory para crear diferentes tipos de publicaciones
 */
public class PublicationFactoryGUI extends JDialog {
    private RedSocialGUI parentGUI;
    private RedSocial redSocial;
    
    private JComboBox<PublicationType> typeComboBox;
    private JTextArea contentArea;
    private JButton selectFileButton;
    private JButton publishButton;
    private JButton cancelButton;
    private JLabel fileLabel;
    private JLabel statusLabel;
    
    private File selectedFile;
    
    public PublicationFactoryGUI(RedSocialGUI parent, RedSocial redSocial) {
        super(parent, "Crear Nueva Publicación", true);
        this.parentGUI = parent;
        this.redSocial = redSocial;
        initializeComponents();
        setupLayout();
        setupEventListeners();
    }
    
    private void initializeComponents() {
        setSize(500, 400);
        setLocationRelativeTo(parentGUI);
        setResizable(false);
        
        // Componentes
        typeComboBox = new JComboBox<>(PublicationType.values());
        
        contentArea = new JTextArea(5, 30);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createLoweredBevelBorder());
        
        selectFileButton = new JButton("Seleccionar Archivo");
        publishButton = new JButton("Publicar");
        publishButton.setBackground(new Color(59, 89, 182));
        publishButton.setForeground(Color.WHITE);
        
        cancelButton = new JButton("Cancelar");
        
        fileLabel = new JLabel("Ningún archivo seleccionado");
        fileLabel.setForeground(Color.GRAY);
        
        statusLabel = new JLabel(" ");
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        
        // Título
        JLabel titleLabel = new JLabel("Crear Nueva Publicación", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);
        
        // Tipo de publicación
        gbc.gridwidth = 1; gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 1; gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(typeComboBox, gbc);
        
        // Contenido de texto
        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Contenido:"), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(new JScrollPane(contentArea), gbc);
        
        // Selección de archivo
        gbc.gridx = 0; gbc.gridy = 3; gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(new JLabel("Archivo:"), gbc);
        
        JPanel filePanel = new JPanel(new BorderLayout());
        filePanel.add(selectFileButton, BorderLayout.WEST);
        filePanel.add(fileLabel, BorderLayout.CENTER);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST; gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(filePanel, gbc);
        
        // Estado
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        gbc.insets = new Insets(15, 0, 0, 0);
        mainPanel.add(statusLabel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(publishButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupEventListeners() {
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateFieldsVisibility();
            }
        });
        
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });
        
        publishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createPublication();
            }
        });
        
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        
        // Inicializar visibilidad
        updateFieldsVisibility();
    }
    
    private void updateFieldsVisibility() {
        PublicationType selectedType = (PublicationType) typeComboBox.getSelectedItem();
        
        switch (selectedType) {
            case TEXT:
                contentArea.setEnabled(true);
                selectFileButton.setEnabled(false);
                break;
            case VIDEO:
            case IMAGE:
                contentArea.setEnabled(false);
                selectFileButton.setEnabled(true);
                break;
            case VIDEO_WITH_TEXT:
            case IMAGE_WITH_TEXT:
                contentArea.setEnabled(true);
                selectFileButton.setEnabled(true);
                break;
        }
    }
    
    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        PublicationType selectedType = (PublicationType) typeComboBox.getSelectedItem();
        
        // Configurar filtros según el tipo
        if (selectedType == PublicationType.IMAGE || selectedType == PublicationType.IMAGE_WITH_TEXT) {
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".jpg") || name.endsWith(".jpeg") || 
                           name.endsWith(".png") || name.endsWith(".gif");
                }
                @Override
                public String getDescription() {
                    return "Imágenes (*.jpg, *.jpeg, *.png, *.gif)";
                }
            });
        } else if (selectedType == PublicationType.VIDEO || selectedType == PublicationType.VIDEO_WITH_TEXT) {
            fileChooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
                @Override
                public boolean accept(File f) {
                    if (f.isDirectory()) return true;
                    String name = f.getName().toLowerCase();
                    return name.endsWith(".mp4") || name.endsWith(".avi") || 
                           name.endsWith(".mov") || name.endsWith(".wmv");
                }
                @Override
                public String getDescription() {
                    return "Videos (*.mp4, *.avi, *.mov, *.wmv)";
                }
            });
        }
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileLabel.setText(selectedFile.getName());
            fileLabel.setForeground(Color.BLACK);
        }
    }
    
    private void createPublication() {
        try {
            PublicationType type = (PublicationType) typeComboBox.getSelectedItem();
            String content = contentArea.getText().trim();
            
            if (selectedFile != null) {
                redSocial.publish(type, content, selectedFile);
            } else {
                redSocial.publish(type, content);
            }

            parentGUI.loadFeed();
            
            showStatus("Publicación creada exitosamente", true);
            
            // Cerrar diálogo después de un breve delay
            Timer timer = new Timer(1500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            timer.setRepeats(false);
            timer.start();
            
        } catch (Exception ex) {
            showStatus("Error: " + ex.getMessage(), false);
        }
    }
    
    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        statusLabel.setForeground(isSuccess ? Color.GREEN : Color.RED);
    }
}