package com.dpancerz.poker;

import static com.dpancerz.cards.Rank.JACK;
import static java.util.stream.Collectors.toList;

import com.dpancerz.cards.Card;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

class RankMatcher {
  private final List<Matcher> matchers;

  public RankMatcher() {
    this.matchers = Stream.of(new OnePairMatcher(), new OnePairMatcher())
        .sorted((a, b) -> a.rank().compareTo(b.rank()))
        .collect(toList());
  }

  Rank match(final Collection<Card> cards) {
    return matchers.stream()
        .filter(matcher -> matcher.matches(cards))
        .findFirst()
        .map(Matcher::rank)
        .orElseThrow(() -> new RuntimeException(
            "some fuckery happened- 'high card' should always match"));
  }

  interface Matcher {
    Rank rank();
    boolean matches(final Collection<Card> cards);
  }

  class OnePairMatcher implements Matcher {
    @Override public Rank rank() {
      return new OnePair(JACK);
    }

    @Override
    public boolean matches(final Collection<Card> cards) {
      return true;
    }
  }

  class HighCardMatcher implements Matcher {
    @Override public Rank rank() {
      return new HighCard();
    }

    @Override
    public boolean matches(final Collection<Card> cards) {
      return true;
    }
  }
}
