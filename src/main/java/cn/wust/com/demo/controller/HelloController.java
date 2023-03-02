package cn.wust.com.demo.controller;

import cn.wust.com.demo.pojo.*;
import cn.wust.com.demo.service.UserBehaviorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
public class HelloController {

    @Autowired
    private UserBehaviorService userBehaviorService;

    @RequestMapping("/index")
    public String sayHello(){
        return "index";
    }

    @RequestMapping("/reports")
    public String getReports(){
        return "reports";
    }

    @RequestMapping("/")
    public String getHit5(){
        return "hit5";
    }

    @RequestMapping("/habit")
    @ResponseBody
    public List<UserHabitPojo> getHabit() {
        List<UserHabitPojo> chartsList;
        chartsList = userBehaviorService.getList();
        return chartsList;
    }

    @RequestMapping("/hour")
    @ResponseBody
    public List<UserHourPojo> getHour() {
        List<UserHourPojo> chartsList;
        chartsList = userBehaviorService.getHourList();
        return chartsList;
    }

    @RequestMapping("/rate")
    @ResponseBody
    public List<ConvertRatePojo> getRate() {
        List<ConvertRatePojo> chartsList;
        chartsList = userBehaviorService.getRateList();
        return chartsList;
    }

    @RequestMapping("/userrate")
    @ResponseBody
    public List<UserRatePojo> getUserrate() {
        List<UserRatePojo> chartsList;
        chartsList = userBehaviorService.getUserrateList();
        System.out.println(chartsList);
        return chartsList;
    }

    @RequestMapping("/userflow")
    @ResponseBody
    public List<UserFlowPojo> getUserFlow() {
        List<UserFlowPojo> chartsList;
        chartsList = userBehaviorService.getUserFlowList();
        return chartsList;
    }

    @RequestMapping("/userhourflow")
    @ResponseBody
    public List<UserFlowPojo> getUserHourFlow() {
        List<UserFlowPojo> chartsList;
        chartsList = userBehaviorService.getUserHourFlowList();
        return chartsList;
    }

    @RequestMapping("/againrate")
    @ResponseBody
    public AgainRatePojo getAgainRate(Model model) {
        AgainRatePojo againrate = userBehaviorService.getAgainRate();
        return againrate;
    }

    @RequestMapping("/selltopcat")
    @ResponseBody
    public List<selltopcatPojo> getSellTopCat() {
        List<selltopcatPojo> sellTopcat = userBehaviorService.getSellTopcat();
        return sellTopcat;
    }

    @RequestMapping("/selltopitem")
    @ResponseBody
    public List<selltopitemPojo> getSellTopItem(String cat){
//        System.out.println(cat);
        List<selltopitemPojo> sellTopitem = userBehaviorService.getSellTopItem(cat);
        return sellTopitem;

    }

    @RequestMapping("/pvseveryhour")
    @ResponseBody
    public List<PvsEveryhour> getPvsEveryhour(){
        List<PvsEveryhour> chartsList = userBehaviorService.getPvsEveryhour();
        return chartsList;
    }

    @RequestMapping("/ipeveryhour")
    @ResponseBody
    public List<IpEveryhour> getIpEveryhour(){
        List<IpEveryhour> chartsList = userBehaviorService.getIpEveryhour();
        return chartsList;
    }

    @RequestMapping("/pvstotal")
    @ResponseBody
    public int getPvsTotal(){
        int total = userBehaviorService.getPvsTotal();
        return total;
    }

    @RequestMapping("/iptotal")
    @ResponseBody
    public int getIpTotal(){
        int total = userBehaviorService.getIpTotal();
        return total;
    }

    @RequestMapping("/avgpv")
    @ResponseBody
    public double getAvgPv(){
        double total = userBehaviorService.getAvgPv();
        return total;
    }

    @RequestMapping("/avgvisit")
    @ResponseBody
    public double getAvgVisit(){
        double total = userBehaviorService.getAvgVisit();
        return total;
    }

    @RequestMapping("/hotpages")
    @ResponseBody
    public List<HotPageTop10> getHotPages(){
        List<HotPageTop10> chartsList = userBehaviorService.getHotPages();
        return chartsList;
    }

    @RequestMapping("/returning")
    @ResponseBody
    public List<ReturningTop10> getReturning(){
        List<ReturningTop10> chartsList = userBehaviorService.getReturning();
        return chartsList;
    }




}
