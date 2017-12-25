package com.action.statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.action.BaseAction;
import com.model.productdemandfile.Productdemandfile;
import com.model.statement.Statement;
import com.service.productdemandfile.ProductdemandfileManager;
import com.service.statement.StatementManager;
import com.util.Constant;

public class StatementAction extends BaseAction {

	protected static final String LIST_JSP = "/pages/statement/list.jsp";

	@Autowired
	private ProductdemandfileManager productdemandfileManager;

	@Autowired
	private StatementManager statementManager;

	/** 执行搜索 */
	public String list() {
		String customerId = getRequest().getSession()
				.getAttribute(Constant.SESSION_USER_CUSTOMERID).toString();
		// String sql
		// productdemandfileManager.

		List<Statement> list = statementManager.getStatementsByCust(customerId);

		if (list.size() > 6) {
			getRequest().setAttribute("select_list",
					list.subList(6, list.size()));
			getRequest().setAttribute("list", list.subList(0, 6));
		} else {
			getRequest().setAttribute("list", list);
		}
		return LIST_JSP;
	}
	
	public void viewpdf() throws Exception{
		String fid =getRequest().getParameter("fid");
		HttpServletResponse response=getResponse();
		Productdemandfile p = this.productdemandfileManager.get(fid);
		getResponse().setContentType("text/html;charset=utf-8");
		if(p!=null){
			InputStream in = null;

			try {
				in = new FileInputStream(p.getFpath());
			} catch (FileNotFoundException e) {

				response.getWriter().write("此文件不存在，无法显示！");
				return;
			}
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "inline; filename=\"" + new String(p.getFname().getBytes("UTF-8"),"iso-8859-1") + "\"");
			OutputStream out = response.getOutputStream();
			byte[] bytes = new byte[1024];
			int len = 0;
			while((len = in.read(bytes,0,1024))!=-1){
				out.write(bytes, 0, len);
			}
			out.flush();
			in.close();
		}
		else
		{ 
			response.getWriter().write("附件fid有误,无法显示");
			return;
		}

	}
}
