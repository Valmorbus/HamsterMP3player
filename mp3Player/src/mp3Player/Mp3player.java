package mp3Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Mp3player extends MediaLib {

	public void playing(boolean playing) {
		File file = new File("C:/Users/borgs_000/workspace/OOPJ15/text/Rick Astley3.mp3");
		Media media = null;
		try {

			URL res = file.toURI().toURL();
			media = new Media(res.toString());
			System.out.println("playing " + res.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		MediaPlayer mediaPlayer = new MediaPlayer(media);
		if (!playing)
			mediaPlayer.play();
		else if (playing)
			mediaPlayer.pause();
	}


}
