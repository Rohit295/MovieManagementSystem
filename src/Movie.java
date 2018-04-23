
public class Movie {
	private String title;
	private int year;
	private int price;
	private Genre genre;
	private int status;
	
	private Customer rentedBy;

	// Constants describing movie's delete status
	public static final int MOVIE_AVAILABLE = 1;	
	public static final int MOVIE_RENTED_OUT = 2;	
	
	public Movie(String title, int year, int price, Genre genre) {
		this.title = title;
		this.year = year;
		this.price = price;
		this.genre = genre;
		this.status = MOVIE_AVAILABLE;
	}
	
	@Override
	public String toString() {
		// format: <year>\t<title>\t<genre name>\t$ <price
		return year + "\t" + title + "\t" + genre.getName() + "\t" + "$ " + price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public int getStatus() {
		return status;
	}
	

	public void rentMovie(Customer rentedBy) {
		this.rentedBy = rentedBy;
		status = MOVIE_RENTED_OUT;
	}

	public void returnMovie() {
		this.rentedBy = null;
		status = MOVIE_AVAILABLE;
	}
}