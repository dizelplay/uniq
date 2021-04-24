import org.junit.After
import org.junit.Before
import org.junit.Test
import org.vorobyov.uniq.Uniq
import java.io.File
import kotlin.test.assertEquals

private val eol = System.lineSeparator()

private fun assertFileContent(expectedContent: String, actualFilename: String) {

    val actual = File(actualFilename).readLines()
    val expected = expectedContent.lines()

    assertEquals(expected.size, actual.size, "Expected ${expected.size} lines, but was: ${actual.size}")

    for (i in expected.indices) {
        assertEquals(expected[i], actual[i])
    }
}

private fun createFile(filename: String, content: String) {
    File(filename).bufferedWriter().append(content).close()
}

private fun deleteFile(filename: String) {
    File(filename).delete()
}

class UniqTest {

    @Before
    fun init() {
        createFile(
            "input",
            "hello${eol}how are you${eol}hello${eol}hello${eol}HELLO${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin"
        )
    }

    @After
    fun clear() {
        deleteFile("input")
        deleteFile("output")
    }

    @Test
    fun simpleTest1() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter()).process()
        assertFileContent(
                "hello${eol}how are you${eol}hello${eol}HELLO${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin",
                "output"
        )
    }
    @Test
    fun simpleTest2() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter(), printCount = true).process()
        assertFileContent(
                "1 hello${eol}1 how are you${eol}2 hello${eol}1 HELLO${eol}1 from kotlin${eol}1 tooo kotlin${eol}1 foor kotlin",
                "output"
        )
    }
    @Test
    fun simpleTest3() {
        Uniq(
                File("input").bufferedReader(),
                File("output").bufferedWriter(),
                printOnlyUnique = true,
                printCount = true
        ).process()
        assertFileContent(
                "1 hello${eol}1 how are you${eol}1 HELLO${eol}1 from kotlin${eol}1 tooo kotlin${eol}1 foor kotlin",
                "output"
        )
    }
    @Test
    fun simpleTest4() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter(), printOnlyUnique = true).process()
        assertFileContent(
            "hello${eol}how are you${eol}HELLO${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin",
            "output"
        )
    }

    @Test
    fun testIgnoreCase1() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter(), ignoreCase = true).process()
        assertFileContent(
            "hello${eol}how are you${eol}hello${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin",
            "output"
        )
    }
    @Test
    fun testIgnoreCase2() {
        Uniq(
            File("input").bufferedReader(),
            File("output").bufferedWriter(),
            ignoreCase = true,
            printCount = true
        ).process()
        assertFileContent(
            "1 hello${eol}1 how are you${eol}3 hello${eol}1 from kotlin${eol}1 tooo kotlin${eol}1 foor kotlin",
            "output"
        )
    }
    @Test
    fun testIgnoreCase3() {
        Uniq(
            File("input").bufferedReader(),
            File("output").bufferedWriter(),
            ignoreCase = true,
            printOnlyUnique = true,
            printCount = true
        ).process()
        assertFileContent(
            "1 hello${eol}1 how are you${eol}1 from kotlin${eol}1 tooo kotlin${eol}1 foor kotlin",
            "output"
        )
    }
    @Test
    fun testIgnoreCase4() {
        Uniq(
            File("input").bufferedReader(),
            File("output").bufferedWriter(),
            ignoreCase = true,
            printOnlyUnique = true
        ).process()
        assertFileContent("hello${eol}how are you${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin", "output")
    }

    @Test
    fun testSkipChars1() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter(), skipChars = 0).process()
        assertFileContent(
            "hello${eol}how are you${eol}hello${eol}HELLO${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin",
            "output"
        )
    }
    @Test
    fun testSkipChars2() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter(), skipChars = 5).process()
        assertFileContent("hello${eol}how are you${eol}hello${eol}from kotlin", "output")
    }
    @Test
    fun testSkipChars3() {
        Uniq(File("input").bufferedReader(), File("output").bufferedWriter(), skipChars = 4).process()
        assertFileContent("hello${eol}how are you${eol}hello${eol}HELLO${eol}from kotlin", "output")
    }
    @Test
    fun testSkipChars4() {
        Uniq(
            File("input").bufferedReader(),
            File("output").bufferedWriter(),
            ignoreCase = true,
            skipChars = 0
        ).process()
        assertFileContent(
            "hello${eol}how are you${eol}hello${eol}from kotlin${eol}tooo kotlin${eol}foor kotlin",
            "output"
        )
    }
    @Test
    fun testSkipChars5() {
        Uniq(
            File("input").bufferedReader(),
            File("output").bufferedWriter(),
            ignoreCase = true,
            skipChars = 5
        ).process()
        assertFileContent("hello${eol}how are you${eol}hello${eol}from kotlin", "output")
    }
    @Test
    fun testSkipChars6() {
        Uniq(
            File("input").bufferedReader(),
            File("output").bufferedWriter(),
            ignoreCase = true,
            skipChars = 4
        ).process()
        assertFileContent("hello${eol}how are you${eol}hello${eol}from kotlin", "output")
    }
}