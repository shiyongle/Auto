package Com.Controller.System;


import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Base.Util.ExcelUtil;
import Com.Base.Util.JsonUtil;
import Com.Base.Util.ListResult;
import Com.Base.Util.mySimpleUtil.MySimpleToolsZ;
import Com.Dao.System.INoteDao;
import Com.Entity.System.Note;

/**
 * 在线留言
 * 
 *
 * @author ZJZ (447338871@qq.com, any Question)
 * @date 2015-1-17 上午10:42:39
 */
@Controller
public class NoteController {
	Logger log = LoggerFactory.getLogger(NoteController.class);
	@Resource
	private INoteDao noteDao;
	
	private static final String BASE_SQL = " SELECT fid, fuser, fuser_name fuserName, fcontent, fphone,DATE_FORMAT(fdate,'%Y-%m-%d %H:%i:%s') fdate, fpre_note fpreNote, fprenote_msg fprenoteMsg,isUnRead FROM t_sys_note ";

	private static final String[][] ROLE_IDS = new String[][]{{"869fb2b8-50df-11e4-bdb9-00ff6b42e1e5","平台人员"}};
	
	@RequestMapping(value = "/gainNotesBase")
	public String gainNotesBase(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			String sql = BASE_SQL +  " where fpre_note = '-1' ";
			
			//非管理员只能查看自己的留言
			if (!isNoteAdmin(request)) {
				request.setAttribute("djsort"," isUnRead desc , t_sys_note.fdate desc"); 
				sql += String.format(" and fuser = '%s' ", MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request));
				
			} else {
				
				request.setAttribute("djsort"," t_sys_note.fdate desc,isUnRead ");
				
			}
			
