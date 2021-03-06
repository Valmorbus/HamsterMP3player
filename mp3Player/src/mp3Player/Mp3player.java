package mp3Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Mp3player extends MediaLib implements MediaPlayerHandler{
	private MediaPlayer mediaPlayer = null;

	public void playing(String text, File file) throws FileNotFoundException{ //possibly add throw to cycle to next song in list on error

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
	@Override
	public void stop() {
		if (mediaPlayer != null)
			mediaPlayer.stop();
	}

	@Override
	public void play() {
		// TODO Auto-generated method stub
		mediaPlayer.play();
	}

	@Override
	public void forward() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reverse() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		mediaPlayer.pause();
		
	}
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}

	public void setMediaPlayer(MediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
	}

}
