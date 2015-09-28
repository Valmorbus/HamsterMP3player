package mp3Player;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MediaPlayerGUI extends Application {
	private String defaultSong = "C:/Users/borgs_000/git/mp3Player/mp3Player/lib/The Hampsterdance Song.mp3";
	private File songfile = new File(defaultSong);
	private Mp3player mp = new Mp3player();
	private MediaLib ml = new MediaLib();
	private File file = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/Dancinghamster.gif");
	private File file2 = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/hamster.gif");
	private Image image = new Image(file2.toURI().toString());
	private Image image2 = new Image(file.toURI().toString());
	private ListView<String> list = new ListView<String>();
	
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
	
		Group root = new Group();
		Scene scene = new Scene(root);
		HBox hbox = new HBox();
		ImageView iv = new ImageView();
		Button playButton = new Button("Play");
		iv.setImage(image);
		
		playButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				String text = (playButton.getText().equals("Play")) ? "Pause" : "Play";
				playButton.setText(text);
				mp.playing(text, songfile);
				if (text.equals("Play"))
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

		primaryStage.setTitle("mp3 player");
		primaryStage.setWidth(550);
		primaryStage.setHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		

	}
	/**
	 * Menu for ...
	 * @return
	 */
	private MenuBar menu(Stage stage) 
	{
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");
		Menu menuView = new Menu("View");
		MenuItem exitItem = new MenuItem("Exit", null);
	    exitItem.setMnemonicParsing(true);
	    exitItem.setAccelerator(new KeyCodeCombination(KeyCode.X,KeyCombination.CONTROL_DOWN));
	    exitItem.setOnAction(new EventHandler<ActionEvent>() {
	      public void handle(ActionEvent event) {
	    	  FileChooser fileChooser = new FileChooser();
	    	  fileChooser.setTitle("Save playlist");
	    	    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	    	  fileChooser.getExtensionFilters().add(extFilter);
	    	  File save = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/mediaLib.txt");
	    	  ml.saveLib( ml.readLib(), save);
	        Platform.exit();
	      }
	    });
	    MenuItem openItem = new MenuItem("Open", null);
	    openItem.setMnemonicParsing(true);
	    openItem.setAccelerator(new KeyCodeCombination(KeyCode.O,KeyCombination.CONTROL_DOWN));
	    openItem.setOnAction(new EventHandler<ActionEvent>(){
	    	public void handle(ActionEvent event){
	    		System.out.println("h�r");
	    		FileChooser fileChooser = new FileChooser();
	    		//fileChooser.getExtensionFilters().add( new FileChooser.ExtensionFilter("mp3"));
	    		fileChooser.setTitle("Open Resource File");
	    		songfile =fileChooser.showOpenDialog(stage);
	    		String name = songfile.getName();
	    		String absolutePath = songfile.getAbsolutePath();
	    		String filePath = absolutePath.
	    				substring(0,absolutePath.lastIndexOf(File.separator))+"\\";
	    		System.out.println(filePath + " " + name);
	    		ml.addToLib(filePath, name);
	    		
	    	}
	    });
	    
	    menuFile.getItems().add(openItem);
	    menuFile.getItems().add(exitItem);
		
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		return menuBar;
	}
	private ListView<String> playList()
	{
		
		//Mp3player mp = new Mp3player();
		ObservableList<String> items = FXCollections.observableArrayList(mp.readLib());
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.getSelectionModel().selectedItemProperty().addListener(
		        (ObservableValue<? extends String> ov, String old_val, 
		            String new_val) -> {
		                System.out.println("h�er" +new_val);
		                songfile = ml.fetch(new_val);
		                mp.stop();
		                mp = new Mp3player();
		                mp.playing("Play", songfile);
		                
		                //mp.stop();         
						//mp.playing("Pause", songfile);
		                
		    });
		
		list.setItems(items);
		
		return list;
	}


}
