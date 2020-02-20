package com.dpancerz.cards;

import static java.util.Comparator.comparingInt;

import java.util.Comparator;

public enum Rank {
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("9"),
  TEN("10"),
  JACK("J"),
  QUEEN("Q"),
  KING("K"),
  ACE("A");

  private final String symbol;

  Rank(final String symbol) {
    this.symbol = symbol;
  }

  public static Comparator<Rank> inDescendingOrder() {
    return comparingInt(Rank::ordinal).reversed();
  }

  @Override
  public String toString() {
    return symbol;
  }

  public boolean isLowerOrEqualTo(final Rank other) {
    return !isGreaterThan(other);
  }

  public boolean isGreaterThan(final Rank other) {
    return this.compareTo(other) > 0;
  }

  public boolean isLowerThan(final Rank other) {
    return this.compareTo(other) < 0;
  }
}
