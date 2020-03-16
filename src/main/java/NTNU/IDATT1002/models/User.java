package NTNU.IDATT1002.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String username;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ImageAlbum> imageAlbums = new ArrayList<>();

    public void addImageAlbum(ImageAlbum imageAlbum) {
        imageAlbums.add(imageAlbum);
        imageAlbum.setAuthor(this);
    }

    public void removeImageAlbum(ImageAlbum imageAlbum) {
        imageAlbums.remove(imageAlbum);
        imageAlbum.setAuthor(null);
    }

}
