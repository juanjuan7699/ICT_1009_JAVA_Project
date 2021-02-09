package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Game1 {

		JFrame window, instructionScreenFrame;
		Container con; //base
		JPanel titleNamePanel, startButtonPanel, quitButtonPanel, instructionButtonPanel, leaderboardButtonPanel, p1ButtonPanel, p1p2ButtonPanel;
		JPanel isp, isp2, isp3, isp4, imagePanel, lastButtonPanel;
		JLabel titleNameLabel, isl, isl2, isl3, isl4, imageLabel;
		Font titleFont = new Font("Times New Roman", Font.PLAIN, 70);
		Font buttonFont = new Font("Times New Roman", Font.PLAIN, 28);
		JButton startButton, quitButton, instructionButton, leaderboardButton, p1Button, p1p2Button;
		JButton backButton;
		JTextArea instructionTextArea, instructionTextArea2, instructionTextArea3;

		
		instructionHandler insHandler = new instructionHandler();
		
		public static void main(String[] args) {
			
			new Game1();
			
		}
		
		public Game1() {
//			
//			ImageIcon jungle = new ImageIcon(this.getClass().getResource("C:\\Users\\tshuj\\Desktop\\jungle_java.png"));
//			imageLabel = new JLabel(jungle);
//			imageLabel.setSize(500, 500);
			
			window = new JFrame();
			//window.add(imageLabel);
			window.setTitle("Animal Hunter");
			window.setSize(1500, 800); // x, y
			// full screen
			//window.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			//window.setUndecorated(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			window.setLocationRelativeTo(null);
			window.getContentPane().setBackground(Color.cyan);
			//window.setLayout(new BorderLayout());
			window.setVisible(true);
			con = window.getContentPane();
			
//			window.setContentPane(new JLabel(new ImageIcon("C:\\Users\\tshuj\\Desktop\\jungle_java.png")));
//			window.setLayout(new FlowLayout());
//			imageLabel.setVisible(true);
			
//			ImageIcon jungle = new ImageIcon("jungle_java.jpg");
//			imageLabel = new JLabel(jungle);
//			imagePanel = new JPanel();
//			imagePanel.setBounds(0, 0, 1500, 800); // x, y
//			//imagePanel.setBounds(null);
//			//imagePanel.setExtenedState(JPanel.MAXIMIZED_BOTH);
//			imagePanel.add(imageLabel);
//			window.getContentPane().add(imagePanel);
			
			titleNamePanel = new JPanel();
			titleNamePanel.setBounds(450, 100, 600, 100); //startpoint(x, y), x-width, height
			titleNamePanel.setBackground(Color.black);
			
			titleNameLabel = new JLabel("Animal Hunter 2k21");
			titleNameLabel.setForeground(Color.yellow);
			titleNameLabel.setVisible(true);
			
//			startButtonPanel = new JPanel();
//			startButtonPanel.setBounds(650, 300, 200, 50); //startpoint(x, y), x-width, height
//			startButtonPanel.setBackground(Color.black);
//			
//			startButton = new JButton("Start");
//			startButton.setBackground(Color.pink);
//			startButton.setForeground(Color.black);
//			startButton.setFont(buttonFont); //add font to button
//			startButton.setBounds(650, 300, 200, 50); //startpoint(x, y), x-width, height
//			con.add(startButton); // add button to frame
			
			p1ButtonPanel = new JPanel();
			p1ButtonPanel.setBounds(500, 300, 200, 50); //startpoint(x, y), x-width, height
			p1ButtonPanel.setBackground(Color.black);
			
			p1Button = new JButton("Single Player");
			p1Button.setBackground(Color.pink);
			p1Button.setForeground(Color.black);
			p1Button.setFont(buttonFont); //add font to button
			
			p1p2ButtonPanel = new JPanel();
			p1p2ButtonPanel.setBounds(790, 300, 200, 50); //startpoint(x, y), x-width, height
			p1p2ButtonPanel.setBackground(Color.black);
			
			p1p2Button = new JButton("Multi-Player");
			p1p2Button.setBackground(Color.magenta);
			p1p2Button.setForeground(Color.black);
			p1p2Button.setFont(buttonFont); //add font to button
			
			instructionButtonPanel = new JPanel();
			instructionButtonPanel.setBounds(650, 400, 200, 50); //startpoint(x, y), x-width, height
			instructionButtonPanel.setBackground(Color.black);
			
			instructionButton = new JButton("How To Play");
			instructionButton.setBackground(Color.orange);
			instructionButton.setForeground(Color.black);
			instructionButton.setFont(buttonFont); //add font to button
//			instructionButtonPanel.setBounds(650, 500, 200, 50); //startpoint(x, y), x-width, height
//			con.add(instructionButton); // add button to frame
			
			instructionButton.addActionListener(insHandler);
			
			leaderboardButtonPanel = new JPanel();
			leaderboardButtonPanel.setBounds(650, 500, 200, 50); //startpoint(x, y), x-width, height
			leaderboardButtonPanel.setBackground(Color.black);
			
			leaderboardButton = new JButton("LeaderBoard");
			leaderboardButton.setBackground(Color.cyan);
			leaderboardButton.setForeground(Color.black);
			leaderboardButton.setFont(buttonFont); //add font to button
//			leaderboardButtonPanel.setBounds(650, 600, 200, 50); //startpoint(x, y), x-width, height
//			con.add(leaderboardButton); // add button to frame
			
			quitButtonPanel = new JPanel();
			quitButtonPanel.setBounds(650, 600, 200, 50); //startpoint(x, y), x-width, height
			quitButtonPanel.setBackground(Color.black);
			
			quitButton = new JButton("Quit");
			quitButton.setBackground(Color.red);
			quitButton.setForeground(Color.black);
			quitButton.setFont(buttonFont); //add font to button
//			quitButtonPanel.setBounds(650, 400, 200, 50); //startpoint(x, y), x-width, height
//			con.add(quitButton); // add button to frame
//			quitButton.setVisible(true);
			
			// close the program when quit is pressed
			quitButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e)
			    {
			        System.exit(0);
			    }
			});
			
			lastButtonPanel = new JPanel();
			lastButtonPanel.setVisible(false);
			
			titleNamePanel.add(titleNameLabel); // add label to panel
			titleNameLabel.setFont(titleFont); // add font to label
