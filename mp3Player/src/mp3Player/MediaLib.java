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
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public abstract class MediaLib {
	private HashMap<String, String> adressName = new HashMap<String, String>();
	private File file = new File("C:/Users/borgs_000/workspace/mp3Player/Lib/mediaLib.txt");
	private ArrayList<String> adress = new ArrayList<String>();
	private ArrayList<String> name = new ArrayList<String>();
	private String temp = null;

	protected String fetch(String name) {
		try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
			while ((temp = fin.readLine() + fin.readLine()) != null)
				;
			if (temp.contains(name))
				return temp;

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

		return "File not Found";

	}

	protected ArrayList<String> readLib() {
		ArrayList<String> allSongs = new ArrayList<String>();
		try (BufferedReader fin = new BufferedReader(new FileReader(file))) {
			while (fin.readLine() != null) {
				allSongs.add(fin.readLine());
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

	protected void saveLib() {
		try (BufferedWriter fout = new BufferedWriter(new FileWriter(file));) {

			Set<Entry<String, String>> mapSet = adressName.entrySet();
			Iterator<Entry<String, String>> mapIterator = mapSet.iterator();
			while (mapIterator.hasNext()) {
				Entry<String, String> mapEntry = (Entry<String, String>) mapIterator.next();
				adress.add(mapEntry.getKey());
				name.add(mapEntry.getValue());
			}
			for (String s : adress) {
				for (String n : name)
					fout.write(s + '\n' + n + ".mp3");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void addToLib(String path, String name) {
		adressName.put(path, name);
	}

}
