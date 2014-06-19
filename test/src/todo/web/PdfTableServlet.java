package todo.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PdfBasicServlet
 */
@WebServlet("/PdfTableServlet.pdf")
public class PdfTableServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try{
			// ドキュメント本体を生成(ページサイズとマージンの設定)
			Document doc = new Document(PageSize.A4, 50, 20, 270, 20);
			
			// ドキュメントの出力を定義
			PdfWriter writer = PdfWriter.getInstance(doc, response.getOutputStream());
			
			// セキュリティ関連の設定を行う
			writer.setEncryption("".getBytes(), "".getBytes(), PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_128);
			
			// ドキュメントをオープン
			doc.open();
			
			// フォントの定義
			Font fTitle = new Font(BaseFont.createFont
					("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED), 11, Font.BOLD);
			Font fData = new Font(BaseFont.createFont
					("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED), 10, Font.NORMAL);
			
			// 列数4のテーブルを定義
			PdfPTable tbl = new PdfPTable(4);
			
			// 各セルの幅(割合)を定義
			int[] ws = {55, 10, 20, 15};
			tbl.setWidths(ws);
			
			// タイトル行を定義
			String[] headers = {"書名", "価格", "出版社", "刊行日"};
			for(int i = 0; i < headers.length; i++){
				PdfPCell c = new PdfPCell(new Phrase(headers[i], fTitle));
				c.setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
				tbl.addCell(c);
			}
			tbl.setHeaderRows(1);
			
			// bookテーブルからすべてのレコードを取得
			Connection db = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			
			try{
				Context context = new InitialContext();
				DataSource ds = (DataSource)context.lookup("java:comp/env/jdbc/dbtest");
				db = ds.getConnection();
				ps = db.prepareStatement("SELECT title, price, publish, published FROM book ORDER BY published DESC");
				rs = ps.executeQuery();
				ResultSetMetaData meta = rs.getMetaData();
				
				// 結果セットから取り出したレコードをテーブルにセット
				while(rs.next()){
					for(int i = 1; i <= meta.getColumnCount(); i++){
					PdfPCell c = new PdfPCell(new Phrase(rs.getString(i), fData));
					tbl.addCell(c);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
			
			
			// 出来上がったテーブルを文書に埋め込み
			doc.add(tbl);
			// ドキュメントをクローズ
			doc.close();
		}catch(DocumentException e){
			throw new ServletException(e);
		}
	}
}