//			startButtonPanel.add(startButton); // add start button to panel
			p1ButtonPanel.add(p1Button); // add button to panel
			p1p2ButtonPanel.add(p1p2Button); // add button to panel
			quitButtonPanel.add(quitButton); // add quit button to panel
			instructionButtonPanel.add(instructionButton); //add instruction button to panel
			leaderboardButtonPanel.add(leaderboardButton); //add leaderboard button to panel
			
			con.add(titleNamePanel); //add title panel to the frame
//			con.add(startButtonPanel); //add start panel to the frame
			con.add(p1ButtonPanel); // add panel to frame
			con.add(p1p2ButtonPanel); // add panel to frame
			con.add(quitButtonPanel); // add quit panel to the frame
			con.add(instructionButtonPanel); //add instruction panel to the frame
			con.add(leaderboardButtonPanel); //add leaderboard panel to the frame	
			con.add(lastButtonPanel);
		}

		public void instructionScreen() {
			
			window.setVisible(false);	
			
			instructionScreenFrame = new JFrame("How To Play");
			instructionScreenFrame.setSize(1500, 800); // x, y
			// full screen
			//instructionScreenFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
			//instructionScreenFrame.setUndecorated(true);
			instructionScreenFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			instructionScreenFrame.getContentPane().setBackground(Color.black);
			instructionScreenFrame.setLayout(null);
			instructionScreenFrame.setVisible(true);
			con = instructionScreenFrame.getContentPane();
			//con.add(instructionScreenFrame);
			
			isp = new JPanel();
			isp.setBounds(100, 50, 600, 100);
			isp.setBackground(Color.black);
			con.add(isp); // add panel to frame
			
			isl = new JLabel("How To Play");
			isl.setForeground(Color.yellow);
			isl.setVisible(true);
			isl.setFont(titleFont); // add font to label
			isp.add(isl); // add label to panel
			
			isp2 = new JPanel();
			isp2.setBounds(100, 200, 300, 200);
			isp2.setBackground(Color.black);
			con.add(isp2); // add panel to frame
			
			instructionTextArea = new JTextArea("Single Player\n" + "\nMove Left: Left Arrow\n" + "Move Right: Right Arrow\n" + "Shoot: Spacebar");
			instructionTextArea.setBounds(100, 200, 300, 200);
			instructionTextArea.setBackground(Color.black);
			instructionTextArea.setForeground(Color.yellow);
			instructionTextArea.setFont(buttonFont);
			instructionTextArea.setLineWrap(true);
			instructionTextArea.setEditable(false);
			
			isp2.add(instructionTextArea); // add textarea to panel
			
			isp3 = new JPanel();
			isp3.setBounds(500, 200, 250, 200);
			isp3.setBackground(Color.black);
			con.add(isp3); // add panel to frame
			
			instructionTextArea2 = new JTextArea("Multiplayer\n" + "\nPlayer 1\n" + "Move Left: 'A'\n" + "Move Right: 'D'\n" + "Shoot: Spacebar");
			instructionTextArea2.setBounds(500, 200, 250, 200);
			instructionTextArea2.setBackground(Color.black);
			instructionTextArea2.setForeground(Color.yellow);
			instructionTextArea2.setFont(buttonFont);
			instructionTextArea2.setLineWrap(true);
			instructionTextArea2.setEditable(false);
			
			isp3.add(instructionTextArea2); // add textarea to panel
			
			isp4 = new JPanel();
			isp4.setBounds(750, 200, 300, 200);
			isp4.setBackground(Color.black);
			con.add(isp4); // add panel to frame
			
			instructionTextArea3 = new JTextArea("\n\nPlayer 2\n" + "Move Left: Left Arrow\n" + "Move Right: Right Arrow\n" + "Shoot: Enter key");
			instructionTextArea3.setBounds(750, 200, 300, 200);
			instructionTextArea3.setBackground(Color.black);
			instructionTextArea3.setForeground(Color.yellow);
			instructionTextArea3.setFont(buttonFont);
			instructionTextArea3.setLineWrap(true);
			instructionTextArea3.setEditable(false);
			
			isp4.add(instructionTextArea3); // add textarea to panel
			
			backButton = new JButton("Back");
			backButton.setBackground(Color.red);
			backButton.setForeground(Color.black);
			backButton.setBounds(650, 600, 200, 50); //startpoint(x, y), x-width, height
			backButton.setFont(buttonFont); //add font to button
			con.add(backButton); // add button to frame
			
			// back to menu page 
			backButton.addActionListener(new ActionListener() {
			    public void actionPerformed(ActionEvent e)
			    {
			        //System.exit(0);
			        instructionScreenFrame.dispose();
			        window.setVisible(true);
			    }
			});
		}
		
		public class instructionHandler implements ActionListener{
			
			public void actionPerformed(ActionEvent event) {
				
				instructionScreen();
			}
		}
}

