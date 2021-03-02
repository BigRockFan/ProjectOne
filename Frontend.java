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
    Frontend frontend = new Frontend();

    //((Frontend)frontend).run(new Backend(new StringReader("title,original_title,year,genre,duration,country,language,director")));
    //FileReader reader = new FileReader("src/movies.csv");

    
   
    
    System.out.println("Welcome to Movie Wrapper!");
    Scanner scan = new Scanner(System.in);
    //startBaseMode(frontend, b1);
    System.out.println("Please enter a command" + "\nPress r to get into ratings mode\nPress g to get into genre mode\nPress x to exit");
    while(true) {
      String s = scan.nextLine().toLowerCase();
      if(s.equals("g")) {
        genreMode(0, frontend);
      }
      else if(s.equals("r")){
        ratingsMode(scan, frontend);
      }
      else if(s.equals("x")) {
        break;
      }
      else {
        System.out.println("Incorrect command. Please try again!");
        continue;
      }
    }
    System.out.println("Select index");
    int index2 = scan.nextInt();
    baseMode(index2, frontend, b1);
  }
  

  private static void startBaseMode(Frontend frontend, Backend b1) {
    System.out.println("No movies are selected");
  }
  
  private static void baseMode(int index, Frontend frontend, Backend b1) {
    if(b1.getThreeMovies(index) == null){
      startBaseMode(frontend, b1);
    }else {
      System.out.println(b1.getThreeMovies(index));
    }
  }
  
  private static void genreMode(int index, Frontend frontend) {
    System.out.println("Please select a genre?");
    b1.addGenre("Action");
  }
  
  private static void ratingsMode(Scanner scan, Frontend frontend) {
    
  }
}
