package gov.nist.itl.ssd.wipp.backend.argo.workflows.spec;

import java.util.List;
import java.util.Map;

public class ArgoLifecycleHook {

    private String template;
    private String expression;
    private Map<String, List<NameValueParam>> arguments;;


    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Map<String, List<NameValueParam>> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, List<NameValueParam>> arguments) {
        this.arguments = arguments;
    }
}
