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

fun main() {
    val initialTurtle = Turtle(0.0, 0.0, 90.0, PenState.Down, Colour.Black)

    val commands = listOf(
        Move(20.0),
        Turn(Direction.Left, 90.0),
        Move(20.0),
        Turn(Direction.Right, 90.0),
        SetColour(Colour.Red),
        Move(20.0),
        Turn(Direction.Right, 90.0),
        SetPen(PenState.Up),
        Move(40.0)
    )

    val movedTurtle = commands.fold(initialTurtle, { turtle, command -> processCommand(turtle, command) })

    println(initialTurtle)
    println(movedTurtle)
}