public class Carte {
	private final String valeur; // num , signe 
	private char couleur;// r , b , j ,v
	public Carte(String valeur, char couleur) {
		this.couleur=couleur;
		this.valeur=valeur;
	}

    public char getCouleur() {
        return couleur;
    }

    public String getValeur() {
        return valeur;
    }

    public void afficher() {
        System.out.println("Carte: " + valeur + " " + couleur);
    }

    public void setCouleur(char couleur) {
        this.couleur = couleur;
    }

    public static void main (String[] args){ 
    
     // Création de cartes
     CarteNormale carte1 = new CarteNormale(5,'R');
     CarteAction carte2 = new CarteAction("passer",'B'); 
     CarteSpeciale carte3 = new CarteSpeciale("wild"); 
        
      // Affichage
     carte1.afficher(); 
     carte2.afficher(); 
     carte3.afficher(); 
              
    }

    public boolean matches(Carte topCard) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'matches'");
    }
}