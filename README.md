# lab6
Задание: Доработайте приложение из видео. Перенесите массив lessons из AppProps в AppState. Добавьте компонент AddLesson, который позволяет добавить урок в массив lessons. Другие компоненты (кроме App и AddLesson) не должны изменяться, но должны корректно работать.
1. В main добавлена переменная типа Lesson, которая является дополнительным уроком:<br>
val extraLesson = Lesson("Additional lesson")<br>
2. В арр var lessons: Array<Lesson> перенесены в состояние, в свойства добавлен var extraLesson:Lesson. Переопределена функция componentDidMount(), в которой описано то, что происходит после render().<br>
Добавлена функция fun click(), которая описывает действия при клике на урок, который нужно добавить.<br>
Код app:<br>
    
        interface AppProps : RProps {
        var students: Array<Student>
        var extraLesson:Lesson
        }

        interface AppState : RState {
            var lessons: Array<Lesson>
            var presents: Array<Array<Boolean>>
        }
        class App : RComponent<AppProps, AppState>() {
        override fun UNSAFE_componentWillMount() {
            state.lessons = lessonsList
            state.presents = Array(state.lessons.size) {
                Array(props.students.size) { false }
            }
        }
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
        }
3. Добавлен компонент addLesson. Он является функциональным, в качестве свойств передан click, чтобы можно было его вызвать при клике, и extraLesson, т.е. дополнительный урок, который необходимо вывести. <br>
Код для addLesson:<br>

    interface addLessonProps : RProps{
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
        click:(Event)->Unit, 
        extraLesson: Lesson 
    ) = child(faddLesson){ 
        attrs.click = click 
        attrs.extraLesson = extraLesson 
    } 

Программа после запуска:<br>![](/screen6/запуск.png)<br>
Добавление урока:<br>![](/screen6/добавление.png)<br>
Проверка корректной работы(отметили нескольких студентов присутствующими):![](/screen6/корректность.png)<br>
Состояние компонентов после данных действий:<br> ![](/screen6/состояние.png)<br>

 
