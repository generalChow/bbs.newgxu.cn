package cn.newgxu.bbs.web.action.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.service.DiaryService;
import cn.newgxu.bbs.web.action.AbstractBaseAction;
import cn.newgxu.bbs.web.model.diary.DiaryModel;

import com.opensymphony.webwork.ServletActionContext;

/**
 * 
 * @author 叨叨雨
 * @since 4.0.0
 * @version $Revision 1.1$
 * @since 2010-10-10
 * @for 增加日记的管理类
 */
@SuppressWarnings("serial")
public class DiaryManagerAction extends AbstractBaseAction{
	private DiaryModel model = new DiaryModel();

	private DiaryService diaryService;
	
	@Override
	public String execute() throws Exception {
		return null;
	}

	/**只有管理员才可以删除日志，前台显示删除按钮
	 * @return
	 * @throws IOException 
	 */
	public void deleteDiary() throws IOException{
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter writer = null;
		writer = response.getWriter();
		try {
			model.setUser(getUser());
			diaryService.delDiaryByAdmin(model);
			writer.write("success");
		} catch (BBSException e) {
			writer.write("error");
		}
	}
	public Object getModel() {
		return model;
	}

	public DiaryService getDiaryService() {
		return diaryService;
	}

	public void setDiaryService(DiaryService diaryService) {
		this.diaryService = diaryService;
	}

	
}
