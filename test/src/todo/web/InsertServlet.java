package todo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class InsertServlet
 */
@WebServlet("/InsertServlet")
public class InsertServlet extends HttpServlet {
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
			
			// Tomcatで管理されたデータベース接続をJNDI経由で取得。
			// java:comp/env/ を必ず付ける
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/dbtest");
			
			// データベース接続を取得する
			connection = ds.getConnection();
			
			// 接続が正しく完了するとコンソールにメッセージを出力
			log("接続を開きました");
			
			// 登録処理を行う
			insert(connection);
			
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
		
		request.getRequestDispatcher("/complete.html").forward(request,response);

	}
	
	private void insert(Connection connection) throws Exception {
		// 実行するSQLを作成
		String sql = "INSERT INTO play.todo_list ( title,task,limitdate,lastupdate,userid,status " + 
		") VALUES (?,?,?, now() ,?,?);";
		
		// try(
		// ステートメント(SQLを格納・実行するコンテナ)を取得。
		PreparedStatement statement = connection.prepareStatement(sql);
		
		// PreparedStatementのプレースホルダーに変数を順番に格納する。
		statement.setString(1, "講習会準備");
		statement.setString(2, "講習会のスライド作成、配布ソースコードの準備");
		statement.setString(3, "2012-06-12");
		statement.setString(4, "USER99");
		statement.setInt(5, 0);
		
		// 更新系操作はexecuteUpdate()を利用し、その対象件数を取得することができる。
		int count = statement.executeUpdate();
		log("１つ目の追加:" + count);
		
		// PreparedStatementのプレースホルダーに変数を順番に格納する。
		statement.setString(1, "講習会");
		statement.setString(2, "2012年7月の講習会を実施");
		statement.setString(3, "2017-07-01");
		statement.setString(4, "USER99");
		statement.setInt(5, 0);
		
		// 更新系操作はexecuteUpdate()を利用し、その対象件数を取得することができる。
		count = statement.executeUpdate();
		log("２つ目の追加:" + count);
	}

}