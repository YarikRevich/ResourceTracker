package com.resourcetracker.config;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;
import java.io.*;

import com.resourcetracker.cloud.Provider.Providers;
import com.resourcetracker.config.parsable.*;
import com.resourcetracker.listenerpoll.Address;
import com.resourcetracker.tools.utils.*;

import org.javatuples.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import org.yaml.snakeyaml.Yaml;

/**
 * Parses YAML config file
 * 
 * @author YarikRevich
 *
 */
public class Parser implements Parsable {
	TreeMap<String, Object> obj;

	/**
	 * Parsed file. Assigned after validation
	 * 
	 * @exception throws exception if YAML config is empty
	 */
	public Parser() throws Exception{
		File file = new File(new ConfigPath(null).configPath().toString());
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Yaml yaml = new Yaml();
		Object config = yaml.load(inputStream);
		if (config == null) {
			throw new Exception("YAML config file should not be empty");
		}

		this.obj = new RawMapConverter<Object>(config).getResObj();
		this.validate();
	}

	/**
	 * Validates YAML config file
	 * 
	 * @return if YAML config file passes validation
	 * @exception 
	 */
	private boolean validate() throws Exception {
		boolean isLocal = this.isLocal();
		boolean isCloud = this.isCloud();

		if (!(isLocal || isCloud)) {
			throw new Exception("YAML config file should contains at least or 'local' or 'cloud'");
		}

		if (isCloud) {
			boolean isCloudProvider = this.isCloudProvider();
			if (!isCloudProvider) {
				throw new Exception("cloud provider should be one of those stated in docs");
			}

			boolean isCredentials = this.isCredentials();
			if (!isCredentials) {
				throw new Exception("'cloud' declaration should have 'credentials' one too");
			}

			boolean isCredentialsFile = this.isCredentialsFile();
			boolean isAccessKey = this.isAccessKey();
			boolean isSecretKey = this.isSecretKey();

			if (!(isCredentialsFile || (isAccessKey && isSecretKey))) {
				throw new Exception(
						"'credentials' declaration should have 'file' one or 'access_key' and 'secret_key' ones too");
			}

			boolean isCloudProviderRawPublicAddresses = this.isCloudProviderRawPublicAddresses();
			if (!isCloudProviderRawPublicAddresses) {
				throw new Exception("'cloud' should contain though one raw address");
			}
		}

		if (isLocal) {
			boolean isLocalRawPublicAddresses = this.isLocalRawPublicAddresses();
			if (!isLocalRawPublicAddresses) {
				throw new Exception("'local' should contain though one raw address");
			}
		}

		return false;
	};

	private boolean isLocal() {
		Object isLocal = this.obj.get("local");
		if (isLocal == null) {
			return false;
		}
		return true;
	}

	public boolean isCloud() {
		Object isCloud = this.obj.get("cloud");
		if (isCloud == null) {
			return false;
		}
		return true;
	};

	private boolean isLocalRawPublicAddresses() {
		TreeMap<String, Object> local = (TreeMap<String, Object>) this.obj.get("local");
		Object rawAddresses = local.get("raw_addresses");
		if (rawAddresses == null) {
			return false;
		}
		return true;
	}

	private boolean isCloudProviderRawPublicAddresses() {
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		Object rawAddresses = cloud.get("raw_addresses");
		if (rawAddresses == null) {
			return false;
		}
		return true;
	}

	private boolean isCredentials() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		TreeMap<String, Object> credentials = (TreeMap<String, Object>) cloud.get("credentials");
		if (credentials == null) {
			throw new Exception("'crendetials' should contain credentials 'file' or 'access_key' or 'secret_key'");
		}

