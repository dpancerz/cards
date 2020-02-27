package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.inDescendingOrder;
import static com.dpancerz.poker.Hands.FULL_HOUSE;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.Rank;
import com.dpancerz.poker.Hand.Matcher;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;

class FullHouse extends PokerRank {
  private final Rank threeOfAKind;
  private final Rank pair;

  FullHouse(final Rank threeOfAKind, final Rank pair) {
    this.threeOfAKind = threeOfAKind;
    this.pair = pair;
  }

  static FullHouse of(final Rank threeOfAKind, final Rank pair) {
    return new FullHouse(threeOfAKind, pair);
  }

  @Override public int compareTo(final PokerRank other) {
    if (! (other instanceof FullHouse)) {
      return super.compareTo(other);
    }
    final FullHouse otherFull = (FullHouse) other;
    if (this.threeOfAKind != otherFull.threeOfAKind) {
      return this.threeOfAKind.compareTo(otherFull.threeOfAKind);
    }
    return this.pair.compareTo(otherFull.pair);
  }

  @Override
  Hands rank() {
    return FULL_HOUSE;
  }

  @Override
  public String toString() {
    return "FullHouse- " + threeOfAKind + "s on " + pair + 's';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final FullHouse fullHouse = (FullHouse) o;
    return threeOfAKind == fullHouse.threeOfAKind &&
        pair == fullHouse.pair;
  }

  @Override
  public int hashCode() {
    return Objects.hash(threeOfAKind, pair);
  }

  static class Matcher implements Hand.Matcher {
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
}
