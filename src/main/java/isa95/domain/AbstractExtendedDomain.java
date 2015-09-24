package isa95.domain;

import java.util.Set;

/**
 * Created by user on 9/24/15.
 */
public class AbstractExtendedDomain {

    private Set<AbstractExtendedProperty> extendedProperties;

    public Set<AbstractExtendedProperty> getExtendedProperties() {
        return extendedProperties;
    }

    public void setExtendedProperties(Set<AbstractExtendedProperty> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }

}
