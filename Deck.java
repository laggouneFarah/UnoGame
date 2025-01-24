import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Carte> cartes;

    // Constructeur
    public Deck() {
        cartes = new ArrayList<>();
        initialiserDeck(); // Initialise le deck avec toutes les cartes
        melanger(); // Mélange les cartes
    }

    // Méthode pour initialiser le deck
    private void initialiserDeck() {
        char[] couleurs = {'r', 'b', 'j', 'v'}; // Couleurs : rouge (r), bleu (b), jaune (j), vert (v)

        // Ajouter les cartes normales (numérotées de 0 à 9)
        for (char couleur : couleurs) {
            for (int numero = 0; numero <= 9; numero++) {
                cartes.add(new CarteNormale(numero, couleur)); // Ajoute une carte normale
                if (numero != 0) { // Les cartes numérotées de 1 à 9 ont deux exemplaires
                    cartes.add(new CarteNormale(numero, couleur));
                }
            }
        }

        // Ajouter les cartes d'action (+2, Inverser, Passer)
        String[] actions = {"+2", "Inverser", "Passer"};
        for (char couleur : couleurs) {
            for (String action : actions) {
                cartes.add(new CarteAction(action, couleur)); // Ajoute une carte d'action
                cartes.add(new CarteAction(action, couleur)); // Deux exemplaires par action
            }
        }

        // Ajouter les cartes spéciales (wild et wildfour)
        for (int i = 0; i < 4; i++) {
            cartes.add(new CarteSpeciale("wild")); // Ajoute une carte wild
            cartes.add(new CarteSpeciale("wildfour")); // Ajoute une carte wildfour
        }

        // Vérifier que le nombre total de cartes est correct (108)
        if (cartes.size() != 108) {
            throw new IllegalStateException("ERREUR : Le nombre total de cartes dans le deck est incorrect. Attendu : 108, Trouvé : " + cartes.size());
        }
    }

    // Méthode pour mélanger les cartes
    public final void melanger() {
        Collections.shuffle(cartes);
    }

    // Méthode pour piocher une carte
    public Carte piocher() {
        if (cartes.isEmpty()) {
            System.out.println("Le deck est vide !");
            return null;
        }
        return cartes.remove(cartes.size() - 1); // Pioche la dernière carte du deck
    }

    // Méthode pour réinitialiser le deck
    public void reinitialiserDeck() {
        cartes.clear(); // Vide le deck
        initialiserDeck(); // Réinitialise le deck
        melanger(); // Mélange les cartes
    }

    // Méthode pour afficher le deck
    public void afficherDeck() {
        for (Carte carte : cartes) {
            System.out.println("la carte est: " + carte); // Affiche chaque carte
        }
    }

    // Méthode pour remettre une carte dans le deck
    public void remettreDansDeck(Carte carte) {
        cartes.add(carte); // Ajoute une carte au deck
    }

    // Méthode pour obtenir le nombre de cartes dans le deck
    public int getNombreDeCartes() {
        return cartes.size();
    }

    // Méthode principale pour tester le deck
    public static void main(String[] args) {
        Deck deck = new Deck(); // Crée un nouveau deck

        System.out.println("Deck initial :");
        deck.afficherDeck(); // Affiche le deck initial

        System.out.println("\nNombre total de cartes : " + deck.getNombreDeCartes()); // Affiche le nombre de cartes

        System.out.println("\nPioche de 110 cartes :");
        for (int i = 0; i < 110; i++) {
            Carte cartePiochee = deck.piocher(); // Pioche une carte
            if (cartePiochee != null) {
                System.out.println("Carte piochée : " + cartePiochee); // Affiche la carte piochée
            }
        }

        System.out.println("\nDeck après pioche :");
        deck.afficherDeck(); // Affiche le deck après pioche

        System.out.println("\nRéinitialisation du deck...");
        deck.reinitialiserDeck(); // Réinitialise le deck

        System.out.println("\nDeck après réinitialisation :");
        deck.afficherDeck(); // Affiche le deck réinitialisé
    }
}