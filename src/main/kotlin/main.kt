import component.app
import data.*
import react.dom.render
import kotlin.browser.document


fun main() {
    val extraLesson = Lesson("Additional lesson")
    render(document.getElementById("root")!!) {
        app(studentList, extraLesson)
    }
}
