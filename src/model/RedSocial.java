package model;

// import java.awt.List;
import java.io.File;
import java.util.List;

import model.publications.Publication;
import model.publications.PublicationFactory;
import model.publications.PublicationType;

/**
 * Clase que representa una red social
 */
public class RedSocial {
    private User user;
    private String networkName;
    private DataManager<Publication> publicationsManager;
    private FileHandler<Publication> dataHandler;
    private PublicationFactory publicationFactory;
    
    public RedSocial(String name, User u) {
        this.networkName = name;
        this.user = u;
        this.publicationsManager = new DataManager<>();
        this.dataHandler = new FileHandler<>(name + "_publications.dat");
        this.publicationFactory = new PublicationFactory();
        
        if (u != null) {
            loadUserPublications();
        }
    }
    
    public String getName() {
        return networkName;
    }
    
    public void setUser(User user) {
        this.user = user;
        loadUserPublications();
    }
    
    public User getUser() {
        return user;
    }
    
    public Publication publish(PublicationType type, String content, File... files) throws Exception {
        if (user == null) {
            throw new Exception("Usuario no autenticado");
        }
        
        Publication publication = publicationFactory.createPublication(type, user, content, files);
        publicationsManager.addData(publication);
        dataHandler.save(publicationsManager.getData());
        return publication;
    }
    
    public List<Publication> getPublications() {
        return publicationsManager.getData();
    }
    
    private void loadUserPublications() {
        try {
            publicationsManager.setData(dataHandler.load());
        } catch (Exception e) {
            System.err.println("Error cargando publicaciones: " + e.getMessage());
        }
    }
}