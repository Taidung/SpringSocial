package vn.taidung.springsocial.model.response;

public class RestResponse<T> {
    private int statusCode;
    private ErrorResponse errors;

    private Object message;
    private T data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getErrors() {
        return errors;
    }

    public void setErrors(ErrorResponse errors) {
        this.errors = errors;
    }

}
