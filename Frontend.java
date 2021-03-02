// --== CS400 File Header Information ==--
// Name: Arnav Karnik
// Email: akarnik@wisc.edu
// Team: Red
// Group: JC
// TA: Xinyi Liu
// Lecturer: Gary Dahl
// Notes to Grader: none

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.Scanner;

public class Frontend {
  private static Backend b1;
  private Reader reader;
  public Frontend() {
    BufferedReader filein = null;
    try {
        FileReader reader = new FileReader("src/movies.csv");
        filein = new BufferedReader(reader);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
        return;
    }
    b1 = new Backend(filein);
  }
  
  
  public static void main(String[] args) {
    // instantiating the frontend object
    Frontend frontend = new Frontend();

    //((Frontend)frontend).run(new Backend(new StringReader("title,original_title,year,genre,duration,country,language,director")));
    //FileReader reader = new FileReader("src/movies.csv");
    // welcome message
    System.out.println("Welcome to Movie Mapper!");
    // importing scanner
    Scanner scan = new Scanner(System.in);
    //startBaseMode(frontend, b1);
    // prompting user with the commands they can use and what they mean
    System.out.println("Please enter a command" + "\nPress r to get into ratings mode\nPress g to get into genre mode\nPress x to exit");
    // while loop continuously runs
    while(true) {
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
        System.out.println("Select index");
        int index2 = scan.nextInt();
        baseMode(index2, frontend, b1);
        continue;
      }
      // if the user provides the program with an improper command
      else {
        System.out.println("Incorrect command. Please try again!");
        continue;
      }
    }
    
  }
  
  /**
   * Helper method with functionality for the Base mode when the Hash table is null
   * @param frontend
   * @param b1
   */
  private static void startBaseMode(Frontend frontend, Backend b1) {
    System.out.println("No movies are selected");
  }
  
  /**
   * Helper method with functionality for the Base mode when the Hash table is not null
   * @param index
   * @param frontend
   * @param b1
   */
  private static void baseMode(int index, Frontend frontend, Backend b1) {
    if(b1.getThreeMovies(index) == null){
      startBaseMode(frontend, b1);
    }else {
      System.out.println(b1.getThreeMovies(index));
    }
  }
  
  /**
   * Helper method with functionality for the Genres Mode
   * @param b1
   * @param scan
   */
  private static void genreMode(Backend b1, Scanner scan) {
    // displays the genres the user can select
    System.out.println(b1.getAllGenres());
    //prompting user to either select or deselect a genre
    System.out.println("Do you want to select or deselect a genre? select/deselect");
    String selectOrDeselect = scan.nextLine();
    // if the user wants to select a genre
    if(selectOrDeselect.equals("select")) {
      System.out.println("Please select a genre?");
      String genreSelect = scan.nextLine();
      b1.addGenre(genreSelect);
    }
    // if user wants to deselect a genre
    else if(selectOrDeselect.equals("deselect")) {
      System.out.println("Please deselect a genre?");
      String genreDeselect = scan.nextLine();
      b1.removeGenre(genreDeselect);
    }
    // if the user does a command that is neither select or deselect genre
    else {
      System.out.println("Incorrect command. Please try again!");
      return;
    } 
      
  }
  
  /**
   * Helper method with functionality for the Ratings Mode
   * @param scan
   * @param b
   */
  private static void ratingsMode(Scanner scan, Backend b) {
    // the rating you want to either select or deselect 
    System.out.println("Please enter a rating like what is displayed below!");
    // gets the ratings that the movies have
    System.out.println(b1.getAvgRatings());
    // enter a movie rating
    System.out.println("Please enter a movie rating between 0-10");
    String rating = scan.nextLine();
    // converts the string rating into an integer to check if the rating is appropriate
    int intRating = Integer.parseInt(rating);
    // checks to see if the rating entered is between 0 and 10
    if(intRating>10 || intRating<0) {
      System.out.println("Please enter a valid rating!");
      return;
    }
    // prompts user to see if they want to either select or deselect the rating
    System.out.println("Would you like to select or deselect the rating? select/deselect");
    String ratingSelector = scan.nextLine();
    // checks to see if the user wants to select the rating
    if(ratingSelector.equals("select")) {
      // adds the rating
      b.addAvgRating(rating);
    }
    // checks to see if the user wants to deselect the rating
    else if(ratingSelector.equals("deselect")) {
      // removes the rating
      b.removeAvgRating(rating);
    }
    // if the user provides the program with an improper command
    else {
      System.out.println("Incorrect command. Please try again!");
      return;
    } 
  }
}
