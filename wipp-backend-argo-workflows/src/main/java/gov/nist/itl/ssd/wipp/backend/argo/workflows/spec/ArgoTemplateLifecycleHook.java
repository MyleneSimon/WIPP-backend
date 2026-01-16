package gov.nist.itl.ssd.wipp.backend.argo.workflows.spec;

import java.util.List;
import java.util.Map;

/**
 * @author Mylene Simon <mylene.simon at nist.gov>
 *
 */
public class ArgoTemplateLifecycleHook extends ArgoAbstractTemplate {

	private ArgoTemplateHttp http;
	private Map<String, List<NameValueParam>> inputs;

	public ArgoTemplateHttp getHttp() {
		return http;
	}

	public void setHttp(ArgoTemplateHttp http) {
		this.http = http;
	}

    public Map<String, List<NameValueParam>> getInputs() { return inputs; }

    public void setInputs(Map<String, List<NameValueParam>> inputs) { this.inputs = inputs; }
}
