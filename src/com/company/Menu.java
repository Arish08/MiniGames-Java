package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.AttributedCharacterIterator;

public class Menu {
	private JFrame frame;
	private JPanel panel1;
	private JRadioButton ticTacToeRadioButton;
	private JRadioButton guessTheWordRadioButton;
	private JButton playButton;
	private JLabel imgLabel;

	Menu() throws IOException {
		// Initialise our window
		frame = new JFrame("Mini games");
		frame.setMinimumSize(new Dimension(800, 400));
		frame.setSize(800, 400);
		frame.setContentPane(panel1);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		imgLabel.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\logo.png"));

		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (ticTacToeRadioButton.isSelected()) {
					new TicTacToe();
				} else if (guessTheWordRadioButton.isSelected()) {
					new GameView();
				} else {
					JOptionPane.showMessageDialog(null, "No mini-game selected!");
				}
			}
		});
	}
}
