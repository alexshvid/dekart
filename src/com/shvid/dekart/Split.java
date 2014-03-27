package com.shvid.dekart;

public final class Split<X extends Comparable<X>, Y extends Comparable<Y>, C> {

	private final Treap<X, Y, C> lesser;
	private final Treap<X, Y, C> greater;  
	private final Treap<X, Y, C> deleted;
	
	public Split(Treap<X, Y, C> lesser, Treap<X, Y, C> greater, Treap<X, Y, C> deleted) {
		this.lesser = lesser;
		this.greater = greater;
		this.deleted = deleted;
	}

	public Treap<X, Y, C> getLesser() {
		return lesser;
	}

	public Treap<X, Y, C> getGreater() {
		return greater;
	}

	public Treap<X, Y, C> getDeleted() {
		return deleted;
	}

}
