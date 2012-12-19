package cn.newgxu.bbs.common.util.fileupload;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author polly
 * @since 4.0.0
 * @version $Revision: 1.1 $
 * 
 */
public class NewgxuFileUploadStats {

	public static final int READY = 0;

	public static final int UPLOADING = 1;

	public static final int PROCESSING = 2;

	public static final int FILISH = 3;

	/**
	 * 0 准备开始上传；1 正在上传；2 正在处理；3 上传完毕。
	 */
	private int stat = READY;

	private long contentLength = -1L;

	private long bytesRead = 1L;

	private long startTime = System.currentTimeMillis();

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
		if (this.bytesRead <= 0) {
			this.bytesRead = 1L;
		}
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public int getStat() {
		return stat;
	}

	public void setStat(int stat) {
		this.stat = stat;
	}

	public String getTotalFormat() {
		return formart(this.contentLength);
	}

	public String getReadFormat() {
		return formart(this.bytesRead);
	}

	private String formart(long num) {
		if (num < 1024) {
			return num + "b";
		}

		NumberFormat formatter = new DecimalFormat(".#");
		double k = num / 1024;
		if (k < 1024) {
			return formatter.format(k) + "K";
		} else {
			return formatter.format(k / 1024) + "M";
		}
	}

	public String getTimeRanStr() {
		return formatTime(getTimeRan());
	}

	public String getTimeLastStr() {
		return formatTime(getTimeLast());
	}

	public String getSpeedStr() {
		int time = (int) (getTimeRan() / 1000);
		if (time <= 0) {
			time = 1;
		}
		return (formart(this.bytesRead / time) + "/s");
	}

	/*
	 * 已经运行时间
	 */
	public long getTimeRan() {
		return System.currentTimeMillis() - startTime;
	}

	/*
	 * 剩下时间
	 */
	private long getTimeLast() {
		return getTimeRan() * 100 / getFilishPer() - getTimeRan();
	}

	private String formatTime(long t) {
		int sec = (int) (t / 1000);
		if (sec > 3600) {
			return (sec / 3600) + "小时" + ((sec % 3600) / 60) + "分";
		} else if (sec > 60 && sec <= 3600) {
			return (sec / 60) + "分" + (sec % 60) + "秒";
		} else {
			return sec + "秒";
		}
	}

	/*
	 * 完成百分比 （1～100 之间）
	 */
	public int getFilishPer() {
		int result = 0;
		// 除数不能为0
		if (this.contentLength <= 0) {
			result = 0;
		} else {
			result = (int) ((this.bytesRead * 100) / this.contentLength);
		}
		return result <= 0 ? 1 : result;
	}

	public String getStatString() {
		switch (stat) {
		case READY:
			return "正在准备上传...";
		case UPLOADING:
			return "正在上传中...";
		case PROCESSING:
			return "系统正在处理...";
		case FILISH:
			return "上传完毕！";
		}
		return "未知状态，请稍候...";
	}
}
