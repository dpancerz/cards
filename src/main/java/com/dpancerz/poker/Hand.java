package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.inDescendingOrder;
import static com.dpancerz.poker.Hands.FOUR_OF_A_KIND;
import static com.dpancerz.poker.Hands.FULL_HOUSE;
import static com.dpancerz.poker.Hands.HIGH_CARD;
import static com.dpancerz.poker.Hands.ONE_PAIR;
import static com.dpancerz.poker.Hands.THREE_OF_A_KIND;
import static com.dpancerz.poker.Hands.TWO_PAIR;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.Rank;
import java.util.Collection;
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
    return Stream.of(new FourOfAKindMatcher(), new FullHouseMatcher(),
        new ThreeOfAKindMatcher(), new TwoPairMatcher(), new OnePairMatcher(),
        new HighCardMatcher())
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

  Map<com.dpancerz.cards.Rank, List<Card>> groupedByRank() {
    return cards.stream()
        .collect(groupingBy(Card::rank));
  }

  private boolean containsAPair() {
    return containsNumberOfAKind(2);
  }

  private boolean containsTwoPair() {
    return findPairs().size() >= 2;
  }

  private Set<Rank> findPairs() {
    return findNumberOfAKind(PAIR);
  }

  private boolean containsThreeOfAKind() {
    return containsNumberOfAKind(3);
  }

  private boolean containsFourOfAKind() {
    return containsNumberOfAKind(4);
  }

  private boolean containsNumberOfAKind(final int number) {
    return groupedByRank()
        .values().stream()
        .anyMatch(containsExactly(number));
  }

  private Set<Rank> findNumberOfAKind(final int number) {
    return groupedByRank()
        .entrySet().stream()
        .filter(entry -> containsExactly(number).test(entry.getValue()))
        .map(Entry::getKey)
        .collect(toSet());
  }

  private Predicate<List<Card>> containsExactly(final int number) {
    return ranks -> ranks.size() == number;
  }

  private interface Matcher {
    Hands handRank();

    PokerRank rank(final Hand cards);

    boolean matches(final Hand hand);
  }

  private static class OnePairMatcher implements Matcher {
    @Override
    public Hands handRank() {
      return ONE_PAIR;
    }

    @Override
    public PokerRank rank(final Hand cards) {
      final Rank rank = cards.findPairs().stream().findFirst()
          .orElseThrow(() -> new RuntimeException(
              "does not contain a Pair even thoughh it should"));

      return new OnePair(rank);
    }

    @Override
    public boolean matches(final Hand hand) {
      return hand.containsAPair();
    }
  }

  private static class TwoPairMatcher implements Matcher {
    @Override
    public Hands handRank() {
      return TWO_PAIR;
    }

    @Override
    public PokerRank rank(final Hand cards) {
      final List<Rank> rank = cards.findPairs()
          .stream()
          .sorted(inDescendingOrder())
          .collect(toList());

      return new TwoPair(rank.get(0), rank.get(1));
    }

    @Override
    public boolean matches(final Hand hand) {
      return hand.containsTwoPair();
    }
  }

  private static class ThreeOfAKindMatcher implements Matcher {
    @Override
    public Hands handRank() {
      return THREE_OF_A_KIND;
    }

    @Override
    public PokerRank rank(final Hand cards) {
      final Rank rank = cards.findNumberOfAKind(3).stream().findFirst()
          .orElseThrow(() -> new RuntimeException(
              "does not contain Three-of-a-kind even though it should"));

      return new ThreeOfAKind(rank);
    }

    @Override
    public boolean matches(final Hand hand) {
      return hand.containsThreeOfAKind();
    }
  }

  private static class FullHouseMatcher implements Matcher {
    @Override
    public Hands handRank() {
      return FULL_HOUSE;
    }

    @Override
    public PokerRank rank(final Hand cards) {
      final Collection<List<Card>> groupedByRank = cards.groupedByRank().values();
      if (countOfRanksOfMinimumSize(groupedByRank, 2) < 2) {
        throw new RuntimeException("can't be a full house if does not contain"
            + " at least two kinds with more than one card");
      }
      if (countOfRanksOfMinimumSize(groupedByRank, 3) < 1) {
        throw new RuntimeException("can't be a full house if does not contain"
            + " a three-of-a-kind");
      }

      final Rank rankOfThreeOfAKind = cards.findNumberOfAKind(3).stream().findFirst()
          .orElseThrow(() -> new RuntimeException(
              "does not contain 3-of-a-kind even though it's a full house"));

      final Rank pairRank = cards.groupedByRank().entrySet().stream()
          .filter(grouped -> grouped.getValue().size() > 1)
          .filter(grouped -> !grouped.getKey().equals(rankOfThreeOfAKind))
          .map(Entry::getKey)
          .min(inDescendingOrder()) // TODO replace with sth more intuitive
          .orElseThrow(() -> new RuntimeException("can't be a full house without a pair"));

      return new FullHouse(rankOfThreeOfAKind, pairRank);
    }

    private long countOfRanksOfMinimumSize(final Collection<List<Card>> groupedByRank,
        final int minimumSize) {
      return groupedByRank.stream().filter(byRank -> byRank.size() >= minimumSize).count();
    }

    @Override
    public boolean matches(final Hand hand) {
      final Collection<List<Card>> groupedByRank = hand.groupedByRank().values();
      return countOfRanksOfMinimumSize(groupedByRank, 2) > 1
          && countOfRanksOfMinimumSize(groupedByRank, 3) > 0;
    }
  }

  private static class FourOfAKindMatcher implements Matcher {
    @Override
    public Hands handRank() {
      return FOUR_OF_A_KIND;
    }

    @Override
    public PokerRank rank(final Hand cards) {
      final Rank rank = cards.findNumberOfAKind(4).stream().findFirst()
          .orElseThrow(() -> new RuntimeException(
              "does not contain Four-of-a-kind even though it should"));

      return new FourOfAKind(rank);
    }

    @Override
    public boolean matches(final Hand hand) {
      return hand.containsFourOfAKind();
    }
  }

  private static class HighCardMatcher implements Matcher {
    @Override
    public Hands handRank() {
      return HIGH_CARD;
    }

    @Override
    public PokerRank rank(final Hand hand) {
      final Card card = hand.highestCard()
          .orElseThrow(() -> new RuntimeException("Empty hand not allowed?"));
      return new HighCard(card.rank());
    }

    @Override
    public boolean matches(final Hand hand) {
      return true;
    }
  }
}
