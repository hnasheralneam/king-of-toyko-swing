public class MyProgram
{
    public static void main(String[] args)
    {
        int aiWins = 0; // out of 10000
        for (int i = 0; i < 20000; i++) {
            Game newGame = new Game(3, false);
            if (newGame.aiWon()) aiWins++;
        }
        System.out.println("AI won " + ((double) aiWins * 100 / 20000) + "% games");
        // Game newGame = new Game(3, true);
    }
}