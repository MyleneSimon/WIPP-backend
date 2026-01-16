package gov.nist.itl.ssd.wipp.backend.argo.workflows.spec;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

public class ArgoTemplateWorkflowTask {
    private String name;
    private String template;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> dependencies;

    private Map<String, List<NameValueParam>> arguments;

    private Map<String, ArgoLifecycleHook> hooks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public List<String> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<String> dependencies) {
        this.dependencies = dependencies;
    }

    public Map<String, List<NameValueParam>> getArguments() {
        return arguments;
    }

    public void setArguments(Map<String, List<NameValueParam>> arguments) {
        this.arguments = arguments;
    }

    public Map<String, ArgoLifecycleHook> getHooks() { return hooks; }

    public void setHooks(Map<String, ArgoLifecycleHook> hooks) { this.hooks = hooks; }
}
