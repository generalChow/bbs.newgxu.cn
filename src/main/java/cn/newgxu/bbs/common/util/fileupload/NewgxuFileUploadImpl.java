package cn.newgxu.bbs.common.util.fileupload;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.Constants;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.Uploadable;

/**
 * @author polly
 * @since 4.0.0
 * @version $Revision: 1.2 $
 * 
 */
public class NewgxuFileUploadImpl implements NewgxuFileUpload {

	private static final Log log = LogFactory
			.getLog(NewgxuFileUploadUtil.class);

	public NewgxuFileUploadStats getStats(HttpServletRequest request) {
		HttpSession session = request.getSession();
		NewgxuFileUploadStats stats = (NewgxuFileUploadStats) session
				.getAttribute("uploadStat");

		if (stats == null) {
			stats = new NewgxuFileUploadStats();
		}

		return stats;
	}

	@SuppressWarnings("unchecked")
	public List<FileItem> upload(HttpServletRequest request,
			final NewgxuFileUploadStats stats) throws FileUploadException {
		HttpSession session = request.getSession();
		session.setAttribute(Constants.UPLOADING_STATS, stats);

		DiskFileItemFactory factory = new DiskFileItemFactory();
		// factory.setSizeThreshold(10240);
		// factory.setRepository(new
		// File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		ProgressListener progressListener = new ProgressListener() {
			public void update(long pBytesRead, long pContentLength, int pItems) {
				stats.setBytesRead(pBytesRead);
				stats.setContentLength(pContentLength);
			}
		};
		upload.setProgressListener(progressListener);

		List<FileItem> items = upload.parseRequest(request);

		return items;
	}

	public List<UploadItem> convertItems(List<FileItem> items) throws Exception {
		List<UploadItem> uploadItems = new ArrayList<UploadItem>(items.size());
		for (FileItem item : items) {
			if (!item.isFormField()) {
				String filename = Util.getSafeFilename(item.getName());
				if (log.isDebugEnabled()) {
					log.debug("filename:" + filename);
				}

				// 数据库查询可上传文件类型和大小限制
				Uploadable uploadable;
				uploadable = Uploadable.getByFilename(filename);
				UploadItem uploadItem = new UploadItem();
				uploadItem.setUploadable(uploadable);
				uploadItem.setFilename(filename);
				uploadItem.setUploadTime(new Date());
				uploadItem.setFileSize((int) (item.getSize() / 1024) + 1);

				String path = Util.getUploadFilePath(uploadable
						.getFilenameExtension());
				File tempFile = new File(path + ".tmp");
				item.write(tempFile);
				System.out.println("储存路径："+tempFile.toString());
				uploadItem.setStoragePath(tempFile.toString());
				uploadItems.add(uploadItem);
			}
		}
		return uploadItems;
	}

}
