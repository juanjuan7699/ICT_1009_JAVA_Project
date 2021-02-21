package com.ict1009.ahg.interfaces;

import com.ict1009.ahg.enums.StatusType;

import java.util.ArrayList;
import java.util.List;

public interface IStatus { //this is for status effects

    void addStatus(StatusType status);
    void removeStatus(StatusType status);
    void removeStatus(int index);
    void removeAllStatus();
    boolean hasStatus(StatusType status);

    List<StatusType> getStatuses();
}
