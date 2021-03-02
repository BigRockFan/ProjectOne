// --== CS400 File Header Information ==--
// Name: Vikas Raaja 
// Email: ravinderaraa@wisc.edu
// Team: JC Red
// Role: Backend Developer 
// TA: Xinyi Liu
// Lecturer: Gary Dahl
// Notes to Grader: None

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.DataFormatException;
import java.io.*;


/**
 * This is the Backend class. It implements the BackendInterface in order
 * to create a blueprint for a backend object. It interacts with the MovieInterface and
 * the front end to supply data with the respect to the user commands
 * 
 * @author Vikas Raaja
 *
 */
public class Backend implements BackendInterface {

	
	private HashTableMap<String, List<MovieInterface>> genreHash; // Hash table that maps genres to lists of corresponding movies 
	private HashTableMap<String, List<MovieInterface>> rateHash; // Hash table that maps ratings to lists of corresponding movies 
	private List<MovieInterface> objList; // List of all the movies
	private List<String> genres; // List of selected genres 
	private List<String> ratings; // List of selected ratings 
	private List<String> allGenres; // List of all the available genres 
	
	/**
	 * Constructor to instantiate backend using a string array of arguments.
	 * These arguments will be extracted from readDataSet()  written by the Data Wrangler 
	 * 
	 * @param args - String[] of arguments 
	 * @throws FileNotFoundException - if the file isn't found
	 */
	public Backend (String[] args) throws FileNotFoundException {
		// declaring all the instance objects
		genreHash = new HashTableMap<String, List<MovieInterface>>();
		rateHash = new HashTableMap<String, List<MovieInterface>>();
		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		
		// using readers to extract the list of movies from the CSV 
		FileReader reader = new FileReader(args[0]);
		BufferedReader file = new BufferedReader(reader);
		MovieDataReader movieDataReader = new MovieDataReader(); // instantiating a MovieDataReader to extract movies
		
		// Handling exceptions in attempts to declare objList 
		try {
			objList = movieDataReader.readDataSet(file);
		} catch (DataFormatException | IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		//Populating the hash tables with these method calls
		populateGenreHash();
		populateRatingHash();
	}
			
	/**
	 * Constructor to instantiate backend using a Reader object.
	 * These reader will be used to extract movies from readDataSet() written by the Data Wrangler 
	 * @param Reader r - the reader from the front end that facilitate's the extraction of the movies 
	 */
	public Backend (Reader r) {
		// declaring all the instance objects
		genreHash = new HashTableMap<String, List<MovieInterface>>();
		rateHash = new HashTableMap<String, List<MovieInterface>>();
		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		
		MovieDataReader movieDataReader = new MovieDataReader(); // instantiating a MovieDataReader to extract movies
		
		// Handling exceptions in attempts to declare objList 
		try {
			objList = movieDataReader.readDataSet(r);
		} catch (DataFormatException | IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		//Populating the hash tables with these method calls
		populateGenreHash();
		populateRatingHash();
	
	}
	
	/**
	 * Private helper method to populate the genre hash table 
	 */
	private void populateGenreHash() {
		List<String> gens = new ArrayList<String>(); //List of genres 
		// This nested for loop iterates through the list of all movies and gets all the available genres 
		for (MovieInterface m: objList) {
			for (String g: m.getGenres()) {
				if (!gens.contains(g)) // this ensures that there are no repeats in the gens list 
					gens.add(g);
			}
		}
		allGenres = gens; // declaring allGenres here 
		
		// this nested for loop finds all the movies with each genre and adds them to the hash table.
		// EX: <genre, list of movies with that genre>
		for(String s: gens) {
			List<MovieInterface> movs = new ArrayList<MovieInterface>();
			for (MovieInterface m: objList) {
				if (m.getGenres().contains(s)) // this ensures that the movie has the genre 
					movs.add(m);
			}
			genreHash.put(s, movs);
		}
	}
	
	/**
	 * Private helper method to populate the ratings hash table 
	 */
	private void populateRatingHash() {
		// In the outer loop, the variable is the rating itself. 
		// We start from rating 10 and go down to 0 to ensure that the 
		// order of the ratings lists are in descending order. 
		for (Float i = 10f; i > 0; i--) { 
			List<MovieInterface> votes = new ArrayList<MovieInterface>(); 
			// this for loop iterates through the list of all movies and compares its vote with i 
			for (MovieInterface m: objList) {
				Float vote = m.getAvgVote();
				if (vote == 10 || (vote < i && vote >= i-1)) 
					votes.add(m); // the movie gets added if the vote is in the current range 
								  // or if it's 10 
			}
			rateHash.put(Float.toString(i-1), votes); 
		}
	}
	
	/**
	 * Private helper method to get the list of selected movies given the 
	 * genres and ratings filters 
	 * @return List<MovieInterface> - list of selected movies 
	 */
	private List<MovieInterface> getSelectedMovies() {
		List<MovieInterface> toReturn = new ArrayList<MovieInterface>(); // the list we're going to return 
		// this nested for loop populates toReturn by adding all the movies with the selected genres 
		for (String g: genres) {
			List<MovieInterface> movies = genreHash.get(g); // list of movies with a specific genre 
			for (MovieInterface m: movies) {
				if (!toReturn.contains(m)) // ensures that there are no repeats 
					toReturn.add(m);
				// Note: if genres is empty, so is toReturn
			}
		}
		// We need to make sure ratings is not empty before proceeding
		// to remove or add anything  
		if (!ratings.isEmpty()) {
			if (genres.isEmpty()) { // Confirms that only movies with selected ratings will be added
				for (String r: ratings) {
					List<MovieInterface> movies = rateHash.get(r); // list of movies with a specific rating 
					for (MovieInterface m: movies) {
						if (!toReturn.contains(m)) // ensures that there are no repeats
							toReturn.add(m);			
					}
				}
				return toReturn;
			}
			// if both toReturn and ratings are not empty, then we should 
			// remove movies from toReturn that don't have the selected ratings.
			for (MovieInterface m: toReturn) {
				if (!ratings.contains(Float.toString(m.getAvgVote())))
					toReturn.remove(m);		
			}
		}
		return toReturn;
	}
	
	/**
	 * Adds a genre to list of currently selected genres 
	 * @param String genre - to be added 
	 */
	@Override
	public void addGenre(String genre) {
		if (!genres.contains(genre))
			genres.add(genre);
	}
	
	/**
	 * Adds a rating to list of currently selected ratings 
	 * @param String rating - to be added 
	 */
	@Override
	public void addAvgRating(String rating) {
		if (!ratings.contains(rating))
			ratings.add(rating);	
	}
	
	/**
	 * Removes a genre from list of currently selected genres 
	 * @param String genre - to be removed 
	 */
	@Override
	public void removeGenre(String genre) {
		genres.remove(genre);
	}
	
	/**
	 * Removes a rating from list of currently selected ratings 
	 * @param String rating - to be removed 
	 */
	@Override
	public void removeAvgRating(String rating) {
		ratings.remove(rating);
	}
	
	/**
	 * @return - the list of currently selected genres
	 */
	@Override 
	public List<String> getGenres() {
		return genres;
	}
	
	/**
	 * @return - the list of all of the available genres 
	 */
	@Override
	public List<String> getAllGenres() {
		return allGenres;
	}
	
	/**
	 * @return - the list of currently selected ratings
	 */
	@Override 
	public List<String> getAvgRatings() {
		return ratings;
	}
	
	/**
	 * @return int - the number of selected movies given the genres and ratings filters 
	 */
	@Override
	public int getNumberOfMovies() {
		return getSelectedMovies().size();
	}
	
	/**
	 * This method gets 3 movies from the list of selected movies (in descending order by rating) starting from 
	 * the startingIndex (it's included). 
	 * @param int - the index to start at 
	 * @return List<MovieInterface> - the list of movies 3 movies (can also be less than 3 movies)
	 */
	@Override
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		List<MovieInterface> toReturn = new ArrayList<MovieInterface>(); // List of movies to return.
		List<MovieInterface> movies = getSelectedMovies(); // List of currently selected movies. 
		Collections.sort(movies, Collections.reverseOrder()); // Sorts the list of currently selected movies in 
															  // descending order by rating. 
		// This for loop adds at most 3 movies to toReturn.
		// If the index is beyond the list's bounds, then the program will
		// throw an IndexOutOfBoundsException exception. If that exception is ever caught,
		// we exit the method.
		for (int m = startingIndex; m < startingIndex+3; m++) {
			try {
				toReturn.add(movies.get(m));
			}
			catch (IndexOutOfBoundsException e) {
				break;
			}
		}

		return toReturn;
		
	}
	
} 
