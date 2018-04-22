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
	 * @param book
	 * @return
	 */
	public int addMovieToCatalogue(Movie movie) {
		this.moviesAvailable.add(movie);
		
		// when adding the book, also add the genre to the Catalogue. Ensure this is unique
		// ref: https://stackoverflow.com/questions/2642589/how-does-a-arraylists-contains-method-evaluate-objects
		if (!genres.contains(movie.getGenre())) {
			genres.add(movie.getGenre());
		} else {
			genres.get(genres.indexOf(movie.getGenre())).addToGenre();
		}
		
		return moviesAvailable.size();
	}

	/**
	 * Return the Movie, if the Title and the YEar of release are an exact match. Remember the Movie may be available or rented out
	 * @param movieName
	 * @param movieYear
	 * @return
	 */
	public Movie findMovie(String movieName, int movieYear) {
		Iterator<Movie> availableMoviesIterator = this.moviesAvailable.iterator();
		while (availableMoviesIterator.hasNext()) {
			Movie oneMovie = availableMoviesIterator.next();
			if (oneMovie.getTitle().equals(movieName) && oneMovie.getYear() == movieYear) {
				return oneMovie;
			}
		}
		Iterator<Movie> rentedMoviesIterator = this.moviesRented.iterator();
		while (rentedMoviesIterator.hasNext()) {
			Movie oneMovie = rentedMoviesIterator.next();
			if (oneMovie.getTitle().equals(movieName) && oneMovie.getYear() == movieYear) {
				return oneMovie;
			}
		}
		
		return null;
	}

	public Movie removeMovieFromCatalogue(String movieName, int movieYear) {
		Movie movieToRemove = findMovie(movieName, movieYear);
		if ((movieToRemove != null) && (movieToRemove.getStatus() == Movie.MOVIE_AVAILABLE)) {
			moviesAvailable.remove(movieToRemove);
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
		moviesRented.add(movieBeingRented);
		moviesAvailable.remove(movieBeingRented);
		return movieBeingRented;
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