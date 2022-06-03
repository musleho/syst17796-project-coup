package Game;
import Effects.*;
import java.util.Hashtable;
import Exceptions.InvalidNameException;


public class Card {
    public static final String[] validNames = {"Ambassador", "Assassin", "Captain", "Contessa", "Duke"};
    private String name;
    private Effect effect;
    private String counteraction;

    public Card (String name) throws InvalidNameException{
        if(isValidName(name)) {
            this.name = name;
            switch(name) {
                case "Ambassador":
                    this.effect = new Exchange();
                    this.counteraction = "block steal";
                    break;
                case "Assassin":
                    this.effect = new Assassinate();
                    this.counteraction = "";
                    break;
                case "Captain":
                    this.effect = new Steal();
                    this.counteraction = "block steal";
                    break;
                case "Contessa":
                    this.effect = new Income(); //placeholder since Contessa has no effect
                    this.counteraction = "block assassinate";
                    break;
                case "Duke":
                    this.effect = new Tax();
                    this.counteraction = "block foreign aid";
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

    public Effect getEffect(){return this.effect;}

    public String getCounteraction(){return this.counteraction;}
}
