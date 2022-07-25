package com.example.mongodbaggregationfailure.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "containers")
public class MyModelContainer {
    @Id
    private String id;

    private String groupId;

    private Integer counter;

    public MyModelContainer() {
    }

    public MyModelContainer(String groupId) {
        this.groupId = groupId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MyModelContainer that = (MyModelContainer) o;

        if (!id.equals(that.id)) return false;
        return groupId.equals(that.groupId);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + groupId.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MyModelContainer{" +
                "id='" + id + '\'' +
                ", groupId='" + groupId + '\'' +
                ", counter=" + counter +
                '}';
    }
}
