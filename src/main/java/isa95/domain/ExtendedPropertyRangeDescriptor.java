package isa95.domain;

/**
* Created by user on 9/24/15.
*/
public class ExtendedPropertyRangeDescriptor {
    private String valueMin;
    private String valueMax;

    ExtendedPropertyRangeDescriptor(String valueMin, String valueMax) {
        this.valueMin = valueMin;
        this.valueMax = valueMax;
    }

    public String getValueMin() {
        return valueMin;
    }

    public void setValueMin(String valueMin) {
        this.valueMin = valueMin;
    }

    public String getValueMax() {
        return valueMax;
    }

    public void setValueMax(String valueMax) {
        this.valueMax = valueMax;
    }
}
