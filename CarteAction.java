public class CarteAction extends Carte{
    public CarteAction( String action, char couleur) {
        super( action,couleur);
        if (!action.equals("+2") && !action.equals("Inverser") && !action.equals("passer")) {
            throw new IllegalArgumentException("Action non valide");
        }
    }    
}