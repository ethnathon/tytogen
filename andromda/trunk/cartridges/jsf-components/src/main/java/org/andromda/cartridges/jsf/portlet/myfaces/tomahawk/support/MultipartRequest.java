package org.andromda.cartridges.jsf.portlet.myfaces.tomahawk.support;

import java.util.Map;
import org.apache.commons.fileupload.FileItem;

/**
 * This interface handles the multipart request for inputFileUpload components.
 *
 * @author <a href="mailto:shinsuke@yahoo.co.jp">Shinsuke Sugaya</a>
 */
public interface MultipartRequest
{
    // Hook for the t:inputFileUpload tag.
    /**
     * @param fieldName
     * @return fileItem
     */
    public abstract FileItem getFileItem(String fieldName);

    /**
     * Not used internally by MyFaces, but provides a way to handle the uploaded
     * files out of MyFaces.
     * @return fileItems
     */
    public abstract Map getFileItems();
}
