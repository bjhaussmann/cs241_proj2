/**
 * 
 */
package cs241_proj2;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author bjhau
 *
 */
public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int choice = 0;
		boolean worked = false;

		System.out.println("=====================================================================");
		System.out.println("Please select how to test the program:");
		System.out.println("(1) 20 sets of 100 randomly generated integers");
		System.out.println("(2) Fixed integer values 1-100");
		System.out.print("Enter choice: ");

		while (worked == false) {
			try {
				choice = input.nextInt(); // Take selection
				worked = true;
			} catch (NumberFormatException e) {
				System.out.print("\nInvalid command. Try again: ");
				worked = false;
			}
			catch (InputMismatchException e)
			{
				System.out.print("\nInvalid command. Try again: ");
				input.nextLine();
				worked = false;
			}
		}

		worked = false;
		while (worked == false) {
			switch (choice) {
			case 1:
				System.out.println();
				option1();
				System.out.println("\n=====================================================================");
				worked = true;
				break;
			case 2:
				System.out.println();
				option2();
				System.out.println("\n=====================================================================");
				worked = true;
				break;
			default:
				System.out.print("\nInvalid command. Please try again: ");
				while (worked == false) {
					try {
						choice = input.nextInt();
						worked = true;
					} catch (NumberFormatException e) {
						System.out.print("\nInvalid command. Try again: ");
						worked = false;
					}
					catch (InputMismatchException e)
					{
						System.out.print("\nInvalid command. Try again: ");
						input.nextLine();
						worked = false;
					}
				}
				worked = false;
				break;
			}
		}
		input.close();
	}

	/**
	 * Generates 20 sets of randomly generated integers. Computes and prints the
	 * average number of swaps.
	 */
	private static void option1() {
		Random rnd = new Random();
		int numberOfSets = 1;
		int averageSeries = 0;
		int averageOptimal = 0;

		while (numberOfSets <= 20) {
			MaxHeap<Integer> mhSeries = new MaxHeap<>(100);
			Integer[] set = new Integer[100];
			int swaps = 0;

			for (int i = 0; i < 100; i++) {
				int value = rnd.nextInt(200) + 1;
				mhSeries.add(value);
				swaps += mhSeries.getSwap();
				set[i] = value;
			}
			averageSeries += swaps;
			MaxHeap<Integer> mhOptimal = new MaxHeap<>(set);
			averageOptimal += mhOptimal.getSwap();
			numberOfSets++;
		}
		System.out.println("Average swaps for series of insertions: " + averageSeries / 20);
		System.out.println("Average swaps for optimal method: " + averageOptimal / 20);
	}

	/**
	 * Generates an array of 100 sequential integers. Outputs the first 10 integers
	 * in the array and the number of swaps. Performs 10 removals and then outputs
	 * the first 10 integers again.
	 */
	private static void option2() {
		MaxHeap<Integer> mhSeries = new MaxHeap<>(100);
		Integer[] set = new Integer[100];
		int swaps = 0;

		for (int i = 1; i < 101; i++) {
			mhSeries.add(i);
			swaps += mhSeries.getSwap();
			set[i - 1] = i;
		}
		System.out.print("Heap built using series of insertions: ");
		printHeap(mhSeries);
		System.out.println("Number of swaps: " + swaps);
		System.out.print("Heap after 10 removals: ");
		for (int i = 0; i < 10; i++) {
			mhSeries.removeMax();
		}
		printHeap(mhSeries);

		System.out.print("\nHeap built using optimal of insertions: ");
		MaxHeap<Integer> mhOptimal = new MaxHeap<>(set);
		printHeap(mhOptimal);
		System.out.println("Number of swaps: " + mhOptimal.getSwap());
		System.out.print("Heap after 10 removals: ");
		for (int i = 0; i < 10; i++) {
			mhOptimal.removeMax();
		}
		printHeap(mhOptimal);
	}

	/**
	 * Prints out all the data in the MaxHeap
	 * 
	 * @param mh
	 *            MaxHeap that has the data being printed
	 */
	private static void printHeap(MaxHeap<Integer> mh) {
		for (int i = 1; i <= 10; i++) {
			System.out.print(mh.getData(i) + ",");
		}
		System.out.println("...");
	}
}
