run: compile
	java Frontend

compile: Backend.class BackendInterface.class Frontend.class HashTableMap.class MapADT.class Movie.class MovieDataReader.class MovieDataReaderInterface.class MovieInterface.class Pair.class

Backend.class: Backend.java
	javac Backend.java

BackendInterface.class: BackendInterface.java
	javac BackendInterface.java

Frontend.class: Frontend.java
	javac Frontend.java

HashTableMap.class: HashTableMap.java
	javac HashTableMap.java

MapADT.class: MapADT.java
	javac MapADT.java

Movie.class: Movie.java
	javac Movie.java

MovieDataReader.class: MovieDataReader.java
	javac MovieDataReader.java

MovieDataReaderInterface.class: MovieDataReader.java
	javac MovieDataReader.java

MovieInterface.class: MovieInterface.java
	javac MovieInterface.java

Pair.class: Pair.java
	javac Pair.java

test: testData testBackend testFrontend

testFrontend: TestFrontend.class
	java TestFrontend

testBackend: TestBackend.class
	java TestBackend

testData: TestMovieAndMovieDataReader.class
	java TestMovieAndMovieDataReader

TestFrontend.class: TestFrontend.java
	javac TestFrontend.java

TestBackend.class: TestBackend.java
	javac TestBackend.java

TestMovieAndMovieDataReader.class: TestMovieAndMovieDataReader.java
	javac TestMovieAndMovieDataReader.java

clean:
	$(RM) *.class
