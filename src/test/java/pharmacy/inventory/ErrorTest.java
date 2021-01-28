package pharmacy.inventory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class ErrorTest implements Errors {
    private String objectname;
    private ArrayList<String> nestedPath=new ArrayList<>();
    @Override
    public String getObjectName() {
        return this.objectname;
    }

    @Override
    public void setNestedPath(String nestedPath) {
        this.nestedPath.add(nestedPath);

    }

    @Override
    public String getNestedPath() {
        
        return String.join(", ", this.nestedPath);
    }

    @Override
    public void pushNestedPath(String subPath) {
        

    }

    @Override
    public void popNestedPath() throws IllegalStateException {
      

    }

    @Override
    public void reject(String errorCode) {

    }

    @Override
    public void reject(String errorCode, String defaultMessage) {

    }

    @Override
    public void reject(String errorCode, Object[] errorArgs, String defaultMessage) {

    }

    @Override
    public void rejectValue(String field, String errorCode) {

    }

    @Override
    public void rejectValue(String field, String errorCode, String defaultMessage) {

    }

    @Override
    public void rejectValue(String field, String errorCode, Object[] errorArgs, String defaultMessage) {

    }

    @Override
    public void addAllErrors(Errors errors) {

    }

    @Override
    public boolean hasErrors() {
        return false;
    }

    @Override
    public int getErrorCount() {
        return 0;
    }

    @Override
    public List<ObjectError> getAllErrors() {
        return null;
    }

    @Override
    public boolean hasGlobalErrors() {
        return false;
    }

    @Override
    public int getGlobalErrorCount() {
        return 0;
    }

    @Override
    public List<ObjectError> getGlobalErrors() {
        List<ObjectError> ret = new ArrayList<ObjectError>();
        return ret;
    }

    @Override
    public ObjectError getGlobalError() {
        
        return null;
    }

    @Override
    public boolean hasFieldErrors() {
        return false;
    }

    @Override
    public int getFieldErrorCount() {
        return 0;
    }

    @Override
    public List<FieldError> getFieldErrors() {
        List<FieldError> ret = new ArrayList<FieldError>();
        return ret;
    }

    @Override
    public FieldError getFieldError() {
    
        return null;
    }

    @Override
    public boolean hasFieldErrors(String field) {
        
        return false;
    }

    @Override
    public int getFieldErrorCount(String field) {
        return 0;
    }

    @Override
    public List<FieldError> getFieldErrors(String field) {
        List<FieldError> ret = new ArrayList<FieldError>();
        return ret;
    }

    @Override
    public FieldError getFieldError(String field) {
        
        return null;
    }

    @Override
    public Object getFieldValue(String field) {
        
        return null;
    }

    @Override
    public Class<?> getFieldType(String field) {
        
        return null;
    }
} 