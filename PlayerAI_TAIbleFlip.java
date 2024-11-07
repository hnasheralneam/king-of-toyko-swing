public class PlayerAI_TAIbleFlip extends Player {
    public int id;
    private int lastTurnMoved = -1;
    
public PlayerAI_TAIbleFlip(int id) {
    this.playerType = "TAIbleFlip";
    this.id = id;
}    

public boolean[] rerollDice(State s) {
boolean onSecondTurn = false;
if (this.lastTurnMoved == s.getCurrentTurn()) {
onSecondTurn = true;
}
this.lastTurnMoved = s.getCurrentTurn();

int[] dice = s.getDice();
boolean[] reroll = new boolean[6];
if (s.getPlayerFames()[s.getCurrentPlayer()] > 16) {
for (int i = 0; i < dice.length; i++) {
if (dice[i] != 3) {
reroll[i] = true;
}
}
}
for (int i = 0; i < dice.length; i++) {
if (dice[i] != 6 && (s.getInTokyo() == id || dice[i] != 5)) {
reroll[i] = true;
}
}
return reroll;
}
/* REWRITTEN */
public boolean[] rerollDice(int currentTurn, int currentPlayer, int inTokyo, int[] dice, int[] playerHealths, int[] playerFames) {
State s = new State();
s.setCurrentTurn(currentTurn);
s.setCurrentPlayer(currentPlayer);
s.setInTokyo(inTokyo);
s.setDice(dice);
s.setPlayerHealths(playerHealths);
s.setPlayerFames(playerFames);
return rerollDice(s);
}

public boolean leaveTokyo(State s) {
if (s.getPlayerFames()[s.getCurrentPlayer()] > -1) {
return false;
}
return true;
}

public boolean leaveTokyo(int currentTurn, int currentPlayer, int inTokyo, int[] dice, int[] playerHealths, int[] playerFames) {
State s = new State();
s.setCurrentTurn(currentTurn);
s.setCurrentPlayer(currentPlayer);
s.setInTokyo(inTokyo);
s.setDice(dice);
s.setPlayerHealths(playerHealths);
s.setPlayerFames(playerFames);
return leaveTokyo(s);
}

public void setId(int id) {
this.id = id;
}

public int getId() {
return this.id;
}
}