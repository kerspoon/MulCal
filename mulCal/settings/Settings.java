package mulCal.settings;

import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import mulCal.util.KeyException;

public class Settings {
    private Properties configFile;

	public Settings() {
		this.configFile = new Properties();
		this.configFile.put("vat", "17.5");
		this.configFile.put("GBP", "1.0");
		this.configFile.put("USD", "1.60");
		this.configFile.put("AUD", "1.60");
		this.configFile.put("EUR", "1.18");
		this.configFile.put("THB", "49.55");
		this.configFile.put("MYR", "4.9");
	}
	
	public void Load(String fileName) throws IOException {
		FileInputStream fs = new FileInputStream(fileName);
		this.configFile.load(fs);
		fs.close();
	}
	
	public void Save(String fileName) throws IOException {
		FileOutputStream fs = new FileOutputStream(fileName);
		this.configFile.store(fs, null);
		fs.close();
	}
	
	public String Get(String key) throws KeyException {
		if (false == this.configFile.containsKey(key)) {
			throw new KeyException(key);
		}
			
		return this.configFile.getProperty(key);
	}
	
	public void Set(String key, String value) throws KeyException {
		if (false == this.configFile.containsKey(key)) {
			throw new KeyException(key);
		}
		this.configFile.setProperty(key, value);
	}
}
