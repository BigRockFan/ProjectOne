// --== CS400 File Header Information ==--
// Name: <Jacopo Franciosi>
// Email: <jfranciosi@wisc.edu>
// Team: <Red>
// Group: <your groups name: N/A>
// TA: <Xinyi>
// Lecturer: <Gary D.>
// Notes to Grader: <N/A>

import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 * This class contains a set of tests for the MovieInterface and MovieDataReaderInterface
 * implementation of the Movie Mapper project.
 */
public class TestMovieAndMovieDataReader {

        MovieDataReaderInterface readerToTest;

        public static void main(String[] args) {
                (new TestMovieAndMovieDataReader()).runTests();
        }

        /**
         * This method calls all of the test methods in the class and ouputs pass / fail
         * for each test.
         */
        public void runTests() {
                // instantiate reader to test once it is implemented
                readerToTest = null; //new MovieDataReader();

                // add all tests to this method
                if (this.testReaderNumberOfMovies()) {
                        System.out.println("Test number of movies: PASSED");
                } else {
                        System.out.println("Test number of movies: FAILED");
                }
                if (this.testReaderMovieTitles()) {
                        System.out.println("Test movie titles: PASSED");
                } else {
                        System.out.println("Test movie titles: FAILED");
                }
                if (this.testMovieOrder()) {
                        System.out.println("Test movie order: PASSED");
                } else {
                        System.out.println("Test movie order: FAILED");
                }
                if (this.testGetters()) {
                        System.out.println("Test getter methods: PASSED");
                } else {
                        System.out.println("Test getter methods: FAILED");
                }
                if (this.testCompareTo()) {
                        System.out.println("Test compareTo method: PASSED");
                } else {
                        System.out.println("Test compareTo method: FAILED");
                }
        }
        /**
         * This test reads in 3 movies and tests whether the list of movies
         * returned is of size 3. It fails if the size is not 3 or if an
         * exception occurs while reading in the movies.
         * @return true if the test passed, false if it failed
         */
        public boolean testReaderNumberOfMovies() {
                List<MovieInterface> movieList;
                try {
                        movieList = readerToTest.readDataSet(new StringReader(
                                        "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
                                        + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior,>
                                        + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph >
                                        + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew>
                        ));
                } catch (Exception e) {
                        e.printStackTrace();
                        // test failed
                        return false;
                }
                if (movieList.size() == 3) {
                        // test passed
                        return true;
                } else {
                        // test failed
                        return false;
                }
        }

    /**
         * This test reads in 3 movies and tests whether the list of movies
         * contains all 3 titles of the source data in any order. It fails
         * if the list returned is missing one or more titles or if an
         * exception occurs while reading in the data.
         * @return true if the test passed, false if it failed
         */
        public boolean testReaderMovieTitles() {
                List<MovieInterface> movieList;
                try {
                        movieList = readerToTest.readDataSet(new StringReader(
                                        "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
                                        + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior,>
                                        + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph >
                                        + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew>
                        ));
                } catch (Exception e) {
                        e.printStackTrace();
                        // test failed
                        return false;
                }
                String title1 = "The Source of Shadows";
                String title2 = "The Insurrection";
                String title3 = "Valley Girl";
                boolean equalOne = true;
                // check if first movie is has of the above titles
                equalOne = equalOne && (title1.equals(movieList.get(0).getTitle()) ||
                                                                title2.equals(movieList.get(0).getTitle()) ||
                                                                title3.equals(movieList.get(0).getTitle()));
                // check if second movie is has of the above titles
                equalOne = equalOne && (title1.equals(movieList.get(1).getTitle()) ||
                                                                title2.equals(movieList.get(1).getTitle()) ||
                                                                title3.equals(movieList.get(1).getTitle()));
                // check if third movie is has of the above titles
                equalOne = equalOne && (title1.equals(movieList.get(2).getTitle()) ||
                                                                title2.equals(movieList.get(2).getTitle()) ||
                                                                title3.equals(movieList.get(2).getTitle()));
                // true if the three movies have the right titles
                return equalOne;
        }

        /**
         * This test reads in 3 movies, then sorts them. It then checks whether
         * the default sorting order is descending based on the average ratings.
         * @return true if the test passed, false if it failed
         */
        public boolean testMovieOrder() {
                List<MovieInterface> movieList;
                try {
                        movieList = readerToTest.readDataSet(new StringReader(
                                        "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
                                        + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior,>
                                        + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph >
                                        + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew>
                        ));
                } catch (Exception e) {
                        e.printStackTrace();
                        // test failed
                        return false;
                }
                Collections.sort(movieList);
                double lastRating = 11.0;
                for (MovieInterface movie : movieList) {
                        if (movie.getAvgVote() > lastRating) {
                                // test fails
                                return false;
                        }
                        lastRating = movie.getAvgVote();
                }
                // test passes
                return true;
        }

