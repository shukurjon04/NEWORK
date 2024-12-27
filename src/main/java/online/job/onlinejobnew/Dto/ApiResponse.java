package online.job.onlinejobnew.Dto;

public class ApiResponse {
    private int status;
    private String message;
    private boolean success;
    private Object data;

    public ApiResponse() {
    }

    public ApiResponse(int status, String message, boolean success, Object data) {
        this.status = status;
        this.message = message;
        this.success = success;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
