import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class Game {
    private List<Player> joueurs;
    private Deck deck;
    private Stack stack;
    private boolean sensHoraire;
    private int indexDuCurrentJoueur;

    public Game(List<Player> joueurs, Deck deck, Stack stack) {
        this.joueurs = joueurs;
        this.deck = deck;
        this.stack = stack;
        this.sensHoraire = true;
        this.indexDuCurrentJoueur = 0;
    }

    public List<Player> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Player> joueurs) {
        this.joueurs = joueurs;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public Stack getStack() {
        return stack;
    }

    public void setStack(Stack stack) {
        this.stack = stack;
    }

    public boolean isSensHoraire() {
        return sensHoraire;
    }

    public void setSensHoraire(boolean sensHoraire) {
        this.sensHoraire = sensHoraire;
    }

    public int getIndexDuCurrentJoueur() {
        return indexDuCurrentJoueur;
    }

    public void setIndexDuCurrentJoueur(int indexDuCurrentJoueur) {
        this.indexDuCurrentJoueur = indexDuCurrentJoueur;
    }

    public void piocher(Player joueur) {
        if (!aCarteValide(joueur)) {
            Card cartePiochee = deck.drawCard();
            joueur.addCarteMain(cartePiochee);
            System.out.println(joueur.getNom() + " Tu viens de piocher la carte : " + cartePiochee);
        } else {
            System.out.println(joueur.getNom() + " Sorry, tu as une carte valide, tu ne peux pas piocher !");
        }
    }

    private boolean aCarteValide(Player joueur) {
        List<Card> mainDuPlayer = joueur.getMain();
        Card derniereCarteSurLeDeck = stack.getFirst();
        
        for (Card carte : mainDuPlayer) {
            if (carte.getCouleur().equals(derniereCarteSurLeDeck.getCouleur()) || carte.getValeur() == derniereCarteSurLeDeck.getValeur()) {
                return true;
            }
        }
        return false;
    }

    public boolean aCarteValide(Player joueur, String chosenColor) {
        List<Card> mainDuPlayer = joueur.getMain();
        
        for (Card carte : mainDuPlayer) {
            if (carte.getCouleur().equals(chosenColor) || carte.getValeur() == stack.getFirst().getValeur()) {
                return true;
            }
        }
        return false;
    }

    public void NextPlayer() {
        if (sensHoraire) {
            indexDuCurrentJoueur = (indexDuCurrentJoueur + 1) % joueurs.size();
        } else {
            indexDuCurrentJoueur = (indexDuCurrentJoueur - 1 + joueurs.size()) % joueurs.size();
        }
        System.out.println("C'est maintenant le tour de " + joueurs.get(indexDuCurrentJoueur).getNom());
    }

    public void Pass() {
        Player currentJoueur = joueurs.get(indexDuCurrentJoueur);
        System.out.println(currentJoueur.getNom() + " passe son tour.");
        NextPlayer();
    }

    public void ReactToCard(Card carteVisible) {
        switch (carteVisible.getType()) {
            case REVERSE:
                sensHoraire = !sensHoraire;
                System.out.println("Le sens du jeu a été inversé !");
                NextPlayer();
                break;
            case PLUS2:
                Player nextJoueur = joueurs.get((indexDuCurrentJoueur + (sensHoraire ? 1 : -1) + joueurs.size()) % joueurs.size());
                nextJoueur.addCarte(deck.drawCard());
                nextJoueur.addCarte(deck.drawCard());
                System.out.println(nextJoueur.getNom() + " a pioché 2 cartes !");
                NextPlayer();
                break;
            case WILD:
                String nouvelleCouleur = choisirCouleur();
                System.out.println("La couleur a été changée en : " + nouvelleCouleur);
                NextPlayer();
                break;
            case WILD_FOUR:
                String chosenColor = choisirCouleur();
                System.out.println("Le joueur a choisi la couleur : " + chosenColor);
                Player joueurSuivant = joueurs.get((indexDuCurrentJoueur + (sensHoraire ? 1 : -1) + joueurs.size()) % joueurs.size());
                if (aCarteValide(joueurSuivant, chosenColor)) {
                    for (int i = 0; i < 4; i++) {
                        joueurSuivant.addCarte(deck.drawCard());
                    }
                    System.out.println(joueurSuivant.getNom() + " a pioché 4 cartes et perd son tour !");
                    NextPlayer();
                    Pass();
                } else {
                    for (int i = 0; i < 4; i++) {
                        joueurSuivant.addCarte(deck.drawCard());
                    }
                    System.out.println(joueurSuivant.getNom() + " a pioché 4 cartes et perd son tour !");
                    NextPlayer();
                    Pass();
                }
                break;
            case BLOCK:
                System.out.println("Le joueur suivant perd son tour !");
                NextPlayer();
                Pass();
                break;
            default:
                System.out.println("Cette carte n'a pas d'effet spécial.");
                break;
        }
    }

    public void distribuerCartesDebut() {
        for (int i = 0; i < joueurs.size(); i++) {
            Player joueur = joueurs.get(i);
            for (int j = 0; j < 7; j++) {
                Card carte = deck.drawCard();
                joueur.addCarteMain(carte);
            }
        }
    }

    public void tirageFirst() {
        Card carte1 = deck.drawCard();
        while (isCarteSpeciale(carte1)) {
            deck.ajouterCarte(carte1);
            deck.shufflerDeck();
            carte1 = deck.drawCard();
        }
        stack.addCarte1(carte1);
        System.out.println("La première carte visible sur le stack est : " + carte1);
    }

    public boolean isCarteSpeciale(Card carte) {
        switch (carte.getType()) {
            case REVERSE:
            case PLUS2:
            case WILD:
            case WILD_FOUR:
            case BLOCK:
                return true;
            default:
                return false;
        }
    }

   /* public String choisirCouleur() {
        String[] couleurs = {"Rouge", "Vert", "Bleu", "Jaune"};
        String couleurChoisie = (String) JOptionPane.showInputDialog(
            null,
            "Choisissez une couleur :",
            "Choix de la couleur",
            JOptionPane.QUESTION_MESSAGE,
            null,
            couleurs,
            couleurs[0]
        );
        if (couleurChoisie == null) {
            couleurChoisie = "Rouge";
        }
        return couleurChoisie;
    }*/ 

    public boolean endGAME() {
        for (Player joueur : joueurs) {
            if (joueur.getMain().isEmpty()) {
                System.out.println(joueur.getNom() + " a gagné ! Félicitations !");
                return true;
            }
        }
        return false;
    }

    public void startGame() {
        distribuerCartesDebut();
        tirageFirst();
        while (!endGAME()) {
            Player currentPlayer = joueurs.get(indexDuCurrentJoueur);
            System.out.println("C'est au tour de " + currentPlayer.getNom());
            
            if (!stack.isEmpty() && isCarteSpeciale(stack.getFirst())) {
                ReactToCard(stack.getFirst());
            } else {
                if (!aCarteValide(currentPlayer)) {
                    piocher(currentPlayer);
                } else {
                    System.out.println(currentPlayer.getNom() + " joue sa carte.");
                }
            }
            
            NextPlayer();
        }
    }
}
