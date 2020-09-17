package com.quartztest.job;

import com.quartztest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * @Author zizi
 * @Version
 * @Description
 * @Date 2020/9/15 15:51
 */
@Slf4j
@DisallowConcurrentExecution
public class QuartzJob implements Job {
    @Autowired
    private UserService userService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println(new Date());
        this.userService.user();
        String batchId = jobExecutionContext.getJobDetail().getKey().getName();
        log.info("执行的任务id为：[{}]", batchId);
    }
}
