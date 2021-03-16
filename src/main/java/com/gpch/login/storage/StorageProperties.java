package com.gpch.login.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@ConfigurationProperties("storage")
@ComponentScan
public class StorageProperties {

	/**
	 * Folder location for storing files
	 */
	private String location = "upload-dir";
	private String loadLocation = "results";

	public String getLocation() {
		return location;
	}
	public String getLoadLocation() {
		return loadLocation;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
