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
    
   public boolean matches(Carte topCard) {
    // Assuming cards match if either their rank (valeur) or suit (couleur) is the same
    return (this.valeur == null ? topCard.getValeur() == null : this.valeur.equals(topCard.getValeur())) || this.couleur == topCard.getCouleur();
   }
   
}