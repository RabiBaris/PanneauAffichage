package javaProject;
public abstract class EquipeAbstraite {

	private String sport;
	private String nom;
	private int score;
	
	protected abstract void ajoutA();
	protected abstract void ajoutB();
	protected abstract void ajoutC();
	
	public String getSport() {
		return sport;
	}
	
	public void setSport(String sport) {
		this.sport = sport;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	
	
	
}
