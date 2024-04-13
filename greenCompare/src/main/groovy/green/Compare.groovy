package green

class Compare {
    boolean expected
    Closure next

    private Compare(Closure target, boolean expected) {
        this.next = target
        this.expected = expected
    }

    static Compare That(Closure next) {
        return new Compare(next, true)
    }

    static Compare Not(Closure next) {
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
