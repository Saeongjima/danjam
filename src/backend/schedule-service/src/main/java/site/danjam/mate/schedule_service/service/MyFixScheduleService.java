package site.danjam.mate.schedule_service.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.danjam.mate.schedule_service.dto.MyFixScheduleInputDTO;
import site.danjam.mate.schedule_service.dto.MyFixScheduleOutputDTO;
import site.danjam.mate.schedule_service.entity.MyFixScheduleEntity;
import site.danjam.mate.schedule_service.global.exception.BaseException;
import site.danjam.mate.schedule_service.global.exception.UserScheduleException;
import site.danjam.mate.schedule_service.repository.MyFixScheduleRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MyFixScheduleService {

    private final MyFixScheduleRepository myFixScheduleRepository;

    //고정 스케줄 저장
    public void setMyFixSchedule(Long userId, String role, MyFixScheduleInputDTO myFixScheduleInputDTO){
        if(userId == null){
            throw new UserScheduleException("유저의 시간표를 저장할 수 없습니다.");
        }

        ModelMapper modelMapper = new ModelMapper();
        MyFixScheduleEntity myFixScheduleEntity = modelMapper.map(myFixScheduleInputDTO, MyFixScheduleEntity.class);
        myFixScheduleEntity.setUserId(userId);
        myFixScheduleRepository.save(myFixScheduleEntity);
    }

    //고정 스케줄 조회
    @Transactional(readOnly = true)
    public List<MyFixScheduleOutputDTO> getMyFixSchedule(Long userId){
        if(userId == null){
            throw new UserScheduleException("유저의 시간표를 불러올 수 없습니다.");
        }
        ModelMapper modelMapper = new ModelMapper();
        List<MyFixScheduleOutputDTO> myFixScheduleOutputDTOS = new ArrayList<>();

        List<MyFixScheduleEntity> myFixScheduleEntities = myFixScheduleRepository.findAllByUserId(userId);

        for(MyFixScheduleEntity myFixScheduleEntity : myFixScheduleEntities){
            MyFixScheduleOutputDTO myFixScheduleOutputDTO = modelMapper.map(myFixScheduleEntity, MyFixScheduleOutputDTO.class);
            myFixScheduleOutputDTOS.add(myFixScheduleOutputDTO);
        }

        return myFixScheduleOutputDTOS;
    }

    //고정 스케줄 수정
    @Transactional
    public void updateMyFixSchedule(Long userId, Long scheduleId, MyFixScheduleInputDTO myFixScheduleInputDTO){
        exceptionHandler(userId,scheduleId);

        MyFixScheduleEntity myFixScheduleEntity = myFixScheduleRepository.findById(scheduleId).orElseThrow(()-> new BaseException("수정할 스케줄이 존재하지 않습니다."));

        myFixScheduleEntity.setStartTime(myFixScheduleInputDTO.getStartTime());
        myFixScheduleEntity.setEndTime(myFixScheduleInputDTO.getEndTime());
        myFixScheduleEntity.setDayOfWeek(myFixScheduleInputDTO.getDayOfWeek());
        myFixScheduleRepository.save(myFixScheduleEntity);

    }

    //고정 스케줄 삭제
    @Transactional
    public void deleteMyFixSchedule(Long userId, Long scheduleId){
        exceptionHandler(userId, scheduleId);

        MyFixScheduleEntity myFixScheduleEntity = myFixScheduleRepository.findById(scheduleId).orElseThrow(()->new BaseException("삭제할 시간표를 찾을 수 없습니다."));
        //수정하려는 시간표가 내 시간표인지 확인
        if(!userId.equals(myFixScheduleEntity.getUserId())){
            throw new BaseException("다른 사람의 시간표를 수정할 수 없습니다.");
        }

        myFixScheduleRepository.deleteById(scheduleId);
    }

    private void exceptionHandler(Long userId, Long scheduleId){
        if(userId == null){
            throw new UserScheduleException("유저의 시간표를 찾을 수 없습니다.");
        }
        if(scheduleId == null){
            throw new UserScheduleException("해당 시간표는 존재하지 않습니다.");
        }
    }



}
