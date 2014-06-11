package todo.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import todo.web.TodoDAO2;
import todo.web.TodoValueObject;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/todo/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * POSTメソッドのみ受け付ける。
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// リクエストパラメータを受け取り、更新VOに格納する準備をする
		int id = Integer.parseInt(request.getParameter("id"));
		String title = request.getParameter("title");
		String task = request.getParameter("task");
		String inputLimitdate = request.getParameter("limitdate");
		String userid = request.getParameter("userid");
		int status = Integer.parseInt(request.getParameter("status"));
		
		// VOへ格納する。登録されるValueObjectの期限(limit)はvoではinputLimitになる。
		TodoValueObject vo = new TodoValueObject();
		vo.setId(id);
		vo.setTitle(title);
		vo.setTask(task);
		vo.setInputLimit(inputLimitdate);
		vo.setUserid(userid);
		vo.setStatus(status);
		
		// DAOの取得
		TodoDAO2 dao = new TodoDAO2();
		String message = "";
		
		try{
			
			dao.getConnection();
			
			// 更新または登録処理を行う。idが0の時は新規登録、id>=1の時は更新
			if(id == 0){
				dao.registerInsert(vo);
				setMessage(request, "タスクの新規登録処理が完了しました。");
			}else{
				dao.registerUpdate(vo);
				setMessage(request, "タスク[ " + id + " ]の更新処理が完了しました。");
			}
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			// DAOの処理が完了したら接続を閉じる
			dao.closeConnection();
		}
	
	String toAddr = "databasetest1991@yahoo.co.jp";
	String fromAddr = "databasetest1991@yahoo.co.jp";
	String personName = "おおその";
	String subject = "TODO管理アプリケーションからの報告です";
	
	// 完了時にメールを送信する
	SimpleMailSender mail = new SimpleMailSender();
	
	try {
		mail.sendMessage(toAddr, fromAddr, personName, subject, message);
	}catch(Exception e){
		e.printStackTrace();
	}
	
	// 完了画面を表示する
	RequestDispatcher rd = request.getRequestDispatcher("/complete.jsp");
	rd.forward(request, response);
	}
	
	/**
	 * JSPで表示するメッセージを設定する。
	 */
	protected void setMessage(HttpServletRequest request, String message){
		request.setAttribute("message", message);
	}
}