package NTNU.IDATT1002.database;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.ImageAlbumService;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.UserService;

import java.io.File;
import java.util.Arrays;
import java.util.Date;


/**
 * Loads test data into the database.
 */
public class LoadDatabase {

    private static UserService userService;
    private static ImageAlbumService imageAlbumService;
    private static ImageService imageService;

    static {
        userService = new UserService();
        imageAlbumService = new ImageAlbumService();
        imageService = new ImageService();
    }

    public static void load() {

        User user = userService.createUser("LarseKaren@mail.com", "LarseKaren", "Larse",
                "Karen", "+47", "00000000", new Date(), "123")
                .get();
        Image image = imageService.createImage(user, new File("t14-test-images/1.jpg")).get();
        imageService.addTagToImage(image, new Tag("#winter"));
        imageService.addTagToImage(image, new Tag("#nature"));
        imageService.addTagToImage(image, new Tag("#mountains"));

        imageAlbumService.createImageAlbum("First",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#winter"), new Tag("#nature"), new Tag("#mountains")),
                Arrays.asList(image));



        image = imageService.createImage(user, new File("t14-test-images/7.jpg")).get();
        imageService.addTagToImage(image, new Tag("#winter"));
        imageService.addTagToImage(image, new Tag("#nature"));
        imageService.addTagToImage(image, new Tag("#ice"));

        imageAlbumService.createImageAlbum("Seventh",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#winter"), new Tag("#nature"), new Tag("#ice")),
                Arrays.asList(image));



        user = userService.createUser("SteiraGuten@mail.com", "SteiraGuten", "Steira",
                "Guten", "+47", "00000000", new Date(), "123")
                .get();
        image = imageService.createImage(user, new File("t14-test-images/2.jpg")).get();
        imageService.addTagToImage(image, new Tag("#mountains"));
        imageService.addTagToImage(image, new Tag("#water"));
        imageService.addTagToImage(image, new Tag("#nature"));

        imageAlbumService.createImageAlbum("Second",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#mountains"), new Tag("#water"), new Tag("#nature")),
                Arrays.asList(image));



        image = imageService.createImage(user, new File("t14-test-images/10.jpg")).get();
        imageService.addTagToImage(image, new Tag("#fjords"));
        imageService.addTagToImage(image, new Tag("#nature"));
        imageService.addTagToImage(image, new Tag("#mountains"));

        imageAlbumService.createImageAlbum("Tenth",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#fjords"), new Tag("#nature"), new Tag("#mountains")),
                Arrays.asList(image));



        user = userService.createUser("MogenBogen@mail.com", "MogenBogen", "Mogen",
                "Bogen", "+47", "00000000", new Date(), "123")
                .get();
        image = imageService.createImage(user, new File("t14-test-images/3.jpg")).get();
        imageService.addTagToImage(image, new Tag("#mountains"));
        imageService.addTagToImage(image, new Tag("#water"));
        imageService.addTagToImage(image, new Tag("#nature"));

        imageAlbumService.createImageAlbum("Third",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#mountains"), new Tag("#water"), new Tag("#nature")),
                Arrays.asList(image));




        image = imageService.createImage(user, new File("t14-test-images/9.jpg")).get();
        imageService.addTagToImage(image, new Tag("#art"));
        imageService.addTagToImage(image, new Tag("#nature"));
        imageService.addTagToImage(image, new Tag("#river"));

        imageAlbumService.createImageAlbum("Ninth",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#art"), new Tag("#nature"), new Tag("#river")),
                Arrays.asList(image));




        user = userService.createUser("Nicolaysen@mail.com", "Nicolaysen", "Nicolai",
                "Sen", "+47", "00000000", new Date(), "123")
                .get();
        image = imageService.createImage(user, new File("t14-test-images/4.jpg")).get();
        imageService.addTagToImage(image, new Tag("#mountains"));
        imageService.addTagToImage(image, new Tag("#grass"));
        imageService.addTagToImage(image, new Tag("#nature"));

        imageAlbumService.createImageAlbum("Fourth",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#mountains"), new Tag("#grass"), new Tag("#nature")),
                Arrays.asList(image));



        image = imageService.createImage(user, new File("t14-test-images/5.jpg")).get();
        imageService.addTagToImage(image, new Tag("#mountains"));
        imageService.addTagToImage(image, new Tag("#water"));
        imageService.addTagToImage(image, new Tag("#dock"));

        imageAlbumService.createImageAlbum("Fifth",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#mountains"), new Tag("#water"), new Tag("#dock")),
                Arrays.asList(image));





        user = userService.createUser("MaseMads@mail.com", "MaseMads", "Mase",
                "Mads", "+47", "00000000", new Date(), "123")
                .get();
        image = imageService.createImage(user, new File("t14-test-images/6.jpg")).get();
        imageService.addTagToImage(image, new Tag("#mountains"));
        imageService.addTagToImage(image, new Tag("#waterfall"));
        imageService.addTagToImage(image, new Tag("#nature"));

        imageAlbumService.createImageAlbum("Sixth",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#mountains"), new Tag("#waterfall"), new Tag("#nature")),
                Arrays.asList(image));




        user = userService.createUser("JensenMannen@mail.com", "JensenMannen", "Jense",
                "Mannen", "+47", "00000000", new Date(), "123")
                .get();
        image = imageService.createImage(user, new File("t14-test-images/8.jpg")).get();
        imageService.addTagToImage(image, new Tag("#winter"));
        imageService.addTagToImage(image, new Tag("#nature"));
        imageService.addTagToImage(image, new Tag("#mountains"));

        imageAlbumService.createImageAlbum("Eighth",
                "Lorem Ipsum",
                user,
                Arrays.asList(new Tag("#winter"), new Tag("#nature"), new Tag("#mountains")),
                Arrays.asList(image));


    }
}
