package mp3Player;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MediaPlayerGUI extends Application {
	private String defaultSong = "Data/The Hampsterdance Song.mp3";
	private File songfile = new File(defaultSong);
	private Mp3player mp = new Mp3player();
	private File file = new File("Data/Dancinghamster.gif");
	private File file2 = new File("Data/hamster.gif");
	private Image image = new Image(file2.toURI().toString());
	private Image image2 = new Image(file.toURI().toString());
	private ListView<String> list = new ListView<String>();
	private ArrayList<String> listOfSongs = new ArrayList<String>();
	private File save = new File("Data/mediaLib.txt");
	private String textOnButton = "Play";
	private ImageView iv = new ImageView();

	private Button playButton = new Button("Play");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		listOfSongs.addAll(mp.readLib());
		Group root = new Group();
		Scene scene = new Scene(root);
		HBox hbox = new HBox();
		playButton = new Button("Play");
		iv.setImage(image);
		
		
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				textOnButton = (playButton.getText().equals("Play")) ? "Pause" : "Play";
				playButton.setText(textOnButton);
				mp.playing(textOnButton, songfile);
				if (textOnButton.equals("Play"))
					iv.setImage(image);
				else
					iv.setImage(image2);
			}
		});
		Button stopButton = new Button("Stop");
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				mp.stop();
				String text = "Play";
				playButton.setText(text);
				iv.setImage(image);
			}
		});

		ListView<String> list = playList();

		MenuBar menuBar = menu(primaryStage);
		hbox.getChildren().addAll(menuBar, iv, playButton, stopButton);
		hbox.getChildren().add(list);
		root.getChildren().add(hbox);
		
		
		hbox.setStyle("-fx-background-color: #333333;");

		
		
		
		

		scene.setOnDragOver(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				Dragboard db = event.getDragboard();
				if (db.hasFiles()) {
					event.acceptTransferModes(TransferMode.COPY);
				} else {
					event.consume();
				}
			}
		});
		scene.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				dragDropped(event);
			}
		});
		
		primaryStage.setTitle("mp3 player");
		primaryStage.setWidth(550);
		primaryStage.setHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	/**
	 * Menu for ...
	 * 
	 * @return
	 */
	private MenuBar menu(Stage stage) {
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");
		Menu menuView = new Menu("View");
		MenuItem exitItem = new MenuItem("Exit", null);
		exitItem.setMnemonicParsing(true);
		exitItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
		exitItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save playlist");
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
				fileChooser.getExtensionFilters().add(extFilter);

				Platform.exit();
			}
		});
		MenuItem openItem = new MenuItem("Open", null);
		openItem.setMnemonicParsing(true);
		openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		openItem.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Resource File");
				songfile = fileChooser.showOpenDialog(stage);
				// String name = songfile.getName();
				saveToList(songfile.getAbsolutePath());
				mp.SaveFile(listOfSongs, save);
				list = playList();
			}
		});

		menuFile.getItems().add(openItem);
		menuFile.getItems().add(exitItem);
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		return menuBar;
	}

	private ListView<String> playList() {
		ObservableList<String> items = FXCollections.observableArrayList(getOnlyName());
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
					// System.out.println("häer" + new_val);
					songfile = mp.fetch(new_val);
					mp.stop();
					mp = new Mp3player();
					playButton.setText("Pause");
					mp.playing("Pause", songfile);
					iv.setImage(image2);
				});

		list.setItems(items);

		return list;
	}

	private ArrayList<String> getOnlyName() {
		ArrayList<String> tempList = new ArrayList<String>();
		for (String s : listOfSongs) {
			File userFile = new File(s);
			String filename = userFile.getName();
			tempList.add(filename);
		}
		return tempList;

	}

	private void saveToList(String s) {
		listOfSongs.add(s);
	}

	private void dragDropped(final DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			success = true;
			String filePath = null;
			for (File file : db.getFiles()) {
				filePath = file.getAbsolutePath();
				System.out.println(filePath);
				songfile = file;
				saveToList(songfile.getAbsolutePath());
				mp.SaveFile(listOfSongs, save);
				list = playList();
			}
		}
		event.setDropCompleted(success);

		event.consume();

	}

}
