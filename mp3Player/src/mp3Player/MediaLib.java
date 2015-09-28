package mp3Player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class MediaLib {
	private HashMap<String, String> adressName = new HashMap<String, String>();
	private File file = new File("C:/Users/borgs_000/git/mp3Player/mp3Player/lib/mediaLib.txt");
	private ArrayList<String> adress = new ArrayList<String>();
	private ArrayList<String> name = new ArrayList<String>();
	private String temp = null;

	protected File fetch(String name) {
		try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
			while ((temp = fin.readLine()) != null) {
				String adress = fin.readLine();
				if (adress.contains(name))
					return new File(temp + adress + ".mp3");
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
		return null;
	}

	protected ArrayList<String> readLib() {
		ArrayList<String> allSongs = new ArrayList<String>();
		String text = "";
		int i = 0;
		try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
			while ((text = fin.readLine()) != null) {
				if (i++ % 2 != 0)
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

	protected void saveLib(ArrayList<String> s1, File f) {
		try (BufferedWriter fout = new BufferedWriter(new FileWriter(file));) {
			System.out.println("här");
			Set<Entry<String, String>> mapSet = adressName.entrySet();
			Iterator<Entry<String, String>> mapIterator = mapSet.iterator();
			while (mapIterator.hasNext()) {
				System.out.println("här iter");
				Entry<String, String> mapEntry = (Entry<String, String>) mapIterator.next();
				adress.add(mapEntry.getKey());
				name.add(mapEntry.getValue());
				System.out.println(mapEntry.getKey() + " " + mapEntry.getValue());
			}
			for (String s : adress) {
				for (String n : name)
					fout.write(s + '\n' + n);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void SaveFile(ArrayList<String> content, File file) {
		try {
			FileWriter fileWriter = null;
			content.addAll(readLib());
			fileWriter = new FileWriter(file);
			for (String s : content)
				fileWriter.write(s);
			fileWriter.close();
		} catch (IOException ex) {

		}

	}

	public void addToLib(String path, String name) {
		adressName.put(path, name);
	}
}
