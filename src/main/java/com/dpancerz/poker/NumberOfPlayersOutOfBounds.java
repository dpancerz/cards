package com.dpancerz.poker;

import static java.lang.String.format;

class NumberOfPlayersOutOfBounds extends RuntimeException {
  NumberOfPlayersOutOfBounds(final int size) {
    super(format("Incorrect players number: %d", size));
  }
}
