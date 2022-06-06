package Game;

import java.util.Scanner;

public abstract class Tools {
    public static final Scanner input = new Scanner(System.in); // Creates a single scanner to be used throughout.
    
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(double seconds){
        try {
            Thread.sleep((int)(1000*seconds));
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public static void showMessage(String message, double seconds) {
        /**Shows a message and sleeps for designated number of seconds*/
        System.out.print(message);
        sleep(seconds);
    }

    public static void showOnlyMessage(String message, double seconds) {
        /**Clears the console before performing showMessage*/
        clearConsole();
        showMessage(message, seconds);
    }

    public static String promptInput(String prompt, String reprompt, String[] validInputs) {
        int iters = 0;
        boolean valid = false;
        String userInput = "";
        if (validInputs != null){
            while(!(valid)) {    
                String statement = ++iters == 1 ?
                                            prompt :
                                            reprompt; 
                System.out.print(statement);
                userInput = input.nextLine();
                for (String validInput : validInputs) {
                    if (userInput.toLowerCase().equals(validInput.toLowerCase())){
                        valid = true;
                        break;
                    }
                }
            }
        }
        else {
            System.out.print(prompt);
            userInput = input.nextLine();
        }
        return userInput;
    }

    public static void showTable(){
        System.out.println(
            "+------------+------------+-------------+---------------------------------------------------+--------------------+\n"+
            "| Key Number | Character  |   Action    |                      Effect                       |   Counteraction    |\n"+
            "+------------+------------+-------------+---------------------------------------------------+--------------------+\n"+
            "|          0 | All        | Review      | Look at your hand or discard (does not use turn)  | N/A                |\n"+   
            "|          1 | All        | Income      | Collect 1 coin                                    | N/A                |\n"+
            "|          2 | All        | Foreign Aid | Collect 2 coins                                   | N/A                |\n"+
            "|          3 | All        | Coup        | Pay 7 coins to reduce a player's influence by 1   | N/A                |\n"+
            "|          4 | Duke       | Tax         | Collect 3 coins                                   | Blocks Foreign Aid |\n"+
            "|          5 | Assassin   | Assassinate | Pay 3 coins to reduce a player's influence by 1   | N/A                |\n"+
            "|          6 | Ambassador | Exchange    | Exchange cards in your hand for cards in the deck | Blocks Steal       |\n"+
            "|          7 | Captain    | Steal       | Take 2 coins from another player                  | Blocks Steal       |\n"+
            "|        N/A | Contessa   | N/A         | N/A                                               | Blocks Assassinate |\n"+
            "+------------+------------+-------------+---------------------------------------------------+--------------------+");
    }
    
}
