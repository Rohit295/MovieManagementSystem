import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Kiosk {
	private Catalogue catalogue;
	private List<Customer> customers;
	
	public Kiosk() {
		catalogue = new Catalogue(this);
		customers = new ArrayList<Customer>(10);
	}
	
	public static void main(String[] args) {
		Kiosk thisKiosk = new Kiosk();
		thisKiosk.use();
	}
	
	/**
	 * 
	 * AMRI: Study 5 Example to understand the 'use' pattern
	 */
	private void use() {
		runKioskMasterView();
		runKioskActions("KioskMasterView");
	}
	
	private void runKioskMasterView() {
		System.out.println(" ");

		System.out.println("Welcome to the Movie Kiosk! Please make a selection from the menu:");
		System.out.println("1. Explore the catalogue.");
		System.out.println("2. View your customer record.");
		System.out.println("3. Show you favorite movies.");
		System.out.println("4. Top up account.");
		System.out.println("5. Enter Admin mode.");
		System.out.println("X. Exit the system.");
		System.out.print("Enter a choice: ");
	}

	private void runKioskActions(String nextView ) {
		while (!nextView.equals("Exit")) {
			if (nextView.equals("KioskMasterView")) {
				nextView = processKioskMasterViewInput();
			} else if (nextView.equals("KioskAdminView")) {
				nextView = processKioskAdminViewInput();
			} else if (nextView.equals("KioskCatalogueView")) {
				nextView = processKioskCatalogueViewInput();
			}
			
			runNextView(nextView);
		}
	}
	
	/**
	 * kioskMasterView is the main Kiosk view
	 * @return
	 */
	private String processKioskMasterViewInput() {
		String nextView = "";
		// ref: https://stackoverflow.com/questions/2506077/how-to-read-integer-value-from-the-standard-input-in-java		
		Scanner inputScanner = new Scanner(System.in);
		String choiceMade = inputScanner.nextLine();
		
		// our user could have entered an Integer OR character. process after knowing what has been entered
		if (choiceMade.equals("X")) {
			nextView = "Exit";
			return nextView;
		}

		// TODO - handle entry of non integer input. alternatively handle everything with ASCII value??
		int choice = Integer.parseInt(choiceMade);
		switch (choice) {
			case 1:
				nextView = "KioskCatalogueView";
				break;
			case 2:
				//nextView = processKioskPatronRecord();
				break;
			case 5:
				nextView = "KioskAdminView";
				break;
			default:
				break;
		}
		
		return nextView;
	}

	private void runKioskAdminView() {
		System.out.println(" ");

		System.out.println("Welcome to the administration menu:");
		System.out.println("1. List all customers.");
		System.out.println("2. Add a customer.");
		System.out.println("3. Remove a customer.");
		System.out.println("4. List all movies.");
		System.out.println("5. Add a movie to the catalogue.");
		System.out.println("6. Remove a movie from the catalogue.");
		System.out.println("R. Return to the previous menu.");
		System.out.print("Enter a choice: ");
	}

	/**
	 * kioskAdminView is the Administration view
	 * @return
	 */
	private String processKioskAdminViewInput() {
		String nextView = "";
		// ref: https://stackoverflow.com/questions/2506077/how-to-read-integer-value-from-the-standard-input-in-java		
		Scanner inputScanner = new Scanner(System.in);
		String choiceMade = inputScanner.nextLine();
		
		// TODO - handle entry of non integer input
		if (choiceMade.equals("R")) {
			nextView = "KioskMasterView";
			return nextView;
		}

		int choice = Integer.parseInt(choiceMade);
		switch (choice) {
			case 1:
				nextView = processAdminListAllCustomers();
				break;
			case 2:
				nextView = processAdminAddCustomer();
				break;
			case 3:
				nextView = processAdminRemoveCustomer();
				break;
			case 4:
				// reusing listAllMovies from Catalogue but ensuring flow stays in Admin View
				listAllMovies();
				nextView = "KioskAdminView";
				break;
			case 5:
				nextView = processAdminAddMovie();
				break;
			case 6:
				nextView = processAdminRemoveMovie();
				break;
			default:
				break;
		}
		
		return nextView;
	}

	private String processAdminListAllCustomers() {
		System.out.println(" ");
		Iterator<Customer> customerIterator = this.customers.listIterator();
		if (!customerIterator.hasNext()) {
			System.out.println("The Kiosk has no customers.");			
			System.out.println(" ");
			return "KioskAdminView";
		}

		// AMRI: understand why I am printing out one customer here and looping for all the others
		System.out.println(customerIterator.next().toString());
		while (customerIterator.hasNext()) {
			System.out.println(customerIterator.next().toString());			
		}

		return "KioskAdminView";
	}

	private String processAdminAddCustomer() {
		System.out.println(" ");
		System.out.println("Adding a new customer.");
		Scanner inputScanner = new Scanner(System.in);

		System.out.print("Enter a new ID: ");
		
		// try to add a new customer with the ID and name entered. If ID is already used, call that out
		int newCustomerID = inputScanner.nextInt();
		inputScanner.nextLine(); // do this to skip the enter button press
		while (isCustomerIDInUse(newCustomerID)) {
			System.out.print("ID is already in use. Enter a new ID: ");
			
			newCustomerID = inputScanner.nextInt();
			inputScanner.nextLine();
		}
		System.out.print("Enter the customer's name: ");
		String newCustomerName = inputScanner.nextLine();

		System.out.print("Enter the customer's initial balance: ");
		int newCustomerBalance = inputScanner.nextInt();
		inputScanner.nextLine(); // do this to skip the enter button press

		Customer newCustomer = new Customer(newCustomerID, newCustomerName, newCustomerBalance);
		this.customers.add(newCustomer);
		System.out.println("Customer added.");

		return "KioskAdminView";
	}
	
	/**
	 * check each of the customers already enrolled to make sure no one has the same ID
	 * @param patronID
	 * @return
	 */
	private boolean isCustomerIDInUse(int customerIDToCheck) {
		Iterator<Customer> customerIterator = this.customers.listIterator();
		while (customerIterator.hasNext()) {
			if (customerIterator.next().getID() == customerIDToCheck)
				return true;
		}
		return false;
	}
	
	private Customer findCustomer(int customerID) {
		Customer customerToReturn = null;
		Iterator<Customer> customerIterator = this.customers.iterator();
		while (customerIterator.hasNext()) {
			Customer oneCustomer = customerIterator.next();
			if (oneCustomer.getID() == customerID) {
				customerToReturn = oneCustomer;
				break;
			}
		}		
		return customerToReturn;
	}

	private String processAdminRemoveCustomer() {
		System.out.println(" ");
		System.out.println("Removing a customer.");
		Scanner inputScanner = new Scanner(System.in);

		// try to remove a customer with the ID entered. If ID is not present, call that out
		System.out.print("Enter a valid customer ID: ");		
		int idCustomerToRemove = inputScanner.nextInt();
		inputScanner.nextLine(); // do this to skip the enter button press
		while (!isCustomerIDInUse(idCustomerToRemove)) {
			System.out.print("ID does not exist. Enter a valid customer ID: ");
			
			idCustomerToRemove = inputScanner.nextInt();
			inputScanner.nextLine();
		}
		
		Customer customerToRemove = findCustomer(idCustomerToRemove);

		// ensure the customer does not have a movie rented out currently
		if (customerToRemove.hasCurrentlyRented()) {
			System.out.print("Customer has currently rented movie and cannot be removed.");			
			return "KioskAdminView";
		}
		
		customers.remove(customerToRemove);
		System.out.println("Removed " + customerToRemove.getName() + " from Kiosk.");			
		return "KioskAdminView";
	}
		
	/**
	 * Add a Movie to the Catalogue
	 * @return
	 */
	private String processAdminAddMovie() {
		System.out.println("Adding a new movie.");
		Scanner inputScanner = new Scanner(System.in);

		System.out.print("Enter the title of the movie: ");
		String newMovieTitle = inputScanner.nextLine();
		
		System.out.print("Enter the year: ");
		String newMovieYear = inputScanner.nextLine();

		System.out.print("Enter the genre: ");
		String newMovieGenre = inputScanner.nextLine();

		System.out.print("Enter the price: ");
		String newMoviePrice = inputScanner.nextLine();

		Movie newMovie= new Movie(newMovieTitle, Integer.parseInt(newMovieYear), Integer.parseInt(newMoviePrice), new Genre(newMovieGenre));
		catalogue.addMovieToCatalogue(newMovie);

		System.out.println("Added " + newMovieTitle + " to catalogue.");
		return "KioskAdminView";
	}
	
	/**
	 * Remove a Movie from the Catalogue.
	 * Remember that the movie being removed, may not be in the catalogue or may be rented out and in either case, cannot be removed from
	 * the catalogue. 
	 * @return
	 */
	private String processAdminRemoveMovie() {
		System.out.println(" ");
		System.out.println("Removing a movie.");
		Scanner inputScanner = new Scanner(System.in);

		System.out.print("Enter the title of the movie: ");
		String titleOfMovieToRemove = inputScanner.nextLine();
		System.out.print("Enter the year: ");
		int yearOfMovieToRemove = Integer.parseInt(inputScanner.nextLine());
		
		Movie movieToRemove = catalogue.findMovie(titleOfMovieToRemove, yearOfMovieToRemove);
		if (movieToRemove == null) {
			System.out.println("Movie " + titleOfMovieToRemove + ", year " + yearOfMovieToRemove + " is not in catalogue.");
		} else if (movieToRemove.getStatus() == Movie.MOVIE_RENTED_OUT) {
			System.out.println("Movie " + titleOfMovieToRemove + ", year " + yearOfMovieToRemove + " is currently rented out.");			
		} else if (catalogue.removeMovieFromCatalogue(titleOfMovieToRemove, yearOfMovieToRemove) != null) {
			System.out.println("Removed " + titleOfMovieToRemove + " from catalogue.");			
		}
		return "KioskAdminView";
	}
	

	private void runNextView(String nextView) {
		if (nextView.equals("KioskMasterView")) {
			runKioskMasterView();			
		} else if (nextView.equals("KioskAdminView")) {
			runKioskAdminView();			
		} else if (nextView.equals("KioskCatalogueView")) {
			runKioskCatalogueView();			
		}
	}
	
	private void runKioskCatalogueView() {
		System.out.println(" ");

		System.out.println("Welcome to the Catalogue! Please make a selection from the menu:");
		System.out.println("1. Display all movies.");
		System.out.println("2. Display all available movies.");
		System.out.println("3. Display all genres.");
		System.out.println("4. Display all movies in a genre.");
		System.out.println("5. Display all movies by year.");
		System.out.println("6. Rent a movie.");
		System.out.println("6. Return a movie.");
		System.out.println("R. Return to the previous menu.");
		System.out.print("Enter a choice: ");
	}

	private String processKioskCatalogueViewInput() {
		String nextView = "";
		
		Scanner inputScanner = new Scanner(System.in);
		String choiceMade = inputScanner.nextLine();
		
		// our user could have entered an Integer OR character. process after knowing what has been entered
		if (choiceMade.equals("R")) {
			nextView = "KioskMasterView";
			return nextView;
		}
		
		int choice = Integer.parseInt(choiceMade);
		switch (choice) {
			case 1:
				nextView = listAllMovies();
				break;
			case 2:
				nextView = listAllAvailableMovies();
				break;
			case 3:
				nextView = listAllGenres();
				break;
			case 4:
				nextView = listAllMoviesInAGenre();
				break;
/*
			case 5:
				nextView = listAllMoviesByYear();
				break;
*/
			case 6:
				nextView = rentAMovie();
				break;
			case 8:
				nextView = returnAMovie();
				break;
			default:
				break;
		}
		
		return nextView;
	}

	/**
	 * List all the Movies in the Catalogue. Since the ask is for all, list everything on the shelf and everything loaned out
	 * @return
	 */
	private String listAllMovies() {
		System.out.println(" ");

		// List everything on the Catalogue and everything rented out
		Iterator<Movie> moviesRentedIterator = this.catalogue.getMoviesRented().iterator();
		Iterator<Movie> moviesAvailableIterator = this.catalogue.getMoviesAvailable().iterator();
		if (!moviesRentedIterator.hasNext() && !moviesAvailableIterator.hasNext()) {
			System.out.println(" ");
			System.out.println("There are no movies in the kiosk.");
		} else {
			System.out.println("The catalogue has the following movies: ");
			while (moviesAvailableIterator.hasNext()) {
				System.out.println(moviesAvailableIterator.next().toString());
			}			
			while (moviesRentedIterator.hasNext()) {
				System.out.println(moviesRentedIterator.next().toString());
			}			
				
		}
		return "KioskCatalogueView";
	}

	/**
	 * List all Movies that are available to Rent. Currently this is all movies in the Catalogue
	 * @return
	 */
	private String listAllAvailableMovies() {
		System.out.println(" ");

		Iterator<Movie> moviesAvailableIterator = this.catalogue.getMoviesAvailable().iterator();
		if (!moviesAvailableIterator.hasNext()) {
			System.out.println("There are no movies available in the Kiosk right now.");
		} else {
			while (moviesAvailableIterator.hasNext()) {
				System.out.println("\t" + moviesAvailableIterator.next().toString());
			}			
		}
		
		return "KioskCatalogueView";
	}

	private String listAllGenres() {
		System.out.println(" ");

		// List all Genres that we know about
		Iterator<Genre> genresIterator = this.catalogue.getGenres().iterator();
		if (!genresIterator.hasNext()) {
			System.out.println("There are no movies in the Kiosk.");
		} else {
			System.out.println("The catalogue has movies in the following genres: ");
			while (genresIterator.hasNext()) {
				System.out.println("\t" + genresIterator.next().toString());
			}			
				
		}
		
		return "KioskCatalogueView";
	}

	private String listAllMoviesInAGenre() {
		Scanner inputScanner = new Scanner(System.in);

		System.out.println("");
		System.out.print("Enter the genre: ");
		String genreName = inputScanner.nextLine();

		boolean isMovieMatchingGenre = false;
		
		Iterator<Movie> moviesAvailableIterator = this.catalogue.getMoviesAvailable().iterator();
		Iterator<Movie> moviesRentedIterator = this.catalogue.getMoviesRented().iterator();

		if (!moviesAvailableIterator.hasNext() && !moviesRentedIterator.hasNext()) {
			System.out.println("This Kiosk has no movies right now.");
		} else {
			// we need to go through movies in Catalogue whether available OR out on loan and see if they belong to this genre
			while (moviesAvailableIterator.hasNext()) {
				Movie movieToCheck = moviesAvailableIterator.next(); 
				if (movieToCheck.getGenre().toString().equals(genreName)) {
					if (!isMovieMatchingGenre) {
						isMovieMatchingGenre = true;
						System.out.println("This Kiosk has the following movies of this genre available: ");
					}
					System.out.println(movieToCheck.toString());
				}
			}			
			while (moviesRentedIterator.hasNext()) {
				Movie movieToCheck = moviesRentedIterator.next(); 
				if (movieToCheck.getGenre().toString().equals(genreName)) {
					if (!isMovieMatchingGenre) {
						isMovieMatchingGenre = true;
						System.out.println("The kiosk has the following movies of this genre available: ");
					}
					System.out.println(movieToCheck.toString());
				}
			}			
		}

		if (!isMovieMatchingGenre) {
			System.out.println("There are no movies in this genre right now.");
		} 			
		
		return "KioskCatalogueView";
	}
	
	private String rentAMovie() {
		Scanner inputScanner = new Scanner(System.in);
		System.out.println(" ");

		// Find a valid customer ID, to rent a movie for
		System.out.print("Enter a valid customer ID: ");		
		int idCustomerWhoWantsToRent = inputScanner.nextInt();
		inputScanner.nextLine(); // do this to skip the enter button press
		while (!isCustomerIDInUse(idCustomerWhoWantsToRent)) {
			System.out.print("ID does not exist. Enter a valid customer ID: ");
			
			idCustomerWhoWantsToRent = inputScanner.nextInt();
			inputScanner.nextLine();
		}
		Customer customerRentingMovie = findCustomer(idCustomerWhoWantsToRent);
		
		// AMRI: verify that you can change the input to also take a year. If NOT, search for movie without expecting year to be passed
		System.out.print("Enter the title of the movie you wish to rent: ");
		String nameMovieToRent = inputScanner.nextLine();
		System.out.print("Enter the year of the movie you wish to rent: ");
		int yearMovieToRent = inputScanner.nextInt();
		inputScanner.nextLine(); // do this to skip the enter button press

		Movie movieToRent = catalogue.findMovie(nameMovieToRent, yearMovieToRent);
		if (movieToRent == null) {
			System.out.println("This movie is not in the Catalogue.");						
			return "KioskCatalogueView";
		} 
		
		int customerRentStatus = customerRentingMovie.canRentThisMovie(movieToRent);
		if (movieToRent.getStatus() == Movie.MOVIE_RENTED_OUT) {
			System.out.println("This movie is already rented out.");						
		} else if (customerRentStatus == Customer.CUSTOMER_OUT_OF_BALANCE) {
			System.out.println("You don't have sufficient funds to rent this movie.");			
		} else if (customerRentStatus == Customer.CUSTOMER_CAN_RENT) {
			customerRentingMovie.rentAMovie(movieToRent);
			catalogue.rentAMovie(movieToRent);
			movieToRent.setStatus(Movie.MOVIE_RENTED_OUT);
			System.out.println("Movie rented.");
		}
		
		return "KioskCatalogueView";
	}
	
	private String returnAMovie() {		
		return "KioskCatalogueView";
	}
}