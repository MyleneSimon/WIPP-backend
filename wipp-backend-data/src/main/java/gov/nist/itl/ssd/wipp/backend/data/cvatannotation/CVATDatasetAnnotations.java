package gov.nist.itl.ssd.wipp.backend.data.cvatannotation;

import gov.nist.itl.ssd.wipp.backend.core.rest.annotation.IdExposed;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@IdExposed
@Document("CVATDatasetAnnotations")
public class CVATDatasetAnnotations {

    @Id
    private String id;

    @Indexed
    private String imagesCollection;

    private String project_id;

//    @Field("task_id")
    private String task_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagesCollection() {
        return imagesCollection;
    }

    public void setImagesCollection(String imagesCollection) {
        this.imagesCollection = imagesCollection;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }
}
