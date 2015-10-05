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
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
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
	private Stage stage = null;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		listOfSongs.addAll(mp.readLib());
		//Group root = new Group();
		
		HBox hbox = new HBox();
		playButton = new Button("Play");
		playButton.setId("play"); 
		iv.setImage(image);
		BorderPane root = new BorderPane();
		Scene scene = new Scene(root);
		
		
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
		stopButton.setId("stop");
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
		list.setId("list");
		
		

		MenuBar menuBar = menu(primaryStage);
		menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
		
		root.setTop(menuBar);
		hbox.getChildren().addAll(iv, playButton, stopButton);
		root.setBottom(hbox);
		
		//HBox menuHbox = new HBox();
		//menuHbox.getChildren().add(menuBar);
//hbox.getChildren().add(list);
		//root.getChildren().add(hbox);
		//root.setAutoSizeChildren(true);
		
		
		
        
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
		layout(scene);
		
		primaryStage.setTitle("mp3 player");
		primaryStage.setWidth(480);
		primaryStage.setHeight(170);
		primaryStage.sizeToScene();
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//playList to be added in own window after file chooser choice
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
		RadioMenuItem playList = new RadioMenuItem("Playlist", null);
		playList.setMnemonicParsing(true);
		playList.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
		playList.setSelected(false);
		playList.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				//playList.setSelected(true);
				
				if(playList.isSelected())
					playListWindow(stage);
				
				else{
					playList.setSelected(false);
					closePlayListWindow();
				}
					
			}
		});

		menuFile.getItems().add(openItem);
		menuFile.getItems().add(exitItem);
		menuView.getItems().add(playList);
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
	private void layout(Scene scene)
	{
		String css = MediaPlayerGUI.class.getResource("MediaPlayerGUI.css").toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        
        
	}
	
	private void playListWindow(Stage primaryStage)
	{
		Scene secondscene = null;
		Group secondroot = new Group();
		
		if (secondscene == null)
		secondscene = new Scene(secondroot);	
		secondroot.getChildren().add(list);
		secondroot.autosize();
		if (stage == null)
			stage = new Stage();
		stage.initOwner(primaryStage);
		stage.sizeToScene();
		stage.setScene(secondscene);
		stage.setWidth(200);
		//stage.setScene(scene);
		stage.show();
		//return stage;
	}
	private void closePlayListWindow()
	{
		if (stage != null){
			stage.close();
			stage = null;
			}
	}

}
