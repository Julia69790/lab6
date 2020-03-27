# lab6
1. В main добавлена переменная типа Lesson, которая является дополнительным уроком:<br>
val extraLesson = Lesson("Additional lesson")<br>
2. В арр var lessons: Array<Lesson> перенесены в состояние, в свойства добавлен var extraLesson:Lesson. Переопределена функция componentDidMount(), в которой описано то, что происходит после render().<br>
Добавлена функция fun click(), которая описывает действия при клике на урок, который нужно добавить.<br>
Код app:<br>
interface AppProps : RProps {<br>
    var students: Array\<Student><br>
    var extraLesson:Lesson<br>
}<br>

interface AppState : RState {<br>
    var lessons: Array\<Lesson><br>
    var presents: Array<Array<Boolean>><br>
}<br>

class App : RComponent<AppProps, AppState>() {<br>
    override fun UNSAFE_componentWillMount() {<br>
        state.lessons = lessonsList<br>
        state.presents = Array(state.lessons.size) {<br>
            Array(props.students.size) { false }<br>
        }<br>
    }<br>

    override fun componentDidMount() {
        click()
        state.presents = Array(state.lessons.size+1) {
            Array(props.students.size) { false }
        }
    }


    override fun RBuilder.render() {
        h1 { +"App" }
        addLesson(click(),props.extraLesson)
        lessonListFull(
            state.lessons,
            props.students,
            state.presents,
            onClickLessonFull
        )
        studentListFull(
            state.lessons,
            props.students,
            transform(state.presents),
            onClickStudentFull
        )
    }

    fun transform(source: Array<Array<Boolean>>) =
        Array(source[0].size){row->
            Array(source.size){col ->
                source[col][row]
            }
        }

    fun onClick(indexLesson: Int, indexStudent: Int) =
        { _: Event ->
            setState {
                presents[indexLesson][indexStudent] =
                    !presents[indexLesson][indexStudent]
            }
        }

    val onClickLessonFull =
        { indexLesson: Int ->
            { indexStudent: Int ->
                onClick(indexLesson, indexStudent)
            }
        }

    val onClickStudentFull =
        { indexStudent: Int ->
            { indexLesson: Int ->
                onClick(indexLesson, indexStudent)
            }
        }
    fun click() = {_: Event ->
        setState{
            lessons += props.extraLesson
        }
    }
}

fun RBuilder.app(
    students: Array<Student>,
    extraLesson:Lesson
) = child(App::class) {
    attrs.students = students
    attrs.extraLesson = extraLesson
}<br>
3. Добавлен компонент addLesson. Он является функциональным, в качестве свойств передан click, чтобы можно было его вызвать при клике, и extraLesson, т.е. дополнительный урок, который необходимо вывести. <br>
Код для addLesson:<br>
interface addLessonProps : RProps{ <br>
    var click: (Event)->Unit <br>
    var extraLesson: Lesson <br>
} <br>

val faddLesson = <br>
    functionalComponent<addLessonProps>{ <br>
        h2{+ "Add Lesson"} <br>
        ul{ <br>
            +"${it.extraLesson.name}" <br>
            attrs.onClickFunction = it.click <br>
        } <br>
    } <br>

fun RBuilder.addLesson( <br>
    click:(Event)->Unit, <br>
    extraLesson: Lesson <br>
) = child(faddLesson){ <br>
    attrs.click = click <br>
    attrs.extraLesson = extraLesson <br>
} <br>
Программа после запуска:<br>![](/screen6/запуск.png)<br>
Добавление урока:<br>![](/screen6/добавление.png)<br>
Проверка корректной работы(отметили нескольких студентов присутствующими):![](/screen6/корректность.png)<br>
