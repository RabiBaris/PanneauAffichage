package javaProject;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class RencontresBDD {
	
	Connection conn;
	
	public RencontresBDD() {
		Connexion();
	}
	
	public void Connexion(){
		try {
			this.conn = DriverManager.getConnection(
			           "jdbc:mysql://localhost:3306/rencontres?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
			           "root", "");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	
	public ArrayList<String> getListeEquipe(String sport) {
		
		ArrayList<String> listeEquipe = new ArrayList<String>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String stmtEq = "SELECT nom FROM equipes WHERE sport = '" + sport + "'";
			ResultSet resSet = stmt.executeQuery(stmtEq);
			
			while(resSet.next()) {
				String nom = resSet.getString("nom");
				listeEquipe.add(nom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeEquipe;
	}
	
	
	public ArrayList<String> getListeSport() {
		
		ArrayList<String> listeSport = new ArrayList<String>();
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String stmtEq = "SELECT nom FROM sports";
			ResultSet resSet = stmt.executeQuery(stmtEq);
			
			while(resSet.next()) {
				String nom = resSet.getString("nom");
				listeSport.add(nom);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listeSport;
	}
	
	
	public int getDuree(String sport) {
		
		int duree = 0;
		
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String stmtEq = "SELECT duree FROM sports WHERE nom = '" + sport + "'";
			ResultSet resSet = stmt.executeQuery(stmtEq);
			
			while(resSet.next()) {
				duree = resSet.getInt("duree");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return duree;
	}
	
	public void enregMatch(int sc1, int sc2, String eq1, String eq2, String sport) {
		Statement stmt;
		
		
		try {
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String dateMatch = formatter.format(date);
			
						
			stmt = conn.createStatement();
			String stmtEq = "INSERT INTO matchs (score1, score2, eqp1, eqp2, sport, date) VALUES (" + sc1 + ", " + sc2 + ", '" + eq1 + "', '" + eq2 + "', '" + sport + "', '" + dateMatch + "')";
			System.out.println(stmtEq);
			stmt.executeUpdate(stmtEq);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
		
}