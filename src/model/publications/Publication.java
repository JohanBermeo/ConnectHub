package model.publications;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import interfaces.Identifiable;
import model.User;
import model.content.Content;

/**
 * Clase que representa una publicación
 */
public class Publication implements Identifiable, Serializable {
    private static int nextId = 1;
    private static final long serialVersionUID = 1L; 
    
    private final int id;
    private final User author;
    private final Date dateCreated;
    private final PublicationType type;
    private List<Content> contents;
    private int likes;
    
    public Publication(User author, PublicationType type) {
        this.id = nextId++;
        this.author = author;
        this.dateCreated = new Date();
        this.type = type;
        this.contents = new ArrayList<>();
        this.likes = 0;
    }
    
    public void addContent(Content content) {
        if (content != null) {
            contents.add(content);
        }
    }
    
    public void addLike() {
        this.likes++;
    }
    
    public void removeLike() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
    
    // Getters
    @Override
    public int getId() { 
        return id; 
    }

    public PublicationType getType() { 
        return type; 
    }
    
    public User getAuthor() { 
        return author; 
    }
    
    public Date getDateCreated() { 
        return new Date(dateCreated.getTime()); 
    }
    
    public List<Content> getContents() { 
        return new ArrayList<>(contents); 
    }
    
    public int getLikes() {
        return likes;
    }
}