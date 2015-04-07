package org.mcguiggan.jackson_databind_demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A friend from the social data.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Friend {

    private int mId;
    private String mName;

    @JsonProperty("id")
    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    @JsonProperty("name")
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
