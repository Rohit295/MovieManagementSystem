import java.util.ArrayList;
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
