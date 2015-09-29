package isa95.domain;

/**
 * Created by user on 9/24/15.
 */
public abstract class AbstractExtendedProperty {

    private String Name;
    private String Description;
    private String ValueUnitOfMeasure;
    private ExtendedPropertyTypeEnum Type;
    private ExtendedPropertyFormatEnum format;

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

    public ExtendedPropertyTypeEnum getType() {
        return Type;
    }

    public void setType(ExtendedPropertyTypeEnum type) {
        Type = type;
    }

    public ExtendedPropertyFormatEnum getFormat() {
        return format;
    }

    public void setFormat(ExtendedPropertyFormatEnum format) {
        this.format = format;
    }
}
