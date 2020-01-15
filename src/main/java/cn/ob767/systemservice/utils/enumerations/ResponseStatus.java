package cn.ob767.systemservice.utils.enumerations;

public enum ResponseStatus {

    SUCCESS(200, "1", "Success"),
    FAILED(200, "1", "Failed"),
    BAD(400, "-1", "Bad Request"),
    ERROR(500, "-99", "Internal Server Error"),
    ;

    private int code;
    private String ret;
    private String message;


    public int getCode() {
        return code;
    }

    public String getRet() {
        return ret;
    }

    public String getMessage() {
        return message;
    }

    ResponseStatus(int code, String ret, String message) {
        this.code = code;
        this.ret = ret;
        this.message = message;
    }
}