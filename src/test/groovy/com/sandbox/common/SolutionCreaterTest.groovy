package com.sandbox.common

import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by sakuna63 on 11/15/14.
 */
class SolutionCreaterTest extends Specification {
    Color red, blue, yellow

    def setup() {
        red = Color.RED;
        blue = Color.BLUE;
        yellow = Color.YELLOW;
    }

    @Unroll
    def "valueExceptWithの動作テスト"() {
        when:
        Color[] colors = Color.valuesExceptWith(red);
        then:
        colors.findAll() == [blue, yellow] && colors.find() != red

        when:
        colors = Color.valuesExceptWith(blue);
        then:
        colors.findAll() == [red, yellow] && colors.find() != blue

        when:
        colors = Color.valuesExceptWith(yellow);
        then:
        colors.findAll() == [red, blue] && colors.find() != yellow
    }

}