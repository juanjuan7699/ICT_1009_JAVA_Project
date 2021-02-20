package com.ict1009.ahg.interfaces;

import com.ict1009.ahg.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public interface IStatus { //this is for status effects

    List<StatusType> statuses = new ArrayList<>();

    void addStatus();
    void removeStatus();
    void removeAllStatus();
    boolean hasStatus(StatusType status);

    default List<StatusType> getStatuses() {
        return statuses;
    }
}
