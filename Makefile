run: compile
	@echo "FIXME: *make run* or just *make* should be the default target which compiles (when needed) and executes your code."

compile:
	@echo "FIXME: *make compile* should compile the code for your project"

test: testData testBackend testFrontend

testFrontend: TestFrontend.class
	java TestFrontend

testBackend: TestBackend.class
	java TestBackend

testData: TestMovieAndMovieDataReader.class
	java TestMovieAndMovieDataReader

TestFrontend.class: testFrontend.java
	javac TestFrontend.java

TestBackend.class: TestBackend.java
	javac TestBackend.java

TestMovieAndMovieDataReader.class: TestMovieAndMovieDataReader.java
	javac TestMovieAndMovieDataReader.java

clean:
	$(RM) *.class
