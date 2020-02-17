package com.dpancerz.cards;

import static java.lang.String.format;
import static java.util.Arrays.stream;

public class TestCardMapper {
  public static Card cardFor(String id) {
    final Rank rank = findRank(id);
    final Suit suit = findSuit(id, rank);
    return new Card(rank, suit);
  }

  private static Suit findSuit(final String id, final Rank rank) {
    final String rankId = rank.toString();
    final String[] withRemovedRank = id.split(rankId);
    final String suitEmoji = withRemovedRank[1];
    return Suit.of(suitEmoji);
  }

  private static Rank findRank(final String id) {
    return stream(Rank.values())
        .filter(rank -> id.startsWith(rank.toString()))
        .findFirst()
        .orElseThrow(() -> new RuntimeException(format("Invalid rank of card: '%s'", id)));
  }
}
