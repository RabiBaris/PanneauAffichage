package javaProject;

public class EquipeFoot extends EquipeAbstraite {
	
	public EquipeFoot(String nom, String sport) {
		this.setNom(nom);
		this.setSport(sport);
	}
	
	public void ajoutA() {
		int a = this.getScore() + 1;
		this.setScore(a);
		
	}

	public void ajoutB() {
		int a = this.getScore() + 1;
		this.setScore(a);
		
	}

	public void ajoutC() {
		int a = this.getScore() + 1;
		this.setScore(a);
		
	}
	
	public static EquipeFoot fabriqueEquipeF(String nom, String sport) {
		
		EquipeFoot a = new EquipeFoot(nom, sport);
		
		return a;
		
	}

}
