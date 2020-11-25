package kr.co.kh.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberExecuter {
	public static String session;
	static {
		session = null;
	}

	public static void main(String[] args) {
		BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
		Connection conn = null;
		Statement stmt = null;
		String sql = null;
		String protocol = null;
		String id = null;
		String pw = null;
		String addr = null;
		String tel = null;
		int cnt = 0;
	
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "pnj0213", "dkdlxl");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		while (true) {
			System.out.println("R: 회원가입 L:회원목록 S:ID찾기 D:회원탈퇴 E:회원수정 I:로그인 O:로그아웃");
			try {
				protocol = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (protocol.equalsIgnoreCase("r")) { //회원가입
				if(session != null) {
					System.out.println("이미 회원가입 하셨습니다.");
					continue;
				}
				try {
					System.out.println("아이디 입력 :");
					id = input.readLine();
					System.out.println("패스워드 입력 :");
					pw = input.readLine();
					System.out.println("주소 입력 :");
					addr = input.readLine();
					System.out.println("전화번호 입력 :");
					tel = input.readLine();
					stmt = conn.createStatement();
					sql = "INSERT INTO member (id, pw, addr, tel) values('"+id+"',+'"+pw+"',+'"+addr+"',+'"+tel+"')";
					cnt = stmt.executeUpdate(sql);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println(cnt + "명 회원등록 되었습니다.");
				
			}  //회원가입 끄으으으읕
			else if (protocol.equalsIgnoreCase("l")) { //회원목록
				try {
					stmt = conn.createStatement();
					sql = "SELECT id, pw, addr, tel from member";
					ResultSet rs = stmt.executeQuery(sql);
					while (rs.next()) {
						String id1 = rs.getString("id");
						String pw1 = rs.getString("pw");
						String addr1 = rs.getString("addr");
						String tel1 = rs.getString("tel");
						System.out.println(id1 + "\t" + pw1 + "\t" + addr1 + "\t" + tel1);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}  //회원목록 끄으으으읕
			else if (protocol.equalsIgnoreCase("s")) { //id찾기
				System.out.println("찾는 아이디 입력 : ");
				try {
					String searchId = input.readLine();
					stmt = conn.createStatement();
					sql = "SELECT id, pw, addr, tel from member where id = '"+searchId+"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(!rs.isBeforeFirst()) {
						System.out.println("찾는 ID가 없습니다.");
						continue;
					}
					while (rs.next()) {
						String id1 = rs.getString("id");
						String pw1 = rs.getString("pw");
						String addr1 = rs.getString("addr");
						String tel1 = rs.getString("tel");
						System.out.println(id1 + "\t" + pw1 + "\t" + addr1 + "\t" + tel1);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}  //id찾기 끄으으으읕
			else if (protocol.equalsIgnoreCase("d")) { //회원탈퇴
				if(session == null) {
					System.out.println("먼저 로그인을 해주세요.");
				} else if(session != null) {
					try {
						stmt = conn.createStatement();
						sql = "DELETE from member where id = '"+ session +"' ";
						cnt = stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println("회원 탈퇴 되셨습니다.");
					session = null;
				}
			}  //회원탈퇼 끄으으으읕
			else if (protocol.equalsIgnoreCase("e")) { //회원수정
				if(session == null) {
					System.out.println("먼저 로그인을 해주세요.");
				} else if(session != null) {
					try {
						stmt = conn.createStatement();
						sql = "SELECT id, pw, addr, tel from member where id = '"+ session +"' ";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							String id1 = rs.getString("id");
							String pw1 = rs.getString("pw");
							String addr1 = rs.getString("addr");
							String tel1 = rs.getString("tel");
							System.out.println(id1 + "\t" + pw1 + "\t" + addr1 + "\t" + tel1);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println("아이디 입력 :");
					try {
						String idUpdate = input.readLine();
						System.out.println("패스워드 입력 :");
						String pwUpdate = input.readLine();
						System.out.println("주소 입력 :");
						String addrUpdate = input.readLine();
						System.out.println("전화번호 입력 :");
						String telUpdate = input.readLine();
						stmt = conn.createStatement();
						sql = "UPDATE member SET id = '"+idUpdate+"', pw = '"+pwUpdate+"', addr = '"+addrUpdate+"', tel = '"+telUpdate+"'";
						session = idUpdate;
						cnt = stmt.executeUpdate(sql);
					} catch (IOException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println(cnt + "명 회원의 정보가 수정되었습니다.");
				}
				
			}  //회원수정 끄으으으읕
			else if (protocol.equalsIgnoreCase("i")) { //로그인
				if(session != null) {
					System.out.println("로그인 중입니다..");
					continue;
				}
				String loginId = null;
				String loginPw = null;
				System.out.println("아이디 입력 : ");
				try {
					id = input.readLine();
					System.out.println("패스워드 입력 : ");
					pw = input.readLine();
					
					stmt = conn.createStatement();
					sql = "SELECT id, pw from member where id = '"+id+"'";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()) {
						loginId = rs.getString("id");
						loginPw = rs.getString("pw");
					}
					if(!loginId.equals(id)) {
						System.out.println("id가 없습니다.");
					} else if(!loginPw.equals(pw)) {
						System.out.println("패스워드가 틀렸습니다..");
					} else {
						System.out.println("환영합니다! 로그인 되셨습니다.");
						session = id;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}  //로그인 끄으으으읕
				/*
				sql = "SELECT id from member where id = '"+id+"'";
				String sql1 = "SELECT pw from member where pw = '"+pw+"'";
				cnt = stmt.executeQuery(sql);
				int cnt1 = stmt.executeQuery(sql1);
				if(cnt == 0){
				System.out.println("id가 없습니다.");
				} else if(cnt1 == 0){
				System.out.println("패스워드가 틀렸습니다..");
				} else if(cnt !=0 && cnt1 !=0){
				System.out.println("환영합니다! 로그인 되셨습니다.");
				session = id;
				}
				*/
			}
			else if (protocol.equalsIgnoreCase("o")) { //로그아웃
				if(session != null) {
					System.out.println("로그아웃 되셨습니다.");
					session = null;
				} else if(session == null) {
					System.out.println("먼저 로그인을 해주세요.");
				}
			}  //로그아웃 끄으으으으읕
		}	//반복문 끝
	}
}
