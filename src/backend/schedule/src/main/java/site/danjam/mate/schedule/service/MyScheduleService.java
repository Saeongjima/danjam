package site.danjam.mate.schedule.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import site.danjam.mate.schedule.dto.MyScheduleInputDTO;
import site.danjam.mate.schedule.dto.MyScheduleOutputDTO;
import site.danjam.mate.schedule.entity.MyScheduleEntity;
import site.danjam.mate.schedule.global.exception.NotFoundUserException;
import site.danjam.mate.schedule.repository.MyScheduleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyScheduleService {

    private final MyScheduleRepository myScheduleRepository;
    //다른 패키지에 있어서 ModelMapper 빈을 인식하지 못하면 springbootApplication에 @componentScan을 추가하면 된다.
    private final ModelMapper modelMapper;

    //새로운 스케줄 생성
    public void createMySchedule(MyScheduleInputDTO myScheduleInputDTO, String username, String role){
        //modelMapper로 서로 다른 클래스의 값을 한번에 복사
        MyScheduleEntity mySchedule = modelMapper.map(myScheduleInputDTO, MyScheduleEntity.class);
        myScheduleRepository.save(mySchedule);
    }

    //내 스케줄 전체 조회
    public List<MyScheduleOutputDTO> readMyScedule(Long userId, String role){
        //username을 통해서 memberId를 가져오기
        //memberId로 스케줄 리스트 가져오기
        //DTO로 변환하여 반환

        //해당 userId값에 해당하는 스케줄을 모두 가져옴 - 유저에 해당하는 스케줄 값들을 모두 가져온다.
        List<MyScheduleEntity> myScheduleEntities = myScheduleRepository.findAllByUserId(userId).orElseThrow(NotFoundUserException::new);

        List<MyScheduleOutputDTO> myScheduleOutputDTOS = new ArrayList<>();

        for(MyScheduleEntity myScheduleEntity : myScheduleEntities){
            myScheduleOutputDTOS.add(MyScheduleOutputDTO.builder()
                    .title(myScheduleEntity.getTitle())
                    .memo(myScheduleEntity.getMemo())
                    .startDate(myScheduleEntity.getStartDate())
                    .endDate(myScheduleEntity.getEndDate())
                    .alarm(myScheduleEntity.getAlarm())
                    .build());
        }

        return myScheduleOutputDTOS;
    }


    //스케줄 수정
    public void updateMySchedule(MyScheduleInputDTO myScheduleInputDTO,Long userId, String role){

    }

    //스케줄 삭제
    public void deleteMySchedule(String username, String role){
        //username으로 mdmberId

    }
}
