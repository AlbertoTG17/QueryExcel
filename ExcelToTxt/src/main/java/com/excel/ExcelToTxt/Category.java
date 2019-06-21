package com.excel.ExcelToTxt;

public class Category {
    
    private String idCategory;
    private String parent;
    private String description;
    
    public String getIdCategory() {
        return idCategory;
    }
    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }
    public String getParent() {
        return parent;
    }
    public void setParent(String parent) {
        this.parent = parent;
    }
    public String getDescription() {
        return description;
    }
   
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Category(String idCategory, String parent, String description) {
        super();
        this.idCategory = idCategory;
        this.parent = parent;
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Category [idCategory=" + idCategory + ", parent=" + parent + ", description=" + description + "]";
    }
    
    
    
    

}
