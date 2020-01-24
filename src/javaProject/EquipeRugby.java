package javaProject;

public class EquipeRugby extends EquipeAbstraite {
	
	public EquipeRugby(String nom, String sport) {
		this.setNom(nom);
		this.setSport(sport);
	}

	public void ajoutA() {
		int a = this.getScore() + 2;
		this.setScore(a);
		
	}

	public void ajoutB() {
		int a = this.getScore() + 3;
		this.setScore(a);
		
	}

	public void ajoutC() {
		int a = this.getScore() + 5;
		this.setScore(a);
		
	}
	
	public static EquipeRugby fabriqueEquipeR(String nom, String sport) {
		
		EquipeRugby a = new EquipeRugby(nom, sport);
		
		return a;
		
	}

}
