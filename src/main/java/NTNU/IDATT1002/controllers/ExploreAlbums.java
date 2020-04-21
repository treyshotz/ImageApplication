package NTNU.IDATT1002.controllers;

import NTNU.IDATT1002.App;
import NTNU.IDATT1002.controllers.components.exploreAlbums.AlbumHBox;
import NTNU.IDATT1002.models.Album;
import NTNU.IDATT1002.models.Image;
import NTNU.IDATT1002.repository.Page;
import NTNU.IDATT1002.service.AlbumService;
import NTNU.IDATT1002.service.PageableService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * Controls the buttons and changeable elements on explore_albums.fxml,
 * a page where you explore albums.
 *
 * @version 1.1 04.04.2020
 */
public class ExploreAlbums extends PaginatedContent<Album> {

    public Button createAlbumButton;

    private AlbumService albumService;

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private static Logger logger = LoggerFactory.getLogger(ExploreAlbums.class);


    /**
     * Tell {@link PaginatedContent} which fields to sort {@link Album}s by.
     * Tell {@link DataExchange} that explore albums page is visited.
     */
    public ExploreAlbums() {
        super("createdAt", "title");
        
        albumService = new AlbumService(App.ex.getEntityManager());
        App.ex.newPage("explore_albums");
    }

    /**
     * Return an {@link AlbumService} to use in {@link PaginatedContent}.
     */
    @Override
    protected PageableService<Album> getService() {
        return albumService;
    }

    /**
     * Uses computeRooAlbumsContainer to return a list og Hboxes with Page as parameter
     * the getContent() gives us the specific content for the page we need
     * @param page
     * @return a list of Hboxes with the Album content for the specific page
     */
    @Override
    public List<HBox> getContentsFrom(Page<Album> page) {

        return computeRootAlbumsContainerChildren(page.getContent());
    }

    /**
     * Create a {@link HBox} of album {@link HBox} children to add to a root container.
     * The limit is defined by the users choice from the list of amounts in PaginatedContent
     * Load each corresponding preview images in a separate background task and add them when ready.
     *
     * @param albums the albums to add
     * @return the list of HBoxes containing album contents
     */
    public List<HBox> computeRootAlbumsContainerChildren(List<Album> albums){
        List<HBox> albumHBoxes = new ArrayList<>();

        for (Album album : albums) {
            AlbumHBox albumHBox = new AlbumHBox(album);

            Task<Optional<Image>> fetchPreviewImageTask = fetchPreviewImageFrom(album);
            executorService.submit(fetchPreviewImageTask);

            fetchPreviewImageTask.setOnSucceeded(event -> {
                Optional<Image> previewImage = fetchPreviewImageTask.getValue();

                previewImage.ifPresent(image -> {
                    albumHBox.replaceAlbumImageViewWith(image);
                    setSwitchToAlbumOnAlbumComponents(albumHBox);
                });
            });

            albumHBoxes.add(albumHBox);
        }

        return albumHBoxes;
    }

    /**
     * Fetch a single image preview from given album in a background task.
     *
     * @param album the album whose images to fetch
     * @return task to return a list of fetched images
     */
    private Task<Optional<Image>> fetchPreviewImageFrom(Album album) {
        return new Task<>() {
            @Override
            protected Optional<Image> call() {
                try {
                    return albumService.findPreviewImage(album);
                } catch (Exception e) {
                    logger.error("[x] Failed to fetch preview image for album {}", album, e);
                }

                return Optional.empty();
            }

            @Override
            protected void cancelled() {
                logger.error("Finding preview image for album task cancelled. Album: {}", album);
            }

            @Override
            protected void failed() {
                logger.error("Finding preview image task for album failed. Album: {}", album);
            }
        };
    }

    /**
     * Add {@link MouseEvent} event handler to appropriate components in given {@link AlbumHBox}.
     *
     * @param albumHBox the composite component.
     */
    private void setSwitchToAlbumOnAlbumComponents(AlbumHBox albumHBox) {
        EventHandler<MouseEvent> mouseEventEventHandler = mouseEvent -> {
            try {
                switchToViewAlbum(mouseEvent);
            } catch (IOException ex) {
                logger.error("[x] Failed to switch to Album View", ex);
            }
        };

        Stream.of(albumHBox.getAlbumTitleHBox(),
                  albumHBox.getAlbumImageView())
                .forEach(node -> albumHBox.addOnMouseClickedEventHandlerToComponent(node, mouseEventEventHandler));
    }

    /**
     * Change scene to Create Album page.
     *
     * @param actionEvent the mouse has done something
     * @throws IOException this page does not exist
     */
    public void switchToCreateAlbum(ActionEvent actionEvent) throws IOException {
        App.setRoot("create_album");
    }

    /**
     * Update content to previous "page".
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToPrevious(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content
    }

    /**
     * Update content to next "page".
     *
     * @param actionEvent
     * @throws IOException
     */
    public void switchToNext(ActionEvent actionEvent) throws IOException {
        //TODO: Make method that updates content
    }

    /**
     * Open specific albums. It takes a clicked element within an album container
     * and finds the fx:id of the main parent, (who's id is the same as the album in the database), and
     * passes the value to Data Exchange so that Image View will know which album was clicked.
     *
     * @param mouseEvent
     * @throws IOException
     */
    public void switchToViewAlbum(MouseEvent mouseEvent) throws IOException {
        boolean albumIdFound = false;
        long albumId = 0;
        Node node = (Node) mouseEvent.getSource();
        while (!albumIdFound){
            if (node.getId() != null){
                albumId = Long.parseLong(node.getId());
                albumIdFound = true;
            }
            node = node.getParent();
        }

        if (albumId != 0) {
            App.ex.setChosenAlbumId(albumId);
            App.setRoot("view_album");
        }
    }

}
