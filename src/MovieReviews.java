import java.io.*;
import java.util.*;

public class MovieReviews {

	static ArrayList<Words> averageScores;

	public static void main(String[] args) throws IOException {

		Scanner in = new Scanner(System.in);

		File movieReviews = new File("movieReviews.txt");
		Scanner movieReviewsFile = new Scanner(movieReviews);

		// the hash maps hashCounter and hashTotalScore will store the values of the
		// total occurrences and total score of each word in the file, as long as the
		// word
		// is longer than 0 characters, and its first letter is alphabetical.
		// the hash maps also first check if they already contain the key before
		// a new key is put in them.
		Map<String, Integer> hashCounter = new HashMap<>();
		Map<String, Integer> hashTotalScore = new HashMap<>();
		ArrayList<Words> avgScores = new ArrayList<>();

		while (movieReviewsFile.hasNextLine()) {
			String line = movieReviewsFile.nextLine().toLowerCase();
			String[] word = line.split(" ");

			for (int i = 1; i < word.length; i++) {
				int score = Integer.parseInt(word[0]);

				if (word[i].length() > 0 && word[i].charAt(0) >= 'a' && word[i].charAt(0) <= 'z') {
					if (!hashCounter.containsKey(word[i])) {
						hashCounter.put(word[i], 1);
					} else {
						int counter = hashCounter.get(word[i]);
						hashCounter.put(word[i], ++counter);
					}

					if (!hashTotalScore.containsKey(word[i]))
						hashTotalScore.put(word[i], score);
					else {
						int totalScores = hashTotalScore.get(word[i]);
						hashTotalScore.put(word[i], totalScores += score);
					}
				}

			}
		}
		movieReviews = new File("movieReviews.txt");
		movieReviewsFile = new Scanner(movieReviews);

		// if a word is repeated atleast 5 times, and if it exists as a key
		// in any of the hash maps, the average of that word is calculated and
		// stored in an array list, which is finally sorted using the
		// comparable interface. The reference to the array list is then
		// passed on to the global instance variable averageScores, which is
		// passed into the parameters of the methods getMostPositiveWords() and
		// getMostNegativeWords()
		while (movieReviewsFile.hasNext()) {
			String word = movieReviewsFile.next();
			double avg = 0;

			if (hashTotalScore.containsKey(word) && hashCounter.get(word) > 4) {
				avg = 1.0 * hashTotalScore.get(word) / hashCounter.get(word);
				avgScores.add(new Words(word, avg));
			}
		}
		Collections.sort(avgScores);

		averageScores = avgScores;

		int userChoice = 0;

		// this is the main loop which keeps the program running
		while (userChoice != 5) {
			movieReviews = new File("movieReviews.txt");
			movieReviewsFile = new Scanner(movieReviews);

			System.out.println("What would you like to do? ");
			System.out.println("1. Get the score of a word");
			System.out.println("2. Get the score of a sentence");
			System.out.println("3. See the top 5 most positive words with more than five occurrences");
			System.out.println("4. See the top 5 most negative words with more than five occurrences");
			System.out.println("5. Exit");

			boolean go = false;
			while (go == false) {
				try {
					userChoice = in.nextInt();

					if (userChoice > 5 || userChoice < 1)
						throw new InputMismatchException();
					go = true;
				} catch (InputMismatchException ax) {
					System.out.println("Please input values from 1-5 only");
					in.nextLine();

				}
			}

			switch (userChoice) {
			case 1:
				getWordScore();
				break;
			case 2:
				getSentenceScore();
				break;
			case 3:
				getMostPositiveWords(averageScores);
				break;
			case 4:
				getMostNegativeWords(averageScores);
			}

		}

		System.out.println("Thank you, bye!");

	}

	public static void getSentenceScore() throws IOException {

		Scanner in = new Scanner(System.in);

		System.out.print("Enter a sentence: ");
		String sentence = in.nextLine();

		ArrayList<String> words = new ArrayList<>();

		for (int i = 0; i < sentence.length(); i++) {
			if (sentence.charAt(i) == ' ' || i == sentence.length() - 1) {
				words.add(sentence.substring(0, i + 1).trim());
				sentence = sentence.substring(i + 1);
				i = 0;
			}
		}
		double avgScores = 0;

		for (int i = 0; i < words.size(); i++) {
			File movieReviews = new File("movieReviews.txt");
			Scanner movieReviewsFile = new Scanner(movieReviews);
			double scores = 0;
			int counter = 0;
			double avgHolder = 0;
			while (movieReviewsFile.hasNextLine()) {
				String line = movieReviewsFile.nextLine().toLowerCase();
				if (line.contains(" " + words.get(i) + " ")) {
					String firstChar = line.charAt(0) + "";
					scores += Integer.parseInt(firstChar);
					counter++;
				}
			}
			avgHolder = (counter == 0) ? (scores / 1) : (scores / counter);
			avgScores += avgHolder;
		}

		avgScores /= words.size();
		String formattedScores = String.format("%.2f", avgScores);
		System.out.println("The average score for the sentence  is " + formattedScores + "\n");

	}

	public static void getWordScore() throws IOException {

		File movieReviews = new File("movieReviews.txt");
		Scanner movieReviewsFile = new Scanner(movieReviews);
		Scanner in = new Scanner(System.in);
		double scores = 0;
		int counter = 0;

		System.out.print("Enter a word: ");
		String wordEntered = in.next().toLowerCase();

		while (movieReviewsFile.hasNextLine()) {
			String line = movieReviewsFile.nextLine().toLowerCase();
			if (line.contains(" " + wordEntered + " ")) {
				String firstChar = line.charAt(0) + "";
				scores += Integer.parseInt(firstChar);
				counter++;
			}

		}

		double avg = (counter == 0) ? (scores / 1) : (scores / counter);

		String avgScores = String.format("%.2f", avg);
		System.out.println(wordEntered + " appears " + counter + " times");
		System.out.println(
				"The average score for reviews containing the word " + wordEntered + " is " + avgScores + "\n");

	}

	// this method and getMostNegativeWords() simply print out the values from the
	// array list.
	// no changes are made to any variables.
	public static void getMostPositiveWords(ArrayList<Words> avgScores) throws IOException {

		System.out.println("\nThe top 5 most positive words with more than 5 occurences are: ");
		int counter = 0;
		for (int i = 0; counter < 5; i++) {
			if (!(avgScores.get(i).word.equals(avgScores.get(i + 1).word))) {
				System.out.printf("%-15s%.2f", avgScores.get(i).word, avgScores.get(i).avg);
				System.out.println();
				counter++;
			}

		}

		System.out.println("\n");

	}

	public static void getMostNegativeWords(ArrayList<Words> avgScores) throws IOException {

		System.out.println("\nThe top 5 most negative words with more than 5 occurences are: ");
		int counter = 0;
		for (int i = avgScores.size() - 1; counter < 5; i--) {
			if (!(avgScores.get(i).word.equals(avgScores.get(i - 1).word))) {
				System.out.printf("%-20s%.2f", avgScores.get(i).word, avgScores.get(i).avg);
				System.out.println();
				counter++;
			}

		}

		System.out.println("\n");

	}
}

class Words implements Comparable<Words> {

	String word;
	double avg;

	public Words(String word, double avg) {
		this.word = word;
		this.avg = avg;
	}

	@Override
	public int compareTo(Words o) {
		if (avg < o.avg)
			return 1;
		if (avg > o.avg)
			return -1;
		else
			return 0;
	}

	public String toString() {
		return "The word is " + word + " and score is " + avg;
	}

}
