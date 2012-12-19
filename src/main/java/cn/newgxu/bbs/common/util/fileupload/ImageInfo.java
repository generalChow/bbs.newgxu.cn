package cn.newgxu.bbs.common.util.fileupload;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于获取图片的的属性
 * @since 2012-03-07
 * @author ivy
 */
public class ImageInfo {
	/**
	 * 简单地获取图片的宽度和高度
	 * @param imagePath 图片的绝对路径
	 * @return 包含图片的宽度和高度的属性键值对
	 */
	public static Map<String, Integer> getImgInfo(String imagePath) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		BufferedImage bufferedIamge = getBufferedImage(imagePath);
		map.put("width", bufferedIamge.getWidth());
		map.put("height", bufferedIamge.getHeight());
		return map;
	}

	/**
	 * 获取一个访问图像数据缓冲区的类，该类封转了图像属性的各种方法
	 * @param imagePath 图像的绝对地址
	 * @return BufferedImage
	 */
	public static BufferedImage getBufferedImage(String imagePath) {
		FileInputStream fileInputStream = null;
		BufferedImage bufferedImage = null;
		try {
			fileInputStream = new FileInputStream(new File(imagePath));
			bufferedImage = ImageIO.read(fileInputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fileInputStream != null)
					fileInputStream.close();
				fileInputStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bufferedImage;
	}

	// private static File file;
	// private static BufferedImage bufferedImage;
	//        
	// private TestImg(String imagePath) {
	// try {
	// file = new File(imagePath);
	// bufferedImage = ImageIO.read(file);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }

	// public static TestImg getInstance(String imagePath) {
	// return new TestImg(imagePath);
	// }
	//        
	// public int getHeight() {
	// return bufferedImage.getHeight();
	// }
	//        
	// public int getWidth() {
	// return bufferedImage.getWidth();
	// }
	//        
	// public long getSize() {
	// return file.length();
	// }

	public static void main(String[] args) {
		String p = "d:/1.jpg";
		Map<String, Integer> m = getImgInfo(p);
		for (Map.Entry<String, Integer> entry : m.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}