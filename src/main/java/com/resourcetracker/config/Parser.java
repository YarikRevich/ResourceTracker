package com.resourcetracker.config;

import java.util.ArrayList;
import java.util.Map;
import com.resourcetracker.cloud.Providers;
import org.javatuples.Pair;

/**
 * Parses
 * 
 * @author YarikRevich
 *
 */
public class Parser {

	IReader reader;

	/**
	 * Parsed file. Assigned after validation
	 */
	Map<String, Object> obj;

	public Parser(IReader reader) {
		this.reader = reader;
	}
//
//	public ArrayList<String> getIPs() {
//		return new ArrayList<String>() {
//		};
//	};

	/**
	 * Validates YAML config file
	 * 
	 * @return if YAML config file passes validation
	 */
	public boolean validate() throws Exception {
		Object config = this.reader.getConfig();
		if (config == null) {
			throw new Exception("YAML config file should not be empty");
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> obj = (Map<String, Object>) config;
		this.obj = obj;

		boolean isLocal = this.isLocal();
		boolean isCloud = this.isCloud();

		if (!(isLocal || isCloud)) {
			throw new Exception("YAML config file should contains at least or 'local' or 'cloud'");
		}
		
		if (isCloud) {
			boolean isCredentials = this.isCredentials();
			if (!isCredentials) {
				throw new Exception("'cloud' declaration should have 'credentials' one too")
			}
			
			boolean isCredentialsFile = this.isCredentialsFile();
			boolean isAccessKey = this.isAccessKey();
			boolean isSecretKey = this.isSecretKey();
			
			if (!( isCredentialsFile || (isAccessKey && isSecretKey))) {
				throw new Exception("'credentials' declaration should have 'file' one or 'access_key' and 'secret_key' ones too")
			}
		}


		return false;
	};

	public boolean isLocal() {
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

	public boolean isCredentials() {
		return false;
	}

	public boolean isCredentialsFile() {
		@SuppressWarnings("unchecked")
		Map<String, Object> cloud = (Map<String, Object>)this.obj.get("cloud");
		Map<String, Object> credentials = (Map<String, Object>)cloud.get("credentials");
		if (credentials == null) {
			throw new Exception("crendetial");
		}
		String credentialsFile = (String)credentials.get("file");
		credentialsFile
		return false;
	}

	public boolean isAccessKey() {
		return false;
	}

	public boolean isSecretKey() {
		return false;
	}

	/**
	 * looks for cloud provider set in YAML config file
	 * 
	 * @return cloud provider set in YAML config file
	 */
	public Providers getCloudProvider() {
		@SuppressWarnings("unchecked")
		Map<String, Object> cloud = (Map<String, Object>) this.obj.get("cloud");
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

	public Pair<String, String> getCloudProviderCredentials() {
		@SuppressWarnings("unchecked")
		Map<String, Object> cloud = (Map<String, Object>) this.obj.get("cloud");
		Map<String, Object> credentials = (Map<String, Object>) cloud.get("credentials");

		return new Pair<String, String>("String", "String");
	};
}
