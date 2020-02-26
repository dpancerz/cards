package com.dpancerz.poker;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;

import com.dpancerz.cards.Rank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

enum RanksInStraight {
    TWO(Rank.TWO, 2),
    THREE(Rank.THREE, 3),
    FOUR(Rank.FOUR, 4),
    FIVE(Rank.FIVE, 5),
    SIX(Rank.SIX, 6),
    SEVEN(Rank.SEVEN, 7),
    EIGHT(Rank.EIGHT, 8),
    NINE(Rank.NINE, 9),
    TEN(Rank.TEN, 10),
    JACK(Rank.JACK, 11),
    QUEEN(Rank.QUEEN, 12),
    KING(Rank.KING, 13),
    ACE(Rank.ACE, 14, 1);

  private final Rank rank;
  private final List<Integer> numericRanks;

  RanksInStraight(final Rank rank, final Integer... numericRanks) {
    this.rank = rank;
    this.numericRanks = asList(numericRanks);
  }

  static RanksInStraight of(final Rank rank) {
    return stream(values())
        .filter(poker -> poker.rank == rank)
        .findAny().orElseThrow(RuntimeException::new);
  }

  Rank rank() {
    return rank;
  }

  List<Integer> numericRanks() {
    return new ArrayList<>(numericRanks);
  }

  Optional<RanksInStraight> higherBy(final int amount) {
    return stream(values())
        .filter(rank -> isHigherThanOtherBy(amount, rank))
        .findAny();
  }

  private boolean isHigherThanOtherBy(final int higherBy, final RanksInStraight other) {
    return this.numericRanks.stream()
        .anyMatch(numRank -> other.numericRanks.stream()
            .anyMatch(otherNumRank -> otherNumRank - higherBy == numRank));
  }
}
