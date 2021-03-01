import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.io.Reader;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;


public class Backend implements BackendInterface {

	
	private HashTableMap<String, List<MovieInterface>> genreHash;
	private HashTableMap<String, List<MovieInterface>> rateHash;
	private List<MovieInterface> objList;
	private List<String> genres;
	private List<String> ratings;
	private List<String> allGenres;
	
	public Backend (String[] args) {
		genreHash = new HashTableMap<String, List<MovieInterface>>();
		rateHash = new HashTableMap<String, List<MovieInterface>>();
		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		FileReader reader = new FileReader(args[0]);
		BufferedReader file = new BufferedReader(reader);
		objList = readDataSet(file);
		populateGenreHash();
		populateRatingHash();
			
	}
	
	public Backend (Reader r) {
		genreHash = new HashTableMap<String, List<MovieInterface>>();
		rateHash = new HashTableMap<String, List<MovieInterface>>();
		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		objList = readDataSet(r);
		populateGenreHash();
		populateRatingHash();
	
	}
	
	private void populateGenreHash() {
		List<String> gens = new ArrayList<String>();
		for (MovieInterface m: objList) {
			for (String g: m.getGenres()) {
				if (!gens.contains(g))
					gens.add(g);
			}
		}
		allGenres = gens;
		for(String s: gens) {
			List<MovieInterface> movs = new ArrayList<MovieInterface>();
			for (MovieInterface m: objList) {
				if (m.getGenres().contains(s))
					movs.add(m);
			}
			genreHash.put(s, movs);
		}
	}
	
	private void populateRatingHash() {
		for (Float i = 10f; i > 0; i--) {
			List<MovieInterface> votes = new ArrayList<MovieInterface>();
			for (MovieInterface m: objList) {
				Float vote = m.getAvgVote();
				if (vote == 10 || (vote < i && vote >= i-1))
					votes.add(m);			
			}
			rateHash.put(Float.toString(i-1), votes);	
		}
	}
	
	private List<MovieInterface> getSelectedMovies() {
		List<MovieInterface> toReturn = new ArrayList<MovieInterface>();
		for (String g: genres) {
			List<MovieInterface> movies = genreHash.get(g);
			for (MovieInterface m: movies) {
				if (!toReturn.contains(m))
					toReturn.add(m);
			}
		}
		for (String r: ratings) {
			List<MovieInterface> movies = rateHash.get(r);
			for (MovieInterface m: movies) {
				if (!toReturn.contains(m))
					toReturn.add(m);
			}
		}
		return toReturn;
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
		return genres;
	}
	
	@Override
	public List<String> getAllGenres() {
		return allGenres;
	}
	
	@Override 
	public List<String> getAvgRatings() {
		return ratings;
	}
	
	@Override
	public int getNumberOfMovies() {
		return getSelectedMovies().size();
	}
	
	@Override
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		List<MovieInterface> toReturn = new ArrayList<MovieInterface>();
		List<MovieInterface> movies = getSelectedMovies();
		Collections.sort(movies, Collections.reverseOrder());
		for (int m = startingIndex; m < startingIndex+3; m++) {
			try {
				toReturn.add(movies.get(m));
			}
			catch (IndexOutOfBoundsException e) {
				break;
			}
		}

		return movies;
		
	}
	
} 
