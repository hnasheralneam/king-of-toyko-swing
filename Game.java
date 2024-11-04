import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Game {
    private State GameState = new State();
    public boolean newToTokyo = true;
    ArrayList<HashMap<Integer, Player>> players = new ArrayList<>(); // references to players
    public boolean firstTime = true; // makes sure you can only get a second turn once
    public boolean hasHumanPlayer = false;
    public boolean hasAIWon;

    // Construct the game
    public Game(int numberOfPlayers, boolean hasPlayer) {
        hasHumanPlayer = hasPlayer;
        // Initialize state
        GameState.setPlayerHealths(new int[numberOfPlayers]);
        GameState.setPlayerFames(new int[numberOfPlayers]);
        GameState.setDice(new int[6]);

        if (hasPlayer == true) {
            // Create a human controlled player
            createPlayer("human");
            numberOfPlayers--;
        }
        else {
            createPlayer("AI");
            numberOfPlayers--;
        }
        // Create AI players to fill up remaining players
        Player naivePlayer;
        for (int i = 0; i < numberOfPlayers; i++) {
            naivePlayer = createPlayer("naive");
            // PlayerNaive_test newTest = new PlayerNaive_test((PlayerNaive) naivePlayer);
        }
        numberOfPlayers++;

        // Choose first player
        GameState.setCurrentPlayer((int) (Math.random() * numberOfPlayers));
        GameState.setInTokyo(GameState.getCurrentPlayer());
        GameState.setCurrentTurn(1);

        // add 1 fame to player who starts in tokyo
        addFame(GameState.getInTokyo(), 1);
        if (hasHumanPlayer) {
            System.out.println("==========================");
            System.out.println("====== Game Started ======");
            System.out.println("==========================");

            System.out.printf("Player %d starts in Tokyo!%n%n", GameState.getInTokyo());
        }
        while (moreThanOnePlayerAlive() && noPlayerAt20Fame()) {
            boolean secondTurn = false;
            if (firstTime == true) {
                GameState.setCurrentTurn(GameState.getCurrentTurn() + 1);
                nextPlayer();
            }
            else {
                firstTime = true;
                secondTurn = true;
            }

            int[] playerRoll = playTurn();
            if (hasHumanPlayer) System.out.println("Player #" + GameState.getCurrentPlayer() + " rolled: " + formattedRoll(playerRoll));

            // Give extra points for staying in tokyo
            if (firstTime && GameState.getCurrentPlayer() == GameState.getInTokyo()) {
                if (!newToTokyo) {
                    if (hasHumanPlayer) System.out.printf("Player %d receives two extra fame for staying a full round in Tokyo!%n", GameState.getInTokyo());
                    addFame(GameState.getInTokyo(), 2);
                }
                else newToTokyo = false;
            }

            if (hasHumanPlayer) printStats();

            if (!secondTurn && count(playerRoll, 4) >= 3) {
                firstTime = false;
            }
        }


        Player winner = determineWinner();
        // printStats();
        if (winner.playerType == "AI") hasAIWon = true;
        if (hasHumanPlayer) System.out.println(winner.playerType + " player has won!");

    }

    public boolean aiWon() {
        return hasAIWon;
    }

    public int[] runTurn(int playerIndex) {
        if (hasHumanPlayer) {
            System.out.println();
            System.out.println("------ Next player's turn (" + playerIndex + ") ------");
            System.out.println("player type - " + getPlayerReference(playerIndex).playerType);
        }
        int[] dice = Game.rollDice(6);

        GameState.setDice(dice);
        int[] diceNumbersToReroll = new int[6];
        int i = 1;
        while (i < 3) {
            if (hasHumanPlayer) {
                System.out.print("Roll #" + i + ": ");
                printRoll(dice);
            }
            boolean[] diceToReroll = getPlayerReference(playerIndex).rerollDice(GameState.getCurrentTurn(),
                    GameState.getCurrentPlayer(),
                    GameState.getInTokyo(),
                    GameState.getDice(),
                    GameState.getPlayerHealths(),
                    GameState.getPlayerFames()
            );
            int keeping = 0;
            for (int j = 0; j < 6; j++) {
                if (diceToReroll[j] == false) {
                    keeping++;
                    diceNumbersToReroll[j] = dice[j];
                }
                else {
                    diceNumbersToReroll[j] = -1;
                }
            }
            dice = Arrays.copyOf(diceNumbersToReroll, diceNumbersToReroll.length);
            int[] newRoll = Game.rollDice(6 - keeping);
            int newRollIndex = 0;
            for (int j = 0; j < 6; j++) {
                if (dice[j] == -1) {
                    dice[j] = newRoll[newRollIndex];
                    newRollIndex++;
                }
            }
            i++;
        }
        if (hasHumanPlayer) {
            System.out.print("Final results - ");
            printRoll(dice);
        }
        return dice;
    }

    private void printRoll(int[] dice) {
        String output = "You rolled: ";
        for (int k = 0; k < dice.length; k++) {
            output += dice[k];
            output += " ";
        }
        System.out.println(output);
    }



    public Player determineWinner() {
        Player winner = players.get(0).get("reference"); // this should never be the final player
        // fame win
        if (noPlayerAt20Fame() == false) {
            for (int i = 0; i < players.size(); i++) {
                if (getFame(i) >= 20) {
                    winner = players.get(i).get("reference");
                }
            }
            // health based win
        } else {
            for (int i = 0; i < players.size(); i++) {
                if (getHealth(i) > 0) {
                    winner = players.get(i).get("reference");
                }
            }
        }
        return winner;
    }

    public int[] playTurn() {
        int[] playerRoll = runTurn(GameState.getCurrentPlayer());
        // decide what to do with roll - more health, damage, fame, or extra turn
        heal(GameState.getCurrentPlayer(), count(playerRoll, 5));
        attack(GameState.getCurrentPlayer(), count(playerRoll, 6));
        // get fame

        for (int i = 1; i < 4; i++) {
            int counted = count(playerRoll, i);

            // No fame for you :(
            if (counted < 3) {
                continue;
            } else {
                // If the player has 4 1s, 5 1s, etc...

                int totalFame = i + (counted - 3);
                addFame(GameState.getCurrentPlayer(), totalFame);
            }

        }
        return playerRoll;
    }


    public Player createPlayer(String playerType) {
        HashMap player = new HashMap();
        Player newPlayer;

        players.add(player);
        int playerIndex = players.size() - 1;

        setHealth(playerIndex, 10);
        setFame(playerIndex, 0);

        if (playerType.equals("human")) {
            newPlayer = new PlayerHuman(playerIndex);
        }
        else if (playerType.equals("AI")) {
            newPlayer = new PlayerAI_placeholder(playerIndex);
        }
        else {
            newPlayer = new PlayerNaive(playerIndex);
        }
        player.put("reference", newPlayer);
        return newPlayer;
    }


    public int count(int[] roll, int target) {
        int res = 0;
        for (int num : roll) {
            if (num == target) {
                res++;
            }
        }
        return res;
    }

    public boolean moreThanOnePlayerAlive() {
        int alive = 0;
        for (int i = 0; i < players.size(); i++) {
            if (getHealth(i) > 0) {
                alive++;
            }
        }
        if (alive > 1) {
            return true;
        } else {
            return false;
        }
    }

    public boolean noPlayerAt20Fame() {
        for (int i = 0; i < players.size(); i++) {
            if (getFame(i) >= 20) {
                return false;
            }
        }
        return true;
    }

    public void nextPlayer() {
        firstTime = true;

        int newCurrentPlayer = GameState.getCurrentPlayer() + 1;
        if (newCurrentPlayer > players.size() - 1) {
            newCurrentPlayer = 0;
        }
        GameState.setCurrentPlayer(newCurrentPlayer);
        if (getHealth(newCurrentPlayer) <= 0) nextPlayer();
    }



    public void attack(int playerIndex, int damage) {
        if (GameState.getCurrentPlayer() != GameState.getInTokyo()) {
            hurt(GameState.getInTokyo(), damage);
            boolean leave = players.get(GameState.getInTokyo()).get("reference").leaveTokyo(GameState.getCurrentTurn(),
                    GameState.getCurrentPlayer(),
                    GameState.getInTokyo(),
                    GameState.getDice(),
                    GameState.getPlayerHealths(),
                    GameState.getPlayerFames());
            if (leave) {
                if (hasHumanPlayer) {
                    System.out.printf("The monster in Tokyo has left, Player %d is now in Tokyo!%n", GameState.getInTokyo());
                }
                moveActivePlayerToTokyo();
            }
        }
        else {
            for (int i = 0; i < players.size(); i++) {
                if (GameState.getCurrentPlayer() != GameState.getInTokyo()) {
                    hurt(i, damage);
                }
            }
        }
    }


    public void addFame(int playerIndex, int amount) {
        setFame(playerIndex, getFame(playerIndex) + amount);
    }

    public void hurt(int playerIndex, int damage) {
        int newHealth = getHealth(playerIndex) - damage;
        setHealth(playerIndex, newHealth);
        if (newHealth <= 0) {
            // player was killed, give attacker fame
            if (hasHumanPlayer) System.out.println("Player " + playerIndex + " was killed at " + (newHealth + damage) + " health and " + getFame(playerIndex) + " fame.");
            addFame(GameState.getCurrentPlayer(), 1);
            if (playerIndex == GameState.getInTokyo()) {
                moveActivePlayerToTokyo();
            }
        }
    }

    public void moveActivePlayerToTokyo() {
        GameState.setInTokyo(GameState.getCurrentPlayer());
        // give new tokyo player 1 fame for being added to tokyo
        addFame(GameState.getInTokyo(), 1);
        newToTokyo = true;
    }

    public void heal(int playerIndex, int amount) {
        int currentHealth = getHealth(playerIndex);
        int newHealth = currentHealth + amount;
        if (playerIndex == GameState.getInTokyo()) return;
        if (newHealth >= 12) setHealth(playerIndex, 12);
        else setHealth(playerIndex, newHealth);
    }

    public static int[] rollDice(int numOfDice) {
        // generates randomly
        int[] roll = new int[numOfDice];
        for (int i = 0; i < numOfDice; i++) {
            roll[i] = (int) (Math.random() * 6) + 1;
        }
        return roll;
    }

    public void printStats() {
        System.out.println("____________");

        for (int i = 0; i < players.size(); i++) {
            if (i == GameState.getCurrentPlayer()) {
                System.out.print("> ");
            }
            if (i == GameState.getInTokyo()) {
                System.out.print("[IN TOKYO] ");
            }
            System.out.print("Player index: " + i);
            System.out.print(" - Health: " + (int) getHealth(i));
            if ((int) getHealth(i) <= 0) {
                System.out.print(" (DEAD) ");
            }
            System.out.println(" - Fame: " + (int) getFame(i));
        }
    }

    private String formattedRoll(int[] dice) {
        String output = "";
        for (int k = 0; k < dice.length; k++) {
            output += dice[k];
            output += " ";
        }
        return output;
    }

    // Getters
    public int getHealth(int playerIndex) {
        return GameState.getPlayerHealths()[playerIndex];
    }

    public int getFame(int playerIndex) {
        return GameState.getPlayerFames()[playerIndex];
    }

    public Player getPlayerReference(int playerIndex) {
        return players.get(playerIndex).get("reference");
    }

    // Setters
    public void setHealth(int playerIndex, int newHealth) {
        int[] playerHealths = GameState.getPlayerHealths();
        playerHealths[playerIndex] = newHealth;
        GameState.setPlayerHealths(playerHealths);
    }

    public void setFame(int playerIndex, int newFame) {
        int[] playerFames = GameState.getPlayerFames();
        playerFames[playerIndex] = newFame;
        GameState.setPlayerFames(playerFames);
    }
}