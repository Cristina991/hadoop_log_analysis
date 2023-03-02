package cn.wust.com.demo.pojo;

import lombok.Data;

@Data
public class UserRatePojo {
    String userrate;
    int amount;

    @Override
    public String toString() {
        return "UserRatePojo{" +
                "userrate='" + userrate + '\'' +
                ", amount=" + amount +
                '}';
    }
}
