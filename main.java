public class main{
    public static void main (){
     
     // Création de cartes
     CarteNormale carte1 = new CarteNormale('R', 5);
     CarteSpeciale carte2 = new CarteAction('B', "Passer"); 
     CarteJoker carte3 = new CarteSpeciale("wild"); 
        
      // Affichage
     carte1.afficher(); 
     carte2.afficher(); 
     carte3.afficher(); 
              
    }
}