package gov.nist.itl.ssd.wipp.backend.argo.workflows.spec;

import java.util.Map;

/**
*
* @author Philippe Dessauw <philippe.dessauw at nist.gov>
* @author Mylene Simon <mylene.simon at nist.gov>
* 
*/
public class ArgoWorkflow {
    private final String apiVersion = "argoproj.io/v1alpha1";
    private final String kind = "Workflow";
    private Map<String, Object> metadata;
    private ArgoWorkflowSpec spec;

    public String getApiVersion() {
        return apiVersion;
    }

    public String getKind() {
        return kind;
    }


    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public ArgoWorkflowSpec getSpec() {
        return spec;
    }

    public void setSpec(ArgoWorkflowSpec spec) {
        this.spec = spec;
    }
}
