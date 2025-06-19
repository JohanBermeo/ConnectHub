package gui;

import javax.swing.*;

import java.awt.*;
import java.io.File;
import model.publications.Publication;
import model.publications.PublicationType;
import model.content.Content;
import model.content.TextContent;
import model.content.ImageContent;
import model.content.VideoContent;

public class PublicationGUI {
   public static JPanel renderPublication(Publication publication) {

      PublicationType type = publication.getType();

      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      panel.setBorder(BorderFactory.createTitledBorder("Publicación de: " + publication.getAuthor().getUsername()));

      // Fecha y likes
      JLabel dateLabel = new JLabel("Fecha: " + publication.getDateCreated());
      JLabel likesLabel = new JLabel("Likes: " + publication.getLikes());
      panel.add(dateLabel);
      panel.add(likesLabel);

      switch (type) {
         case VIDEO:
            panel.add(renderVideoPublication(publication.getContents()));
            break;
         case IMAGE:
            panel.add(renderImagePublication(publication.getContents()));
            break;
         case TEXT:
            panel.add(renderTextPublication(publication.getContents()));
            break;
         case IMAGE_WITH_TEXT:
            panel.add(renderImageWithTextPublication(publication.getContents()));
            break;
         case VIDEO_WITH_TEXT:
            panel.add(renderVideoWithTextPublication(publication.getContents()));
            break;
         default:
            panel.add(new JLabel("[Tipo de publicación no soportado]"));
      }

      return panel;
   }

   // ...existing code...

   private static JPanel renderVideoPublication(java.util.List<Content> contents) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      for (Content content : contents) {
         if (content instanceof VideoContent) {
            File videoFile = ((VideoContent) content).getContent();
            if (videoFile != null && videoFile.exists()) {
               JButton openVideoButton = new JButton("Abrir video");
               openVideoButton.addActionListener(e -> {
                  try {
                     Desktop.getDesktop().open(videoFile);
                  } catch (Exception ex) {
                     JOptionPane.showMessageDialog(panel, "No se pudo abrir el video.", "Error", JOptionPane.ERROR_MESSAGE);
                  }
               });
               panel.add(openVideoButton);
            } else {
               panel.add(new JLabel("[Video no disponible]"));
            }
         }
      }
      return panel;
   }

   private static JPanel renderImagePublication(java.util.List<Content> contents) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      for (Content content : contents) {
         if (content instanceof ImageContent) {
            File imageFile = ((ImageContent) content).getContent();
            if (imageFile != null && imageFile.exists()) {
               ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
               Image img = icon.getImage();
               Image scaledImg = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
               JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
               imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
               panel.add(imageLabel);
            } else {
               panel.add(new JLabel("[Imagen no disponible]"));
            }
         }
      }
      return panel;
   }

   private static JPanel renderTextPublication(java.util.List<Content> contents) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      for (Content content : contents) {
         if (content instanceof TextContent) {
            JTextArea textArea = new JTextArea(((TextContent) content).getContent());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setBackground(panel.getBackground());
            textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(textArea);
         }
      }
      return panel;
   }

   private static JPanel renderImageWithTextPublication(java.util.List<Content> contents) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      // Primero imagen, luego texto
      for (Content content : contents) {
         if (content instanceof ImageContent) {
            File imageFile = ((ImageContent) content).getContent();
            if (imageFile != null && imageFile.exists()) {
               ImageIcon icon = new ImageIcon(imageFile.getAbsolutePath());
               Image img = icon.getImage();
               Image scaledImg = img.getScaledInstance(300, -1, Image.SCALE_SMOOTH);
               JLabel imageLabel = new JLabel(new ImageIcon(scaledImg));
               imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
               panel.add(imageLabel);
            } else {
               panel.add(new JLabel("[Imagen no disponible]"));
            }
         }
      }
      for (Content content : contents) {
         if (content instanceof TextContent) {
            JTextArea textArea = new JTextArea(((TextContent) content).getContent());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setBackground(panel.getBackground());
            textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(textArea);
         }
      }
      return panel;
   }

   private static JPanel renderVideoWithTextPublication(java.util.List<Content> contents) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
      // Primero video, luego texto
      for (Content content : contents) {
         if (content instanceof VideoContent) {
            File videoFile = ((VideoContent) content).getContent();
            if (videoFile != null && videoFile.exists()) {
               JButton openVideoButton = new JButton("Abrir video");
               openVideoButton.addActionListener(e -> {
                  try {
                     Desktop.getDesktop().open(videoFile);
                  } catch (Exception ex) {
                     JOptionPane.showMessageDialog(panel, "No se pudo abrir el video.", "Error", JOptionPane.ERROR_MESSAGE);
                  }
               });
               openVideoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
               panel.add(openVideoButton);
            } else {
               panel.add(new JLabel("[Video no disponible]"));
            }
         }
      }
      for (Content content : contents) {
         if (content instanceof TextContent) {
            JTextArea textArea = new JTextArea(((TextContent) content).getContent());
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            textArea.setBackground(panel.getBackground());
            textArea.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(textArea);
         }
      }
      return panel;
   }

}