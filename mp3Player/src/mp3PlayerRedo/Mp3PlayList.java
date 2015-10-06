package mp3PlayerRedo;

import java.io.File;
import java.util.ArrayList;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;


public class Mp3PlayList extends MediaLib{
	Stage stage = null;
	private ListView<String> list = new ListView<String>();
	private ArrayList<String> listOfSongs  = readLib();
	private File save = new File("Data/mediaLib.dat");
	private String defaultSong = "Data/The Hampsterdance Song.mp3";
	private File songfile = new File(defaultSong);
	
	protected ListView<String> playList() {
		ObservableList<String> items = FXCollections.observableArrayList(getOnlyName());
		list.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		list.getSelectionModel().selectedItemProperty()
				.addListener((ObservableValue<? extends String> ov, String old_val, String new_val) -> {
					songfile = fetch(new_val);
					

				});

		list.setItems(items);
		return list;
	}

	
	private ArrayList<String> getOnlyName() {
		listOfSongs = readLib();
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
				SaveFile(listOfSongs, save);
				list = playList();
			}
		}
		event.setDropCompleted(success);
		event.consume();

	}

	public void playListWindow() {
		
		Scene scene = null;
		Group secondroot = new Group();
		list = playList();
		//if (scene == null)
		scene = new Scene(secondroot);
		secondroot.getChildren().add(list);
		secondroot.autosize();
		if (stage == null)
			stage = new Stage();
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
				dragDropped(event);
			}
		});
		
		stage.sizeToScene();
		stage.setScene(scene);
		stage.setWidth(200);
		// stage.setScene(scene);
		stage.show();
		/*try{
			
		}
		finally{
			//saveToList(songfile.getAbsolutePath());
			SaveFile(listOfSongs, save);
		}
		*/
		// return stage;
	}
	public boolean isSelected()
	{
		if (getSongfile() != null)
			return true;
		else 
			return false;
	}

	public void closePlayListWindow() {
		if (stage != null) {
			stage.close();
			stage = null;
		}
	}
	public String getSelected()
	{
		return list.getSelectionModel().getSelectedItem();
	}


	public ListView<String> getList() {
		return list;
	}


	public void setList(ListView<String> list) {
		this.list = list;
	}


	public File getSongfile() {
		return songfile;
	}


	public void setSongfile(File songfile) {
		this.songfile = songfile;
	}


	public ArrayList<String> getListOfSongs() {
		return listOfSongs;
	}


	public void setListOfSongs(ArrayList<String> listOfSongs) {
		this.listOfSongs = listOfSongs;
	}


	public File getSave() {
		return save;
	}


	public void setSave(File save) {
		this.save = save;
	}
	
	
}
