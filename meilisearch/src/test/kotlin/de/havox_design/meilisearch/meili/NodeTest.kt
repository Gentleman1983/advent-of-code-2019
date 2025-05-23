package de.havox_design.meilisearch.meili

import de.havox_design.aoc.utils.kotlin.helpers.tests.shouldBe
import nl.jqno.equalsverifier.EqualsVerifier
import nl.jqno.equalsverifier.Warning
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class NodeTest {
    @ParameterizedTest
    @MethodSource("getDataForTestBuildTree")
    fun testBuildTree(filename: String, optimize: Boolean, expectedResult: Node) =
        Node.treeOf(MeiliSearch(filename).data, optimize).shouldBe(expectedResult)

    @Test
    fun verifyEqualsContractOnNodeClass() =
        EqualsVerifier
            .simple()
            .forClass(Node::class.java)
            .withPrefabValues(Node::class.java, Node(key = "Foo"), Node(key = "Bar"))
            .suppress(Warning.INHERITED_DIRECTLY_FROM_OBJECT)
            .verify()

    companion object {
        @JvmStatic
        private fun getDataForTestBuildTree(): Stream<Arguments> =
            Stream.of(
                Arguments.of(
                    "sample.txt",
                    false,
                    getUnoptimizedTree()
                ),
                Arguments.of(
                    "sample.txt",
                    true,
                    getOptimizedTree()
                )
            )

        private fun getUnoptimizedTree(): Node {
            val root = Node("root")
            val l = Node("L", parent = root)
            val ll = Node("L", parent = l)
            val lll = Node("L", setOf("tommy"), parent = ll)
            val lr = Node("R", parent = l)
            val lrl = Node("L", setOf("caro"), parent = lr)
            val lrll = Node("L", setOf("chayaline", "thomas"), parent = lrl)
            val lrlr = Node("R", setOf("kero"), parent = lrl)
            val lrr = Node("R", parent = lr)
            val lrrr = Node("R", setOf("luna"), parent = lrr)
            val r = Node("R", parent = root)
            val rl = Node("L", parent = r)
            val rll = Node("L", parent = rl)
            val rlll = Node("L", setOf("loic"), parent = rll)
            val rllr = Node("R", setOf("lena"), parent = rll)
            val rlr = Node("R", parent = rl)
            val rlrl = Node("L", parent = rlr)
            val rlrlr = Node("R", setOf("tamo"), parent = rlrl)

            ll.left = lll
            l.left = ll
            l.right = lr
            lr.left = lrl
            lr.right = lrr
            lrl.left = lrll
            lrl.right = lrlr
            lrr.right = lrrr
            root.left = l
            root.right = r
            r.left = rl
            rl.left = rll
            rl.right = rlr
            rll.left = rlll
            rll.right = rllr
            rlr.left = rlrl
            rlrl.right = rlrlr

            return root
        }

        private fun getOptimizedTree(): Node {
            val root = Node("root")
            val l = Node("L", parent = root)
            val lll = Node("LL", setOf("tommy"), parent = l)
            val lr = Node("R", parent = l)
            val lrl = Node("L", setOf("caro"), parent = lr)
            val lrll = Node("L", setOf("chayaline", "thomas"), parent = lrl)
            val lrlr = Node("R", setOf("kero"), parent = lrl)
            val lrrr = Node("RR", setOf("luna"), parent = lr)
            val rl = Node("RL", parent = root)
            val rll = Node("L", parent = rl)
            val rlll = Node("L", setOf("loic"), parent = rll)
            val rllr = Node("R", setOf("lena"), parent = rll)
            val rlrlr = Node("RLR", setOf("tamo"), parent = rl)

            l.left = lll
            l.right = lr
            lr.left = lrl
            lr.right = lrrr
            lrl.left = lrll
            lrl.right = lrlr
            root.left = l
            root.right = rl
            rl.left = rll
            rl.right = rlrlr
            rll.left = rlll
            rll.right = rllr

            return root
        }
    }
}
