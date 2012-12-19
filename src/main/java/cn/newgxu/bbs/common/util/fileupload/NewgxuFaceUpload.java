package cn.newgxu.bbs.common.util.fileupload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.domain.UploadItem;

/**
 * 
 * @author 红叶狐
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public interface NewgxuFaceUpload {
	public List<FileItem> upload(HttpServletRequest request,
			final NewgxuFileUploadStats stats) throws FileUploadException;

	public NewgxuFileUploadStats getStats(HttpServletRequest request);

	public List<UploadItem> convertItems(List<FileItem> items)
			throws BBSException;
}
