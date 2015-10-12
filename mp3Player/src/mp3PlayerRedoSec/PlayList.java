package mp3PlayerRedoSec;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class PlayList{
	private Stage stage = new Stage();
	BorderPane bp = new BorderPane();
	
	
	
	public Stage newPlayList()
	{	
			Scene scene = new Scene(bp);
			stage.setScene(scene);	
			bp.setLeft(Mp3PlayerGUI.list);
			stage.setTitle("Playlist");
			stage.setOpacity(1.0);
			stage.sizeToScene();
			stage.setResizable(false);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			stage.setX(primaryScreenBounds.getMinX() + primaryScreenBounds.getWidth() - 400);
			stage.setY(primaryScreenBounds.getMinY() + primaryScreenBounds.getHeight() - 800);
			stage.setWidth(400);
			stage.setHeight(300);
			
			return stage;
			//stage.show();
			
	}
	public Scene scenefixtemp()
	{
		Scene scene = new Scene(bp);
		return scene;
	}
	
	public void close() throws Throwable{
		this.stage.setOpacity(0);
		this.stage.toBack();
		this.stage.opacityProperty().add(0);
		this.stage.hide();
		
		this.finalize();
	}

	public Stage start(Stage stage) {
		stage = newPlayList();
		stage.hide();
		return stage;
	
		//stage.close();
	}

	
}
