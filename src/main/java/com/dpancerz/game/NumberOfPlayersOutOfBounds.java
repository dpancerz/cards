package com.dpancerz.game;

import static java.lang.String.format;

public class NumberOfPlayersOutOfBounds extends RuntimeException {
  public NumberOfPlayersOutOfBounds(final int size) {
    super(format("Incorrect players number: %d", size));
  }
}
