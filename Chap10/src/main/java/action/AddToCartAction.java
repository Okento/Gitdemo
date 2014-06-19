package action;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.ServletRequestAware;

import test.BookDB;
import test.TBook;

@Results({
	@Result(name="addtocart", location="bookstore.jsp"),
	@Result(name="checkout", location="checkout.jsp")
})

public class AddToCartAction implements ServletRequestAware {
	
	private List<TBook> lblist;
	private HttpServletRequest request;
	
	private String[] selecteditems = null;
	
	@Action("/AddToCart")
	public String addToCart(){
		HttpSession session = request.getSession(); // �Z�b�V�����̊J�n�B�Z�b�V�����̎󂯎��B
		session.removeAttribute("CART"); 			// �J�[�g�̒��̃Z�b�V������������������B(���A�N�Z�X���邲�Ƃɏ���������Ă���)
		
		BookDB bd = new BookDB();
		
		// �A�C�e���̏�񂪑��݂��Ȃ�������A�I������Ă��Ȃ��ꍇ�̌��o
		// �����Z�b�g���Ă���
		if(selecteditems != null && selecteditems.length != 0){
			List<String> cart = Arrays.asList(selecteditems);
			session.setAttribute("CART", cart);		// �J�[�g("CART")���̒��ɃZ�b�g����
		}
		
		try{
			bd.getConnection();
			lblist = bd.getListbook();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// BD�̏���������������ڑ������
			bd.closeConnection();
		}
		return ("addtocart"); //���U���g�ɖ߂��B(���U���g��addtocart�̏ꍇbookstore.jsp������Ǝw�����Ă���̂ł����Ȃ�(18�s��)
	}
	
	public String checkout(){
		HttpSession session = request.getSession(false);
		List<String> cart = (List<String>)session.getAttribute("CART");
		
		BookDB bd = new BookDB();

		try{
			bd.getConnection();
			int total = bd.total(cart);
			request.setAttribute("TOTAL", total);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			// BD�̏���������������ڑ������
			bd.closeConnection();
		}
		return ("checkout");
	}
	
	
	public List<TBook> getLblist(){
		return lblist;
	}
	public void setLblist(List<TBook> lblist){
		this.lblist = lblist;
	}
	
	public String[] getSelecteditems(){
		return selecteditems;
	}
	public void setSelecteditems(String[] selecteditems){
		this.selecteditems = selecteditems;
	}
	
	public void setServletRequest(HttpServletRequest request){
		this.request = request;
	}
}
