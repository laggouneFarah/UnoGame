import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Carte> cartes;

    public Deck() {
        cartes = new ArrayList<>();
        initialiserDeck();
        melanger();
    }

    // initialiser le deck avec toutes les cartes Uno
    private void initialiserDeck() {
        // ajouter les cartes normales (men 0 à 9 pour chaque couleur, 2 de chaque sauf le 0)
        char[] couleurs = {'R', 'B', 'V', 'J'};

        for (char couleur : couleurs) {
            for (int numero = 0; numero <= 9; numero++) {
                cartes.add(new CarteNormale(couleur, numero));
                if (numero != 0) { //apart 0
                    cartes.add(new CarteNormale(couleur, numero));
                }
            }
        }

        // ajouter les cartes spéciales (Passer, Inverser, +2)
        String[] actions = {"Passer", "Inverser", "+2"};
        for (char couleur : couleurs) {
            for (String action : actions) {
                cartes.add(new CarteSpeciale(couleur, action));
                cartes.add(new CarteSpeciale(couleur, action));
            }
        }

        // ajouter les cartes Joker (Joker et +4)
        for (int i = 0; i < 4; i++) {
            cartes.add(new CarteJoker("Joker"));
            cartes.add(new CarteJoker("+4"));
        }
    }

    //mélanger le deck
    public void melanger() {
        Collections.shuffle(cartes);
    }

    // piocher une carte
    public Carte piocher() {
        if (cartes.isEmpty()) {
            System.out.println("Le deck est vide !");
            return null;
        }
        return cartes.remove(cartes.size() - 1); // Retire la dernière carte
    }

    // réinitialiser le deck (refresh si on veut rejouer)
    public void reinitialiserDeck() {
        cartes.clear();
        initialiserDeck();
        melanger();
    }

    // afficher les cartes restantes dans le deck
    public void afficherDeck() {
        for (Carte carte : cartes) {
            System.out.println(carte);
        }
    }
}