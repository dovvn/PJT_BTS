package com.ssafy.bts.Domain.Comment;

import com.ssafy.bts.Domain.Qna.QnaDTO;
import com.ssafy.bts.Domain.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommetDTO {
    private int comId;
    private QnaDTO qna;
    private UserDTO user;
    private String comContent;
    private Date comDate;

    public CommetDTO(Comment comment){
        this.comId = comment.getComId();
        if(comment.getQna() != null){
            qna = new QnaDTO();
            this.qna.setQnaId(comment.getQna().getQnaId());
        }
        if(comment.getUser() != null){
            user = new UserDTO();
            this.user.setUserId(comment.getUser().getUserId());
            this.user.setUserNickname(comment.getUser().getUserNickname());
            this.user.setUserImg(comment.getUser().getUserImg());
        }
        this.comContent = comment.getComContent();
        this.comDate = comment.getComDate();
    }
}
