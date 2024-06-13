package com.app.emailserver.domain.usecase.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Value("${email.scheduled_hour_cron}")
    private String scheduledHourCron;

    @Autowired
    private EmailSchedulerService emailSchedulerService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> emailSchedulerService.markEmailsAsSpam(),
                triggerContext -> new CronTrigger(scheduledHourCron).nextExecutionTime(triggerContext).toInstant()
        );
    }
}
