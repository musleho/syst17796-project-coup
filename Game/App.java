package Game;

//This will eventually be the application file. For now it can serve as a sort of test playground.
public class App {
    public static void main(String[] args) {
        //Game initiation logic
        Game.clearConsole();
        System.out.println("Welcome to JavaCoup!");
        Game.sleep(2);
        Game.clearConsole();
        System.out.println("Before we begin, take a moment to review the short guide below");
        Game.sleep(2);
        Game.showTable();
        Game.sleep(5);
        System.out.println();System.out.println();
        System.out.println("Now let's get some players registered.");
        Game.sleep(1);
        String[] validNumPlayers = {"3", "4", "5", "6"};
        String plyrCount = Game.promptInput("Firstly, how many players are there? (Minimum of 3 and maximum of 6): ",
                          "Sorry, that won't work. Please enter a number between 3 and 6: ", 
                          validNumPlayers);
        for (int i = 1; i <= Integer.parseInt(plyrCount); i++) {
            Game.registerPlayer();
            System.out.println("Thanks for joining, " + Game.ALL_PLAYERS.get(Game.ALL_PLAYERS.size()-1).getName() + "!");
            if (i < Integer.parseInt(plyrCount)) {
                System.out.println("Please press enter and pass the computer to the next registrant.");
                Game.input.nextLine(); Game.input.nextLine();
            }
        }

        Game.resetActivePlayers(); //Puts all registered players into active players list

        Deck deck = new Deck();
        deck.shuffle();

        //Rounds loop
        boolean stillPlaying = true;
        while(stillPlaying){
            //Round Initiation
            for(Player player : Game.PLAYERS) { //Each player draws two cards;
                deck.drawCard(player);
                deck.drawCard(player);
            }
            
            //
            Game.resetActivePlayers();
            for (Player player : Game.PLAYERS) {
                player.resetPlayer();
            }


            stillPlaying = false;
        }
    }
    
}
