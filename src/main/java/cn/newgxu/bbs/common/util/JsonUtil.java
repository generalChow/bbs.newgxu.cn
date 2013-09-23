package cn.newgxu.bbs.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.opensymphony.webwork.ServletActionContext;

public class JsonUtil {

	public static void sendJsonStr(String json) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("application/json;charset=utf-8");
		try {
			PrintWriter writer = response.getWriter();

			writer.write(json);

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
