package com.dpancerz.poker;

import static com.dpancerz.poker.Hands.FLUSH;
import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.Rank;
import com.dpancerz.cards.Suit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

class Flush extends PokerRank {
  public static final int POKER_HAND_SIZE = 5;
  private final List<Rank> ranks;
  private final Suit suit;

  public Flush(final Suit suit, final List<Rank> ranks) {
    if (ranks.size() != POKER_HAND_SIZE) {
      throw new IllegalArgumentException(
          "wrong number of cards. Expected 5, got: " + ranks.size());
    }
    final ArrayList<Rank> ranksList = new ArrayList<>(ranks);
    ranksList.sort(comparingInt(Rank::ordinal).reversed());
    this.ranks = ranksList;
    this.suit = suit;
  }

  static Flush of(final Suit suit, final Rank... ranks) {
    return new Flush(suit, asList(ranks));
  }

  Rank highestCard() {
    return ranks.get(0);
  }

  Rank highestCard(int number) {
    if (number > POKER_HAND_SIZE || number < 1) {
      throw new IllegalArgumentException("allowed values: 1 to 5.");
    }
    return ranks.get(number - 1);
  }

  Suit suit() {
    return suit;
  }

  Set<Card> cards() { // feels hackish and unnecessary. to be replaced?
    return ranks.stream()
        .map(rank -> new Card(rank, suit))
        .collect(toSet());
  }

  List<Rank> ranks() {
    return ranks;
  }

  @Override
  Hands rank() {
    return FLUSH;
  }

  @Override
  public int compareTo(final PokerRank other) {
    if (!(other instanceof Flush)) {
      return super.compareTo(other);
    }
    final Flush otherFlush = (Flush) other;
    for (int i = 1; i <= POKER_HAND_SIZE; i++) {
      final int comparison = this.highestCard(i)
          .compareTo(otherFlush.highestCard(i));
      if (comparison != 0) {
        return comparison;
      }
    }
    return 0;
  }

  @Override
  public String toString() {
    return "Flush of " + ranks + " in " + suit;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Flush flush = (Flush) o;
    return Objects.equals(ranks, flush.ranks) &&
        suit == flush.suit;
  }

  @Override
  public int hashCode() {
    return Objects.hash(ranks, suit);
  }

  static class Matcher implements Hand.Matcher {
    @Override
    public Hands handRank() {
      return FLUSH;
    }

    @Override
    public Flush rank(final Hand cards) {
      final Map.Entry<Suit, List<Card>> suitsToCards = cards
          .groupedBySuit().entrySet().stream()
          .filter(entry -> entry.getValue().size() > 4)
          .max(withHighestCard(0)
              .thenComparing(withHighestCard(1))
              .thenComparing(withHighestCard(2))
              .thenComparing(withHighestCard(3))
              .thenComparing(withHighestCard(4)))
          .orElseThrow(RuntimeException::new);

      final Suit suit = suitsToCards.getKey();
      final List<Rank> ranks = suitsToCards.getValue().stream()
          .map(Card::rank).collect(toList());

      return new Flush(suit, ranks);
    }

    private Comparator<Entry<Suit, List<Card>>> withHighestCard(final int which) {
      return comparing(entry -> topRank(entry, which));
    }

    private Rank topRank(final Entry<Suit, List<Card>> entry, final int which) {
      return entry.getValue().get(which).rank();
    }

    @Override
    public boolean matches(final Hand hand) {
      final Map<Suit, List<Card>> suitsToCards = hand.groupedBySuit();
      return suitsToCards.entrySet().stream()
          .anyMatch(entry -> entry.getValue().size() > 4);
    }
  }
}
