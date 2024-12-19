public class CarteNormale extends Carte{
    public CarteNormale(String.valeurof(int),couleur){
        super(couleur, String.valueOf(numero));
        if (numero < 0 || numero > 9) {
            throw new IllegalArgumentException("donner un num entre 0 et 9");
        }
    }
}