package com.erica.gamsung.global.config.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("{} 연결됨", session.getId());
        sessionList.add(session);
        log.info(session + " 웹소켓 생성");
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : " + payload);

        for(WebSocketSession sess: sessionList) {
            sess.sendMessage(message);
        }
    }
    @Override
    public void afterConnectionClosed(final WebSocketSession session, final CloseStatus status) throws Exception {
        log.info(session + " 웹소켓 해제");
        sessionList.remove(session);
    }
}
//ws로 구독
//서버 -> 안드로이드 구독 성공! 메시지 전달
//이후 로그인 페이지로 로딩 (get 요청 받기 - 로그인경로/uuid) 여기까지는 가능..!

//로그인 되면 그 서버 -> 안드로이드 uuid에 토큰 전달