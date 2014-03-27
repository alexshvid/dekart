package com.shvid.dekart;

public final class Split<X extends Comparable<X>, Y extends Comparable<Y>, C> {

	private final ImmutableTreap<X, Y, C> lesser;
	private final ImmutableTreap<X, Y, C> greater;  
	private final ImmutableTreap<X, Y, C> deleted;
	
	public Split(ImmutableTreap<X, Y, C> lesser, ImmutableTreap<X, Y, C> greater, ImmutableTreap<X, Y, C> deleted) {
		this.lesser = lesser;
		this.greater = greater;
		this.deleted = deleted;
	}

	public ImmutableTreap<X, Y, C> getLesser() {
		return lesser;
	}

	public ImmutableTreap<X, Y, C> getGreater() {
		return greater;
	}

	public ImmutableTreap<X, Y, C> getDeleted() {
		return deleted;
	}

}
