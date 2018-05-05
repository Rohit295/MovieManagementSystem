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
	public int addMovieToCatalogue(Movie movieToAdd) {
		this.moviesAvailable.add(movieToAdd);
		
		// when adding the movie, also add the genre to the Catalogue. Ensure this is unique
		// ref: https://stackoverflow.com/questions/2642589/how-does-a-arraylists-contains-method-evaluate-objects
		if (!genres.contains(movieToAdd.getGenre())) {
			genres.add(movieToAdd.getGenre());
		}
		
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

	/**
	 * Check to see if movie passed in, is in the Available List
	 * @param movieToCheck
	 * @return
	 */
	public boolean isMovieAvailable(Movie movieToCheck) {
		Iterator<Movie> availableMoviesIterator = this.moviesAvailable.iterator();
		while (availableMoviesIterator.hasNext()) {
			Movie oneMovie = availableMoviesIterator.next();
			if (oneMovie.getTitle().equals(movieToCheck.getTitle()) && (oneMovie.getYear() ==  movieToCheck.getYear())) {
				return true;
			}
		}
		return false;
	}
	
	public Movie removeMovieFromCatalogue(Movie movieToRemove) {
		if (isMovieAvailable(movieToRemove)) {
			moviesAvailable.remove(movieToRemove);

			// Adjust the genre information Check to see if there are more movies, with the same genre of the movie being removed.
			Genre genreMovieToRemove = genres.get(genres.indexOf(movieToRemove.getGenre()));
			if (!isMoviesInThisGenre(genreMovieToRemove)) {
				genres.remove(genreMovieToRemove);
			}
		}
		
		return movieToRemove;
	}
	
	/**
	 * Helper method to check if any other movies, in the catalogue, have the specific Genre
	 * @param genreToCheck
	 * @return
	 */
	private boolean isMoviesInThisGenre(Genre genreToCheck) {
		
		Iterator<Movie> availableMoviesIterator = this.moviesAvailable.iterator();
		while (availableMoviesIterator.hasNext()) {
			Movie oneMovie = availableMoviesIterator.next();
			if (oneMovie.getGenre().equals(genreToCheck)) {
				return true;
			}
		}
		
		Iterator<Movie> rentedMoviesIterator = this.moviesRented.iterator();
		while (rentedMoviesIterator.hasNext()) {
			Movie oneMovie = rentedMoviesIterator.next();
			if (oneMovie.getGenre().equals(genreToCheck)) {
				return true;
			}
		}
		
		return false;
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