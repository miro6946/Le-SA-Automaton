package kr.lesaautomaton.utils;

import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;

public class ByteSizeFieldValidator extends FieldValidatorSupport {

    private boolean doTrim = true;
    private Byte maxLength = -1;
    private Byte minLength = -1;
 
    public void setMaxLength(Byte maxLength) {
        this.maxLength = maxLength;
    }
 
    public int getMaxLength() {
        return maxLength;
    }
 
    public void setMinLength(Byte minLength) {
        this.minLength = minLength;
    }
 
    public int getMinLength() {
        return minLength;
    }
 
    public void setTrim(boolean trim) {
        doTrim = trim;
    }
 
    public boolean getTrim() {
        return doTrim;
    }
 
    public void validate(Object object) throws ValidationException {
        String fieldName = getFieldName();
        String val = (String) getFieldValue(fieldName, object);
 
        if (val == null || val.getBytes().length <= 0) {
            // use a required validator for these
            return;
        }
        
        if (doTrim) {
            val = val.trim();
            if (val.getBytes().length <= 0) {
                // use a required validator
                return;
            }
        }
 
        if ((minLength > -1) && (val.getBytes().length < minLength)) {
            addFieldError(fieldName, object);
        } else if ((maxLength > -1) && (val.getBytes().length > maxLength)) {
            addFieldError(fieldName, object);
        }
    }

}
