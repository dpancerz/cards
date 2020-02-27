package com.dpancerz.poker;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.Rank;
import com.dpancerz.cards.Suit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

class Hand {
  public static final int PAIR = 2;
  private final Set<Card> cards;
  private final List<Matcher> matchers;

  Hand(final Set<Card> cards) {
    this.cards = cards;
    this.matchers = matchers();
  }

  Hand() {
    this(new HashSet<>());
  }

  private static List<Matcher> matchers() {
    return Stream.of(new StraightFlush.Matcher(), new FourOfAKind.Matcher(),
        new Flush.Matcher(), new FullHouse.Matcher(), new Straight.Matcher(),
        new ThreeOfAKind.Matcher(), new TwoPair.Matcher(), new OnePair.Matcher(),
        new HighCard.Matcher())
        .sorted(comparing(Matcher::handRank).reversed())
        .collect(toList());
  }

  int size() {
    return cards.size();
  }

  Set<Card> cards() {
    return new HashSet<>(cards);
  }

  void add(final Card card) {
    cards.add(card);
    //CardAdded
  }

  PokerRank rank() {
    return matchers.stream()
        .filter(matcher -> matcher.matches(this))
        .findFirst()
        .map(matcher -> matcher.rank(this))
        .orElseThrow(() -> new RuntimeException(
            "some fuckery happened- 'high card' should always match"));
  }

  Optional<Card> highestCard() {
    return cards.stream()
        .max(comparing(Card::rank));
  }

  Card getLowestCard() {
    return cards.stream()
        .min(comparing(Card::rank))
        .orElseThrow(() -> new RuntimeException(
            "empty hand does not have a lowest card"));
  }

  Map<com.dpancerz.cards.Rank, List<Card>> groupedByRank() {
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
    return findNumberOfAKind(PAIR);
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

  interface Matcher {
    Hands handRank();

    PokerRank rank(final Hand cards);

    boolean matches(final Hand hand);
  }
}
