
public class CarteSpeciale extends Carte {
	String wild;
    String wildfour;

	public CarteSpeciale(int num, char couleur,String wildfour, String wild ) {
		super(num, couleur);
        this.wild=wild;
        this.wildfour=wildfour;
	}

}
