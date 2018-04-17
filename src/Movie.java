
public class Movie {
	private String title;
	private int year;
	private int price;
	private Genre genre;

	@Override
	public String toString() {
		// format: <year>\t<title>\t<genre name>\t$ <price
		return year + "\t" + title + "\t" + genre.getName() + "\t" + "$ " + price;
	}


}
