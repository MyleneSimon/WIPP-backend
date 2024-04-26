/*
 * This software was developed at the National Institute of Standards and
 * Technology by employees of the Federal Government in the course of
 * their official duties. Pursuant to title 17 Section 105 of the United
 * States Code this software is not subject to copyright protection and is
 * in the public domain. This software is an experimental system. NIST assumes
 * no responsibility whatsoever for its use by other parties, and makes no
 * guarantees, expressed or implied, about its quality, reliability, or
 * any other characteristic. We would appreciate acknowledgement if the
 * software is used.
 */
package gov.nist.itl.ssd.wipp.backend.argo.workflows.spec;

/**
 * Argo PersistentVolumeClaim spec
 *  
 * @author Mylene Simon <mylene.simon at nist.gov>
 *
 */
public class ArgoEmptyDir {

	private String medium;

	public ArgoEmptyDir(String medium) {
		this.medium = medium;
	}

	public String getMedium() {
		return medium;
	}

	public void setMedium(String medium) {
		this.medium = medium;
	}
}
