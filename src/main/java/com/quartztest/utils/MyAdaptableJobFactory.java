package com.quartztest.utils;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * @Author zizi
 * @Version
 * @Description
 * @Date 2020/9/15 16:47
 */
@Component("myAdaptableJobFactory")
public class MyAdaptableJobFactory extends AdaptableJobFactory {
    //AutowireCapableBeanFactory 可以将一个对象添加到springIOC容器里面，并且完成该对象的注入
    @Autowired
    private AutowireCapableBeanFactory autowireCapableBeanFactory;

    //该方法需要手动将实例化的任务对象添加到springIOC容器中并且完成对象的注入
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        Object jobInstance = super.createJobInstance(bundle);
        //将jobInstance添加到springIOC容器中完成注入
        this.autowireCapableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}
