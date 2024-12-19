import java.util.ArrayList;
import java.util.Random;

public class Player {
    private String playerName;          // Name of the player
    private ArrayList<Card> cardsInHand; // List of cards the player holds
    private boolean isHumanPlayer;     // Flag to check if the player is human
    private Random rng;                // Random number generator for AI decisions

    // Constructor
    public Player(String playerName, boolean isHumanPlayer) {
        this.playerName = playerName;
        this.cardsInHand = new ArrayList<>();  // Initialize hand as empty
        this.isHumanPlayer = isHumanPlayer;
        this.rng = new Random(); // Initialize RNG
    }

    // Add a card to the player's hand
    public void addCard(Card card) {
        cardsInHand.add(card);
        // Debug: Uncomment for debugging
        // System.out.println("DEBUG: " + playerName + " added " + card + " to hand.");
    }

    // Remove a card from the player's hand
    public void playCard(Card card) {
        if (!cardsInHand.remove(card)) {
            System.out.println("Warning: Tried to play a card not in hand."); // Typical human oversight
        }
    }

    // Show the player's cards
    public void displayHand() {
        System.out.println(playerName + "'s hand:");
        if (cardsInHand.isEmpty()) {
            System.out.println("  (No cards left!)");
        } else {
            for (Card card : cardsInHand) {
                System.out.println("  - " + card); // Indent for clarity
            }
        }
    }

    // Draw a card
    public void drawFromDeck(Deck deck) {
        Card newCard = deck.draw();
        if (newCard != null) {
            addCard(newCard);
            System.out.println(playerName + " drew a card: " + newCard);
        } else {
            System.out.println("Deck is empty! " + playerName + " can't draw.");
        }
    }

    // Check if a card can be played
    public boolean hasPlayableCard(Card topCard) {
        for (Card card : cardsInHand) {
            if (card.matches(topCard)) {
                return true;
            }
        }
        return false; // No playable cards
    }

    // AI logic for choosing a card to play
    public Card selectCardToPlay(Card topCard) {
        ArrayList<Card> validCards = new ArrayList<>();
        for (Card card : cardsInHand) {
            if (card.matches(topCard)) {
                validCards.add(card);
            }
        }
        if (validCards.isEmpty()) {
            return null; // No matches
        }
        // Pick a random card—could optimize, but keeping it simple
        return validCards.get(rng.nextInt(validCards.size()));
    }

    // Check if player has won
    public boolean hasWon() {
        return cardsInHand.isEmpty();
    }

    // Get the player's name
    public String getName() {
        return playerName;
    }

    // AI or player decision logic
    public void takeTurn(Card topCard, Deck deck) {
        if (isHumanPlayer) {
            // Placeholder for human logic
            System.out.println(playerName + ", it's your turn!");
        } else {
            // AI behavior
            if (hasPlayableCard(topCard)) {
                Card cardToPlay = selectCardToPlay(topCard);
                if (cardToPlay != null) {
                    playCard(cardToPlay);
                    System.out.println(playerName + " played: " + cardToPlay);
                }
            } else {
                drawFromDeck(deck);
            }
        }
    }
}
