package mp3PlayerRedoSec;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;

import org.omg.Messaging.SyncScopeHelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Mp3PlayerGUI extends Application {
	Mp3Controller mp3 = new Mp3Controller();
	File defaultFile = new File("Data/big_buck_bunny.mp4");
	String play = "Play";
	MediaPlayer mPlayer;
	MediaView mview;
	private ListView<String> list = new ListView<String>();
	private ArrayList<String> listOfSongs  =mp3.readLib();
	private File save = new File("Data/mediaLib.dat");
	private String defaultFileString = "Data/big_buck_bunny.mp4";
	private File mediaFile = new File(defaultFileString);
	private BorderPane borderPane;
	
	
	/*
	 * todo: 
	 * https://gist.github.com/jewelsea/7821196
	 * 
	 * kolla grafiken till denna
	 * http://www.java2s.com/Code/Java/JavaFX/Draggablepanel.htm
	 */
	
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {	
		Scene scene = setScene(primaryStage);
		
		primaryStage.setTitle("Media Player!");
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.show();	
				
	}
	private Scene setScene(Stage stage)
	{
		Media media = null;
		try {
			media = mp3.getMedia(defaultFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer = new MediaPlayer(media);
		mview = new MediaView(mPlayer);
		mview.isResizable();
		borderPane = new BorderPane();
		borderPane.setCenter(mview);

		borderPane.setBottom(toolBar());
		ListView<String> list = playList();
		list.setId("list");
		borderPane.setRight(list);
		list.setOnDragOver(new EventHandler<DragEvent>() {
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
		list.setOnDragDropped(new EventHandler<DragEvent>() {
			@Override
			public void handle(DragEvent event) {
				dragDroppedList(event);
			}
		});
			
		MenuBar menuBar = menu(stage);
		menuBar.prefWidthProperty().bind(borderPane.widthProperty());
		borderPane.setTop(menuBar);
		
		mPlayer.setAutoPlay(false);
		Scene scene = new Scene(borderPane, 900, 600);
		scene.setFill(Color.BLACK);
		
		return scene;
	}

	private HBox toolBar() {
		HBox hToolBar = new HBox();
		hToolBar.setPadding(new Insets(20));
		hToolBar.setAlignment(Pos.CENTER);
		hToolBar.alignmentProperty().isBound();
		hToolBar.setSpacing(5);
		hToolBar.setStyle("-fx-background-color: Black");
		hToolBar.getChildren().addAll(stopButton(),playButton());
		return hToolBar;
	}
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
				mediaFile = fileChooser.showOpenDialog(stage);
				// String name = songfile.getName();
				saveToList(mediaFile.getAbsolutePath());
				mp3.SaveFile(listOfSongs, save);
				list =  playList();
			}
		});
		RadioMenuItem playList = new RadioMenuItem("Playlist", null);
		playList.setMnemonicParsing(true);
		playList.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
		playList.setSelected(true);
		playList.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// playList.setSelected(true);
				if (playList.isSelected())
					{
					list.setVisible(true);
					}
				else {
					list.setVisible(false);
				
					
					
				}
			}
		});

		menuFile.getItems().add(openItem);
		menuFile.getItems().add(exitItem);
		menuView.getItems().add(playList);
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		return menuBar;
	}

	private Button playButton() {
		Image playButtonImage = new Image(new File("Data/mp3buttons/Play.png").toURI().toString());
		Button playButton = new Button();
		playButton.setGraphic(new ImageView(playButtonImage));
		playButton.setStyle("-fx-background-color: Black");
		playButton.setOnAction((ActionEvent e)->{
			mPlayer.play();
		});
		playButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			playButton.setStyle("-fx-background-color: Black");
			playButton.setStyle("-fx-body-color: Black");
		});
		playButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			playButton.setStyle("-fx-background-color: Black");
		});
		
		return playButton;
	}
	private Button stopButton() {
		Image stopButtonImage = new Image(new File("Data/mp3buttons/Stop.png").toURI().toString());
		Button stopButton = new Button();
		stopButton.setGraphic(new ImageView(stopButtonImage));
		stopButton.setStyle("-fx-background-color: Black");
		
		stopButton.addEventHandler(MouseEvent.MOUSE_ENTERED, (MouseEvent e) -> {
			stopButton.setStyle("-fx-background-color: Black");
			stopButton.setStyle("-fx-body-color: Black");
		});
		stopButton.addEventHandler(MouseEvent.MOUSE_EXITED, (MouseEvent e) -> {
			stopButton.setStyle("-fx-background-color: Black");
		});
		stopButton.setOnAction((e)->{
			mPlayer.stop();
		});
		return stopButton;
	}
	private ListView<String> playList() {
		ObservableList<String> items = FXCollections.observableArrayList(getOnlyName());
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
					mediaFile = mp3.fetch(new_val);
					Media pickedMedia = null;;
				
					try {
						pickedMedia = mp3.getMedia(mediaFile);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mPlayer.stop();
					mPlayer = new MediaPlayer(pickedMedia);
					mview.setMediaPlayer(mPlayer);
					mPlayer.play();
					
				});

		list.setItems(items);
		return list;
	}
	
	private ArrayList<String> getOnlyName() {
		listOfSongs = mp3.readLib();
		ArrayList<String> tempList = new ArrayList<String>();
		for (String s : listOfSongs) {
			File userFile = new File(s);
			String filename = userFile.getName();
			tempList.add(filename);
		}
		return tempList;
	}

	public void saveToList(String s) {
		listOfSongs.add(s);
	}
	
	private void dragDroppedList(final DragEvent event) {
		Dragboard db = event.getDragboard();
		boolean success = false;
		if (db.hasFiles()) {
			success = true;
			String filePath = null;
			for (File file : db.getFiles()) {
				filePath = file.getAbsolutePath();
				System.out.println(filePath);
				mediaFile = file;
				saveToList(mediaFile.getAbsolutePath());
				mp3.SaveFile(listOfSongs, save);
				list = playList();
			}
		}
		event.setDropCompleted(success);
		event.consume();
	
	}
	/*
	public void playListWindow() {
		
		Scene scene = null;
		Group secondroot = new Group();
		list = playList();
		//if (scene == null)
		scene = new Scene(secondroot);
		secondroot.getChildren().add(list);
		secondroot.autosize();
		//stage.initOwner(primaryStage);
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
				dragDroppedList(event);
			}
		});*/
	}







