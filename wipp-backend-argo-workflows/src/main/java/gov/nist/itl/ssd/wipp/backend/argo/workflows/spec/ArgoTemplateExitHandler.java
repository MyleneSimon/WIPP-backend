package gov.nist.itl.ssd.wipp.backend.argo.workflows.spec;

/**
 * @author Mylene Simon <mylene.simon at nist.gov>
 *
 */
public class ArgoTemplateExitHandler extends ArgoAbstractTemplate {

	private ArgoTemplateExitHandlerContainer container;

	private ArgoTemplateHttp http;

	public ArgoTemplateExitHandlerContainer getContainer() {
		return container;
	}

	public void setContainer(ArgoTemplateExitHandlerContainer container) {
		this.container = container;
	}

	public ArgoTemplateHttp getHttp() {
		return http;
	}

	public void setHttp(ArgoTemplateHttp http) {
		this.http = http;
	}
	
}
