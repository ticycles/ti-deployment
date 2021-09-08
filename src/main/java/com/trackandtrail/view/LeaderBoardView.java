package com.trackandtrail.view;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.trackandtrail.model.user.UserModel;

import lombok.Data;

@Data
@Entity
@Immutable
@Table(name = "leader_board_view")
public class LeaderBoardView {

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "user_id", nullable = true)
	@OnDelete(action = OnDeleteAction.CASCADE)
	private UserModel user;

	@Id
	@Column(name = "avg_distance")
	private Float avgDistance;

}
