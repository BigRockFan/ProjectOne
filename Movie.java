import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.io.BufferedReader;
import java.util.Scanner;

// Movie class
public class Movie implements MovieInterface, MovieDataReaderInterface {
  
  //instance variables
  private String title;
  private Integer year;
  private List<String> genres;
  private String director;
  private String description;
  Float avg_vote;
  
  public Movie(String title, Integer year, List<String> genres, String director,
      String description, Float avg_vote) {
    this.title = title;
    this.year = year;
    this.genres = genres;
    this.director = director;
    this.description = description;
    this.avg_vote = avg_vote;
  }
  
  @Override
  public String getTitle() {
    // TODO Auto-generated method stub
    return title;
  }
  
  @Override
  public Integer getYear() {
    // TODO Auto-generated method stub
    return year;
  }
  
  @Override
  public List<String> getGenres() {
    // TODO Auto-generated method stub
    return genres;
  }
  
  public String getGenresString() {
    // TODO Auto-generated method stub
    String genres = "";
    for (int i = 0; i < this.genres.size(); i++) {
      genres += this.genres.get(i) + ",";
    }
    return genres.substring(0, genres.length()-1);
  }
  
  @Override
  public String getDirector() {
    // TODO Auto-generated method stub
    return director;
  }
  
  @Override
  public String getDescription() {
    // TODO Auto-generated method stub
    return description;
  }
  
  @Override
  public Float getAvgVote() {
    // TODO Auto-generated method stub
    return avg_vote;
  }
  
  @Override
  public int compareTo(MovieInterface otherMovie) {
    // TODO Auto-generated method stub
    if (this.getAvgVote() == otherMovie.getAvgVote()) return 0;
    return this.getAvgVote() > otherMovie.getAvgVote() ? 1: -1;
  }
  
  @Override
  public List<MovieInterface> readDataSet(Reader inputFileReader)
    throws FileNotFoundException, IOException, DataFormatException {
    // TODO Auto-generated method stub
    if (inputFileReader == null) throw new FileNotFoundException("File not found");
    // BufferedReader in = new BufferedReader(inputFileReader);
    List<MovieInterface> movieList = new ArrayList<MovieInterface>();
    Scanner inScan = new Scanner(inputFileReader);
    inScan.useDelimiter(",");
    while (inScan.hasNext()) {
      
    }
    return null;
    
  }
}
