package green

class Compare {
    boolean expected
    def next

    private Compare(Closure target, expected) {
        this.next = target
        this.expected = expected
    }

    static Compare that(Closure next) {
        return new Compare(next, true)
    }

    static Compare not(Closure next) {
        return new Compare({ !next.call(it) }, false)
    }

    Compare shouldBe(Closure next) {
        this.next = next
        return this
    }

    boolean isValid() {
        return next.call() == expected
    }

    boolean isInvalid() {
        return next.call() != expected
    }
}
