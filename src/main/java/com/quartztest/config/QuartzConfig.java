package com.quartztest.config;

import com.quartztest.job.QuartzJob;
import com.quartztest.utils.MyAdaptableJobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author zizi
 * @Version
 * @Description
 * @Date 2020/9/15 10:35
 */
@Configuration
public class QuartzConfig {
    /**
     * 创建job对象
     */
    @Bean
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        //关联我们自己的job类
        factoryBean.setJobClass(QuartzJob.class);
        factoryBean.setGroup("jobGroup");
        factoryBean.setName("jobName");
        return factoryBean;
    }


    //指定quartz.properties
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        //填写刚刚创建的配置文件名
        propertiesFactoryBean.setLocation(new ClassPathResource("quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * Cron trigger
     */
    @Bean
    public CronTriggerFactoryBean cronTriggerFactoryBean(JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
        cronTriggerFactoryBean.setJobDetail(jobDetailFactoryBean.getObject());
        cronTriggerFactoryBean.setGroup("tiggergroup");
        cronTriggerFactoryBean.setName("tigger");
        //设置触发时间
        cronTriggerFactoryBean.setCronExpression("0/2 * * * * ?");
        return cronTriggerFactoryBean;
    }

    /**
     * 创建Schedule对象
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(CronTriggerFactoryBean cronTriggerFactoryBean, MyAdaptableJobFactory myAdaptableJobFactory) throws IOException {
        // 1、创建调度器Scheduler
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        //读取配置文件
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        //关联trigger
        schedulerFactoryBean.setTriggers(cronTriggerFactoryBean.getObject());
        schedulerFactoryBean.setJobFactory(myAdaptableJobFactory);
        return schedulerFactoryBean;
    }
}