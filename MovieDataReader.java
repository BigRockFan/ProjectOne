// --== CS400 File Header Information ==--
// Name: <Jacopo Franciosi>
// Email: <jfranciosi@wisc.edu>
// Team: <Red>
// Group: <your groups name: N/A>
// TA: <Xinyi>
// Lecturer: <Gary D.>
// Notes to Grader: <N/A> Used stackOverflow to figure out a splitter https://stackoverflow.com/
// questions/1757065/java-splitting-a-comma-separated-string-but-ignoring-commas-in-quotes

import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.zip.DataFormatException;

/**
 * Class implementation of MovieDataReaderInterface
 * @author Jacopo Franciosi
 * This class allows the reading of a CSV file into a format the back-end dev. can use
 */
public class MovieDataReader implements MovieDataReaderInterface {

  /**
   * Reads data from a CSV file of movies and returns it in as a List of movies
   * @param inputFileReader the file that will be read and formatted into a list
   * @throws DataFormatException if the data's format is incorrect
   * @throws IOException if there is an error opening the file
   * @return List<MovieInterface> list of movies 
   */
  @Override
  public List<MovieInterface> readDataSet(Reader inputFileReader)
    throws IOException, DataFormatException {
    // creates a hash, movieList, and the correct attributes array to add to a movie
    HashTableMap<Integer, String> table = new HashTableMap<Integer, String>();
    List<MovieInterface> movieList = new ArrayList<MovieInterface>();
    int x = 0;
    String[] parameters = {"title", "year", "genre", "director", "description", "avg_vote"};
    try {
      Scanner inScan = new Scanner(inputFileReader);
      if (inputFileReader == null) throw new IOException();
      String placer = inScan.next(); // takes the next line of the CSV
      if (placer != null) {
        // this places the movie attribute names into a file to later compare them
        String[] headers = placer.split(",");
        for (int i = 0; i < headers.length; i++) {
          table.put(i, headers[i]);
        }
        inScan.nextLine();
        while (inScan.hasNextLine()) {
          placer = inScan.nextLine();
          // this splits the actual movie attributes and checks if the column size is off (if any
              // of them are missing).
          String[] splitter = placer.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1); //*see header*
          if (splitter.length != table.size()) {
            throw new DataFormatException("Mismatch error, column amount is inaccurate");
          }
          // fields for movie
          String t = "";
          Integer yr = 0; 
          List<String> genres = new ArrayList<>();
          String dir = "";
          String descr = "";
          Float avgV = 0.0f;
          int index = 0;
          // checks for each field above that if it is a required category to add then it is set to
              // the correct CSV value
          for (int i = 0; i < splitter.length; i++) {
            if (table.get(i).equals(parameters[index])) {
              if (parameters[index].equals("title")) {
                if (splitter[i].length() > 0 && splitter[i].charAt(0) == '"') {
                  splitter[i] = MovieDataReader.splitHelper(splitter[i]);
                }
                t = splitter[i];
              } else if (parameters[index].equals("year")) {
                yr = Integer.parseInt(splitter[i]);
              } else if (parameters[index].equals("genre")) {
                if (splitter[i].length() > 0 && splitter[i].charAt(0) == '"') {
                  splitter[i] = MovieDataReader.splitHelper(splitter[i]);
                }
                String[] genresSplit = splitter[i].split(",");
                List<String> temp = new ArrayList<>();
                for (int j = 0; j < genresSplit.length; j++) {
                  temp.add(genresSplit[j].trim());
                }
                genres = temp;
              } else if (parameters[index].equals("director")) {
                if (splitter[i].length() > 0 && splitter[i].charAt(0) == '"') {
                  splitter[i] = MovieDataReader.splitHelper(splitter[i]);
                }
                dir = splitter[i];
              } else if (parameters[index].equals("description")) {
                if (splitter[i].length() > 0 && splitter[i].charAt(0) == '"') {
                  splitter[i] = MovieDataReader.splitHelper(splitter[i]);
                }
                descr = splitter[i];
              } else if (parameters[index].equals("avg_vote")) {
                avgV = Float.parseFloat(splitter[i]);
              } else {
                // continue when the field is not one the required ones, like duration, or language
                continue;
              }
              index++;
            }
          }
          // add a new movie into the list with all the attributes
          movieList.add(new Movie(t, yr, genres, dir, descr, avgV));
        }
      }
    // catches an IOException if there is a problem processing the file
    }catch (IOException e) { 
      throw new IOException("File processing has encountered an error");
    }
    return movieList;
  }
  
  /**
   * Helper method to format substringing
   * @param str String to be formatted
   * @return String newly formatted String
   */
  private static String splitHelper(String str) {
    str = str.substring(1,str.length() - 1);
    return str;
  }
}
