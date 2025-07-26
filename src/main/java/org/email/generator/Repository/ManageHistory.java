package org.email.generator.Repository;

import org.email.generator.Model.EmailHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManageHistory extends JpaRepository<EmailHistory,Long>{

}
