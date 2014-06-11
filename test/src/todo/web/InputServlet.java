package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 新規登録の入力画面を表示する
 */
@WebServlet("/todo/input")
public class InputServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// voの作成
		TodoValueObject vo = new TodoValueObject();
		
		// 新規登録であることを判別するためid=0としている。
		vo.setId(0);
		
		// タスク１件のvoをリクエスト属性へバインド
		request.setAttribute("vo", vo);
		
		// 詳細画面を表示する
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// POST送信時もGETと同じ処理を行う
		doGet(request, response);
	}
}