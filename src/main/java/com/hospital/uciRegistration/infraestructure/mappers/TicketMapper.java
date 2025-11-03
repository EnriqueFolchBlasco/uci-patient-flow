package com.hospital.uciRegistration.infraestructure.mappers;

import com.hospital.uciRegistration.domain.model.patient.Patient;
import com.hospital.uciRegistration.domain.model.ticket.*;
import com.hospital.uciRegistration.infraestructure.entity.TicketEntity;
import com.hospital.uciRegistration.infraestructure.entity.PatientEntity;
import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    private final PatientMapper patientMapper;
    private final TriageInfoMapper triageInfoMapper;

    public TicketMapper(PatientMapper patientMapper, TriageInfoMapper triageInfoMapper) {
        this.patientMapper = patientMapper;
        this.triageInfoMapper = triageInfoMapper;
    }

    public TicketEntity toEntity(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        PatientEntity patientEntity = patientMapper.toPatientEntity(ticket.getPatient());
        boolean[] triageFlags = triageInfoMapper.toEntityFields(ticket.getTriageInfo());

        TicketEntity entity = new TicketEntity();
        entity.setId(ticket.getId());
        entity.setPatient(patientEntity);
        entity.setPriority(ticket.getPriority());

        entity.setColor(ticket.getColorCode().getColor());
        entity.setNoun(ticket.getColorCode().getNoun());
        entity.setColorCode(ticket.getColorCode().getColor_code());

        entity.setRiskOfFall(triageFlags[0]);
        entity.setBreathingDifficulty(triageFlags[1]);
        entity.setSeverePain(triageFlags[2]);
        entity.setBleeding(triageFlags[3]);
        entity.setUnconscious(triageFlags[4]);

        entity.setCreatedAt(ticket.getCreatedAt());
        entity.setStatus(ticket.getStatus());

        return entity;
    }

    public Ticket fromEntity(TicketEntity entity) {
        if (entity == null) {
            return null;
        }

        Patient patient = patientMapper.fromPatientEntity(entity.getPatient());
        TriageInfo triageInfo = triageInfoMapper.fromEntityFields(
                entity.isRiskOfFall(),
                entity.isBreathingDifficulty(),
                entity.isSeverePain(),
                entity.isBleeding(),
                entity.isUnconscious()
        );

        ColorCode code = new ColorCode(entity.getColor(), entity.getNoun());

        return new Ticket(
                entity.getId(),
                patient,
                entity.getPriority(),
                code,
                triageInfo,
                entity.getCreatedAt(),
                entity.getStatus()
        );
    }
}
