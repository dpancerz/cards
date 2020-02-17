package com.dpancerz.poker;

abstract class Rank implements Comparable<Rank> {
  @Override
  public int compareTo(Rank rank) {
    return rank().compareTo(rank.rank());
  }

  abstract Hands rank();
}
