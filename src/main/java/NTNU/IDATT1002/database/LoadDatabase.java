package NTNU.IDATT1002.database;

import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.models.Tag;
import NTNU.IDATT1002.models.User;
import NTNU.IDATT1002.service.ImageAlbumService;
import NTNU.IDATT1002.service.ImageService;
import NTNU.IDATT1002.service.UserService;

import java.io.File;
import java.util.ArrayList;
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

        User user = userService.createUser("LarseKaren@mail.com",
                "LarseKaren",
                "Larse",
                "Karen",
                "+47",
                "00000000",
                new Date(),
                "123")
                .get();
        ArrayList<Tag> tags = new ArrayList<>(Arrays.asList(
                new Tag("#winter"),
                new Tag("#nature"),
                new Tag("#mountains")
        ));
        Image image = imageService.createImage(user,
                new File("t14-test-images/1.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("First",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#winter"),
                        new Tag("#nature"),
                        new Tag("#mountains")),
                Arrays.asList(image));



        tags = new ArrayList<>(Arrays.asList(
                new Tag("#winter"),
                new Tag("#nature"),
                new Tag("#ice")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/7.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Seventh",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#winter"),
                        new Tag("#nature"),
                        new Tag("#ice")),
                Arrays.asList(image));



        tags = new ArrayList<>(Arrays.asList(
                new Tag("#winter"),
                new Tag("#nature"),
                new Tag("#water")
        ));
        user = userService.createUser("SteiraGuten@mail.com",
                "SteiraGuten",
                "Steira",
                "Guten",
                "+47",
                "00000000",
                new Date(),
                "123")
                .get();
        image = imageService.createImage(user,
                new File("t14-test-images/2.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Second",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#mountains"),
                        new Tag("#water"),
                        new Tag("#nature")),
                Arrays.asList(image));


        tags = new ArrayList<>(Arrays.asList(
                new Tag("#fjords"),
                new Tag("#nature"),
                new Tag("#mountains")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/10.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Tenth",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#fjords"),
                        new Tag("#nature"),
                        new Tag("#mountains")),
                Arrays.asList(image));



        user = userService.createUser("MogenBogen@mail.com",
                "MogenBogen",
                "Mogen",
                "Bogen",
                "+47",
                "00000000",
                new Date(),
                "123")
                .get();
        tags = new ArrayList<>(Arrays.asList(
                new Tag("#mountains"),
                new Tag("#nature"),
                new Tag("#water")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/3.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Third",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#mountains"),
                        new Tag("#water"),
                        new Tag("#nature")),
                Arrays.asList(image));



        tags = new ArrayList<>(Arrays.asList(
                new Tag("#art"),
                new Tag("#nature"),
                new Tag("#river")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/9.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Ninth",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#art"),
                        new Tag("#nature"),
                        new Tag("#river")),
                Arrays.asList(image));




        user = userService.createUser("Nicolaysen@mail.com",
                "Nicolaysen",
                "Nicolai",
                "Sen",
                "+47",
                "00000000",
                new Date(),
                "123")
                .get();
        tags = new ArrayList<>(Arrays.asList(
                new Tag("#mountains"),
                new Tag("#nature"),
                new Tag("#grass")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/4.jpg"),
                tags).get();

        imageAlbumService.createImageAlbum("Fourth",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#mountains"),
                        new Tag("#grass"),
                        new Tag("#nature")),
                Arrays.asList(image));



        tags = new ArrayList<>(Arrays.asList(
                new Tag("#mountains"),
                new Tag("#dock"),
                new Tag("#water")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/5.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Fifth",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#mountains"),
                        new Tag("#water"),
                        new Tag("#dock")),
                Arrays.asList(image));




        user = userService.createUser("MaseMads@mail.com",
                "MaseMads",
                "Mase",
                "Mads",
                "+47",
                "00000000",
                new Date(),
                "123")
                .get();
        tags = new ArrayList<>(Arrays.asList(
                new Tag("#mountains"),
                new Tag("#nature"),
                new Tag("#waterfall")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/6.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Sixth",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#mountains"),
                        new Tag("#waterfall"),
                        new Tag("#nature")),
                Arrays.asList(image));




        user = userService.createUser("JensenMannen@mail.com",
                "JensenMannen",
                "Jense",
                "Mannen",
                "+47",
                "00000000",
                new Date(),
                "123")
                .get();
        tags = new ArrayList<>(Arrays.asList(
                new Tag("#winter"),
                new Tag("#nature"),
                new Tag("#mountains")
        ));
        image = imageService.createImage(user,
                new File("t14-test-images/8.jpg"),
                tags).get();
        imageAlbumService.createImageAlbum("Eighth",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ",
                user,
                Arrays.asList(new Tag("#winter"),
                        new Tag("#nature"),
                        new Tag("#mountains")),
                Arrays.asList(image));


    }
}
