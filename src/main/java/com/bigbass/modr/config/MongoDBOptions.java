package com.bigbass.modr.config;

public class MongoDBOptions {
	public String hostname;
	public int port;
	
	public String username;
	public String password;

	public String database;
	public String authDatabase;
	
	public MongoDBOptions(){
		hostname = "hostname";
		port = 27017;
		
		username = "username";
		password = "password";

		database = "database_name";
		authDatabase = "authentication_database_name";
	}
}
