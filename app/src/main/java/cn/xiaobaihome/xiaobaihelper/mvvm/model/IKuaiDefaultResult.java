package cn.xiaobaihome.xiaobaihelper.mvvm.model;

public class IKuaiDefaultResult<T> {

    private int Result;
    private String ErrMsg;
    private T Data;

    public IKuaiDefaultResult() {
    }

    public IKuaiDefaultResult(int Result, String ErrMsg, T Data) {
        this.Result = Result;
        this.ErrMsg = ErrMsg;
        this.Data = Data;
    }

    public int getResult() {
        return Result;
    }

    public void setResult(int result) {
        Result = result;
    }

    public String getErrMsg() {
        return ErrMsg;
    }

    public void setErrMsg(String errMsg) {
        ErrMsg = errMsg;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }
}
