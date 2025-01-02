
import java.util.ArrayList;

  public final class Game {
      private final Deck deck; // Le deck principal contenant toutes les cartes
      private final ArrayList<Player> listeDePlayers; // Liste des joueurs
      private final ArrayList<Carte> gamePile; // Pile de défausse pour les cartes jouées
      private int currentPlayerIndex; // Index du joueur actuel (tour en cours)
      private boolean reverseDirection; // Indique si le sens du jeu est inversé
  
      // Constructeur de la classe Game
      public Game(ArrayList<Player> listeDePlayers) {
          this.deck = new Deck(); // Créer un nouveau deck
          this.listeDePlayers = listeDePlayers; // Initialiser la liste des joueurs
          this.gamePile = new ArrayList<>(); // Initialiser une pile de défausse vide
          this.currentPlayerIndex = 0; // Le premier joueur commence
          this.reverseDirection = false; // Le jeu commence dans le sens horaire
  
          // Initialiser le jeu en distribuant les cartes et en plaçant la première carte
          initGame();
      }
  
      // Méthode pour initialiser le jeu
      public void initGame() {
          // Distribuer 7 cartes à chaque joueur
          for (Player player : listeDePlayers) {
              for (int i = 0; i < 7; i++) {
                  player.addCard(deck.piocher()); // Chaque joueur pioche une carte
              }
          }
  
          // Placer une première carte valide sur la pile de défausse
          Carte firstCard;
          do {
              firstCard = deck.piocher(); // Piocher une carte dans le deck
              if (firstCard instanceof CarteSpeciale) {
                // Si la carte est spéciale, la remettre dans le deck et mélanger
                  deck.remettreDansDeck(firstCard); // Méthode pour remettre la carte dans le deck
                  Deck.melanger(); // melanger a nouveau
              }
          } while (firstCard instanceof CarteSpeciale); // Éviter les cartes spéciales comme première carte
          gamePile.add(firstCard); // Ajouter la carte à la pile de défausse
  
          System.out.println("Le jeu commence avec la carte : " + firstCard);
      }
  
      // Méthode pour obtenir la carte au sommet de la pile de défausse
      public Carte getTopCard() {
          return gamePile.get(gamePile.size() - 1); // Dernière carte de la pile
      }
      /*gamePile.size() donne le nombre d'éléments dans la pile.
       gamePile.size() - 1 donne l'indice de la dernière carte de la pile (les indices des listes commencent à 0, donc l'indice de la dernière carte est toujours taille - 1).
       gamePile.get(...) permet d'accéder à l'élément à cet indice, donc la dernière carte de la pile.*/
      
      
      // Méthode pour passer au joueur suivant
      public void nextPlayer() {
          if (reverseDirection) {
              // Si le sens est inversé, on recule dans la liste des joueurs
              currentPlayerIndex = (currentPlayerIndex - 1 + listeDePlayers.size()) % listeDePlayers.size();
          } else {
              // Sinon, on avance dans la liste des joueurs
              currentPlayerIndex = (currentPlayerIndex + 1) % listeDePlayers.size();
          }
      }
  
      // Méthode pour gérer les cartes spéciales
      public void specialCardCase(CarteAction card) {
          String action = card.getValeur(); // Récupérer l'action de la carte
          switch (action) {
              case "Passer" -> {
                  System.out.println(listeDePlayers.get(currentPlayerIndex).getName() + " perd son tour !!");
                  nextPlayer(); // Passer au joueur suivant
              }
              case "Inverser" -> {
                  System.out.println("Le sens du jeu est inversé !!");
                  reverseDirection = !reverseDirection; // Changer le sens du jeu
              }
              case "+2" -> {
                  nextPlayer(); // Passer au joueur suivant
                  Player nextPlayer = listeDePlayers.get(currentPlayerIndex);
                  System.out.println(nextPlayer.getName() + " oh non , tu dois piocher 2 cartes !");
                  nextPlayer.addCard(deck.piocher()); // Le joueur suivant pioche 2 cartes
                  nextPlayer.addCard(deck.piocher());
              }
          }
      }
  
      // Méthode pour jouer un tour
      public void playTurn() {
          Player currentPlayer = listeDePlayers.get(currentPlayerIndex); // Obtenir le joueur actuel
          Carte topCard = getTopCard(); // Obtenir la carte au sommet de la pile de défausse
  
          System.out.println("\nC'est au tour de " + currentPlayer.getName());
          System.out.println("Carte au sommet : " + topCard);
  
          if (currentPlayer.hasPlayableCard(topCard)) {
              // Si le joueur a une carte jouable
              Carte playedCard = currentPlayer.selectCardToPlay(topCard); // Choisir une carte à jouer
              currentPlayer.playCard(playedCard); // Retirer la carte de la main du joueur
              gamePile.add(playedCard); // Ajouter la carte à la pile de défausse
              System.out.println(currentPlayer.getName() + " a joué : " + playedCard);
  
              if (playedCard instanceof CarteAction carteAction) {
                  // Si c'est une carte d'action, gérer son effet
                  specialCardCase(carteAction);
              } else if (playedCard instanceof CarteSpeciale) {
                  // Si c'est une carte spéciale, choisir une couleur
                  char chosenColor = currentPlayer.choisirCouleur();
                  playedCard.setCouleur(chosenColor);
                  System.out.println("La couleur choisie est : " + chosenColor);
              }
  
              if (currentPlayer.hasWon()) {
                  // Si le joueur n'a plus de cartes, il gagne
                  System.out.println(currentPlayer.getName() + " À GAGNÉ BRAVO !");
                  System.exit(0); // Terminer le jeu
              }
          } else {
              // Si le joueur ne peut pas jouer, il doit piocher une carte
              System.out.println(currentPlayer.getName() + " ne peut pas jouer , donc if faut piocher une carte !");
              currentPlayer.drawFromDeck(deck);
          }
  
          nextPlayer(); // Passer au joueur suivant
      }
  
      // Méthode pour démarrer le jeu
      public void start() {
          System.out.println("La partie commence !");
          while (true) {
              playTurn(); // Jouer les tours en boucle
          }
      }
  }    

