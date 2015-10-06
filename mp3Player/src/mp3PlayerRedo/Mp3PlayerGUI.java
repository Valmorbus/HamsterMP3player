package mp3PlayerRedo;

import java.io.File;
import java.net.MalformedURLException;

import org.omg.Messaging.SyncScopeHelper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Mp3PlayerGUI extends Application {
	Mp3Controller mp3 = new Mp3Controller();
	File file = new File("Data/big_buck_bunny.mp4");
	String play = "Play";
	MediaPlayer mPlayer;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
	
		
		Scene scene = setScene(primaryStage);
		
		primaryStage.setTitle("Media Player!");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
	}
	private Scene setScene(Stage stage)
	{
		Media media = null;
		try {
			media = mp3.getMedia(file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mPlayer = new MediaPlayer(media);
		MediaView mview = new MediaView(mPlayer);
		
		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(mview);
		borderPane.setBottom(toolBar());
		
		MenuBar menuBar = menu(stage);
		menuBar.prefWidthProperty().bind(borderPane.widthProperty());
		borderPane.setTop(menuBar);
		
		mPlayer.setAutoPlay(false);
		Scene scene = new Scene(borderPane, 600, 600);
		scene.setFill(Color.BLACK);
		
		
		
		/*if (mp3.isSelected()){
			try {
				mPlayer = new MediaPlayer(mp3.getMedia(file));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		
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
				mp3.setSongfile(fileChooser.showOpenDialog(stage));
				// String name = songfile.getName();
				mp3.saveToList(mp3.getSongfile().getAbsolutePath());
				mp3.SaveFile(mp3.getListOfSongs(), mp3.getSave());
				mp3.setList(mp3.playList());
			}
		});
		RadioMenuItem playList = new RadioMenuItem("Playlist", null);
		playList.setMnemonicParsing(true);
		playList.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
		playList.setSelected(false);
		playList.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				// playList.setSelected(true);
				if (playList.isSelected())
					mp3.playListWindow();

				else {
					playList.setSelected(false);
					mp3.closePlayListWindow();
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
	/*private File getNewMedia()
	{
		if (mp3.getList().getSelectionModel().getSelectedItem() != null)
		{
		String path= mp3.getList().getSelectionModel().getSelectedItem();
		file = mp3.fetch(path);
		}
	else 
		file = null;
		
		return file;
	}*/

}





