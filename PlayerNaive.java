public class PlayerNaive extends Player {
    public int id;

    public PlayerNaive(int id) {
        this.playerType = "naive";
        this.id = id;
    }


    // PLAYER DECISION FUNCTIONS
    public boolean[] rerollDice(int currentTurn,
                                int currentPlayer,
                                int inTokyo,
                                int[] dice,
                                int[] playerHealths,
                                int[] playerFames) {
        boolean[] decisions = new boolean[6];
        for (int i = 0; i < dice.length; i++) {
            if (dice[i] == 6) {
                decisions[i] = false;
            }
            else {
                decisions[i] = true;
            }
        }
        return decisions;
    }

    public boolean leaveTokyo(int currentTurn,
                              int currentPlayer,
                              int inTokyo,
                              int[] dice,
                              int[] playerHealths,
                              int[] playerFames) {
        if (playerHealths[id] == 1) return true;
        return false;
    }
}