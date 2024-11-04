import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class MyProgram implements ActionListener {
    JFrame frame;
    JPanel contentPane, outputPane;

    JLabel titleLabel, descriptionLabel, gamesLabel, playersLabel, forLoopDescription, indiTotalStatsLabel, pauseLabel;
    JButton playerLockInButton, runButton;
    JCheckBox individualStatsCheckBox, totalStatsCheckBox, yesPauseCheckBox, noPauseCheckBox;
    JTextField gamesInput, playersInput;
    JComboBox<String> selectionBox;

    public MyProgram() {
        frame = new JFrame("King of Tokyo Simulation GUI");
        frame.setPreferredSize(new Dimension(400, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        
        // input frame: get inputs n stuff
        contentPane = new JPanel();
        titleLabel = new JLabel("King of Tokyo Game Simulator");
        descriptionLabel = new JLabel("Run vast amounts of games and get the stats!");
        gamesLabel = new JLabel("How many games do you want?");
        playersLabel = new JLabel("How many players do you want?");

        gamesInput = new JTextField(10);
        playersInput = new JTextField(10);

        contentPane.add(titleLabel);
        contentPane.add(descriptionLabel);
        contentPane.add(new JLabel("                                                                                                              "));
        contentPane.add(gamesLabel);
        contentPane.add(gamesInput);
        contentPane.add(playersLabel);
        contentPane.add(playersInput);

        playerLockInButton = new JButton("Lock in players");
        playerLockInButton.addActionListener(this);
        contentPane.add(playerLockInButton);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
        System.out.println("action taken: " + eventName);
    }

    private static void runGUI() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        MyProgram greeting = new MyProgram();
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                runGUI();
            }
        });


        int games = 20000;
        int players = 3;

        int aiWins = 0; // out of 10000
        for (int i = 0; i < games; i++) {
            Game newGame = new Game(players, false);
            if (newGame.aiWon()) aiWins++;
        }
        String message = "AI won " + ((double) aiWins * 100 / games) + "% games";
        
        // Popup
        final JFrame parent = new JFrame();
    
        parent.pack();
        parent.setVisible(true);

        JOptionPane.showMessageDialog(parent, message);
    }
}



/*
 * // Popup
        final JFrame parent = new JFrame();
        JButton button = new JButton();
    
        button.setText("Click me to show dialog!");
        parent.add(button);
        parent.pack();
        parent.setVisible(true);
    
        button.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String name = JOptionPane.showInputDialog(parent,
                        "What is your name?", null);
            }
        });
 */