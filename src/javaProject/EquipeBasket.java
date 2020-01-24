package javaProject;

public class EquipeBasket extends EquipeAbstraite {

	public EquipeBasket(String nom, String sport) {
		this.setNom(nom);
		this.setSport(sport);
	}
	
	public void ajoutA() {
		int a = this.getScore() + 1;
		this.setScore(a);
		
	}

	public void ajoutB() {
		int a = this.getScore() + 2;
		this.setScore(a);
		
	}

	public void ajoutC() {
		int a = this.getScore() + 3;
		this.setScore(a);
		
	}
	
	public static EquipeBasket fabriqueEquipeB(String nom, String sport) {
		
		EquipeBasket a = new EquipeBasket(nom, sport);
		
		return a;
		
	}

}
