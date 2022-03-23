package com.resourcetracker.tools.params;

public class BooleanParam implements Param<Boolean>{
    private Boolean value;
    public BooleanParam(Boolean value){
        this.value = value;
    }

    @Override
    public void setValue(Boolean value){
        this.value = value;
    }

    @Override
    public Boolean getValue(){
        return this.value;
    }
}
