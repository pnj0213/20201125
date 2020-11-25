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
			System.out.println("�̹� ȸ������ �ϼ̽��ϴ�.");
		} else if (session == null) {
			System.out.println("���̵� �Է� :");
			String id = input.readLine();
			System.out.println("�н����� �Է� :");
			String pw = input.readLine();
			System.out.println("�ּ� �Է� :");
			String addr = input.readLine();
			System.out.println("��ȭ��ȣ �Է� :");
			String tel = input.readLine();
			stmt = conn.createStatement();
			sql = "INSERT INTO member (id, pw, addr, tel) values('" + id + "',+'" + pw + "',+'" + addr + "',+'" + tel
					+ "')";
			cnt = stmt.executeUpdate(sql);
			System.out.println(cnt + "�� ȸ����� �Ǿ����ϴ�.");
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
		System.out.println("ã�� ���̵� �Է� : ");
		String searchId = input.readLine();
		stmt = conn.createStatement();
		sql = "SELECT id, pw, addr, tel from member where id = '" + searchId + "'";
		ResultSet rs = stmt.executeQuery(sql);
		if (!rs.isBeforeFirst()) {
			System.out.println("ã�� ID�� �����ϴ�.");
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
			System.out.println("���� �α����� ���ּ���.");
		} else if (session != null) {
			stmt = conn.createStatement();
			sql = "DELETE from member where id = '" + session + "' ";
			cnt = stmt.executeUpdate(sql);
			System.out.println("ȸ�� Ż�� �Ǽ̽��ϴ�.");
			session = null;
		}
	}

	public static void update(Statement stmt, Connection conn, String sql, BufferedReader input, int cnt)
			throws IOException, SQLException {
		if (session == null) {
			System.out.println("���� �α����� ���ּ���.");
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
			System.out.println("���̵� �Է� :");
			String idUpdate = input.readLine();
			System.out.println("�н����� �Է� :");
			String pwUpdate = input.readLine();
			System.out.println("�ּ� �Է� :");
			String addrUpdate = input.readLine();
			System.out.println("��ȭ��ȣ �Է� :");
			String telUpdate = input.readLine();
			stmt = conn.createStatement();
			sql = "UPDATE member SET id = '" + idUpdate + "', pw = '" + pwUpdate + "', addr = '" + addrUpdate
					+ "', tel = '" + telUpdate + "'";
			session = idUpdate;
			cnt = stmt.executeUpdate(sql);
			System.out.println(cnt + "�� ȸ���� ������ �����Ǿ����ϴ�.");
		}
	}

	public static void login(Statement stmt, Connection conn, String sql, BufferedReader input)
			throws IOException, SQLException {
		String loginId = null;
		String loginPw = null;
		System.out.println("���̵� �Է� : ");
		String id = input.readLine();
		System.out.println("�н����� �Է� : ");
		String pw = input.readLine();
		stmt = conn.createStatement();
		sql = "SELECT id, pw from member";
		ResultSet rs = stmt.executeQuery(sql);
		while (rs.next()) {
			loginId = rs.getString("id");
			loginPw = rs.getString("pw");
		}
		if (!loginId.equals(id)) {
			System.out.println("id�� �����ϴ�.");
		} else if (!loginPw.equals(pw)) {
			System.out.println("�н����尡 Ʋ�Ƚ��ϴ�..");
		} else {
			System.out.println("ȯ���մϴ�! �α��� �Ǽ̽��ϴ�.");
			session = id;
		}
	}

	public static void logout() {
		if (session != null) {
			System.out.println("�α׾ƿ� �Ǽ̽��ϴ�.");
			session = null;
		} else if (session == null) {
			System.out.println("���� �α����� ���ּ���.");
		}
	}

}
