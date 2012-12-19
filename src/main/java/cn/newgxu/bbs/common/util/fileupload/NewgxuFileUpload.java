package cn.newgxu.bbs.common.util.fileupload;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import cn.newgxu.bbs.domain.UploadItem;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @author polly
 * @since 4.0.0
 * @version $Revision: 1.2 $
 * 
 */
public interface NewgxuFileUpload {

	public List<FileItem> upload(HttpServletRequest request,
			final NewgxuFileUploadStats stats) throws FileUploadException;

	public NewgxuFileUploadStats getStats(HttpServletRequest request);

	public List<UploadItem> convertItems(List<FileItem> items) throws ObjectNotFoundException, Exception;

}
