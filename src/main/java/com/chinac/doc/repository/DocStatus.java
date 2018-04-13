package com.chinac.doc.repository;

public enum DocStatus {
    Published(10), Uploaded(20);

    private final int value;

    DocStatus(int value) {

        this.value = value;
    }

    public static DocStatus parse(int value) {
        DocStatus ret = Uploaded;
        switch (value) {
            case 10:
                ret = Published;
                break;
            case 20:
                ret = Uploaded;
                break;
        }
        return ret;
    }

    public int getValue() {
        return value;
    }
}
