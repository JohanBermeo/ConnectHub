package model.publications;
import java.io.File;

import model.User;
import model.content.ImageContent;
import model.content.TextContent;
import model.content.VideoContent;

/**
 * Factory para crear publicaciones
 */
public class PublicationFactory {
    
    public Publication createPublication(PublicationType type, User user, String content, File... files) throws Exception {
        Publication publication = new Publication(user);
    
        switch (type) {
            case VIDEO:
                if (files.length == 0) {
                    throw new Exception("Video requerido para publicación de video");
                }
                publication.addContent(new VideoContent(files[0]));
                break;
                
            case IMAGE:
                if (files.length == 0) {
                    throw new Exception("Imagen requerida");
                }
                publication.addContent(new ImageContent(files[0]));
                break;
                
            case TEXT:
                if (content == null || content.trim().isEmpty()) {
                    throw new Exception("Contenido de texto requerido");
                }
                publication.addContent(new TextContent(content));
                break;
                
            case VIDEO_WITH_TEXT:
                if (files.length == 0 || content == null) {
                    throw new Exception("Video y texto requeridos");
                }
                publication.addContent(new VideoContent(files[0]));
                publication.addContent(new TextContent(content));
                break;
                
            case IMAGE_WITH_TEXT:
                if (files.length == 0 || content == null) {
                    throw new Exception("Imagen y texto requeridos");
                }
                publication.addContent(new ImageContent(files[0]));
                publication.addContent(new TextContent(content));
                break;
                
            default:
                throw new Exception("Tipo de publicación no soportado: " + type);
        }
        
        return publication;
    }
}