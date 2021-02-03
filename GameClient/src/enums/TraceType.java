package enums;

public enum TraceType { //what kind of aoe damage it is going to deal after travelling to the location
    SINGLE, //single enemy only, line trace
    CIRCLE, //multi enemy, explosives like grenades
    CONE, //cone shaped trace, basically 1/nth of a circle
    FRONT_REC,
    CHAINED, //chain to the nearest enemy like chain lightning
}
