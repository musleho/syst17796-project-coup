package Game;

import Exceptions.*;
import Effects.*;
import java.util.Collections;

//This will eventually be the application file. For now it can serve as a sort of test playground.
public class App {

    public static void main(String[] args) {
        //Game initiation logic

        Deck deck = Game.deck;
        Tools.showOnlyMessage("Welcome to JavaCoup!\n", 1.5);
        
        Tools.showOnlyMessage("Before we begin, take a moment to review the short guide below\n", 2.5);
        Tools.showTable();
        Tools.showMessage("\n\n", 5);
        
        Tools.showMessage("Now let's get some players registered. ", 1.5);

        String[] yN = {"y", "n"};
        String[] validNumPlayers = {"3", "4", "5", "6"};
        String rawPlayerCount = Tools.promptInput("Firstly, how many players are there? (Minimum of 3 and maximum of 6): ",
                          "Sorry, that won't work. Please enter a number between 3 and 6: ", 
                          validNumPlayers);
        
        int plyrCount = Integer.parseInt(rawPlayerCount);
        for (int i = 1; i <= plyrCount; i++) {
            Game.registerPlayer();
            System.out.println("Thanks for joining, " + Game.ALL_PLAYERS.get(Game.ALL_PLAYERS.size()-1).getName() + "!");
            if (i < plyrCount) {
                System.out.println("Please press enter and pass the computer to the next registrant.");
                Tools.input.nextLine(); 
            }
            Tools.input.nextLine();
        }

        //Rounds loop
        boolean stillPlaying = true;
        String roundWinner = null;
        while(stillPlaying){
            //Round Initiation
            Game.resetActivePlayers(); //Puts all registered players into active players list and reset their fields.
            deck.shuffle(); //shuffle the deck.
            for(Player player : Game.PLAYERS) { //Each player draws two cards;
                player.drawCard(deck);
                player.drawCard(deck);
                Game.showCards(player);
            }
            // int turnCount = 0;
            int startingPlayer = 0;
            if (roundWinner == null) {
                double rand = Math.random()*Game.ALL_PLAYERS.size();
                startingPlayer = (int)(Math.floor(rand));
            }
            else {
                try {startingPlayer = Game.findAnyPlayer(roundWinner).getNumber();}
                catch (PlayerNotFoundException e) {e.printStackTrace();}
            }

            //Turn logic
            while(roundWinner == null){
                // int numPlayers = Game.PLAYERS.size(); // gets the number of living players each turn
                Player activePlayer = Game.findTurnPlayer(startingPlayer++); //get the current player's turn
                Tools.showOnlyMessage("It is now " + activePlayer.getName() + "'s turn.\n\n", 3);
                
                Player[] otherPlayers = Game.findWaitingPlayers(activePlayer); //the list of non-active player objects.

                Player[] activePlayerAsArr = {activePlayer}; //to pass activePlayer as array into processChallenges() call.
                
                String[] otherPlayerNames = new String[otherPlayers.length]; //names of non-active players for validation
                for (int i = 0; i < otherPlayerNames.length; i++) {
                    otherPlayerNames[i] = otherPlayers[i].getName();
                    // System.out.println(otherPlayers[i]);
                }
                
                String[] validEffects = {"0", "1", "2", "3", "4", "5", "6", "7"};
                int playerChoice = 0;
                boolean sufficientCoins = true; //flips to false if declareEffect throws InsufficientCoinsException
                Effect declaredEffect = Game.EFFECTS[2]; //default to coup until properly overwritten
                if (activePlayer.getCoins() >= 10); //force a coup if the player has 10 or more coins.
                else {
                    while (playerChoice == 0 || sufficientCoins == false) {
                        Tools.showTable();
                        Game.showAllCoins();
                        System.out.println("\nDiscard pile:");
                        deck.inspectDiscard();
                        System.out.println();
                        String effectPrompt = "Enter the key number of the effect you wish to declare: ";
                        playerChoice = Integer.parseInt(Tools.promptInput(effectPrompt, "Sorry, that isn't valid. " + effectPrompt, validEffects));
                        try {
                            declaredEffect = activePlayer.declareEffect(playerChoice);
                            sufficientCoins = true;
                        }
                        catch (InsufficientCoinsException e) {
                            Tools.showMessage("Sorry, you don't have enough coins to do that.\n", 1.25);
                            Tools.showMessage("Let's try again. ", 0.5);
                            sufficientCoins = false;
                        }
                        catch (ArrayIndexOutOfBoundsException e){ //This gets triggered if player enters 0 - checks hand.
                            Game.showCards(activePlayer); //This is a bad way to code this...
                        }
                    }
                }
                Tools.showMessage(activePlayer.getName() + " has declared " + declaredEffect.getName() + "!\n", 2.5);
                
                int cardFlag = Game.checkEffectBluff(activePlayer, declaredEffect.getName());
                boolean apIsBluffing = cardFlag < 0;

                Player targetPlayer = activePlayer; //default, assumes effect is not targeted.
                
                if (declaredEffect.isTargeted()) {
                    String targetPrompt = "Enter the name of the target player: ";
                    String target = Tools.promptInput(targetPrompt, "Sorry, that isn't valid. " + targetPrompt, otherPlayerNames);
                    try {
                        targetPlayer = Game.findLivingPlayer(target);
                    }
                    catch (PlayerNotFoundException e) {e.printStackTrace();} //theoretically shouldn't reach here...
                    Tools.showMessage(targetPlayer.getName() + " is the chosen target!\n", 2);
                }

                boolean challengeIssued = false;
                boolean challengeSuccessful = false;
                
                if (declaredEffect.isRefutable()){
                    boolean[] challengeResults = Game.processChallenges(otherPlayers, activePlayer, apIsBluffing, cardFlag, deck);
                    challengeIssued = challengeResults[0];
                    challengeSuccessful = challengeResults[1];
                }

                boolean blockSuccessful = false;
                if (!challengeIssued){ //blocking only relevent when a challenge was not issued.
                    if(declaredEffect.isBlockable()){
                        if (declaredEffect instanceof ForeignAid) {  
                            for (Player player : otherPlayers) {
                                String blockPrompt = player.getName() + ", would you like to counteract? [y/n]: ";
                                String willBlock = Tools.promptInput(blockPrompt, "Sorry, that isn't valid. " + blockPrompt, yN).toLowerCase();
                                if(willBlock.equals("y")) {
                                    String counteract = targetPlayer.counteract(declaredEffect);
                                    int blockFlag = Game.checkCounteractBluff(player, counteract);
                                    boolean tpIsBluffing = blockFlag < 0;
                                    boolean blockChallenge = Game.processChallenges(activePlayerAsArr, player, tpIsBluffing, blockFlag, deck)[1];
                                    blockSuccessful = !blockChallenge; //successful challenge means failed block and vice versa, hence the not operator.
                                    break; //once a block and challenge are resolved, no other blocks/challenges can be issued.
                                }
                            }
                        }
                        else {
                            String blockPrompt = targetPlayer.getName() + ", would you like to counteract? [y/n]: ";
                            String willBlock = Tools.promptInput(blockPrompt, "Sorry, that isn't valid. " + blockPrompt, yN).toLowerCase();
                            if(willBlock.equals("y")) {
                                String counteract = targetPlayer.counteract(declaredEffect);
                                int blockFlag = Game.checkCounteractBluff(targetPlayer, counteract);
                                boolean tpIsBluffing = blockFlag < 0;
                                boolean blockChallenge = Game.processChallenges(activePlayerAsArr, targetPlayer, tpIsBluffing, blockFlag, deck)[1];
                                blockSuccessful = !blockChallenge; //successful challenge means failed block and vice versa, hence the not operator.
                            }
                        }
                    }
                }

                if (!challengeSuccessful && !blockSuccessful) {//both the challenge and the block have to be passed in order to execute.
                    declaredEffect.execute(targetPlayer); //do the effect to the target player (which is the active player if effect is not targeted)
                    activePlayer.spendCoins(declaredEffect.getCost()); //spend the coins
                }
                
                else if (blockSuccessful && declaredEffect instanceof Assassinate){ //blocking assassinate still spends the coins
                    activePlayer.spendCoins(3);
                }
                
                Game.updatePlayerList();
                roundWinner = Game.getWinner();
                if(!(roundWinner == null)){
                    try {Game.findAnyPlayer(roundWinner).increaseScore();}
                    catch (PlayerNotFoundException e) {e.printStackTrace();}
                }
            }
            
            String cont = Tools.promptInput("Would you like to play another round [y/n]: ",
                                        "Sorry, I didn't get that. Would you like to play another round [y/n]",
                                        yN);
            stillPlaying = cont.toLowerCase().equals("y");
            deck.resetDeck();
            Game.resetActivePlayers();
        }
        
        // Show the player ranks.
        Collections.sort(Game.ALL_PLAYERS);
        Tools.showOnlyMessage("Thanks for playing! Here are the winners...\n", 3);
        for (int i = Game.ALL_PLAYERS.size(); i > 0; i--) {
            Tools.showMessage("\n# " + i + ".", 0.5);
            Tools.showMessage(".", 0.5);
            Tools.showMessage(". ", 0.5);
            Tools.showMessage(Game.ALL_PLAYERS.get(i) + "!", 2);
        }
    }
}
