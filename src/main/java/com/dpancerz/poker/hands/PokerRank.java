package com.dpancerz.poker.hands;

abstract class PokerRank implements Comparable<PokerRank> {
  @Override
  public int compareTo(PokerRank pokerRank) {
    return rank().compareTo(pokerRank.rank());
  }

  abstract Hands rank();
}
