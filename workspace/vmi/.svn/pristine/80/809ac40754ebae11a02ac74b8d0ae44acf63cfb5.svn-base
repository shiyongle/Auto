package Com.Base.Util.mySimpleUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;


public class ValidateCode {

	public static final String RANDOMCODEKEY = "RANDOMVALIDATECODEKEY";// 放到session中的key
	private static final String RONDOMSTRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";// 随机产生的字符串
	private Random random = new Random();
	private int count;
	private String code = null;
	private int width = 80;// 图片宽
	private int height = 26;// 图片高
	private int lineSize = 40;// 干扰线数量
	private final int codeNum = 4;// 随机产生字符数量
	private Timer timer;// 实例化Timer类

	
	public String getCode() {
		return code;
	}
	
	public void clearCode() {
		count = 0;
		if(code != null){
			code = null;
			timer.cancel();
		}
	}
	
	public void addCount(){
		if(code == null){
			count++;
		}
		if(count==3){
			code = generateRandomCode();
			timer = new Timer();
			timer.schedule(new TimerTask(){
				public void run() {
					code = null;
					count = 0;
				}
			},1000 * 60 * 5);
		}else{
			reclocking();
		}
	}
	public void reclocking(){	// 重新计时,清除验证码
		if(code != null){
			timer.cancel();
			timer = new Timer();
			timer.schedule(new TimerTask(){
				public void run() {
					code = null;
					count = 0;
				}
			},1000 * 60 * 5);
		}
	}

	private String generateRandomCode(){
		String code = "";
		int num;
		for(int i=0;i<codeNum;i++){
			num = random.nextInt(RONDOMSTRING.length());
			code += RONDOMSTRING.charAt(num);
		}
		return code;
	}
	/**
	 * 生成随机图片
	 */
	public void getRandomCode(HttpServletResponse response) {
		if(code != null){
			code = generateRandomCode();
		}
		// BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
		BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR);
		Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 18));
		g.setColor(getRandColor(110, 133));
		// 绘制干扰线
		for (int i = 0; i <= lineSize; i++) {
			drawLine(g);
		}
		drawString(g,code!=null?code:"1234");	//校验码为空时，前端可以随意输入
		g.dispose();
		try {
			ImageIO.write(image, "JPEG", response.getOutputStream());// 将内存中的图片通过流动形式输出到客户端
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * 获得字体
	 */
	private Font getFont() {
		return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
	}
	
	/*
	 * 获得颜色
	 */
	private Color getRandColor(int fc, int bc) {
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc - 16);
		int g = fc + random.nextInt(bc - fc - 14);
		int b = fc + random.nextInt(bc - fc - 18);
		return new Color(r, g, b);
	}
	/*
	 * 绘制字符串
	 */
	private void drawString(Graphics g, String code){
		int length = code.length();
		g.setFont(getFont());
		for(int i=0;i<length;i++){
			g.setColor(new Color(random.nextInt(101), random.nextInt(111), random.nextInt(121)));
			g.translate(random.nextInt(3), random.nextInt(3));
			g.drawString(String.valueOf(code.charAt(i)),13*i,16);
		}
	}

	/*
	 * 绘制干扰线
	 */
	private void drawLine(Graphics g) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(13);
		int yl = random.nextInt(15);
		g.drawLine(x, y, x + xl, y + yl);
	}

	
}
