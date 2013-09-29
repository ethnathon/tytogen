package org.andromda.cartridges.jsf.forms;

public interface Resettable {

	void interUseCaseReset(String useCaseName);

	void preserveField(String fieldName);
	
	/*
	 * useful with dialogs
	 */
	void setPreserveErrors(boolean preserveErrors);
}
