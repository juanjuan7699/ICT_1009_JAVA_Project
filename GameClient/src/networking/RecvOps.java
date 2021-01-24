package networking;

public enum RecvOps { //netty ops for networking.. simplified networking only (if doesnt work scrape this entire folder)
//RECVOPS COMING FROM SERVER

    //lobby
    NULL(0x00),
    HANDSHAKE(0x01),
    PING(0x02),
    LOGIN_RESULT(0x03),
    CREATE_MATCH(0x04),
    JOIN_MATCH(0x05),
    LEAVE_MATCH(0x06),

    //in game [all are from others]
    MOVE(0x07), //[byte MOVE][byte toggle][String headerlen+UUID][byte DIRECTION]: [07] [01] [00 05 01 02 03 04 05] [00] ..//if toggle 0, packet size is 2
    DO_ATTACK(0x08),
    DO_LOOT(0x09),

    //general
    CHAT(0x0A);



    public final int operation;

    RecvOps(int operation) {
        this.operation = operation;
    }
}