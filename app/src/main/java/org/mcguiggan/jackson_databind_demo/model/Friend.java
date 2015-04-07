package org.mcguiggan.jackson_databind_demo.model;

/**
 * A friend from the social data.
 */
public class Friend {

    private int mId;
    private String mName;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }
}
