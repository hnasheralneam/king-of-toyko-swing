public class PlayerAI_placeholder extends Player {
    public int id;

    public PlayerAI_placeholder(int id) {
        this.playerType = "AI";
        this.id = id;
    }

    public boolean[] rerollDice(int currentTurn,
                                int currentPlayer,
                                int inTokyo,
                                int[] dice,
                                int[] playerHealths,
                                int[] playerFames) {

        boolean[] decisions = new boolean[6]; // false = keep; true = reroll
        // decide pair to go for
        // true means reroll, false means keep in this array
        boolean[] targets = {true, true, true, true};
        // if focused is true, "inferior" rolls will only be kept if
        // they are three of a kind already
        boolean focused = false;
        for (int i = 4; i > 0; i--) {
            // dont even bother selecting if there isn't at least 2
            if (getInstances(dice, i) >= 2) {
                // no targeted rolls yet
                if (focused == false) {
                    targets[i-1] = false;
                    focused = true;
                } else {
                    // already targeted! check if 3+
                    if (getInstances(dice,i) >=3) {
                        targets[i-1] = false;
                    }
                }
            }
        }
        if (focused == false) {
            targets[3] = false;
        }
        if (getInstances(dice, 4) >= 3) {
            targets[3] = false;
            // System.out.println("Bonus round coming up!");
        }
        int foursCounted = 0;
        for (int i = 0; i < dice.length; i++) {
            switch (dice[i]) {
                case 4:
                    if (foursCounted < 3) {
                        decisions[i] = targets[3];
                        foursCounted++;
                    } else {
                        decisions[i] = true;
                    }
                    break;
                case 6:
                    decisions[i] = false;
                    break;
                case 5:
                    decisions[i] = needToHeal(inTokyo, playerHealths[currentPlayer], playerFames[currentPlayer]) ? false : true;
                    break;
                default:
                    // reroll by default
                    // if is score dice
                    if (dice[i] < 4) {
                        decisions[i] = targets[dice[i]-1];
                    } else {
                        decisions[i] = true;
                    }
                    break;
            }
        }

        // previousTurn = currentTurn;
        return decisions;
    }

    public boolean needToHeal(int playerInTokyo, int health, int fame) {
        if (id != playerInTokyo) {
            if (health < 6 && fame < 17) return true;
            else if (health < 4 && fame >= 17) return true;
        }
        return false;
    }

    public boolean leaveTokyo(int currentTurn,
                              int currentPlayer,
                              int inTokyo,
                              int[] dice,
                              int[] playerHealths,
                              int[] playerFames) {
        if (playerHealths[id] < 6) return true;
        else return false;
    }
    private int getInstances(int[] dice, int index) {
        int res = 0;
        for (int i : dice) {
            if (i == index) {
                res++;
            }
        }
        return res;
    }
}