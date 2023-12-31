package org.example.repository;

import org.example.entity.CustomUser;
import org.example.entity.WhatsappMessage;
import org.example.util.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface MessageRepository extends JpaRepository<WhatsappMessage, String> {
    @Modifying
    @Transactional
    @Query("UPDATE WhatsappMessage m SET m.messageStatus = :messageStatus, m.lastModifiedDate = :lastModifiedDate, m.lastUpdatedBy = :lastUpdatedBy WHERE m.messageId = :messageId")
    void updateMessageStatusAndLastModifiedDateAndUpdatedUserAndLastUpdatedByById(@Param("messageId") String messageId,
                                                                                  @Param("messageStatus") MessageStatus messageStatus,
                                                                                  @Param("lastModifiedDate") Date lastModifiedDate,
                                                                                  @Param("lastUpdatedBy") CustomUser lastUpdatedBy);


}
