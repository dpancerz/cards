package com.dpancerz.cards;

public enum Suit {
  HEARTS("❤️"),
  DIAMONDS("♦"),
  CLUBS("♣️"),
  SPADES("♠️");

  private final String symbol;

  Suit(final String symbol) {
    this.symbol = symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
