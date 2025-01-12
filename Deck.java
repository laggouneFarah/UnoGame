import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Carte> cartes;

    public Deck() {
        cartes = new ArrayList<>();
        initialiserDeck();
        melanger();
    }

    private void initialiserDeck() {
        char[] couleurs = {'r', 'b', 'j', 'v'};

        for (char couleur : couleurs) {
            for (int numero = 0; numero <= 9; numero++) {
                cartes.add(new CarteNormale(numero, couleur));
                if (numero != 0) { 
                    cartes.add(new CarteNormale(numero, couleur));
                }
            }
        }
        
        String[] actions = {"+2", "Inverser", "Passer"};
        for (char couleur : couleurs) {
            for (String action : actions) {
                cartes.add(new CarteAction(action, couleur));
                cartes.add(new CarteAction(action, couleur));
            }
        }
        

        for (int i = 0; i < 4; i++) {
            cartes.add(new CarteSpeciale("wild"));
            cartes.add(new CarteSpeciale("wildfour"));
        }
    }

    public final void melanger() {
        Collections.shuffle(cartes);
    }

    public Carte piocher() {
        if (cartes.isEmpty()) {
            System.out.println("Le deck est vide !");
            return null;
        }
        return cartes.remove(cartes.size() - 1); 
    }

    public void reinitialiserDeck() {
        cartes.clear();
        initialiserDeck();
        melanger();
    }

    public void afficherDeck() {
        for (Carte carte : cartes) {
            carte.afficher();
        }
    }

    public void remettreDansDeck(Carte carte) {
        cartes.add(carte); 
    }

    public static void main(String[] args) {
        Deck deck = new Deck();

        System.out.println("Deck initial :");
        deck.afficherDeck();

        System.out.println("\nPioche de 5 cartes :");
        for (int i = 0; i < 5; i++) {
            Carte cartePiochee = deck.piocher();
            if (cartePiochee != null) {
                System.out.println("Carte piochée : ");
                cartePiochee.afficher();
            }
        }

        System.out.println("\nDeck après pioche :");
        deck.afficherDeck();

        System.out.println("\nRéinitialisation du deck :");
        deck.reinitialiserDeck();
        deck.afficherDeck();
    }
}