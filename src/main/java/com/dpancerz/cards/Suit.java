package com.dpancerz.cards;

import static java.util.Arrays.stream;

public enum Suit {
  HEARTS("❤️"),
  DIAMONDS("♦️"),
  CLUBS("♣️"),
  SPADES("♠️");

  private final String symbol;

  Suit(final String symbol) {
    this.symbol = symbol;
  }

  public static Suit of(String emoji) {
    return stream(Suit.values())
        .filter(suit -> suit.symbol.equals(emoji))
        .findFirst()
        .orElseThrow(() -> new RuntimeException("wrong suit emoji: " + emoji));
  }

  @Override
  public String toString() {
    return symbol;
  }
}
