package model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class WishList {
	
	private String userId;
	private String imdbId;	
	private int day,month,year;
	private String name;
	private String poster;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

		
	public int getDay() {
		return day;
	}
	public int  getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}

	
	public void setDate(int day, int month, int year) {
		// TODO Auto-generated method stub
		this.day=day;
		this.month=month;
		this.year=year;
	}

}
