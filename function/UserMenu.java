package movie.function;

import java.io.*;
import java.util.*;

import movie.information.Movie;
import movie.information.User;
import movie.information.UserAccount;

public class UserMenu {
	//영화 관리
	MovieManger m = new MovieManger();
	Scanner sc = new Scanner(System.in);
	//영화 파일
	File moviefile = new File("C:/Temp/movie.txt");
	//예매 파일
	File reservationFile = new File("C:/Temp/reservation.txt");
	//회원 계정 파일
	File userAccountFile = new File("C:/Temp/user.txt");
	//입출력 한개씩 읽어올 값
	int byteNo;
	char[] cbuf = new char[1000];
	String data;
	//영화 --> 한개씩 넣는다.
	FileReader fr;
	//예매하기
	FileWriter fw;
	
	//회원 로그인 메인 메뉴
	private void UserLoginMenu(String id) throws Exception {
		System.out.println(id+" 회원");
		System.out.println("1.영화 예매하기");
		System.out.println("2.예매 확인하기");
		System.out.println("3.예매 취소하기");
		System.out.println("0.메인 화면 돌아가기");
		String choice = sc.nextLine().trim();
		switch (choice) {
		case "1": 
			reservationMenu();
			UserLoginMenu(id);
			break;
		case "2":
			reservationSearch();
			UserLoginMenu(id);
			break;
		case "3":
			reservationCancleMenu();
			UserLoginMenu(id);
			break;
		case "0":
			System.out.println("메인화면으로 돌아갑니다.");
			break;
		default:
			System.out.println("잘못된 입력입니다.");
		}
	}
	
	//회원가입 메서드
	public void signUp() throws IOException {
		//회원파일을 읽어서 리스트에 저장 id중복 확인
		
		List<UserAccount> accountList = m.accountList();
		fr = new FileReader(userAccountFile);
		
		String id;
		boolean Check;
			do {
				System.out.println("──────────회원가입──────────");
				Check = false;
				System.out.print("아이디를 입력해주세요.");
				id = sc.nextLine().trim();
				
				for(UserAccount user : accountList) {
					
					if(id.trim().equals(user.id)) {
						Check = true;
						break;
					}
				}	
			}while(Check);

		//아이디 입력후 비밀번호 입력
			System.out.println("──────────회원가입──────────");
		System.out.print("비밀번호를 입력해주세요.");
		String pwd = sc.nextLine().trim();
		fw = new FileWriter(userAccountFile,true);
		String account = id+","+pwd+",";
		System.out.println(id +"님 회원가입이 완료되었습니다.");
		fw.write(account);
		fw.close();
	}//회원가입메서드 끝
			
	//회원 로그인 메서드
	public void login() throws Exception {
		//계정 정보 불러오기
		List<UserAccount> accountList = m.accountList();
		
		//아이디 비밀번호 받기
		System.out.print("아이디를 입력해주세요.");
		String id = sc.nextLine().trim();
		System.out.print("비밀번호를 입력해주세요.");
		String pwd = sc.nextLine().trim();
		
		//정보 리스트 반복해서 아이디 비밀번호확인하기	
		//맞으면 예매 메서드로 넘어가기 
		boolean loginCheck = false;
		for(UserAccount user :accountList) {
			//아이디가 같고 비밀번호가 같을때
			if(id.trim().equals(user.id) && pwd.trim().equals(user.pwd)) {
				loginCheck = true;
			}
		}
			//로그인 체크되면 예매화면 넘어가기 아이디도 가져가
			if(loginCheck==true) {
				UserLoginMenu(id);
			}else {
				System.out.println("로그인 할수 없습니다.");
			}
	}

