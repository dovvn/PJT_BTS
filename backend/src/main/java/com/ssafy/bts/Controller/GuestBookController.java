package com.ssafy.bts.Controller;

import com.ssafy.bts.Domain.GuestBook.GuestBook;
import com.ssafy.bts.Domain.GuestBook.GuestBookDTO;
import com.ssafy.bts.Domain.Info.Info;
import com.ssafy.bts.Domain.Info.InfoDTO;
import com.ssafy.bts.Domain.Room.Room;
import com.ssafy.bts.Domain.User.User;
import com.ssafy.bts.Service.GuestBookService;
import com.ssafy.bts.Service.RoomService;
import com.ssafy.bts.Service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Api
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/gb")
@RequiredArgsConstructor
public class GuestBookController {
    private final GuestBookService guestBookService;
    private final UserService userService;
    private final RoomService roomService;


    @ApiOperation(value = "현재 방의 방명록 클릭 시 아이디,닉네임,날짜 및 시간 테이블에 넣고 전체 리스트 반환", notes = "성공시 data값으로 현재 방의 방명록 리스트 반환", response = BaseResponse.class)
    @PostMapping("/{roomId}/{userId}")
    public BaseResponse insertGuestBook(@ApiParam(value = "방번호(roomId)")@PathVariable int roomId,
                                        @ApiParam(value = "로그인한 아이디")@PathVariable String userId) throws IOException {
        BaseResponse response = null;

        try{
            Room room = roomService.findByRoomId(roomId);
            User user = userService.findByUserId(userId);

            GuestBook guestBook = guestBookService.findByRoomAndUser(room, user);
            if(guestBook == null){ //넣기
                GuestBook gb = GuestBook.createGuestBook();
                gb.setRoom(room);
                gb.setUser(user);
                guestBookService.save(gb);
            }else{ //수정
                guestBook.setVisitDate(new Date());
                guestBookService.updateGuestBook(room, user, guestBook);
            }

            List<GuestBook> guestBookList  = guestBookService.findByRoom(room);
            List<GuestBookDTO> collect = guestBookList.stream()
                    .map(m-> new GuestBookDTO(m))
                    .collect(Collectors.toList());
            response = new BaseResponse("success", collect);
        }catch(IllegalStateException e){
            response = new BaseResponse("fail", e.getMessage());
        }
        return response;
    }
}