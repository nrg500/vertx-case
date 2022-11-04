package com.openvalue.exercise;

import java.time.Duration;
import java.util.Random;
import java.util.logging.Logger;

public class ChipMachine {

  Logger log = Logger.getLogger(ChipMachine.class.getName());
  private int temperature = 40;
  private int smokePPM = 0;
  private boolean running = false;

  private boolean ventilation = false;

  private Duration runningFor = Duration.ZERO;
  Random rand = new Random();

  public int getTemperatureMeasurement() {
    return temperature;
  }

  public int getSmokeMeasurement() {
    return smokePPM;
  }

  public void tick() {
    log.info(() -> "tick temperature: " + getTemperatureMeasurement() + ", smoke: " + getSmokeMeasurement());
    if(running) {
      if(temperature > 80) {
        throw new IllegalStateException("Machine is too hot! Turn on the ventilation!");
      }
      if(smokePPM > 500) {
        throw new IllegalStateException("There is too much air pollution! Trigger an emergency stop!");
      }
      temperature += rand.nextInt(3);
      smokePPM += rand.nextInt(10);
      runningFor = runningFor.minus(Duration.ofSeconds(1));
    }
    if(ventilation) {
      if(temperature < 10 && smokePPM < 50) {
        throw new IllegalStateException("Ventilation should not be running!");
      }
      temperature -= Math.max(temperature - rand.nextInt(4), 5);
      smokePPM = Math.max(smokePPM - rand.nextInt(12), 0);
    }
  }

  public void turnOnVentilation() {
    ventilation = true;
  }

  public void turnOffVentilation() {
    ventilation = false;
  }

  public void planJob(int jobDurationInMinutes) {
    runningFor = Duration.ofMinutes(jobDurationInMinutes);
  }


  public void startRun() {
    running = true;
  }

  public void emergencyStop() {
    this.running = false;
  }

  public void stopRun() {
    if(!(runningFor.isNegative() || runningFor.isZero())) {
      throw new IllegalStateException("Cannot stop a run that is still running.");
    }
    this.running = false;
  }
}
