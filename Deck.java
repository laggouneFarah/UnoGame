import java.util.ArrayList;
public class Deck {
    private final ArrayList<Carte> cartes;

    public Deck() {
        cartes = new ArrayList<>();
        initialiserDeck();
        melanger();
    }

    private void initialiserDeck() {
        char[] couleur = {'r','b','j','v'};
       

        // ajouter les cartes normales (0 à 9 pour chaque couleur, 2 de chaque sauf le 0)
        for (int j=0;j<4;j++) {
            for (int numero = 0; numero <= 9; numero++) {
                cartes.add(new CarteNormale(numero, couleur));
                if (numero != 0) { // Sauf pour le 0
                    cartes.add(new CarteNormale(numero, couleur));
                }
            }
        }
        
        String action;

        for (int j=0;j<4;j++) {
            for (int i=0;i<2;i++) {
                                cartes.add(new CarteAction(action , couleur));
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

      // Piocher une carte
       public Carte piocher() {
        if (cartes.isEmpty()) {
            System.out.println("Le deck est vide !");
            return null;
        }
        return cartes.remove(cartes.size() - 1); // Retire la dernière carte
        }

        //pour rejouer

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

     // hadi machi ana li drtha 9alek zidi Bch trj3i une carte l paquet tae les cartes  ki y9olk f jeu 
       public void remettreDansDeck(Carte carte) {
        cartes.add(carte); // Ajouter la carte à la fin du deck
        }

    // Méthode main pour tester la classe Deck
    public static void main(String[] args) {
        Deck deck = new Deck();

        System.out.println("Deck initial :");
        deck.afficherDeck();

        System.out.println("\nPioche de 5 cartes :");
        for (int i = 0; i < 5; i++) {
            Carte cartePiochee = deck.piocher();
            System.out.println("Carte piochée : " + cartePiochee);
        }

        System.out.println("\nDeck après pioche :");
        deck.afficherDeck();

        System.out.println("\nRéinitialisation du deck :");
        deck.reinitialiserDeck();
        deck.afficherDeck();
    }
}
