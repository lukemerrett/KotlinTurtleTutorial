# Kotlin Turtle Tutorial

A Kotlin version of the [F# Turtle Tutorial](https://github.com/FSharpBristol/FSharpTurtleTutorial) to explore the differences between the languages.

This is an exercise to build a [Logo Turtle](https://turtleacademy.com/playground/en) DSL, inspired by [Scott Wlaschin's](https://twitter.com/ScottWlaschin) blog post ["Thirteen ways of looking at a turtle"](https://fsharpforfunandprofit.com/posts/13-ways-of-looking-at-a-turtle/).

The exercise acts as both a good introduction to the syntax of the language along with demonstrating the power of discriminated unions, record types and pattern matching.

## F# Version For Reference

```fsharp
module TurtleRunner

open System

// Basic discriminted unions - think enums
type PenState = Up | Down
type Colour = Black | Red | Blue | Green
type TurnDirection = Left | Right

// Record type - translates to a sealed class with readonly, immutable properties
type Turtle = {
    xpos:float
    ypos:float
    angle:float
    penState:PenState
    colour:Colour
}

// Discriminated unions with values - think enums on steroids
type Command = 
    | Move of float
    | Turn of TurnDirection * float 
    | SetPen of PenState 
    | SetColour of Colour

// Function to apply state changes in a Command to a Turtle
let processCommand turtle command = 
    match command with
    | Move distance ->  let angleInRads = turtle.angle * (Math.PI/180.0)
                        {turtle with 
                            xpos = turtle.xpos + distance * sin angleInRads
                            ypos = turtle.ypos + distance * cos angleInRads}
    | Turn(direction, degrees) -> match direction with 
                                  | Left ->  {turtle with angle = turtle.angle - degrees} 
                                  | Right -> {turtle with angle = turtle.angle + degrees} 
    | SetPen state ->  {turtle with penState=state}
    | SetColour colour -> {turtle with colour=colour}

// List of Commands to apply
let commands = [
    Move 20.0
    Turn(Left, 90.0)
    Move 20.0
    Turn(Right, 90.0)
    SetColour Red 
    Move 20.0
    Turn(Right, 90.0)
    SetPen Up 
    Move 40.0
]

// Our initial Turtle instance
let turtle = {xpos=0.0; ypos=0.0; angle=90.0; penState=Down; colour=Black}

// Apply all the commands to the Turtle in turn
let movedTurtle = 
    commands 
    |> List.fold (fun agg command -> processCommand agg command) turtl
```