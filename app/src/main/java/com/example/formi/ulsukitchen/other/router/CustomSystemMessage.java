package com.example.formi.ulsukitchen.other.router;

import ru.terrakok.cicerone.commands.SystemMessage;

class CustomSystemMessage extends SystemMessage {
    private int type;

    CustomSystemMessage(String message, int type) {
        super(message);
        this.type = type;
    }

    int getType() {
        return type;
    }
}
