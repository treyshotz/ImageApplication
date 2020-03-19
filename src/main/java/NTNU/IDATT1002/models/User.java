package NTNU.IDATT1002.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    private String username;

    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ImageAlbum> imageAlbums = new ArrayList<>();

    /**
     * Add given image album.
     *
     * @param imageAlbum the image album to add
     */
    public void addImageAlbum(ImageAlbum imageAlbum) {
        imageAlbums.add(imageAlbum);
        imageAlbum.setUser(this);
    }

    /**
     * Remove given image album.
     *
     * @param imageAlbum the image album to remove
     */
    public void removeImageAlbum(ImageAlbum imageAlbum) {
        imageAlbums.remove(imageAlbum);
        imageAlbum.setUser(null);
    }

}
