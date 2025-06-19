import javax.swing.*;

import auth.AuthenticationService;
import gui.AuthenticationGUI;

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
                
        // Crear el servicio de autenticación
        AuthenticationService authService = null;
        try {
            authService = new AuthenticationService();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar el servicio de autenticación: " + e.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Crear y mostrar la interfaz de autenticación
        AuthenticationGUI authGUI = new AuthenticationGUI(authService);
        authGUI.setVisible(true);
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