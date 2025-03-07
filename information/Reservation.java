package movie.information;

public class Reservation extends Movie {
	public String reservationNum;
	public Reservation(String reservationNum,String num, String movieNum, String movieName, String genre) {
		super(num, movieNum, movieName, genre);
		this.reservationNum = reservationNum;
	}
	
}
