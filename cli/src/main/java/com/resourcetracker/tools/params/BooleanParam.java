package com.resourcetracker.tools.params;

import java.util.ArrayList;

public class BooleanParam implements Param<Boolean>{
    private Boolean value;
	private ArrayList<String> positionalArgs = new ArrayList<String>();
	private boolean withPositionalArgs = false;

    public BooleanParam(Boolean value){
        this.value = value;
    }

    @Override
    public void setValue(Boolean value){
        this.value = value;
    }

	@Override
	public void setPositionalArg(String posArg){
		this.positionalArgs.add(posArg);
	}

	@Override
	public ArrayList<String> getPositionalArgs(){
		return this.positionalArgs;
	}

    @Override
    public Boolean getValue(){
        return this.value;
    }

	@Override
	public boolean isWithPositionalArgs(){
		return withPositionalArgs;
	}

	@Override
	public BooleanParam withPositionalArgs(){
		withPositionalArgs = true;
		return this;
	}
}
