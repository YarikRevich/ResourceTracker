package com.resourcetracker.entity;

public class StateEntity {
	public enum Mode{
		STARTED("started"),
		STOPED("stoped");

		private String mode;
		private Mode(String mode){
			this.mode = mode;
		}
	}

	public Mode mode;
	public int configFileHash;
}
