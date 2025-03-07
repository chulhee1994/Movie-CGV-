package movie.function;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import movie.information.AdminAccount;


public class AdminManager {
	//관리자 파일
	File adminFile = new File("C:/Temp/admin.txt");
	FileReader fr;
	FileWriter fw;
	
	char[] cbuf = new char[1000];
	int byteNo;
	String data;
	//관리자 파일 없으면 만들기 공백 상단 입력 메서드
	public void setAdmin() throws IOException {
		
		if(!adminFile.exists()) {
			adminFile.createNewFile();
			
		fw = new FileWriter(adminFile);
		fw.write("아이디,비밀번호,");
		fw.flush();
		fw.close();
		}
	}
	
	
	//관리자 아이디 리스트 출력
	public List<AdminAccount> adminAccountList() throws IOException{
		setAdmin();
		fr = new FileReader(adminFile);
		List<AdminAccount> AccountList = new ArrayList<AdminAccount>();
		
		while ( (byteNo=fr.read(cbuf)) != -1) {
			data = new String(cbuf,0,byteNo);
		}
		String[] str = data.split(",");
		
		for(int i=0; i<str.length; i+=2) {
			AccountList.add(new AdminAccount(str[i],str[i+1], ""));
		}
		fr.close();
		
		return AccountList;
	}
	
	//예매된 영화리스트 전체출력
}
