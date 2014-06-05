package sample.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.sun.xml.internal.bind.v2.TODO;

import sample.dao.DatabaseConnection;
import sample.dao.TodoDAO;
import sample.entity.Todo;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/SelectServlet")
public class SelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		
		try {
			// JNDI参照コンテキストを取得
			Context context = new InitialContext();
			
			// Tomcatで管理されたデータベース接続をJNDI経由で取得。java:comp/env/ を必ず付ける
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/dbtest");
			
			// データベース接続を取得する
			connection = ds.getConnection();
			
			
			// 接続が正しく完了するとコンソールにメッセージを出力
			log("接続を開きました");
			
			// 検索を行う
			List<Todo> resultList = select(connection);
			
			// 検索結果をリクエスト属性へ格納
			request.setAttribute("list", resultList);
			
			// 検索結果を表示するlist.jspへフォワード
			request.getRequestDispatcher("/list.jsp").forward(request, response);
			
		} catch(Exception e) {
			throw new ServletException(e);
		} finally {
			try {
				connection.close();
				log("接続を閉じました");
			} catch(SQLException e) {
				throw new ServletException(e);
			}
		}
	}
	
	private List<Todo> select(Connection connection) throws Exception {
		// 実行するSQLを作成
		String sql = "INSERT INTO lecture.todo_list ( title,task,limitdate,lastupdate,userid,status " + 
		 "FROM todo_list WHERE useid like CONCAT('%',?,'%')";
		
		// ステートメント(SQLを格納・実行するコンテナ)を取得。
		PreparedStatement statement = connection.prepareStatement(sql);
			
		// PreparedStatementのプレースホルダーに変数を順番に格納する。
		statement.setString(1, "USER");
		ResultSet rs = statement.executeQuery();
		
		List<Todo> resultList = new ArrayList<Todo>();
		
		while(rs.next()){
			// 検索結果１レコードの内容を取得する
			Todo todo = new Todo();
			todo.setTitle(rs.getString("title"));
			todo.setTask(rs.getString("task"));
			todo.setLimitdate(rs.getString("limitdate"));
			todo.setLastupdate(rs.getString("lastupdate"));
			todo.setUserid(rs.getString("userid"));
			todo.setStatus(rs.getString("status"));
			
			// 検索結果をListへ格納
			resultList.add(todo);
		}
		
		return resultList;
	}
}