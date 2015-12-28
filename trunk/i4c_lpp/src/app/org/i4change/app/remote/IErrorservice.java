package org.i4change.app.remote;

import org.i4change.app.data.basic.beans.ErrorResult;

public interface IErrorservice {

	/**
	 * Gets an Error-Object by its id
	 * TODO: add error-code-handlers
	 * -20 duplicate FileName
	 * -21 FileName too short (length = 0)
	 * and make the persistent in the DataBase
	 * @param SID
	 * @param errorid
	 * @return
	 */
	public abstract ErrorResult getErrorByCode(String SID, Long errorid,
			Long language_id);

}