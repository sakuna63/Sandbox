package com.sandbox.ga.selector
import com.sandbox.ga.Generation
import spock.lang.Specification
import spock.lang.Unroll

class RouletteSelectorTest extends Specification {

    @Unroll
    def "RouletteSelector#gensの変更に伴うRouletteSelector#totalの変化チェック"() {
        setup:
        def selector = new RouletteSelector(new Random())
        def gen1 = new Generation(null)
        gen1.point = 1
        def gen2 = new Generation(null)
        gen2.point = 2
        def gen3 = new Generation(null)
        gen3.point = 3

        when:
        selector.reset([gen1] as Generation[])
        then:
        selector.total == 1

        when:
        selector.reset([gen1, gen2] as Generation[])
        then:
        selector.total == 3

        when:
        selector.reset([gen1, gen2, gen3] as Generation[])
        then:
        selector.total == 6
    }
}