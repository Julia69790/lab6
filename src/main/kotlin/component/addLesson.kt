package component

import data.Lesson
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.RBuilder
import react.RProps
import react.child
import react.dom.h2
import react.dom.ul
import react.functionalComponent

interface addLessonProps : RProps{
    //var lessons: Array<Lesson>
    var click: (Event)->Unit
    var extraLesson: Lesson
}

val faddLesson =
    functionalComponent<addLessonProps>{
        h2{+ "Add Lesson"}
        ul{
            +"${it.extraLesson.name}"
            attrs.onClickFunction = it.click
        }
    }

fun RBuilder.addLesson(
    //lessons: Array<Lesson>,
    click:(Event)->Unit,
    extraLesson: Lesson
) = child(faddLesson){
    //attrs.lesson = lesson
    attrs.click = click
    attrs.extraLesson = extraLesson
}


