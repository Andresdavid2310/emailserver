package com.app.emailserver.domain.usecase.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Objects;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Value("${email.scheduled_hour_cron}")
    public String scheduledHourCron;

    @Autowired
    private EmailSchedulerService emailSchedulerService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(
                () -> emailSchedulerService.markEmailsAsSpam(),
                triggerContext -> Objects.requireNonNull(new CronTrigger(scheduledHourCron).nextExecution(triggerContext))
        );
    }
}
