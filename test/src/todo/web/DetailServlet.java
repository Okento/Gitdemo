package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DetailServlet
 */
@WebServlet("/todo/detail")
public class DetailServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// DAOの取得
		TodoDAO2 dao = new TodoDAO2();
		
		// リクエストパラメータから選択したタスクidを取得する
		String paramId = request.getParameter("id");
		
		// Stringからintへ変換し、daoで処理を行う。更新対象のタスクを１件取得する。
		TodoValueObject vo;
		try {
			
			dao.getConnection();
			
			// intへ変換※NumberFormatExceptionが発生する可能性あり。チェック対象。
			int id = Integer.parseInt(paramId);
			
			// タスク詳細結果を取得
			vo = dao.detail(id);
			
		}catch(Exception e) {
			throw new ServletException(e);
		}finally{
			// DAOの処理が完了したら接続を閉じる
			dao.closeConnection();
		}
		
		// タスク１件のvoをリクエスト属性へバインド
		request.setAttribute("vo", vo);
		
		// 画面を返す。検索一覧を表示する
		RequestDispatcher rd = request.getRequestDispatcher("/detail.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// POST送信時もGETと同じ処理を行う
		doGet(request, response);
	}
}