import java.util.List;

public class Customer {

	private int ID;
	private String name;
	private int balance;
	private List<Movie> currentlyRented;
	private List<Movie> rentingHistory;
	
	public Customer(int ID, String name, int balance) {
		this.ID = ID;
		this.name = name;
		this.balance = balance;
	}
	
	public String toString() {
		// return format: <ID>\t<name>\t$ <balance>
		return "" + ID + "\t" + name + "\t" + "$ " + balance;
	}
}
