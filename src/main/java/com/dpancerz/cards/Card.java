package com.dpancerz.cards;

import java.util.Objects;

public class Card {
  private final Rank rank;
  private final Suit suit;

  Card(final Rank rank, final Suit suit) {
    this.rank = rank;
    this.suit = suit;
  }

  public Rank rank() {
    return rank;
  }

  public Suit suit() {
    return suit;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final Card card = (Card) o;
    return rank == card.rank &&
        suit == card.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(rank, suit);
  }

  @Override
  public String toString() {
    return rank.toString() + suit.toString();
  }
}
