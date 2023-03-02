package cn.wust.com.demo.service;

import cn.wust.com.demo.pojo.*;

import java.util.List;

public interface  UserBehaviorService  {
     List<UserHabitPojo> getList();

     List<UserHourPojo> getHourList();

     List<ConvertRatePojo> getRateList();

     List<UserRatePojo> getUserrateList();

     List<UserFlowPojo> getUserFlowList();

     List<UserFlowPojo> getUserHourFlowList();
     AgainRatePojo getAgainRate();
     List<selltopcatPojo> getSellTopcat();
     List<selltopitemPojo> getSellTopItem(String cat);
     List<PvsEveryhour> getPvsEveryhour();
     List<IpEveryhour> getIpEveryhour();
     List<HotPageTop10> getHotPages();
     List<ReturningTop10> getReturning();

    int getPvsTotal();
    int getIpTotal();
    double getAvgPv();
    double getAvgVisit();
}
