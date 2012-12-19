package cn.newgxu.bbs.common.util;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class ExcelResult implements Result{
	private static final long serialVersionUID = 1L;
	private HSSFWorkbook workbook;
    private String filename;
    private String contenttype;
    public void execute(ActionInvocation invocation) throws Exception {
        if(contenttype==null)
            contenttype = "application/ms-excel";
        if (workbook==null)
            workbook = (HSSFWorkbook) invocation.getStack().findValue("workbook");
      

        HttpServletResponse response = ServletActionContext.getResponse();
        response.setContentType(contenttype);
        response.setHeader("Content-Disposition","attachment;Filename="+filename+".xls");
        OutputStream os = response.getOutputStream();
        workbook.write(os);
        os.flush();
        os.close();
    }

    public void setWorkbook(HSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }
}