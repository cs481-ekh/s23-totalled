import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MainKtTest {
    val expected = 42
    val actual = 40 + 2
    @Test
    fun sampleTest() {
        assertEquals(expected, actual)
    }

}