package Com.Base.data;

import java.util.HashMap;
import java.util.Map;

/**
 * 窗口类型
 * 
 * @version 0.1 
 *
 * @author ZJZ (447338871@qq.com, any Question) 
 * @date 2013-12-24 下午3:59:43
 */
public class CusWinType {

	private String fid;
	private String ftype;
	private String fcode;
	private String ftypename;

	public String getFtypename() {
		return ftypename;
	}

	public void setFtypename(String ftypename) {
		this.ftypename = ftypename;
	}

	public CusWinType() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CusWinType(String fid, String ftype, String ftypename, String fcode) {
		super();
		this.fid = fid;
		this.ftype = ftype;
		this.fcode = fcode;
		this.ftypename = ftypename;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public String getFcode() {
		return fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	/**
	 * 类型数组，新增时在此添加
	 */
	public static final String[][] initCfgTypes = new String[][] {
			{ "cus", "自定义", "" },
//			{
//					"baiduWestList",
//					"百度风云榜",
//					"<iframe vspace=\"0\" hspace=\"0\" scrolling=\"no\" frameborder=\"0\" id=\"clip\" name=\"clip\" width=\"300\" height=\"355\" src=\"http://top.baidu.com/clip?b=1\" ></iframe>" },
//			{
//					"mobilePhoneList",
//					"手机排行榜",
//					"<iframe vspace=\"0\" hspace=\"0\" scrolling=\"no\" frameborder=\"0\" id=\"clip\" name=\"clip\" width=\"300\" height=\"355\" src=\"http://top.baidu.com/clip?b=238&hd_h_info=1&p_name=%E4%BB%8A%E6%97%A5%E6%89%8B%E6%9C%BA%E4%BA%A7%E5%93%81%E6%8E%92%E8%A1%8C%E6%A6%9C\" ></iframe>" },
			{
					"weather",
					"天气",
					"<iframe src=\"http://m.weather.com.cn/m/pn12/weather.htm \" width=\"245\" height=\"110\"marginheight=\"0\" hspace=\"0\" vspace=\"0\" frameborder=\"0\" scrolling=\"no\"></iframe>"},
//			{
//					"weather2",
//					"天气2",
//					"<iframe src=\"http://m.weather.com.cn/m/pn11/weather.htm \" width=\"490\" height=\"50\"marginheight=\"0\" hspace=\"0\" vspace=\"0\" frameborder=\"0\" scrolling=\"no\"></iframe>" },
			{
					"baiduToolbar",
					"百度工具栏",
					"<iframe id=\"baiduframe\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\"framespacing=\"0\" vspace=\"0\" hspace=\"0\" frameborder=\"0\" width=\"400\" height=\"90\"src=\"http://unstat.baidu.com/bdun.bsc?tn=hekai_pg&cv=0&cid=1133937&csid=242&bgcr=ffffff&ftcr=000000&urlcr=0000ff&tbsz=335&sropls=1,2,3,4,5,6&kwgp=0\"></iframe>"}

			,
			{
					"sinaNews",
					"新浪新闻",
					"<IFRAME border=0 name=sina_roll marginWidth=0 marginHeight=0 src=http://news.sina.com.cn/o/allnews/input/index.html frameBorder=Nowidth=\"100%\" scrolling=no height=15></IFRAME>"},
			{
					"rollingNews",
					"滚动新闻",
					"<iframe frameborder=0 src=\"http://www.acs.gov.cn/sites/aqzn/jrxwiframe.jsp\"></iframe>"}
					
//					, 
//			{
//					"cctv",
//					"CCTV",
//					"<iframe src=http://www.cctv.com/homepage/46/index.shtml; name=\"express\" width=\"100%\" height=\"20\" marginwidth=\"0\" marginheight=\"0\" frameborder=\"0\"scrolling=\"no\"></iframe>" },
//			{
//					"sportsNews",
//					"体育新闻",
//					"<iframe WIDTH=100% HEIGHT=20 MARGINWIDTH=0 MARGINHEIGHT=0 HSPACE=0 VSPACE=0 FRAMEBORDER=0 SCROLLING=noSRC=\'http://links.news.sohu.com/scscsp002.html?uid=73916&fid=scscsp002\'></iframe>" },
//			{
//					"automotiveNews",
//					"汽车新闻",
//					"<iframe frameborder=0 leftmargin=0 marginheight=0 marginwidth=0 scrolling=no src=\"http://miu.vip.sina.com/news/1/15.htm\" topmargin=0 width=760 height=105></iframe>" },
//			{
//					"entertainmentNews",
//					"娱乐新闻",
//					"<iframe WIDTH=100% HEIGHT=20 MARGINWIDTH=0 MARGINHEIGHT=0 HSPACE=0 VSPACE=0 FRAMEBORDER=0 SCROLLING=no SRC=\'http://links.news.sohu.com/scscen002.html?uid=73916&fid=scscen002\'></iframe>" },
//			{
//					"businessNews",
//					"财经新闻",
//					"<iframe WIDTH=100% HEIGHT=20 MARGINWIDTH=0 MARGINHEIGHT=0 HSPACE=0 VSPACE=0 FRAMEBORDER=0 SCROLLING=noSRC=\'http://links.news.sohu.com/scscbu002.html?uid=73916&fid=scscbu002\'></iframe>" }
//					
//					

	};

	/**
	 * 初始化的映射
	 */
	public static final Map<String, CusWinType> cusWinTypes = new HashMap<String, CusWinType>();

	static {

		for (int i = 0; i < initCfgTypes.length; i++) {

			CusWinType cwt = new CusWinType(i + "", initCfgTypes[i][0],
					initCfgTypes[i][1],
					initCfgTypes[i][2].replaceAll("\"", "'"));

			cusWinTypes.put(i + "", cwt);

		}

	}
}
