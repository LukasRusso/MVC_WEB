package ec.ftt.Model;


public class Movie {
	private long id;
	private String name;
	private String producer;
	private String genre;
	private String releaseDate;
	public Movie() {

	}
	public Movie(Long id, String name, String producer, String genre, String releaseDate) {
		super();
		setId(id);
		setName(name);
		setProducer(producer);
		setGenre(genre);
		setReleaseDate(releaseDate);
	}
	public Movie(String name, String producer, String genre, String releaseDate) {
		super();
		setName(name);
		setProducer(producer);
		setGenre(genre);
		setReleaseDate(releaseDate);
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProducer() {
		return producer;
	}
	public void setProducer(String producer) {
		this.producer = producer;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

}
