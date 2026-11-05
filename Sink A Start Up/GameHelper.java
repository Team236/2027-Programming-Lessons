import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameHelper {
    private static final String ALPHABET = "abcdefg";
    private static final int GRID_LENGTH = 7;
    private static final int GRID_SIZE = 49;
    private final int[] grid = new int[GRID_SIZE];
    private int startupCount = 0;
    private final Scanner scanner = new Scanner(System.in);

    /**
     * Prompts the user in the console and returns the string input.
     *
     * @param prompt Text message to display to the player
     * @return User input formatted as lowercase string
     */
    public String getUserInput(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine().toLowerCase().trim();
    }

    /**
     * Randomly places a Startup on the 7x7 grid without overlapping.
     *
     * @param startupSize Number of contiguous cells required (e.g., 3)
     * @return ArrayList of coordinate strings (e.g., ["a1", "a2", "a3"])
     */
    public ArrayList<String> placeStartup(int startupSize) {
        ArrayList<String> alphaCells = new ArrayList<>();
        int[] coords = new int[startupSize];
        int attempts = 0;
        boolean success = false;
        int location = 0;

        startupCount++;
        int incr = 1; // Horizontal increment (move 1 column right)
        if ((startupCount % 2) == 1) { // Alternate orientation for odd startups
            incr = GRID_LENGTH; // Vertical increment (move 1 row down)
        }

        while (!success && attempts++ < 200) {
            location = (int) (Math.random() * GRID_SIZE);
            int x = 0;
            success = true;

            while (success && x < startupSize) {
                if (grid[location] == 0) {
                    coords[x] = location;
                    x++;
                    location += incr;

                    // Out of bottom border check
                    if (location >= GRID_SIZE) {
                        success = false;
                    }
                    // Out of right border wrap-around check
                    if (x > 0 && (location % GRID_LENGTH == 0)) {
                        success = false;
                    }
                } else {
                    // Cell already occupied
                    success = false;
                }
            }
        }

        // Convert raw numeric grid positions into letter/number coordinates (e.g.,
        // "b3")
        int x = 0;
        int row = 0;
        int column = 0;

        while (x < startupSize) {
            grid[coords[x]] = 1; // Mark cell as occupied
            row = coords[x] / GRID_LENGTH;
            column = coords[x] % GRID_LENGTH;
            String temp = String.valueOf(ALPHABET.charAt(column));

            alphaCells.add(temp.concat(Integer.toString(row)));
            x++;
        }

        return alphaCells;
    }

    public static boolean isValidCoordinate(String input) {
        if (input == null || input.length() != 2) {
            return false;
        }
        char column = Character.toUpperCase(input.charAt(0));
        char row = input.charAt(1);
        return (column >= 'A' && column <= 'G') && (row >= '0' && row <= '6');
    }

}