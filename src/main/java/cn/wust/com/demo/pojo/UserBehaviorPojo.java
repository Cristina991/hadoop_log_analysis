package cn.wust.com.demo.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.security.Timestamp;

@Data
@TableName("userbehavior")
public class UserBehaviorPojo implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId
    private int user_id;

    private int item_id;

    private int cat_id;

    private String behavior_type;

    private int time_stamp;

    private Timestamp time_;

    private String date_;

    private String hour_;

    @Override
    public String toString() {
        return "UserBehaviorPojo{" +
                "user_id=" + user_id +
                ", item_id=" + item_id +
                ", cat_id=" + cat_id +
                ", behavior_type='" + behavior_type + '\'' +
                ", time_stamp=" + time_stamp +
                ", time_=" + time_ +
                ", date_='" + date_ + '\'' +
                ", hour_='" + hour_ + '\'' +
                '}';
    }
}
