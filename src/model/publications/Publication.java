package model.publications;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.User;
import model.content.Content;
import model.interfaces.Identifiable;

/**
 * Clase que representa una publicación
 */
public class Publication implements Identifiable {
    private static int nextId = 1;
    
    private final int id;
    private final User author;
    private final Date dateCreated;
    private Date dateModified;
    private List<Content> contents;
    private int likes;
    
    public Publication(User author) {
        this.id = nextId++;
        this.author = author;
        this.dateCreated = new Date();
        this.dateModified = new Date();
        this.contents = new ArrayList<>();
        this.likes = 0;
    }
    
    public void addContent(Content content) {
        if (content != null) {
            contents.add(content);
            this.dateModified = new Date();
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
    
    public User getAuthor() { 
        return author; 
    }
    
    public Date getDateCreated() { 
        return new Date(dateCreated.getTime()); 
    }
    
    public Date getDateModified() { 
        return new Date(dateModified.getTime()); 
    }
    
    public List<Content> getContents() { 
        return new ArrayList<>(contents); 
    }
    
    public int getLikes() {
        return likes;
    }
}