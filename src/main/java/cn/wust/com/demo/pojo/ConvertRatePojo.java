package cn.wust.com.demo.pojo;

import lombok.Data;

@Data
public class ConvertRatePojo {
    String behaviortype;
    int behavioramount;
    String rate;

    @Override
    public String toString() {
        return "ConvertRatePojo{" +
                "behaviortype='" + behaviortype + '\'' +
                ", behavioramount=" + behavioramount +
                ", rate='" + rate + '\'' +
                '}';
    }
}
