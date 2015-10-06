package mp3PlayerRedoSec;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Mp3Controller extends MediaLib implements MediaPlayerHandler {

	// public Media media = null;
	private MediaPlayer mediaPlayer = null;
	//private File file;

	public void playing(String text, File file) throws FileNotFoundException {
		// MediaPlayer mediaPlayer = null;
		
		Media media = null;
		try {
			URL res = file.toURI().toURL();
			media = new Media(res.toString());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (mediaPlayer == null)
			mediaPlayer = new MediaPlayer(media);

		if (text.equals("Play")) {
			pause();
		} else {
			play();
		}
	}

	public void stop() {
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}

	public void play() {
		// TODO Auto-generated method stub
		mediaPlayer.play();
	}

	public void forward() {
		// TODO Auto-generated method stub

	}

	public void reverse() {
		// TODO Auto-generated method stub

	}

	public void pause() {
		mediaPlayer.pause();

	}

	public Media getMedia(File fil) throws MalformedURLException {

		URL res = fil.toURI().toURL();
		Media media = new Media(res.toString());

		return media;
	}
	/*
	public Media getNewMedia() {
		Media media = null;
		if (getList().getSelectionModel().getSelectedItem() != null) {
			String path = getList().getSelectionModel().getSelectedItem();
			file = fetch(path);
			try {
				media = getMedia(file);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			file = null;

		return media;
	}*/

}
