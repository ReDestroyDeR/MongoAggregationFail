package com.example.mongodbaggregationfailure.domain;

import com.example.mongodbaggregationfailure.enums.Status;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "models")
public class MyModel {
    @Id
    private String id;

    @Field(targetType = FieldType.OBJECT_ID)
    private String containerId;

    private Status status = Status.NOT_ACTIVE;

    public MyModel() {
    }

    public MyModel(String containerId, Status status) {
        this.containerId = containerId;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContainerId() {
        return containerId;
    }

    public void setContainerId(String containerId) {
        this.containerId = containerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyModel myModel = (MyModel) o;

        if (!id.equals(myModel.id)) return false;
        if (!containerId.equals(myModel.containerId)) return false;
        return status == myModel.status;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + containerId.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MyModel{" +
                "id='" + id + '\'' +
                ", containerId='" + containerId + '\'' +
                ", status=" + status +
                '}';
    }
}
