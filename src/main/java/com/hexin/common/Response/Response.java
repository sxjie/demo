package com.hexin.common.Response;

public class Response {
    public static final int STATUS_SUCCESS = 0;
    public static final int STATUS_KNOWN_ERROR = 1;
    public static final int STATUS_UNKNOWN_ERROR = -1;

    private int status;
    private String msg = "";
    private Object result;

    public Response() {
        this.status = STATUS_SUCCESS;
        this.msg = "执行成功";
    }

    public Response(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Response withResult(Object result) {
        this.setResult(result);
        return this;
    }


    // get / set
    public int getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
