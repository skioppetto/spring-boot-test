package isa95.domain;

import java.util.Set;

/**
 * Created by user on 9/24/15.
 */
public class ProductDefinition extends AbstractExtendedDomain{

    private String ProductDefinitionID;
    private String Version;
    private String PublishedDate;
    private String HierarchyScope;
    private String BillOfMaterialID;
    private String BillOfResourcesID;

    /*private String Description;
    private String Location;
    private String ProductProductionRule;
  private String ManufacturingBill;
    private String ProductSegment;*/


    private Set<AbstractExtendedProperty> extendedProperties;

    public String getProductDefinitionID() {
        return ProductDefinitionID;
    }

    public void setProductDefinitionID(String productDefinitionID) {
        ProductDefinitionID = productDefinitionID;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getPublishedDate() {
        return PublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        PublishedDate = publishedDate;
    }

    public String getHierarchyScope() {
        return HierarchyScope;
    }

    public void setHierarchyScope(String hierarchyScope) {
        HierarchyScope = hierarchyScope;
    }

    public String getBillOfMaterialID() {
        return BillOfMaterialID;
    }

    public void setBillOfMaterialID(String billOfMaterialID) {
        BillOfMaterialID = billOfMaterialID;
    }

    public String getBillOfResourcesID() {
        return BillOfResourcesID;
    }

    public void setBillOfResourcesID(String billOfResourcesID) {
        BillOfResourcesID = billOfResourcesID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductDefinition that = (ProductDefinition) o;

        if (!ProductDefinitionID.equals(that.ProductDefinitionID)) return false;
        if (Version != null ? !Version.equals(that.Version) : that.Version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ProductDefinitionID.hashCode();
        result = 31 * result + (Version != null ? Version.hashCode() : 0);
        return result;
    }

    @Override
    public Set<AbstractExtendedProperty> getExtendedProperties() {
        return extendedProperties;
    }

    @Override
    public void setExtendedProperties(Set<AbstractExtendedProperty> extendedProperties) {
        this.extendedProperties = extendedProperties;
    }
}
