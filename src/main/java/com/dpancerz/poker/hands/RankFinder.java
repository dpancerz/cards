package com.dpancerz.poker.hands;

class RankFinder {
  private final Matchers matchers;

  RankFinder(final Matchers matchers) {
    this.matchers = matchers;
  }

  public PokerRank findRank(final Cards hand) {
    return matchers.all().stream()
        .filter(matcher -> matcher.matches(hand))
        .findFirst()
        .map(matcher -> matcher.rank(hand))
        .orElseThrow(() -> new RuntimeException(
            "some fuckery happened- 'high card' should always match"));
  }
}
