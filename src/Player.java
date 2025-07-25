/**
 * The player class - it stores the player information for the "Stuck in the Mud" game
 */
public class Player {
    /**
     * Name of the player
     */
    private String name;
    /**
     * Score of the player
     */
    private int score;

    /**
     * Creates a player
     * @param name name of the player
     */
    public Player(String name) {
        if (name.isBlank()){
            throw new IllegalArgumentException("name cannot be blank");
        }

        setName(name);
        score = 0;
    }


    /**
     * Gets the player's name
     * @return the player's name
     */
    public String getName(){
        return name;
    }


    /**
     * Gets the player's score
     * @return the player's score
     */
    public int getScore(){
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Adds the player's round total to the total score
     * @param roundTotal the player's total of the round
     */
    public void addRoundTotal(int roundTotal){
        score += roundTotal;
    }
}