package Game;
import Exceptions.InvalidNameException;


public class Card {
    public static final String[] validNames = {"Ambassador", "Assassin", "Captain", "Contessa", "Duke"};
    private String name;
    private String effect;
    private String counteraction;

    public Card (String name) throws InvalidNameException{
        if(isValidName(name)) {
            this.name = name;
            switch(name) {
                case "Ambassador":
                    this.effect = "exchange";
                    this.counteraction = "block steal";
                    break;
                case "Assassin":
                    this.effect = "assassinate";
                    this.counteraction = "N/A";
                    break;
                case "Captain":
                    this.effect = "steal";
                    this.counteraction = "block steal";
                    break;
                case "Contessa":
                    this.effect = "N/A";
                    this.counteraction = "block assassinate";
                    break;
                case "Duke":
                    this.effect = "tax";
                    this.counteraction = "block foreign aid";
                    break;
            }
        }
        else throw new InvalidNameException();
    }

    //private method for use in the constructor
    private boolean isValidName(String name){
        for(String validName : validNames) {
            if(name.equals(validName)){
                return true;
            }
        }
        return false;
    }

    public String getName() {return this.name;}

    public String getEffect(){return this.effect;}

    public String getCounteraction(){return this.counteraction;}

    public String toString() {
        return "Name: " + this.name + "\n"
             + "Effect: " + this.effect + "\n"
             + "Counteraction: " + this.counteraction + "\n";
    }

    public boolean equals(Card card){
        return card.name.equals(this.name);
    }
}