package kr.lesaautomaton.utils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.ValidatorContext;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class ByteLengthVaildator extends FieldValidatorSupport {
	
	private int maxBytes;
	private int minBytes;

	@Override
	public void validate(Object object) throws ValidationException {
		// TODO Auto-generated method stub
		
		ValidatorContext context = getValidatorContext();
		
		
		

	}

	public int getMaxBytes() {
		return maxBytes;
	}

	public void setMaxBytes(int maxBytes) {
		this.maxBytes = maxBytes;
	}

	public int getMinBytes() {
		return minBytes;
	}

	public void setMinBytes(int minBytes) {
		this.minBytes = minBytes;
	}	

}
