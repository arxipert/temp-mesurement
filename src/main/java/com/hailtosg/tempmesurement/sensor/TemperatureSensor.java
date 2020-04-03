package com.hailtosg.tempmesurement.sensor;

import com.hailtosg.tempmesurement.model.Temperature;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class TemperatureSensor {
    private final ApplicationEventPublisher publisher;
    private final Random random = new Random();
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    @PostConstruct
    public void startProcessing() {
        this.executorService.schedule(this::probe, 1, TimeUnit.SECONDS);
    }

    private void probe() {
        double temperature = 16+ random.nextGaussian() *10;
        publisher.publishEvent(new Temperature(temperature));
        executorService.schedule(this::probe, random.nextInt(5000), TimeUnit.MILLISECONDS);
    }
}
