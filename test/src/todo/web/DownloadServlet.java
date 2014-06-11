package todo.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/todo/download")
public class DownloadServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException{
		// ダウンロードするファイルの情報を取得する
		int id = Integer.parseInt(request.getParameter("id"));
		TodoDAO2 dao = new TodoDAO2();
		TodoValueObject vo = null;
		
		try{
			dao.getConnection();
			
			vo = dao.detail(id);
			
		}catch(Exception e){
			e.printStackTrace();
			throw new ServletException();
		}finally{
			dao.closeConnection();
		}
		
		// ファイル名を取り出す
		String filename = vo.getFilename();
		
		// もしファイルが登録されていない(取得できなかった)場合は、再度詳細画面へ戻る。
		if(filename == null || "".equals(filename)){
			request.setAttribute("message", "ファイルは添付されていません");
			request.getRequestDispatcher("/todo/search?id=" + id).forward(request,response);
			
			// ここで終了
			return;
		}
		
		// 添付ファイルの保存ディレクトリ→アップロードディレクトリ
		File downloadFile = new File("C:/tmp/" + filename);
		FileInputStream fis = new FileInputStream(downloadFile);
		BufferedInputStream buf = new BufferedInputStream(fis);
		
		// 全角ファイル名を変換しておく
		filename = URLEncoder.encode(filename, "utf-8");
		
		response.setContentType("application/octet-stream; charset=\"utf-8\"");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + "\"");
		
		// サーブレットの出力(レスポンス)を取得する
		ServletOutputStream out = response.getOutputStream();
		
		int length = 0;
		byte[] buffer = new byte[1024];
		while ((length = buf.read(buffer)) >= 0){
			out.write(buffer, 0, length);
		}
		out.close();
		out.flush();
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
