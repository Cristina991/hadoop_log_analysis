package cn.wust.com.demo.mrbean;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class UserBehaviorBean implements Writable {
    private String user_id;
    private String item_id;
    private String cat_id;
    private String behavior_type;
    private String time_stamp;
    private String time;
    private String date;
    private String hour;

    public void set(String user_id,String item_id,String cat_id,String behavior_type,String time_stamp,String time,String date,String hour){
        this.user_id=user_id;
        this.item_id  = item_id;
        this.cat_id = cat_id;
        this.behavior_type = behavior_type;
        this.time_stamp = time_stamp;
        this.time = time;
        this.date = date;
        this.hour = hour;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getBehavior_type() {
        return behavior_type;
    }

    public void setBehavior_type(String behavior_type) {
        this.behavior_type = behavior_type;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return  user_id +
                "," + item_id +
                ","  + cat_id +
                ","  + behavior_type  +
                ","  + time_stamp +
                ","  + time +
                ","  + date +
                ","  + hour;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(user_id);
        out.writeUTF(item_id);
        out.writeUTF(cat_id);
        out.writeUTF(behavior_type);
        out.writeUTF(time_stamp);
        out.writeUTF(time);
        out.writeUTF(date);
        out.writeUTF(hour);

    }

    @Override
    public void readFields(DataInput in) throws IOException {
        user_id = in.readUTF();
        item_id = in.readUTF();
        cat_id = in.readUTF();
        behavior_type = in.readUTF();
        time_stamp = in.readUTF();
        time= in.readUTF();
        date = in.readUTF();
        hour = in.readUTF();

    }

}
