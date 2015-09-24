package isa95.domain;

import java.util.Set;

/**
 * Created by user on 9/24/15.
 */
public class ExtendedPropertyRange extends AbstractExtendedProperty{

    private Set<ExtendedPropertyRangeDescriptor> valueRanges;

    public Set<ExtendedPropertyRangeDescriptor> getValueRanges() {
        return valueRanges;
    }

    public void setValueRanges(Set<ExtendedPropertyRangeDescriptor> valueRanges) {
        this.valueRanges = valueRanges;
    }
}
