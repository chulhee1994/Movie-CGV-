package movie.information;

public class User {
	public String reservationNum; //예매번호
	public String num;	//발급번호
	public String movieNum; //영화번호
	public String movieName; //영화제목
	public String genre;	//장르
	


	public User(String reservationNum, String num, String movieNum, String movieName, String genre) {
		super();
		this.reservationNum = reservationNum;
		this.num = num;
		this.movieNum = movieNum;
		this.movieName = movieName;
		this.genre = genre;
	}



	@Override
	public String toString() {
		String str = 
				String.format("%10s %10s %10s %10s %10s\n",reservationNum,num,movieNum,movieName,genre);
		return str ;
	}
	
	
}
