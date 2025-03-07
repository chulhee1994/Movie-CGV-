package movie.function;

import java.io.*;
import java.util.*;

import movie.information.*;

public class AdminMenu {
	//영화 관리
	MovieManger m = new MovieManger();
	AdminManager am = new AdminManager();
	Scanner sc = new Scanner(System.in);
	//영화 파일
	File moviefile = new File("C:/Temp/movie.txt");
	//예매 파일
	File reservationFile = new File("C:/Temp/reservation.txt");
	//회원 계정 파일
	File userAccountFile = new File("C:/Temp/user.txt");
	//관리자 계정 파일
	File AccountFile = new File("C:/Temp/admin.txt");
	FileReader fr;
	FileWriter fw;
	//영화 리스트
	List<Movie> movieList;
	List<User> reservationList;
	//관리자 메뉴 
	public void adminMainMenu() throws Exception {
		
		System.out.println("1.관리자 로그인");
		System.out.println("2.관리자 회원가입");
		System.out.println("0.뒤로가기");
		System.out.println("메뉴를 선택하세요.");
		String choice = sc.nextLine().trim();
		switch (choice) {
		case "1": 
			adminLogin();
			break;
		case "2":
			adminSignUp();
			break;
		case "0":
			System.out.println("이전 화면으로 돌아갑니다.");
			break;
		default:
			System.out.println("잘못된 입력입니다.");
		}
	}

	//관리자 로그인 메인 메뉴
	private void adminLoginMenu(AdminAccount admin) throws Exception {
		System.out.println(admin.id +"관리자님");
		System.out.println("1.영화 등록하기");
		System.out.println("2.영화 목록보기");
		System.out.println("3.영화 삭제하기");
		System.out.println("4.예매된 영화 전체보기");
		System.out.println("0.메인 화면 돌아가기");
		String choice = sc.nextLine().trim();
		switch (choice) {
		case "1": 
			movieRegist();
			adminLoginMenu(admin);
			break;
		case "2":
			allMovieList();
			adminLoginMenu(admin);
			break;
		case "3":
			movieDelete();
			adminLoginMenu(admin);
			break;
		case "4":
			allReservationList();
			adminLoginMenu(admin);
			 break;
		case "0":
			System.out.println("메인화면으로 돌아갑니다.");
			break;
		default:
			System.out.println("잘못된 입력입니다.");
		}
	}

	//관리자 로그인
	public void adminLogin() throws Exception {
		System.out.print("아이디를 입력해주세요.");
		String id = sc.nextLine().trim();
		System.out.print("비밀번호를 입력해주세요.");
		String pwd = sc.nextLine().trim();
		
		List<AdminAccount> adminAccount = am.adminAccountList();
		//key값을 가지고 관리자 회원가입에 사용 무차별한 회원가입 불가
		//return new AdminUser(id, pwd, null);
		boolean adminLoginCheck = false;
		
		for(AdminAccount account:adminAccount) {
			if(id.trim().equals(account.id.trim())&&
			   pwd.trim().equals(account.pwd.trim()
			    )) {
				adminLoginCheck = true;
				break;
			}
		}
		
		if(adminLoginCheck) {
			adminLoginMenu(new AdminAccount(id, pwd, null));
		}else {
			System.out.println("관리자 로그인 할수 없습니다.");
		}
		
	}
	
	//관리자 회원가입
	private void adminSignUp() throws IOException {
		//리스트 받아오기
		List<AdminAccount> adminAccount = am.adminAccountList();
		//관리자 아이디 중복확인
		String id;
		boolean check;
		do {
			System.out.println("--------관리자 회원가입--------");
			System.out.println("관리자 아이디를 입력해주세요.");
			id = sc.nextLine().trim();
			
			check = false;
			
			for(AdminAccount account : adminAccount) {
				if(id.trim().equals(account.id.trim())) {
					check = true;
					break;
				}
			}
		} while (check);
		
		System.out.println("────────관리자 회원가입────────");
		System.out.println("관리자 비밀번호를 입력해주세요.");
		String pwd = sc.nextLine().trim();
		System.out.println("────────관리자 회원가입────────");
		System.out.println("관리자 키를 입력해주세요.");
		String key = sc.nextLine().trim();
		
		fw = new FileWriter(AccountFile, true);
		String str = id+","+pwd+",";
		fw.write(str.trim());
		fw.close();
	}
	