			ListResult result = noteDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/gainNotesByPreNote")
	public String gainNotesByPreNote(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			request.setAttribute("djsort","t_sys_note.fdate desc");
			
//			String preNoteId = request.getParameter("preNoteId");
			
			String sql = BASE_SQL;
//			
//			String sql = BASE_SQL + " where fpre_note = '%s' ";
//			
//			sql = String.format(sql, preNoteId);
			
			ListResult result = noteDao.QueryFilterList(sql, request);
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/gainNotesMsgByPreNote")
	public String gainNotesMsgByPreNote(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			request.setAttribute("djsort","t_sys_note.fdate desc");
			
			String preNoteId = request.getParameter("fid");
			
			String sql = BASE_SQL + String.format( " where fpre_note = '%s' ", preNoteId);
			
			ListResult result = noteDao.QueryFilterList(sql, request);
			
			StringBuilder builder = new StringBuilder("<table border='0'>");
			
			for (HashMap<String, Object> map : result.getData()) {

				builder.append(String
						.format("<tr> <td width='600'><li>%s  </li></td> <td width='200'>%s  </td><td width='100'>by %s  </td><td width='100'> %s  </td> </tr>",
								map.get("fcontent"), map.get("fprenoteMsg"),
								map.get("fuserName"), map.get("fdate")));

			}
			
			builder.append("</table>");
			
			JSONObject json = new JSONObject();
			json.put("msg", builder.toString());
		
			reponse.getWriter().write(JsonUtil.result(true, "","", json.toString()));
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/gainNotesByID")
	public String gainNotesByID(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			String fid = request.getParameter("fid");
			
			String sql = BASE_SQL + String.format(" where fid = '%s' ", fid);
			
			
			
			ListResult result = noteDao.QueryFilterList(sql, request);
			
			
			//前端数据转码问题，前端解决
//			for (HashMap<String, Object> map : result.getData()) {
//				
//				String content = (String) map.get("fcontent");
//				
//				map.put("fcontent", content.replaceAll("<br/>", '\u0009' + ""));
//	
//			}
			
			reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	@RequestMapping(value = "/gainCurrentUser")
	public String gainCurrentUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {

			String userId = MySimpleToolsZ.getMySimpleToolsZ()
					.getCurrentUserId(request);

			String hql = " select fname, ftel from SysUser where fid = '%s' ";

			hql = String.format(hql, userId);

			java.util.List<?> nameAndPhone = (java.util.List<?>) noteDao.QueryByHql(hql);

			reponse.getWriter().write(
					JsonUtil.result(true, "", nameAndPhone.size()+ "",
							JSONArray.fromObject(nameAndPhone).toString()));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	/**
	 * 首界面回复
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-1-20 下午4:41:35  (ZJZ)
	 */
	@RequestMapping(value = "/saveNotes")
	public String saveNotes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			String fids = request.getParameter("fids");
			
			JSONArray fidsJsa = JSONArray.fromObject(fids);
			
			List<Note> notes = new ArrayList<>();
			
			for (int i = 0; i < fidsJsa.size(); i++) {

				String id = fidsJsa.getString(i);

				Note po = buildPo(request, noteDao);

				Note pos = noteDao.Query(id);

				String fcontent = pos.getFcontent();

				po.setFprenoteMsg(buildPreNoteS(pos, fcontent));

				po.setFpreNote(id);

				// 如果回复消息的是管理员，且不是回复自己的消息
				if (!MySimpleToolsZ.getMySimpleToolsZ()
						.getCurrentUserId(request).equals(pos.getFuser())
						&& isNoteAdmin(request)) {
					
					pos.setIsUnRead(1);
					po.setIsUnRead(1);

				} else {

					po.setIsUnRead(0);
					pos.setIsUnRead(0);
				}

				notes.add(po);
				notes.add(pos);
			}
			
			
			noteDao.ExecSaveNotes(notes);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 次界面回复
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-1-20 下午4:41:35  (ZJZ)
	 */
	@RequestMapping(value = "/saveReplyNotes")
	public String saveReplyNotes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
//			String fids = request.getParameter("fids");
			
			String replyFids = request.getParameter("replyFids");
			
			JSONArray replyFidsJsa = JSONArray.fromObject(replyFids);

			
			StringBuilder builder = new StringBuilder();
			
			for (int i = 0; i < replyFidsJsa.size(); i++) {

				String id = replyFidsJsa.getString(i);

//				Note po = buildPo(request, noteDao);

				Note pos = noteDao.Query(id);

				String fcontent = pos.getFcontent();

				builder.append(buildPreNoteS(pos, fcontent));

			}
			
			String fid = request.getParameter("fids");
			
			Note po = buildPo(request, noteDao);
			
			po.setFprenoteMsg(builder.toString());
			
			po.setFpreNote(fid);
			
			Note pos = noteDao.Query(fid);

			// 如果回复消息的是管理员，且管理员不是回复自己的消息
			if (!MySimpleToolsZ.getMySimpleToolsZ()
					.getCurrentUserId(request).equals(pos.getFuser())
					&& isNoteAdmin(request)) {
				
				pos.setIsUnRead(1);

			} else {

				pos.setIsUnRead(0);
			}
			
			List<Note> notes = new ArrayList<>();
			
			notes.add(po);
			notes.add(pos);
			
			noteDao.ExecSaveNotes(notes);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String buildPreNoteS(Note pos, String fcontent) {
		return String.format("@ %s %s\n",
				pos.getFuserName(),
				fcontent.length() > 10 ? (fcontent.subSequence(0, 10)
						+ "……") : fcontent);
	}
	
	@RequestMapping(value = "/saveNote")
	public String saveNote(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			Note po = buildPo(request, noteDao);
			
			noteDao.ExecSave(po);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException | CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
	 * 设置读取状态
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-1-21 下午2:43:51  (ZJZ)
	 */
	@RequestMapping(value = "/setNoteReadedState")
	public String setNoteReadedState(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			String fid = request.getParameter("fid");
			
			int isUnRead = Integer.parseInt(request.getParameter("isUnRead")) ;
			
			Note note = noteDao.Query(fid);
			
			note.setIsUnRead(isUnRead);
			
			noteDao.ExecSave(note);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

	private Note buildPo(HttpServletRequest request, IBaseDao dao) throws CloneNotSupportedException {
		String fid = request.getParameter("fid");
		
		Note note;
			
		if (fid != null  && !fid.isEmpty()) {
			
			note = noteDao.Query(fid);
			
		} else {
		
			note = (Note) request.getAttribute("Note");
			
			note.setFdate(new Date());
			note.setFuser(MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request));
			
			note = (Note) note.clone();
			
		}
		
		return note;
	}

	@RequestMapping(value = "/makeNotetoExcel")
	public String makeNotetoExcel(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		try {
			String sql= "";
			
			ListResult result = noteDao.QueryFilterList(sql, request);
			ExcelUtil.toexcel(reponse,result);
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}

		return null;

	}
	
	//已经在数据库控制外键级联，无需在此控制
	@RequestMapping(value = "/deleteNotes")
	public String deleteNotes(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {

		String fids = request.getParameter("fidcls");
		
		String fidsS = "('" + fids.replace(",", "','") + "')";

		try {
			
			String sql = " DELETE FROM t_sys_note WHERE fid in %s ";
			
			sql = String.format(sql, fidsS);
			
			noteDao.ExecBySql(sql);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功!", "", ""));
		} catch (Exception e) {
			reponse.getWriter().write(
					JsonUtil.result(false, e.toString(), "", ""));
		}
		return null;

	}
	
	/**
	 * 
	 * 目前用户是管理员
	 *
	 * @param request
	 * @param reponse
	 * @return
	 * @throws IOException
	 *
	 * @date 2015-1-17 上午9:29:21  (ZJZ)
	 */
	@RequestMapping(value = "/isNoteAdminCurrentUser")
	public String isNoteAdminCurrentUser(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException {
		
		try {
			
			boolean isAdmin = isNoteAdmin(request);
			
			JSONObject jso = new JSONObject();
			
			jso.put("isAdmin", isAdmin);
			
			reponse.getWriter().write(JsonUtil.result(true, "", "", jso.toString()));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	public boolean isNoteAdmin(HttpServletRequest request) {
		// TODO Auto-generated method stub
		
		StringBuilder builder = new StringBuilder("(");
		
		for (String[] idPs : ROLE_IDS) {
			
			if (!idPs[0].equals(ROLE_IDS[0][0])){
				
				builder.append(",");
				
			}
			
			builder.append(String.format("'%s'", idPs[0]));
			
		}
		
		builder.append(")");
		
		String hql = " select fuserid from UserRole where froleid in %s ";
		
		List<String> userIds = noteDao.QueryByHql(String.format(hql, builder.toString()));
		
		String currentUserId = MySimpleToolsZ.getMySimpleToolsZ().getCurrentUserId(request);
		
		return userIds.contains(currentUserId);

	}
	/**
	 * 
	 */
	@RequestMapping(value = "/findAnswerByTime")
	public String findAnswerByTime(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		String fids = request.getParameter("fid");
		String sql = "SELECT fid, fuser, fuser_name fuserName, fcontent, fphone, DATE_FORMAT(fdate,'%Y-%m-%d %H:%i:%s') fdate, fpre_note fpreNote, fprenote_msg fprenoteMsg,isUnRead FROM t_sys_note where fpre_note= '"+fids+"' order by  fdate";
		try{
		ListResult result = noteDao.QueryFilterList(sql, request);
		reponse.getWriter().write(JsonUtil.result(true, "", result));
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	
	/**
    *
    * @param theString
    * @return String
    */
   public static String unicodeToUtf8(String theString) {
       char aChar;
       int len = theString.length();
       StringBuffer outBuffer = new StringBuffer(len);
       for (int x = 0; x < len;) {
           aChar = theString.charAt(x++);
           if (aChar == '\\') {
               aChar = theString.charAt(x++);
               if (aChar == 'u') {
                   // Read the xxxx
                   int value = 0;
                   for (int i = 0; i < 4; i++) {
                       aChar = theString.charAt(x++);
                       switch (aChar) {
                       case '0':
                       case '1':
                       case '2':
                       case '3':
                       case '4':
                       case '5':
                       case '6':
                       case '7':
                       case '8':
                       case '9':
                           value = (value << 4) + aChar - '0';
                           break;
                       case 'a':
                       case 'b':
                       case 'c':
                       case 'd':
                       case 'e':
                       case 'f':
                           value = (value << 4) + 10 + aChar - 'a';
                           break;
                       case 'A':
                       case 'B':
                       case 'C':
                       case 'D':
                       case 'E':
                       case 'F':
                           value = (value << 4) + 10 + aChar - 'A';
                           break;
                       default:
                           throw new IllegalArgumentException(
                                   "Malformed   \\uxxxx   encoding.");
                       }
                   }
                   outBuffer.append((char) value);
               } else {
                   if (aChar == 't')
                       aChar = '\t';
                   else if (aChar == 'r')
                       aChar = '\r';
                   else if (aChar == 'n')
                       aChar = '\n';
                   else if (aChar == 'f')
                       aChar = '\f';
                   outBuffer.append(aChar);
               }
           } else
               outBuffer.append(aChar);
       }
       return outBuffer.toString();
   }

	@RequestMapping(value = "/saveNoteForBoard")
	public String saveNoteForBoard(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		
		try{
			String fcontent = unicodeToUtf8(request.getParameter("fcontent"));
			String userId = MySimpleToolsZ.getMySimpleToolsZ()
					.getCurrentUserId(request);

			String hql = " select fname, ftel from SysUser where fid = '%s' ";

			hql = String.format(hql, userId);
			java.util.List<?> nameAndPhone = (java.util.List<?>) noteDao.QueryByHql(hql);
			JSONArray jsonArray =JSONArray.fromObject(nameAndPhone);
			String[] arr = jsonArray.getString(0).split(",");
			String name = arr[0].substring(2, arr[0].length()-1);
			String phone = arr[1].substring(1, arr[1].length()-2);
			List<Note> notes = new ArrayList<Note>();
			Note po = new Note();
			po.setFuser(userId);
			po.setFuserName(name);
			po.setFcontent(fcontent);
			po.setFphone(phone);
			po.setFdate(new Date());
			po.setFpreNote("-1");
			notes.add(po);
			noteDao.ExecSaveNotes(notes);
			
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));

			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping(value = "/saveNoteAnswerForBoard")
	public String saveNoteAnswerForBoard(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		reponse.setCharacterEncoding("utf-8");
		String answerMsg = request.getParameter("answerMsg");
		String answerPerson = request.getParameter("answerPerson");
		String fprnote = request.getParameter("fid");
		try{
			String userId = MySimpleToolsZ.getMySimpleToolsZ()
					.getCurrentUserId(request);
			
			if(StringUtils.isEmpty(userId)){
				userId = "f3e31688-3c1b-11e5-85a0-00ff6b42e1e5";
			}
			
			String hql = " select fname, ftel from SysUser where fid = '%s' ";

			hql = String.format(hql, userId);
			java.util.List<?> nameAndPhone = (java.util.List<?>) noteDao.QueryByHql(hql);
			JSONArray jsonArray =JSONArray.fromObject(nameAndPhone);
			String[] arr = jsonArray.getString(0).split(",");
			String name = arr[0].substring(2, arr[0].length()-1);
			String phone = arr[1].substring(1, arr[1].length()-2);
			answerPerson.replaceFirst("回复", "@");
			List<Note> notes = new ArrayList<Note>();
			Note po = new Note();
			po.setFuser(userId);
			po.setFuserName(name);
			po.setFphone(phone);
			po.setFdate(new Date());
			po.setFcontent(answerMsg);
			po.setFpreNote(fprnote);
			po.setFprenoteMsg(answerPerson);
			notes.add(po);
			noteDao.ExecSaveNotes(notes);
			reponse.getWriter().write(JsonUtil.result(true, "成功", "",""));
			
			
		} catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}
	@RequestMapping(value = "/getNowUserName")
	public String getNowUserName(HttpServletRequest request,HttpServletResponse reponse) throws IOException {
		try{
			String userId = MySimpleToolsZ.getMySimpleToolsZ()
					.getCurrentUserId(request);

			String hql = " select fname, ftel from SysUser where fid = '%s' ";
			hql = String.format(hql, userId);
			java.util.List<?> nameAndPhone = (java.util.List<?>) noteDao.QueryByHql(hql);
			JSONArray jsonArray =JSONArray.fromObject(nameAndPhone);
			String[] arr = jsonArray.getString(0).split(",");
			String name = arr[0].substring(2, arr[0].length()-1);
			JSONObject jso = new JSONObject();
			jso.put("nameForAnswer", name);
			reponse.getWriter().write(JsonUtil.result(true, "成功","", jso.toString()));
		}catch (DJException e) {
			// TODO Auto-generated catch block
			reponse.getWriter().write(
					JsonUtil.result(false, e.getMessage(), "", ""));
		}
		return null;
	}

}