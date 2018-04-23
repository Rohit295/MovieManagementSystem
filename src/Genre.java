public class Genre {
	private String name;
	private int moviesInThisGenre;

	public Genre(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public int getMoviesInThisGenre() {
		return moviesInThisGenre;
	}
	
	public void addToGenre() {
		moviesInThisGenre++;
	}

	public void removeFromGenre() {
		moviesInThisGenre--;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Genre) {
			// ref https://stackoverflow.com/questions/513832/how-do-i-compare-strings-in-java
			return name.equals(((Genre)obj).name);
			//return this.name == ((Genre)obj).name;
		} else {
			return super.equals(obj);			
		}
	}

	@Override
	public String toString() {
		return name;
	}
	
}