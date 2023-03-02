package cn.wust.com.demo.pojo;

public class Result {
    /**
     * 返回状态码 0成功 1失败
     */
    int code;
    /**
     * 返回信息说明
     */
    String msg;
    /**
     * 返回数据量
     */
    int count;
    /**
     * 返回数据
     */
    Object data;

    Result() {

    }

    Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    Result(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    Result(int code, String msg, int count, Object data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
