package com.example.chess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chess.domain.FriendRequest;
import com.example.chess.domain.User;
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long>{

	List<FriendRequest> findAllByReceiverAndIsAccepted(User receiver , Boolean isAccepted);
	
	void deleteByReceiverAndSenderAndIsAccepted(User receiver , String sender,Boolean isAccepted);
	
	Optional<FriendRequest> findByReceiverAndSenderAndIsAccepted(User receiver, String sender, Boolean isAccepted);
	
	
}
