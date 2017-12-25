package Com.Base.Util.mySimpleUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.params;

public abstract class ControlerCommon {

	public ControlerCommon() {
		// TODO Auto-generated constructor stub
	}

	protected abstract String getName();
	
	public String select(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
		
			ListResult result = buildListResult( request,
			 reponse);
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	public abstract ListResult buildListResult(HttpServletRequest request,
			HttpServletResponse reponse) ; 

	@RequestMapping("/t")
	public String save(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
		
			
			
			reponse.getWriter().write(JsonUtil.result(true, "保存成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping("/t")
	public String delete(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		try {
		
			
			
			reponse.getWriter().write(JsonUtil.result(true, "删除成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
}
