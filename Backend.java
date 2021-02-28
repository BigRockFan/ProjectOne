import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


public class Backend implements BackendInterface {

	/**
	 * 1. Ask about constructors 
	 * 2. Ask about getGenres() and getAllGenres()?
	 */
	
	List<MovieInterface> objList;
	List<String> genres;
	List<String> ratings;
	
	public Backend (String[] args) {
		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		FileReader reader = new FileReader(args[0]);
		BufferedReader file = new BufferedReader(reader);
		objList = readDataSet(file);
	}
	
	public Backend (Reader r) {
		genres = new ArrayList<String>();
		objList = readDataSet(r);
		ratings = new ArrayList<String>();
	
	}
	
	@Override
	public void addGenre(String genre) {
		genres.add(genre);
	}
	
	@Override
	public void addAvgRating(String rating) {
		ratings.add(rating);
	}
	
	@Override
	public void removeGenre(String genre) {
		genres.remove(genre);
	}
	
	@Override
	public void removeAvgRating(String rating) {
		ratings.remove(rating);
	}
	
	@Override 
	public List<String> getGenres() {
		List<String> toReturn = new ArrayList<String>(); // list of genres to return
		for (int i = 0; i < objList.size(); i++) {
			// making a copy list for each genre list 
			List<String> copy = objList.get(i).getGenres();
			for (int a = 0; a < copy.size(); a++) {
				// if toReturn doesn't already contain the genre(s)
				// in copy, the genre(s) will be added 
				if (!toReturn.contains(copy.get(a))) {
					toReturn.add(copy.get(a));
				}
			}
		}
		
		return toReturn;
	}
	
	@Override
	public List<String> getAllGenres() {
		return genres;
	}
	
	@Override 
	public List<String> getAvgRatings() {
		return ratings;
	}
	
	@Override
	public int getNumberOfMovies() {
		return objList.size();
	}
	
	@Override
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		List<MovieInterface> movies = new ArrayList<MovieInterface>();
		for (int i = startingIndex; i < startingIndex+3; i++) {
			if (i < objList.size()) 
				movies.add(objList.get(i));
		}
		return movies;
		
	}
	
} 
