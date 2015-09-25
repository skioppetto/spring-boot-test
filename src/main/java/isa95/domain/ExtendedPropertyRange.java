package isa95.domain;

import java.util.List;

/**
 * Created by user on 9/24/15.
 */
public class ExtendedPropertyRange extends AbstractExtendedProperty{

    private List<ExtendedPropertyRangeDescriptor> valueRanges;

    public List<ExtendedPropertyRangeDescriptor> getValueRanges() {
        return valueRanges;
    }

    public void setValueRanges(List<ExtendedPropertyRangeDescriptor> valueRanges) {
        this.valueRanges = valueRanges;
    }
}
