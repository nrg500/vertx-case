package com.openvalue.exercise;

import io.vertx.core.AbstractVerticle;

public class ChipVerticle extends AbstractVerticle {
  ChipMachine chipMachine1 = new ChipMachine();
  ChipMachine chipMachine2 = new ChipMachine();
  ChipMachine chipMachine3 = new ChipMachine();

  @Override
  public void start() {
    chipMachine1.planJob(3);
    chipMachine1.startRun();
    vertx.setPeriodic(1000, id -> {
      chipMachine1.tick();
      chipMachine2.tick();
      chipMachine3.tick();
    });
  }
}
