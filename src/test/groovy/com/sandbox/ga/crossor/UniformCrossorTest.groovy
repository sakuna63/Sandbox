package com.sandbox.ga.crossor

import com.sandbox.common.Color
import com.sandbox.ga.Generation
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Created by sakuna63 on 11/15/14.
 */
class UniformCrossorTest extends Specification {
    Generation gen1, gen2;

    def setup() {
        Color red = Color.RED;
        Color blue = Color.BLUE;
        Color yellow = Color.YELLOW;
        gen1 = new Generation([red, blue, yellow, red, blue, yellow] as Color[]);
        gen2 = new Generation([blue, red, red, yellow, yellow, red] as Color[]);

    }

    @Unroll
    def "小遺伝子の大きさが2であることを確認"() {
        setup:
        Crossor crossor = new UniformCrossor(new Random());

        expect:
        Generation[] children = crossor.cross(gen1, gen2)
        children.length == 2
    }

    @Unroll
    def "小遺伝子が親遺伝子と異なることを確認"() {
        setup:
        Crossor crossor = new UniformCrossor(new Random());

        expect:
        Generation[] children = crossor.cross(gen1, gen2)
        !Arrays.equals(children[0].solution, gen1.solution) &&
                !Arrays.equals(children[1].solution, gen2.solution) &&
                !Arrays.equals(children[0].solution, gen1.solution) &&
                !Arrays.equals(children[1].solution, gen2.solution)
    }

    @Unroll
    def "小遺伝子が親遺伝子の要素から構成されていることを確認"() {
        setup:
        Crossor crossor = new UniformCrossor(new Random());

        expect:
        Generation[] children = crossor.cross(gen1, gen2)
        checkCrossedGeneration([gen1, gen2] as Generation[], children);
    }

    boolean checkCrossedGeneration(Generation[] parents, Generation[] children) {
        boolean result = true;
        parents.each {
            it.solution.eachWithIndex { Color color, int i ->
                if (!color.equals(children[0].solution[i])
                        && !color.equals(children[1].solution[i])) {
                    result = false;
                }
            }
        }
        return result;
    }

}