	//영화 등록
	private void movieRegist() throws Exception {
		List<Movie> movieList = m.movieList();
		//고유번호 겹치는지 확인 겹치면 안됨
		m.printMovieList();
		boolean Check;
		String num ="";
		do {
			System.out.println("고유번호를 입력해주세요.");
			Check = false;
			num = sc.nextLine().trim();
			for(Movie m : movieList) {
				if(m.num.trim().equals(num.trim())) {
					Check = true;
				}
			}
		} while (Check);
		//반복문으로 영화제목이 겹치는게 있으면 영화번호에 입력
		//영화제목이 겹치는게 없으면 새로입력
		//영화제목 입력 받아 겹치는 제목 있으면 
		//영화번호 뽑아온다.
		
		boolean movieNameCheck = false;
		System.out.println("영화제목을 입력해주세요.");
		String movieName = sc.nextLine();
		String movieNum = "";
		
		for(Movie m : movieList) {
			if(m.movieName.equals(movieName)) {
				movieNameCheck = true;
				movieNum = m.movieNum;
				break;
			}
		}
		
		if(movieNameCheck) {
			System.out.println("똑같은 영화제목이 있습니다. 영화번호를 설정합니다.");
		}else {
			System.out.println("영화번호를 입력해주세요.");
			movieNum = sc.nextLine().trim();
		}
		
		System.out.println("장르를 입력해주세요.");
		String genre = sc.nextLine().trim();
		String registStr =num.trim()+","+movieNum.trim()+","+movieName+","+genre+",";

		fw = new FileWriter(moviefile,true);
		fw.write(registStr);
		System.out.println("영화등록이 완료 되었습니다.");
		System.out.println("────────────────────────────────────────────────────────");
		System.out.println("고유번호 :"+num);
		System.out.println("영화번호 :" + movieNum);
		System.out.println("영화제목 :" + movieName);
		System.out.println("영화장르 :" + genre);
		System.out.println("────────────────────────────────────────────────────────");
		
		fw.flush();
		fw.close();
	}
	
	//영화 삭제
	private void movieDelete() throws Exception {
		m.printMovieList();
		//1.영화리스트 불러온다.
		//2.고유번호를 입력 받는다.
		//3.고유번호를 리스트랑 비교 맞는 번호가 있으면 삭제
		//4.예매하고 있는중인 영화가 있으면 삭제 불가능하게 만들어준다.
		movieList = m.movieList();
		reservationList = m.reservationList();
		System.out.print("영화 고유번호를 입력해주세요.");
		String num = sc.nextLine().trim();
		boolean movieCheck = false;
		boolean reservationCheck = false;
		
		//영화 배열 반복 비교
		for(int i=0; i<movieList.size(); i++) {
			if(num.trim().equals(movieList.get(i).num.trim())) {
				movieCheck = true;
				//movieList.remove(i);
			}
		}
		//예매 배열 반복 비교
		for(int i =0; i<reservationList.size(); i++) {
			if(num.trim().equals(reservationList.get(i).num.trim())) {
				reservationCheck = true;
				break;
			}
		}
		
		//삭제된 배열을 파일에 다시 덮어쓰기
		if(movieCheck) {
			fw = new FileWriter(moviefile, false);
			String reservationStr = "";
			for(Movie m : movieList) {
				reservationStr += m.num+","+m.movieNum+","+m.movieName+","+m.genre+",";
			}
			fw.write(reservationStr.trim());
			System.out.println(num+"번 영화가 삭제되었습니다.");
		}else {
			//삭제 안함
			System.out.println("고유번호가 "+num+"인 영화가 없습니다.");
		}
		fw.flush();
		fw.close();
	}//메서드
	
	//영화 전체리스트 출력
	private void allMovieList() throws Exception {
		System.out.println("영화 전체 리스트");
		m.printMovieList();
	}
	
	//예매된 리스트 전체 출력
	private void allReservationList() throws Exception {
		System.out.println("예매 전체 리스트");
		m.printReservationList();
	}
}
