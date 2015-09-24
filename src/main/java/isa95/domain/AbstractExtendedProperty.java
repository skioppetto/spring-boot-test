package isa95.domain;

/**
 * Created by user on 9/24/15.
 */
public abstract class AbstractExtendedProperty {

    private String Name;
    private String Description;
    private String ValueUnitOfMeasure;
    private String Type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getValueUnitOfMeasure() {
        return ValueUnitOfMeasure;
    }

    public void setValueUnitOfMeasure(String valueUnitOfMeasure) {
        ValueUnitOfMeasure = valueUnitOfMeasure;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }
}