	//영화 예매 메서드
	private void reservationMenu() throws Exception {
		m.printMovieList();
		List<Movie>movieList = m.movieList();
		//영화리스트 출력하기
		fw = new FileWriter("C:/Temp/reservation.txt",true);
		//영화 출력 끝
		System.out.print("예매할 영화의 번호를 입력해주세요.");
		String numChoice = sc.nextLine();
		
		boolean reservationCheck = false;
		//예매 처리
		for(Movie v : movieList) {
			//선택한 번호랑 예매번호랑 같을때
			if(numChoice.equals(v.num)) {
				
				//랜덤예약번호 생성
				int randomNum = (int)(Math.random()*10000);
				/*
				 한번은 무조건 실행후 예매번호 반복해서 비교한뒤 겹치면 다시생성 겹치는게 없으면 그대로 넣기 처리문
				 */
		String reservationStr =randomNum+","+v.num+"," + v.movieNum+","
				+v.movieName+"," +v.genre+",";
		//System.out.println(reservationStr);
				fw.write(reservationStr);
				System.out.println("영화가 예매되었습니다 예매번호 : ["+randomNum + "]");
				reservationCheck = true;
			}
		}
		
		if(reservationCheck==false) {
			System.out.println("────────────────────────────────────────────────────────");
			System.out.println("예매할 영화가 없습니다.");
			System.out.println("────────────────────────────────────────────────────────");
		}
		fw.flush();
		fw.close();
	}//예매 메서드
	
	//예매 확인 메서드 배열을 불러와 대표값이랑 비교
	public void reservationSearch() throws Exception {
		List<User>reservationList = m.reservationList();
		System.out.print("예매번호를 입력하세요.");
		String reservationNum = sc.nextLine();
		boolean movieCheck = false;
		//반복해서 확인하는데 리스트가 끝날때까지 없으면 false로 없습니다 출력
		for(User u :reservationList) {
			//예약번호랑 자기 번호가 맞을경우 
			if(u.reservationNum.equals(reservationNum)) {
				System.out.println("예매한 영화가 확인되었습니다.");
				System.out.printf("%10s %10s %10s %10s %10s\n","예매번호","영화번호","발급번호","영화제목","장르");
				System.out.println("────────────────────────────────────────────────────────");
				System.out.println(u.toString());
				System.out.println("────────────────────────────────────────────────────────");
				movieCheck =true;
			}
		}
		//예매한 영화가 없을경우 출력
		if(movieCheck ==false) {
			System.out.println("────────────────────────────────────────────────────────");
			System.out.println("예매하신 영화가 없습니다.");
			System.out.println("────────────────────────────────────────────────────────");
		}
		
	}
	//영화 예매 취소 메서드 예매배열을 불러와 대표값이랑 같은 객체 제거
	public void reservationCancleMenu() throws Exception {
		List<User>reservationList = m.reservationList();
		fw = new FileWriter(reservationFile);
		/*
		1.영화 예매 배열을 가져온다.
		2.취소할 예매번호를 입력받는다.
		3.배열의 reservationNum이랑 예매번호랑 비교한다.
		같을경우 --> 해당인덱스를 제거 --> 문자열에 ,붙여서 길게만들기 --> 파일 클리어 --> 파일업로드
		다를경우 --> 인덱스 제거 안함 
		
		5.문자열에 ,를 추가하여 파일을 다시쓴다.
		*/
		//fw = new FileWriter(reservationFile);
		System.out.print("예매취소할 예매번호를 입력해주세요.");
		String cancleNum = sc.nextLine();
		
		boolean cancleCheck = false;
		for(int i=0; i<reservationList.size(); i++) {
			//번호가 있을경우 제거
			if(cancleNum.equals(reservationList.get(i).reservationNum.trim())) {
				reservationList.remove(i);
				System.out.println(cancleNum + "번 예매번호가 취소되었습니다.");
				System.out.println("────────────────────────────────────────────────────────");
				cancleCheck = true;
			}
			//배열을 문자열로 바꾸기
			//파일 내용 삭제
		}
		//번호가 없을경우 제거  X
		if(cancleCheck == false) {
			System.out.println("취소하실 영화가 없습니다.");
		}
		//문자열을 파일에 다시 쓴다.
		String removeStr = " ";
		for(User u : reservationList) {
			removeStr += u.reservationNum + "," + u.num +"," + u.movieNum+","+u.movieName+","+u.genre+",";
		}
		fw.write("");
		fw.write(removeStr);
		fw.close();
	}
}
