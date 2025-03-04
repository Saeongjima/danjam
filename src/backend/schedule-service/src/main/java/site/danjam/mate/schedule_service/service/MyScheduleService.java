package site.danjam.mate.schedule_service.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.schedule_service.dto.MyScheduleInputDTO;
import site.danjam.mate.schedule_service.dto.MyScheduleOutputDTO;
import site.danjam.mate.schedule_service.entity.MyScheduleEntity;
import site.danjam.mate.schedule_service.global.entity.Code;
import site.danjam.mate.schedule_service.global.exception.BaseException;
import site.danjam.mate.schedule_service.global.exception.NotFoundUserException;
import site.danjam.mate.schedule_service.global.exception.UserScheduleException;
import site.danjam.mate.schedule_service.repository.MyScheduleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyScheduleService {

    private final MyScheduleRepository myScheduleRepository;

    private final ModelMapper modelMapper;

    public void createMySchedule(MyScheduleInputDTO myScheduleInputDTO, Long userId, String role){
        //modelMapper로 서로 다른 클래스의 값을 한번에 복사
        MyScheduleEntity mySchedule = modelMapper.map(myScheduleInputDTO, MyScheduleEntity.class);
        mySchedule.setUserId(userId);
        myScheduleRepository.save(mySchedule);
    }

    //내 스케줄 전체 조회
    @Transactional(readOnly = true)
    public List<MyScheduleOutputDTO> readMyScedule(Long userId, String role){

        List<MyScheduleEntity> myScheduleEntities = myScheduleRepository.findByUserId(userId);

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


    public void updateMySchedule(MyScheduleInputDTO myScheduleInputDTO,Long userId, String role, Long scheduleId){
        // 스케쥴이 존재하지 않을 경우 예외 처리
        MyScheduleEntity myScheduleEntity = myScheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new UserScheduleException("스케줄이 존재하지 않습니다."));

        //스케줄의 유저와 해당 유저는 다르다
        if(!myScheduleEntity.getUserId().equals(userId)){
            throw new UserScheduleException("해당 유저의 스케줄이 아닙니다");
        }

        modelMapper.map(myScheduleInputDTO, myScheduleEntity);
        myScheduleRepository.save(myScheduleEntity);
    }

    public void deleteMySchedule(Long userId, String role, Long scheduleId){
        MyScheduleEntity myScheduleEntity = myScheduleRepository.findById(scheduleId).orElseThrow(BaseException::new);

        if(!myScheduleEntity.getUserId().equals(userId)){
            throw new UserScheduleException("유저의 스케줄이 아닙니다.");
        }
        myScheduleRepository.delete(myScheduleEntity);
    }
}

