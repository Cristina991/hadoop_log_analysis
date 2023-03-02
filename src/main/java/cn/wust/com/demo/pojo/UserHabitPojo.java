package cn.wust.com.demo.pojo;

import lombok.Data;

@Data
public class UserHabitPojo {

    String date;
    int pvsum;
    int buysum;
    int cartsum;
    int favsum;

    @Override
    public String toString() {
        return "UserHabitPojo{" +
                "data='" + date + '\'' +
                ", pvsum=" + pvsum +
                ", buysum=" + buysum +
                ", cartsum=" + cartsum +
                ", favsum=" + favsum +
                '}';
    }
}
