package isa95.domain;

import java.util.Set;

/**
 * Created by user on 9/24/15.
 */
public class ExtendedPropertySet extends AbstractExtendedProperty{

    private Set<String> valueSet;

    public Set<String> getValueSet() {
        return valueSet;
    }

    public void setValueSet(Set<String> valueSet) {
        this.valueSet = valueSet;
    }
}
