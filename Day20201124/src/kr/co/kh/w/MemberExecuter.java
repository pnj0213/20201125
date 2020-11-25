package kr.co.kh.w;

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
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://underdogb.cafe24.com:3306/underdogb?characterEncoding=utf8", "underdogb", "khacademy1!");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		while (true) {// �ݺ���
			System.out.println("R:ȸ������ S:IDã��D:ȸ��Ż��E:ȸ������I:�α���O:�α׾ƿ�L:ȸ�����");
			String idlogin = null;
			String sql = null;
			Statement stmt = null;
			try {
				String protocol = input.readLine();
				if (protocol.equals("R") || protocol.equals("r")) {// ȸ������
					try {
						stmt = conn.createStatement();
						sql = "select id from member";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							idlogin = rs.getString("id");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
//					if (idlogin.equals(session != null)) {
//						System.out.println("�α������Դϴ�.");
//						continue;
//					}
					System.out.println("���̵��Է�:");
					String id = input.readLine();
					System.out.println("�н������Է�:");
					String pw = input.readLine();
					System.out.println("�ּ��Է�:");
					String addr = input.readLine();
					System.out.println("��ȭ��ȣ�Է�:");
					String tel = input.readLine();
					int cnt = 0;
					try {
						stmt = conn.createStatement();
						sql = "insert into member(id,pw,addr,tel) values('" + id + "','" + pw + "','" + addr + "','"
								+ tel + "')";
						cnt = stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println(cnt + "�� ȸ����ϵǾ����ϴ�.");
				} // ȸ������
				else if (protocol.equals("S") || protocol.equals("s")) {// IDã��
					System.out.println("ã�� ID �Է� :");
					String idSearch = input.readLine();
					System.out.print("ȸ�����̵�\tȸ���н�����\tȸ���ּ�\tȸ����ȭ��ȣ\n");
					String id = null;
					String pw = null;
					String addr = null;
					String tel = null;
					try {
						stmt = conn.createStatement();
						sql = "select id,pw,addr,tel from member where id = '" + idSearch + "'";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							id = rs.getString("id");
							pw = rs.getString("pw");
							addr = rs.getString("addr");
							tel = rs.getString("tel");
							System.out.print(id + "\t" + pw + "\t" + addr + "\t" + tel + "\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (id == null) {
						System.out.println("ã�� ���̵� �����ϴ�.");
						continue;
					}

				} // IDã��
				else if (protocol.equals("D") || protocol.equals("d")) {// ȸ��Ż��
					String id = null;
					if(session == null) {
						System.out.println("�α����� ���� ���ּ���..");
						continue;
					}
					System.out.println("���� Ż���Ͻðڽ��ϱ� ? y/n");
					String result = input.readLine();
					if(result.equals("y")) {
						try {
							stmt = conn.createStatement();
							sql = "delete from member where id = '"+session+"' ";
							int cnt = stmt.executeUpdate(sql);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						session = null;
						System.out.println("ȸ��Ż��Ǿ����ϴ�.");
					} else {
						continue;
					}
//					try {
//						stmt = conn.createStatement();
//						sql = "select id from member";
//						ResultSet rs = stmt.executeQuery(sql);
//						
//						while(rs.next()) {
//							id = rs.getString("id");
//						}
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//					if(id.equals(session)) {
//						try {
//							stmt = conn.createStatement();
//							sql = "delete from member where id = '"+session+"'";
//							int cnt = stmt.executeUpdate(sql);
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//						session = null;
//						System.out.println("ȸ��Ż�� �Ǿ����ϴ�.");
//					}

				} // ȸ��Ż��
				else if (protocol.equals("E") || protocol.equals("e")) {// ȸ������
					if(session == null) {
						System.out.println("�α��� ���� �ϼ���..");
						continue;
					}
					System.out.print("ȸ�����̵�\tȸ���н�����\tȸ���ּ�\tȸ����ȭ��ȣ\n");
					String id = null;
					String pw = null;
					String addr = null;
					String tel = null;
					try {
						stmt = conn.createStatement();
						sql = "select id,pw,addr,tel from member where id = '"+session+"' ";
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							id = rs.getString("id");
							pw = rs.getString("pw");
							addr = rs.getString("addr");
							tel = rs.getString("tel");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(id.equals(session)) {
						System.out.print(id + "\t" + pw + "\t" + addr + "\t" + tel + "\n");
						System.out.println("���̵��Է�:");
						String idUpdate = input.readLine();
						System.out.println("�н������Է�:");
						String pwUpdate = input.readLine();
						System.out.println("�ּ��Է�:");
						String addrUpdate = input.readLine();
						System.out.println("��ȭ��ȣ�Է�:");
						String telUpdate = input.readLine();
						
						try {
							stmt = conn.createStatement();
							sql = "update member set id= '"+idUpdate+"', pw='"+pwUpdate+"', addr='"+addrUpdate+"', tel='"+telUpdate+"' where id='"+session+"'";
							int cnt = stmt.executeUpdate(sql);
							session = idUpdate;
							System.out.println(cnt + "�� ȸ���� �����Ǿ����ϴ�.");
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				} // ȸ������
				else if (protocol.equals("I") || protocol.equals("i")) {// �α���
					if(session != null) {
						System.out.println("�α��� ���Դϴ�..");
						continue;
					}
					System.out.println("���̵��Է�:");
					String loginId = input.readLine();
					System.out.println("�н������Է�:");
					String loginPw = input.readLine();
					String id = null;
					String pw = null;
					try {
						stmt = conn.createStatement();
						sql = "select id,pw from member where id = '"+loginId+"'";
						ResultSet rs = stmt.executeQuery(sql);
						while (rs.next()) {
							id = rs.getString("id");
							pw = rs.getString("pw");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if (!(loginId.equals(id))) {
						System.out.println("���̵� Ʋ�Ƚ��ϴ�.");
						continue;
					}
					if (!(loginPw.equals(pw))) {
						System.out.println("�н����尡 Ʋ�Ƚ��ϴ�.");
						continue;
					}
					System.out.println("ȯ���մϴ�. �α��εǾ����ϴ�.");
					session = loginId;
					
				} // �α���
				else if (protocol.equals("O") || protocol.equals("o")) {// �α׾ƿ�
					session = null;
					System.out.println("�α׾ƿ� �Ǿ����ϴ�.");

				} // �α׾ƿ�
				else if (protocol.equals("L") || protocol.equals("l")) { // ȸ�����

					System.out.print("ȸ�����̵�\tȸ���н�����\tȸ���ּ�\tȸ����ȭ��ȣ\n");

					try {
						stmt = conn.createStatement();
						sql = "select * from member";
						ResultSet rs = stmt.executeQuery(sql);

						while (rs.next()) {
							String id = rs.getString("id");
							String pw = rs.getString("pw");
							String addr = rs.getString("addr");
							String tel = rs.getString("tel");
							System.out.print(id + "\t" + pw + "\t" + addr + "\t" + tel + "\n");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				} // ȸ�����
			} catch (IOException e) {
				e.printStackTrace();
			}
		} // �ݺ���
	}
}