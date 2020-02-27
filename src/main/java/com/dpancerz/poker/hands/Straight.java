package com.dpancerz.poker.hands;

import static com.dpancerz.cards.Rank.ACE;
import static com.dpancerz.cards.Rank.TWO;
import static com.dpancerz.maths.ConsecutiveCounter.containsConsecutive;
import static com.dpancerz.poker.hands.Hands.STRAIGHT;
import static java.util.stream.Collectors.toList;

import com.dpancerz.cards.Card;
import com.dpancerz.cards.Rank;
import java.util.List;
import java.util.Objects;
import java.util.Set;

class Straight extends PokerRank {
  private static final int DIFF_BETWEEN_LOWEST_AND_HIGHEST = 4;
  private final Rank lowestCard;
  private final Rank highestCard;

  private Straight(final Rank lowestCard, final Rank highestCard) {
    this.lowestCard = lowestCard;
    this.highestCard = highestCard;
  }

  static Straight startingFrom(final Rank lowestCard) {
    final Rank highestCard = RanksInStraight.of(lowestCard)
        .higherBy(DIFF_BETWEEN_LOWEST_AND_HIGHEST)
        .orElseThrow(RuntimeException::new)
        .rank();
    return new Straight(lowestCard, highestCard);
  }

  @Override
  Hands rank() {
    return STRAIGHT;
  }

  Rank from() {
    return lowestCard;
  }

  Rank to() {
    return highestCard;
  }

  @Override
  public int compareTo(final PokerRank other) {
    if (!(other instanceof Straight)) {
      return super.compareTo(other);
    }
    final Straight otherStraight = (Straight) other;
    return this.highestCard.compareTo(otherStraight.highestCard);
  }

  @Override
  public String toString() {
    return "Straight from " + lowestCard + " to " + highestCard;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final Straight straight = (Straight) o;
    return lowestCard == straight.lowestCard &&
        highestCard == straight.highestCard;
  }

  @Override
  public int hashCode() {
    return Objects.hash(lowestCard, highestCard);
  }

  static class Matcher implements com.dpancerz.poker.hands.Matcher {
    private static boolean containsFiveConsecutive(final List<Integer> integerRanks) {
      return containsConsecutive(integerRanks, 5);
    }

    @Override
    public Hands handRank() {
      return STRAIGHT;
    }

    @Override
    public PokerRank rank(final Cards hand) {
      final Card highestCard = hand.highestCard()
          .orElseThrow(() -> new RuntimeException("Empty hand not allowed!"));
      final Rank lowestRank = hand.getLowestCard().rank();

      if (highestCard.rank() == ACE && lowestRank == TWO) {
        return Straight.startingFrom(ACE);
      }
      return Straight.startingFrom(lowestRank);
    }

    @Override
    public boolean matches(final Cards hand) {
      final Set<Rank> ranks = hand.groupedByRank().keySet();
      final List<Integer> integerRanks = asIntegers(ranks);

      return containsFiveConsecutive(integerRanks);
    }

    private List<Integer> asIntegers(final Set<Rank> ranks) {
      return ranks.stream().map(RanksInStraight::of)
          .flatMap(rank -> rank.numericRanks().stream())
          .sorted()
          .collect(toList());
    }
  }
}
