package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.storage.ResourcesStorage;

/**
 * Controller for a resources page.
 */
public class ResourcesWindow extends UiPart<Stage> {

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "ExtensionWindow.fxml";
    private ObservableList<String> lst = FXCollections.observableArrayList();
    private ResourcesStorage storage;

    @FXML
    private ListView<String> linkList;

    @FXML
    private Button userButton;

    @FXML
    private TextField userInput;

    @FXML
    private ContextMenu userOptions;

    @FXML
    private MenuItem copyItem;

    @FXML
    private MenuItem deleteItem;

    /**
     * Creates a new ResourcesWindow.
     *
     * @param root Stage to use as the root of the ResourcesWindow.
     */
    public ResourcesWindow(Stage root) throws IOException {
        super(FXML, root);
        storage = new ResourcesStorage("data/resources.txt");
    }

    /**
     * Creates a new ResourcesWindow.
     */
    public ResourcesWindow() throws IOException {
        this(new Stage());
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() throws IOException {
        logger.fine("Showing resources page.");
        loadData();
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    private void loadData() throws IOException {
        lst.clear();
        linkList.getItems().clear();
        lst.addAll(storage.load());
        linkList.getItems().addAll(lst);
    }

    @FXML
    private void handleUserInput() throws IOException {
        String input = userInput.getText();
        lst.add(input);
        linkList.getItems().addAll(input);
        storage.save(lst);
        userInput.clear();
    }

    @FXML
    private void chooseMenuOptions() {
        copyItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(linkList.getSelectionModel().getSelectedItem());
                clipboard.setContent(content);
            }
        });
        deleteItem.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int index = linkList.getSelectionModel().getSelectedIndex();
                linkList.getItems().remove(index);
                lst.remove(index, index + 1);
                try {
                    storage.save(lst);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        userOptions.getItems().add(copyItem);
        userOptions.getItems().add(deleteItem);
    }
}