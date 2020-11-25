package kr.co.kh.Static.practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MemberExecuter {
	

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
			System.out.println("R: ȸ������ L:ȸ����� S:IDã�� D:ȸ��Ż�� E:ȸ������ I:�α��� O:�α׾ƿ�");
			try {
				protocol = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (protocol.equalsIgnoreCase("r")) { //ȸ������
				try {
					MemberFunction.register(protocol, input, stmt, conn, sql, cnt);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (protocol.equalsIgnoreCase("l")) { //ȸ�����
				try {
					MemberFunction.list(stmt, sql, conn);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (protocol.equalsIgnoreCase("s")) { //idã��
				try {
					MemberFunction.search(stmt, sql, conn, input);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (protocol.equalsIgnoreCase("d")) { //ȸ��Ż��
				try {
					MemberFunction.delete(stmt, conn, sql, cnt);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (protocol.equalsIgnoreCase("e")) { //ȸ������
				try {
					MemberFunction.update(stmt, conn, sql, input, cnt);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (protocol.equalsIgnoreCase("i")) { //�α���
				try {
					MemberFunction.login(stmt, conn, sql, input);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else if (protocol.equalsIgnoreCase("o")) { //�α׾ƿ�
				MemberFunction.logout();
			}
		}	
	}
}
