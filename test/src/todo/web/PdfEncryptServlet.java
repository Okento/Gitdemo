package todo.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class PdfBasicServlet
 */
@WebServlet("/PdfEncryptServlet.pdf")
public class PdfEncryptServlet extends HttpServlet {
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
			writer.setEncryption("dbtest".getBytes(), "dbtest".getBytes(), PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY, PdfWriter.STANDARD_ENCRYPTION_128);
			
			// ドキュメントをオープン
			doc.open();
			
			// フォントの定義
			Font fnt = new Font(BaseFont.createFont
					("HeiseiKakuGo-W5","UniJIS-UCS2-H",BaseFont.NOT_EMBEDDED), 18, Font.BOLD);
			
			// 文字列の出力
			doc.add(new Paragraph("こんにちは、iText!", fnt));
			
			// ドキュメントをクローズ
			doc.close();
		}catch(DocumentException e){
			throw new ServletException(e);
		}
	}
}