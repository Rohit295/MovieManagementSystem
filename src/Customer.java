import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Customer {

	private int ID;
	private String name;
	private int balance;
	private List<Movie> currentlyRented;
	private Map<Movie, Integer> rentingHistory;
	
	public static final int CUSTOMER_CAN_RENT = 0;
	public static final int CUSTOMER_OUT_OF_BALANCE = 1;
	
	public Customer(int ID, String name, int balance) {
		this.ID = ID;
		this.name = name;
		this.balance = balance;
		
		currentlyRented = new ArrayList<Movie>(5);
		rentingHistory = new HashMap<Movie, Integer>(5);
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
		
		// Add this Movie to the currently rented list & adjust the balance
		currentlyRented.add(movieToRent);
		balance -= movieToRent.getPrice();
		
		// Also add this movie to the Renting History. If already present, increase the renting count. If not, add to History
		boolean movieAlreadyInHistory = false;
		Iterator<Movie> rentHistoryIterator = rentingHistory.keySet().iterator();
		while (rentHistoryIterator.hasNext()) {
			Movie oneMovie = rentHistoryIterator.next();
			if (oneMovie.getTitle().equals(movieToRent.getTitle()) && oneMovie.getYear() == movieToRent.getYear()) {
				movieAlreadyInHistory = true;
				Integer rentCount = rentingHistory.get(oneMovie) + 1;
				rentingHistory.replace(oneMovie, rentCount);
			}
		}
		if (!movieAlreadyInHistory) {
			rentingHistory.put(movieToRent, new Integer(1));
		}
		return movieToRent;
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
	
	public String toString() {
		// return format: <ID>\t<name>\t$ <balance>
		return ID + "\t" + name + "\t" + "$ " + balance;
	}
}