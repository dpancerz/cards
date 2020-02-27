package com.dpancerz.poker.hands;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Stream;

class Matchers {
  static final List<Matcher> ALL_MATCHERS = Stream
      .of(new StraightFlush.Matcher(), new FourOfAKind.Matcher(),
      new Flush.Matcher(), new FullHouse.Matcher(), new Straight.Matcher(),
      new ThreeOfAKind.Matcher(), new TwoPair.Matcher(), new OnePair.Matcher(),
      new HighCard.Matcher())
      .sorted(comparing(Matcher::handRank).reversed())
      .collect(toList());
  private List<Matcher> matchers;

  Matchers() {
    this(ALL_MATCHERS);
  }

  Matchers(final List<Matcher> matchers) {
    this.matchers = matchers;
  }

  List<Matcher> all() {
    return matchers;
  }
}
