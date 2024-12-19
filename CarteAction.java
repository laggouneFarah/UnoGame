public class CarteAction extends Carte{
    public CarteSpeciale(char couleur, String action) {
        super(couleur, action);
        if (!action.equals("+2") && !action.equals("Inverser") && !action.equals("Passer")) {
            throw new IllegalArgumentException("Action non valide");
        }
    }    
}