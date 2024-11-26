package com.example.calamitycall.models.preference;

public class PreferenceUpdateRequest<T> {
    private String tableName;
    private T data;

    public PreferenceUpdateRequest(String tableName, T data) {
        this.tableName = tableName;
        this.data = data;
    }

    // setters
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setData(T data) {
        this.data = data;
    }



    // getters
    public String getTableName() {
        return tableName;
    }

    public T getData() {
        return data;
    }
}
