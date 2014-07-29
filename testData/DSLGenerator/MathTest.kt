package testData.DSLGenerator.math

import ru.ifmo.rain.mekhanikov.antdsl.*
import ru.ifmo.rain.mekhanikov.antdsl.taskdefs.*
import ru.ifmo.rain.mekhanikov.antdsl.other.net.sf.antcontrib.math.*

val destFile by StringProperty()
val antContribJarFile by StringProperty()
val fact by DoubleProperty()

fun main(args: Array<String>) {
    project(args) {
        taskdef(resource = "net/sf/antcontrib/antcontrib.properties",
                classpath = antContribJarFile)

        default = target("Test math") {
            math(result = "fact") {
                op(op = "*") {
                    num(value = "1")
                    num(value = "2")
                    num(value = "3")
                    num(value = "4")
                    num(value = "5")
                }
            }
            echo(message = "$fact", file = destFile)
        }
    }
}
