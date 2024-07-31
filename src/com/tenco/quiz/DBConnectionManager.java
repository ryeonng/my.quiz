package com.tenco.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {

	private static final String URL = "jdbc:mysql://localhost:3306/quizdb?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";
	
	// * static {} 블록 (정적 초기화 블록)
	// 클래스가 처음 로드(컴파일)될 때, 한 번 실행 된다.
	// 쓰임의 목적 : 정적(static)변수의 초기화나, 복잡한 초기화 작업을 수행할 때 사용한다.
	// stiatic {} 블록 안에 예외를 던질 수도 있다.
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	// 정적 메서드(함수) Connection 객체를 리턴하는 함수를 만들어 보자.
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(URL, USER, PASSWORD);
	}
	
} // end of class
