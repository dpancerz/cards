package com.dpancerz.cards;

import static com.dpancerz.cards.Rank.NINE;
import static com.dpancerz.cards.Rank.SEVEN;
import static com.dpancerz.cards.Rank.TWO;
import static java.util.EnumSet.allOf;
import static java.util.stream.Collectors.toList;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Deck {
  private final List<Card> cards;

  public Deck(final List<Card> cards) {
    this.cards = cards;
  }

  public static Deck startingFrom(final Rank lowestRank) {
    return new Deck(new LinkedList<>(allOf(Suit.class).stream()
        .flatMap(suit -> allOf(Rank.class).stream()
            .filter(rank -> rank.compareTo(lowestRank) <= 0)
            .map(rank -> new Card(rank, suit)))
        .collect(toList())));
  }

  public static Deck ofTwentyFourCards() {
    return startingFrom(NINE);
  }

  public static Deck ofThirtyTwoCards() {
    return startingFrom(SEVEN);
  }

  public static Deck ofFiftyTwoCards() {
    return startingFrom(TWO);
  }

  void shuffle() {
    Collections.shuffle(cards);
  }


  @Override
  public String toString() {
    return cards.toString();
  }

  public int size() {
    return cards.size();
  }

  public Card drawOne() {
    if (cards.isEmpty()) {
      throw new DrawFromEmptyDeck();
    }
    return cards.remove(0);
  }

  boolean doesNotContain(final Card card) {
    return !contains(card);
  }

  boolean doesNotContainAnyOf(final Collection<Card> cards) {
    return cards.stream().noneMatch(this::contains);
  }

  boolean contains(final Card card) {
    return cards.contains(card);
  }
}
