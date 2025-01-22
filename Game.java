import java.util.ArrayList;
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

    public List<Player> getJoueurs() {
        return joueurs;
    }

    public void setJoueurs(List<Player> joueurs) {
        this.joueurs = joueurs;
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
            Carte cartePiochee = jeuDeCartes.piocher();
            joueur.addCard(cartePiochee);
            System.out.println(joueur.getName() + " Tu viens de piocher la carte : " + cartePiochee);
        } else {
            System.out.println(joueur.getName() + " Sorry, tu as une carte valide, tu ne peux pas piocher !");
        }
    }

    @SuppressWarnings("StringEquality")
    private boolean aCarteValide(Player joueur) {
        List<Carte> mainDuPlayer = joueur.getMain();
        Carte derniereCarteSurLeDeck =  cartesSurTable.get(0);
        
        for (Carte carte : mainDuPlayer) {
            if (carte.getCouleur()==derniereCarteSurLeDeck.getCouleur() || (carte.getValeur() == derniereCarteSurLeDeck.getValeur())) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("StringEquality")
    public boolean aCarteValide(Player joueur, String chosenColor) {
        List<Carte> mainDuPlayer = joueur.getMain();
        
        for (Carte carte : mainDuPlayer) {
           if (Character.toString(carte.getCouleur()).equals(chosenColor)|| carte.getValeur() == cartesSurTable.get(0).getValeur()) {
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
        System.out.println("C'est maintenant le tour de " + joueurs.get(indexDuCurrentJoueur).getName());
    }

    public void Pass() {
        Player currentJoueur = joueurs.get(indexDuCurrentJoueur);
        System.out.println(currentJoueur.getName() + " passe son tour.");
        NextPlayer();
    }
 


    @SuppressWarnings("ConvertToTryWithResources")
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
        scanner.close();
        return couleurChoisie;
        
    }


    public void ReactToCard(Carte carteVisible) {
        if (carteVisible instanceof CarteAction) {
            String action = carteVisible.getValeur(); 
            switch (action) {
                case "Inverser" -> {
                    sensHoraire = !sensHoraire;
                    System.out.println("Le sens du jeu a été inversé !");
                    NextPlayer();
                }
                case "+2" -> {
                    Player nextJoueur = joueurs.get((indexDuCurrentJoueur + (sensHoraire ? 1 : -1) + joueurs.size()) % joueurs.size());
                    nextJoueur.addCard(jeuDeCartes.piocher());
                    nextJoueur.addCard(jeuDeCartes.piocher());
                    System.out.println(nextJoueur.getName() + " a pioché 2 cartes !");
                    NextPlayer();
                }
                case "Passer" -> {
                    System.out.println("Le joueur suivant perd son tour !");
                    NextPlayer();
                    Pass();
                }
                default -> System.out.println("Cette carte d'action n'a pas d'effet spécial.");
            }
        } else if (carteVisible instanceof CarteSpeciale) {
            String valeur = carteVisible.getValeur();
            switch (valeur) {
                case "wild" -> {
                    String nouvelleCouleur = choisirCouleur();
                    System.out.println("La couleur a été changée en : " + nouvelleCouleur);
                    NextPlayer();
                }
                case "wildfour" -> {
                    String chosenColor = choisirCouleur();
                    System.out.println("Le joueur a choisi la couleur : " + chosenColor);
                    Player joueurSuivant = joueurs.get((indexDuCurrentJoueur + (sensHoraire ? 1 : -1) + joueurs.size()) % joueurs.size());
                    for (int i = 0; i < 4; i++) {
                        joueurSuivant.addCard(jeuDeCartes.piocher());
                    }
                    System.out.println(joueurSuivant.getName() + " a pioché 4 cartes et perd son tour !");
                    NextPlayer();
                    Pass();
                }
                default -> System.out.println("Cette carte spéciale n'a pas d'effet spécial.");
            }
        } else if (carteVisible instanceof CarteNormale) {
            System.out.println("Cette carte est une carte normale et n'a pas d'effet spécial.");
            // Vous pouvez ajouter ici la logique pour jouer une carte normale si nécessaire
        } else {
            System.out.println("Type de carte inconnu.");
        }
    }
    

    public void distribuerCartesDebut() {
        for (int i = 0; i < joueurs.size(); i++) {
            Player joueur = joueurs.get(i);
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
            
            if (!cartesSurTable.isEmpty() && carteNotNormale(cartesSurTable.get(0))) {
                ReactToCard(cartesSurTable.get(0));
            } else {
                if (!aCarteValide(currentPlayer)) {
                    piocher(currentPlayer);
                } else {
                    System.out.println(currentPlayer.getName() + " joue sa carte.");
                }
            }
            
            NextPlayer();
        }
    }
}