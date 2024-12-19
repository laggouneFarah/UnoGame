
public class CarteSpeciale extends Carte {
	public CarteSpeciale(String valeur) {
		super(valeur, 'N'); //N veut dire aucune couleur
        if(!type.equals("wild") && !type.equals("wildfour")){
			throw new IllegalArgumentException("Type non valide");
		}
	}

}
