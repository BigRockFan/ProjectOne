// --== CS400 File Header Information ==--
// Name: <Jacopo Franciosi>
// Email: <jfranciosi@wisc.edu>
// Team: <Red>
// Group: <your groups name: N/A>
// TA: <Xinyi>
// Lecturer: <Gary D.>
// Notes to Grader: <N/A>

import java.util.List;

/**
 * Class implementation of MovieInterface
 * Constructs a movie object with the given instance fields
 * @author Jacopo Franciosi
 *
 */
public class Movie implements MovieInterface{
  
  //instance fields
  private String title;
  private Integer year;
  private List<String> genres; // list of genres
  private String director;
  private String description;
  Float avg_vote;
  
  /**
   * Constructor for a movie object
   * @param title String title of the movie
   * @param year Integer year movie was released
   * @param genres List<String> list of genres the movie falls under
   * @param director String movie's director
   * @param description String movie's description
   * @param avg_vote Float average vote given to the movie
   */
  public Movie(String title, Integer year, List<String> genres, String director,
      String description, Float avg_vote) {
    this.title = title;
    this.year = year;
    this.genres = genres;
    this.director = director;
    this.description = description;
    this.avg_vote = avg_vote;
  }
  
  /**
   * Getter method for title
   * @return String (title)
   */
  @Override
  public String getTitle() {
    return title;
  }
  
  /**
   * Getter method for year
   * @return Integer (year)
   */
  @Override
  public Integer getYear() {
    return year;
  }
  
  /**
   * Getter method for genres
   * @return List<String> (genres)
   */
  @Override
  public List<String> getGenres() {
    return genres;
  }
  
  /**
   * Getter method for genres as a comma separated string 
   * @return String (genres)
   */
  public String getGenresString() {
    String genres = "";
    for (int i = 0; i < this.genres.size(); i++) {
      genres += this.genres.get(i) + ",";
    }
    return genres.substring(0, genres.length()-1);
  }
  
  /**
   * Getter method for director
   * @return String (director)
   */
  @Override
  public String getDirector() {
    return director;
  }
  
  /**
   * Getter method for description
   * @return String (description)
   */
  @Override
  public String getDescription() {
    return description;
  }
  
  /**
   * Getter method for avg_vote
   * @return Float (avg_vote)
   */
  @Override
  public Float getAvgVote() {
    return avg_vote;
  }
  
  /**
   * Method that compares two objects of type MovieInterface
   * @param otherMovie MovieInterface to be compared with
   * @return int 0 if the same Movie object is compared, 1 (positive value) if the average vote of 
   * the one being compared is greater that the other movie's average rating and -1 (negative value)
   * if otherwise
   */
  @Override
  public int compareTo(MovieInterface otherMovie) {
    if (this.getAvgVote() == otherMovie.getAvgVote()) return 0;
    return this.getAvgVote() > otherMovie.getAvgVote() ? 1: -1;
  }
}
