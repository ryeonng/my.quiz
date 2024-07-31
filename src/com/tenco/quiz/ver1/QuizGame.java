package com.tenco.quiz.ver1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.tenco.quiz.DBConnectionManager;

public class QuizGame {

	private static final String ADD_QUIZ = " insert into quiz(question, answer) values(?, ?) ";
	private static final String VIEW_QUIZ = " select * from quiz ";
	private static final String RANDOM_QUIZ = " select * from quiz order by rand() limit 1 ";
	
	public static void main(String[] args) {

		
		try ( Connection conn = DBConnectionManager.getConnection(); // 만들어 놓은 클래스 호출
				Scanner scanner = new Scanner(System.in)){
			
			// CRUD
			while(true) {
				printMenu();
				
				int choice = scanner.nextInt(); // 블로킹 처리 : 계속 돌아가는 while문 멈춤
				
				if(choice == 1) {
					addQuizQuestion(conn, scanner);
				} else if(choice == 2) {
					viewQuizQustion(conn);
				} else if(choice == 3) {
					playQuizGame(conn,scanner);
				} else if(choice == 4) {
					System.out.println("프로그램을 종료 합니다.");
					break;
				} else {
					System.out.println("잘못된 선택 입니다. 다시 시도하세요.");
				}
			}
			
		} catch (Exception e) {
		
		}
		
	} // end of main

	private static void printMenu() {
		System.out.println();
		System.out.println("--------------------");
		System.out.println("1. 퀴즈 문제 추가");
		System.out.println("2. 퀴즈 문제 조회");
		System.out.println("3. 퀴즈 게임 시작");
		System.out.println("4. 게임 종료");
		System.out.println("옵션을 선택 하세요 : ");
	}

	private static void playQuizGame(Connection conn, Scanner scanner) {
		
		try (PreparedStatement pstmt = conn.prepareStatement(RANDOM_QUIZ)){
			ResultSet rs = pstmt.executeQuery();
			
			// 방어적 코드 
			if(rs.next()) {
				
				String question = rs.getString("question");
				String answer = rs.getString("answer");
				
				System.out.println("퀴즈 문제 : " + question);
				// 버그 처리
				scanner.nextLine();
				
				System.out.print("당신의 답 : ");
				String userAnswer = scanner.nextLine();
				
				if(userAnswer.equalsIgnoreCase(answer)) { // equalsIgnoreCase : 대소문자 구분 x
					System.out.println("정답입니다 ! 점수를 얻었습니다.");
				} else {
					System.out.println("오답입니다.");
				}
				
				System.out.println("퀴즈 정답 : " + answer);
			} else {
				System.out.println("죄송합니다. 아직 퀴즈 출제 중입니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void viewQuizQustion(Connection conn) {
		
		try (PreparedStatement pstmt = conn.prepareStatement(VIEW_QUIZ)){
			ResultSet resultSet = pstmt.executeQuery();
			while(resultSet.next()) {
				System.out.println("문제의 ID : " + resultSet.getInt("id"));
				System.out.println("문제 : " + resultSet.getString("question"));
				System.out.println("정답 : " + resultSet.getString("answer"));
				if(!resultSet.isLast()) {
		               System.out.println("---------------------------------------------" );               
		            };
		         }
		      } catch (SQLException e) {
		         e.printStackTrace();
		      }
		   }

	private static void addQuizQuestion(Connection conn, Scanner scanner) {
		System.out.println("퀴즈 문제를 입력하세요. : ");
		scanner.nextLine();
		String question = scanner.nextLine();
		System.out.println("퀴즈 정답을 입력하세요. : ");
		String answer = scanner.nextLine();
		
		// 쿼리 구문 준비
		try (PreparedStatement pstmt = conn.prepareStatement(ADD_QUIZ)){
			pstmt.setString(1, question);
			pstmt.setString(2, answer);
			
			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 : " + rowsInsertedCount);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

	// 퀴즈를 추가하는 함수 만들기
	// 사용자에게 퀴즈와 답을 입력 받아야 한다.
	// 데이터를 받았다면, mysql에 접근해 쿼리를 날림. = Connection을 활용해 쿼리를 날려야 한다.
	
}