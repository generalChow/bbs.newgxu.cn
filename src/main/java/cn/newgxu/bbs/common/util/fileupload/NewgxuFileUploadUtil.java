package cn.newgxu.bbs.common.util.fileupload;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.bbs.domain.Uploadable;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author polly
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class NewgxuFileUploadUtil {

	private static final Log log = LogFactory
			.getLog(NewgxuFileUploadUtil.class);

	public static List<UploadItem> convertItems(List<FileItem> items) {
		List<UploadItem> uploadItems = new ArrayList<UploadItem>(items.size());
		for (FileItem item : items) {
			if (!item.isFormField()) {
				String filename = Util.getSafeFilename(item.getName());

				if (log.isDebugEnabled()) {
					log.debug("filename:" + filename);
				}

				Uploadable uploadable;
				try {
					uploadable = Uploadable.getByFilename(filename);
					UploadItem uploadItem = new UploadItem();
					uploadItem.setUploadable(uploadable);
					uploadItem.setFilename(filename);
					uploadItem.setUploadTime(new Date());
					uploadItem.setFileSize((int) (item.getSize() / 1024) + 1);

					String path = Util.getUploadFilePath(uploadable
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
				} catch (ObjectNotFoundException e) {
					log.debug(e);
				}

			}
		}
		return uploadItems;
	}

}
