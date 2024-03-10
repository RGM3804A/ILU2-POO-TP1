package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtals);
	}
	
	private static class Marche{
		private Etal[] etals;
		private int nbEtals;
		
		public Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			for(int i=0; i<nbEtal; i++) {
				this.etals[i] = new Etal();
			}
			this.nbEtals = nbEtal;
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			if(!etals[indiceEtal].isEtalOccupe())
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre() {
			int indiceEtal = -1;
			for(int i = 0;i<nbEtals && indiceEtal == -1;i++) {
				if(!etals[i].isEtalOccupe()) {
					indiceEtal = i;
				}
			}
			return indiceEtal;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nbEtalVendeur = 0;
			for(int i = 0;i<nbEtals;i++) {
				/*try {*/
				if(etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) 
					nbEtalVendeur++;
				/*} catch(NullPointerException e) {
					e.printStackTrace();
				}*/
			}
			Etal[] etalVendeur = new Etal[nbEtalVendeur];
			int nbEtal = 0;
			for(int j = 0;nbEtal <nbEtalVendeur && j<nbEtals;j++) {
				if( etals[j].isEtalOccupe() && etals[j].contientProduit(produit)) {
					etalVendeur[nbEtal] = etals[j];
					nbEtal++;
				}
			}
			return etalVendeur;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalDuVendeur = null;
			for(int i = 0;i<nbEtals && etalDuVendeur == null;i++) {
				if(etals[i].getVendeur() == gaulois) 
					etalDuVendeur = etals[i];
			}
			return etalDuVendeur;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalNonOccupee = 0;
			for(int i = 0; i<nbEtals;i++) {
				String infoEtal = etals[i].afficherEtal();
				if(etals[i].isEtalOccupe()) {
					chaine.append(infoEtal);
				} else {
					nbEtalNonOccupee++;
				}
			}
			if(nbEtalNonOccupee >0 )
				chaine.append("Il reste " + nbEtalNonOccupee + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
		
		
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		StringBuilder chaine = new StringBuilder();
		if(this.chef == null)
			throw new VillageSansChefException("Ce village n'a pas de chef à sa tête.");
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder(vendeur.getNom() +" cherche un endroit pour vendre " + nbProduit + " " + produit +"\n");
		int indiceEtal = marche.trouverEtalLibre();
		if(indiceEtal != -1) {
			chaine.append("Le vendeur " + vendeur.getNom() + "  vend des fleurs à l'étal n°" + (indiceEtal+1) + "\n");
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
		}
		return chaine.toString();
	}
	
	 public String rechercherVendeursProduit(String produit) {
		 Etal[] etalVendeur = marche.trouverEtals(produit);
		 StringBuilder chaine = new StringBuilder();
		 if(etalVendeur.length>=1) {
			 chaine.append("Les vendeurs qui proposent des " + produit +" sont :\n");
			 for(int i = 0; i<etalVendeur.length; i++) {
				 chaine.append("- " + etalVendeur[i].getVendeur().getNom() + "\n");
			 }
		 } else {
			 chaine.append("Il n'y a pas de vendeur qui propose des " + produit +" au marché.\n");
		 }
		 return chaine.toString();
	 }
	 
	 public Etal rechercherEtal(Gaulois vendeur) {
		 return marche.trouverVendeur(vendeur);
	 }
	 
	 public String partirVendeur(Gaulois vendeur) {
		 Etal etal = rechercherEtal(vendeur);
		 String infoEtal = etal.libererEtal();
		 return infoEtal;
	 }
	
	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder("Le marché du village " +'"'+ this.getNom() +'"'+ " possède plusieurs étals :\n");
		String infoMarche = marche.afficherMarche();
		chaine.append(infoMarche);
		return chaine.toString();
	}
	
	
	
}