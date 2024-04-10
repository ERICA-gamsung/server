package com.erica.gamsung.gpt.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GptResponse {
    private List<Choice> choices;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Choice {
        // gpt 대화 인덱스 번호
        private int index;

        // gpt 대화 메시지
        // content는 유저의 prompt가 아닌 gpt로부터 받은 response
        private Message message;
    }
}
