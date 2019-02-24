package com.Xernium.ProtoFlow;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.Xernium.ProtoFlow.Data.BedrockPlayer;
import com.Xernium.ProtoFlow.Data.XUID;

public class Database {

	private static Connection _dbcon = null;

	public static boolean attemptDatabaseConnect() {
		Connection conn = null;
		try {
			try {
				new File(PluginMain.getPlugin().getDataFolder().getPath()).mkdirs();
			} catch (Exception unthrown) {

			}
			String url = "jdbc:sqlite:" + PluginMain.getPlugin().getDataFolder().getPath() + "/PlayerDatabase.db";
			conn = DriverManager.getConnection(url);

			String createCMD = "CREATE TABLE  IF NOT exists`PlayerDB` (\n`PDB_ID`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n`Name`	TEXT,\n`XUID`	TEXT UNIQUE );";
			Statement stmt = conn.createStatement();
			// create a new table
			stmt.execute(createCMD);
			stmt.close();
			conn.close();
			try {
				Connection dbcon = DriverManager.getConnection(url);
				_dbcon = dbcon;
			} catch (Exception e) {
			}
			return true;

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
		return false;
	}

	public static void closeDatabase() {
		try {
			if (_dbcon != null) {
				_dbcon.close();
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	
	public static Set<BedrockPlayer> getAll() {
		Statement stmt = null;
		HashMap<XUID,String> playersInDB = new HashMap<XUID,String>();
		String query = "SELECT * from PlayerDB;";
		try {
			stmt = _dbcon.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				if(rs.getString("XUID") != null && rs.getString("Name") != null) {
					XUID uid = null;
					String name = null;
					try {
						uid = XUID.fromString(rs.getString("XUID"));
						name = rs.getString("Name");
					} catch(Exception e) {
						name = null;
						uid = null;
					}
					if(name!=null & uid !=null)
						playersInDB.put(uid, name);
				}
			}
			stmt.close();
		} catch (SQLException e) {
			System.out.println("[PotoFlow-DB] SERVE! Failed to get DatabaseDatasets");
			System.out.println("[PotoFlow-DB] -- START REPORT -- ");
			e.printStackTrace();
			System.out.println("[[PotoFlow-DB] -- END REPORT -- ");
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException f) {
					System.out.println("[PotoFlow-DB] Urgent! Database seems to be corrupted! ");
					System.out.println("[PotoFlow-DB] Please run 'pragma integrity check;' ");
					System.out.println("[PotoFlow-DB] -- START REPORT -- ");
					e.printStackTrace();
					System.out.println("[PotoFlow-DB] -- END REPORT -- ");
				}
			}
			return new HashSet<BedrockPlayer>();
		}
		Set<BedrockPlayer> reti = new HashSet<BedrockPlayer>();
		for (HashMap.Entry<XUID, String> entry : playersInDB.entrySet()) {
			reti.add(BedrockPlayer.assumeNew(entry.getValue(), entry.getKey()));
		}
		return reti;

	}
	
	public static boolean deleteDatabaseEntry(XUID xuid) {
		Statement stmt = null;
		String delBricked = "DELETE FROM PlayerDB WHERE XUID = \"" + xuid.getID() + "\";";
		try {
			stmt = _dbcon.createStatement();
			stmt.executeUpdate(delBricked);
			stmt.close();
		} catch (SQLException e) {
			// This is expected to error
			
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException f) {
					System.out.println("[PotoFlow-DB] Urgent! Database seems to be corrupted! ");
					System.out.println("[PotoFlow-DB] -- START REPORT -- ");
					e.printStackTrace();
					System.out.println("[PotoFlow-DB] -- END REPORT -- ");
					return false;
				}
			}
			return false;
		}
		return true;
	}
	

	public static boolean createNewDatabaseEntry(BedrockPlayer p) {
		if(p == null)
			return false;
		String name = p.getName();
		XUID xuid = p.getXUID();
		
		if(xuid == null || name == null)
			return false;
		Database.deleteDatabaseEntry(xuid);
		String addEntry = "INSERT INTO PlayerDB (Name,XUID) \n VALUES(\"" + name + "\",\"" + xuid.getID() + "\");";
		Statement stmt_w = null;
		try {
			stmt_w = _dbcon.createStatement();
			stmt_w.executeUpdate(addEntry);
			stmt_w.close();
		} catch (SQLException e) {
			if (stmt_w != null) {
				try {
					stmt_w.close();
				} catch (SQLException f) {
					System.out.println("[HDB] Urgent! Database seems to be corrupted! ");
					System.out.println("[HDB] -- START REPORT -- ");
					e.printStackTrace();
					System.out.println("[HDB] -- END REPORT -- ");
					return false;
				}
			}
			return false;
		}
		return true;
	}
	
	public static boolean checkIsWhitelistedBedrock(String name) {
		if(name==null)
			return false;
		Set<BedrockPlayer> r = getAll();
		for(BedrockPlayer p : r) 
			if(p.getName().equalsIgnoreCase(name))
				return true;
		
		return false;
	}
	
	public static BedrockPlayer get(String name) {
		if(name==null)
			return null;
		Set<BedrockPlayer> r = getAll();
		for(BedrockPlayer p : r) 
			if(p.getName().equalsIgnoreCase(name))
				return p;
		
		return null;
	}

	
	public static BedrockPlayer get(XUID xuid) {
		if(xuid==null)
			return null;
		Set<BedrockPlayer> r = getAll();
		for(BedrockPlayer p : r) 
			if(p.getXUID().getID().equalsIgnoreCase(xuid.getID()))
				return p;
		
		return null;
	}
}
