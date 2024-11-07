import java.awt.Dimension;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;


public class MyProgram implements ActionListener {
    static ActionListener taskPerformer;

    JFrame frame;
    JPanel contentPane, outputPane;

    int gamesAmount, playersAmount;
    boolean individualGameResults, pauseBetweenGames;

    JRadioButton yesIndividualGameResults;
    JRadioButton noIndividualGameResults;
    ButtonGroup individualGameResultsGroup;
    JLabel individualGameResultsLabel;

    JRadioButton yesPauseBetweenGames;
    JRadioButton noPauseBetweenGames;
    ButtonGroup pauseBetweenGamesGroup;
    JLabel pauseBetweenGamesLabel;


    String[] playerTypes;

    JLabel titleLabel, descriptionLabel, gamesLabel, playersLabel, resultsLabel;
    JButton playerLockInButton, runButton;
    JCheckBox individualStatsCheckBox, pauseCheckBox;
    JTextField gamesInput, playersInput;
    String[] selections = {"Naive", "placeholder (the best)", "Kirby", "TAIbleFlip"};

    public MyProgram() {
        frame = new JFrame("King of Tokyo Simulation GUI");
        frame.setPreferredSize(new Dimension(380, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        // input frame: get inputs n stuff
        contentPane = new JPanel();
        titleLabel = new JLabel("King of Tokyo Game Simulator");
        descriptionLabel = new JLabel("Run vast amounts of games and get the stats!");
        descriptionLabel.setFont(descriptionLabel.getFont().deriveFont(50));
        gamesLabel = new JLabel("How many games do you want?");
        playersLabel = new JLabel("How many players do you want?");

        gamesInput = new JTextField(10);
        playersInput = new JTextField(10);

        runButton = new JButton("Let's go!");
        runButton.setActionCommand("Run");
        runButton.addActionListener(this);
        

        playerLockInButton = new JButton("Lock in players");
        playerLockInButton = new JButton("PlayerLockIn");
        playerLockInButton.addActionListener(this);        

        individualStatsCheckBox = new JCheckBox("Do you want individual game stats?");
        individualStatsCheckBox.addActionListener(this);

        pauseCheckBox = new JCheckBox("Do you want to add a pause between games?");
        pauseCheckBox.addActionListener(this);


        contentPane.add(titleLabel);
        contentPane.add(descriptionLabel);
        contentPane.add(new JLabel("                                                                                                              "));
        contentPane.add(gamesLabel);
        contentPane.add(gamesInput);
        contentPane.add(playersInput);
        contentPane.add(playersLabel);
        contentPane.add(playersInput);


        individualGameResultsLabel = new JLabel("Do you want individual game results or total stats?");
        yesIndividualGameResults = new JRadioButton("individual results");
        noIndividualGameResults = new JRadioButton("total stats");
        individualGameResultsGroup = new ButtonGroup();
        individualGameResultsGroup.add(yesIndividualGameResults);
        individualGameResultsGroup.add(noIndividualGameResults);

        pauseBetweenGamesLabel = new JLabel("Do you want to pause between games?                              ");
        yesPauseBetweenGames = new JRadioButton("                            Yes");
        noPauseBetweenGames = new JRadioButton("No                                   ");
        pauseBetweenGamesGroup = new ButtonGroup();
        pauseBetweenGamesGroup.add(yesPauseBetweenGames);
        pauseBetweenGamesGroup.add(noPauseBetweenGames);

        contentPane.add(individualGameResultsLabel);
        contentPane.add(yesIndividualGameResults);
        contentPane.add(noIndividualGameResults);
        contentPane.add(pauseBetweenGamesLabel);
        contentPane.add(yesPauseBetweenGames);
        contentPane.add(noPauseBetweenGames);
        contentPane.add(runButton);

        outputPane = new JPanel();
        resultsLabel = new JLabel();
        outputPane.add(resultsLabel);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
        System.out.println("action taken: " + eventName);
        
        if (eventName.equals("Run")) {
            gamesAmount = Integer.parseInt(gamesInput.getText());
            playersAmount = Integer.parseInt(playersInput.getText());
            individualGameResults = yesIndividualGameResults.isSelected();
            pauseBetweenGames = yesPauseBetweenGames.isSelected();
            getPlayerTypes();
            runSimulation();
            if (!individualGameResults) {
                JButton playAgainButton = new JButton("Run another simulation!");
                playAgainButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setContentPane(contentPane);
                        frame.pack();
                        frame.setVisible(true);
                    }
                });
                outputPane.add(playAgainButton);

                frame.setContentPane(outputPane);
                frame.pack();
                frame.setVisible(true);
            }
        }

    }

    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        new MyProgram();
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                runGUI();
            }
        });
    }

    public void runSimulation() {
        final JFrame parent = new JFrame();
        parent.pack();
        parent.setVisible(true);

        int[] wins = new int[playerTypes.length]; // tracks the number of wins each player gets
        for (int i = 0; i < gamesAmount; i++) {
            Game newGame = new Game(playerTypes);
            wins[newGame.getWinner()]++;
            
            if (individualGameResults) {
                JOptionPane.showMessageDialog(parent, newGame.getFinalStats());
                if (pauseBetweenGames) {
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (!individualGameResults) {
            String message = "<html>";

            for (int i = 0; i < playersAmount; i++) {
                message += "Player " + i + " (" + playerTypes[i] + ") won " + wins[i] + " (" + Math.round(((double) wins[i] * 100 / gamesAmount) * 100) / 100.0 + "%) games<br>";
            }
            message += "</html>";
        
            resultsLabel.setText(message);     
        }
    } 

    public void getPlayerTypes() {
        playerTypes = new String[playersAmount];
        for (int i = 0; i < playersAmount; i++) {
            int response = JOptionPane.showOptionDialog(null, "Select player type for player " + (i + 1),
            "Player Type Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
            selections, "Naive");
            playerTypes[i] = selections[response];
        }
    }
}
