package turtle_tutorial

enum class PenState { Up, Down }
enum class Colour { Black, Red, Blue, Green }
enum class TurnDirection { Left, Right }

data class Turtle (
    val xpos: Float,
    val ypos: Float,
    val angle: Float,
    val penState: PenState,
    val colour: Colour)

interface Command

data class Move (val distance: Float) : Command
data class Turn (val direction: TurnDirection, val degrees: Float) : Command
data class SetPen (val penState: PenState) : Command
data class SetColour (val colour: Colour) : Command