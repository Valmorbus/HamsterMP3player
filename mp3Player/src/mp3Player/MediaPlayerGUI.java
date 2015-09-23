package mp3Player;

import java.io.File;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MediaPlayerGUI extends Application {
	private boolean onOff = false;

	public static void main(String[] args) {
		launch(args);

	}
	@Override
	public void start(Stage primaryStage) {
		
		Mp3player mp = new Mp3player();
		//ArrayList<String> test = new ArrayList<String>();
		//test = mp.readLib();

		Group root = new Group();
		Scene scene = new Scene(root);
		HBox hbox = new HBox();
		File file2 = new File("C:/Users/borgs_000/workspace/OOPJ15/text/Dancinghamster.gif");
		Image image = new Image(file2.toURI().toString());
		ImageView iv = new ImageView();
		iv.setImage(image);
		Button playButton = new Button("Play");
			playButton.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	String text = (playButton.getText().equals("Play"))?"Pause":"Play";
			        playButton.setText(text);
			        	mp.playing(text);
			    }
			});
		Button stopButton = new Button("Stop");
		stopButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        	mp.stop();
		        	String text = "Play";
			        playButton.setText(text);
		    }
		});
		ListView<String> list = new ListView<String>();
		ObservableList<String> items = FXCollections.observableArrayList(mp.readLib());

		items.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(ListChangeListener.Change change) {
                System.out.println("Detected a change! ");
            }
        }); 
		list.setItems(items);
		hbox.getChildren().addAll(iv, playButton, stopButton);
		hbox.getChildren().add(list);
		root.getChildren().add(hbox);
		
		primaryStage.setTitle("mp3 player");
		primaryStage.setWidth(550);
		primaryStage.setHeight(400);
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//mp.playing();
		

	}

}
