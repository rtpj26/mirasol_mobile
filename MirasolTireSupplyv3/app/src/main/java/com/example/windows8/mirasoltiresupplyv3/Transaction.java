package com.example.windows8.mirasoltiresupplyv3;

/**
 * Created by WINDOWS 8 on 9/29/2016.
 */

public class Transaction {
    public String getMode() {
        return mode;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    private String mode, timestamp, status;

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Transaction(String mode, String timestamp, String status) {
        this.mode = mode;
        this.timestamp = timestamp;
        this.status = status;
    }


}
