package com.dpancerz.poker.hands;

interface Matcher {
  Hands handRank();

  PokerRank rank(final Cards cards);

  boolean matches(final Cards hand);
}
