package cn.wust.com.demo.mapper;

import cn.wust.com.demo.pojo.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserBehaviorMapper  {
    @Select("SELECT date_ AS 'date',SUM(CASE WHEN behavior_type = 'pv' THEN 1 ELSE 0 END) AS 'pvsum'," +
            "SUM(CASE WHEN behavior_type = 'buy'THEN 1 ELSE 0 END) AS 'buysum'," +
            "SUM(CASE WHEN behavior_type = 'cart'THEN 1 ELSE 0 END) AS 'cartsum'," +
            "SUM(CASE WHEN behavior_type = 'fav'THEN 1 ELSE 0 END) AS 'favsum'" +
            "FROM userbehavior " +
            "GROUP BY `date_`;")
     List<UserHabitPojo> getList();

    @Select("SELECT hour_ AS HOUR,SUM(CASE behavior_type WHEN 'pv' THEN 1 ELSE 0 END)AS pvhour," +
            "SUM(CASE behavior_type WHEN 'cart' THEN 1 ELSE 0 END)AS carthour," +
            "SUM(CASE behavior_type WHEN 'fav' THEN 1 ELSE 0 END)AS favhour," +
            "SUM(CASE behavior_type WHEN 'buy' THEN 1 ELSE 0 END)AS buyhour " +
            "FROM userbehavior " +
            "GROUP BY hour_ " +
            "ORDER BY hour_;;")
    List<UserHourPojo> getHourList();

    @Select("SELECT behavior_type AS behaviortype,COUNT(behavior_type) AS behavioramount," +
            "CONCAT(ROUND(COUNT(behavior_type)/(SELECT COUNT(behavior_type) FROM userbehavior WHERE behavior_type='pv')*100,2)) AS rate " +
            "FROM userbehavior GROUP BY behavior_type ORDER BY COUNT(behavior_type) DESC;")
    List<ConvertRatePojo> getRateList();

    @Select("SELECT 用户分类 as userrate,用户数量 as amount " +
            "FROM(SELECT(CASE " +
            "WHEN R值打分>3.2521 AND F值打分>1.2896 THEN '重要价值用户' " +
            "WHEN R值打分>3.2521 AND F值打分<1.2896 THEN '重要发展用户' " +
            "WHEN R值打分<3.2521 AND F值打分>1.2896 THEN '重要保持用户' " +
            "WHEN R值打分<3.2521 AND F值打分<1.2896 THEN '重要挽留用户' " +
            "END)AS 用户分类," +
            "COUNT(用户ID)AS 用户数量 " +
            "FROM 价值打分 " +
            "GROUP BY 用户分类) AS 分类;")
    List<UserRatePojo> getUserrateList();

    @Select("SELECT date_ AS DATE,COUNT( DISTINCT user_id ) AS visitors,SUM( CASE WHEN behavior_type = 'pv' THEN 1 ELSE 0 END ) AS views,SUM( CASE WHEN behavior_type = 'pv' THEN 1 ELSE 0 END ) / COUNT( DISTINCT user_id ) AS avgviews FROM userbehavior GROUP BY date_")
    List<UserFlowPojo> getUserFlowList();

    @Select("SELECT hour_ As DATE,COUNT( DISTINCT user_id ) AS visitors,SUM( CASE WHEN behavior_type = 'pv' THEN 1 ELSE 0 END ) AS views,SUM( CASE WHEN behavior_type = 'pv' THEN 1 ELSE 0 END ) / COUNT( DISTINCT user_id ) AS avgviews FROM userbehavior GROUP BY hour_")
    List<UserFlowPojo> getUserHourFlowList();

    @Select("SELECT SUM( CASE WHEN 购买次数 > 1 THEN 1 ELSE 0 END ) AS again, COUNT( a.user_id ) AS buynum,SUM( CASE WHEN 购买次数 > 1 THEN 1 ELSE 0 END ) / COUNT( a.user_id ) AS againrate " + "FROM( SELECT user_id, COUNT( user_id ) AS 购买次数 FROM userbehavior WHERE behavior_type = 'buy' GROUP BY user_id ) a")
    AgainRatePojo getAgainRate();

    @Select("SELECT a.cat_id as catid,a.购买量 as buyamount FROM ( SELECT cat_id, SUM( CASE WHEN behavior_type = 'buy' THEN 1 ELSE 0 END ) AS 购买量 FROM userbehavior GROUP BY cat_id ) a ORDER BY 购买量 DESC LIMIT 5")
    List<selltopcatPojo> getSellTopcat();

    @Select("SELECT a.item_id AS itemid,a.购买量 AS buyamount FROM ( SELECT item_id, SUM( CASE WHEN behavior_type = 'buy' AND cat_id=#{arg0}  "+" THEN 1 ELSE 0 END ) AS 购买量 FROM userbehavior GROUP BY item_id ) a ORDER BY 购买量 DESC LIMIT 5")
    List<selltopitemPojo> getSellTopItem(String catid);

    @Select("SELECT * from dw_pvs_everyhour_oneday")
    List<PvsEveryhour> getPvsEveryhour();

    @Select("SELECT COUNT(1) AS dstcipcnts ,HOUR FROM dw_user_dstc_ip_h GROUP BY HOUR")
    List<IpEveryhour> getIpEveryhour();

    @Select("SELECT SUM(pvs) FROM dw_pvs_everyhour_oneday")
    int getPvsTotal();

    //人均页面浏览量
    @Select("select sum(b.pvs)/count(b.remote_addr) from" + "(select remote_addr,count(1) as pvs from ods_weblog_detail group by remote_addr) b;\n")
    int getavgpvuser();

    @Select("SELECT COUNT(1) FROM dw_user_dstc_ip_h")
    int getIpTotal();

    @Select("select * from dw_avgpv_user_everyday")
    double getAvgPv();

    @Select("select * from dw_avgvisit_user")
    double getAvgVisit();

    @Select("select * from dw_hotpages_everyday")
    List<HotPageTop10> getHotPages();

    @Select("select * from dw_user_returning")
    List<ReturningTop10> getReturning();




}
