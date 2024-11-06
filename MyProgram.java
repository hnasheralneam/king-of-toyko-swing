import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class MyProgram implements ActionListener {
    JFrame frame;
    JPanel contentPane, outputPane;

    JLabel titleLabel, descriptionLabel, gamesLabel, playersLabel, indiTotalStatsLabel, pauseLabel;
    JButton playerLockInButton, runButton;
    JCheckBox individualStatsCheckBox, pauseCheckBox;
    boolean individualStats, pause;
    JTextField gamesInput, playersInput;
    JComboBox<String> selectionBox;
    String[] selections = {"Select...", "Naive", "AI"};

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
        indiTotalStatsLabel = new JLabel("Do you want individual stats?");
        pauseLabel = new JLabel("Do you want a pause between games?");

        gamesInput = new JTextField(10);
        playersInput = new JTextField(10);

        runButton = new JButton("Run!");
        runButton.setActionCommand("Run");
        runButton.addActionListener(this);
        contentPane.add(runButton);

        playerLockInButton = new JButton("Lock in players");
        playerLockInButton = new JButton("PlayerLockIn");
        playerLockInButton.addActionListener(this);
        contentPane.add(playerLockInButton);

        individualStatsCheckBox = new JCheckBox("Do you want individual game stats?");
        individualStatsCheckBox.addActionListener(this);

        pauseCheckBox = new JCheckBox("Do you want to add a pause between games?");
        pauseCheckBox.addActionListener(this);


        contentPane.add(titleLabel);
        contentPane.add(descriptionLabel);
        contentPane.add(new JLabel("                                                                                                              "));
        contentPane.add(gamesLabel);
        contentPane.add(gamesInput);
        contentPane.add(playersLabel);
        contentPane.add(playersInput);



        frame.setContentPane(contentPane);
        frame.pack();
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent event) {
        String eventName = event.getActionCommand();
        System.out.println("action taken: " + eventName);
        
        if (eventName.equals("Run")) {
            gamesAmount = Integer.parseInt(gamesInput.getText());
            // TO-DO: run Game instance
        }

    } // end action performed STUFF


    //TO-DO: checkbox validation
    public void itemStateChanged(ItemEvent e) { 
        // if the state of individual/total stats is changed 
        if (e.getSource() == individualStaindividualStats = true;t
                inindividualStats = false;d
                 } 
  
        // if pause status changed        teChange() ==    // yes pause
                papause = false;u            else
                // no pause
      // end item state STUFF alkfjaklkljekqlgkfl           pause = false;
        } 
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