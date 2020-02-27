package com.dpancerz.poker.hands;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.Rank;
import com.dpancerz.cards.Suit;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

class Cards {
  private final Set<Card> cards;

  Cards(final Set<Card> cards) {
    this.cards = cards;
  }

  Card getLowestCard() {
    return cards.stream()
        .min(comparing(Card::rank))
        .orElseThrow(() -> new RuntimeException(
            "empty hand does not have a lowest card"));
  }

  void add(final Card card) {
    cards.add(card);
  }

  Optional<Card> highestCard() {
    return cards.stream()
            .max(comparing(Card::rank));
  }

  Map<Rank, List<Card>> groupedByRank() {
    return cards.stream()
            .collect(groupingBy(Card::rank));
  }

  Map<Suit, List<Card>> groupedBySuit() {
    return cards.stream()
            .collect(groupingBy(Card::suit));
  }

  boolean containsAPair() {
    return containsNumberOfAKind(2);
  }

  boolean containsTwoPair() {
    return findPairs().size() >= 2;
  }

  Set<Rank> findPairs() {
    return findNumberOfAKind(2);
  }

  boolean containsThreeOfAKind() {
    return containsNumberOfAKind(3);
  }

  boolean containsFourOfAKind() {
    return containsNumberOfAKind(4);
  }

  private boolean containsNumberOfAKind(final int number) {
    return groupedByRank()
        .values().stream()
        .anyMatch(containsExactly(number));
  }

  Set<Rank> findNumberOfAKind(final int number) {
    return groupedByRank()
        .entrySet().stream()
        .filter(entry -> containsExactly(number).test(entry.getValue()))
        .map(Entry::getKey)
        .collect(toSet());
  }

  private Predicate<List<Card>> containsExactly(final int number) {
    return ranks -> ranks.size() == number;
  }

  int size() {
    return cards.size();
  }
}
