package com.example.chess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.chess.domain.GameRequest;
import com.example.chess.domain.User;

public interface GameRequestRepository extends JpaRepository<GameRequest, Long>{
	
	    void deleteBySenderAndIsAccepted(User sender, Boolean isAccepted);
	    List<GameRequest> findAllByReceiverAndIsAccepted(User receiver, Boolean isAccepted);
	    List<GameRequest> findAllBySenderAndIsAccepted(User sender, Boolean isAccepted);
	    Optional<GameRequest> findBySenderAndIsAccepted(User sender, Boolean isAccepted);
	    Optional<GameRequest> findBySenderAndReceiverAndIsAccepted(User sender, User receiver, Boolean isAccepted);

}
