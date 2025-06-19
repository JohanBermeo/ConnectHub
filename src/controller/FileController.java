package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manejador de archivos genérico
 */
public class FileController<T> {
    private String basePath;
    
    public FileController(String basePath) {
        this.basePath = basePath;
    }
    
    public void save(List<T> data) throws Exception {
        try {
            // Implementación de guardado
            // Por ahora es un placeholder
            System.out.println("Guardando datos en: " + basePath);
        } catch (Exception e) {
            throw new Exception("Error al guardar datos", e);
        }
    }
    
    public List<T> load() throws Exception {
        try {
            // Implementación de carga
            // Por ahora retorna lista vacía
            System.out.println("Cargando datos desde: " + basePath);
            return new ArrayList<>();
        } catch (Exception e) {
            throw new Exception("Error al cargar datos", e);
        }
    }
    
    public String getBasePath() {
        return basePath;
    }
}