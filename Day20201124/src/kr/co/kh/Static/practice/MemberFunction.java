package kr.co.kh.Static.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberFunction {
	public static String session;

	static void session() {
		session = null;
	}

	public static void register(String protocol, BufferedReader input, Statement stmt, Connection conn, String sql,
			int cnt) throws IOException, SQLException {
		if (session != null) {
			System.out.println("이미 회원가입 하셨습니다.");
		} else if (session == null) {
			System.out.println("아이디 입력 :");
			String id = input.readLine();
			System.out.println("패스워드 입력 :");
			String pw = input.readLine();
			System.out.println("주소 입력 :");
			String addr = input.readLine();
			System.out.println("전화번호 입력 :");
			String tel = input.readLine();
			stmt = conn.createStatement();
			sql = "INSERT INTO member (id, pw, addr, tel) values('" + id + "',+'" + pw + "',+'" + addr + "',+'" + tel
					+ "')";
			cnt = stmt.executeUpdate(sql);
			System.out.println(cnt + "명 회원등록 되었습니다.");
		}
	}

	public static void list(Statement stmt, String sql, Connection conn) throws IOException, SQLException {
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
	}

	public static void search(Statement stmt, String sql, Connection conn, BufferedReader input)
			throws IOException, SQLException {
		System.out.println("찾는 아이디 입력 : ");
		String searchId = input.readLine();
		stmt = conn.createStatement();
		sql = "SELECT id, pw, addr, tel from member where id = '" + searchId + "'";
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.isBeforeFirst()) {
			System.out.println("찾는 ID가 없습니다.");
		}
		while (rs.next()) {
			String id1 = rs.getString("id");
			String pw1 = rs.getString("pw");
			String addr1 = rs.getString("addr");
			String tel1 = rs.getString("tel");
			System.out.println(id1 + "\t" + pw1 + "\t" + addr1 + "\t" + tel1);
		}
	}

	public static void delete(Statement stmt, Connection conn, String sql, int cnt) throws SQLException {
		if (session == null) {
			System.out.println("먼저 로그인을 해주세요.");
		} else if (session != null) {
			stmt = conn.createStatement();
			sql = "DELETE from member where id = '" + session + "' ";
			cnt = stmt.executeUpdate(sql);
			System.out.println("회원 탈퇴 되셨습니다.");
			session = null;
		}
	}

	public static void update(Statement stmt, Connection conn, String sql, BufferedReader input, int cnt)
			throws IOException, SQLException {
		if (session == null) {
			System.out.println("먼저 로그인을 해주세요.");
		} else if (session != null) {
			stmt = conn.createStatement();
			sql = "SELECT id, pw, addr, tel from member where id = '" + session + "' ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				String id1 = rs.getString("id");
				String pw1 = rs.getString("pw");
				String addr1 = rs.getString("addr");
				String tel1 = rs.getString("tel");
				System.out.println(id1 + "\t" + pw1 + "\t" + addr1 + "\t" + tel1);
			}
			System.out.println("아이디 입력 :");
			String idUpdate = input.readLine();
			System.out.println("패스워드 입력 :");
			String pwUpdate = input.readLine();
			System.out.println("주소 입력 :");
			String addrUpdate = input.readLine();
			System.out.println("전화번호 입력 :");
			String telUpdate = input.readLine();
			stmt = conn.createStatement();
			sql = "UPDATE member SET id = '" + idUpdate + "', pw = '" + pwUpdate + "', addr = '" + addrUpdate
					+ "', tel = '" + telUpdate + "'";
			session = idUpdate;
			cnt = stmt.executeUpdate(sql);
			System.out.println(cnt + "명 회원의 정보가 수정되었습니다.");
		}
	}

	public static void login(Statement stmt, Connection conn, String sql, BufferedReader input)
			throws IOException, SQLException {
		String loginId = null;
		String loginPw = null;
		System.out.println("아이디 입력 : ");
		String id = input.readLine();
		System.out.println("패스워드 입력 : ");
		String pw = input.readLine();
		stmt = conn.createStatement();
		sql = "SELECT id, pw from member";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			loginId = rs.getString("id");
			loginPw = rs.getString("pw");
		}
		if (!loginId.equals(id)) {
			System.out.println("id가 없습니다.");
		} else if (!loginPw.equals(pw)) {
			System.out.println("패스워드가 틀렸습니다..");
		} else {
			System.out.println("환영합니다! 로그인 되셨습니다.");
			session = id;
		}
	}

	public static void logout() {
		if (session != null) {
			System.out.println("로그아웃 되셨습니다.");
			session = null;
		} else if (session == null) {
			System.out.println("먼저 로그인을 해주세요.");
		}
	}

}
