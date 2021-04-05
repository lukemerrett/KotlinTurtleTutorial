package turtle_tutorial

import kotlin.math.*

enum class PenState { Up, Down }
enum class Colour { Black, Red, Blue, Green }
enum class Direction { Left, Right }

data class Turtle (
    val xpos: Double,
    val ypos: Double,
    val angle: Double,
    val penState: PenState,
    val colour: Colour)

interface Command

data class Move (val distance: Double) : Command
data class Turn (val direction: Direction, val degrees: Double) : Command
data class SetPen (val penState: PenState) : Command
data class SetColour (val colour: Colour) : Command

fun processCommand (turtle: Turtle, command: Command): Turtle {
    return when (command) {
        is Move -> {
            val angleInRads = turtle.angle * (PI /180.0)
            return turtle.copy(
                xpos = turtle.xpos + command.distance * sin(angleInRads),
                ypos = turtle.ypos + command.distance * cos(angleInRads)
            )
        }
        is Turn -> {
            return when (command.direction) {
                Direction.Left -> turtle.copy(angle = turtle.angle - command.degrees)
                Direction.Right -> turtle.copy(angle = turtle.angle + command.degrees)
            }
        }
        is SetPen -> turtle.copy(penState = command.penState)
        is SetColour -> turtle.copy(colour = command.colour)
        else -> turtle
    }
}