package mp3Player;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MediaPlayerGUI extends Application {
	File songfile = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/The Hampsterdance Song.mp3");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		Mp3player mp = new Mp3player();
		
		Group root = new Group();
		Scene scene = new Scene(root);
		HBox hbox = new HBox();
		File file = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/Dancinghamster.gif");
		File file2 = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/hamster.gif");
		Image image = new Image(file2.toURI().toString());
		Image image2 = new Image(file.toURI().toString());
		ImageView iv = new ImageView();
		Button playButton = new Button("Play");
		
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
		MenuBar menuBar = menu();

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
	private MenuBar menu() 
	{
		MenuBar menuBar = new MenuBar();
		Menu menuFile = new Menu("File");
		Menu menuEdit = new Menu("Edit");
		Menu menuView = new Menu("View");
		menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
		return menuBar;
	}
	private ListView<String> playList()
	{
		MediaLib ml = new MediaLib();
		Mp3player mp = new Mp3player();
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(mp.readLib());
		list.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		list.getSelectionModel().selectedItemProperty().addListener(
		        (ObservableValue<? extends String> ov, String old_val, 
		            String new_val) -> {
		                System.out.println("häer" +new_val);
		                songfile = ml.fetch(new_val);
						mp.playing("Play", songfile);
		                
		    });
		
		list.setItems(items);
		
		return list;
	}


}
