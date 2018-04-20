
public class Movie {
	private String title;
	private int year;
	private int price;
	private Genre genre;

	public Movie(String title, int year, int price, Genre genre) {
		this.title = title;
		this.year = year;
		this.price = price;
		this.genre = genre;
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
	
}