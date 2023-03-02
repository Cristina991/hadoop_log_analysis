package cn.wust.com.demo.service;

import cn.wust.com.demo.mapper.UserBehaviorMapper;
import cn.wust.com.demo.pojo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBehaviorServiceImpl  implements UserBehaviorService {
    @Autowired
   private UserBehaviorMapper userBehaviorMapper;

    @Override
    public List<UserHabitPojo> getList() {
        return userBehaviorMapper.getList();
    }

    @Override
    public List<UserHourPojo> getHourList() {
        return userBehaviorMapper.getHourList();
    }

    @Override
    public List<ConvertRatePojo> getRateList() {
        return userBehaviorMapper.getRateList();
    }

    @Override
    public List<UserRatePojo> getUserrateList() {
        return userBehaviorMapper.getUserrateList();
    }

    @Override
    public List<UserFlowPojo> getUserFlowList() {
        return userBehaviorMapper.getUserFlowList();
    }

    @Override
    public List<UserFlowPojo> getUserHourFlowList() {
        return userBehaviorMapper.getUserHourFlowList();
    }

    @Override
    public AgainRatePojo getAgainRate() {
        return userBehaviorMapper.getAgainRate();
    }

    @Override
    public List<selltopcatPojo> getSellTopcat() {
        return userBehaviorMapper.getSellTopcat();
    }

    @Override
    public List<selltopitemPojo> getSellTopItem(String cat) {
        return userBehaviorMapper.getSellTopItem(cat);
    }

    @Override
    public List<PvsEveryhour> getPvsEveryhour() {
        return userBehaviorMapper.getPvsEveryhour();
    }

    @Override
    public List<IpEveryhour> getIpEveryhour() {
        return userBehaviorMapper.getIpEveryhour();
    }

    @Override
    public List<HotPageTop10> getHotPages() {
        return userBehaviorMapper.getHotPages();
    }

    @Override
    public List<ReturningTop10> getReturning() {
        return userBehaviorMapper.getReturning();
    }

    @Override
    public int getPvsTotal() {
        return userBehaviorMapper.getPvsTotal();
    }

    @Override
    public int getIpTotal() {
        return userBehaviorMapper.getIpTotal();
    }

    @Override
    public double getAvgPv() {
        return userBehaviorMapper.getAvgPv();
    }

    @Override
    public double getAvgVisit() {
        return userBehaviorMapper.getAvgVisit();
    }


}
