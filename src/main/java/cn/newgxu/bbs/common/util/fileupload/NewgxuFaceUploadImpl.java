package cn.newgxu.bbs.common.util.fileupload;

import java.awt.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.Uploadable;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class NewgxuFaceUploadImpl implements NewgxuFaceUpload {

	private static final Log log = LogFactory
			.getLog(NewgxuFileUploadUtil.class);

	public List<UploadItem> convertItems(List<FileItem> items)
			throws BBSException {

		List<UploadItem> uploadItems = new ArrayList<UploadItem>(items.size());
		for (FileItem item : items) {
			if (!item.isFormField()) {
				String filename = Util.getSafeFilename(item.getName());

				if (log.isDebugEnabled()) {
					log.debug("filename:" + filename);
				}

				String ext = Util.getFilenameExtension(filename);
				if (!Util.checkFileExt(ext)) {
					throw new BBSException(BBSExceptionMessage.FACE_EXT_ERROR);
				}

				Uploadable uploadable;
				try {
					uploadable = Uploadable.getByFilename(filename);
					UploadItem uploadItem = new UploadItem();
					uploadItem.setUploadable(uploadable);
					uploadItem.setFilename(filename);
					uploadItem.setUploadTime(new Date());
					uploadItem.setFileSize((int) (item.getSize() / 1024) + 1);

					String path = Util.getUploadFacePath(uploadable
							.getFilenameExtension());
					File tempFile = new File(path + ".tmp");
					try {
						item.write(tempFile);
						uploadItem.setStoragePath(tempFile.toString());
						uploadItems.add(uploadItem);
					} catch (Exception e) {
						log.error(e);
						e.printStackTrace();
					}
					if (!checkMagSize(tempFile)) {
						tempFile.delete();
						throw new BBSException(
								BBSExceptionMessage.FACE_SIZE_ERROR);
					}
				} catch (ObjectNotFoundException e) {
					log.debug(e);
				}

			}
		}
		return uploadItems;
	}

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

	public boolean checkMagSize(File file) throws BBSException {
		int width = 0, height = 0;
		try {
			Image src = ImageIO.read(file);
			width = src.getWidth(null);
			height = src.getHeight(null);
		} catch (Exception e) {
			log.debug(e);
			return false;
		}
		if (width < 1 || width > 125 || height < 1 || height > 125) {
			return false;
		}
		return true;
	}
}
