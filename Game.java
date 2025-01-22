import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Game {
    private List<Player> joueurs;
    private Deck jeuDeCartes;
    private List<Carte> cartesSurTable;
    private boolean sensHoraire;
    private int indexDuCurrentJoueur;

    // Constructeur
    public Game(List<Player> joueurs, Deck jeuDeCartes) {
        if (jeuDeCartes == null) {
            throw new IllegalArgumentException("ERREUR , le jeu de cartes ne doit pas etre NULL.");
        }

        this.joueurs = joueurs;
        this.jeuDeCartes = jeuDeCartes;
        this.cartesSurTable = new ArrayList<>(); 
        this.sensHoraire = true; 
        this.indexDuCurrentJoueur = 0; 
    }

    public void piocher(Player joueur) {
        if (!aCarteValide(joueur)) {
            Carte cartePiochee = jeuDeCartes.piocher();
            if (cartePiochee != null) {
                joueur.addCard(cartePiochee);
                System.out.println(joueur.getName() + " Tu viens de piocher la carte : " + cartePiochee);
            } else {
                System.out.println("Le deck est vide !");
            }
        } else {
            System.out.println(joueur.getName() + " Sorry, tu as une carte valide, tu ne peux pas piocher !");
        }
    }

    public void Pass() {
        Player currentJoueur = joueurs.get(indexDuCurrentJoueur);
        System.out.println(currentJoueur.getName() + " passe son tour.");
        NextPlayer();
    }

    private boolean aCarteValide(Player joueur) {
        List<Carte> mainDuPlayer = joueur.getMain();
        if (cartesSurTable.isEmpty()) {
            return false; // Si aucune carte sur la table, le joueur ne peut pas jouer
        }
        Carte derniereCarteSurLeDeck = cartesSurTable.get(0);
        
        for (Carte carte : mainDuPlayer) {
            if (carte.getCouleur() == derniereCarteSurLeDeck.getCouleur() || carte.getValeur().equals(derniereCarteSurLeDeck.getValeur())) {
                return true;
            }
        }
        return false;
    }

    public void NextPlayer() {
        if (!cartesSurTable.isEmpty()) {
            System.out.println("Carte actuelle sur la table : " + cartesSurTable.get(0));
        }
        if (sensHoraire) {
            indexDuCurrentJoueur = (indexDuCurrentJoueur + 1) % joueurs.size();
        } else {
            indexDuCurrentJoueur = (indexDuCurrentJoueur - 1 + joueurs.size()) % joueurs.size();
        }
    }

    public String choisirCouleur() {
        Scanner scanner = new Scanner(System.in);
        String couleurChoisie;

        while (true) {
            System.out.println("Choisissez une couleur (rouge, jaune, vert, bleu) : ");
            couleurChoisie = scanner.nextLine();
            if (couleurChoisie.equals("rouge") || couleurChoisie.equals("jaune") || 
                couleurChoisie.equals("vert") || couleurChoisie.equals("bleu")) {
                break; 
            } else {
                System.out.println("Couleur invalide. Veuillez choisir entre rouge, jaune, vert ou bleu.");
            }
        }
        return couleurChoisie;
    }

    public void ReactToCard(Carte carteVisible) {
        if (carteVisible instanceof CarteAction) {
            String action = carteVisible.getValeur(); 
            switch (action) {
                case "Inverser":
                    sensHoraire = !sensHoraire;
                    System.out.println("Le sens du jeu a été inversé !");
                    NextPlayer();
                    break;
                case "+2":
                    Player nextJoueur = joueurs.get((indexDuCurrentJoueur + (sensHoraire ? 1 : -1) + joueurs.size()) % joueurs.size());
                    nextJoueur.addCard(jeuDeCartes.piocher());
                    nextJoueur.addCard(jeuDeCartes.piocher());
                    System.out.println(nextJoueur.getName() + " a pioché 2 cartes !");
                    NextPlayer();
                    break;
                case "Passer":
                    System.out.println("Le joueur suivant perd son tour !");
                    NextPlayer();
                    Pass();
                    break;
                default:
                    System.out.println("Cette carte d'action n'a pas d'effet spécial.");
                    break;
            }
        } else if (carteVisible instanceof CarteSpeciale) {
            String valeur = carteVisible.getValeur();
            switch (valeur) {
                case "wild":
                    String nouvelleCouleur = choisirCouleur();
                    System.out.println("La couleur a été changée en : " + nouvelleCouleur);
                    NextPlayer();
                    break;
                case "wildfour":
                    String chosenColor = choisirCouleur();
                    System.out.println("Le joueur a choisi la couleur : " + chosenColor);
                    Player joueurSuivant = joueurs.get((indexDuCurrentJoueur + (sensHoraire ? 1 : -1) + joueurs.size()) % joueurs.size());
                    for (int i = 0; i < 4; i++) {
                        joueurSuivant.addCard(jeuDeCartes.piocher());
                    }
                    System.out.println(joueurSuivant.getName() + " a pioché 4 cartes et perd son tour !");
                    NextPlayer();
                    Pass();
                    break;
                default:
                    System.out.println("Cette carte spéciale n'a pas d'effet spécial.");
                    break;
            }
        } else if (carteVisible instanceof CarteNormale) {
            System.out.println("Cette carte est une carte normale et n'a pas d'effet spécial.");
        } else {
            System.out.println("Type de carte inconnu.");
        }
    }

    public void distribuerCartesDebut() {
        for (Player joueur : joueurs) {
            for (int j = 0; j < 7; j++) {
                Carte carte = jeuDeCartes.piocher();
                joueur.addCard(carte);
            }
        }
    }

    public void tirageFirst() {
        Carte carte1 = jeuDeCartes.piocher();
        while (carteNotNormale(carte1)) {
            jeuDeCartes.remettreDansDeck(carte1);
            jeuDeCartes.melanger();
            carte1 = jeuDeCartes.piocher();
        }
        cartesSurTable.add(carte1);
        System.out.println("La première carte visible sur la table est : " + carte1);
    }

    public boolean carteNotNormale(Carte carte) {
        if (carte instanceof CarteAction) {
            String action = carte.getValeur();
            return action.equals("+2") || action.equals("Inverser") || action.equals("Passer");
        } else if (carte instanceof CarteSpeciale) {
            String valeur = carte.getValeur();
            return valeur.equals("wild") || valeur.equals("wildfour");
        }
        return false; 
    }

    public boolean endGAME() {
        for (Player joueur : joueurs) {
            if (joueur.getMain().isEmpty()) {
                System.out.println(joueur.getName() + " a gagné ! Félicitations !");
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
            System.out.println("C'est au tour de " + currentPlayer.getName());
            
            // Afficher les cartes du joueur
            System.out.println("Vos cartes : " + currentPlayer.getMain());
    
            if (!aCarteValide(currentPlayer)) {
                System.out.println("Vous ne pouvez pas jouer, vous devez piocher une carte.");
                piocher(currentPlayer);
            } else if (carteNotNormale(currentPlayer.getMain())) {
                ReactToCard(currentPlayer.getMain());
            } else {
    
                jouerCarte(currentPlayer);
            }
            
            NextPlayer();
        }
    }
    

    private void jouerCarte(Player joueur) {
        Scanner scanner = new Scanner(System.in);
        List<Carte> mainDuJoueur = joueur.getMain();

        // Afficher les cartes du joueur
        for (int i = 0; i < mainDuJoueur.size(); i++) {
            System.out.println(i + ": " + mainDuJoueur.get(i));
        }

        boolean carteJouee = false; // Indicateur pour savoir si une carte a été jouée

        while (!carteJouee) {
            System.out.println("Entrez le numéro de la carte que vous voulez jouer : ");
            int choix = scanner.nextInt();

            // Vérifier que le choix est valide
            if (choix >= 0 && choix < mainDuJoueur.size()) {
                Carte carteChoisie = mainDuJoueur.get(choix);
                // Vérifier si la carte choisie est jouable
                if (carteChoisie.getCouleur() == cartesSurTable.get(0).getCouleur() || 
                    carteChoisie.getValeur().equals(cartesSurTable.get(0).getValeur())) {
                    
                    // Jouer la carte
                    cartesSurTable.add(carteChoisie);
                    joueur.getMain().remove(carteChoisie);
                    System.out.println(joueur.getName() + " a joué la carte : " + carteChoisie);
                    carteJouee = true; // Une carte a été jouée, sortir de la boucle
                } else {
                    System.out.println("Cette carte ne peut pas être jouée ! Veuillez choisir une autre carte.");
                }
            } else {
                System.out.println("Choix invalide !");
            }
        }
    }
    public static void main(String[] args) {
        // Créer un deck de cartes
        Deck deck = new Deck(); // Assurez-vous que la classe Deck est définie

        // Créer des joueurs
        List<Player> joueurs = new ArrayList<>();
        joueurs.add(new Player("Alice"));
        joueurs.add(new Player("Bob"));
        joueurs.add(new Player("Charlie"));

        // Créer une instance de Game
        Game game = new Game(joueurs, deck);

                // Démarrer le jeu
                game.startGame();
            }
        }