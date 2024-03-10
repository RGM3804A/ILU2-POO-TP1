package histoire;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	
	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois asterix = new Gaulois("Astérix", 8);
		try {
			etal.libererEtal();
			etal.occuperEtal(bonemine, "fleurs", 20);
			//etal.acheterProduit(0,asterix);
			etal.acheterProduit(5,null);
			etal.acheterProduit(1,asterix);
		} catch(IllegalArgumentException e) {
			System.out.println("Le client n'a rien voulu acheter.");
		}catch(IllegalStateException e) {
			System.out.println("Personne ne vend à cet étal.");
		}
		System.out.println("Fin du test");
	}
}