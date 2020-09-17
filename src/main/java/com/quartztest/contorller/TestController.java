package com.quartztest.contorller;

import com.quartztest.constant.GloabalConstant;
import com.quartztest.job.QuartzJob;
import com.quartztest.model.QuartzJobModule;
import com.quartztest.utils.CronUtil;
import com.quartztest.utils.QuartzJobComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Result;
import java.util.Calendar;
import java.util.Date;

/**
 * @Author zizi
 * @Version
 * @Description
 * @Date 2020/9/16 15:08
 */
@RestController
@RequestMapping("/job")
public class TestController {
    private final static Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private QuartzJobComponent quartzJobComponent;

    @PostMapping("/add")
    @ResponseBody
    public String  save() {
        LOGGER.info("新增任务");
        try {
            QuartzJobModule quartzJobModule = new QuartzJobModule();
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.YEAR, 2020);
            cal.set(Calendar.MONTH, 8);
            cal.set(Calendar.DATE, 9);
            cal.set(Calendar.HOUR_OF_DAY, 12);
            cal.set(Calendar.MINUTE, 30);
            cal.set(Calendar.SECOND, 00);
            Date startDate = cal.getTime();// 任务开始日期为2020年9月9日12点30分

            Calendar endCal = Calendar.getInstance();
            endCal.set(Calendar.YEAR, 2020);
            endCal.set(Calendar.MONTH, 8);
            endCal.set(Calendar.DATE, 12);
            endCal.set(Calendar.HOUR_OF_DAY, 12);
            endCal.set(Calendar.MINUTE, 30);
            endCal.set(Calendar.SECOND, 00);
            Date endDate = endCal.getTime();// 任务结束日期为2020年9月12日12点30分

            quartzJobModule.setStartTime(CronUtil.getStartDate(startDate));
            quartzJobModule.setEndTime(CronUtil.getEndDate(endDate));
            // 注意：在后面的任务中需要通过这个JobName来获取你要处理的数据，因此您可以讲这个设置为你要处理的数据的主键，比如id
            quartzJobModule.setJobName("testJobId");
            quartzJobModule.setTriggerName("tesTriggerNmae");
            quartzJobModule.setJobGroupName(GloabalConstant.QZ_JOB_GROUP_NAME);
            quartzJobModule.setTriggerGroupName(GloabalConstant.QZ_TRIGGER_GROUP_NAME);

            String weeks = "1,2,3,5";// 该处模拟每周1,2,3,5执行任务
            String cronExpression = CronUtil
                    .convertCronExpression(startDate,
                            endDate, weeks.split(","));
            quartzJobModule.setCron(cronExpression);
            quartzJobModule.setJobClass(QuartzJob.class);
            quartzJobComponent.addJob(quartzJobModule);
        }
        catch (Exception e) {
            e.printStackTrace();
            return "ok";
        }
        return "ok";
    }

    @PostMapping("/edit")
    @ResponseBody
    public String edit() {
        LOGGER.info("编辑任务");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2020);
        cal.set(Calendar.MONTH, 8);
        cal.set(Calendar.DATE, 12);
        cal.set(Calendar.HOUR_OF_DAY, 12);
        cal.set(Calendar.MINUTE, 30);
        cal.set(Calendar.SECOND, 00);
        Date startDate = cal.getTime();

        Calendar endCal = Calendar.getInstance();
        endCal.set(Calendar.YEAR, 2020);
        endCal.set(Calendar.MONTH, 8);
        endCal.set(Calendar.DATE, 24);
        endCal.set(Calendar.HOUR_OF_DAY, 12);
        endCal.set(Calendar.MINUTE, 30);
        endCal.set(Calendar.SECOND, 00);
        Date endDate = endCal.getTime();
        // "testJobId"为add方法添加的job的name
        quartzJobComponent.modifyJobTime("testJobId", "/10 *  * ? * *", startDate, endDate);
        return "ok";
    }

    @PostMapping("/pause")
    @ResponseBody
    public String  pause(String jobName, String jobGroup) {
        LOGGER.info("停止任务");
        quartzJobComponent.pauseJob("testJobId");
        return"ok";
    }

    @PostMapping("/resume")
    @ResponseBody
    public String  resume(String jobName, String jobGroup) {
        LOGGER.info("恢复任务");
        quartzJobComponent.removeJob("testJobId");
        return "ok";

    }

    @PostMapping("/remove")
    @ResponseBody
    public String  remove(String jobName, String jobGroup) {
        LOGGER.info("移除任务");
        quartzJobComponent.removeJob("testJobId");
        return "ok";
    }
}
