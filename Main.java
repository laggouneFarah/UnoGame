import java.util.ArrayList;
import java.util.Scanner;

public class Main{
    @SuppressWarnings("ConvertToTryWithResources")
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();
        Deck jeuDeCartes = new Deck();
        // Get number of players
        System.out.println("entrer le nombre de joueur");
        int numberOfPlayers = scanner.nextInt();
        scanner.nextLine(); // Clear buffer
        
        if (numberOfPlayers < 2 || numberOfPlayers > 10) {
            System.out.println("Nombre de joueurs invalide. Le jeu nécessite 2 à 10 joueurs.");
            scanner.close();
            return;
        }
        
        // Create players
        for (int i = 0; i < numberOfPlayers; i++) {
            System.out.println("Nom du joueur " + (i + 1) + " :");
            String playerName = scanner.nextLine();
            System.out.println("Est-ce un joueur humain ? (O/N)");
            boolean isHuman = scanner.nextLine().toUpperCase().charAt(0) == 'O';
            players.add(new Player(playerName, isHuman));
        }
        
        // Start game
        Game game = new Game(players , jeuDeCartes);
        game.startGame();
        
        scanner.close();
    }

}