package movie.function;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import movie.information.Movie;
import movie.information.User;
import movie.information.UserAccount;

public class MovieManger {
	//영화 파일
	File moviefile = new File("C:/Temp/movie.txt");
	
	//예매 파일
	File reservationFile = new File("C:/Temp/reservation.txt");
	
	//계정 파일
	File userAccountFile = new File("C:/Temp/user.txt");
	//입출력 한개씩 읽어올 값
	int byteNo;
	
	char[] cbuf = new char[1000];
	String data;
	
	//영화 --> 한개씩 넣는다.
	FileReader fr;
	//예매하기
	FileWriter fw;
	
	
	//영화리스트 공백 상단 입력 메서드
	//영화 파일 없으면 만든다.
	public void setMovie() throws IOException {

		if(!moviefile.exists()) {
			moviefile.createNewFile();
		fw = new FileWriter(moviefile,true);
		fw.write("고유번호,영화번호,영화제목,장르,");
		fw.flush();
		fw.close();
		}
	}
	
	//예매리스트 공백 상단 입력 메서드
	//예매 파일 없으면 만든다.
	public void setReservation() throws IOException{
	
		
			if(!reservationFile.exists()) {
				reservationFile.createNewFile();
			
		fw = new FileWriter(reservationFile, true);
		fw.write("예매번호,고유번호,영화번호,영화제목,장르,");
		fw.flush();
		fw.close();
			}
	}
	
	
	//계정 정보 공백 상단 입력 메서드
	//관리자 파일 없으면 만든다.
	public void setAccount() throws IOException {
		
		if(!userAccountFile.exists()) {
			userAccountFile.createNewFile();
		
		fw = new FileWriter(userAccountFile,true);
		fw.write("아이디,비밀번호,");
		fw.flush();
		fw.close();
		}
	}
	//영화 전체 리스트 출력
	public void printMovieList() throws Exception {
		
		List<Movie>movieList = movieList();
		System.out.println("────────────────────────────────────────────────────────");
		for(Movie v : movieList) {
			System.out.printf("%10s %10s %10s %10s\n",
					v.num,v.movieNum,v.movieName,v.genre
					);
		}
		System.out.println("────────────────────────────────────────────────────────");
	}
	
	//영화 전체 예매리스트 출력
	public void printReservationList() throws Exception{
		List<User>reservationList = reservationList();
		System.out.println("────────────────────────────────────────────────────────");
		for(User u : reservationList) {
			System.out.printf("%10s %10s %10s %10s %10s\n",
					u.reservationNum,u.num,u.movieNum,u.movieName,u.genre
					);
		}
		System.out.println("────────────────────────────────────────────────────────");
	}
	
	//영화 리스트 출력 메서드
		public List<Movie> movieList() throws Exception  {
			List<Movie>movieList = new ArrayList<Movie>();
			setMovie();
			fr = new FileReader(moviefile);
			
			
			//영화 전체목록을 파일 입력을 통해 받아온다.
			while( (byteNo=fr.read(cbuf) ) != -1 ){
				data = new String(cbuf,0,byteNo);
			}
			//문자열을 ,를 기준으로 잘라서 배열에 보관
			String[] str = data.split(",");
				
				//문자열을 배열에 담기
				for(int i=0; i<str.length; i+=4) {
				movieList.add(new Movie(str[i], str[i+1], str[i+2], str[i+3]));
				}
				fr.close();
				return movieList;
		}
		
		//영화 예매 리스트 출력 매서드
		public List<User> reservationList() throws IOException{
			List<User>reservationList = new ArrayList<User>();
			setReservation();
			fr = new FileReader(reservationFile);
		
			if(reservationList !=null) {
				reservationList.remove(reservationList);
			}
			
			while( (byteNo=fr.read(cbuf) ) != -1 ){
				data = new String(cbuf,0,byteNo);
			}
			//문자열을 ,를 기준으로 잘라서 배열에 보관
			String[] str = data.split(",");
				//문자열을 배열에 담기
				for(int i=0; i<str.length; i+=5) {
				reservationList.add(new User(str[i], str[i+1], str[i+2], str[i+3],str[i+4]));
				}
				fr.close();

				return reservationList;
		}
		
		//회원 정보 출력 메서드
		public List<UserAccount> accountList() throws IOException{
			List<UserAccount> accountList = new ArrayList<UserAccount>();
			//파일이 없으면 만든다.

			setAccount();
	
			fr = new FileReader(userAccountFile);
			
			
			while( (byteNo=fr.read(cbuf) ) != -1 ){
				data = new String(cbuf,0,byteNo);
			}	
			
			String[] str = data.trim().split(",");
			
			for(int i =0; i<str.length; i+=2) {
				accountList.add(new UserAccount(str[i], str[i+1]));
			}
			fr.close();
			return accountList;
			
		}
		
		
}
