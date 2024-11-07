import java.util.Scanner;

public class PlayerHuman extends Player {
    public int id;


    public PlayerHuman(int id) {
        this.playerType = "human";
        this.id = id;
    }

    // PLAYER DECISION METHODS
    @Override
    public boolean[] rerollDice(int currentTurn,
                                int currentPlayer,
                                int inTokyo,
                                int[] dice,
                                int[] playerHealths,
                                int[] playerFames) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write the index of each die you want to keep \n(0 to reroll all, 7 to keep all)");
        String input = scanner.nextLine();
        scanner.close();

        boolean[] results = new boolean[] {true, true, true, true, true, true};
        switch (input) {
            case "0" -> // reroll all
                results = new boolean[] {true, true, true, true, true, true};
            case "7" -> // keep all
                results = new boolean[] {false, false, false, false, false, false};
            default -> {
                // everything else
                String[] resultsArray = input.split(" ");
                for (String answer : resultsArray) {
                    if (Integer.parseInt(answer) >= 0 && Integer.parseInt(answer) <= 7) {
                        results[Integer.parseInt(answer) - 1] = false;
                    }
                    else {
                        return rerollDice(currentTurn, currentPlayer, inTokyo, dice, playerHealths, playerFames);
                    }
                }
            }
        }

        return results;
    }

    @Override
    public boolean leaveTokyo(int currentTurn,
                              int currentPlayer,
                              int inTokyo,
                              int[] dice,
                              int[] playerHealths,
                              int[] playerFames) {
        int health = playerHealths[id];
        int fame = playerFames[id];

        Scanner scanner = new Scanner(System.in);
        System.out.println("!!! Player in Tokyo - you've been attacked !!!");
        System.out.println("You have " + health + " health and " + fame + " fame. Do you want to leave Tokyo? (y/n)");
        String input = scanner.nextLine();
        scanner.close();
        if (input.toLowerCase().equals("y")) {
            System.out.println("You want to leave Tokyo!");
            return true;
        }
        else if (input.toLowerCase().equals("n")) {
            System.out.println("You don't want to leave Tokyo!");
            return false;
        }

        System.out.println("Invalid input - try again");
        return leaveTokyo(currentTurn, currentPlayer, inTokyo, dice, playerHealths, playerFames);
    }
}