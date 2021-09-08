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
package gov.nist.itl.ssd.wipp.backend.data.statmodeling.manifest.layers;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author Mylene Simon <mylene.simon at nist.gov>
 *
 */
public class ColonyFeatures {
	
	@JsonDeserialize(using = ColonyServiceUrlManualRefDeserializer.class)
	@JsonSerialize(using = ColonyServiceUrlManualRefSerializer.class)
	private String serviceUrl;
	
	private String framesPrefix;
	
	private String framesSuffix;
	
	private int paddingSize;
	
	private String layer;

	public String getServiceUrl() {
		return serviceUrl;
	}

	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}

	public String getFramesPrefix() {
		return framesPrefix;
	}

	public void setFramesPrefix(String framesPrefix) {
		this.framesPrefix = framesPrefix;
	}

	public String getFramesSuffix() {
		return framesSuffix;
	}

	public void setFramesSuffix(String framesSuffix) {
		this.framesSuffix = framesSuffix;
	}

	public int getPaddingSize() {
		return paddingSize;
	}

	public void setPaddingSize(int paddingSize) {
		this.paddingSize = paddingSize;
	}

	public String getLayer() {
		return layer;
	}

	public void setLayer(String layer) {
		this.layer = layer;
	}

}
