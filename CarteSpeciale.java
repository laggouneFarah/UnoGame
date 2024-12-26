
public class CarteSpeciale extends Carte {
	public CarteSpeciale(String valeur) {
		super(valeur, 'N'); //N veut dire aucune couleur
        if(!valeur.equals("wild") && !valeur.equals("wildfour")){
			throw new IllegalArgumentException("Type non valide");
		}
	}
	//j'ai redefinie la methode psk ay tkhrjli null
        @Override
	public void afficher() {
        // Redéfinir l'affichage sans couleur pour CarteSpeciale
        System.out.println("Carte spéciale: " + getValeur());
    }
}
