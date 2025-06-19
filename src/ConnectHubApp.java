import javax.swing.*;

import controller.AuthenticationController;
import view.AuthenticationView;

/**
 * Clase principal para ejecutar la aplicación ConnectHub
 * Punto de entrada del sistema con interfaz gráfica
 */
public class ConnectHubApp {
    
    /**
     * Método principal que inicia la aplicación
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        // Ejecutar en el Event Dispatch Thread para thread safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Establecer el Look and Feel del sistema operativo
                    UIManager.setLookAndFeel(UIManager.getLookAndFeel());
                } catch (Exception e) {
                    System.err.println("No se pudo establecer el Look and Feel: " + e.getMessage());
                    // Continuar con el Look and Feel por defecto
                }
                
                // Crear el servicio de autenticación
                AuthenticationController authService = new AuthenticationController();
                
                // Crear y mostrar la interfaz de autenticación
                AuthenticationView authGUI = new AuthenticationView(authService);
                authGUI.setVisible(true);
            }
        });
    }
    
    /**
     * Método auxiliar para mostrar información sobre la aplicación
     */
    public static void showAbout() {
        String aboutText = "ConnectHub v1.0\n" +
                          "Una red social moderna\n" +
                          "Desarrollado con Java Swing\n\n" +
                          "Características:\n" +
                          "• Autenticación de usuarios\n" +
                          "• Publicaciones multimedia\n" +
                          "• Feed en tiempo real\n" +
                          "• Interfaz intuitiva";
        
        JOptionPane.showMessageDialog(null, aboutText, "Acerca de ConnectHub", 
                                    JOptionPane.INFORMATION_MESSAGE);
    }
}