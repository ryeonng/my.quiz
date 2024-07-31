package com.tenco.quiz.ver4;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 커넥션 풀을 활용하는 예제로 수정해 보자.
 * HikariCP-5.1.0.jar -> lib 설정
 */

public class DBConnectionManager {

	private static HikariDataSource dataSource;
	
	private static final String URL = "jdbc:mysql://localhost:3306/demo3?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";
	
	static {
		// HikariCP 를 사용하기 위한 설정이 필요하다.
		// HikariConfig 라는 객체를 제공해 줘서 이 클래스를 활용해 상세한 설정을 할 수 있다.
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl(URL);
		config.setUsername(USER);
		config.setPassword(PASSWORD);
		config.setMaximumPoolSize(10); // 최대 연결 수 설정 (10개 - 기본 값)
		
		dataSource = new HikariDataSource(config);
		
	}
	
	public static Connection getConnection() throws SQLException{
		System.out.println("HikariCP를 사용한 Data Source 활용");
		return dataSource.getConnection();
	}
	
	// 테스트 코드를 통해 확인하기
	public static void main(String[] args) {
		
		try {
			Connection conn = DBConnectionManager.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	} // end of main
	
	
// 기본 JDBC 드라이버를 사용하는 버전
//	public static Connection getConnection() throws SQLException {
//		return DriverManager.getConnection(URL, USER, PASSWORD);
//	}
	
} // end of class
