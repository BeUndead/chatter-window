package com.com.sockets;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;
import java.util.function.Consumer;

/**
 * This class is an implementation for <code>StompSessionHandlerAdapter</code>.
 * Once a connection is established, We subscribe to /topic/messages and 
 * send a sample message to server.
 * 
 * @author Kalyan
 *
 */
public class CustomSessionHandler extends StompSessionHandlerAdapter {

    private /* Nullable */ StompSession session = null;

    private final Consumer<String> messageOutput;
    private final String name;

    public CustomSessionHandler(final String name,
                                final Consumer<String> messageOutput) {
        this.name = name;
        this.messageOutput = messageOutput;
    }

    public void send(final String to, final String message) {
        this.session.send("/app/chat", buildMessage(to, message));
        System.out.println("Sending message from " + this.name + " to " + to);
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        this.session = session;
        session.subscribe("/topic/" + this.name, this);
        session.subscribe("/topic/Everyone", this);

//        session.send("/app/chat", buildMessage("Jenn", "Hi"));
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        System.out.println("Sad face: " + exception.getMessage());
        exception.printStackTrace();
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return OutputMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        Message msg = ((OutputMessage) payload).getPayload();
        this.messageOutput.accept(msg.getMessage());
        System.out.println(name + " received : " + msg.getMessage() + " at " + msg.getTimestamp());
    }


    private Message buildMessage(final String to, final String data) {
        final Message message = new Message();
        message.setTo(to);
        message.setMessage(data);
        return message;
    }
}