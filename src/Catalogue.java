import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalogue {
	private Kiosk kiosk;
	private List<Movie> moviesAvailable;
	private List<Movie> moviesRented;
	private List<Genre> genres;
	
	public Catalogue(Kiosk kiosk) {
		this.kiosk = kiosk;

		this.moviesAvailable = new ArrayList<Movie>(10);
		this.moviesRented = new ArrayList<Movie>(10);
		this.genres = new ArrayList<Genre>(10);
	}

	/**
	 * 
	 * @param Movie
	 * @return
	 */
	public int addMovieToCatalogue(Movie movie) {
		// todo: should I check if the movie is already added and prevent duplicates
		
		this.moviesAvailable.add(movie);
		
		// when adding the movie, also add the genre to the Catalogue. Ensure this is unique
		// ref: https://stackoverflow.com/questions/2642589/how-does-a-arraylists-contains-method-evaluate-objects
		if (!genres.contains(movie.getGenre())) {
			genres.add(movie.getGenre());
		}
		
		genres.get(genres.indexOf(movie.getGenre())).addToGenre();
		return moviesAvailable.size();
	}

	/**
	 * Return the Movie, if the Title and the YEar of release are an exact match. Remember the Movie may be available or rented out
	 * Also the year may not be passed in some cases. There do not worry about year and just use name
	 * @param movieName
	 * @param movieYear
	 * @return
	 */
	public Movie findMovie(String movieName, int movieYear) {
		Iterator<Movie> availableMoviesIterator = this.moviesAvailable.iterator();
		while (availableMoviesIterator.hasNext()) {
			Movie oneMovie = availableMoviesIterator.next();
			if (oneMovie.getTitle().equals(movieName) && (oneMovie.getYear() == movieYear || movieYear == 0)) {
				return oneMovie;
			}
		}
		Iterator<Movie> rentedMoviesIterator = this.moviesRented.iterator();
		while (rentedMoviesIterator.hasNext()) {
			Movie oneMovie = rentedMoviesIterator.next();
			if (oneMovie.getTitle().equals(movieName) && (oneMovie.getYear() == movieYear || movieYear == 0)) {
				return oneMovie;
			}
		}
		return null;
	}

	public Movie removeMovieFromCatalogue(Movie movieToRemove) {
		if (movieToRemove.getStatus() == Movie.MOVIE_AVAILABLE) {
			moviesAvailable.remove(movieToRemove);

			// Adjust the genre information
			Genre genreMovieToRemove = genres.get(genres.indexOf(movieToRemove.getGenre()));
			genreMovieToRemove.removeFromGenre();
			if (genreMovieToRemove.getMoviesInThisGenre() == 0) {
				genres.remove(genreMovieToRemove);
			}
		}
		
		return movieToRemove;
	}
	

	/**
	 * 
	 * AMRI: this is a standard pattern when adding/removing 'things'. Return the thing being added, as confirmation that the add went through.
	 * Caller will check to see that return is not a 'null' which would indicate something went wrong
	 * @param movieBeingRented
	 * @return
	 */
	public Movie rentAMovie(Movie movieBeingRented) {
		moviesAvailable.remove(movieBeingRented);
		moviesRented.add(movieBeingRented);
		return movieBeingRented;
	}

	
	public Movie returnAMovie (Movie movieBeingReturned) {
		moviesRented.remove(movieBeingReturned);
		moviesAvailable.add(movieBeingReturned);
		return movieBeingReturned;		
	}
	
	public List<Movie> getMoviesAvailable() {
		return moviesAvailable;
	}

	public void setMoviesAvailable(List<Movie> moviesAvailable) {
		this.moviesAvailable = moviesAvailable;
	}

	public List<Movie> getMoviesRented() {
		return moviesRented;
	}

	public void setMoviesRented(List<Movie> moviesRented) {
		this.moviesRented = moviesRented;
	}

	public List<Genre> getGenres() {
		return genres;
	}	
}