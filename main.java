public class main{
    public static void main (String[] args){ 
     
     // Création de cartes
     CarteNormale carte1 = new CarteNormale(5,'R');
     CarteAction carte2 = new CarteAction("passer",'B'); 
     CarteSpeciale carte3 = new CarteSpeciale("wild"); 
        
      // Affichage
     carte1.afficher(); 
     carte2.afficher(); 
     carte3.afficher(); 
              
    }
}