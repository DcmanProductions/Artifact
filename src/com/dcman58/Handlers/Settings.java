package com.dcman58.Handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Settings {

	private File objFile;
	private PrintWriter writer;
	private BufferedWriter bw;
	private BufferedReader reader;
	private String folderName = System.getenv("APPDATA") + "/Chase Labs/Artifact/Config/";
	private String FileName = "settings.cfg", fileContent;

	private List<Setting> settings;

	public Settings() {
		settings = new ArrayList<Setting>();
	}

	public void Init() {
		Load();
		AddSetting(new Setting("music", 0.0));
	}

	public void AddSetting(Setting setting) {
		if (!Exists(setting))
			settings.add(setting);
		Save(false);
	}

	public void RemoveSetting(Setting setting) {
		settings.remove(setting);
		Save(true);
	}

	public boolean Exists(Setting setting) {
		for (Setting s : settings) {
			if (s.getName().equals(setting.getName()))
				return true;
		}
		return false;
	}

	public Setting GetByKey(String key) {
		Setting i = new Setting();
		for (Setting s : settings) {
			if (s.getName().equals(key))
				i = s;
		}
		return i;
	}

	public void Save(boolean forceLoad) {
		File file = new File(folderName, FileName);
		new File(folderName).mkdirs();
		FileWriter fw;
		try {
			fw = new FileWriter(file);
			PrintWriter pw = new PrintWriter(fw);
			for (Setting s : settings) {
				pw.println(s.getName() + ":" + s.getValue());
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (forceLoad)
				Load();
		}

	}

	public void Load() {
		System.out.println("Loading Save File....\n");
		try {
			FileReader fr = new FileReader(new File(folderName, FileName));
			BufferedReader br = new BufferedReader(fr);
			String text;
			while ((text = br.readLine()) != null) {
				System.out.println(text + "\n");
				for (Setting s : settings) {
					if (text.split(":").length > 0) {
						if (text.split(":")[0].replace(":", "").startsWith(s.getName())) {
							s.setValue(text.split(":")[1].replace(":", ""));
							continue;
						}
					}
				}
				AddSetting(new Setting(text.split(":")[0].replace(":", ""), text.split(":")[1].replace(":", "")));
			}

			br.close();

		} catch (FileNotFoundException e) {
			Save(false);
//			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}