        // TODO: Data Wrangler, add at least 2 more tests
        /**
        *This test reads in 3 movies and checks that the getter methods(all but the getName()) are correct. 
        *@return true if the test passed, false if it failed
        */
        public boolean testGetters(){
                List<MovieInterface> movieList;
                try {
                        movieList = readerToTest.readDataSet(new StringReader(
                                "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
                                + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor >
                                + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph Camiller>
                                + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",>
                        ));
                 } catch (Exception e) {
                        e.printStackTrace();
                        // test failed
                        return false;
                }
                Integer m1Yr = Integer.valueOf(2020);
                Integer m2Yr = Integer.valueOf(2020);
                Integer m3Yr = Integer.valueOf(2020);
                if (m1Yr != movieList.get(0).getYear() || m2Yr != movieList.get(1).getYear()|| m3Yr != movieList.get(2).getYear()) return false;
                boolean genreWrong = false;
                List<String> m1G = new ArrayList<String>();
                m1G.add("Horror");
                List<String> m2G = new ArrayList<String>();
                m2G.add("Action");
                List<String> m3G = new ArrayList<String>();
                m3G.add("Comedy"); m3G.add("Musical"); m3G.add("Romance");
                for (int i = 0; i < m1G.size(); i++){
                        if (!m1G.get(i).equals(movieList.get(i).getGenres().get(i))) genreWrong = true;
                }
                for (int i = 0; i < m2G.size(); i++){
                        if (!m2G.get(i).equals(movieList.get(i).getGenres().get(i))) genreWrong = true;
                }
                for (int i = 0; i < m3G.size(); i++){
                        if (!m3G.get(i).equals(movieList.get(i).getGenres().get(i))) genreWrong = true;
                }
                if (genreWrong) return false;
                String m1Dir = "Ryan Bury, Jennifer Bonior";
                String m2Dir = "Rene Perez";
                String m3Dir = "Rachel Lee Goldenberg";
                if (!m1Dir.equals(movieList.get(0).getDirector()) || !m2Dir.equals(movieList.get(1).getDirector())|| !m3Dir.equals(movieList.get(2).getDirector()))>
                String m1Des = "A series of stories woven together by one of our most primal fears, the fear of the unknown.";
                String m2Des = "The director of the largest media company wants to expose how left-wing powers use film to control populations.";
                String m3Des = "Set to a new wave '80s soundtrack, a pair of young lovers from different backgrounds defy their parents and friends to stay togethe>
                if (!m1Des.equals(movieList.get(0).getDescription()) || !m2Des.equals(movieList.get(1).getDescription())|| !m3Des.equals(movieList.get(2).getDescri>
                Float m1V = 3.5f;
                Float m2V = 2.9f;
                Float m3V = 5.4f;
                if (Float.compare(movieList.get(0).getAvgVote(),m1V) != 0) return false;
                return true;
         }

        /**
        * This method tests that the compareTo() methods works as intended.
        * @return true if the test passed, false if it failed
        */
        public boolean testCompareTo(){
                List<MovieInterface> movieList;
                 try {
                        movieList = readerToTest.readDataSet(new StringReader(
                                "title,original_title,year,genre,duration,country,language,director,writer,production_company,actors,description,avg_vote\n"
                                + "The Source of Shadows,The Source of Shadows,2020,Horror,83,USA,English,\"Ryan Bury, Jennifer Bonior\",\"Jennifer Bonior, Trevor >
                                + "The Insurrection,The Insurrection,2020,Action,90,USA,English,Rene Perez,Rene Perez,,\"Michael Paré, Wilma Elles, Joseph Camiller>
                                + "Valley Girl,Valley Girl,2020,\"Comedy, Musical, Romance\",102,USA,English,Rachel Lee Goldenberg,\"Amy Talkington, Andrew Lane\",>
                        ));
                 } catch (Exception e) {
                        e.printStackTrace();
                        // test failed
                        return false;
                 }
                List<String> testMovie = new ArrayList<String>();
                testMovie.add("Horror");
                Movie m1 = new Movie("The Source of Shadows",2020,testMovie,"Ryan Bury, Jennifer Bonior","A series of stories woven together by one of our most pri>
                Movie m2 = new Movie("Different",2020,testMovie,"Ryan Bury, Jennifer Bonior","A series of stories woven together by one of our most primal fears, t>
                if (m1.compareTo(m2) <= 0) return false;
                if (movieList.get(0).compareTo(m1) != 0) return false;
                return true;
        }
}
