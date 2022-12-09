package com.scoutingthestatline;

import java.util.Comparator;

public class IntegerComparator implements Comparator{

    @Override
    public int compare(Object o1, Object o2) {
        int i1 = Integer.parseInt((String)o1);
        int i2 = Integer.parseInt((String)o2);
        return i1 - i2;
    }
    
}
