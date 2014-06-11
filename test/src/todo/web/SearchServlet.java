package todo.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.web.TodoDAO2;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/todo/SearchServlet")
public class SearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// DAOの取得
		TodoDAO2 dao = new TodoDAO2();
		try {
			
			dao.getConnection();
			
			// タスクのリストを一覧で取得し、リクエスト属性へ格納する
			List<TodoValueObject> list = dao.todoList();
			
			request.setAttribute("todoList", list);
		}catch(Exception e) {
			throw new ServletException(e);
		}finally{
			// DAOの処理が完了したら接続を閉じる
			dao.closeConnection();
		}
		
		// 検索一覧を表示する
		RequestDispatcher rd = request.getRequestDispatcher("../search.jsp");
		rd.forward(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// POST送信時もGETと同じ処理を行う
		doGet(request,response);
	}
}