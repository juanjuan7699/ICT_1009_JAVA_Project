package networking;

public enum SendOps { //netty ops for networking.. simplified networking only (if doesnt work scrape this entire folder)
//SENDOPS TOWARDS SERVER

    //lobby
    NULL(0x00),
    HANDSHAKE(0x01),
    PONG(0x02),
    LOGIN(0x03),
    CREATE_MATCH(0x04),
    JOIN_MATCH(0x05),
    LEAVE_MATCH(0x06),

    //in game
    MOVE(0x07), //[byte MOVE][byte toggle(start/stop)][byte DIRECTION]: [07][01][04] ...//direction 0-8 NSEW
    DO_ATTACK(0x08),
    DO_LOOT(0x09),

    //general
    CHAT(0x0A);



    public final int operation;

    SendOps(int operation) {
        this.operation = operation;
    }
}
