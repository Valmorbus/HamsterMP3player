package mp3Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MediaLib {
	//private HashMap<String, String> adressName = new HashMap<String, String>();
	protected File file = new File("Data/mediaLib.dat");
	private String temp = null;

	protected File fetch(String name) {
		try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
			while ((temp = fin.readLine()) != null) {
				String adress = temp;
				if (adress.contains(name))
					return new File(adress); // + ".mp3");
			}
		} catch (FileNotFoundException e) {
	/*		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write("nothing in list");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	protected ArrayList<String> readLib() {
		ArrayList<String> allSongs = new ArrayList<String>();
		String text = "";
		// int i = 0;
		try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
			while ((text = fin.readLine()) != null) {
				allSongs.add(text);
			}
		} catch (FileNotFoundException e) {
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				bw.write("nothing in list");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return allSongs;
	}

	protected void SaveFile(ArrayList<String> content, File file) {
		try {
			FileWriter fileWriter = null;
			fileWriter = new FileWriter(file);
			for (String s : content)
				fileWriter.write(s + "\n");
			fileWriter.close();
		} catch (IOException ex) {

		}
	}
/*
	public void addToLib(String path, String name) {
		adressName.put(path, name);
	}*/
}
