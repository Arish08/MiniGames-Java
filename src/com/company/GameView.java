package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Random;

public class GameView extends Game {
	private JFrame frame = new JFrame("Hangman");
	private JPanel rootPanel;
	private JPanel primaryPanel;
	private JTextField guessField;
	private JLabel currentWordLabel;
	private JButton checkBtn;

	private JPanel Difficulty;
	private JRadioButton easyRadioButton;
	private JRadioButton mediumRadioButton;
	private JRadioButton hardRadioButton;
	private JButton changeDifficultyButton;
	private JLabel hintLabel;
	private JLabel hangManLabel;
	private ButtonGroup difficultyGroup;

	private String difficulty = null;
	private Word randomWord;
	private String currentWord;
	private int randomIdx = -1;

	private int wrongs = 0;

	GameView() {
		// Get the screen size, and perform some calculation for ideal size
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); // Get Users Screen Size
		if (screenSize.getWidth() > 800 && screenSize.getHeight() > 600) {
			screenSize.setSize(screenSize.getWidth() / 1.5, screenSize.getHeight() / 1.5);
		}

		// Initialise our window
		frame.setMinimumSize(screenSize);
		frame.setContentPane(rootPanel);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);

		hangManLabel.setVisible(false);
		hintLabel.setVisible(false);
		guessField.setVisible(false);

		// Check Button
		checkBtn.addActionListener(e -> {
			String guess = guessField.getText();
			checkGuess(guess);
		});

		// Change Difficulty Button
		changeDifficultyButton.addActionListener(e -> {
			if (easyRadioButton.isSelected()) {
				difficulty = "Easy";
			} else if (mediumRadioButton.isSelected()) {
				difficulty = "Medium";
			} else if (hardRadioButton.isSelected()) {
				difficulty = "Hard";
			} else {
				JOptionPane.showMessageDialog(null, "Please select a difficulty level first!");
				return;
			}
			System.out.println("Refreshing word...");
			randomIdx = -1;
			selectWord();
			guessField.setVisible(true);
		});
	}

	private int selectRandom() {
		int temp = new Random().nextInt(getDifficultyWords(difficulty).size());
		if (temp == randomIdx) {
			return selectRandom();
		}
		return temp;
	}

	void selectWord() {
		randomIdx = selectRandom();

		randomWord = getDifficultyWords(difficulty).get(randomIdx);
		setCurrentWord(getCensoredWord(randomWord.getWord()));

		checkGuess(String.valueOf(randomWord.getWord().charAt(new Random().nextInt(randomWord.getWord().length()))));
		System.out.println("Word: " + randomWord.getWord());

		hangManLabel.setVisible(true);
		updateHangman();
	}

	void updateHangman() {
		hangManLabel.setIcon(new ImageIcon(System.getProperty("user.dir") + "\\hangman_" + wrongs + ".png"));
	}

	private void checkGuess(String guess) {
		String message = null;

		System.out.println("currentWord: " + currentWord + " // " + "guess: " + guess);
		guess = guess.toLowerCase(Locale.ROOT);

		if (difficulty == null) {
			message = "Please select a difficulty level first.";
		} else if (guess.length() != 1) {
			message = "Please enter one character at a time.";
		} else if (!guess.matches("^[A-Za-z]*$")) {
			message = "Please enter alphabets only.";
		} else if (currentWord.contains(guess)) {
			message = "Already guessed or present as a hint";
		} else {
			boolean goodGuess = false;
			for (int i = 0; i < randomWord.getWord().length(); i++) {
				if (guess.equals(Character.toString(randomWord.getWord().charAt(i)))) {
					goodGuess = true;

					StringBuilder newWord = new StringBuilder(currentWord);
					newWord.setCharAt(i, randomWord.getWord().charAt(i));
					setCurrentWord(newWord.toString());
				}   
			}

			if (!goodGuess) {
				message = "Incorrect guess!";
				wrongs ++;
				updateHangman();
			}
		}

		if (wrongs == 3) {
			promptGame("lose");
			return;
		}

		guessField.setText("");
		if (message != null) {
			JOptionPane.showMessageDialog(null, message);
		} else if (currentWord.equals(randomWord.getWord())) {
			promptGame("win");
			wrongs = 0;
		}
	}

	private void promptGame(String word) {
		if (JOptionPane.showConfirmDialog(null, "You " + word + "!  Do you want to continue and play more?", "Guess the Game", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION) {
			System.exit(1);
		} else {
			frame.dispose();
			new GameView();
		}
	}

	private String getCensoredWord(String word) {
		return "" + "-".repeat(word.length());
	}

	private void setCurrentWord(String word) {
		currentWord = word;
		currentWordLabel.setText(word);

		hintLabel.setVisible(!difficulty.equals("Hard"));
		hintLabel.setText("Hint: " + randomWord.getHint());
	}
}
