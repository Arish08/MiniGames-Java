package com.company;

import java.util.ArrayList;

public abstract class Game {
	final int MAX_DIFFICULTIES = 3;
	private Difficulty[] difficulties = new Difficulty[MAX_DIFFICULTIES];

	public ArrayList<Word> getDifficultyWords(String name) {
		for (Difficulty diff: difficulties) {
			if (diff.getName().equals(name)) {
				return diff.getWords();
			}
		}
		return null;
	}
	void loadDifficulties() {
		difficulties[0] = new Difficulty("Easy");
		difficulties[0].addWord("apple", "fruit");
		difficulties[0].addWord("banana", "fruit");
		difficulties[0].addWord("orange", "fruit/colour");
		difficulties[0].addWord("chicago", "city");
		difficulties[0].addWord("london", "city");
		difficulties[0].addWord("mcdonalds", "restaurant");

		difficulties[1] = new Difficulty("Medium");
		difficulties[1].addWord("shakespeare", "writer");
		difficulties[1].addWord("darwin", "scientist");
		difficulties[1].addWord("mississippi", "location");
		difficulties[1].addWord("eerie", "adjective");
		difficulties[1].addWord("elephant", "animals");

		difficulties[2] = new Difficulty("Hard");
		difficulties[2].addWord("jazz", "music genre");
		difficulties[2].addWord("fox", "animal");
		difficulties[2].addWord("matchbox", "object");
		difficulties[2].addWord("bubbliest", "adjective");
		difficulties[2].addWord("mummifying", "process");

		for (int i = 0; i < difficulties.length; i++) {
			System.out.println("Difficulty level: " + difficulties[i].getName() + " - Words: " + difficulties[i].getWords().size());
		}
	}
	Game() {
		loadDifficulties();
	}
}
