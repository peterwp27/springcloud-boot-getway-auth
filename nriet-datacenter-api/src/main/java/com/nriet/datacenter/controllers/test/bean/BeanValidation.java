package com.nriet.datacenter.controllers.test.bean;
import javax.validation.constraints.AssertFalse;  
import javax.validation.constraints.Size;  
  
import org.hibernate.validator.constraints.Range;  
  
public class BeanValidation {  
    @Size(min=6,max=10)  
    private String field1;  
    @Range(min=0,max=1)  
    private Long field2;  
    @AssertFalse  
    private Boolean field3;  
    public String getField1() {  
        return field1;  
    }  
    public void setField1(String field1) {  
        this.field1 = field1;  
    }  
    public Long getField2() {  
        return field2;  
    }  
    public void setField2(Long field2) {  
        this.field2 = field2;  
    }  
    public Boolean getField3() {  
        return field3;  
    }  
    public void setField3(Boolean field3) {  
        this.field3 = field3;  
    }  
      
}  