		return true;
	}

	private boolean isCredentialsFile() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		TreeMap<String, Object> credentials = (TreeMap<String, Object>) cloud.get("credentials");
		if (credentials == null) {
			throw new Exception("'credentials' should contain credentials 'file' or 'access_key' or 'secret_key'");
		}

		Object credentialsFileRaw = credentials.get("file");
		if (credentialsFileRaw == null) {
			return false;
		}

		return true;
	}

	private boolean isAccessKey() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		TreeMap<String, Object> credentials = (TreeMap<String, Object>) cloud.get("credentials");
		if (credentials == null) {
			throw new Exception("'credentials' should contain credentials 'file' or 'access_key' or 'secret_key'");
		}

		Object accessKeyRaw = credentials.get("access_key");
		if (accessKeyRaw == null) {
			return false;
		}

		return true;
	}

	private boolean isSecretKey() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		TreeMap<String, Object> credentials = (TreeMap<String, Object>) cloud.get("credentials");
		if (credentials == null) {
			throw new Exception("'credentials' should contain credentials 'file' or 'access_key' or 'secret_key'");
		}

		Object secretKeyRaw = credentials.get("secret_key");
		if (secretKeyRaw == null) {
			return false;
		}

		return true;
	}

	private boolean isCloudProvider() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		String cloudProvider = (String) cloud.get("provider");

		switch (cloudProvider) {
			case "aws":
				return true;
			case "gcp":
				return true;
			case "az":
				return true;
		}
		return false;
	}

	/**
	 * looks for cloud provider set in YAML config file
	 * 
	 * @return cloud provider set in YAML config file
	 */
	public Providers getCloudProvider() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		String cloudProvider = (String) cloud.get("provider");

		switch (cloudProvider) {
			case "aws":
				return Providers.AWS;
			case "gcp":
				return Providers.GCP;
			case "az":
				return Providers.AZ;
		}
		return Providers.NONE;
	}

	private Pair<String, String> getCloudProviderCredentialsFromCSVFile(TreeMap<String, Object> credentials) {
		String accessKey = null, secretKey = null;
		String credentialsFile = (String) credentials.get("file");
		try (CSVReader csvReader = new CSVReader(new FileReader(credentialsFile));) {
			String[] line = null;
			while ((line = csvReader.readNext()) != null) {
				for (String word : line) {
					int index = word.indexOf("=") + 1;
					if (word.contains("AWSAccessKeyId")) {
						accessKey = word.substring(index);
					} else if (word.contains("AWSSecretKey")) {
						secretKey = word.substring(index);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (CsvValidationException e) {
			e.printStackTrace();
		}
		return new Pair<String, String>(accessKey, secretKey);
	}

	@Override
	public Pair<String, String> getCloudProviderCredentials() throws Exception {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		TreeMap<String, Object> credentials = (TreeMap<String, Object>) cloud.get("credentials");

		if (this.isAccessKey() && this.isSecretKey()) {
			return new Pair<String, String>((String) credentials.get("access_key"),
					(String) credentials.get("secret_key"));
		} else if (this.isCredentialsFile()) {
			return this.getCloudProviderCredentialsFromCSVFile(credentials);
		}

		return null;
	}

	@Override
	public ArrayList<String> getCloudProviderRawPublicAddresses() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		ArrayList<String> rawAddresses = new ArrayList<String>();
		try {
			rawAddresses = (ArrayList<String>) cloud.get("raw_addresses");
		} catch (ClassCastException e) {
			// STUB
		}

		return rawAddresses;
	}

	@Override
	public ArrayList<String> getLocalRawPublicAddresses() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> local = (TreeMap<String, Object>) this.obj.get("local");
		ArrayList<String> rawAddresses = new ArrayList<String>();
		try {
			rawAddresses = (ArrayList<String>) local.get("raw_addresses");
		} catch (ClassCastException e) {
			// STUB
		}

		return rawAddresses;
	}

	@Override
	public TreeMap<String, Address> getCloudProviderRawPublicAddressesWithTags() {
		@SuppressWarnings("unchecked")
		TreeMap<String, Object> cloud = (TreeMap<String, Object>) this.obj.get("cloud");
		TreeMap<String, Address> resultAddresses = new TreeMap<String, Address>();
		TreeMap<String, String> rawAddresses = new TreeMap<String, String>();
		try {
			rawAddresses = (TreeMap<String, String>) cloud.get("raw_addresses");
		} catch (ClassCastException e) {
			// STUB
		}
		int index = 0;
		for (Map.Entry<String, String> value : rawAddresses.entrySet()) {
			resultAddresses.put(value.getKey(), new Address(index, value.getValue()));
			index++;
		}
		return resultAddresses;
	}

	@Override
	public TreeMap<String, Address> getLocalRawPublicAddressesWithTags() {
		TreeMap<String, Object> local = null;
		try {
			local = new RawMapConverter<Object>(this.obj.get("local")).getResObj();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		TreeMap<String, Address> resultAddresses = new TreeMap<String, Address>();
		TreeMap<String, String> rawAddresses = new TreeMap<String, String>();
		try {
			try {
				rawAddresses = new RawMapConverter<String>(local.get("raw_addresses")).getResObj();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (ClassCastException e) {
			// STUB
		}
		int index = 0;
		for (Map.Entry<String, String> value : rawAddresses.entrySet()) {
			resultAddresses.put(value.getKey(), new Address(index, value.getValue()));
			index++;
		}
		return resultAddresses;
	}

	public boolean isSaveToBucket() {
		return false;
	}

	public boolean isDemon() {
		try {
			new RawMapConverter(this.obj.get("demon")).getResObj();
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
