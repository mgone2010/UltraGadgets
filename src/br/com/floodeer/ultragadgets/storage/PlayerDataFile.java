package br.com.floodeer.ultragadgets.storage;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.YamlConfigurationOptions;

public class PlayerDataFile {

	private File file = null;

	private YamlConfiguration yaml = new YamlConfiguration();

	public PlayerDataFile(File file) {

		this.file = file;

		if (!file.exists()) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		this.load();

	}

	public PlayerDataFile(String path) {

		this.file = new File(path);

		if (!file.exists()) {

			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		this.load();

	}

	private void load() {

		try {
			this.yaml.load(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void save() {

		try {
			this.yaml.save(this.file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void delete() {
		try {
			this.file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getInteger(String s) {

		return this.yaml.getInt(s);

	}

	public void reload() {

		this.save();

		this.load();

	}

	public String getString(String s) {

		return this.yaml.getString(s);

	}

	public Object get(String s) {

		return this.yaml.get(s);

	}

	public boolean getBoolean(String s) {

		return this.yaml.getBoolean(s);

	}

	public void add(String s, Object o) {

		if (!this.contains(s)) {

			this.set(s, o);

		}

	}

	public void addToStringList(String s, String o) {

		this.yaml.getStringList(s).add(o);

	}

	public void removeFromStringList(String s, String o) {

		this.yaml.getStringList(s).remove(o);

	}

	public java.util.List<String> getStringList(String s) {

		return this.yaml.getStringList(s);

	}

	public void addToIntegerList(String s, int o) {

		this.yaml.getIntegerList(s).add(o);

	}

	public void removeFromIntegerList(String s, int o) {

		this.yaml.getIntegerList(s).remove(o);

	}

	public java.util.List<Integer> getIntegerList(String s) {

		return this.yaml.getIntegerList(s);

	}

	public void createNewStringList(String s, java.util.List<String> list) {

		this.yaml.set(s, list);

	}

	public void createNewIntegerList(String s, java.util.List<Integer> list) {

		this.yaml.set(s, list);

	}

	public void remove(String s) {

		this.set(s, null);

	}

	public boolean contains(String s) {

		return this.yaml.contains(s);

	}

	public double getDouble(String s) {

		return this.yaml.getDouble(s);

	}

	public void set(String s, Object o) {

		this.yaml.set(s, o);

	}

	public void increment(String s) {

		this.yaml.set(s, this.getInteger(s) + 1);

	}

	public void decrement(String s) {

		this.yaml.set(s, this.getInteger(s) - 1);

	}

	public void increment(String s, int i) {

		this.yaml.set(s, this.getInteger(s) + i);

	}

	public void decrement(String s, int i) {

		this.yaml.set(s, this.getInteger(s) - i);

	}

	public YamlConfigurationOptions options() {

		return this.yaml.options();

	}

}