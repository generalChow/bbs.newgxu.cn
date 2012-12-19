package cn.newgxu.bbs.web.action.admin;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.opensymphony.xwork.ActionSupport;

public class ExportExcelAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private HSSFWorkbook workbook;

	public String execute() throws Exception {
		return SUCCESS;
	}

	@SuppressWarnings("deprecation")
	public String product() throws Exception {
		try {
			workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet();

			workbook.setSheetName(0, "厂商产品", (short) 1);
			HSSFRow row = sheet.createRow((short) 0);

			HSSFCell cell0 = row.createCell((short) 0);
			HSSFCell cell1 = row.createCell((short) 1);
			HSSFCell cell2 = row.createCell((short) 2);
			HSSFCell cell3 = row.createCell((short) 3);
			HSSFCell cell4 = row.createCell((short) 4);
			HSSFCell cell5 = row.createCell((short) 5);
			HSSFCell cell6 = row.createCell((short) 6);
			HSSFCell cell7 = row.createCell((short) 7);
			HSSFCell cell8 = row.createCell((short) 8);
			HSSFCell cell9 = row.createCell((short) 9);

			cell0.setEncoding(HSSFCell.ENCODING_UTF_16);// 这里是设置编码保证中文正常显示
			cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell9.setEncoding(HSSFCell.ENCODING_UTF_16);

			cell0.setCellValue("厂商名");
			cell1.setCellValue("产品名");
			cell2.setCellValue("重量");
			cell3.setCellValue("星级");
			cell4.setCellValue("parama");
			cell5.setCellValue("paramb");
			cell6.setCellValue("paramc");
			cell7.setCellValue("paramd");
			cell8.setCellValue("状态");
			cell9.setCellValue("备注");

		} catch (Exception e) {
		}
		return SUCCESS;
	}

	public HSSFWorkbook getWorkbook() {
		return workbook;
	}

}
