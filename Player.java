public abstract class Player {
    public String playerType;
    public int id;
    public Game game;
    public State GameState;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // PLAYER DECISION FUNCTIONS
    public abstract boolean[] rerollDice(int currentTurn,
                                         int currentPlayer,
                                         int inTokyo,
                                         int[] dice,
                                         int[] playerHealths,
                                         int[] playerFames);
    public abstract boolean leaveTokyo(int currentTurn,
                                       int currentPlayer,
                                       int inTokyo,
                                       int[] dice,
                                       int[] playerHealths,
                                       int[] playerFames);

}