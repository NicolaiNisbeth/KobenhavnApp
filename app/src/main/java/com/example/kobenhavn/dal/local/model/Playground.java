package com.example.kobenhavn.dal.local.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Immutable POJO that represents a playground
 */
@Entity(tableName = "playground_table")
public class Playground implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @SerializedName("body")
    @ColumnInfo(name = "comment_text")
    private String commentText;

    @ColumnInfo(name = "sync_pending")
    private boolean syncPending;

    public long getId() {
        return id;
    }

    public String getCommentText() {
        return commentText;
    }

    public boolean isSyncPending() {
        return syncPending;
    }

    @Ignore
    public Playground(String commentText){
        this.commentText = commentText;
        this.syncPending = true;
    }

    public Playground(long id, String commentText, boolean syncPending) {
        this.id = id;
        this.commentText = commentText;
        this.syncPending = syncPending;
    }

    @Override
    public String toString() {
        return "Playground{" +
                "id=" + id +
                ", commentText='" + commentText + '\'' +
                ", syncPending=" + syncPending +
                '}';
    }
}
