<%@ page contentType="image/jpeg" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*,cn.newgxu.bbs.common.util.Util" %>
<%
// 在内存中创建图象 
int width=42, height=16; 
BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 

// 获取图形上下文 
Graphics g = image.getGraphics(); 

// 设定背景色 
g.setColor(new Color(0xDCDCDC)); 
g.fillRect(0, 0, width, height); 

//画边框 
//g.setColor(Color.black); 
//g.drawRect(0,0,width-1,height-1); 

// 取随机产生的认证码(4位数字) 
String rand = Util.getRandomString();

// 将认证码存入SESSION 
Util.saveValidCode(session, rand); 

// 将认证码显示到图象中 
g.setColor(Color.black); 
String numberStr = rand;

g.setFont(new Font("Lucida Console",Font.PLAIN,15)); 
String Str = numberStr.substring(0,1); 
g.drawString(Str,1,13); 

Str = numberStr.substring(1,2); 
g.drawString(Str,11,14); 
Str = numberStr.substring(2,3); 
g.drawString(Str,21,13); 

Str = numberStr.substring(3,4); 
g.drawString(Str,31,14); 

// 随机产生88个干扰点，使图象中的认证码不易被其它程序探测到 
Random random = new Random(); 
for (int i=0;i<40;i++) { 
	int x = random.nextInt(width); 
	int y = random.nextInt(height); 
	g.drawOval(x,y,0,0); 
} 

// 图象生效 
g.dispose(); 

// 输出图象到页面 
ImageIO.write(image, "JPEG", response.getOutputStream()); 
%>
