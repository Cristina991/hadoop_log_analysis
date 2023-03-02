package cn.wust.com.demo.pojo;

import java.util.List;

public class FlowReturnPojo {
    List<String> dates;
    List<String> new_uvs;

    @Override
    public String toString() {
        return "FlowReturnPojo{" +
                "dates=" + dates +
                ", new_uvs=" + new_uvs +
                '}';
    }
}
