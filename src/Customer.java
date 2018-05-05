import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Customer {

	private int ID;
	private String name;
	private int balance;
	private List<Movie> currentlyRented;
	private List<Movie> rentingHistory;
	
	public static final int CUSTOMER_CAN_RENT = 0;
	public static final int CUSTOMER_OUT_OF_BALANCE = 1;
	
	public Customer(int ID, String name, int balance) {
		this.ID = ID;
		this.name = name;
		this.balance = balance;
		
		currentlyRented = new ArrayList<Movie>(5);
		rentingHistory = new ArrayList<Movie>(5);
	}

	public int getID() {
		return ID;
	}


	public void setID(int iD) {
		ID = iD;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getBalance() {
		return balance;
	}


	public void setBalance(int balance) {
		this.balance = balance;
	}

	public List<Movie> getCurrentlyRented() {
		return currentlyRented;
	}
	
	/** 
	 * Use this to check if this Customer can rent a Movie at all
	 * @param movieToRent
	 */
	public int canRentThisMovie(Movie movieToRent) {
		if (movieToRent.getPrice() > balance) {
			return CUSTOMER_OUT_OF_BALANCE;
		}
		return CUSTOMER_CAN_RENT;
	}
	
	public boolean hasCurrentlyRented() {
		if (currentlyRented.size() != 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * when this customer rents a movie, add it to the currently rented list. Also make sure it becomes part of this customer's
	 * renting history so that favourites etc. can be processed later
	 * @param movie
	 */
	public Movie rentAMovie(Movie movieToRent) {
		if (!(canRentThisMovie(movieToRent) == CUSTOMER_CAN_RENT)) {
			return null;
		}
		
		// Add this Movie to the currently rented list, to the Renting History & adjust the balance. Remember the movie could get added to Renting
		// history numerous times
		currentlyRented.add(movieToRent);
		rentingHistory.add(movieToRent);
		balance -= movieToRent.getPrice();
		
		return movieToRent;
	}
	
	public boolean hasRentedMovie(Movie movieToCheck) {
		if (currentlyRented.contains(movieToCheck))
			return true;
		else		
			return false;
	}
	
	/**
	 * when this customer returns a movie, simply remove it from Currently Rented list
	 * @param movieToReturn
	 */
	public void returnAMovie(Movie movieToReturn) {
		Iterator<Movie> currentlyRentedIterator = currentlyRented.iterator();
		while (currentlyRentedIterator.hasNext()) {
			Movie oneMovie = currentlyRentedIterator.next();
			if (oneMovie.getTitle().equals(movieToReturn.getTitle()) && oneMovie.getYear() == movieToReturn.getYear()) {
				currentlyRented.remove(oneMovie);
				break;
			}
		}		
	}
	
	public int topUpBalance(int amountToTopUp) {
		balance += amountToTopUp;
		return balance;
	}
	
	
	/**
	 * Return the customer's favorite movies list as a list of Movie Titles. Logic is simple: read all the movies in the renting history and make a map of Movie 
	 * and Count of times the movie has been rented. Then sort the map on number of times rented and return the movies list
	 * @return
	 */
	public List<Movie> getCustomerFavoriteMovies() {
		Map<Movie, Integer> mapFavoriteMovies = new HashMap<Movie, Integer>(5);
		List<Movie> moviesRented = new ArrayList<Movie>(5);
		
		Iterator<Movie> movieIterator = rentingHistory.iterator();
		while (movieIterator.hasNext()) {
			Movie oneMovie = movieIterator.next();
			if (mapFavoriteMovies.keySet().contains(oneMovie)) {
				Integer rentCount = mapFavoriteMovies.get(oneMovie) + 1;
				mapFavoriteMovies.replace(oneMovie, rentCount);
			} else {
				mapFavoriteMovies.put(oneMovie, new Integer(1));
			}
		}	
		
		// At this point, I have created a map of all rented movies and have created a count for each movie. If I sort the map on integer count,
		// I have the users favorite movies from most to least favorite
		Map<String, Movie> sortedMoviesMap = new TreeMap<String, Movie>();
		Map<String, Movie> sortedDescendingMoviesMap = new TreeMap<String, Movie>(Collections.reverseOrder());
		
		movieIterator = mapFavoriteMovies.keySet().iterator();
		while (movieIterator.hasNext()) {
			Movie movieToInsert = (Movie)movieIterator.next();
			sortedMoviesMap.put(((Integer)mapFavoriteMovies.get(movieToInsert)).intValue() + movieToInsert.getTitle() + movieToInsert.getYear(), movieToInsert);
		}
		sortedDescendingMoviesMap.putAll(sortedMoviesMap);
		moviesRented = new ArrayList<Movie>(sortedDescendingMoviesMap.values());
		return moviesRented;
	}
	
	public String toString() {
		// return format: <ID>\t<name>\t$ <balance>
		return ID + "\t" + name + "\t" + "$ " + balance;
	}
}