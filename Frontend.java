// --== CS400 File Header Information ==--
// Name: Arnav Karnik
// Email: akarnik@wisc.edu
// Team: Red
// Group: JC
// TA: Xinyi Liu
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

public class Frontend {
    private static Backend b1;
    private Reader reader;
    public Frontend() {
        BufferedReader filein = null;
        try {
            FileReader reader = new FileReader("movies.csv");
            filein = new BufferedReader(reader);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        b1 = new Backend(filein);
    }

    public void run(Backend b1) {

        // welcome message
        System.out.println("Welcome to Movie Mapper!");
        // importing scanner
        Scanner scan = new Scanner(System.in);
        // prompting user with the commands they can use and what they mean
        baseMode(0, b1, scan);

    }
    public static void main(String[] args) {
        // instantiating the frontend object
        Frontend frontend = new Frontend();
        frontend.run(b1);
    }

    /**
     * Helper method with functionality for the Base mode when the Hash table is null
     * @param b1
     */
    private static void startBaseMode(Backend b1) {
        System.out.println("\nNo movies are selected\n");
    }

    /**
     * Helper method with functionality for the Base mode when the Hash table is not null
     * @param index
     * @param b1
     * @param scan
     */
    private static void baseMode(int index, Backend b1, Scanner scan) {
        // while loop continuously runs
        while(true) {
            if(b1.getThreeMovies(index).isEmpty()){
                startBaseMode(b1);
            }else {
                displayThreeMovies(index);
            }
            System.out.println("Please enter a command:\nType a number to navigate to that movie\nPress r to get into ratings mode\nPress g to get into genre mode\nPress x to exit");
            // retrieving the command from the user
            String s = scan.nextLine().toLowerCase();
            // if the user wants to go into the genre mode
            if(s.equals("g")) {
                genreMode(b1, scan);
            }
            // if the user wants to go into the ratings mode
            else if(s.equals("r")){
                ratingsMode(scan, b1);
            }
            // if the user wants to exit
            else if(s.equals("x")) {
                System.out.println("\nThank you for using Movie Mapper!");
                return;
            }
            else if (Integer.parseInt(s) <= b1.getNumberOfMovies() && Integer.parseInt(s) > 0) {
                baseMode(Integer.parseInt(s) - 1, b1, scan);
                return;
            }
            else if (Integer.parseInt(s) <= 0 || Integer.parseInt(s) > b1.getNumberOfMovies()) {
                System.out.println("\nInvalid rank - there are no movies at this rank.\n");
            }
            // if the user provides the program with an improper command
            else {
                System.out.println("Incorrect command. Please try again!");
            }
        }
    }

    /**
     * Helper method with functionality for the Genres Mode
     * @param b1
     * @param scan
     */
    private static void genreMode(Backend b1, Scanner scan) {
        while (true) {
            // displays the genres the user can select
            System.out.println("Genres: "+b1.getAllGenres());
            //prompting user to either select or deselect a genre
            System.out.println("Currently selected: "+b1.getGenres());
            System.out.println("Do you want to select or deselect a genre? select/deselect/exit(x)");
            String selectOrDeselect = scan.nextLine();
            // if the user wants to select a genre
            if (selectOrDeselect.equals("select")) {
                while (true) {
                    System.out.println("Please select a genre (or x to exit):");
                    String genreSelect = scan.nextLine();
                    if (genreSelect.equals("x"))
                        break;
                    else if (b1.getAllGenres().contains(genreSelect))
                        b1.addGenre(genreSelect);
                    else
                        System.out.println("Genre does not exist.");

                }
            }
            // if user wants to deselect a genre
            else if (selectOrDeselect.equals("deselect")) {
                while (true) {
                    System.out.println("Please deselect a genre (or x to exit):");
                    String genreDeselect = scan.nextLine();
                    if (genreDeselect.equals("x"))
                        break;
                    else if (b1.getGenres().contains(genreDeselect))
                        b1.removeGenre(genreDeselect);
                    else
                        System.out.println("This genre has not been selected or does not exist.");
                }
            }
            else if (selectOrDeselect.equals("x"))
                break;
            // if the user does a command that is neither select or deselect genre
            else {
                System.out.println("Incorrect command. Please try again!");
            }
        }

    }

    /**
     * Helper method with functionality for the Ratings Mode
     * @param scan
     * @param b
     */
    private static void ratingsMode(Scanner scan, Backend b) {
        while (true) {
            // the rating you want to either select or deselect
            System.out.println("Ratings: [\"0\", \"1\", \"2\", \"3\", \"4\", \"5\", \"6\", \"7\", \"8\", \"9\", \"10\"]");
            // gets the ratings that the movies have
            System.out.println("Currently selected: " + b1.getAvgRatings());

            // prompts user to see if they want to either select or deselect the rating
            System.out.println("Would you like to select or deselect the rating? select/deselect/exit(x)");
            String ratingSelector = scan.nextLine();
            // checks to see if the user wants to select the rating
            if (ratingSelector.equals("select")) {
                while (true) {
                    // enter a movie rating
                    System.out.println("Please enter a movie rating [0-9] to select (or x to exit): ");
                    String rating = scan.nextLine();
                    // if user wants to exit, return to regular ratings mode
                    if (rating.equals(""))
                        continue;
                    if (rating.equals("x"))
                        break;
                    // converts the string rating into an integer to check if the rating is appropriate
                    int intRating = Integer.parseInt(rating);
                    // checks to see if the rating entered is between 0 and 10 and not already in selected ratings
                    if (intRating < 10 && intRating >= 0 && !b1.getAvgRatings().contains(rating+".0")) {
                        // adds the rating
                        b.addAvgRating(rating+".0");
                    }
                    else
                        System.out.println("Rating is invalid or already selected.");
                }
            }
            // checks to see if the user wants to deselect the rating
            else if (ratingSelector.equals("deselect")) {
                while (true) {
                    // enter a movie rating
                    System.out.println("Please enter a movie rating [0-9] to deselect (or x to exit): ");
                    String rating = scan.nextLine();
                    // if user wants to exit, return to regular ratings mode
                    if (rating.equals("x"))
                        break;
                    // converts the string rating into an integer to check if the rating is appropriate
                    int intRating = Integer.parseInt(rating);
                    // checks to see if the rating entered is between 0 and 10 and is currently selected
                    if (intRating < 10 && intRating >= 0 && b1.getAvgRatings().contains(rating+".0")) {
                        // removes the rating
                        b.removeAvgRating(rating+".0");
                    }
                    else
                        System.out.println("Rating is invalid or not currently selected.");
                }
            } else if (ratingSelector.equals("x"))
                break;
                // if the user provides the program with an improper command
            else {
                System.out.println("Incorrect command. Please try again!");
            }
        }
    }

    private static void displayThreeMovies(int index) {
        int indexOfThree = 0;
        for (int i = index; i < index+3; i++) {
            try {
                MovieInterface movie = b1.getThreeMovies(index).get(indexOfThree);
                System.out.printf((i + 1) + ") %-40.40s  %-40.40s  " + movie.getAvgVote() + "%n", movie.getTitle(), movie.getGenres());
            } catch (IndexOutOfBoundsException e) {
                System.out.println(" ~~~~~~ No movie to print ~~~~~~ ");
            }
            indexOfThree++;
        }
        System.out.println();
    }
}
