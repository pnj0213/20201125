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
			System.out.println("R: ȸ������ L:ȸ����� S:IDã�� D:ȸ��Ż�� E:ȸ������ I:�α��� O:�α׾ƿ�");
			try {
				protocol = input.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (protocol.equalsIgnoreCase("r")) { //ȸ������
				if(session != null) {
					System.out.println("�̹� ȸ������ �ϼ̽��ϴ�.");
					continue;
				}
				try {
					System.out.println("���̵� �Է� :");
					id = input.readLine();
					System.out.println("�н����� �Է� :");
					pw = input.readLine();
					System.out.println("�ּ� �Է� :");
					addr = input.readLine();
					System.out.println("��ȭ��ȣ �Է� :");
					tel = input.readLine();
					stmt = conn.createStatement();
					sql = "INSERT INTO member (id, pw, addr, tel) values('"+id+"',+'"+pw+"',+'"+addr+"',+'"+tel+"')";
					cnt = stmt.executeUpdate(sql);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				System.out.println(cnt + "�� ȸ����� �Ǿ����ϴ�.");
				
			}  //ȸ������ ����������
			else if (protocol.equalsIgnoreCase("l")) { //ȸ�����
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
			}  //ȸ����� ����������
			else if (protocol.equalsIgnoreCase("s")) { //idã��
				System.out.println("ã�� ���̵� �Է� : ");
				try {
					String searchId = input.readLine();
					stmt = conn.createStatement();
					sql = "SELECT id, pw, addr, tel from member where id = '"+searchId+"'";
					ResultSet rs = stmt.executeQuery(sql);
					if(!rs.isBeforeFirst()) {
						System.out.println("ã�� ID�� �����ϴ�.");
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
			}  //idã�� ����������
			else if (protocol.equalsIgnoreCase("d")) { //ȸ��Ż��
				if(session == null) {
					System.out.println("���� �α����� ���ּ���.");
				} else if(session != null) {
					try {
						stmt = conn.createStatement();
						sql = "DELETE from member where id = '"+ session +"' ";
						cnt = stmt.executeUpdate(sql);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					System.out.println("ȸ�� Ż�� �Ǽ̽��ϴ�.");
					session = null;
				}
			}  //ȸ��Ż�p ����������
			else if (protocol.equalsIgnoreCase("e")) { //ȸ������
				if(session == null) {
					System.out.println("���� �α����� ���ּ���.");
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
					System.out.println("���̵� �Է� :");
					try {
						String idUpdate = input.readLine();
						System.out.println("�н����� �Է� :");
						String pwUpdate = input.readLine();
						System.out.println("�ּ� �Է� :");
						String addrUpdate = input.readLine();
						System.out.println("��ȭ��ȣ �Է� :");
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
					System.out.println(cnt + "�� ȸ���� ������ �����Ǿ����ϴ�.");
				}
				
			}  //ȸ������ ����������
			else if (protocol.equalsIgnoreCase("i")) { //�α���
				if(session != null) {
					System.out.println("�α��� ���Դϴ�..");
					continue;
				}
				String loginId = null;
				String loginPw = null;
				System.out.println("���̵� �Է� : ");
				try {
					id = input.readLine();
					System.out.println("�н����� �Է� : ");
					pw = input.readLine();
					
					stmt = conn.createStatement();
					sql = "SELECT id, pw from member where id = '"+id+"'";
					ResultSet rs = stmt.executeQuery(sql);
					
					while(rs.next()) {
						loginId = rs.getString("id");
						loginPw = rs.getString("pw");
					}
					if(!loginId.equals(id)) {
						System.out.println("id�� �����ϴ�.");
					} else if(!loginPw.equals(pw)) {
						System.out.println("�н����尡 Ʋ�Ƚ��ϴ�..");
					} else {
						System.out.println("ȯ���մϴ�! �α��� �Ǽ̽��ϴ�.");
						session = id;
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}  //�α��� ����������
				/*
				sql = "SELECT id from member where id = '"+id+"'";
				String sql1 = "SELECT pw from member where pw = '"+pw+"'";
				cnt = stmt.executeQuery(sql);
				int cnt1 = stmt.executeQuery(sql1);
				if(cnt == 0){
				System.out.println("id�� �����ϴ�.");
				} else if(cnt1 == 0){
				System.out.println("�н����尡 Ʋ�Ƚ��ϴ�..");
				} else if(cnt !=0 && cnt1 !=0){
				System.out.println("ȯ���մϴ�! �α��� �Ǽ̽��ϴ�.");
				session = id;
				}
				*/
			}
			else if (protocol.equalsIgnoreCase("o")) { //�α׾ƿ�
				if(session != null) {
					System.out.println("�α׾ƿ� �Ǽ̽��ϴ�.");
					session = null;
				} else if(session == null) {
					System.out.println("���� �α����� ���ּ���.");
				}
			}  //�α׾ƿ� ������������
		}	//�ݺ��� ��
	}
}
