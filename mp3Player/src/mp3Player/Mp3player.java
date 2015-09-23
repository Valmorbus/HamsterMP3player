package mp3Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Mp3player extends MediaLib {
	private MediaPlayer mediaPlayer = null; 
	private boolean playing = false;

	public void playing(String text) {
		File file = new File("C:/Users/borgs_000/workspace/OOPJ15/text/Rick Astley3.mp3");
		Media media = null;
		try {

			URL res = file.toURI().toURL();
			media = new Media(res.toString());
			System.out.println("playing " + res.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		if (mediaPlayer == null)
			mediaPlayer = new MediaPlayer(media);
		if (text.equals("Play")) 
			{
			mediaPlayer.pause();
			playing = false;
			}
			
		else{
			mediaPlayer.play();
			playing = true;
		}
				
	}
	public void stop()
	{
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}


}
