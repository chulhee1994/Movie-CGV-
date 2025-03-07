package movie.app;

import java.util.Scanner;

import movie.function.AdminMenu;
import movie.function.UserMenu;

public class MainApp {
	
	public static void main(String[] args) throws Exception{
		Scanner sc = new Scanner(System.in);
		UserMenu um = new UserMenu();
		AdminMenu am = new AdminMenu();
		
		String choice = null;
		do {
			System.out.println();
			System.out.println("┌───────────────────────────┐");
			System.out.println("│  ██████  ██████  ██    ██ │");
			System.out.println("│ ██      ██       ██    ██ │");
			System.out.println("│ ██      ██   ███ ██    ██ │" );
			System.out.println("│ ██      ██    ██  ██  ██  │");
			System.out.println("│  ██████  ██████    ████   │");
			System.out.println("└───────────────────────────┘");
			System.out.println("1.유저 로그인");
			System.out.println("2.회원가입");
			System.out.println("3.관리자 메뉴 이동");
			System.out.println("0.프로그램 종료");
			System.out.print("메뉴를 선택하세요:");
			choice = sc.nextLine().trim();
		
				switch (choice) {
				case "1": 
					um.login();
					break;
				case "2":
					um.signUp();
					break;
				case "3": 
					am.adminMainMenu();
					break;
				case "0":
					System.out.println("프로그램을 종료합니다.");
					break;
				}
		}while(!choice.equals("0"));
		
		
	}
}
