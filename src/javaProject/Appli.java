package javaProject;

public class Appli {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		EquipeAbstraite a;
		a = (EquipeAbstraite) EquipeRugby.fabriqueEquipeR("PSG", "Rugby");
		
		a.ajoutB();
		a.ajoutA();
		System.out.println(a.getNom());
		System.out.println(a.getScore());
		
		
		RencontresBDD aff = new RencontresBDD();
		
		aff.enregMatch(0, 0, "ddd", "fff", "rugby");
		

	}

}
