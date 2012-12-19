package cn.newgxu.bbs.web.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.util.Util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ValidImageServlet extends BaseServlet {

	private static final long	serialVersionUID	= 5146905313018020357L;

	private static final Log	log					= LogFactory.getLog(ValidImageServlet.class);

	public
			void service(
					HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {
		String validCode = Util.getRandomString();
		Util.saveValidCode(request.getSession(), validCode);
		response(response, getBufferedImage(validCode));
	}

	/**
	 * 给定范围获得随机颜色
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 */
	public Color getRandColor(int fc, int bc) {
		Random random = new Random();
		fc = fc > 255 ? 255 : fc;
		bc = bc > 255 ? 255 : bc;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 通过文件创建图像 格式为jpg类型
	 * 
	 * @param fileName
	 * @param content
	 */
	public void creatImage(String fileName, String content) {
		try {
			File f = new File(fileName);
			if (!f.exists()) {
				f.createNewFile();
			} else {
				Thread.sleep(200);
				f.delete();
				f.createNewFile();
			}
			ImageIO.write(getBufferedImage(content), "jpg", f);
		} catch (Exception e) {
			log.warn(e);
		}
	}

	/**
	 * 创建图像 格式为jpg类型
	 * 
	 * @param content - String 图片输出内容
	 * @return java.awt.image.BufferedImage
	 * @since 2005-7-19
	 */
	public BufferedImage getBufferedImage(String content) {
		return getBufferedImage(content, 40, Constants.RANDOM_STRING_HEIGHT);
	}

	/**
	 * 创建图像 格式为jpg类型
	 * 
	 * @param content - String 图片输出内容
	 * @param width - int 图片宽度
	 * @param height - int 图片高度
	 * @return java.awt.image.BufferedImage
	 */
	public
			BufferedImage
			getBufferedImage(String content, int width, int height) {
		// 在内存中创建图象
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics2D g = image.createGraphics();
		// 设定背景色
		g.setColor(getRandColor(200, 250));
		g.fillRect(0, 0, width, height);
		// 设定字体
		// g.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		g.setFont(new Font("宋体", Font.PLAIN, 18));
		g.setColor(Color.black);// 黑色文字
		g.drawString(content, 3, 13);
		g.dispose();
		return image;
	}

	/**
	 * 将现有BufferedImage融合进Response
	 * 
	 * @param response - javax.servlet.http.ServletResponse 将使用的response对象
	 * @param img - java.awt.image.BufferedImage
	 */
	public void response(HttpServletResponse response, BufferedImage img) {
		try {
			response.setContentType("image/jpeg");// 设定输出的类型
			response.setHeader("Cache-Control", "no-cache");
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(response.getOutputStream());
			encoder.encode(img);// 对图片进行输出编码
		} catch (Exception e) {
			log.warn(e);
		}
	}

	/**
	 * 测试
	 */
	public static void main(String[] args) throws Exception {
		ValidImageServlet c = new ValidImageServlet();
		c.creatImage("H:/Downloads/temp/1.jpg", Util.getRandomString());
	}

}
