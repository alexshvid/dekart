package com.shvid.dekart;

public final class Split<X extends Comparable<X>, Y extends Comparable<Y>, C> {

	private final Treap<X, Y, C> lesser;
	private final Treap<X, Y, C> greater;  
	
	public Split(Treap<X, Y, C> lesser, Treap<X, Y, C> greater) {
		this.lesser = lesser;
		this.greater = greater;
	}

	public Treap<X, Y, C> getLesser() {
		return lesser;
	}

	public Treap<X, Y, C> getGreater() {
		return greater;
	}

}
